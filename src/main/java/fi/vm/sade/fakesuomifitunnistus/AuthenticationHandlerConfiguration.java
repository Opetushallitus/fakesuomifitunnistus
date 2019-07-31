package fi.vm.sade.fakesuomifitunnistus;

import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationHandlerConfiguration implements AuthenticationEventExecutionPlanConfigurer {

    private final ServicesManager servicesManager;

    public AuthenticationHandlerConfiguration(ServicesManager servicesManager) {
        this.servicesManager = servicesManager;
    }

    @Override
    public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan plan) {
        PrincipalFactory principalFactory = new NationalIdentificationNumberPrincipalFactory();
        NationalIdentificationNumberAuthenticationHandler authenticationHandler = new NationalIdentificationNumberAuthenticationHandler(
                "nationalIdentificationNumberAuthenticationHandler", servicesManager, principalFactory, 1);
        plan.registerAuthenticationHandler(authenticationHandler);
    }

}
