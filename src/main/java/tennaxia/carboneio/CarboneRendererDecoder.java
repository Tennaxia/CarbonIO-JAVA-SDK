package tennaxia.carboneio;

import java.io.IOException;
import java.lang.reflect.Type;

import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import feign.gson.GsonDecoder;

class CarboneRendererDecoder implements Decoder {
    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        if (type.equals(CarboneFileResponse.class)) {
            return new CarboneFileResponse(response.body().asInputStream().readAllBytes());
        }
        return new GsonDecoder().decode(response, type);
    }
}
