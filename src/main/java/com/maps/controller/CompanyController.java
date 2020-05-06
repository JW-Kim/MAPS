package com.maps.controller;

import com.maps.model.Code;
import com.maps.model.Company;
import com.maps.model.MapType;
import com.maps.service.CodeService;
import com.maps.service.CompanyService;
import com.maps.utils.IdGen;
import com.maps.utils.response.GridResponseObject;
import com.maps.utils.response.HttpResultCode;
import com.maps.utils.response.ResponseObject;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.http.auth.AuthScope;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/company")
public class CompanyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);

    @Resource(name = "com.maps.service.impl.CompanyService")
    private CompanyService companyService;

    @Resource
    RestTemplate restTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    GridResponseObject<Company> selectCompany(@RequestParam("latitude") String latitude,
                                              @RequestParam("longitude") String longitude,
                                              @RequestParam("distance") String distance) {
        GridResponseObject<Company> gridResponseObject = new GridResponseObject();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("latitude", latitude);
        paramMap.put("longitude", longitude);
        paramMap.put("distance", distance);

        gridResponseObject.setData(companyService.selectCompany(paramMap));
        gridResponseObject.setResultCode(HttpResultCode.PRODUCT_SUCCESS);

        return gridResponseObject;
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody ResponseObject<Boolean> createCompany(@RequestParam("mapType") String mapType) throws Exception{
        ResponseObject<Boolean> responseObject = new ResponseObject<Boolean>();

        int pIndex = 1;
        int pSize = 1000;

        // Delete map type
        Map<String, Object> deleteParamMap = new HashMap<>();
        deleteParamMap.put("mapType", MapType.valueOf(mapType));
        companyService.deleteCompany(deleteParamMap);

        while (true) {
            HttpHeaders headers =  new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

            HttpEntity<String> entity = new HttpEntity<String>(null, headers);
            Map<String, Object> paramMap = new HashMap();
            paramMap.put("KEY", "6bb035c5849e42cab88e77e16482aeae");
            paramMap.put("Type", "json");
            paramMap.put("pIndex", pIndex);
            paramMap.put("pSize", pSize);

            ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());

            ResponseEntity<String> result = new
                    RestTemplate(requestFactory).exchange("https://openapi.gg.go.kr/RegionMnyFacltStus?KEY={KEY}&Type={Type}&pIndex={pIndex}&pSize={pSize}", HttpMethod.GET, entity, String.class, paramMap);
//        LOGGER.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> : {}", result);
//        LOGGER.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> : {}", result.getStatusCode());
//        LOGGER.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> : {}", result.getBody());

            if(result.getStatusCode() == HttpStatus.OK) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    Map<String, Object> map = mapper.readValue(result.getBody(), Map.class);

                    if(map.get("RegionMnyFacltStus") == null){
                        break;
                    }
                    List<Object> list = (List<Object>) map.get("RegionMnyFacltStus");
                    Map<String, Object> rowObjt = (Map<String, Object>) list.get(1);
                    List<Map<String, Object>> tempCompanyList = (List<Map<String, Object>>) rowObjt.get("row");
                    List<Company> companyList = new ArrayList<>();

                    // 1. data Parsing
                    for(int i=0 ; i<tempCompanyList.size(); i++) {
                        if(MapType.G_MONEY.getCode().equals(mapType)){
                            Company company = new Company();
                            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");

                            company.setCompanyId(IdGen.getNextId());
                            company.setMapType(MapType.valueOf(mapType)+"");
                            company.setCompanyNm(tempCompanyList.get(i).get("CMPNM_NM")+"");                //상호명
                            company.setCityCd(tempCompanyList.get(i).get("SIGUN_CD")+"");                   //시군코드
                            company.setCityNm(tempCompanyList.get(i).get("SIGUN_NM")+"");                   //시군명
                            company.setIndustryCd(tempCompanyList.get(i).get("INDUTYPE_CD")+"");            //업종코드
                            company.setIndustryNm(tempCompanyList.get(i).get("INDUTYPE_NM")+"");            //업종명(종목명)
                            company.setBizCondNm(tempCompanyList.get(i).get("BIZCOND_NM")+"");               //업태명
                            company.setRoadAddress(tempCompanyList.get(i).get("REFINE_ROADNM_ADDR")+"");    //소재지도로명주소
                            company.setAddress(tempCompanyList.get(i).get("REFINE_LOTNO_ADDR")+"");         //소재지지번주소
                            company.setTelNo(tempCompanyList.get(i).get("TELNO")+"");                         //전화번호
                            company.setRegionMoneyNm(tempCompanyList.get(i).get("REGION_MNY_NM")+"");        //사용가능한지역화폐명
                            company.setZipCd(tempCompanyList.get(i).get("REFINE_ZIP_CD")+"");                //우편번호
                            company.setLatitude(tempCompanyList.get(i).get("REFINE_WGS84_LAT")+"");          //위도
                            company.setLongitude(tempCompanyList.get(i).get("REFINE_WGS84_LOGT")+"");        //경도
                            company.setDataStandDtm(transFormat.parse(tempCompanyList.get(i).get("DATA_STD_DE")+""));            //데이터기준일자
                            companyList.add(company);
                        }
                    }

                    // 2. Insert map type
                    Map<String, Object> insertParamMap = new HashMap<>();
                    insertParamMap.put("companyList", companyList);
                    companyService.insertCompany(insertParamMap);

                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }

                pIndex++;

            } else {
                break;
            }
        }

        responseObject.setData(true);
        return responseObject;
    }

}
