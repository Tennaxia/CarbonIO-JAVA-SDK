package tennaxia.carboneio;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class CarboneResponse {

    private boolean success;
    private String error;
    private String code;
    private CarboneResponseData data;

    @Data
    @Builder
    protected static class CarboneResponseData {
        String templateId;
        String renderId;
    }
}

