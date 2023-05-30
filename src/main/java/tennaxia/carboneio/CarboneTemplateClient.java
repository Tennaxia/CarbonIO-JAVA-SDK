package tennaxia.carboneio;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

interface CarboneTemplateClient {

    @RequestLine("POST")
    @Headers("Content-Type: multipart/form-data")
    CarboneResponse addTemplate(@Param("template") byte[] templateFile) throws CarboneException;

    @RequestLine("DELETE /{templateId}")
    CarboneResponse deleteTemplate(@Param("templateId") String templateId) throws CarboneException;
}
