package service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductService {
    @Getter
    private final static ProductService INSTANCE = new ProductService();



}
