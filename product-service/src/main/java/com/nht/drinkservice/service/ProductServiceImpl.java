package com.nht.drinkservice.service;

import com.nht.drinkservice.dto.ProductDto;
import com.nht.drinkservice.dto.controller.request.RequestCreateProduct;
import com.nht.drinkservice.dto.controller.request.RequestUpdateProduct;
import com.nht.drinkservice.entity.Category;
import com.nht.drinkservice.entity.Ingredient;
import com.nht.drinkservice.entity.Inventory;
import com.nht.drinkservice.entity.Product;
import com.nht.drinkservice.mapper.ProductMapper;
import com.nht.drinkservice.repository.CategoryRepository;
import com.nht.drinkservice.repository.IngredientRepository;
import com.nht.drinkservice.repository.ProductRepository;
import exceptions.AlreadyExistsException;
import exceptions.InvalidPageRequest;
import exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final IngredientRepository ingredientRepository;

    @Override
    @Transactional
    public ProductDto createProduct(RequestCreateProduct requestCreateProduct) {
        final Product productToSave = productMapper.toEntity(requestCreateProduct);
        // check if product name already exists
        if (isProductExists(requestCreateProduct.name())) {
            throw new AlreadyExistsException("Product with the given name already exists: " + requestCreateProduct.name());
        }

        // check if category exists
        final Category category = categoryRepository.findById(requestCreateProduct.categoryId())
                .orElseThrow(() -> new NotFoundException("Category not found: " + requestCreateProduct.categoryId()));

        // check if ingredients exist
        final Set<Ingredient> ingredients = requestCreateProduct.ingredients().stream()
                .distinct()
                .map(ingredientId -> ingredientRepository.findById(ingredientId)
                        .orElseThrow(() -> new NotFoundException("Ingredient not found: " + ingredientId)))
                .collect(Collectors.toSet());

        // new inventory for a new product.
        final Inventory inventory = new Inventory();
        inventory.setProduct(productToSave);
        inventory.setQuantityOnHand(requestCreateProduct.quantity());

        // set category and ingredients relations before saving the product
        productToSave.setCategory(category);
        productToSave.setIngredients(ingredients);
        productToSave.setInventory(inventory);

        // save the product
        return productMapper.toDto(productRepository.save(productToSave));
    }

    @Override
    public ProductDto findById(Long id) {
        // check if product exists
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found: " + id));
        return productMapper.toDto(product);
    }

    @Override
    public Page<ProductDto> getProductsByPage(Pageable pageable) {
        // check if page is valid
        if (pageable.getPageNumber() < 0 || pageable.getPageSize() <= 0) {
            throw new InvalidPageRequest("Invalid page number or size");
        }
        return productRepository.findAll(pageable).map(productMapper::toDto);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productMapper.toDtoList(productRepository.findAll());
    }

    @Override
    @Transactional
    public ProductDto updateProduct(Long id, RequestUpdateProduct requestUpdateProduct) {
        // check if product exists
        final Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found: " + id));

        // check if product name already exists
        if (isProductExists(requestUpdateProduct.name())) {
            throw new AlreadyExistsException("Product with the given name already exists: " + requestUpdateProduct.name());
        }

        // update product fields except category, ingredients and inventory.
        productMapper.updateEntity(existingProduct, requestUpdateProduct);

        // check if category exists
        final Category category = categoryRepository.findById(requestUpdateProduct.categoryId())
                .orElseThrow(() -> new NotFoundException("Category not found: " + requestUpdateProduct.categoryId()));

        // check if ingredients exist
        final Set<Ingredient> ingredients = requestUpdateProduct.ingredients().stream()
                .distinct()
                .map(ingredientId -> ingredientRepository.findById(ingredientId)
                        .orElseThrow(() -> new NotFoundException("Ingredient not found: " + ingredientId)))
                .collect(Collectors.toSet());

        // update product fields
        existingProduct.setCategory(category);
        existingProduct.setIngredients(ingredients);

        return productMapper.toDto(productRepository.save(existingProduct));
    }

    @Override
    public void deleteProduct(Long id) {
        // check if product exists
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found: " + id));
        productRepository.delete(product);
    }

    @Override
    public boolean isProductExists(Long id) {
        return productRepository.existsById(id);
    }

    @Override
    public boolean isProductExists(String name) {
        return productRepository.existsByName(name);
    }

}
