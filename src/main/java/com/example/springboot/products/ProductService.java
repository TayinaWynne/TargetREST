package com.example.springboot.products;

import static com.example.springboot.products.RedSkyRestService.*;
import static com.example.springboot.products.ProductController.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final RedSkyRestService redSkyRestService;
    private final PricingService pricingService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(RedSkyRestService redSkyRestService, PricingService pricingService, ProductMapper productMapper) {
        this.redSkyRestService = redSkyRestService;
        this.pricingService = pricingService;
        this.productMapper = productMapper;
    }

    public ProductController.ProductDTO getProductInformation(Long id) {

        RedSkyWrapper<RedSkyProductDTO> redSkyWrapper = redSkyRestService.getRedSkyProductInformation(id);
        ProductController.PriceDTO priceDTO = pricingService.getPriceForItem(id);

        if(redSkyWrapper == null || priceDTO == null) {
            return null;
        }

        return productMapper.fromEntity(redSkyWrapper, priceDTO);
    }

    public ProductDTO updatePricingInformation(Long id, ProductController.ProductDTO productDTO) {
        //Note: there doesn't appear to be any clear way to push updates to the RedSky product platform
        PriceDTO priceDTO = pricingService.updatePriceForItem(id, productDTO.getCurrent_price().getValue().multiply(PricingService.ONE_HUNDRED).longValue());
        if(priceDTO != null) {
            return productDTO;
        }
        return null;
    }

}
