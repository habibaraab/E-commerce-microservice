package com.spring.product.DTO.Mapper;

import com.spring.product.DTO.ProductPurchaseRequest;
import com.spring.product.DTO.ProductPurchaseResponse;
import com.spring.product.DTO.ProductRequest;
import com.spring.product.DTO.ProductResponse;
import com.spring.product.Model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductPurchaseResponse toPurchaseResponse(ProductPurchaseRequest request);


    @Mapping(target = "category.id", source = "categoryId")
    Product toProduct(ProductRequest request);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "category.description", target = "categoryDescription")
    ProductResponse toProductResponse(Product product);

    ProductRequest toProductRequest(ProductResponse response);

    ProductPurchaseRequest toPurchaseRequest(ProductPurchaseResponse response);
}
