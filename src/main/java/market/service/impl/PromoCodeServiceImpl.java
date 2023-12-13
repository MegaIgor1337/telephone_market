package market.service.impl;

import lombok.RequiredArgsConstructor;
import market.service.dto.CreatePromoCodeDto;
import market.service.dto.PromoCodeDto;
import market.service.dto.PromoCodeDtoWithPage;
import market.service.dto.PromoCodeFilter;
import market.model.entity.PromoCode;
import market.model.enums.PromoCodeStatus;
import market.exception.ValidationException;
import market.service.mapper.CreatePromoCodeMapper;
import market.service.mapper.PromoCodeDtoWithPageMapper;
import market.service.mapper.PromoCodeMapper;
import market.model.repository.ProductRepository;
import market.model.repository.PromoCodeProductRepository;
import market.model.repository.PromoCodeRepository;
import market.service.validator.AddedPromoCodeValidator;
import market.service.validator.ChangeDiscountPromoCodeValidator;
import market.service.validator.ChangeNamePromoCodeValidator;
import market.service.validator.EnteredPromoCodeValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static market.service.util.ConstantContainer.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PromoCodeServiceImpl implements market.service.PromoCodeService {
    private final CreatePromoCodeMapper createPromoCodeMapper;
    private final AddedPromoCodeValidator addedPromoCodeValidator;
    private final ChangeNamePromoCodeValidator changeNamePromoCodeValidator;
    private final PromoCodeMapper promoCodeMapper;
    private final ProductRepository productRepository;
    private final ChangeDiscountPromoCodeValidator changeDiscountPromoCodeValidator;
    private final PromoCodeRepository promoCodeRepository;
    private final PromoCodeProductRepository promoCodeProductRepository;
    private final PromoCodeDtoWithPageMapper promoCodeDtoWithPageMapper;
    private final EnteredPromoCodeValidator enteredPromoCodeValidator;

    @Override
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

    @Override
    public PromoCodeDtoWithPage getPromoCodeWithPage(Long id, Integer page) {
        var promoCode = promoCodeRepository.findById(id);
        return promoCodeDtoWithPageMapper.promoCodeDtoToPromoCodeDtoWithPage(promoCodeMapper
                .promoCodeTopromoCodeDto(promoCode
                        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND))), page, PAGE_SIZE);
    }

    @Override
    @Transactional
    public void deleteProductFromPromoCode(String productId, Long promoCodeId) {
        promoCodeProductRepository
                .deleteAllByProductIdAndPromoCodeId(Long.valueOf(productId), promoCodeId);
    }

    @Override
    @Transactional
    public void deletePromoCode(Long id) {
        promoCodeRepository.deleteAllById(id);
    }

    @Override
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

    @Override
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

    @Override
    @Transactional
    public void changeStatus(Long id, String newStatus) {
        var promoCode = promoCodeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        promoCode.setStatus(PromoCodeStatus.valueOf(newStatus));
    }

    @Override
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
