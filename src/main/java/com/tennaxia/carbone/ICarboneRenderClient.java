package com.tennaxia.carbone;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface ICarboneRenderClient {
    @RequestLine("POST /{templateId}")
    @Headers("Content-Type: application/json")
    CarboneResponse renderReport(CarboneData carboneData, @Param("templateId") String templateId) throws CarboneException;

    @RequestLine("GET /{renderId}")
    CarboneFileResponse getReport(@Param("renderId") String renderId) throws CarboneException;
}
