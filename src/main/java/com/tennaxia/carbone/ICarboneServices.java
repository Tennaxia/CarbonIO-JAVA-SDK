package com.tennaxia.carbone;

import java.util.Map;
import java.util.Optional;

public interface ICarboneServices {

    /**
     * Upload template to Carbone
     * @param templateFile file's content in byte[]
     * @return an {@link Optional} containing the templateId to use for render if template is successfully sent
     * @throws CarboneException contain CarboneResponse format with API error code and error messages
     */
    Optional<String> addTemplate(byte[] templateFile) throws CarboneException;

    /**
     * Delete uploaded template
     * @param templateId id returned by addTemplate() method
     * @throws CarboneException contain CarboneResponse format with API error code and error messages
     */
    void deleteTemplate(String templateId) throws CarboneException;

    /**
     * Render report with default option (pdf file format and UseLosslessCompression disabled)
     * @param renderData Json object with data set to replace in template
     * @param templateId id returned by addTemplate() method
     * @return id of rendered report
     * @throws CarboneException contain CarboneResponse format with API error code and error messages
     */
    String renderReport(Object renderData, String templateId) throws CarboneException;

    /**
     * Render report with custom options
     * @param renderData Json object with data set to replace in template
     * @param templateId id returned by addTemplate() method
     * @param additionalOptions map of additional pdf options
     * @return id of rendered report
     * @throws CarboneException contain CarboneResponse format with API error code and error messages
     */
    String renderReport(Object renderData, String templateId, Map<String, Object> additionalOptions) throws CarboneException;

    /**
     * Download rendered report
     * @param renderId id returned by renderReport()
     * @return report content in byte[]
     * @throws CarboneException contain CarboneResponse format with API error code and error messages
     */
    byte[] getReport(String renderId) throws CarboneException;
}
