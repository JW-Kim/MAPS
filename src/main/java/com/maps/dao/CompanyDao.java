package com.maps.dao;

import com.maps.model.Code;
import com.maps.model.Company;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("com.maps.dao.CompanyDao")
public interface CompanyDao {
    public List<Company> selectCompany(Map<String, Object> paramMap);

    public int deleteCompany(Map<String, Object> paramMap);

    public int insertCompany(Map<String, Object> paramMap);
}
