package com.example.simplecurdapp.controller;

import com.example.simplecurdapp.exception.ResourceNotFoundException;
import com.example.simplecurdapp.model.Product;
import com.example.simplecurdapp.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    void getAllProductsShouldReturnOkAndList() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(createProduct(1L, "Phone")));

        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Phone"));
    }

    @Test
    void getProductByIdShouldReturnOkWhenFound() throws Exception {
        when(productService.getProductById(1L)).thenReturn(createProduct(1L, "Laptop"));

        mockMvc.perform(get("/api/products/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void getProductByIdShouldReturnNotFoundWhenMissing() throws Exception {
        doThrow(new ResourceNotFoundException("Product", "id", 99L))
            .when(productService).getProductById(99L);

        mockMvc.perform(get("/api/products/99"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void createProductShouldReturnCreated() throws Exception {
        Product created = createProduct(3L, "Tablet");
        when(productService.createProduct(org.mockito.ArgumentMatchers.any(Product.class))).thenReturn(created);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createProduct(null, "Tablet"))))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.name").value("Tablet"));
    }

    @Test
    void deleteProductShouldReturnNoContent() throws Exception {
        doNothing().when(productService).deleteProduct(5L);

        mockMvc.perform(delete("/api/products/5"))
            .andExpect(status().isNoContent());
    }

    @Test
    void updateProductQuantityShouldReturnOk() throws Exception {
        Product updated = createProduct(8L, "Monitor");
        updated.setQuantity(42);
        when(productService.updateProductQuantity(8L, 42)).thenReturn(updated);

        mockMvc.perform(patch("/api/products/8/quantity").param("quantity", "42"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(8))
            .andExpect(jsonPath("$.quantity").value(42));
    }

    private Product createProduct(Long id, String name) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription("Description");
        product.setPrice(new BigDecimal("25.50"));
        product.setQuantity(10);
        product.setCategory("Electronics");
        product.setIsAvailable(true);
        return product;
    }
}
