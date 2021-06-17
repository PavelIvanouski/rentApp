package by.academy.rentApp.service.impl;

import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.exception.ValidationException;
import by.academy.rentApp.model.dao.EngineDao;
import by.academy.rentApp.model.entity.Engine;
import by.academy.rentApp.service.EngineConverter;
import by.academy.rentApp.service.EngineService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class EngineServiceImpl implements EngineService {
    private final EngineDao dao;
    private final EngineConverter engineConverter;

    public EngineServiceImpl(EngineDao dao, EngineConverter engineConverter) {
        this.dao = dao;
        this.engineConverter = engineConverter;
    }

    @Override
    public List<EngineDto> getAll() {
        List<Engine> engins = dao.findAll();
        List<EngineDto> engineDtos = new ArrayList<>();

        engins.forEach(engine -> {
            engineDtos.add(engineConverter.fromEngineToEngineDto(engine));
        });

        return engineDtos;
    }

    @Override
    public EngineDto saveEngine(EngineDto engineDto) throws ValidationException {
        validateEngineDto(engineDto);
        Engine savedEngine = dao.save(engineConverter.fromEngineDtoToEngine(engineDto));

        return engineConverter.fromEngineToEngineDto(savedEngine);
    }

    @Override
    public EngineDto findEngineById(Integer id) {
        Engine engine = dao.findEngineById(id);
        return engineConverter.fromEngineToEngineDto(engine);
    }

    @Override
    public void deleteEngine(EngineDto engineDto) {
        dao.delete(engineConverter.fromEngineDtoToEngine(engineDto));
    }

    @Override
    public boolean existsById(Integer id) {
        return dao.existsById(id);
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
