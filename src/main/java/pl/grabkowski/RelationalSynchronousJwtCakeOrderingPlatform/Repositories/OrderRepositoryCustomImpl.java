package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.OrderFilterOptions;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.Sort;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Order;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.OrderStatus;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom{

    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;
    private String findFilteredOrders = "SELECT DISTINCT o, t, i, u, ut, so FROM Order o LEFT JOIN FETCH o.orderTasteSet t LEFT JOIN FETCH o.image i LEFT JOIN FETCH o.user u LEFT JOIN FETCH u.userToken ut LEFT JOIN FETCH u.setOfOrders so";
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



    public OrderRepositoryCustomImpl(EntityManager entityManager, JdbcTemplate jdbcTemplate) {
        this.entityManager = entityManager;
        this.jdbcTemplate = jdbcTemplate;
    }
    /// params for native query (f.ex. "WHERE o.event_date >= ?"),
    /// for JPQL should be like this: "WHERE o.eventDate >= ?"
    private String createParamsFromOrderFilterOptionsDto(OrderFilterOptions orderFilterOptions){
        String params = "";
        List<String> listOfClauses= new ArrayList<>();

        String username = orderFilterOptions.getUsername();
        if(username!=null && !username.isEmpty()){
            String usernameClause = "u.username LIKE '%" + username + "%'";
            listOfClauses.add(usernameClause);
        }

        String nickname = orderFilterOptions.getNickname();
        if(nickname!=null && !nickname.isEmpty()){
            String nicknameClause = "u.nickname LIKE '%" + nickname + "%'";
            listOfClauses.add(nicknameClause);
        }

        String description = orderFilterOptions.getDescription();
        if(description!=null && !description.isEmpty()){
            String descriptionClause = "o.description LIKE '%" + description + "%'";
            listOfClauses.add(descriptionClause);
        }

        String typeOfProduct = orderFilterOptions.getTypeOfProduct();
        if(typeOfProduct!=null && !typeOfProduct.isEmpty()){
            String typeOfProductClause = "o.type_of_product LIKE '%" + typeOfProduct + "%'";
            listOfClauses.add(typeOfProductClause);
        }
        String status = orderFilterOptions.getOrderStatus();
        if(status!=null && !status.isEmpty()){
            OrderStatus orderStatus = OrderStatus.parseString(status);
            if(orderStatus!=null){
                String statusClause = "o.order_status LIKE '%" + status + "%'";
                listOfClauses.add(statusClause);

            }

        }

        String fromEventDate = orderFilterOptions.getFromEventDate();
        if(fromEventDate!=null && !fromEventDate.isEmpty()){
            LocalDate date = LocalDate.parse(fromEventDate);
            String fromEventDateClause = "o.event_date >= '" + date + "'";
            listOfClauses.add(fromEventDateClause);
        }

        String upToEventDate = orderFilterOptions.getUpToEventDate();
        if(upToEventDate!=null && !upToEventDate.isEmpty()){
            LocalDate date = LocalDate.parse(upToEventDate);
            String upToEventDateClause = "o.event_date <= '" + date + "'";
            listOfClauses.add(upToEventDateClause);
        }

        //////
        String fromCreationDate = orderFilterOptions.getFromCreationDate();
        if(fromCreationDate!=null && !fromCreationDate.isEmpty()){
            LocalDate date = LocalDate.parse(fromCreationDate);
            String fromCreationDateClause = "o.creation_date >= '" + date + "'";
            listOfClauses.add(fromCreationDateClause);
        }

        String upToCreationDate = orderFilterOptions.getUpToCreationDate();
        if(upToCreationDate!=null && !upToCreationDate.isEmpty()){
            LocalDateTime date = LocalDate.parse(upToCreationDate).atTime(23,59,59,999999);
            String dateString = date.toString().replace("T"," ");
            String upToCreationDateClause = "o.creation_date <= '" + dateString + "'";
            listOfClauses.add(upToCreationDateClause);
        }
        ////

        Long phoneNumber = orderFilterOptions.getPhoneNumber();
        if(phoneNumber!=null && !(phoneNumber.longValue()== 0)){
            String phoneNumberClause = "o.phone_number LIKE '%" + phoneNumber + "%'";
            listOfClauses.add(phoneNumberClause);
        }



        if(listOfClauses.size()>0){
            params = params + " WHERE " + listOfClauses.get(0);
            if (listOfClauses.size()>1){
                for(int i = 1; i< listOfClauses.size(); i++){
                    params = params + " AND " + listOfClauses.get(i);
                }
            }
        }

        return params;


    }

    @Override
    public List<Order> findFiltered(OrderFilterOptions orderFilterOptions) {

        String params = this.createParamsFromOrderFilterOptionsDto(orderFilterOptions);


        return this.entityManager.createQuery(this.findFilteredOrders + params)
                .getResultList();
    }

    public List<Order> findFiltered(OrderFilterOptions orderFilterOptions, Sort sort){
        String params = this.createParamsFromOrderFilterOptionsDto(orderFilterOptions);
        List<Order> list = jdbcTemplate.query(this.findFilteredOrdersNative + params, new OrdersResultSetExtractor());

        return list;
    }

    @Override
    public List<Order> findFilteredByUserId(OrderFilterOptions orderFilterOptions, Long id) {
        String params = this.createParamsFromOrderFilterOptionsDto(orderFilterOptions);
        String query = this.findFilteredOrdersNative;
        String whereUserId = " u.id = ";
        if(params !=null && params.length() >0){
            query = query + params + " AND" + whereUserId + id;
        }
        else {

            query = query + " WHERE " + whereUserId + id;
        }
        List<Order> list = jdbcTemplate.query(query, new OrdersResultSetExtractor());
        return list;

    }
}
