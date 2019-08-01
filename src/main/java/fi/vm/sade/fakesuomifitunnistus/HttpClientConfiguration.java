package fi.vm.sade.fakesuomifitunnistus;

import fi.vm.sade.javautils.http.OphHttpClient;
import fi.vm.sade.javautils.http.auth.CasAuthenticator;
import fi.vm.sade.properties.OphProperties;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.message.BasicHeader;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.support.Beans;
import org.apereo.cas.util.http.HttpClient;
import org.apereo.cas.util.http.SimpleHttpClientFactoryBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.HostnameVerifier;

import static java.util.Collections.singletonList;

@Configuration
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class HttpClientConfiguration {

    private static final String CALLER_ID = "1.2.246.562.10.00000000001.fake-suomifi-tunnistus";

    private final CasConfigurationProperties casProperties;
    private final SSLConnectionSocketFactory trustStoreSslSocketFactory;
    private final HostnameVerifier hostnameVerifier;

    public HttpClientConfiguration(CasConfigurationProperties casProperties,
                                   SSLConnectionSocketFactory trustStoreSslSocketFactory,
                                   HostnameVerifier hostnameVerifier) {
        this.casProperties = casProperties;
        this.trustStoreSslSocketFactory = trustStoreSslSocketFactory;
        this.hostnameVerifier = hostnameVerifier;
    }

    @Bean
    public OphHttpClient oppijanumerorekisteriHttpClient(OphProperties properties) {
        CasAuthenticator authenticator = new CasAuthenticator.Builder()
                .webCasUrl(properties.url("cas.base"))
                .username(properties.require("service-username"))
                .password(properties.require("service-password"))
                .casServiceUrl(properties.url("oppijanumerorekisteri-service.login"))
                .build();
        return new OphHttpClient.Builder(CALLER_ID).authenticator(authenticator).build();
    }

    // override cas httpclient to include caller-id header
    @Bean
    public HttpClient noRedirectHttpClient() {
        return getHttpClient(false);
    }

    // override cas httpclient to include caller-id header
    @Bean
    public HttpClient supportsTrustStoreSslSocketFactoryHttpClient() {
        return getHttpClient(true);
    }

    // copy from CasCoreHttpConfiguration
    private HttpClient getHttpClient(final boolean redirectEnabled) {
        var c = buildHttpClientFactoryBean();
        c.setRedirectsEnabled(redirectEnabled);
        c.setCircularRedirectsAllowed(redirectEnabled);
        c.setSslSocketFactory(trustStoreSslSocketFactory);
        c.setHostnameVerifier(hostnameVerifier);
        return c.getObject();
    }

    // copy from CasCoreHttpConfiguration (added caller-id header)
    private SimpleHttpClientFactoryBean buildHttpClientFactoryBean() {
        var c = new SimpleHttpClientFactoryBean.DefaultHttpClient();
        var httpClient = casProperties.getHttpClient();
        c.setConnectionTimeout(Beans.newDuration(httpClient.getConnectionTimeout()).toMillis());
        c.setReadTimeout((int) Beans.newDuration(httpClient.getReadTimeout()).toMillis());
        c.setDefaultHeaders(singletonList(new BasicHeader("Caller-Id", CALLER_ID)));
        return c;
    }

}
