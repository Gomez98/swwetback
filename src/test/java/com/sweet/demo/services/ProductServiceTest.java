package com.sweet.demo.services;

import com.sweet.demo.domain.Product;
import com.sweet.demo.exceptions.InvalidTermException;
import com.sweet.demo.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> mockPage = new PageImpl<>(Collections.singletonList(new Product()));
        Mockito.when(productRepository.findAll(pageable)).thenReturn(mockPage);

        // Act
        Page<Product> result = productService.getAllProducts(null, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(mockPage, result);
    }

    @Test
    void testGetAllProductsByTitle() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        String title = "Test";
        Page<Product> mockPage = new PageImpl<>(Collections.singletonList(new Product()));
        Mockito.when(productRepository.findByTitleContainingIgnoreCase(eq(title), any())).thenReturn(mockPage);

        // Act
        Page<Product> result = productService.getAllProducts(title, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(mockPage, result);
    }

    @Test
    void testGetAllProductsByPrice() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Double price = 10.0;
        Page<Product> mockPage = new PageImpl<>(Collections.singletonList(new Product()));
        Mockito.when(productRepository.findByPrice(eq(price), any())).thenReturn(mockPage);

        // Act
        Page<Product> result = productService.getAllProducts(price.toString(), pageable);

        // Assert
        assertNotNull(result);
        assertEquals(mockPage, result);
    }

    @Test
    void testGetAllProductsInvalidTermException() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        // Act & Assert
        assertThrows(InvalidTermException.class, () -> productService.getAllProducts("!@#$", pageable));
    }

    @Test
    void testGetAllProductsIllegalArgumentException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productService.getAllProducts("term", null));
        assertThrows(IllegalArgumentException.class, () -> productService.getAllProducts("term", PageRequest.of(-1, 10)));
        assertThrows(IllegalArgumentException.class, () -> productService.getAllProducts("term", PageRequest.of(0, 0)));
    }


}
