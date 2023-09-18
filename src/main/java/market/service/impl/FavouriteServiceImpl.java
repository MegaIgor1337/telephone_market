package market.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import market.dto.FavouriteDto;
import market.entity.Favourite;
import market.mapper.FavouriteMapper;
import market.repository.FavouriteRepository;
import market.repository.ProductRepository;
import market.repository.UserRepository;
import market.service.FavouriteService;
import market.util.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static market.util.ConstantContainer.PAGE_SIZE;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class FavouriteServiceImpl implements FavouriteService {
    private final FavouriteRepository favouriteRepository;
    private final FavouriteMapper favouriteMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    @Override
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
            log.info("User {}, added product {} to favourite", userId, productId);
        }
    }

    @Override
    public Page<FavouriteDto> getFavouritesByUserId(Long userId, Integer page) {
        return  PageUtil.createPageFromList(favouriteRepository.findAllByUserId(userId)
                .stream().map(favouriteMapper::favouriteToFavouriteDto).toList(),
                PageRequest.of(page, PAGE_SIZE));
    }

    @Override
    @Transactional
    public void deleteProductFromFavourite(Long favouriteId) {
        var favourite = favouriteRepository.findById(favouriteId);
        favourite.ifPresent(favouriteRepository::delete);
        log.info("Favourite {} deleted", favouriteId);
    }
}
