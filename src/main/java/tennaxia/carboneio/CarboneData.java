package tennaxia.carboneio;

import lombok.Data;

@Data
final class CarboneData {
    Object data;
    CarboneConvertTo convertTo;

    public CarboneData(Object data, String formatName, CarboneFormatOptions additionalFormatOptions) {
        this.data = data;
        this.convertTo = new CarboneConvertTo(formatName, additionalFormatOptions);
    }

    @Data
    private static final class CarboneConvertTo {
        private String formatName;
        private CarboneFormatOptions formatOptions;

        public CarboneConvertTo(String format, CarboneFormatOptions formatOptions) {
            this.formatName = format;
            this.formatOptions = formatOptions;
        }
    }
}
