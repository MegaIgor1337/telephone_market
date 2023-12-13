package market.service.mapper;

import market.service.dto.ColorDto;
import market.model.entity.Color;
import org.mapstruct.Mapper;

import static market.service.util.ConstantContainer.SPRING;

@Mapper(componentModel = SPRING)
public interface ColorMapper {
    Color colorDtoToColor(ColorDto colorDto);
    ColorDto colorToColorDto(Color color);
}
