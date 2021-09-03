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
        orders.setTotalPrice(totalPrice);
        entityManager.persist(orders);

        // List 자료형인 order에 요소 값을 OrderItems 객체를 생성 후 저장합니다.
        // TODO :
        for (int i = 0; i < order.size(); i++) {
            OrderItems orderItems = new OrderItems();
            orderItems.setItems(entityManager.find(Items.class, order.get(i).getItemId()));
            orderItems.setOrderQuantity(order.get(i).getQuantity());
            orderItems.setOrders(orders);
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
        List<OrderItems> orderList = entityManager.createQuery("SELECT *" +
                "FROM OrderItems as o_i " +
                        "LEFT JOIN items as i ON i.id = o_i.item_id" +
                        "LEFT JOIN orders as o ON o.id = o_i.order_id",OrderItems.class)
                .getResultList();









        // entityManager를 종료합니다.
        entityManager.flush();
        entityManager.close();

        return list;
    }
}
