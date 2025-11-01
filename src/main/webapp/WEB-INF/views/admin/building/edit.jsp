<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 10/7/2025
  Time: 6:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<c:url var="buildingAPI" value="/api/building"/>
<html>
<head>
    <title>Thêm tòa nhà</title>
</head>
<body>
<div class="main-content" id="main-container">




    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs" id="breadcrumbs">
                <script type="text/javascript">
                    try { ace.settings.check('breadcrumbs', 'fixed') } catch (e) { }
                </script>

                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="#">Home</a>
                    </li>
                    <li class="active">Dashboard</li>
                </ul><!-- /.breadcrumb -->


            </div>

            <div class="page-content">


                <div class="page-header">
                    <h1 style="font-family: 'Times New Roman', Times, serif;">
                        Sửa hoặc thêm tòa nhà
                        <small>
                            <i class="ace-icon fa fa-angle-double-right"></i>
                            overview &amp; stats
                        </small>
                    </h1>
                </div><!-- /.page-header -->
                <div class="row">
                    <div class="col-xs-12 ">

                    </div>
                </div>

                <!-- Bảng danh sách -->
                <div class="row" style="font-family: 'Times New Roman', Times, serif;">
                   <form:form modelAttribute="buildingEdit" id="listForm" method="GET">
                       <div class="col-xs-12">
                           <form class="form-horizontal" role="form" >
                               <div class="form-group">
                                   <label class="col-xs-3">Tên tòa nhà</label>
                                   <div class="col-xs-9">
                                       <form:input class="form-control" path="name"/>
                                   </div>
                               </div>

                               <div class="form-group">
                               <label class="col-xs-3">Quận</label>
    <div class="col-xs-2">

        <form:select class="form-control" path="district">
            <form:option value="">--- Chọn Quận ---</form:option>
            <form:options items="${districts}" />
        </form:select>
    </div>
</div>


                               <div class="form-group">
                                   <label class="col-xs-3">Phường</label>
                                   <div class="col-xs-9">
                                       <form:input class="form-control" path="ward"/>

                                   </div>
                               </div>

                               <div class="form-group">
                                   <label class="col-xs-3">Đường</label>
                                   <div class="col-xs-9">
                                      <form:input class="form-control" path="street"/>
                                   </div>
                               </div>

                               <div class="form-group">
                                   <label class="col-xs-3">Kết cấu</label>
                                   <div class="col-xs-9">

                                       <form:input class="form-control" path="structure"/>
                                   </div>
                               </div>

                               <div class="form-group">
                                   <label class="col-xs-3">Số tầng hầm</label>
                                   <div class="col-xs-9">
                                       <form:input class="form-control" path="numberOfBasement"/>
                                   </div>
                               </div>

                               <div class="form-group">
                                   <label class="col-xs-3">Diện tích sàn</label>
                                   <div class="col-xs-9">
                                       <form:input class="form-control" path="floorArea"/>
                                   </div>
                               </div>

                               <div class="form-group">
                                   <label class="col-xs-3">Hướng</label>
                                   <div class="col-xs-9">
                                       <form:input class="form-control" path="direction"/>
                                   </div>
                               </div>

                               <div class="form-group">
                                   <label class="col-xs-3">Hạng</label>
                                   <div class="col-xs-9">
                                       <form:input class="form-control" path="level"/>
                                   </div>
                               </div>

                               <div class="form-group">
                                   <label class="col-xs-3">Diện tích thuê</label>
                                   <div class="col-xs-9">
                                       <form:input class="form-control" path="rentArea"/>
                                   </div>
                               </div>

                               <div class="form-group">
                                   <label class="col-xs-3">Giá thuê</label>
                                   <div class="col-xs-9">
                                       <form:input class="form-control" path="rentPrice"/>
                                   </div>
                               </div>

                               <div class="form-group">
                                   <label class="col-xs-3">Mô tả giá</label>
                                   <div class="col-xs-9">
                                       <form:input class="form-control" path="rentPriceDescription"/>
                                   </div>
                               </div>

                               <div class="form-group">
                                   <label class="col-xs-3">Phí dịch vụ</label>
                                   <div class="col-xs-9">
                                       <form:input class="form-control" path="serviceFee"/>
                                   </div>
                               </div>

                               <div class="form-group">
                                   <label class="col-xs-3">Phí ô tô</label>
                                   <div class="col-xs-9">
                                       <form:input class="form-control" path="carFee"/>
                                   </div>
                               </div>

                               <div class="form-group">
                                   <label class="col-xs-3">Phí mô tô</label>
                                   <div class="col-xs-9">
                                       <form:input class="form-control" path="motoFee"/>
                                   </div>
                               </div>

                               <div class="form-group">
                                   <label class="col-xs-3">Phí ngoài giờ</label>
                                   <div class="col-xs-9">
                                       <form:input class="form-control" path="overtimeFee"/>
                                   </div>
                               </div>

                               <div class="form-group">
                                   <label class="col-xs-3">Tiền điện</label>
                                   <div class="col-xs-9">
                                       <form:input class="form-control" path="electricityFee"/>
                                   </div>
                               </div>

                               <div class="form-group">
                                   <label class="col-xs-3">Đặt cọc </label>
                                   <div class="col-xs-9">
                                       <form:input class="form-control" path="deposit"/>
                                   </div>
                               </div>

                               <div class="form-group">
                                   <label class="col-xs-3">Thanh toán</label>
                                   <div class="col-xs-9">
                                       <form:input class="form-control" path="payment"/>
                                   </div>
                               </div>

                               <div class="form-group">
                                   <label class="col-xs-3">Thời hạn thuê</label>
                                   <div class="col-xs-9">
                                      <form:input class="form-control" path="rentTime"/>
                                   </div>
                               </div>

                               <div class="form-group">
                                   <label class="col-xs-3">Thời gian trang trí</label>
                                   <div class="col-xs-9">
                                       <form:input class="form-control" path="decorationTime"/>
                                   </div>
                               </div>

                               <div class="form-group">
                                   <label class="col-xs-3">Tên quản lý</label>
                                   <div class="col-xs-9">
                                       <form:input class="form-control" path="managerName"/>
                                   </div>
                               </div>

                               <div class="form-group">
                                   <label class="col-xs-3">SĐT quản lý</label>
                                   <div class="col-xs-9">
                                       <form:input class="form-control" path="managerPhone"/>
                                   </div>
                               </div>

                               <div class="form-group">
                                   <label class="col-xs-3">Phí môi giới</label>
                                   <div class="col-xs-9">
                                       <form:input class="form-control" path="brokerageFee"/>
                                   </div>
                               </div>


                               <div class="form-group">
                                   <label class="col-xs-3">Loại tòa nhà</label>
                                   <div class="col-xs-9">
                                      <form:checkboxes items="${typeCodes}" path="typeCode"/>
                                   </div>
                               </div>

                               <div class="form-group">
                                   <label class="col-xs-3">Ghi chú</label>
                                   <div class="col-xs-9">
                                       <form:input class="form-control" path="note"/>
                                   </div>
                               </div>
                               <div class="form-group">
                                   <label class="col-xs-3"></label>
                                   <div class="col col-xs-9">
                                       <c:if test="${not empty buildingEdit.id}">
                                       <button type="button" class="btn btn-primary" id="btnAddOrUpdateBuilding">Cập nhật tòa nhà</button>
                                       <button type="button" class="btn btn-primary" id="btnCancel">Hủy thao tác</button>
