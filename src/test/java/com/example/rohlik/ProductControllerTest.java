package com.example.rohlik;

import com.example.rohlik.controllers.ProductController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createProduct_test() throws Exception {
        mockMvc.perform(post("/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"vejce\",\n" +
                                "  \"quantityInStock\": 150,\n" +
                                "  \"pricePerUnit\": 5\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProduct_test() throws Exception {
        mockMvc.perform(delete("/product/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProduct_notFound_test() throws Exception {
        mockMvc.perform(delete("/product/delete/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateProduct_test() throws Exception {
        mockMvc.perform(put("/product/update/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"houska\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateProduct_notFound_test() throws Exception {
        mockMvc.perform(put("/product/update/200")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"houska\"\n" +
                                "}"))
                .andExpect(status().isNotFound());
    }




}
