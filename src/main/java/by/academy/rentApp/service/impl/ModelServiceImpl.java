package by.academy.rentApp.service.impl;

import by.academy.rentApp.dto.ModelDto;
import by.academy.rentApp.model.dao.ModelDao;
import by.academy.rentApp.model.entity.Model;
import by.academy.rentApp.service.ModelService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {
    private final ModelDao dao;

    public ModelServiceImpl(ModelDao dao) {
        this.dao = dao;
    }

    @Override
    public List<ModelDto> getAll() {
        List<Model> models = dao.findAll();

        List<ModelDto> modelDtos = new ArrayList<>();

        models.forEach(model -> {
            ModelDto modelDto = new ModelDto();
            setModelIntoModelDto(model, modelDto);
            modelDtos.add(modelDto);
        });

        return modelDtos;
    }

    @Override
    public ModelDto getModel(Integer id) {
        Model model = dao.findModelById(id);
        ModelDto modelDto = new ModelDto();
        setModelIntoModelDto(model, modelDto);
        return modelDto;
    }

    public void setModelIntoModelDto(Model model, ModelDto modelDto) {
        modelDto.setId(model.getId());
        modelDto.setName(model.getName());
        modelDto.setSeatsNum(model.getSeatsNum());
        modelDto.setEngineVolume(model.getEngineVolume());
        modelDto.setEngine(model.getEngine().getName());
        modelDto.setAutoTransmission(model.isAutoTransmission());
        modelDto.setType(model.getType().getName());
    }
}
