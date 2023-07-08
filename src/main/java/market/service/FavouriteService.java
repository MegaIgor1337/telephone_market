package market.service;


import lombok.RequiredArgsConstructor;
import market.dto.FavouriteDto;
import market.entity.Favourite;
import market.entity.Product;
import market.mapper.FavouriteMapper;
import market.repository.FavouriteRepository;
import market.repository.ProductRepository;
import market.repository.UserRepository;
import market.util.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static market.util.ConstantContainer.PAGE_SIZE;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FavouriteService {
    private final FavouriteRepository favouriteRepository;
    private final FavouriteMapper favouriteMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addFavourite(Long userId, Long productId) {
        var user = userRepository.findById(userId);
        var product = productRepository.findById(productId);
        if (user.isPresent() && product.isPresent()) {
            var favourite = Favourite.builder()
                    .user(user.get())
                    .product(product.get())
                    .date(LocalDateTime.now())
                    .build();
            favouriteRepository.save(favourite);
        }
    }

    public Page<FavouriteDto> getFavouritesByUserId(Long userId, Integer page) {
        return  PageUtil.createPageFromList(favouriteRepository.findAllByUserId(userId)
                .stream().map(favouriteMapper::favouriteToFavouriteDto).toList(),
                PageRequest.of(page, PAGE_SIZE));
    }

    @Transactional
    public void deleteProductFromFavourite(Long favouriteId) {
        var favourite = favouriteRepository.findById(favouriteId);
        favourite.ifPresent(favouriteRepository::delete);
    }
}
