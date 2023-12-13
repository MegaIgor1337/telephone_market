package market.service.mapper;

import market.service.dto.BrandDto;
import market.model.entity.Brand;
import org.mapstruct.Mapper;

import static market.service.util.ConstantContainer.SPRING;

@Mapper(componentModel = SPRING)
public interface BrandMapper {
    Brand brandDtoToBran(BrandDto brandDto);
    BrandDto brandToBrandDto(Brand brand);
}
