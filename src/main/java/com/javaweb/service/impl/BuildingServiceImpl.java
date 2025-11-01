package com.javaweb.service.impl;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.builder.BuildingSearchBuilderConverter;
import com.javaweb.converter.BuildingConverter;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.StaffResponseDTO;
import com.javaweb.repository.AssignmentBuildingRepository;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.service.AssignmentBuildingService;
import com.javaweb.service.BuildingService;
import com.javaweb.service.RentAreaService;
import com.javaweb.utils.NumberUtils;
import com.javaweb.utils.StringUtils;
import com.javaweb.utils.UploadFileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuildingServiceImpl implements BuildingService {
   @Autowired
   private BuildingSearchBuilderConverter buildingSearchBuilderConverter;
    @Autowired
    private BuildingRepository buildingRepository ;
    @Autowired
    private BuildingConverter buildingconverter;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RentAreaService rentAreaService;
    @Autowired
    private AssignmentBuildingService assignmentBuildingService;
    @Autowired
    private AssignmentBuildingRepository assignmentBuildingRepository;
    @Autowired
    private UploadFileUtils uploadFileUtils;


    @Override
    public List<BuildingSearchResponse> findAll(BuildingSearchRequest buildingSearchRequest, Pageable pageable){
        List<String> typeCode =buildingSearchRequest.getTypeCode();
        BuildingSearchBuilder buildingSearchBuilder=buildingSearchBuilderConverter.toBuildingSearchBuilder(buildingSearchRequest,typeCode);
        List<BuildingEntity> buildingEntities=buildingRepository.findAll(buildingSearchBuilder,pageable);
        List<BuildingSearchResponse> res=new ArrayList<>();
        for(BuildingEntity item:buildingEntities){
            BuildingSearchResponse building =buildingconverter.toBuildingSearchResponse(item);
            res.add(building);
        }


        return res;
    }

    public static String removeAccent(List<String> typeCodes)
    {
        String s = String.join( ",", typeCodes);
        return s;
    }

    public static boolean checkAddBuilding(BuildingDTO buildingDTO)
    {
        if(!StringUtils.check(buildingDTO.getName())) return false;
        if(!StringUtils.check(buildingDTO.getDistrict())) return false;
        if(!StringUtils.check(buildingDTO.getStreet())) return false;
        if(!StringUtils.check(buildingDTO.getWard())) return false;
        if(!StringUtils.check(buildingDTO.getRentArea())) return false;
        if(!StringUtils.check(buildingDTO.getRentPriceDescription())) return false;

        if(!NumberUtils.checkNumber(buildingDTO.getNumberOfBasement())) return false;
        if(!NumberUtils.checkNumber(buildingDTO.getFloorArea())) return false;
        if(!NumberUtils.checkNumber(buildingDTO.getRentPrice())) return false;

        return true;
    }

    @Override
    public BuildingDTO findById(Long id)
    {
        BuildingEntity buildingEntity = buildingRepository.findById(id).get();
        BuildingDTO res = modelMapper.map(buildingEntity, BuildingDTO.class);

        List<RentAreaEntity> rentAreaEntities = buildingEntity.getRentAreaEntities();
        String rentArea = rentAreaEntities.stream().map(it -> it.getValue().toString()).collect(Collectors.joining( ", "));
        // res.setImage(buildingEntity.getImage());

        res.setRentArea(rentArea);
        res.setTypeCode(toTypeCodeList(buildingEntity.getTypeCode()));

        return res;
    }

    public List<String> toTypeCodeList(String typeCodes)
    {
        String[] arr = typeCodes.split( ",");
        List<String> res = new ArrayList<>();
        for(String it : arr) res.add(it);
        return res;
    }
    @Override
    public BuildingDTO addOrUpdateBuilding(BuildingDTO buildingDTO) {
        // ✅ 1. Kiểm tra dữ liệu đầu vào
        if (!checkAddBuilding(buildingDTO)) return null;

        Long buildingId = buildingDTO.getId();
        BuildingEntity buildingEntity;

        if (buildingId != null) {
            // ===== UPDATE =====
            BuildingEntity foundBuilding = buildingRepository.findById(buildingId)
                    .orElseThrow(() -> new NotFoundException("Building not found!"));

            // Map dữ liệu mới từ DTO sang entity cũ (tránh mất các giá trị khác)
            modelMapper.map(buildingDTO, foundBuilding);
            buildingEntity = foundBuilding;
        } else {
            // ===== ADD =====
            buildingEntity = modelMapper.map(buildingDTO, BuildingEntity.class);
        }

        // ✅ 2. Chuẩn hóa dữ liệu typeCode
        buildingEntity.setTypeCode(removeAccent(buildingDTO.getTypeCode()));

        // ✅ 3. Lưu building vào DB (Hibernate sẽ tự sinh id nếu là thêm mới)
        BuildingEntity savedBuilding = buildingRepository.save(buildingEntity);

        // ✅ 4. Xử lý rent area (nếu có dữ liệu)
        if (StringUtils.check(buildingDTO.getRentArea())) {
            // Cập nhật id vào DTO để tránh lỗi null
            buildingDTO.setId(savedBuilding.getId());
            rentAreaService.addRentArea(buildingDTO);
        }

        // ✅ 5. Trả về DTO đã cập nhật (bao gồm id mới)
        return modelMapper.map(savedBuilding, BuildingDTO.class);
    }




//    private void saveThumbnail(BuildingDTO buildingDTO, BuildingEntity buildingEntity)
//    {
//        String path = "/building/" + buildingDTO.getImageName();
//        if (null != buildingDTO.getImageBase64())
//        {
//            if (null != buildingEntity.getImage())
//            {
//                if (!path.equals(buildingEntity.getImage()))
//                {
//                    File file = new File( "C://home/office" + buildingEntity.getImage());
//                    file.delete();
//                }
//            }
//
//            byte[] bytes = Base64.decodeBase64(buildingDTO.getImageBase64().getBytes());
//            uploadFileUtils.writeOrUpdate(path, bytes);
//            buildingEntity.setImage(path);
//        }
//    }
    @Override
    public void deleteBuildings(List<Long> ids) {
        rentAreaService.deleteByBuildings(ids);
        assignmentBuildingService.deleteByBuildingsIn(ids);
        for(Long id:ids) buildingRepository.deleteById(id);
    }
    public ResponseDTO listStaffs(Long buildingId) {
        BuildingEntity building =buildingRepository.findById(buildingId).get();
        List<UserEntity> staffs =userRepository.findByStatusAndRoles_Code(1,"STAFF");
        List<UserEntity> staffAssigment = building.getUserEntities();
        List< StaffResponseDTO> staffResponseDTOS=new ArrayList<>();
        ResponseDTO responseDTO = new ResponseDTO();

        for(UserEntity it : staffs){
            StaffResponseDTO staffResponseDTO = new StaffResponseDTO();
            staffResponseDTO.setFullName(it.getFullName());
            staffResponseDTO.setStaffId(it.getId());
            if(staffAssigment.contains(it)){
                staffResponseDTO.setChecked("checked");
            }
            else{
                staffResponseDTO.setChecked("");
            }
            staffResponseDTOS.add(staffResponseDTO);
        }
        responseDTO.setData(staffResponseDTOS);
        responseDTO.setMessage("success");
        return responseDTO;

    }
    @Override
    public int countTotalItem(List<BuildingSearchResponse> list) {
        return (list != null) ? list.size() : 0;
    }
}