package com.codestates.seb.CmarketServer.Domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrdersResDTO {

    private long id;
    private Date created_at;
    private String image;
    private String name;
    private int order_quantity;
    private int price;
    private int total_price;

}
