package com.maps.service;

import java.util.Map;

import com.maps.model.Code;

public interface CodeService {
    public Code selectCode(Map<String, Object> paramMap);
}
