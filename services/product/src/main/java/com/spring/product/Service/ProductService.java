package com.spring.product.Service;

import com.spring.product.DTO.Mapper.ProductMapper;
import com.spring.product.DTO.ProductPurchaseRequest;
import com.spring.product.DTO.ProductPurchaseResponse;
import com.spring.product.DTO.ProductRequest;
import com.spring.product.DTO.ProductResponse;
import com.spring.product.Exception.ProductPurchaseException;
import com.spring.product.Model.Category;
import com.spring.product.Model.Product;
import com.spring.product.Repository.CategoryRepository;
import com.spring.product.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
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


    @Transactional(rollbackFor = ProductPurchaseException.class)
    public List<ProductPurchaseResponse> purchaseProducts(
            List<ProductPurchaseRequest> request
    ) {
        var productIds = request
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        var storedProducts = productRepository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exist");
        }
        var sortedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = sortedRequest.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID:: " + productRequest.productId());
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(product);
            purchasedProducts.add(productMapper.toProductPurchaseResponse(product, productRequest.quantity()));
        }
        return purchasedProducts;
    }
}
