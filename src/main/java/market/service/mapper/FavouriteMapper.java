package market.service.mapper;

import market.service.dto.FavouriteDto;
import market.model.entity.Favourite;
import org.mapstruct.Mapper;

import static market.service.util.ConstantContainer.SPRING;

@Mapper(componentModel = SPRING, uses = {UserMapper.class, ProductMapper.class})
public interface FavouriteMapper {
    Favourite favouriteDtoToFavourite(FavouriteDto favouriteDto);
    FavouriteDto favouriteToFavouriteDto(Favourite favourite);
}
