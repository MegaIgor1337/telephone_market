package dao;

import entity.promoCodeProduct.PromoCodeProduct;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PromoCodeProductDao implements Dao<Long, PromoCodeProduct> {
    @Getter
    private final static PromoCodeProductDao INSTANCE = new PromoCodeProductDao();
    @Override
    public Class<PromoCodeProduct> getEntityClass() {
        return PromoCodeProduct.class;
    }
}
