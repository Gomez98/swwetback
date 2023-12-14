package com.sweet.demo.services;

import com.sweet.demo.domain.Product;
import com.sweet.demo.exceptions.InvalidTermException;
import com.sweet.demo.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service class providing business logic for managing products.
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Retrieves all products or filtered products based on a search term.
     *
     * @param term     Optional search term. It can be a price or a title.
     * @param pageable Pagination configuration for the results.
     * @return A page of products that match the search criteria.
     */
    public Page<Product> getAllProducts(String term, Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable cannot be null");
        }

        if (pageable.getPageNumber() < 0) {
            throw new IllegalArgumentException("Page number must be greater than or equal to 0");
        }

        if (pageable.getPageSize() <= 0) {
            throw new IllegalArgumentException("Page size must be greater than 0");
        }

        if (term != null && !term.trim().isEmpty()) {
            if (term.matches("^[a-zA-Z0-9.\\s]+$")) {
                try {
                    Double price = Double.parseDouble(term);
                    return getByPrice(price, pageable);
                } catch (NumberFormatException e) {
                    return getByTitle(term, pageable);
                }
            } else {
                throw new InvalidTermException("The term is invalid");
            }
        } else {
            return getAll(pageable);
        }

    }


    /**
     * Retrieves all products with pagination.
     *
     * @param pageable Pagination configuration for the results.
     * @return A page of all products.
     */
    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    /**
     * Retrieves products by title with pagination.
     *
     * @param title    The title to search for.
     * @param pageable Pagination configuration for the results.
     * @return A page of products that match the title.
     */
    public Page<Product> getByTitle(String title, Pageable pageable) {
        return productRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    /**
     * Retrieves products by price with pagination.
     *
     * @param price    The price to search for.
     * @param pageable Pagination configuration for the results.
     * @return A page of products that match the price.
     */
    public Page<Product> getByPrice(Double price, Pageable pageable) {
        return productRepository.findByPrice(price, pageable);
    }
}
