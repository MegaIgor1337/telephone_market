package dao.filter;

public record ModelFilter(int limit,
                          int offset,
                          String model) {
}
