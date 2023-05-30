package tennaxia.carboneio;

import static java.util.Optional.empty;

import java.util.Optional;

import feign.form.FormEncoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;

public final class CarboneIOServices {

    private static final String CARBONE_URI = "https://render.carbone.io";

    private final CarboneTemplateClient carboneTemplateClient;
    private final CarboneRenderClient carboneRenderClient;

    /**
     * Initialise Carbone.io's services
     * @param token the product key of your Carbone.io subscription
     * @param version version of Carbone.io
     */
    public CarboneIOServices(String token, String version) {
        this.carboneTemplateClient = CarboneFeignClientBuilder.createCarboneFeignClient(token, version)
            .encoder(new FormEncoder())
            .decoder(new GsonDecoder())
            .logger(new Slf4jLogger(CarboneTemplateClient.class))
            .target(CarboneTemplateClient.class, CARBONE_URI + "/template");

        this.carboneRenderClient = CarboneFeignClientBuilder.createCarboneFeignClient(token, version)
            .encoder(new GsonEncoder())
            .decoder(new CarboneRendererDecoder())
            .logger(new Slf4jLogger(CarboneRenderClient.class))
            .target(CarboneRenderClient.class, CARBONE_URI + "/render");
    }

    /**
     * Upload template to Carbone.io
     * @param templateFile file's content in byte[]
     * @return an {@link Optional} containing the templateId to use for render if template is successfully sent
     * @throws CarboneException contain CarboneResponse format with API error code and error messages
     */
    public Optional<String> addTemplate(byte[] templateFile) throws CarboneException {
        CarboneResponse carboneResponse = carboneTemplateClient.addTemplate(templateFile);
        if (carboneResponse.isSuccess()) {
            return Optional.of(carboneResponse.getData().getTemplateId());
        } else {
            return empty();
        }
    }

    /**
     * Delete uploaded template
     * @param templateId id returned by addTemplate() method
     * @throws CarboneException contain CarboneResponse format with API error code and error messages
     */
    public void deleteTemplate(String templateId) throws CarboneException {
        carboneTemplateClient.deleteTemplate(templateId);
    }

    /**
     * Render report with default option (pdf file format and UseLosslessCompression disabled)
     * @param renderData Json object with data set to replace in template
     * @param templateId id returned by addTemplate() method
     * @return id of rendered report
     * @throws CarboneException contain CarboneResponse format with API error code and error messages
     */
    public String renderReport(Object renderData, String templateId) throws CarboneException {
        return renderReport(renderData, templateId, new CarboneFormatOptions(false));
    }

    /**
     * Render report with custom options
     * @param renderData Json object with data set to replace in template
     * @param templateId id returned by addTemplate() method
     * @param additionalOptions extendable object to set additional options
     * @return id of rendered report
     * @throws CarboneException contain CarboneResponse format with API error code and error messages
     */
    public String renderReport(Object renderData, String templateId, CarboneFormatOptions additionalOptions) throws CarboneException {
        CarboneResponse carboneResponse = carboneRenderClient
            .renderReport(new CarboneData(renderData, "pdf", additionalOptions), templateId);
        return carboneResponse.getData().getRenderId();
    }

    /**
     * Download rendered report
     * @param renderId id returned by renderReport()
     * @return report content in byte[]
     * @throws CarboneException contain CarboneResponse format with API error code and error messages
     */
    public byte[] getReport(String renderId) throws CarboneException {
        CarboneFileResponse response = carboneRenderClient.getReport(renderId);
        return response.getFileContent();
    }
}
