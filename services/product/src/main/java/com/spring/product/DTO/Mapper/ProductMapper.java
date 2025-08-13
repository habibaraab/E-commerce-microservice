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

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "description", source = "product.description")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "quantity", source = "quantity")
    ProductPurchaseResponse toProductPurchaseResponse(Product product, double quantity);


    ProductRequest toProductRequest(ProductResponse response);

    ProductPurchaseRequest toPurchaseRequest(ProductPurchaseResponse response);
}
