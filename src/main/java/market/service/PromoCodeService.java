package market.service;

import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import market.dto.PromoCodeDto;
import market.dto.PromoCodeDtoWithPage;
import market.dto.PromoCodeFilter;
import market.entity.PromoCode;
import market.exception.ValidationException;
import market.mapper.PromoCodeDtoWithPageMapper;
import market.mapper.PromoCodeMapper;
import market.repository.PromoCodeProductRepository;
import market.repository.PromoCodeRepository;
import market.validator.EnteredPromoCodeValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static market.util.StringContainer.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PromoCodeService {
    private final PromoCodeMapper promoCodeMapper;
    private final Integer pageSize = 2;
    private final PromoCodeRepository promoCodeRepository;
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
        return promoCodeRepository.findAll(specification, PageRequest.of(page, pageSize))
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
                        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND))), page, pageSize);
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
}
