package com.codestates.seb.CmarketServer.Controller;

import com.codestates.seb.CmarketServer.Domain.NewOrdersDTO;
import com.codestates.seb.CmarketServer.Domain.OrdersResDTO;
import com.codestates.seb.CmarketServer.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 주문하기
    @PostMapping(value = "/users/{userId}/orders/new")
    public ResponseEntity<?> CreateOrder(@PathVariable(value = "userId", required = true)Long userId,
                                         @RequestBody(required = true)NewOrdersDTO newOrdersDTO){

        if(newOrdersDTO.getOrders().size() == 0){
            return ResponseEntity.badRequest().body("Bad request.");
        }

        try{
            orderService.CreateOrder(userId, newOrdersDTO.getOrders(), newOrdersDTO.getTotalPrice());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Not found");
        }

        return ResponseEntity.ok().body("Order has been placed.");
    }

    // 주문 내역 조회
    @GetMapping(value = "/users/{userId}/orders")
    public ResponseEntity<?> GetOrderList(@PathVariable(value = "userId", required = true) Long userId) {

        List<OrdersResDTO> ordersResDTO = orderService.ResponseOrderData(userId);

        if(ordersResDTO == null){
            return ResponseEntity.badRequest().body("Not found");
        }

        return ResponseEntity.ok().body(ordersResDTO);
    }
}
