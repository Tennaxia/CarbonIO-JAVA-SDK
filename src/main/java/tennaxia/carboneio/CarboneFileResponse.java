package tennaxia.carboneio;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
class CarboneFileResponse {

    private final byte[] fileContent;
}

