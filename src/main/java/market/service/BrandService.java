package market.service;


import lombok.RequiredArgsConstructor;
import market.dto.BrandDto;
import market.mapper.BrandMapper;
import market.repository.BrandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    public List<BrandDto> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(brandMapper::brandToBrandDto).toList();
    }
}
