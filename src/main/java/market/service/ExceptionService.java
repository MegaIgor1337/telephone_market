package market.service;

import lombok.RequiredArgsConstructor;
import market.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExceptionService {
    private final ImageService imageService;
    public Optional<byte[]> findPicture() {
        return Optional.of("error-img.jpg")
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }
}
