package service;

public class ProductService {
    private final static ProductService INSTANCE = new ProductService();


    public static ProductService getInstance() {
        return INSTANCE;
    }
}
