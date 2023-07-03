package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.OrderFilterOptions;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.Page;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.Sort;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Exceptions.BadRequestException;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Image;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Order;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.OrderStatus;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom{

    private final EntityManager entityManager;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static Logger logger = LoggerFactory.getLogger(OrderRepositoryCustomImpl.class);

    private String selectAllColumns = "SELECT " +
            "o.id AS o_id, o.creation_date, o.description AS o_description, o.event_date, o.number_of_servings, o.order_status, o.phone_number AS o_phone_number, o.type_of_product, o.image_id, o.user_id, " +
            "i.id AS i_id, i.description AS i_description, i.image_destination, i.outer_service_id, i.url, " +
            "u.id AS u_id, u.is_user_enabled, u.nickname, u.phone_number AS u_phone_number, u.role, u.username, " +
            "ot.id AS ot_id, ot.name, ot.order_id";
    private String selectDistinctIds = "SELECT DISTINCT o.id AS o_id";
    private String from = " FROM orders AS o " +
            "LEFT JOIN order_taste AS ot ON o.id = ot.order_id " +
            "LEFT JOIN image AS i ON o.image_id = i.id " +
            "LEFT JOIN user AS u ON o.user_id = u.id";


    private String selectCount = "SELECT COUNT(DISTINCT o.id) FROM " +
            "orders AS o " +
            "LEFT JOIN order_taste AS ot ON o.id = ot.order_id " +
            "LEFT JOIN image AS i ON o.image_id = i.id " +
            "LEFT JOIN user AS u ON o.user_id = u.id";


    ///JPQL option
    // private String findFilteredOrdersJPQL = "SELECT DISTINCT o, t, i, u, ut, so FROM Order o LEFT JOIN FETCH o.orderTasteSet t LEFT JOIN FETCH o.image i LEFT JOIN FETCH o.user u LEFT JOIN FETCH u.userToken ut LEFT JOIN FETCH u.setOfOrders so";
    private String findFilteredOrdersNative = "SELECT " +
            "o.id AS o_id, o.creation_date, o.description AS o_description, o.event_date, o.number_of_servings, o.order_status, o.phone_number AS o_phone_number, o.type_of_product, o.image_id, o.user_id, "+
            "i.id AS i_id, i.description AS i_description, i.image_destination, i.outer_service_id, i.url, " +
            "u.id AS u_id, u.is_user_enabled, u.nickname, u.phone_number AS u_phone_number, u.role, u.username, " +
            "ot.id AS ot_id, ot.name, ot.order_id "+
            "FROM " +
            "orders AS o "+
            "LEFT JOIN order_taste AS ot ON o.id = ot.order_id " +
            "LEFT JOIN image AS i ON o.image_id = i.id "+
            "LEFT JOIN user AS u ON o.user_id = u.id";



    public OrderRepositoryCustomImpl(EntityManager entityManager, NamedParameterJdbcTemplate jdbcTemplate) {
        this.entityManager = entityManager;
        this.jdbcTemplate = jdbcTemplate;
    }

    ///JPQL option
    /*  @Override
    public List<Order> findFiltered(OrderFilterOptions orderFilterOptions, Sort sort) {

        String params = this.createJPQLParamsFromOrderFilterOptionsDto(orderFilterOptions);


        return this.entityManager.createQuery(this.findFilteredOrdersJPQL + params)
                .getResultList();
    }

   */

    public List<Order>findFilteredTest(){
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("description", "%nothing%");
        String query = this.findFilteredOrdersNative + " WHERE o.description LIKE :description";

        return this.jdbcTemplate.query(query,sqlParameterSource,new OrdersResultSetExtractor());
    }
    @Override
    public Page<Order> findFiltered(Sort sort, Page<Order> page){
        return this.findFiltered(null, sort, page);

    }
    @Override
    public Page<Order> findFiltered(OrderFilterOptions orderFilterOptions, Page<Order> page){

        return this.findFiltered(orderFilterOptions, null,page);
    }

    public Page<Order> findFiltered(OrderFilterOptions orderFilterOptions, Sort sort, Page<Order> page){
       return this.findFilteredByUserId(orderFilterOptions,sort,page,null);
    }

    @Override
    public Page<Order> findFilteredByUserId(OrderFilterOptions orderFilterOptions, Sort sort, Page<Order> page, Long id) {
        SQLNamedParameters sqlNamedParameters = this.createNativeParamsFromOrderFilterOptionsDto(orderFilterOptions);
        sqlNamedParameters = this.addUserIdParam(sqlNamedParameters, id);

        ///////

        long numberOfItems = this.countItems(sqlNamedParameters);
        long currentPage = 1;
        long itemsPerPage = 10;


        if(page!=null){
            currentPage = page.getCurrentPage();
            itemsPerPage = page.getItemsPerPage();


        }
        if(currentPage<1){
            throw new BadRequestException("error.bad_request.page_lower_than_one");
        }
        Page<Order> returnedPage = new Page<>(currentPage,itemsPerPage,numberOfItems);





        sqlNamedParameters = this.addNativeSortingParams(sqlNamedParameters, sort);
        sqlNamedParameters = this.addPaging(sqlNamedParameters, returnedPage);



        StringBuilder parametersToLog = new StringBuilder();
        sqlNamedParameters.paramsValues.entrySet().forEach(entry-> {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(value !=null){

                parametersToLog.append(key + ": " + value.toString() + " ");
            }


        });

        String query = this.selectAllColumns +
                this.from +
                " JOIN" +
                " (" +
                this.selectDistinctIds +
                this.from +
                sqlNamedParameters.paramsString +
                ")" +
                " AS o1" +
                " ON o.id = o1.o_id";
        if (sort != null && sort.getParameter()!=null){
           query = query + " ORDER BY " + sort.getParameter().getValue()+ " " + sort.getSortingDirection();

        }
        logger.info("JDBCTemplate QUERY: " + query);

        logger.info("JDBCTemplate QUERY PARAMETERS: " + parametersToLog.toString());

        List<Order> list = jdbcTemplate.query(query, sqlNamedParameters.paramsValues, new OrdersResultSetExtractor());

        returnedPage.setListOfItems(list);


        return returnedPage;



    }
    ////
    /// private methods
    ////

    private long countItems(SQLNamedParameters sqlNamedParameters){


        if (sqlNamedParameters!=null){
            Long numberOfItems = this.jdbcTemplate
                    .queryForObject(this.selectCount + sqlNamedParameters.paramsString, sqlNamedParameters.paramsValues,
                            Long.class);
            return numberOfItems;

        }
        return 0;
    }


    // doesn't change the state of provided sqlNamedParameters
    private SQLNamedParameters addUserIdParam(SQLNamedParameters sqlNamedParameters, Long id){
        if(id !=null && id >0 && sqlNamedParameters != null){
            //
            SQLNamedParameters snp = new SQLNamedParameters();
            String paramsString = sqlNamedParameters.paramsString;
            Map<String, Object> paramsValues = new HashMap<>();
            for (Map.Entry<String, Object> entry : sqlNamedParameters.paramsValues.entrySet()){
                paramsValues.put(entry.getKey(), entry.getValue());

            }
            //
            String whereUserId = " u.id = :u_id";
            if(paramsString !=null && paramsString.length() >0){

                paramsString = paramsString + " AND" + whereUserId;
                paramsValues.put("u_id", id);
            }
            else {

                paramsString = paramsString + " WHERE" + whereUserId;
                paramsValues.put("u_id", id);


            }
            snp.paramsString = paramsString;
            snp.paramsValues = paramsValues;
            return snp;

        }
        return sqlNamedParameters;
    }

    // doesn't change the state of provided sqlNamedParameters
    private SQLNamedParameters addPaging (SQLNamedParameters sqlNamedParameters, Page<Order> page){


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
    // doesn't change the state of provided sqlNamedParameters
    private SQLNamedParameters addNativeSortingParams(SQLNamedParameters sqlNamedParameters, Sort sort){

        if(sort != null && sqlNamedParameters!=null){
            SQLNamedParameters snp = new SQLNamedParameters();
            String paramsString = sqlNamedParameters.paramsString;
            Map<String, Object> paramsValues = new HashMap<>();
            for (Map.Entry<String, Object> entry : sqlNamedParameters.paramsValues.entrySet()){
                paramsValues.put(entry.getKey(), entry.getValue());

            }
            if(sort.getParameter()!=null){
                paramsString = paramsString + " ORDER BY " + sort.getParameter().getValue()+ " " + sort.getSortingDirection();


            }
            snp.paramsString = paramsString;
            snp.paramsValues = paramsValues;
            return snp;
        }

       return sqlNamedParameters;

    }
    /// params for native query (f.ex. "WHERE o.event_date >= ?"),
    /// for JPQL should be like this: "WHERE o.eventDate >= ?"
    private SQLNamedParameters createNativeParamsFromOrderFilterOptionsDto(OrderFilterOptions orderFilterOptions){
        SQLNamedParameters sqlNamedParameters = new SQLNamedParameters();
        String params = "";
        Map<String, Object> paramsValues = new HashMap<>();
        List<String> listOfClauses= new ArrayList<>();
        if(orderFilterOptions!=null) {


            String username = orderFilterOptions.getUsername();
            if (username != null && !username.isEmpty()) {
                String usernameClause = "u.username LIKE :u_username";
                listOfClauses.add(usernameClause);
                paramsValues.put("u_username", "%" + username + "%");
            }

            String nickname = orderFilterOptions.getNickname();
            if (nickname != null && !nickname.isEmpty()) {
                String nicknameClause = "u.nickname LIKE :u_nickname";
                listOfClauses.add(nicknameClause);
                paramsValues.put("u_nickname", "%" + nickname + "%");
            }

            String description = orderFilterOptions.getDescription();
            if (description != null && !description.isEmpty()) {
                String descriptionClause = "o.description LIKE :o_description";
                listOfClauses.add(descriptionClause);
                paramsValues.put("o_description", "%" + description + "%");
            }

            String typeOfProduct = orderFilterOptions.getTypeOfProduct();
            if (typeOfProduct != null && !typeOfProduct.isEmpty()) {
                String typeOfProductClause = "o.type_of_product LIKE ':o_type_of_product";
                listOfClauses.add(typeOfProductClause);
                paramsValues.put("o_type_of_product", "%" + typeOfProduct + "%");
            }
            String status = orderFilterOptions.getOrderStatus();
            if (status != null && !status.isEmpty()) {
                OrderStatus orderStatus = OrderStatus.parseString(status);
                if (orderStatus != null) {
                    String statusClause = "o.order_status LIKE :o_order_status";
                    listOfClauses.add(statusClause);
                    paramsValues.put("o_order_status", "%" + orderStatus + "%");

                }

            }

            String fromEventDate = orderFilterOptions.getFromEventDate();
            if (fromEventDate != null && !fromEventDate.isEmpty()) {
                LocalDate date = LocalDate.parse(fromEventDate);
                String fromEventDateClause = "o.event_date >= :from_event_date";
                listOfClauses.add(fromEventDateClause);
                paramsValues.put("from_event_date", date);
            }

            String upToEventDate = orderFilterOptions.getUpToEventDate();
            if (upToEventDate != null && !upToEventDate.isEmpty()) {
                LocalDate date = LocalDate.parse(upToEventDate);
                String upToEventDateClause = "o.event_date <= :up_to_event_date";
                listOfClauses.add(upToEventDateClause);
                paramsValues.put("up_to_event_date", date);
            }

            //////
            String fromCreationDate = orderFilterOptions.getFromCreationDate();
            if (fromCreationDate != null && !fromCreationDate.isEmpty()) {
                LocalDate date = LocalDate.parse(fromCreationDate);
                String fromCreationDateClause = "o.creation_date >= :from_creation_date";
                listOfClauses.add(fromCreationDateClause);
                paramsValues.put("from_creation_date", date);
            }

            String upToCreationDate = orderFilterOptions.getUpToCreationDate();
            if (upToCreationDate != null && !upToCreationDate.isEmpty()) {
                LocalDateTime date = LocalDate.parse(upToCreationDate).atTime(23, 59, 59, 999999);
                String dateString = date.toString().replace("T", " ");
                String upToCreationDateClause = "o.creation_date <= :up_to_creation_date";
                listOfClauses.add(upToCreationDateClause);
                paramsValues.put("up_to_creation_date", dateString);
            }
            ////

            Long phoneNumber = orderFilterOptions.getPhoneNumber();
            if (phoneNumber != null && !(phoneNumber.longValue() == 0)) {
                String phoneNumberClause = "o.phone_number LIKE :o_phone_number";
                listOfClauses.add(phoneNumberClause);
                paramsValues.put("o_phone_number", "%" + phoneNumber + "%");
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
