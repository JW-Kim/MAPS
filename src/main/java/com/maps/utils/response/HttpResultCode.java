//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.maps.utils.response;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public enum HttpResultCode implements ResultCode {
    PRODUCT_SUCCESS(200, 200),
    PRODUCT_INVALID_PARAMETER(400, 400),
    PRODUCT_FORBIDDEN(403, 403),
    PRODUCT_NOT_FOUND(404, 404),
    PRODUCT_REQUEST_TIMEOUT(408, 408),
    PRODUCT_CONFLICT(409, 409),
    PRODUCT_INTERNAL_SERVER_EXCEPTION(500, 500),
    PRODUCT_REMOTE_SERVER_EXCEPTION(503, 503);

    private int httpStatusCode;
    private int code;
    private static final Map<Integer, HttpResultCode> LOOKUP = new HashMap(HashMapUtil.getCapacity(200));

    private HttpResultCode(int httpStatusCode, int code) {
        this.httpStatusCode = httpStatusCode;
        this.code = code;
    }

    public int getHttpStatus() {
        return this.httpStatusCode;
    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return super.name();
    }

    public static HttpResultCode get(int code) {
        return (HttpResultCode) LOOKUP.get(code);
    }

    static {
        Iterator var0 = EnumSet.allOf(HttpResultCode.class).iterator();

        while (var0.hasNext()) {
            HttpResultCode type = (HttpResultCode) var0.next();
            LOOKUP.put(type.getCode(), type);
        }

    }
}
