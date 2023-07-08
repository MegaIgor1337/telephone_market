package market.mapper;

import market.dto.BrandDto;
import market.entity.Brand;
import org.mapstruct.Mapper;

import static market.util.ConstantContainer.SPRING;

@Mapper(componentModel = SPRING)
public interface BrandMapper {
    Brand brandDtoToBran(BrandDto brandDto);
    BrandDto brandToBrandDto(Brand brand);
}
