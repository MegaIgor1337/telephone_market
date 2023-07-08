package market.mapper;

import market.dto.FavouriteDto;
import market.entity.Favourite;
import org.mapstruct.Mapper;

import static market.util.ConstantContainer.SPRING;

@Mapper(componentModel = SPRING, uses = {UserMapper.class, ProductMapper.class})
public interface FavouriteMapper {
    Favourite favouriteDtoToFavourite(FavouriteDto favouriteDto);
    FavouriteDto favouriteToFavouriteDto(Favourite favourite);
}
