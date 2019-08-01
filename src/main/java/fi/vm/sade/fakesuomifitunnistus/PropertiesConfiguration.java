package fi.vm.sade.fakesuomifitunnistus;

import fi.vm.sade.properties.OphProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class PropertiesConfiguration {

    @Bean
    public OphProperties properties(Environment environment) {
        OphProperties properties = new OphProperties("/fake-suomifi-tunnistus-oph.properties");
        properties.addDefault("url-virkailija", environment.getRequiredProperty("cas.custom.properties.url-virkailija"));
        properties.addDefault("service-username", environment.getRequiredProperty("cas.custom.properties.service-username"));
        properties.addDefault("service-password", environment.getRequiredProperty("cas.custom.properties.service-password"));
        return properties;
    }

}
