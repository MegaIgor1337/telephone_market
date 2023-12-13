package market.service.mapper;

import market.service.dto.ModelDto;
import market.model.entity.Model;
import org.mapstruct.Mapper;

import static market.service.util.ConstantContainer.SPRING;

@Mapper(componentModel = SPRING)
public interface ModelMapper {
    Model modelDtoToModel(ModelDto modelDto);
    ModelDto modelToModelDto(Model model);
}
