package com.javaweb.repository.custom.impl;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.repository.BuildingRepositoryCustom;
import com.javaweb.utils.NumberUtils;
import org.hibernate.engine.internal.Collections;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class BuildingRepositoryImpl implements BuildingRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder, Pageable pageable) {
        StringBuilder sql = new StringBuilder("SELECT * FROM building b ");
        StringBuilder where = new StringBuilder(" WHERE 1 = 1");

        // 1️⃣ Join nếu có staffId
        joinExecute(buildingSearchBuilder, sql);

        // 2️⃣ Điều kiện bình thường
        queryNormal(buildingSearchBuilder, where);

        // 3️⃣ Điều kiện đặc biệt
        querySpecial(buildingSearchBuilder, where);

        // 4️⃣ Ghép các phần WHERE lại
        sql.append(where);

        // 5️⃣ Thêm group by (phải nối vào sql, không phải where)
        groupByQuery(buildingSearchBuilder, sql);

        // 6️⃣ Thêm phân trang
        sql.append(" LIMIT ").append(pageable.getPageSize())
                .append(" OFFSET ").append(pageable.getOffset());

        // 7️⃣ In ra SQL để debug
        System.out.println("👉 SQL query = " + sql);

        // 8️⃣ Thực thi
        Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
        System.out.println("🔍 SQL: " + sql);

        return query.getResultList();
    }

    @Override
    public int countToTalItem(BuildingSearchResponse buildingSearchResponse) {
        // 1. Xây dựng câu lệnh SQL từ hàm hỗ trợ
        String sql = buildQueryFilter(buildingSearchResponse.getId());

        // 2. Tạo Native Query (truy vấn SQL thuần)
        // Lưu ý: Không cần tham số BuildingEntity.class vì ta chỉ cần đếm kích thước list
        Query query = entityManager.createNativeQuery(sql);

        // 3. Thực thi truy vấn và trả về kích thước của danh sách kết quả
        return query.getResultList().size();
    }

    /**
     * Hàm hỗ trợ xây dựng câu lệnh SQL SELECT có điều kiện WHERE theo ID
     */
    private String buildQueryFilter(Long id) {
        String sql = "SELECT * FROM building b where b.id = " + id;
        return sql;
    }

    /**
     * Xây dựng mệnh đề LIMIT và OFFSET cho việc phân trang.
     * Áp dụng cho các truy vấn SQL thuần (Native Query).
     */
    public static void splitPage(Pageable pageable, StringBuilder where) {

        // Thêm mệnh đề LIMIT (Kích thước trang)
        where.append(" LIMIT ")
                .append(pageable.getPageSize())
                // Thêm ký tự xuống dòng ('\n') để dễ đọc SQL khi debug (tùy chọn)
                .append("\n")
                // Thêm mệnh đề OFFSET (Vị trí bắt đầu)
                .append(" OFFSET ")
                .append(pageable.getOffset());
    }

    /**
     * Xây dựng mệnh đề JOIN dựa trên các tiêu chí tìm kiếm.
     * Trong trường hợp này, JOIN với bảng 'assignmentbuilding' nếu có lọc theo staffId.
     */
    public static void joinExecute(BuildingSearchBuilder buildingSearchBuilder, StringBuilder sql) {

        // Lấy giá trị staffId từ builder
        Long staffId = buildingSearchBuilder.getStaffId();

        // Kiểm tra nếu staffId hợp lệ (không null/không rỗng)
        if (NumberUtils.checkNumber(staffId)) {
            // Thêm mệnh đề JOIN: JOIN assignmentbuilding ON assignmentbuilding.buildingId = b.id
            // Giả định 'b' là alias của bảng 'building'
            sql.append(" join assignmentbuilding on assignmentbuilding.buildingId = b.id ");
        }
    }

    /**
     * Xây dựng các điều kiện tìm kiếm cơ bản (normal) bằng Reflection.
     * Phương thức này tự động duyệt qua các trường của BuildingSearchBuilder và tạo mệnh đề WHERE
     * cho các trường có giá trị, trừ các trường đặc biệt.
     */
