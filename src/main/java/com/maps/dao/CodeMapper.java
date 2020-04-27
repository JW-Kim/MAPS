package com.maps.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maps.model.Code;

@Repository("com.maps.dao.CodeMapper")
public interface CodeMapper {
    public Code selectCode(Map<String, Object> paramMap);
}
