package market.service.impl;

import lombok.RequiredArgsConstructor;
import market.dto.CreateProductDto;
import market.dto.ProductFilter;
import market.dto.ProductDto;
import market.entity.*;
import market.enums.OrderStatus;
import market.exception.ValidationException;
import market.mapper.ProductMapper;
import market.repository.*;
import market.service.ModelService;
import market.validator.CreateProductValidator;
import market.validator.EnteredAddCountValidator;
import market.validator.EnteredProductInfoValidator;
import market.validator.EnteredRemoveCountValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

import static market.util.ConstantContainer.*;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements market.service.ProductService {
    private final ProductRepository productRepository;
    private final CreateProductValidator createProductValidator;
    private final EnteredRemoveCountValidator enteredRemoveCountValidator;
    private final EnteredAddCountValidator enteredAddCountValidator;
    private final ColorRepository colorRepository;
    private final BrandRepository brandRepository;
    private final CountryRepository countryRepository;
    private final ModelRepository modelRepository;
    private final ProductMapper productMapper;
    private final BrandServiceImpl brandService;
    private final ModelService modelService;
    private final CountryServiceImpl countryService;
    private final ColorServiceImpl colorService;
    private final EnteredProductInfoValidator enteredProductInfoValidator;


    @Override
    public Page<ProductDto> getProducts(Pageable pageable) {
        return productRepository.findAllBy(pageable)
                .map(productMapper::productToProductDto);
    }

    @Override
    public List<ProductDto> getProductsByUserIdAndOrderStatus(Long id, OrderStatus orderStatus) {
        return productRepository.findByIdAndOrderStatus(id, orderStatus)
                .stream().map(productMapper::productToProductDto).toList();
    }

    @Override
    public List<ProductDto> getProductsByUserIdInFavourites(Long id) {
        return productRepository.findAllByInFavouritesAndUserId(id)
                .stream().map(productMapper::productToProductDto).toList();
    }

    @Override
    public Page<ProductDto> getProductsByPredicates(ProductFilter filter, Integer page) {
        enteredProductInfoValidator.putBrands(brandService.getAllBrands());
        enteredProductInfoValidator.putColors(colorService.getAllColors());
        enteredProductInfoValidator.putModels(modelService.getAllModels());
        enteredProductInfoValidator.putCountries(countryService.getAllCountries());
        var validationResult = enteredProductInfoValidator.isValid(filter);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        Specification<Product> specification = getSpecifications(filter);
        Sort sort = Sort.by(ID);
        if (CHEAP_FIRST.equals(filter.getPriceQuery())) {
            sort = Sort.by(COST);
        } else if (REACH_FIRST.equals(filter.getPriceQuery())) {
            sort = Sort.by(COST).descending();
        }
        var pageable = PageRequest.of(page, PAGE_SIZE, sort);
        return productRepository.findAll(specification, pageable)
                .map(productMapper::productToProductDto);
    }


    private Specification<Product> getSpecifications(ProductFilter filter) {
        Specification<Product> specification = Specification.where(null);
        if (filter.getModel() != null && !filter.getModel().isBlank()) {
            specification = specification
                    .and((root, query, cb) -> cb.equal(root.get(MODEL).get(MODEL), filter.getModel()));
        }
        if (filter.getCountry() != null && !filter.getCountry().isBlank()) {
            specification = specification
                    .and((root, query, cb) -> cb.equal(root.get(COUNTRY).get(COUNTRY), filter.getCountry()));
        }
        if (filter.getColor() != null && !filter.getColor().isBlank()) {
            specification = specification
                    .and((root, query, cb) -> cb.equal(root.get(COLOR).get(COLOR), filter.getColor()));
        }
        if (filter.getBrand() != null && !filter.getBrand().isBlank()) {
            specification = specification
                    .and((root, query, cb) -> cb.equal(root.get(BRAND).get(BRAND), filter.getBrand()));
        }
        if (filter.getCount() != null && !filter.getCount().isBlank()) {
            specification = specification
                    .and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get(STORAGE_COUNT), filter.getCount()));
        } else {
            specification = specification
                    .and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get(STORAGE_COUNT), 1));
        }
        if (filter.getMinPrice() != null && !filter.getMinPrice().isBlank()) {
            specification = specification
                    .and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get(COST),
                            Double.parseDouble(filter.getMinPrice())));
        }
        if (filter.getMaxPrice() != null && !filter.getMaxPrice().isBlank()) {
            specification = specification
                    .and((root, query, cb) -> cb.lessThanOrEqualTo(root.get(COST),
                            Double.parseDouble(filter.getMaxPrice())));
        }
        return specification;
    }

    @Override
    @Transactional
    public void addCount(Long id, String count) {
        var validationResult = enteredAddCountValidator.isValid(count);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var storageCount = product.getStorageCount();
        product.setStorageCount(storageCount + Integer.parseInt(count));
        productRepository.saveAndFlush(product);
    }

    @Override
    @Transactional
    public void removeCount(Long id, String count) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var storageCount = product.getStorageCount();
        enteredRemoveCountValidator.putProductCount(storageCount);
        var validationResult = enteredRemoveCountValidator.isValid(count);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        product.setStorageCount(storageCount - Integer.parseInt(count));
        productRepository.saveAndFlush(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        productRepository.delete(productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @Override
    public List<ProductDto> getProductsForAddingPromoCode(Long promoCodeId) {
        return productRepository.findProductsForAddingPromoCode(promoCodeId)
                .stream()
                .map(productMapper::productToProductDto).toList();
    }

    @Override
    public List<ProductDto> getProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::productToProductDto).toList();
    }

    @Override
    @Transactional
    public void addNewProduct(CreateProductDto createProductDto) {
        createProductValidator.putProducts(productRepository.findAll());
        var validationResult = createProductValidator.isValid(createProductDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var product = createProduct(createProductDto);
        productRepository.saveAndFlush(product);
    }

    private Product createProduct(CreateProductDto createProductDto) {
        var colorOptional = colorRepository.findByColorIgnoreCase(createProductDto.getColor());
        var color = colorOptional.orElseGet(() -> colorRepository.saveAndFlush(Color.builder()
                .color(createProductDto.getColor()).build()));
        var countryOptional = countryRepository.findByCountryIgnoreCase(createProductDto.getCountry());
        var country = countryOptional.orElseGet(() -> countryRepository.saveAndFlush(Country.builder()
                .country(createProductDto.getCountry()).build()));
        var brandOptional = brandRepository.findByBrandIgnoreCase(createProductDto.getBrand());
        var brand = brandOptional.orElseGet(() -> brandRepository.saveAndFlush(Brand.builder()
                .brand(createProductDto.getBrand()).build()));
        var modelOptional = modelRepository.findByModelIgnoreCase(createProductDto.getModel());
        var model = modelOptional.orElseGet(() -> modelRepository.saveAndFlush(Model.builder()
                .model(createProductDto.getModel()).build()));
        return Product.builder()
                .country(country)
                .brand(brand)
                .model(model)
                .color(color)
                .storageCount(createProductDto.getCount())
                .cost(BigDecimal.valueOf(createProductDto.getCost()))
                .build();
    }
}
