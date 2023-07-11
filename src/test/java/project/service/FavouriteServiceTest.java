package project.service;

import lombok.RequiredArgsConstructor;
import market.repository.FavouriteRepository;
import market.service.FavouriteService;
import org.junit.jupiter.api.Test;
import project.annotation.IT;
import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class FavouriteServiceTest {
    private final FavouriteService favouriteService;
    private final FavouriteRepository favouriteRepository;
    @Test
    void addFavourite() {
        favouriteService.addFavourite(2L, 5L);
        assertThat(favouriteRepository.findAll()).hasSize(3);
    }

    @Test
    void getFavouritesByUserId() {
        var result = favouriteService.getFavouritesByUserId(2L, 0);
        assertThat(result).hasSize(1);
    }

    @Test
    void deleteProductFromFavourite() {
        favouriteService.deleteProductFromFavourite(1L);
        assertThat(favouriteRepository.findAll()).hasSize(1);
    }
}
