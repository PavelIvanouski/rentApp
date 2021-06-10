package by.academy.rentApp.service.impl;

import by.academy.rentApp.dto.EngineDto;
import by.academy.rentApp.model.dao.EngineDao;
import by.academy.rentApp.model.entity.Engine;
import by.academy.rentApp.service.EngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EngineServiceImpl implements EngineService {
    private final EngineDao dao;

    public EngineServiceImpl(EngineDao dao) {
        this.dao = dao;
    }

    @Override
    public List<EngineDto> getAll() {
        List<Engine> engins = dao.findAll();

        List<EngineDto> engineDtos = new ArrayList<>();

        engins.forEach(engine -> {
            EngineDto engineDto = new EngineDto();

            engineDto.setId(engine.getId());
            engineDto.setName(engine.getName());

            engineDtos.add(engineDto);
        });

        return engineDtos;
    }
}
