package com.sweet.demo.controllers;

import com.sweet.demo.controllers.ProductController;
import com.sweet.demo.domain.Product;
import com.sweet.demo.domain.Response;
import com.sweet.demo.exceptions.InvalidTermException;
import com.sweet.demo.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        Page<Product> mockPage = new PageImpl<>(Collections.singletonList(new Product()));
        Mockito.when(productService.getAllProducts(any(), any())).thenReturn(mockPage);

        // Act
        ResponseEntity<Response<Page<Product>>> responseEntity = productController.getAllProducts("term", Pageable.unpaged());

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Success", responseEntity.getBody().getMessage());
        assertEquals(mockPage, responseEntity.getBody().getData());
    }

    @Test
    void testGetAllProductsInvalidTermException() {
        // Arrange
        Mockito.when(productService.getAllProducts(any(), any()))
                .thenThrow(new InvalidTermException("Invalid term"));

        // Act
        ResponseEntity<Response<Page<Product>>> responseEntity = productController.getAllProducts("!@#$", Pageable.unpaged());

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Error: Invalid term", responseEntity.getBody().getMessage());
        assertNull(responseEntity.getBody().getData());
    }

    @Test
    void testGetAllProductsException() {
        // Arrange
        Mockito.when(productService.getAllProducts(any(), any()))
                .thenThrow(new RuntimeException("Internal server error"));

        // Act
        ResponseEntity<Response<Page<Product>>> responseEntity = productController.getAllProducts("term", Pageable.unpaged());

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Error: Internal server error", responseEntity.getBody().getMessage());
        assertNull(responseEntity.getBody().getData());
    }

}
