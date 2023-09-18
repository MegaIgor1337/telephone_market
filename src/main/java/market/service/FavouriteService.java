package market.service;

import market.dto.FavouriteDto;
import org.springframework.data.domain.Page;

public interface FavouriteService {
    void addFavourite(Long userId, Long productId);
    Page<FavouriteDto> getFavouritesByUserId(Long userId, Integer page);
    void deleteProductFromFavourite(Long favouriteId);
}
