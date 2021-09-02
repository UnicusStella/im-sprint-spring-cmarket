package com.codestates.seb.CmarketServer.Service;

import com.codestates.seb.CmarketServer.Domain.OrdersDTO;
import com.codestates.seb.CmarketServer.Domain.OrdersResDTO;
import com.codestates.seb.CmarketServer.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void CreateOrder(long id, List<OrdersDTO> order, int totalPrice){
        orderRepository.SaveOrders(id, order, totalPrice);
    }

    public List<OrdersResDTO> ResponseOrderData(long id){
        return orderRepository.OrdersFindById(id);
    }

}