//    public static void queryNormal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
//        try {
//            // Lấy tất cả các trường được khai báo trong lớp BuildingSearchBuilder
//            Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
//
//            // Duyệt qua từng trường
//            for (Field item : fields) {
//                // Đặt trường là có thể truy cập được (cần thiết cho các trường private)
//                item.setAccessible(true);
//                String fieldName = item.getName();
//
//                // Loại bỏ các trường đặc biệt (được xử lý ở nơi khác, ví dụ: querySpecial)
//                if (!fieldName.equals("staffId")
//                        && !fieldName.equals("typeCode")
//                        && !fieldName.startsWith("area")
//                        && !fieldName.startsWith("rentPrice")) {
//
//                    // Lấy giá trị của trường đó từ đối tượng buildingSearchBuilder
//                    Object value = item.get(buildingSearchBuilder);
//
//                    // Chỉ thêm điều kiện nếu giá trị không phải là null
//                    if (value != null) {
//                        // Xử lý cho các trường kiểu số (Long, Integer)
//                        if (item.getType().getName().equals("java.lang.Long")
//                                || item.getType().getName().equals("java.lang.Integer")) {
//
//                            // Thêm điều kiện tìm kiếm bằng (=) cho kiểu số
//                            // Giả định 'b' là alias cho bảng building
//                            where.append(" AND b.").append(fieldName).append(" = ").append(value).append(" ");
//
//                            // Xử lý cho các trường kiểu chuỗi (String)
//                        } else if (item.getType().getName().equals("java.lang.String")) {
//
//                            // Thêm điều kiện tìm kiếm tương đối (LIKE) cho kiểu chuỗi
//                            where.append(" AND b.").append(fieldName).append(" LIKE '%").append(value).append("%' ");
//                        }
//                        // Có thể thêm logic xử lý cho các kiểu dữ liệu khác (Date, Double, ...) ở đây
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            // Xử lý ngoại lệ trong trường hợp Reflection gặp lỗi
//            // Ví dụ: in ra lỗi hoặc log lại
//            ex.printStackTrace();
//        }
//    }
    public static void queryNormal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
        try {
            Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();

            for (Field item : fields) {
                item.setAccessible(true);
                String fieldName = item.getName();

                if (!fieldName.equals("staffId")
                        && !fieldName.equals("typeCode")
                        && !fieldName.startsWith("area")
                        && !fieldName.startsWith("rentPrice")) {

                    Object value = item.get(buildingSearchBuilder);
                    if (value != null && !value.toString().isEmpty()) {

                        // 🔥 Ánh xạ tên field trong Java sang tên cột trong DB
                        String columnName = convertFieldToColumn(fieldName);

                        if (item.getType().getName().equals("java.lang.Long")
                                || item.getType().getName().equals("java.lang.Integer")) {

                            where.append(" AND b.").append(columnName).append(" = ").append(value).append(" ");
                        } else if (item.getType().getName().equals("java.lang.String")) {
                            where.append(" AND b.").append(columnName).append(" LIKE '%").append(value).append("%' ");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static String convertFieldToColumn(String fieldName) {
        switch (fieldName) {
            case "floorArea": return "floorarea";
            case "numberOfBasement": return "numberofbasement";
            case "managerName": return "managername";
            case "managerPhone": return "managerphone";
            case "typeCode": return "type";
            default: return fieldName.toLowerCase(); // fallback
        }
    }


    /**
     * Xây dựng các điều kiện tìm kiếm đặc biệt (special)
     * (Ví dụ: staffId, rentArea, rentPrice, typeCode)
     */
    public static void querySpecial(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
        // --- 1. Lọc theo Nhân viên quản lý (staffId) ---
        Long staffId = buildingSearchBuilder.getStaffId();
        // Giả định NumberUtils.checkNumber(staffId) kiểm tra xem staffId có hợp lệ/không null không
        if (NumberUtils.checkNumber(staffId)) {
            // Giả định bảng AssignmentBuilding có alias là 'assignmentbuilding'
            where.append(" AND assignmentbuilding.staffId = " + staffId + " ");
        }

        // --- 2. Lọc theo Diện tích thuê (rentArea) ---
        Long rentAreaTo = buildingSearchBuilder.getAreaTo(); // Đổi tên từ getRentAreaTo()
        Long rentAreaFrom = buildingSearchBuilder.getAreaFrom(); // Đổi tên từ getRentAreaFrom()

        // Kiểm tra xem có điều kiện lọc diện tích nào được cung cấp không
        if (NumberUtils.checkNumber(rentAreaFrom) || NumberUtils.checkNumber(rentAreaTo)) {
            // Sử dụng mệnh đề EXISTS để kiểm tra trong bảng rentArea
            where.append(" AND exists (select * from rentArea r where b.id = r.buildingId ");

            if (rentAreaFrom != null) {
                // Thêm điều kiện diện tích từ
                where.append(" AND r.value >= " + rentAreaFrom + " ");
            }

            if (rentAreaTo != null) {
                // Thêm điều kiện diện tích đến
                where.append(" AND r.value <= " + rentAreaTo + " ");
            }

            where.append(") "); // Đóng mệnh đề EXISTS
        }

        // --- 3. Lọc theo Giá thuê (rentPrice) ---
        Long rentPriceTo = buildingSearchBuilder.getRentPriceTo();
        Long rentPriceFrom = buildingSearchBuilder.getRentPriceFrom();

        // Kiểm tra xem có điều kiện lọc giá thuê nào được cung cấp không
        if (NumberUtils.checkNumber(rentPriceFrom) || NumberUtils.checkNumber(rentPriceTo)) {
            if (rentPriceFrom != null) {
                // Thêm điều kiện giá từ (Giả định cột rentPrice nằm trực tiếp trong bảng Building (b))
                where.append(" AND b.rentPrice >= " + rentPriceFrom + " ");
            }

            if (rentPriceTo != null) {
                // Thêm điều kiện giá đến
                where.append(" AND b.rentPrice <= " + rentPriceTo + " ");
            }
        }

        // --- 4. Lọc theo Loại tòa nhà (typeCode) - Phần này bị cắt ở cuối ảnh ---
        // Tôi sẽ bổ sung phần logic thường thấy cho lọc theo danh sách typeCode:
        List<String> typeCode = buildingSearchBuilder.getTypeCode();

        // 5. Lọc theo Loại tòa nhà (typeCode)
        if (typeCode != null && typeCode.size() != 0) {

            // Bắt đầu mệnh đề AND cho toàn bộ điều kiện lọc theo typeCode
            where.append(" AND (");

            // Sử dụng Stream để tạo chuỗi điều kiện OR
            // Mỗi item 'it' trong typeCode sẽ được chuyển thành: "b.type like '%typeCodeValue%'"
            String sql = typeCode.stream()
                    .map(it -> "b.type like '%" + it + "%'")
                    // Nối các điều kiện bằng chuỗi " OR " (đây là giá trị của 'delimiter' bị cắt)
                    .collect(Collectors.joining(" OR "));

            // Thêm chuỗi điều kiện SQL đã tạo vào mệnh đề WHERE
            where.append(sql);

            // Đóng ngoặc cho mệnh đề OR
            where.append(") ");
        }
    }

    /**
     * Xây dựng mệnh đề GROUP BY dựa trên các tiêu chí tìm kiếm (nếu cần).
     * Mệnh đề GROUP BY được sử dụng để tránh trùng lặp Building khi JOIN với các bảng 1-n (ví dụ: assignmentbuilding).
     */
    public static void groupByQuery(BuildingSearchBuilder buildingSearchBuilder, StringBuilder sql) {
        sql.append(" GROUP BY b.id");
        if (buildingSearchBuilder.getStaffId() != null) {
            sql.append(", assignmentbuilding.id");
        }
    }
}
