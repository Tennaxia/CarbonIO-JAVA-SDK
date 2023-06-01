package com.tennaxia.carbone;

import static java.util.Optional.empty;

import java.util.Map;
import java.util.Optional;

final class CarboneServices implements ICarboneServices {

    private static final Map<String, Object> USE_LOSSLESS_COMPRESSION = Map.of("UseLosslessCompression", false);

    private final ICarboneTemplateClient carboneTemplateClient;
    private final ICarboneRenderClient carboneRenderClient;

    public CarboneServices(ICarboneTemplateClient carboneTemplateClient, ICarboneRenderClient carboneRenderClient) {
        this.carboneTemplateClient = carboneTemplateClient;
        this.carboneRenderClient = carboneRenderClient;
    }

    @Override
    public Optional<String> addTemplate(byte[] templateFile) throws CarboneException {
        CarboneResponse carboneResponse = carboneTemplateClient.addTemplate(templateFile);
        if (carboneResponse.isSuccess()) {
            return Optional.of(carboneResponse.getData().getTemplateId());
        } else {
            return empty();
        }
    }

    @Override
    public void deleteTemplate(String templateId) throws CarboneException {
        carboneTemplateClient.deleteTemplate(templateId);
    }

    @Override
    public String renderReport(Object renderData, String templateId) throws CarboneException {
        return renderReport(renderData, templateId, USE_LOSSLESS_COMPRESSION);
    }

    @Override
    public String renderReport(Object renderData, String templateId, Map<String, Object> additionalOptions) throws CarboneException {
        CarboneResponse carboneResponse = carboneRenderClient
            .renderReport(new CarboneData(renderData, "pdf", additionalOptions), templateId);
        return carboneResponse.getData().getRenderId();
    }

    @Override
    public byte[] getReport(String renderId) throws CarboneException {
        CarboneFileResponse response = carboneRenderClient.getReport(renderId);
        return response.getFileContent();
    }
}
