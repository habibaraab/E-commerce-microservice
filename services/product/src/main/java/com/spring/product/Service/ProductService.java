package com.spring.product.Service;

import com.spring.product.DTO.Mapper.ProductMapper;
import com.spring.product.DTO.ProductRequest;
import com.spring.product.DTO.ProductResponse;
import com.spring.product.Model.Category;
import com.spring.product.Model.Product;
import com.spring.product.Repository.CategoryRepository;
import com.spring.product.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final ProductMapper productMapper;


    public ProductResponse addProduct(ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Product product=productRepository.save(productMapper.toProduct(productRequest));
        product.setCategory(category);
        return productMapper.toProductResponse(product);

    }
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }
    public ProductResponse getProductById(int id) {
         return productMapper.toProductResponse(productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found with id: "+id)));
    }

    public ProductResponse updateProduct(int id, ProductRequest request) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        existingProduct.setName(request.name());
        existingProduct.setDescription(request.description());
        existingProduct.setAvailableQuantity(request.availableQuantity());
        existingProduct.setPrice(request.price());

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existingProduct.setCategory(category);
        Product updatedProduct = productRepository.save(existingProduct);

        return productMapper.toProductResponse(updatedProduct);
    }


    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
