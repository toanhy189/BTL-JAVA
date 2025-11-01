package com.javaweb.converter;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.utils.DistrictCode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

@Component// Đánh dấu đây là một Bean của Spring để có thể @Autowired
public class BuildingConverter {
    @Autowired
    private RentAreaConverter rentAreaConverter;


    @Autowired
    private ModelMapper modelMapper;

    /**
     * Chuyển đổi từ BuildingEntity sang BuildingSearchResponse (DTO)
     */
    public BuildingSearchResponse toBuildingSearchResponse(BuildingEntity buildingEntity) {

        // 1. Map các trường giống nhau bằng ModelMapper
        BuildingSearchResponse res = modelMapper.map(buildingEntity, BuildingSearchResponse.class);

        // 2. Xử lý trường RentArea (nối các giá trị diện tích lại với nhau)
        List<RentAreaEntity> rentAreaEntities = buildingEntity.getRentAreaEntities();

        String rentArea = rentAreaEntities.stream()
                // Lấy giá trị diện tích và chuyển thành chuỗi
                .map(it -> it.getValue().toString())
                // Nối các chuỗi diện tích lại với nhau, cách nhau bởi dấu phẩy và khoảng trắng (giả định)
                .collect(Collectors.joining(", ")); // Giả định delimiter là ", "

        res.setRentArea(rentArea);

        // 3. Xử lý trường District (chuyển mã code thành tên hiển thị)
        Map<String, String> districts = DistrictCode.type();

        String districtName = "";

        if (buildingEntity.getDistrict() != null && buildingEntity.getDistrict()!="") {
            // Lấy tên quận/huyện từ Enum/Map dựa trên mã code
            districtName = districts.get(buildingEntity.getDistrict());
        }

        // 4. Xử lý trường Address (ghép chuỗi địa chỉ)
        if (districtName != null && districtName!="") {
            res.setAddress(
                    buildingEntity.getStreet() + ", " +
                            buildingEntity.getWard() + ", " +
                            districtName
            );
        }

        return res;
    }

    public BuildingDTO toBuildingDTO(BuildingEntity buildingEntity) {
        return modelMapper.map(buildingEntity, BuildingDTO.class);
    }

    // ---

    // Phương thức chuyển đổi từ BuildingDTO sang BuildingEntity
    public BuildingEntity toBuildingEntity(BuildingDTO buildingDTO) {
        // 1. Ánh xạ cơ bản các trường trùng tên
        BuildingEntity buildingEntity = modelMapper.map(buildingDTO, BuildingEntity.class);

        // 2. Xử lý trường 'typeCode': Chuyển đổi và loại bỏ dấu tiếng Việt
        // Giả định removeAccent() trả về chuỗi đã được xử lý (ví dụ: "TANG_TRET,NGUYEN_CAN")
        buildingEntity.setTypeCode(removeAccent(buildingDTO.getTypeCode()));

        // 3. Xử lý trường 'rentAreaEntities': Chuyển đổi danh sách khu vực thuê
        // Phương thức này sử dụng BuildingDTO để lấy dữ liệu khu vực thuê
        // và sử dụng buildingEntity để thiết lập mối quan hệ
        buildingEntity.setRentAreaEntities(rentAreaConverter.toRentAreaEntityList(buildingDTO, buildingEntity));

        return buildingEntity;
    }

    // ---

    // Phương thức tiện ích để loại bỏ dấu tiếng Việt và nối chuỗi (như trong ảnh)
    // Lưu ý: Hình ảnh cho thấy tham số là List<String>, nhưng phương thức được gọi ở trên
    // có lẽ mong đợi là String hoặc List<String> tùy theo cách getTypeCode() trả về.
    // Tôi sẽ viết lại theo cú pháp trong ảnh cuối cùng:
    public static String removeAccent(List<String> typeCodes) {
        // Phương thức String.join(delimiter, elements) nối các phần tử lại bằng delimiter
        // Giả định rằng logic loại bỏ dấu tiếng Việt đã được thực hiện bên trong hàm này trước khi join,
        // hoặc logic removeAccent thực tế nhận vào String và chỉ là tên hàm bị trùng lắp.
        // Dựa vào cú pháp trong ảnh, đây là hàm nối chuỗi:
        return String.join( ",", typeCodes);
    }
}
