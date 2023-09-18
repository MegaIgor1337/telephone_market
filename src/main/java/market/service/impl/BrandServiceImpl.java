package market.service.impl;


import lombok.RequiredArgsConstructor;
import market.dto.BrandDto;
import market.mapper.BrandMapper;
import market.repository.BrandRepository;
import market.service.BrandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public List<BrandDto> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(brandMapper::brandToBrandDto).toList();
    }
}
