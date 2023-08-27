package com.example.springproject.Repository.Pagination;

import com.example.springproject.models.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductPagitionandSorting extends PagingAndSortingRepository<Product, Long> {

}
