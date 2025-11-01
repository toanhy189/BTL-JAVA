package com.javaweb.repository;

import com.javaweb.entity.AssignmentBuildingEntity;
import com.javaweb.entity.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AssignmentBuildingRepository extends JpaRepository<AssignmentBuildingEntity, Long> {

    void deleteByBuildings(BuildingEntity buildingEntity);

    List<AssignmentBuildingEntity> findByBuildings(BuildingEntity buildingEntity);

    void deleteByBuildingsIdIn(List<Long> ids);
}
