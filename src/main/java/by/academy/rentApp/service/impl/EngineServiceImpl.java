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

    public EngineServiceImpl(EngineRepository engineRepository) {
        this.engineRepository = engineRepository;
    }

    @Override
    public List<EngineDto> getAll() {
        List<Engine> engins = engineRepository.findAll();
        List<EngineDto> engineDtos = new ArrayList<>();
        engins.forEach(engine -> {
            engineDtos.add(EngineMapper.INSTANCE.engineToEngineDto(engine));
        });
        return engineDtos;
    }

    @Override
    @Transactional
    public EngineDto saveEngine(EngineDto engineDto) {
        Engine savedEngine = engineRepository.save(EngineMapper.INSTANCE.engineDtoToEngine(engineDto));
        return EngineMapper.INSTANCE.engineToEngineDto(savedEngine);
    }

    @Override
    public EngineDto findEngineById(Integer id) {
        Engine engine = engineRepository.findEngineById(id);
        return EngineMapper.INSTANCE.engineToEngineDto(engine);
    }

    @Override
    public void deleteEngine(EngineDto engineDto) {
        engineRepository.delete(EngineMapper.INSTANCE.engineDtoToEngine(engineDto));
    }

    @Override
    public boolean existsById(Integer id) {
        return engineRepository.existsById(id);
    }

}
