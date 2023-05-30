package tennaxia.carboneio;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

interface CarboneRenderClient {

    @RequestLine("POST /{templateId}")
    @Headers("Content-Type: application/json")
    CarboneResponse renderReport(CarboneData carboneData, @Param("templateId") String templateId) throws CarboneException;

    @RequestLine("GET /{renderId}")
    CarboneFileResponse getReport(@Param("renderId") String renderId) throws CarboneException;
}
