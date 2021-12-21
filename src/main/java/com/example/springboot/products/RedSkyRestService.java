package com.example.springboot.products;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class RedSkyRestService {

    private final RestOperations restTemplate;

    public RedSkyRestService(RestOperations restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RedSkyWrapper<RedSkyProductDTO> getRedSkyProductInformation(Long id) {
        UriComponentsBuilder builder = initRequestBuilder()
                .queryParam("tcin", String.valueOf(id));

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        String url = builder.build().toString();
        HttpMethod method = HttpMethod.GET;

        try {
            ResponseEntity<RedSkyWrapper<RedSkyProductDTO>> response = restTemplate.exchange(url, method, entity, new ParameterizedTypeReference<>(){});
            if(response != null && response.getStatusCodeValue() >= 200 && response.getStatusCodeValue() < 300) {
                return response.getBody();
            }
            return null;
        }
        catch(Exception e) {
            return null;
        }
    }

    private static UriComponentsBuilder initRequestBuilder() {
        return UriComponentsBuilder.fromHttpUrl("https://redsky-uat.perf.target.com/redsky_aggregations/v1/redsky/case_study_v1")
                .queryParam("key", "3yUxt7WltYG7MFKPp7uyELi1K40ad2ys");
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RedSkyWrapper<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RedSkyProductDTO {
        private RedSkyProductDetails product;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RedSkyProductDetails {
        private String tcin;
        private RedSkyItemDetails item;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RedSkyItemDetails {
        private RedSkyProductDescription product_description;
        private RedSkyEnrichmentDetails enrichment;
        private RedSkyProductClassification product_classification;
        private RedSkyBrandDetails primary_brand;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RedSkyProductDescription {
        private String title;
        private String downstream_description;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RedSkyEnrichmentDetails {
        private RedSkyEnrichmentImageDetails images;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RedSkyEnrichmentImageDetails {
        private String primary_image_url;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RedSkyProductClassification {
        private String product_type_name;
        private String merchandise_type_name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RedSkyBrandDetails {
        private String name;
    }

}
