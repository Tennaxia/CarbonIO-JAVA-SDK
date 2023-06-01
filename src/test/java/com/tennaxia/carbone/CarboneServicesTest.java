package com.tennaxia.carbone;

import static com.tennaxia.carbone.CarboneServicesFactory.CARBONE_SERVICES_FACTORY_INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;

import org.junit.Test;

public class CarboneServicesTest {

    @Test
    public void doitRetournerLIdDuFichierTemplate() throws CarboneException {
        //GIVEN
        var carboneServices = CARBONE_SERVICES_FACTORY_INSTANCE.create(
            new MockCarboneTemplateClient("templateId"),
            null
        );

        //WHEN
        Optional<String> id = carboneServices.addTemplate("".getBytes());

        //THEN
        assertThat(id).hasValue("templateId");
    }

    @Test
    public void doitRetournerLIdDuRapportATelecharger() throws CarboneException {
        //GIVEN
        var carboneServices = CARBONE_SERVICES_FACTORY_INSTANCE.create(
            null,
            new MockCarboneRenderClient("renderId", "reportContent")
        );

        //WHEN
        String id = carboneServices.renderReport(new Object(), "templateId");

        //THEN
        assertThat(id).isEqualTo("renderId");
    }

    @Test
    public void doitRenvoyerLeMessageDErreurDUneCarboneException() throws CarboneException {
        //GIVEN
        var carboneServices = CARBONE_SERVICES_FACTORY_INSTANCE.create(
            null,
            new MockCarboneRenderClient("renderId", "reportContent")
        );

        //WHEN THEN
        assertThatThrownBy(() -> carboneServices.renderReport(new Object(), "mauvaisTemplateId"))
            .isInstanceOf(CarboneException.class);
    }

    private static class MockCarboneTemplateClient implements ICarboneTemplateClient {

        private final String templateId;

        private MockCarboneTemplateClient(String templateId) {
            this.templateId = templateId;
        }

        @Override
        public CarboneResponse addTemplate(byte[] templateFile) throws CarboneException {
            return CarboneResponse.builder()
                .success(true)
                .data(CarboneResponse.CarboneResponseData.builder().templateId(this.templateId).build())
                .build();
        }

        @Override
        public CarboneResponse deleteTemplate(String templateId) throws CarboneException {
            return null;
        }
    }

    private static class MockCarboneRenderClient implements ICarboneRenderClient {

        private final String renderId;
        private final byte[] reportContent;

        private MockCarboneRenderClient(String renderId, String reportContent) {
            this.renderId = renderId;
            this.reportContent = reportContent.getBytes();
        }

        @Override
        public CarboneResponse renderReport(CarboneData carboneData, String templateId) throws CarboneException {
            if (templateId.equals("templateId")) {
                return CarboneResponse.builder()
                    .success(true)
                    .data(CarboneResponse.CarboneResponseData.builder().renderId(renderId).build())
                    .build();
            } else {
                throw new CarboneException("template not found");
            }
        }

        @Override
        public CarboneFileResponse getReport(String renderId) throws CarboneException {
            if (renderId.equals("renderId")) {
                return new CarboneFileResponse(reportContent);
            } else {
                throw new CarboneException("wrong render Id");
            }
        }
    }
}