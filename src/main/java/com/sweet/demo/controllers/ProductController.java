package com.sweet.demo.controllers;

import com.sweet.demo.domain.Product;
import com.sweet.demo.domain.Response;
import com.sweet.demo.exceptions.InvalidTermException;
import com.sweet.demo.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller managing operations related to products.
 */
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Retrieves all products or filtered products based on a search term.
     *
     * @param term     Optional search term. It can be a price or a title.
     * @param pageable Pagination configuration for the results.
     * @return A page of products that match the search criteria.
     */
    @Operation(summary = "Retrieve all products or filtered products based on a search term",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved products"),
                    @ApiResponse(responseCode = "400", description = "Invalid term", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            })
    @GetMapping("/all")
    public ResponseEntity<Response<Page<Product>>> getAllProducts(
            @Parameter(description = "Optional search term. It can be a price or a title.")
            @RequestParam(required = false) String term,
            Pageable pageable) {
        try {
            Page<Product> products = productService.getAllProducts(term, pageable);
            Response<Page<Product>> response = new Response<>("Success", products);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidTermException e) {
            Response<Page<Product>> response = new Response<>("Error: " + e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            Response<Page<Product>> response = new Response<>("Error: " + e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Performs a health check for the controller.
     *
     * @return A ResponseEntity containing a message indicating that the controller is in good health.
     */
    @Operation(summary = "Perform a health check for the controller",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Controller is in good health"),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            })
    @GetMapping("/health-check")
    public ResponseEntity<Response<String>> health() {
        return new ResponseEntity<>(new Response<>("Hello world from Product", null), HttpStatus.OK);
    }
}
