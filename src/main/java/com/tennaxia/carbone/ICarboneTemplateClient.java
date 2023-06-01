package com.tennaxia.carbone;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface ICarboneTemplateClient {
    @RequestLine("POST")
    @Headers("Content-Type: multipart/form-data")
    CarboneResponse addTemplate(@Param("template") byte[] templateFile) throws CarboneException;

    @RequestLine("DELETE /{templateId}")
    CarboneResponse deleteTemplate(@Param("templateId") String templateId) throws CarboneException;
}
