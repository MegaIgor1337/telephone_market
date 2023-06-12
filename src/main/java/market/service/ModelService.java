package market.service;

import lombok.RequiredArgsConstructor;
import market.dto.ModelDto;
import market.mapper.ModelMapper;
import market.repository.ModelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ModelService {
    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;
    public List<ModelDto> getAllModels() {
        return modelRepository.findAll().stream()
                .map(modelMapper::modelToModelDto).toList();
    }
}
