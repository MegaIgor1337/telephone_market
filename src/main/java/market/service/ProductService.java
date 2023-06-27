package market.service;

import lombok.RequiredArgsConstructor;
import market.dto.ProductFilter;
import market.dto.ProductDto;
import market.entity.Product;
import market.enums.OrderStatus;
import market.exception.ValidationException;
import market.mapper.ProductMapper;
import market.repository.ProductRepository;
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

import java.util.List;

import static market.util.StringContainer.*;



@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final Integer pageSize = 2;
    private final ProductRepository productRepository;
    private final EnteredRemoveCountValidator enteredRemoveCountValidator;
    private final EnteredAddCountValidator enteredAddCountValidator;
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
        var pageable = PageRequest.of(page, pageSize, sort);
        return productRepository.findAll(specification, pageable)
                .map(productMapper::productToProductDto);
    }


    public Specification<Product> getSpecifications(ProductFilter filter) {
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

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.delete(productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    public List<ProductDto> getProductsForAddingPromoCode(Long promoCodeId) {
        return productRepository.findProductsForAddingPromoCode(promoCodeId)
                .stream().map(productMapper::productToProductDto).toList();
    }
}
