package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.Page;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.UserDto;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions.BadRequestException;

import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryCustomImpl implements UserRepositoryCustom{
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static Logger logger = LoggerFactory.getLogger(UserRepositoryCustomImpl.class);
    private String count = "SELECT " +
            "COUNT(*) " +
            "FROM " +
            "user ";
    private String select = "SELECT " +
            "id, " +
            "is_user_enabled, "+
            "nickname, " +
            "phone_number, " +
            "role, "+
            "username "+
            "FROM " +
            "user ";

    public UserRepositoryCustomImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Page<User> findAll(UserDto userSearchParameters, Page<UserDto> requestedPageParameters) {
        SQLNamedParameters sqlNamedParameters = this.createSearchParameters(userSearchParameters);
        long numberOfItems = this.countItems(sqlNamedParameters);
        long currentPage = 1;
        long itemsPerPage = 10;


        if(requestedPageParameters!=null){
            currentPage = requestedPageParameters.getCurrentPage();
            itemsPerPage = requestedPageParameters.getItemsPerPage();
        }
        if(currentPage<1){
            throw new BadRequestException("error.bad_request.page_lower_than_one");
        }
        Page<User> returnedPage = new Page<>(currentPage,itemsPerPage,numberOfItems);
        sqlNamedParameters = this.addPaging(sqlNamedParameters, returnedPage);

        String query = this.select + sqlNamedParameters.paramsString;

        this.logQueryWithParameters(query, sqlNamedParameters);
        ///
        List<User> list = this.jdbcTemplate.query(query, sqlNamedParameters.paramsValues, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUserEnabled(rs.getBoolean("is_user_enabled"));
                user.setNickname(rs.getString("nickname"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                user.setPhoneNumber(rs.getLong("phone_number"));

                return user;
            }
        });
        returnedPage.setListOfItems(list);

        return returnedPage;
    }
    private void logQueryWithParameters(String query, SQLNamedParameters sqlNamedParameters){
        StringBuilder parametersToLog = new StringBuilder();
        sqlNamedParameters.paramsValues.entrySet().forEach(entry-> {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(value !=null){

                parametersToLog.append(key + ": " + value.toString() + " ");
            }
        });
        logger.info("JDBCTemplate QUERY: " + query);
        logger.info("JDBCTemplate QUERY PARAMETERS: " + parametersToLog.toString());
    }
    private long countItems(SQLNamedParameters sqlNamedParameters){


        if (sqlNamedParameters!=null){
            Long numberOfItems = this.jdbcTemplate
                    .queryForObject(this.count + sqlNamedParameters.paramsString, sqlNamedParameters.paramsValues,
                            Long.class);
            return numberOfItems;

        }
        return 0;
    }
    private SQLNamedParameters addPaging (SQLNamedParameters sqlNamedParameters, Page<User> page){
        if(page != null && sqlNamedParameters!=null){
            SQLNamedParameters snp = new SQLNamedParameters();
            String paramsString = sqlNamedParameters.paramsString;
            Map<String, Object> paramsValues = new HashMap<>();
            for (Map.Entry<String, Object> entry : sqlNamedParameters.paramsValues.entrySet()){
                paramsValues.put(entry.getKey(), entry.getValue());
            }
            paramsString = paramsString + " LIMIT :limit OFFSET :offset";
            paramsValues.put("limit", page.getItemsPerPage());
            paramsValues.put("offset", page.getOffset());
            snp.paramsString = paramsString;
            snp.paramsValues = paramsValues;
            return snp;
        }
        return sqlNamedParameters;
    }
    private SQLNamedParameters createSearchParameters(UserDto user){
        SQLNamedParameters sqlNamedParameters = new SQLNamedParameters();
        String params = "";
        Map<String, Object> paramsValues = new HashMap<>();
        List<String> listOfClauses= new ArrayList<>();
        if(user !=null){
            String username = user.getUsername();
            if (username != null && !username.isEmpty()) {
                String usernameClause = "username LIKE :username";
                listOfClauses.add(usernameClause);
                paramsValues.put("username", "%" + username + "%");
            }

            String nickname = user.getNickname();
            if (nickname != null && !nickname.isEmpty()) {
                String nicknameClause = "nickname LIKE :nickname";
                listOfClauses.add(nicknameClause);
                paramsValues.put("nickname", "%" + nickname + "%");
            }
            String phoneNumber = user.getPhoneNumber();
            if (phoneNumber != null && !(Long.parseLong(phoneNumber) == 0)) {
                String phoneNumberClause = "phone_number LIKE :phone_number";
                listOfClauses.add(phoneNumberClause);
                paramsValues.put("phone_number", "%" + phoneNumber + "%");
            }
            ////
            String is_user_enabled = user.isUserEnabled();
            if (is_user_enabled != null && !is_user_enabled.isEmpty()) {
                String is_user_enabledClause = "";
                if(is_user_enabled.equalsIgnoreCase("TRUE")){
                     is_user_enabledClause = "is_user_enabled = 1";
                }
                else{
                     is_user_enabledClause = "is_user_enabled = 0";
                }

                listOfClauses.add(is_user_enabledClause);

            }
            ///
            String role = user.getRole();
            if (role != null && !role.isEmpty()) {
                String roleClause = "role LIKE :role";
                listOfClauses.add(roleClause);
                paramsValues.put("role", "%" + role + "%");
            }


        }
        if(listOfClauses.size()>0){
            params = params + " WHERE " + listOfClauses.get(0);
            if (listOfClauses.size()>1){
                for(int i = 1; i< listOfClauses.size(); i++){
                    params = params + " AND " + listOfClauses.get(i);
                }
            }
        }
        sqlNamedParameters.paramsString = params;
        sqlNamedParameters.paramsValues = paramsValues;

        return sqlNamedParameters;

    }
    class SQLNamedParameters{
        private String paramsString;
        private Map<String, Object> paramsValues;


    }
}
