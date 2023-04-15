package dao.filter;

public record BrandFilter(int limit,
                          int offset,
                          String brand) {
}
