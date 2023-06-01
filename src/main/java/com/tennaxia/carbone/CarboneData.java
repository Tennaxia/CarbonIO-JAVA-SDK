package com.tennaxia.carbone;

import java.util.Map;

import lombok.Data;

@Data
final class CarboneData {
    Object data;
    CarboneConvertTo convertTo;

    public CarboneData(Object data, String formatName, Map<String, Object> additionalFormatOptions) {
        this.data = data;
        this.convertTo = new CarboneConvertTo(formatName, additionalFormatOptions);
    }

    @Data
    private static final class CarboneConvertTo {
        private String formatName;
        private Map<String, Object> formatOptions;

        public CarboneConvertTo(String format, Map<String, Object> formatOptions) {
            this.formatName = format;
            this.formatOptions = formatOptions;
        }
    }
}
