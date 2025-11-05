package com.javaweb.controller.admin;



import com.javaweb.enums.buildingType;
import com.javaweb.enums.districtCode;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.service.BuildingService;
import com.javaweb.service.IUserService;
import com.javaweb.utils.BuildingType;
import com.javaweb.utils.DistrictCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.spel.ast.TypeCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller(value="buildingControllerOfAdmin")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;

    @Autowired
    private IUserService userService;

    /* bach123456789 */
    @RequestMapping(value = "/admin/building-list", method = RequestMethod.GET)
    public ModelAndView buildingList(@ModelAttribute BuildingSearchRequest buildingSearchRequest,HttpServletRequest request) {

        ModelAndView mav = new ModelAndView("admin/building/list");
        mav.addObject("modelSearch", buildingSearchRequest);

        List<BuildingSearchResponse> res = buildingService.findAll(buildingSearchRequest, PageRequest.of(buildingSearchRequest.getPage() - 1, buildingSearchRequest.getMaxPageItems()));

        BuildingSearchResponse resultModel = new BuildingSearchResponse();
        resultModel.setListResult(res);
        resultModel.setTotalItems(buildingService.countTotalItem(res));
        resultModel.setMaxPageItems(buildingSearchRequest.getMaxPageItems());
        resultModel.setPage(buildingSearchRequest.getPage());

        mav.addObject("buildingList", resultModel);


        mav.addObject("buildingList", resultModel);
        mav.addObject("listStaffs", userService.getStaffs());

        mav.addObject("districts", DistrictCode.type());
        mav.addObject("typeCodes", BuildingType.type());
        return mav;
    }

    /* bach123456789 */
    @RequestMapping(value = "/admin/building-edit", method = RequestMethod.GET)
    public ModelAndView buildingEdit(@ModelAttribute("buildingEdit") BuildingDTO buildingDTO, HttpServletRequest request) {

        ModelAndView mav = new ModelAndView("admin/building/edit");

        mav.addObject("districts", DistrictCode.type());
        mav.addObject("typeCodes", BuildingType.type());
        return mav;
    }
    @RequestMapping(value="/admin/building-edit-{id}",method = RequestMethod.GET)
    public ModelAndView buildingEdit(@PathVariable("id") Long id, HttpServletRequest request){
        ModelAndView mav =new ModelAndView(  "admin/building/edit");
//Xuong DB tim buidling theo id
        BuildingDTO buildingDTO = new BuildingDTO();
        mav.addObject("buildingEdit",buildingDTO);
        mav.addObject("districts", DistrictCode.type());
        mav.addObject("typeCodes", BuildingType.type());
        return mav;
    }
//    @RequestMapping(value = "/admin/building-list", method = RequestMethod.GET)
//    public ModelAndView buildingList(@ModelAttribute BuildingSearchRequest buildingSearchRequest, HttpServletRequest request) {
//
//        System.out.println(">>> [Controller] Name = " + buildingSearchRequest.getName());
//        System.out.println(">>> [Controller] District = " + buildingSearchRequest.getDistrict());
//        System.out.println(">>> [Controller] TypeCode = " + buildingSearchRequest.getTypeCode());
//        System.out.println(">>> [Controller] Page = " + buildingSearchRequest.getPage());
//        System.out.println(">>> [Controller] MaxPageItems = " + buildingSearchRequest.getMaxPageItems());
//
//        ModelAndView mav = new ModelAndView("admin/building/list");
//        mav.addObject("modelSearch", buildingSearchRequest);
//
//        List<BuildingSearchResponse> res = buildingService.findAll(buildingSearchRequest,
//                PageRequest.of(buildingSearchRequest.getPage() - 1, buildingSearchRequest.getMaxPageItems()));
//
//        System.out.println(">>> [Controller] Số kết quả trả về từ service = " + res.size());
//
//        BuildingSearchResponse resultModel = new BuildingSearchResponse();
//        resultModel.setListResult(res);
//        resultModel.setTotalItems(buildingService.countTotalItem(res));
//        resultModel.setMaxPageItems(buildingSearchRequest.getMaxPageItems());
//        resultModel.setPage(buildingSearchRequest.getPage());
//
//        mav.addObject("buildingList", resultModel);
//        mav.addObject("listStaffs", userService.getStaffs());
//        mav.addObject("districts", DistrictCode.type());
//        mav.addObject("typeCodes", BuildingType.type());
//
//        return mav;
//    }
}
