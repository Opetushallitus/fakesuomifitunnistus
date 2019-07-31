package fi.vm.sade.fakesuomifitunnistus;

import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalFactory;

import java.util.Map;

public class NationalIdentificationNumberPrincipalFactory implements PrincipalFactory {

    private static final String ATTRIBUTE_NATIONAL_IDENTIFICATION_NUMBER = "nationalIdentificationNumber";

    private final PrincipalFactory principalFactory = new DefaultPrincipalFactory();

    @Override
    public Principal createPrincipal(String id, Map<String, Object> attributes) {
        attributes.put(ATTRIBUTE_NATIONAL_IDENTIFICATION_NUMBER, id);

        return principalFactory.createPrincipal(id, attributes);
    }

}
