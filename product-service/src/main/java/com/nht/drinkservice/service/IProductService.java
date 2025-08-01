package com.nht.drinkservice.service;

import com.nht.drinkservice.dto.ProductDto;
import com.nht.drinkservice.dto.controller.request.RequestCreateProduct;
import com.nht.drinkservice.dto.controller.request.RequestUpdateProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    ProductDto createProduct(RequestCreateProduct createProductDto);
    ProductDto findById(Long id);
    Page<ProductDto> getProductsByPage(Pageable pageable);
    List<ProductDto> getAllProducts();
    ProductDto updateProduct(Long id, RequestUpdateProduct requestUpdateProduct);
    void deleteProduct(Long id);
    boolean isProductExists(Long id);
    boolean isProductExists(String name);
}
