package com.api.product_control.controllers;

import com.api.product_control.dtos.ProductDto;
import com.api.product_control.exceptions.ProductAlreadyExistsException;
import com.api.product_control.exceptions.ProductNotFoundException;
import com.api.product_control.models.ProductModel;
import com.api.product_control.services.ProductService;
import io.restassured.http.ContentType;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest
public class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @MockBean
    private ProductService productService;

    @BeforeEach
    public void setUp(){
        standaloneSetup(this.productController);
    }

    @Test
    public void shouldReturnCreated_WhenPostingProduct(){
        var productDto = new ProductDto("Smartphone Xiaomi Redmi Note 11",
                new BigDecimal("2000.00"));
        var productModel = new ProductModel();
        productModel.setIdProduct(UUID.randomUUID());

        when(productService.existsByName(productDto.name())).thenReturn(false);
        when(productService.save(any(ProductModel.class))).thenReturn(productModel);

        given()
                .contentType(ContentType.JSON)
                .body(productDto)
        .when()
                .post("/products")
        .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void shouldThrowProductAlreadyExistsException_WhenProductAlreadyExists(){
        var productDto = new ProductDto("Smartphone Xiaomi Redmi Note 11",
                new BigDecimal("2000.00"));
        when(productService.existsByName(productDto.name())).thenReturn(true);

        var exception = assertThrows(ServletException.class, () ->
                given()
                        .contentType(ContentType.JSON)
                        .body(productDto)
                .when()
                        .post("/products")
                .then()
                        .statusCode(HttpStatus.CONFLICT.value())
        );
        assertInstanceOf(ProductAlreadyExistsException.class, exception.getCause());
    }

    @Test
    public void shouldReturnSuccess_WhenGettingProduct(){
        var id = UUID.randomUUID();
        when(productService.findById(id)).thenReturn(Optional.of(new ProductModel()));

        given()
                .accept(ContentType.JSON)
        .when()
                .get("/products/{id}", id)
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldThrowProductNotFoundException_WhenGettingNonExistingProduct(){
        UUID id = UUID.randomUUID();
        when(productService.findById(id)).thenReturn(Optional.empty());

        var exception = assertThrows(ServletException.class, () ->
                given()
                        .accept(ContentType.JSON)
                .when()
                        .get("/products/{id}", id)
                .then()
                        .statusCode(HttpStatus.NOT_FOUND.value())
        );
        assertInstanceOf(ProductNotFoundException.class, exception.getCause());
    }

    @Test
    public void shouldReturnSuccess_WhenUpdatingProduct() {
        var productDto = new ProductDto("Smartphone Xiaomi Redmi Note 11", new BigDecimal("2000.00"));
        var productModel = new ProductModel();
        var id = UUID.randomUUID();
        productModel.setIdProduct(id);

        when(productService.findById(id)).thenReturn(Optional.of(productModel));
        when(productService.save(any(ProductModel.class))).thenReturn(productModel);

        given()
                .contentType(ContentType.JSON)
                .body(productDto)
        .when()
                .put("/products/{id}", id)
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldThrowProductNotFoundException_WhenUpdatingNonExistingProduct() {
        var productDto = new ProductDto("Smartphone Xiaomi Redmi Note 11", new BigDecimal("2000.00"));
        UUID id = UUID.randomUUID();

        when(productService.findById(id)).thenReturn(Optional.empty());

        var exception = assertThrows(ServletException.class, () ->
                given()
                        .contentType(ContentType.JSON)
                .body(productDto)
                        .when()
                        .put("/products/{id}", id)
                .then()
                        .statusCode(HttpStatus.NOT_FOUND.value())
        );
        assertInstanceOf(ProductNotFoundException.class, exception.getCause());
    }


    @Test
    public void shouldReturnSuccess_WhenDeletingProduct(){
        var id = UUID.randomUUID();
        when(productService.findById(id)).thenReturn(Optional.of(new ProductModel()));

        given()
                .accept(ContentType.JSON)
        .when()
                .delete("/products/{id}", id)
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldThrowProductNotFoundException_WhenDeletingNonExistingProduct(){
        UUID id = UUID.randomUUID();
        when(productService.findById(id)).thenReturn(Optional.empty());

        var exception = assertThrows(ServletException.class, () ->
                given()
                        .accept(ContentType.JSON)
                .when()
                        .delete("/products/{id}", id)
                .then()
                        .statusCode(HttpStatus.NOT_FOUND.value())
        );
        assertInstanceOf(ProductNotFoundException.class, exception.getCause());
    }
}
