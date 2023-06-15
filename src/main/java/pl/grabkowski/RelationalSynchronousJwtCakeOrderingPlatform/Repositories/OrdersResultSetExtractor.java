package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Repositories;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class OrdersResultSetExtractor implements ResultSetExtractor<List<Order>> {

    @Override
    public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, Order> map = new HashMap<>();
        while(rs.next()){

            Order order = null;

            Long o_id = rs.getLong("o_id");
            LocalDateTime creation_date = null;
            if(rs.getTimestamp("creation_date")!=null){
                creation_date = rs.getTimestamp("creation_date").toLocalDateTime();
            }
            String o_description = rs.getString("o_description");
            LocalDate event_date = null;
            if(rs.getDate("event_date")!=null){
                event_date = rs.getDate("event_date").toLocalDate();
            }
            String number_of_servings = rs.getString("number_of_servings");
            OrderStatus order_status = null;
            if(rs.getString("order_status")!=null){
                order_status = OrderStatus.parseString(rs.getString("order_status"));
            }
            Long o_phone_number = rs.getLong("o_phone_number");
            String type_of_product = rs.getString("type_of_product");

            Long i_id = rs.getLong("i_id");
            String i_description = rs.getString("i_description");
            ImageDestination image_destination = null;
            if(rs.getString("image_destination")!=null) {
                image_destination = ImageDestination.valueOf(rs.getString("image_destination"));
            }
            String outer_service_id = rs.getString("outer_service_id");
            String url = rs.getString("url");
            Image image = new Image(i_id,url,outer_service_id,i_description,image_destination);


            Long u_id = rs.getLong("u_id");
            boolean is_user_enabled = rs.getBoolean("is_user_enabled");
            String nickname = rs.getString("nickname");
            Long u_phone_number = rs.getLong("u_phone_number");
            String role = rs.getString("role");
            String username = rs.getString("username");
            User user = new User(u_id,username,null, role,nickname,u_phone_number,is_user_enabled,null,null);

            Long ot_id = rs.getLong("ot_id");
            String name = rs.getString("name");
            OrderTaste orderTaste = new OrderTaste(ot_id,name,null);

            order = map.get(o_id);
            if(order == null){
                Set<OrderTaste> setOfTaste = new HashSet<>();
                setOfTaste.add(orderTaste);
                order = new Order(o_id,
                        user,
                        o_phone_number,
                        event_date,
                        type_of_product,
                        number_of_servings,
                        setOfTaste,
                        o_description,
                        image,
                        order_status,
                        creation_date);
                map.put(o_id, order);
            }
            else{
                Set<OrderTaste> setOfTaste = order.getOrderTasteSet();
                if(!setOfTaste.contains(orderTaste)){
                    setOfTaste.add(orderTaste);
                }
            }


        }
        return map.values().stream().collect(Collectors.toList());
    }
}
