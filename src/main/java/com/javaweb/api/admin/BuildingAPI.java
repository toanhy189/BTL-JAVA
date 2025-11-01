package com.javaweb.api.admin;

import com.javaweb.model.dto.AssignmentBuildingDTO;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.service.AssignmentBuildingService;
import com.javaweb.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "buildingAPIOfAdmin")
@RequestMapping("/api/building")
@Transactional
public class BuildingAPI {

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private AssignmentBuildingService assignmentBuildingService;


    /* bach123456789 */
    @GetMapping
    public List<BuildingSearchResponse> getBuilding(@ModelAttribute BuildingSearchRequest buildingSearchRequest, Pageable pageable)
    {
        List<BuildingSearchResponse> res = buildingService.findAll(buildingSearchRequest, pageable);
        return res;
    }

    /* bach123456789 */
    @PostMapping
    public ResponseEntity<BuildingDTO> addOrUpdateBuilding(@RequestBody BuildingDTO buildingDTO)
    {
        return ResponseEntity.ok(buildingService.addOrUpdateBuilding(buildingDTO));
    }

    /* bach123456789 */
    @DeleteMapping("/{ids}")
    public void deleteBuilding(@PathVariable List<Long> ids) { buildingService.deleteBuildings(ids); }
//    @DeleteMapping
//    public void deleteBuilding(@RequestParam("ids") List<Long> ids) { // <<< Sửa thành @RequestParam("ids")
//        buildingService.deleteBuildings(ids);
//    }

   @GetMapping("{id}/staffs")
    public ResponseDTO loadStaffs(@PathVariable Long id){
        ResponseDTO result=buildingService.listStaffs(id);
        return result;
   }

//    @PostMapping("/assignment")
//    public void updateAssignmentBuilding(@RequestBody AssignmentBuildingDTO assignmentBuildingDTO) {
//        assignmentBuildingService.addAssignmentBuildingEntity(assignmentBuildingDTO);
//    }
@PostMapping("/assignment")
public ResponseDTO updateAssignmentBuilding(@RequestBody AssignmentBuildingDTO assignmentBuildingDTO) {
    assignmentBuildingService.addAssignmentBuildingEntity(assignmentBuildingDTO);

    ResponseDTO response = new ResponseDTO();
    response.setMessage("success");
    return response;
}

}
