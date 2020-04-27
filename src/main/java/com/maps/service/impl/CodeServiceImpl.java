package com.maps.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.maps.dao.CodeMapper;
import com.maps.model.Code;
import com.maps.service.CodeService;

@Service("com.maps.service.impl.CodeService")
public class CodeServiceImpl implements CodeService {

    @Resource(name = "com.maps.dao.CodeMapper")
    private CodeMapper codeMapper;

    public Code selectCode(Map<String, Object> paramMap) {
        return codeMapper.selectCode(paramMap);
    }
}
