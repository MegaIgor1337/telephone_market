package market.service;

import lombok.RequiredArgsConstructor;
import market.dto.CreateUserProductDto;
import market.dto.ProductDto;
import market.entity.Product;
import market.enums.OrderStatus;
import market.exception.ValidationException;
import market.mapper.ListToPageMapper;
import market.mapper.ProductMapper;
import market.repository.ProductRepository;
import market.validator.EnteredProductInfoValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static market.util.StringContainer.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ListToPageMapper listToPageMapper;
    private final ProductMapper productMapper;
    private final BrandService brandService;
    private final ModelService modelService;
    private final CountryService countryService;
    private final ColorService colorService;
    private final EnteredProductInfoValidator enteredProductInfoValidator;

    public Page<ProductDto> getProducts(Pageable pageable) {
        return productRepository.findAllBy(pageable)
                .map(productMapper::productToProductDto);
    }

    public List<ProductDto> getProductsByUserIdAndOrderStatus(Long id, OrderStatus orderStatus) {
        return productRepository.findByIdAndOrderStatus(id, orderStatus)
                .stream().map(productMapper::productToProductDto).toList();
    }

    public List<ProductDto> getProductsByUserIdInFavourites(Long id) {
        return productRepository.findAllByInFavouritesAndUserId(id)
                .stream().map(productMapper::productToProductDto).toList();
    }

    public Page<ProductDto> getProductsByPredicates(CreateUserProductDto createUserProductDto, PageRequest pageRequest) {
        enteredProductInfoValidator.putBrands(brandService.getAllBrands());
        enteredProductInfoValidator.putColors(colorService.getAllColors());
        enteredProductInfoValidator.putModels(modelService.getAllModels());
        enteredProductInfoValidator.putCountries(countryService.getAllCountries());
        var validationResult = enteredProductInfoValidator.isValid(createUserProductDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        Specification<Product> specification = getSpecifications(createUserProductDto);
        return listToPageMapper.convertListToPage(productRepository.findAll(specification)
                .stream().map(productMapper::productToProductDto).toList(), pageRequest);
    }


    private Specification<Product> getSpecifications(CreateUserProductDto createUserProductDto) {
        Specification<Product> specification = Specification.where(null);
        if (createUserProductDto.getModel() != null && !createUserProductDto.getModel().equals(EMPTY_PARAM)) {
            specification = specification
                    .and((root, query, cb) -> cb.equal(root.get(MODEL).get(MODEL), createUserProductDto.getModel()));
        }
        if (createUserProductDto.getCountry() != null && !createUserProductDto.getCountry().equals(EMPTY_PARAM)) {
            specification = specification
                    .and((root, query, cb) -> cb.equal(root.get(COUNTRY).get(COUNTRY), createUserProductDto.getCountry()));
        }
        if (createUserProductDto.getColor() != null && !createUserProductDto.getColor().equals(EMPTY_PARAM)) {
            specification = specification
                    .and((root, query, cb) -> cb.equal(root.get(COLOR).get(COLOR), createUserProductDto.getColor()));
        }
        if (createUserProductDto.getBrand() != null && !createUserProductDto.getBrand().equals(EMPTY_PARAM)) {
            specification = specification
                    .and((root, query, cb) -> cb.equal(root.get(BRAND).get(BRAND), createUserProductDto.getBrand()));
        }
        if (createUserProductDto.getCount() != null && !createUserProductDto.getCount().equals(EMPTY_PARAM)) {
            specification = specification
                    .and((root, query, cb) -> cb.equal(root.get(COUNT), createUserProductDto.getCount()));
        }
        if (createUserProductDto.getMinPrice() != null && !createUserProductDto.getMinPrice().equals(EMPTY_PARAM)) {
            specification = specification
                    .and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get(COST),
                            Double.parseDouble(createUserProductDto.getMinPrice())));
        }
        if (createUserProductDto.getMaxPrice() != null && !createUserProductDto.getMaxPrice().equals(EMPTY_PARAM)) {
            specification = specification
                    .and((root, query, cb) -> cb.lessThanOrEqualTo(root.get(COST),
                            Double.parseDouble(createUserProductDto.getMaxPrice())));
        }
        return specification;
    }
}
