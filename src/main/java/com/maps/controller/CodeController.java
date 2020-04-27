package com.maps.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maps.model.Code;
import com.maps.service.CodeService;
import com.maps.utils.response.HttpResultCode;
import com.maps.utils.response.ResponseObject;

@Controller
@RequestMapping("/code")
public class CodeController {

    @Resource(name = "com.maps.service.impl.CodeService")
    private CodeService codeService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ResponseObject<Code> selectCode(@RequestParam("cdId") String cdId,
                                    @RequestParam("cdDtlId") String cdDtlId) {
        ResponseObject<Code> responseObject = new ResponseObject<Code>();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("cdId", cdId);
        paramMap.put("cdDtlId", cdDtlId);

        responseObject.setData(codeService.selectCode(paramMap));
        responseObject.setResultCode(HttpResultCode.PRODUCT_SUCCESS);

        return responseObject;
    }

}
