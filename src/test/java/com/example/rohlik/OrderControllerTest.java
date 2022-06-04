package com.example.rohlik;

import com.example.rohlik.controllers.OrderController;
import com.example.rohlik.controllers.ProductController;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.nio.charset.Charset;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Order(1)
    @Test
    public void createOrder_test() throws Exception {
        mockMvc.perform(post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[ \n" +
                                "{\n" +
                                "    \"name\": \"rajce\",\n" +
                                "    \"count\": 5\n" +
                                "},\n" +
                                "{\n" +
                                "    \"name\": \"mliko\",\n" +
                                "    \"count\": 2\n" +
                                "},\n" +
                                "{\n" +
                                "    \"name\": \"rohlik\",\n" +
                                "    \"count\": 30\n" +
                                "}\n" +
                                "]"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0]['id']",is(1)))
                .andExpect(jsonPath("$[0]['product']['id']",is(2)))
                .andExpect(jsonPath("$[0]['product']['name']",is("rajce")))
                .andExpect(jsonPath("$[0]['product']['quantityInStock']",is(245)))
                .andExpect(jsonPath("$[0]['product']['pricePerUnit']",is(5)))
                .andExpect(jsonPath("$[0]['quantity']",is(5)))
                .andExpect(jsonPath("$[1]['id']",is(2)))
                .andExpect(jsonPath("$[1]['product']['id']",is(1)))
                .andExpect(jsonPath("$[1]['product']['name']",is("mliko")))
                .andExpect(jsonPath("$[1]['product']['quantityInStock']",is(198)))
                .andExpect(jsonPath("$[1]['product']['pricePerUnit']",is(10)))
                .andExpect(jsonPath("$[1]['quantity']",is(2)))
                .andExpect(jsonPath("$[2]['id']",is(3)))
                .andExpect(jsonPath("$[2]['product']['id']",is(3)))
                .andExpect(jsonPath("$[2]['product']['name']",is("rohlik")))
                .andExpect(jsonPath("$[2]['product']['quantityInStock']",is(970)))
                .andExpect(jsonPath("$[2]['product']['pricePerUnit']",is(2)))
                .andExpect(jsonPath("$[2]['quantity']",is(30)));

    }
    @Order(2)
    @Test
    public void createOrder_outOfStock_test() throws Exception {
        mockMvc.perform(post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[ \n" +
                                "{\n" +
                                "    \"name\": \"rajce\",\n" +
                                "    \"count\": 5\n" +
                                "},\n" +
                                "{\n" +
                                "    \"name\": \"mliko\",\n" +
                                "    \"count\": 20000\n" +
                                "},\n" +
                                "{\n" +
                                "    \"name\": \"rohlik\",\n" +
                                "    \"count\": 30\n" +
                                "}\n" +
                                "]"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0]['product']['id']",is(1)))
                .andExpect(jsonPath("$[0]['product']['name']",is("mliko")))
                .andExpect(jsonPath("$[0]['product']['quantityInStock']",is(198)))
                .andExpect(jsonPath("$[0]['product']['pricePerUnit']",is(10)))
                .andExpect(jsonPath("$[0]['missingCount']",is(19802)));
    }
    @Order(3)
    @Test
    public void paymentOrder_test() throws Exception {
        mockMvc.perform(get("/order/payment/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @Order(4)
    public void paymentOrder_alreadyPaid_test() throws Exception {
        mockMvc.perform(get("/order/payment/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Order(5)
    @Test
    public void cancelOrder_test() throws Exception {
        mockMvc.perform(get("/order/cancel/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Order(6)
    @Test
    public void cancelOrder_notFound_test() throws Exception {
        mockMvc.perform(get("/order/cancel/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    public void paymentOrder_notFound_test() throws Exception {
        mockMvc.perform(get("/order/payment/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
