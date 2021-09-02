package com.codestates.seb.CmarketServer.Repository;

import com.codestates.seb.CmarketServer.Domain.OrdersDTO;
import com.codestates.seb.CmarketServer.Domain.OrdersResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class OrderRepository {

    private final EntityManager entityManager;

    @Autowired
    public OrderRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void SaveOrders(long id, List<OrdersDTO> order, int totalPrice){
        // 유저 객체를 찾아옵니다.
        // TODO :


        // Orders 객체를 생성 후 데이터를 저장합니다.
        // TODO :


        // List 자료형인 order에 요소 값을 OrderItems 객체를 생성 후 저장합니다.
        // TODO :


        // entityManager를 종료합니다.
        entityManager.flush();
        entityManager.close();
    }

    public List<OrdersResDTO> OrdersFindById(long id) {
        List<OrdersResDTO> list = new ArrayList<>();
        // 양방향 매핑 관계를 만들어 주어야합니다.
        // orderItems 객체를 통해 Orders와 Items 객체에 접근하여야합니다.
        // TODO :


        // entityManager를 종료합니다.
        entityManager.flush();
        entityManager.close();

        return list;
    }
}
