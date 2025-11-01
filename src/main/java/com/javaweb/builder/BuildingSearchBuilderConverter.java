package com.javaweb.builder;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.model.request.BuildingSearchRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuildingSearchBuilderConverter {

    public BuildingSearchBuilder toBuildingSearchBuilder(BuildingSearchRequest request, List<String> typeCode) {
        // Sử dụng Builder pattern để tạo BuildingSearchBuilder
        BuildingSearchBuilder builder = new BuildingSearchBuilder.Builder()
                .setName(request.getName())
                .setFloorArea(request.getFloorArea())
                .setDistrict(request.getDistrict())
                .setWard(request.getWard())
                .setStreet(request.getStreet())
                .setNumberOfBasement(request.getNumberOfBasement())
                .setDirection(request.getDirection())
                .setLevel(request.getLevel())
                .setAreaFrom(request.getAreaFrom())
                .setAreaTo(request.getAreaTo())
                .setRentPriceFrom(request.getRentPriceFrom())
                .setRentPriceTo(request.getRentPriceTo())
                .setManagerName(request.getManagerName())
                .setManagerPhone(request.getManagerPhone())
                .setStaffId(request.getStaffId())
                .setTypeCode(typeCode)
                .build();

        return builder;
    }
}
