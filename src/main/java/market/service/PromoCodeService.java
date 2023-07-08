package market.service;

import lombok.RequiredArgsConstructor;
import market.dto.CreatePromoCodeDto;
import market.dto.PromoCodeDto;
import market.dto.PromoCodeDtoWithPage;
import market.dto.PromoCodeFilter;
import market.entity.PromoCode;
import market.enums.PromoCodeStatus;
import market.exception.ValidationException;
import market.mapper.CreatePromoCodeMapper;
import market.mapper.ProductMapper;
import market.mapper.PromoCodeDtoWithPageMapper;
import market.mapper.PromoCodeMapper;
import market.repository.ProductRepository;
import market.repository.PromoCodeProductRepository;
import market.repository.PromoCodeRepository;
import market.validator.AddedPromoCodeValidator;
import market.validator.ChangeDiscountPromoCodeValidator;
import market.validator.ChangeNamePromoCodeValidator;
import market.validator.EnteredPromoCodeValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static market.util.ConstantContainer.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PromoCodeService {
    private final CreatePromoCodeMapper createPromoCodeMapper;
    private final AddedPromoCodeValidator addedPromoCodeValidator;
    private final ChangeNamePromoCodeValidator changeNamePromoCodeValidator;
    private final PromoCodeMapper promoCodeMapper;
    private final ProductRepository productRepository;
    private final ChangeDiscountPromoCodeValidator changeDiscountPromoCodeValidator;
    private final PromoCodeRepository promoCodeRepository;
    private final ProductMapper productMapper;
    private final PromoCodeProductRepository promoCodeProductRepository;
    private final PromoCodeDtoWithPageMapper promoCodeDtoWithPageMapper;
    private final EnteredPromoCodeValidator enteredPromoCodeValidator;

    public Page<PromoCodeDto> getPromoCodes(PromoCodeFilter promoCodeFilter, Integer page) {
        enteredPromoCodeValidator.putPromoCodes(promoCodeRepository.findAll());
        var validationResult = enteredPromoCodeValidator.isValid(promoCodeFilter);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        Specification<PromoCode> specification = getSpecifications(promoCodeFilter);
        return promoCodeRepository.findAll(specification, PageRequest.of(page, PAGE_SIZE))
                .map(promoCodeMapper::promoCodeTopromoCodeDto);
    }

    private Specification<PromoCode> getSpecifications(PromoCodeFilter promoCodeFilter) {
        Specification<PromoCode> specification = Specification.where(null);
        if (promoCodeFilter.getStatus() != null) {
            specification = specification
                    .and((root, query, cb) -> cb.equal(root.get(STATUS),
                            promoCodeFilter.getStatus()));
        }
        if (promoCodeFilter.getDiscount() != null && !promoCodeFilter.getDiscount().isBlank()) {
            specification = specification
                    .and(((root, query, cb) -> cb.equal(root.get(DISCOUNT), promoCodeFilter.getDiscount())));
        }
        if (promoCodeFilter.getName() != null && !promoCodeFilter.getName().isBlank()) {
            specification = specification
                    .and(((root, query, cb) -> cb.like(root.get(NAME), promoCodeFilter.getName())));
        }
        return specification;
    }

    public PromoCodeDtoWithPage getPromoCodeWithPage(Long id, Integer page) {
        var promoCode = promoCodeRepository.findById(id);
        return promoCodeDtoWithPageMapper.promoCodeDtoToPromoCodeDtoWithPage(promoCodeMapper
                .promoCodeTopromoCodeDto(promoCode
                        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND))), page, PAGE_SIZE);
    }

    @Transactional
    public void deleteProductFromPromoCode(String productId, Long promoCodeId) {
        promoCodeProductRepository
                .deleteAllByProductIdAndPromoCodeId(Long.valueOf(productId), promoCodeId);
    }

    @Transactional
    public void deletePromoCode(Long id) {
        promoCodeRepository.deleteAllById(id);
    }

    @Transactional
    public void changeName(Long id, String newName) {
        changeNamePromoCodeValidator
                .putPromoCodeAndPromoCodeId(promoCodeRepository.findAll(), id);
        var validationResult = changeNamePromoCodeValidator.isValid(newName);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var promoCode = promoCodeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        promoCode.setName(newName);
        promoCodeRepository.saveAndFlush(promoCode);
    }

    @Transactional
    public void changeDiscount(Long id, String newDiscount) {
        var validationResult = changeDiscountPromoCodeValidator
                .isValid(Double.valueOf(newDiscount));
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var promoCode = promoCodeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        promoCode.setDiscount(Double.valueOf(newDiscount));
        promoCodeRepository.saveAndFlush(promoCode);
    }

    @Transactional
    public void changeStatus(Long id, String newStatus) {
        var promoCode = promoCodeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        promoCode.setStatus(PromoCodeStatus.valueOf(newStatus));
    }

    @Transactional
    public void addPromoCode(CreatePromoCodeDto createPromoCodeDto) {
        addedPromoCodeValidator.putPromoCodes(promoCodeRepository.findAll());
        var validationResult = addedPromoCodeValidator.isValid(createPromoCodeDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var promoCode = createPromoCodeMapper.createPromoCodeDtoToPromoCode(createPromoCodeDto);
        promoCode = promoCodeRepository.saveAndFlush(promoCode);
        var promoCodeProducts = createPromoCodeMapper
                .createPromoCodeProduct(promoCode, createPromoCodeDto.getProductsId()
                        .stream().map(productRepository::findById)
                        .map(p -> p.orElseThrow(() -> new ResponseStatusException(NOT_FOUND))).toList());
        promoCodeProducts.stream().forEach(promoCodeProductRepository::saveAndFlush);
    }
}
