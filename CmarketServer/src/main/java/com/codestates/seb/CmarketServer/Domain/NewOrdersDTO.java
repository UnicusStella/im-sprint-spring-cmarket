package com.codestates.seb.CmarketServer.Domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewOrdersDTO {

    private List<OrdersDTO> orders;
    private int totalPrice;

}