</c:if>
<c:if test="${ empty buildingEdit.id}">
                                       <button type="button" class="btn btn-primary" id="btnAddOrUpdateBuilding">Thêm tòa nhà</button>
                                       <button type="button" class="btn btn-primary" id="btnCancel">Hủy thao tác</button>
</c:if>
                                   </div>

                               </div>
                               <form:hidden path="id" id="buildingId"/>
                           </form>










                       </div><!-- /.span -->
                </div>
</form:form>



            </div><!-- /.page-content -->
        </div>

    </div><!-- /.main-content -->




</div><!-- /.main-container -->

<script>




    $('#btnAddOrUpdateBuilding').click(function () {
        var data = {};
        var typeCode = [];
        var formData = $('#listForm').serializeArray();
        $.each(formData, function (i, v) {
            if (v.name != 'typeCode') {
                data["" + v.name + ""] = v.value;
            }
            else {
                typeCode.push(v.value);
            }

        })
        data['typeCode'] = typeCode;

        if(typeCode!=''){
        addOrUpdateBuilding(data);
        }else{
         window.location.href="<c:url value="/admin/building-edit?typeCode=require"/>";
         }
        });




       function addOrUpdateBuilding(data) {
    $.ajax({
        type: "POST",
        url: "${buildingAPI}",                 // buildingAPI được định nghĩa trong JSP
        data: JSON.stringify(data),            // chuyển object thành JSON
        contentType: "application/json",       // định dạng dữ liệu gửi đi
        dataType: "json",                      // định dạng dữ liệu nhận về
        success: function (response) {
            console.log("Success:", response);
            // điều hướng về trang danh sách với message = success
            window.location.href = "<c:url value='/admin/building-list?message=success'/>";
        },
        error: function (response) {
             console.log("Error:", response.responseText);

            // console.log("Failed");
            // điều hướng về trang edit với message = error
            window.location.href = "<c:url value='/admin/building-edit?message=error'/>";
        }
    });
}

    $('#btnCancel').click(function (){
        window.location.href="/admin/building-list";
    });



</script>

</body>
</html>
