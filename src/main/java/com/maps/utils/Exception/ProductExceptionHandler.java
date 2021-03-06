package com.maps.utils.Exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maps.utils.response.HttpResultCode;
import com.maps.utils.response.ResponseObject;
import com.maps.utils.response.ResultCode;


@ControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(ProductRuntimeException.class)
    public @ResponseBody
    Object handleProductRuntimeException(ProductRuntimeException e, HttpServletRequest request, HttpServletResponse response) {
        ResultCode resResultCode = e.getResultCode();
        String resMessage = e.getMessage();
        String resData = null;
        if (resResultCode != null && resMessage != null
                && !resMessage.equals(resResultCode.toString())) {
            e.setMessage(resResultCode.toString().concat(" :: ").concat(resMessage));
            resData = resMessage;
            resMessage = resResultCode.toString();
        }

        ResponseObject<Object> resObj = new ResponseObject<Object>();
        if (resResultCode != null) {
            resObj.setResultCode(resResultCode);
            response.setStatus(e.getResultCode().getHttpStatus());
        } else {
            resObj.setResultCode(HttpResultCode.PRODUCT_INTERNAL_SERVER_EXCEPTION);
            response.setStatus(HttpResultCode.PRODUCT_INTERNAL_SERVER_EXCEPTION.getHttpStatus());
        }
        resObj.setMessage(resMessage);
        resObj.setData(resData);
        return resObj;
    }

    @ExceptionHandler(Throwable.class)
    public @ResponseBody
    Object handleInternalServerException(Exception e, HttpServletRequest request, HttpServletResponse response) {

        ResponseObject<Object> resObj = new ResponseObject<Object>();
        resObj.setResultCode(HttpResultCode.PRODUCT_INTERNAL_SERVER_EXCEPTION);

        if (e instanceof MissingServletRequestParameterException) {
            resObj.setResultCode(HttpResultCode.PRODUCT_INVALID_PARAMETER);
            response.setStatus(HttpResultCode.PRODUCT_INTERNAL_SERVER_EXCEPTION.getHttpStatus());
            resObj.setMessage(e.getMessage());
        }
        return resObj;
    }
}
