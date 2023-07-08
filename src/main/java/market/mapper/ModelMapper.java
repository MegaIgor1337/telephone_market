package market.mapper;

import market.dto.ModelDto;
import market.entity.Model;
import org.mapstruct.Mapper;

import static market.util.ConstantContainer.SPRING;

@Mapper(componentModel = SPRING)
public interface ModelMapper {
    Model modelDtoToModel(ModelDto modelDto);
    ModelDto modelToModelDto(Model model);
}
