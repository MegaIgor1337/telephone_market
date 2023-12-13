package market.service.impl;


import lombok.RequiredArgsConstructor;
import market.service.dto.BrandDto;
import market.service.mapper.BrandMapper;
import market.model.repository.BrandRepository;
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
