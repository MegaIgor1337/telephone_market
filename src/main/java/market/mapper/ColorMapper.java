package market.mapper;

import market.dto.ColorDto;
import market.entity.Color;
import org.mapstruct.Mapper;

import static market.util.StringContainer.SPRING;

@Mapper(componentModel = SPRING)
public interface ColorMapper {
    Color colorDtoToColor(ColorDto colorDto);
    ColorDto colorToColorDto(Color color);
}
