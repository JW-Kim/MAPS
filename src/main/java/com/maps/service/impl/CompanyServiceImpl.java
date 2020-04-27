package com.maps.service.impl;

import com.maps.dao.CompanyDao;
import com.maps.model.Company;
import com.maps.service.CompanyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("com.maps.service.impl.CompanyService")
public class CompanyServiceImpl implements CompanyService {

    @Resource(name = "com.maps.dao.CompanyDao")
    private CompanyDao companyDao;

    public List<Company> selectAllCompany() {
        return companyDao.selectAllCompany();
    }
}
