package fi.vm.sade.fakesuomifitunnistus;

import fi.utils.identification.SocialSecurityNumber;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.credential.UsernamePasswordCredential;
import org.apereo.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;

import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;

public class NationalIdentificationNumberAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {

    public NationalIdentificationNumberAuthenticationHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    @Override
    protected AuthenticationHandlerExecutionResult authenticateUsernamePasswordInternal(UsernamePasswordCredential credential, String originalPassword) throws GeneralSecurityException, PreventedException {
        String username = credential.getUsername();
        if (SocialSecurityNumber.validate(username)) {
            return createHandlerResult(credential, principalFactory.createPrincipal(username));
        }
        throw new FailedLoginException(String.format("Username '%s' is not valid national identification number", username));
    }

}
