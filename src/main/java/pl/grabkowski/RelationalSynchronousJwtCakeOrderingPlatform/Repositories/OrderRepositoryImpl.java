package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories;

import org.springframework.stereotype.Repository;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO.OrderFilterOptions;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.Order;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.OrderStatus;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepositoryCustom{

    private final EntityManager entityManager;
    private String findFilteredOrders = "SELECT DISTINCT o, t, i, u, ut, so FROM Order o LEFT JOIN FETCH o.orderTasteSet t LEFT JOIN FETCH o.image i LEFT JOIN FETCH o.user u LEFT JOIN FETCH u.userToken ut LEFT JOIN FETCH u.setOfOrders so";

    public OrderRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Order> findFiltered(OrderFilterOptions orderFilterOptions) {
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
            String typeOfProductClause = "o.typeOfProduct LIKE '%" + typeOfProduct + "%'";
            listOfClauses.add(typeOfProductClause);
        }
        String status = orderFilterOptions.getOrderStatus();
        if(status!=null && !status.isEmpty()){
            OrderStatus orderStatus = OrderStatus.parseString(status);
            if(orderStatus!=null){
                String statusClause = "o.orderStatus LIKE '%" + status + "%'";
                listOfClauses.add(statusClause);

            }

        }

        String fromEventDate = orderFilterOptions.getFromEventDate();
        if(fromEventDate!=null && !fromEventDate.isEmpty()){
            LocalDate date = LocalDate.parse(fromEventDate);
            String fromEventDateClause = "o.eventDate >= '" + date + "'";
            listOfClauses.add(fromEventDateClause);
        }

        String upToEventDate = orderFilterOptions.getUpToEventDate();
        if(upToEventDate!=null && !upToEventDate.isEmpty()){
            LocalDate date = LocalDate.parse(upToEventDate);
            String upToEventDateClause = "o.eventDate <= '" + date + "'";
            listOfClauses.add(upToEventDateClause);
        }


        if(listOfClauses.size()>0){
            params = params + " WHERE " + listOfClauses.get(0);
            if (listOfClauses.size()>1){
                for(int i = 1; i< listOfClauses.size(); i++){
                    params = params + " AND " + listOfClauses.get(i);
                }
            }
        }


        return this.entityManager.createQuery(this.findFilteredOrders + params).getResultList();
    }




}
