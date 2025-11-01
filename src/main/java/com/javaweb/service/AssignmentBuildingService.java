package com.javaweb.service;

import com.javaweb.model.dto.AssignmentBuildingDTO;

import java.util.List;

public interface AssignmentBuildingService {

    public void deleteByBuildingsIn(List<Long> ids);

    public void addAssignmentBuildingEntity(AssignmentBuildingDTO assignmentBuildingDTO);
}
