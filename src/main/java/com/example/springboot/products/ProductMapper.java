package com.example.springboot.products;

import static com.example.springboot.products.ProductController.*;
import static com.example.springboot.products.RedSkyRestService.*;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO fromEntity(RedSkyWrapper<RedSkyProductDTO> productEntity, PriceDTO pricingEntity) {
        return ProductDTO.builder()
                .id(Long.parseLong(productEntity.getData().getProduct().getTcin()))
                .name(productEntity.getData().getProduct().getItem().getProduct_description().getTitle())
                .current_price(pricingEntity)
                .build();
    }

}
