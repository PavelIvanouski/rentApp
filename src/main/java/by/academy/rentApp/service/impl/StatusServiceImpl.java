package by.academy.rentApp.service.impl;

import by.academy.rentApp.dto.BrandDto;
import by.academy.rentApp.dto.StatusDto;
import by.academy.rentApp.mapper.StatusMapper;
import by.academy.rentApp.model.entity.Brand;
import by.academy.rentApp.model.entity.Status;
import by.academy.rentApp.model.repository.StatusRepository;
import by.academy.rentApp.service.StatusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {
    private final StatusRepository statusRepository;

    private final StatusMapper statusMapper;

    public StatusServiceImpl(StatusRepository statusRepository, StatusMapper statusMapper) {
        this.statusRepository = statusRepository;
        this.statusMapper = statusMapper;
    }


    @Override
    public List<StatusDto> getAll() {
        List<Status> statuses = statusRepository.findAll();
        List<StatusDto> statusDtos = new ArrayList<>();
        statuses.forEach(status -> {
            statusDtos.add(statusMapper.statusToStatusDto(status));
        });
        return statusDtos;
    }

    @Override
    public List<StatusDto> getAllByIdList(List<Integer> idList) {
        List<Status> statuses = statusRepository.findAllByIdList(idList);
        List<StatusDto> statusDtos = new ArrayList<>();
        statuses.forEach(status -> {
            statusDtos.add(statusMapper.statusToStatusDto(status));
        });
        return statusDtos;
    }

    @Override
    @Transactional
    public StatusDto saveStatus(StatusDto statusDto) {
        Status savedStatus = statusRepository.save(statusMapper.statusDtoToStatus(statusDto));
        return statusMapper.statusToStatusDto(savedStatus);
    }

    @Override
    public StatusDto findStatusById(Integer id) {
        Status status = statusRepository.findStatusById(id);
        return statusMapper.statusToStatusDto(status);
    }

    @Override
    public StatusDto findStatusByName(String name) {
        Status status = statusRepository.findStatusByName(name);
        return statusMapper.statusToStatusDto(status);
    }

    @Override
    public void deleteStatus(StatusDto statusDto) {
        statusRepository.delete(statusMapper.statusDtoToStatus(statusDto));
    }

    @Override
    public boolean existsById(Integer id) {
        return statusRepository.existsById(id);
    }

}
