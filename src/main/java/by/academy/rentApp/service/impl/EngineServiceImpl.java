package by.academy.rentApp.service.impl;

import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.exception.ValidationException;
import by.academy.rentApp.model.repository.EngineRepository;
import by.academy.rentApp.model.entity.Engine;
import by.academy.rentApp.service.EngineConverter;
import by.academy.rentApp.service.EngineService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class EngineServiceImpl implements EngineService {
    private final EngineRepository engineRepository;
    private final EngineConverter engineConverter;

    public EngineServiceImpl(EngineRepository engineRepository, EngineConverter engineConverter) {
        this.engineRepository = engineRepository;
        this.engineConverter = engineConverter;
    }

    @Override
    public List<EngineDto> getAll() {
        List<Engine> engins = engineRepository.findAll();
        List<EngineDto> engineDtos = new ArrayList<>();

        engins.forEach(engine -> {
            engineDtos.add(engineConverter.fromEngineToEngineDto(engine));
        });

        return engineDtos;
    }

    @Override
    public EngineDto saveEngine(EngineDto engineDto) throws ValidationException {
        validateEngineDto(engineDto);
        Engine savedEngine = engineRepository.save(engineConverter.fromEngineDtoToEngine(engineDto));

        return engineConverter.fromEngineToEngineDto(savedEngine);
    }

    @Override
    public EngineDto findEngineById(Integer id) {
        Engine engine = engineRepository.findEngineById(id);
        return engineConverter.fromEngineToEngineDto(engine);
    }

    @Override
    public void deleteEngine(EngineDto engineDto) {
        engineRepository.delete(engineConverter.fromEngineDtoToEngine(engineDto));
    }

    @Override
    public boolean existsById(Integer id) {
        return engineRepository.existsById(id);
    }


    private void validateEngineDto(EngineDto engineDto) throws ValidationException {
        if (isNull(engineDto)) {
            throw new ValidationException("Object engine is null");
        }
        if (isNull(engineDto.getName()) || engineDto.getName().isEmpty()) {
            throw new ValidationException("Name is empty");
        }
    }


}
