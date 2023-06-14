package market.service;


import lombok.RequiredArgsConstructor;
import market.dto.ColorDto;
import market.mapper.ColorMapper;
import market.repository.ColorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ColorService {
    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;
    public List<ColorDto> getAllColors() {
        return colorRepository.findAll().stream()
                .map(colorMapper::colorToColorDto).toList();
    }
}
