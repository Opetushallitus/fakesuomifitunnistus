package fi.vm.sade.fakesuomifitunnistus;

import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalFactory;

import java.util.Map;

public class NationalIdentificationNumberPrincipalFactory implements PrincipalFactory {

    private static final String ATTRIBUTE_NATIONAL_IDENTIFICATION_NUMBER = "nationalIdentificationNumber";
    private static final String ATTRIBUTE_COMMON_NAME = "cn";
    private static final String ATTRIBUTE_DISPLAY_NAME = "displayName";
    private static final String ATTRIBUTE_GIVEN_NAME = "givenName";
    private static final String ATTRIBUTE_SURNAME = "sn";

    private final PrincipalFactory principalFactory = new DefaultPrincipalFactory();
    private final OppijanumerorekisteriClient oppijanumerorekisteriClient;
    private final PersonProvider randomPersonProvider;

    public NationalIdentificationNumberPrincipalFactory(OppijanumerorekisteriClient oppijanumerorekisteriClient,
                                                        PersonProvider randomPersonProvider) {
        this.oppijanumerorekisteriClient = oppijanumerorekisteriClient;
        this.randomPersonProvider = randomPersonProvider;
    }

    @Override
    public Principal createPrincipal(String id, Map<String, Object> attributes) {
        attributes.put(ATTRIBUTE_NATIONAL_IDENTIFICATION_NUMBER, id);

        PersonDto person = oppijanumerorekisteriClient
                .getByNationalIdentificationNumber(id)
                .orElseGet(randomPersonProvider);
        attributes.put(ATTRIBUTE_COMMON_NAME, person.getCommonName());
        attributes.put(ATTRIBUTE_DISPLAY_NAME, person.getDisplayName());
        attributes.put(ATTRIBUTE_GIVEN_NAME, person.getGivenName());
        attributes.put(ATTRIBUTE_SURNAME, person.surname);

        return principalFactory.createPrincipal(id, attributes);
    }

}
