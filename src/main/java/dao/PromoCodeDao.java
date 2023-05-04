package dao;

import entity.promoCode.PromoCode;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PromoCodeDao implements Dao<Long, PromoCode> {
    @Getter
    private static final PromoCodeDao INSTANCE = new PromoCodeDao();

    @Override
    public Class<PromoCode> getEntityClass() {
        return PromoCode.class;
    }
}
