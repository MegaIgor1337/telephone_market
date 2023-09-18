package market.service.impl;

import lombok.RequiredArgsConstructor;
import market.service.ExceptionService;
import market.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExceptionServiceImpl implements ExceptionService {
    private final ImageService imageService;

    @Override
    public Optional<byte[]> findPicture() {
        return Optional.of("error-img.jpg")
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }
}
