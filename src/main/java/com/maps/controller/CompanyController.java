package com.maps.controller;

import com.maps.model.Code;
import com.maps.model.Company;
import com.maps.service.CodeService;
import com.maps.service.CompanyService;
import com.maps.utils.response.GridResponseObject;
import com.maps.utils.response.HttpResultCode;
import com.maps.utils.response.ResponseObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Resource(name = "com.maps.service.impl.CompanyService")
    private CompanyService companyService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    GridResponseObject<Company> selectAllCompany() {
        GridResponseObject<Company> gridResponseObject = new GridResponseObject();

        gridResponseObject.setData(companyService.selectAllCompany());
        gridResponseObject.setResultCode(HttpResultCode.PRODUCT_SUCCESS);

        return gridResponseObject;
    }

}
