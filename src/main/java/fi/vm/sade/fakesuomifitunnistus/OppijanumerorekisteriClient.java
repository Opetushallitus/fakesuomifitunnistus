package fi.vm.sade.fakesuomifitunnistus;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.vm.sade.javautils.http.OphHttpClient;
import fi.vm.sade.javautils.http.OphHttpRequest;
import fi.vm.sade.properties.OphProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class OppijanumerorekisteriClient {

    private final OphHttpClient oppijanumerorekisteriHttpClient;
    private final OphProperties properties;
    private final ObjectMapper objectMapper;

    public OppijanumerorekisteriClient(@Qualifier("oppijanumerorekisteriHttpClient") OphHttpClient oppijanumerorekisteriHttpClient, OphProperties properties) {
        this.oppijanumerorekisteriHttpClient = oppijanumerorekisteriHttpClient;
        this.properties = properties;
        this.objectMapper = new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    private <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<PersonDto> getByNationalIdentificationNumber(String nationalIdentificationNumber) {
        String url = properties.url("oppijanumerorekisteri-service.henkilo.byHetu", nationalIdentificationNumber);
        OphHttpRequest request = OphHttpRequest.Builder.get(url).build();
        return oppijanumerorekisteriHttpClient.<PersonDto>execute(request)
                .expectedStatus(200)
                .mapWith(json -> fromJson(json, PersonDto.class));
    }

}
