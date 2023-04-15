package dao.filter;

public record CountryFilter(int limit,
                            int offset,
                            String country) {
}
