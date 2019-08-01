package fi.vm.sade.fakesuomifitunnistus;

import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationHandlerConfiguration implements AuthenticationEventExecutionPlanConfigurer {

    private final ServicesManager servicesManager;
    private final OppijanumerorekisteriClient oppijanumerorekisteriClient;
    private final PersonProvider personProvider;

    public AuthenticationHandlerConfiguration(ServicesManager servicesManager,
                                              OppijanumerorekisteriClient oppijanumerorekisteriClient,
                                              PersonProvider personProvider) {
        this.servicesManager = servicesManager;
        this.oppijanumerorekisteriClient = oppijanumerorekisteriClient;
        this.personProvider = personProvider;
    }

    @Override
    public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan plan) {
        PrincipalFactory principalFactory = new NationalIdentificationNumberPrincipalFactory(oppijanumerorekisteriClient, personProvider);
        NationalIdentificationNumberAuthenticationHandler authenticationHandler = new NationalIdentificationNumberAuthenticationHandler(
                "nationalIdentificationNumberAuthenticationHandler", servicesManager, principalFactory, 1);
        plan.registerAuthenticationHandler(authenticationHandler);
    }

}
