package com.codestates.seb.CmarketServer.Repository;

import com.codestates.seb.CmarketServer.Domain.OrdersDTO;
import com.codestates.seb.CmarketServer.Domain.OrdersResDTO;
import com.codestates.seb.CmarketServer.Entity.Items;
import com.codestates.seb.CmarketServer.Entity.OrderItems;
import com.codestates.seb.CmarketServer.Entity.Orders;
import com.codestates.seb.CmarketServer.Entity.Users;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class OrderRepository {

    private final EntityManager entityManager;

    @Autowired
    public OrderRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void SaveOrders(long id, List<OrdersDTO> order, int totalPrice) {
        // 유저 객체를 찾아옵니다.
        // TODO :
        Users users = entityManager.find(Users.class, id);

        // Orders 객체를 생성 후 데이터를 저장합니다.
        // TODO :
        Orders orders = new Orders();
        orders.setUsers(users);
        orders.setCreatedAt(new Date());
        orders.setTotalPrice(totalPrice);
        entityManager.persist(orders);

        // List 자료형인 order에 요소 값을 OrderItems 객체를 생성 후 저장합니다.
        // TODO :
        for(OrdersDTO ordersDTO : order){
            OrderItems orderItems = new OrderItems();
            Items items = entityManager.find(Items.class, ordersDTO.getItemId());
            orderItems.setOrders(orders);
            orderItems.setItems(items);
            orderItems.setOrderQuantity(ordersDTO.getQuantity());
            entityManager.persist(orderItems);
        }

        // entityManager를 종료합니다.
        entityManager.flush();
        entityManager.close();
    }

    public List<OrdersResDTO> OrdersFindById(long id) {
        List<OrdersResDTO> list = new ArrayList<>();
        // 양방향 매핑 관계를 만들어 주어야합니다.
        // orderItems 객체를 통해 Orders와 Items 객체에 접근하여야합니다.
        // TODO :
        List<Orders> ordersList = entityManager.find(Users.class,id).getOrdersList();

        for (Orders orders : ordersList){
            List<OrderItems> orderItemsList = entityManager.find(Orders.class, orders.getId()).getOrderItemsList();

            for(OrderItems orderItems : orderItemsList){
                OrdersResDTO ordersResDTO = new OrdersResDTO();
                ordersResDTO.setId(orderItems.getId());
                ordersResDTO.setCreated_at(orders.getCreatedAt());
                ordersResDTO.setImage(orderItems.getItems().getImage());
                ordersResDTO.setName(orderItems.getItems().getName());
                ordersResDTO.setOrder_quantity(orderItems.getOrderQuantity());
                ordersResDTO.setPrice(orderItems.getItems().getPrice());
                ordersResDTO.setTotal_price(orders.getTotalPrice());
                list.add(ordersResDTO);
            }
        }

        // entityManager를 종료합니다.
        entityManager.flush();
        entityManager.close();

        return list;
    }
}
