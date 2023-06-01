package com.tennaxia.carbone;

import feign.form.FormEncoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;

public enum CarboneServicesFactory {

    CARBONE_SERVICES_FACTORY_INSTANCE;

    private static final String CARBONE_URI = "https://render.carbone.io";

    public ICarboneServices create(String token, String version) {

        var carboneTemplateClient = CarboneFeignClientBuilder.createCarboneFeignClient(token, version)
            .encoder(new FormEncoder())
            .decoder(new GsonDecoder())
            .logger(new Slf4jLogger(CarboneTemplateClient.class))
            .target(CarboneTemplateClient.class, CARBONE_URI + "/template");

        var carboneRenderClient = CarboneFeignClientBuilder.createCarboneFeignClient(token, version)
            .encoder(new GsonEncoder())
            .decoder(new CarboneRendererDecoder())
            .logger(new Slf4jLogger(CarboneRenderClient.class))
            .target(CarboneRenderClient.class, CARBONE_URI + "/render");

        return create(carboneTemplateClient, carboneRenderClient);
    }


    public ICarboneServices create(ICarboneTemplateClient carboneTemplateClient, ICarboneRenderClient carboneRenderClient) {
        return new CarboneServices(carboneTemplateClient, carboneRenderClient);
    }
}
