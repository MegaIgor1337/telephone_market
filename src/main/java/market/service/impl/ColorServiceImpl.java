package market.service.impl;


import lombok.RequiredArgsConstructor;
import market.service.dto.ColorDto;
import market.service.mapper.ColorMapper;
import market.model.repository.ColorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ColorServiceImpl implements market.service.ColorService {
    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;

    @Override
    public List<ColorDto> getAllColors() {
        return colorRepository.findAll().stream()
                .map(colorMapper::colorToColorDto).toList();
    }
}
