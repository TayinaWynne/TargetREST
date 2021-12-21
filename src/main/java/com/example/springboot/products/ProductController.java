package com.example.springboot.products;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(value="", produces=MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProductInformation(@PathVariable("id") Long id) {
        ProductDTO productDTO = productService.getProductInformation(id);
        if(productDTO != null) {
            return ResponseEntity.ok(productDTO);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDTO> updateProductInformation(@PathVariable("id") Long id, @RequestBody ProductDTO updatedDTO) {
        ProductDTO productDTO = productService.updatePricingInformation(id, updatedDTO);
        if(productDTO != null) {
            return ResponseEntity.ok(productDTO);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductDTO {
        private long id;
        private String name;
        private PriceDTO current_price;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PriceDTO {
        private BigDecimal value;
        private String currency_code;
    }

}
