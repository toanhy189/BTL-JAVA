package com.javaweb.builder;

import java.util.List;

/**
 * Lớp chứa các tiêu chí tìm kiếm cho Building (Dùng trong Repository & Service)
 * - Sử dụng Builder Pattern để dễ mở rộng và khởi tạo linh hoạt.
 */
public class BuildingSearchBuilder {

    // --- Các trường cơ bản trong bảng Building ---
    private String name;
    private String district;
    private String street;
    private String ward;
    private String direction;
    private Long level;
    private Long numberOfBasement;
    private Long floorArea;
    private Long rentPriceFrom;
    private Long rentPriceTo;
    private Long areaFrom;
    private Long areaTo;
    private String managerName;
    private String managerPhone;

    // --- Các trường đặc biệt ---
    private Long staffId; // lọc theo nhân viên quản lý
    private List<String> typeCode; // danh sách loại tòa nhà (ví dụ: TANG_TRET, NGUYEN_CAN, NOI_THAT)

    // --- Getter (bắt buộc để dùng với Reflection) ---
    public String getName() {
        return name;
    }

    public String getDistrict() {
        return district;
    }

    public String getStreet() {
        return street;
    }

    public String getWard() {
        return ward;
    }

    public String getDirection() {
        return direction;
    }

    public Long getLevel() {
        return level;
    }

    public Long getNumberOfBasement() {
        return numberOfBasement;
    }

    public Long getFloorArea() {
        return floorArea;
    }

    public Long getRentPriceFrom() {
        return rentPriceFrom;
    }

    public Long getRentPriceTo() {
        return rentPriceTo;
    }

    public Long getAreaFrom() {
        return areaFrom;
    }

    public Long getAreaTo() {
        return areaTo;
    }

    public Long getStaffId() {
        return staffId;
    }

    public List<String> getTypeCode() {
        return typeCode;
    }

    public String getManagerName() {
        return managerName;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    // --- Builder Pattern để khởi tạo linh hoạt ---
    private BuildingSearchBuilder(Builder builder) {
        this.name = builder.name;
        this.district = builder.district;
        this.street = builder.street;
        this.ward = builder.ward;
        this.direction = builder.direction;
        this.level = builder.level;
        this.numberOfBasement = builder.numberOfBasement;
        this.floorArea = builder.floorArea;
        this.rentPriceFrom = builder.rentPriceFrom;
        this.rentPriceTo = builder.rentPriceTo;
        this.managerName=builder.managerName;
        this.managerPhone=builder.managerPhone;
        this.areaFrom = builder.areaFrom;
        this.areaTo = builder.areaTo;
        this.staffId = builder.staffId;
        this.typeCode = builder.typeCode;

    }

    public static class Builder {
        private String name;
        private String district;
        private String street;
        private String ward;
        private String direction;
        private Long level;
        private Long numberOfBasement;
        private Long floorArea;
        private Long rentPriceFrom;
        private Long rentPriceTo;
        private Long areaFrom;
        private Long areaTo;
        private Long staffId;
        private List<String> typeCode;
        private String managerName;
        private String managerPhone;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDistrict(String district) {
            this.district = district;
            return this;
        }

        public Builder setStreet(String street) {
            this.street = street;
            return this;
        }

        public Builder setWard(String ward) {
            this.ward = ward;
            return this;
        }

        public Builder setDirection(String direction) {
            this.direction = direction;
            return this;
        }

        public Builder setLevel(Long level) {
            this.level = level;
            return this;
        }

        public Builder setNumberOfBasement(Long numberOfBasement) {
            this.numberOfBasement = numberOfBasement;
            return this;
        }

        public Builder setFloorArea(Long floorArea) {
            this.floorArea = floorArea;
            return this;
        }

        public Builder setRentPriceFrom(Long rentPriceFrom) {
            this.rentPriceFrom = rentPriceFrom;
            return this;
        }

        public Builder setRentPriceTo(Long rentPriceTo) {
            this.rentPriceTo = rentPriceTo;
            return this;
        }

        public Builder setAreaFrom(Long areaFrom) {
            this.areaFrom = areaFrom;
            return this;
        }

        public Builder setAreaTo(Long areaTo) {
            this.areaTo = areaTo;
            return this;
        }

        public Builder setStaffId(Long staffId) {
            this.staffId = staffId;
            return this;
        }

        public Builder setTypeCode(List<String> typeCode) {
            this.typeCode = typeCode;
            return this;
        }

        public BuildingSearchBuilder build() {
            return new BuildingSearchBuilder(this);
        }

        public Builder setManagerName(String managerName) {
            this.managerName = managerName;
            return this;
        }

        public Builder setManagerPhone(String managerPhone) {
            this.managerPhone = managerPhone;
            return this;
        }
    }
}
