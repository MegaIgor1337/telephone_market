package market.service.impl;

import lombok.RequiredArgsConstructor;
import market.service.dto.ModelDto;
import market.service.mapper.ModelMapper;
import market.model.repository.ModelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ModelServiceImpl implements market.service.ModelService {
    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<ModelDto> getAllModels() {
        return modelRepository.findAll().stream()
                .map(modelMapper::modelToModelDto).toList();
    }
}
