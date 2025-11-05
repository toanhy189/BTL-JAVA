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
//Kết hợp @Controller + @ResponseBody. Mọi phương thức trả về Java object sẽ được tự động chuyển thành JSON.
@RestController(value = "buildingAPIOfAdmin")
//Base URL cho tất cả endpoint trong class: /api/building
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

//    URL: GET /api/building/{id}/staffs → ví dụ /api/building/5/staffs
//Mục đích: Lấy danh sách nhân viên khả dụng và đánh dấu ai đã được gán cho building id.
   @GetMapping("{id}/staffs")
//    @PathVariable Long id từ URL.
    public ResponseDTO loadStaffs(@PathVariable Long id){
//       Lấy danh sách tất cả staff
        ResponseDTO result=buildingService.listStaffs(id);
        return result;
   }


@PostMapping("/assignment")
public ResponseDTO updateAssignmentBuilding(@RequestBody AssignmentBuildingDTO assignmentBuildingDTO) {
    assignmentBuildingService.addAssignmentBuildingEntity(assignmentBuildingDTO);

    ResponseDTO response = new ResponseDTO();
    response.setMessage("success");
    return response;
}

}
