package dao.filter;

public record AddressFilter(int limit,
                            int offset,
                            String country,
                            String city,
                            String street,
                            String house,
                            String flat) {
}
