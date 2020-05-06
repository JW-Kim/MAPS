package com.maps.service;

import com.maps.model.Company;

import java.util.List;
import java.util.Map;

public interface CompanyService {
    public List<Company> selectCompany(Map<String, Object> paramMap);

    public int deleteCompany(Map<String, Object> paramMap);

    public int insertCompany(Map<String, Object> paramMap);
}
