package com.javaweb.converter;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.repository.RentAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RentAreaConverter {
    // ... có thể có các constructor hoặc trường khác ở đây
@Autowired
private RentAreaRepository rentAreaRepository;
    public RentAreaEntity toRentAreaEntity(BuildingDTO buildingDTO, Long val) {
        BuildingEntity buildingEntity = new BuildingEntity();
        buildingEntity.setId(buildingDTO.getId());
        RentAreaEntity res = new RentAreaEntity();
        res.setValue(val);
        res.setBuildingId(buildingEntity);
        return res;
    }

    public RentAreaEntity toRentAreaEntity(Long val, BuildingEntity buildingEntity) {
        // 1. Khởi tạo đối tượng RentAreaEntity mới
        RentAreaEntity res = new RentAreaEntity();

        // 2. Thiết lập ID của Tòa nhà (Mối quan hệ)
        // Giả định rằng setBuildingId nhận BuildingEntity hoặc ID của BuildingEntity
        // Dựa trên tên hàm setBuildingId, có khả năng đây là cách thiết lập mối quan hệ.
        res.setBuildingId(buildingEntity);

        // 3. Thiết lập giá trị diện tích thuê
        res.setValue(val);

        // 4. Trả về đối tượng RentAreaEntity đã được thiết lập
        return res;
    }

    public List<RentAreaEntity> toRentAreaEntityList(BuildingDTO buildingDTO, BuildingEntity buildingEntity) {
        // Lấy chuỗi các khu vực cho thuê và tách bằng dấu phẩy
        String[] rentAreas = buildingDTO.getRentArea().split(",");

        // Khởi tạo danh sách các thực thể khu vực cho thuê
        List<RentAreaEntity> rentAreaEntityList = new ArrayList<>();

        // Lặp qua mảng các chuỗi khu vực cho thuê, chuyển đổi và thêm vào danh sách
        for (String val : rentAreas) {
            // Chuyển đổi chuỗi (val) thành Long và gọi phương thức chuyển đổi
            rentAreaEntityList.add(toRentAreaEntity(Long.valueOf(val), buildingEntity));
        }

        // Trả về danh sách kết quả
        return rentAreaEntityList;
    }
}
