package by.academy.rentApp.service.impl;

import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.mapper.EngineMapper;
import by.academy.rentApp.model.repository.EngineRepository;
import by.academy.rentApp.model.entity.Engine;
import by.academy.rentApp.service.EngineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EngineServiceImpl implements EngineService {
    private final EngineRepository engineRepository;

    private final EngineMapper engineMapper;

    public EngineServiceImpl(EngineRepository engineRepository, EngineMapper engineMapper) {
        this.engineRepository = engineRepository;
        this.engineMapper = engineMapper;
    }

    @Override
    public List<EngineDto> getAll() {
        List<Engine> engins = engineRepository.findAll();
        List<EngineDto> engineDtos = new ArrayList<>();
        engins.forEach(engine -> {
            engineDtos.add(engineMapper.engineToEngineDto(engine));
        });
        return engineDtos;
    }

    @Override
    @Transactional
    public EngineDto saveEngine(EngineDto engineDto) {
        Engine savedEngine = engineRepository.save(engineMapper.engineDtoToEngine(engineDto));
        return engineMapper.engineToEngineDto(savedEngine);
    }

    @Override
    public EngineDto findEngineById(Integer id) {
        Engine engine = engineRepository.findEngineById(id);
        return engineMapper.engineToEngineDto(engine);
    }

    @Override
    public EngineDto findEngineByName(String name) {
        Engine engine = engineRepository.findEngineByName(name);
        return engineMapper.engineToEngineDto(engine);
    }

    @Override
    public void deleteEngine(EngineDto engineDto) {
        engineRepository.delete(engineMapper.engineDtoToEngine(engineDto));
    }

    @Override
    public boolean existsById(Integer id) {
        return engineRepository.existsById(id);
    }

}
