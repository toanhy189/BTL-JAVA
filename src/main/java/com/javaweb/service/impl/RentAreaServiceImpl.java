package com.javaweb.service.impl;

import com.javaweb.converter.RentAreaConverter;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.service.RentAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RentAreaServiceImpl implements RentAreaService {

    @Autowired
    private RentAreaRepository rentAreaRepository;
    @Autowired
    private RentAreaConverter rentAreaConverter;
    @Autowired
    private BuildingRepository buildingRepository;

    @Override
    public void deleteByBuildings(List<Long> ids)
    {

        rentAreaRepository.deleteByBuildingId_IdIn(ids);
    }

   @Override
    public void addRentArea(BuildingDTO buildingDTO)
    {
        BuildingEntity buildingEntity = buildingRepository.findById(buildingDTO.getId()).get();
        rentAreaRepository.deleteByBuildingId(buildingEntity);

        String[] rentAreas = buildingDTO.getRentArea().trim().split( ",");

        for(String val : rentAreas)
        {
            RentAreaEntity rentAreaEntity = rentAreaConverter.toRentAreaEntity(buildingDTO, Long.valueOf(val));
            rentAreaRepository.save(rentAreaEntity);
        }
    }
}
