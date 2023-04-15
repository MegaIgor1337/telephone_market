package dao.filter;

import entity.brand.Brand;
import entity.color.Color;
import entity.country.Country;
import entity.model.Model;

import java.math.BigDecimal;

public record ProductFilter(int limit,
                            int offset,
                            Brand brand,
                            Model model,
                            Color color,
                            Country country,
                            Integer storageCount,
                            BigDecimal cost) {
}
