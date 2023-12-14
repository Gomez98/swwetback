package com.sweet.demo.repositories;

import com.sweet.demo.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Product entities.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Retrieves a page of products that contain the specified title, ignoring case.
     *
     * @param title    The title to search for.
     * @param pageable Pagination configuration for the results.
     * @return A page of products that match the title.
     */
    Page<Product> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    /**
     * Retrieves a page of products that match the specified price.
     *
     * @param price    The price to search for.
     * @param pageable Pagination configuration for the results.
     * @return A page of products that match the price.
     */
    Page<Product> findByPrice(Double price, Pageable pageable);
}
