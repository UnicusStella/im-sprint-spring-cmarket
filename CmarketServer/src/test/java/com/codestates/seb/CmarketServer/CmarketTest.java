package com.codestates.seb.CmarketServer;

import com.codestates.seb.CmarketServer.CodeStatesSubmit.Submit;
import com.codestates.seb.CmarketServer.Domain.NewOrdersDTO;
import com.codestates.seb.CmarketServer.Domain.OrdersDTO;
import com.codestates.seb.CmarketServer.Domain.OrdersResDTO;
import com.codestates.seb.CmarketServer.Entity.OrderItems;
import com.codestates.seb.CmarketServer.Entity.Orders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.persistence.EntityManager;
import java.util.*;

@AutoConfigureMockMvc
@SpringBootTest
public class CmarketTest {

    private static Submit submit = new Submit();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private EntityManager entityManager;

    @AfterAll
    static void after() throws Exception {
        submit.SubmitJson("im-sprint-spring-cmarket", 3);
        submit.ResultSubmit();
    }

    @BeforeEach
    public void beforEach() throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(MockMvcResultHandlers.print())
                .build();

        objectMapper = Jackson2ObjectMapperBuilder.json()
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .modules(new JavaTimeModule())
                .build();
    }

    @Test
    @DisplayName(value = "데이터베이스에 저장된 상품 목록을 가져와야합니다.")
    void FindItem() throws Exception {
        MvcResult result = null;
        String data = null;
        String url = "/items";
        String standard = "[{\"id\":1,\"name\":\"노른자 분리기\",\"price\":9900,\"image\":\"../images/egg.png\"},{\"id\":2,\"name\":\"2020년 달력\",\"price\":12000,\"image\":\"../images/2020.jpg\"},{\"id\":3,\"name\":\"개구리 안대\",\"price\":2900,\"image\":\"../images/frog.jpg\"},{\"id\":4,\"name\":\"뜯어온 보도블럭\",\"price\":4900,\"image\":\"../images/block.jpg\"},{\"id\":5,\"name\":\"칼라 립스틱\",\"price\":2900,\"image\":\"../images/lip.jpg\"},{\"id\":6,\"name\":\"잉어 슈즈\",\"price\":3900,\"image\":\"../images/fish.jpg\"},{\"id\":7,\"name\":\"웰컴 매트\",\"price\":6900,\"image\":\"../images/welcome.jpg\"},{\"id\":8,\"name\":\"강시 모자\",\"price\":9900,\"image\":\"../images/hat.jpg\"}]";
        try{
            result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            data = result.getResponse().getContentAsString();
            submit.ResultSave(data.equals(standard));
        }finally {
            Assertions.assertEquals(data, standard);
        }

    }

    @Test
    @DisplayName(value = "주문내역을 데이터베이스에 저장해야합니다.")
    void CreateOrder() throws Exception{
        MvcResult result = null;
        List<OrderItems> orderItems = new ArrayList<>();
        String url = "/users/1/orders/new";
        String standard = "{\"orders\":[{\"quantity\":1,\"itemId\":2}],\"totalPrice\":12000}";

        OrdersDTO ordersDTO_1 = new OrdersDTO();
        ordersDTO_1.setQuantity(1);
        ordersDTO_1.setItemId(2);

        OrdersDTO ordersDTO_2 = new OrdersDTO();
        ordersDTO_2.setQuantity(2);
        ordersDTO_2.setItemId(3);

        ArrayList<OrdersDTO> ordersDTOS = new ArrayList<>();
        ordersDTOS.add(ordersDTO_1);
        ordersDTOS.add(ordersDTO_2);

        NewOrdersDTO newOrdersDTO = new NewOrdersDTO();
        newOrdersDTO.setOrders(ordersDTOS);
        newOrdersDTO.setTotalPrice(16900);

        try{
            String content = objectMapper.writeValueAsString(newOrdersDTO);
            result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            orderItems = entityManager
                    .createQuery("SELECT orderItem FROM OrderItems AS orderItem", OrderItems.class)
                    .getResultList();

            submit.ResultSave(String.valueOf(orderItems.get(0).getOrders().getTotalPrice()).equals("16900"));
        }catch (Exception e){
            System.out.println(e);
        }finally {
            Assertions.assertEquals(orderItems.get(0).getOrders().getTotalPrice(),16900);
        }
    }

    @Test
    @DisplayName(value = "데이터베이스에 저장된 주문내역을 가져와야합니다.")
    void OrderFindById() throws Exception{
        OrdersResDTO[] GetData= null;
        MvcResult result = null;
        String PostUrl = "/users/1/orders/new";

        OrdersDTO ordersDTO_1 = new OrdersDTO();
        ordersDTO_1.setQuantity(1);
        ordersDTO_1.setItemId(2);

        OrdersDTO ordersDTO_2 = new OrdersDTO();
        ordersDTO_2.setQuantity(2);
        ordersDTO_2.setItemId(3);

        ArrayList<OrdersDTO> ordersDTOS = new ArrayList<>();
        ordersDTOS.add(ordersDTO_1);
        ordersDTOS.add(ordersDTO_2);

        NewOrdersDTO newOrdersDTO = new NewOrdersDTO();
        newOrdersDTO.setOrders(ordersDTOS);
        newOrdersDTO.setTotalPrice(16900);

        String content = objectMapper.writeValueAsString(newOrdersDTO);

        result = mockMvc.perform(MockMvcRequestBuilders.post(PostUrl)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String getUrl = "/users/1/orders";

        try{
            result = mockMvc.perform(MockMvcRequestBuilders.get(getUrl)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            GetData = objectMapper.readValue(result.getResponse().getContentAsString(), OrdersResDTO[].class);
            System.out.println(GetData[0].getCreated_at());
            submit.ResultSave(GetData[0].getName().equals("2020년 달력"));
        }finally {
            Assertions.assertEquals(GetData[0].getName(), "2020년 달력");
        }
    }
}
