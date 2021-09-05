package microservices.sample.auth.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.stereotype.Service;
import microservices.sample.auth.model.Application;

/**
 * A custom implementation of {@link RegisteredClientRepository} that uses the
 * {@link ClientSettings} and {@link TokenSettings} to determine which
 * {@link RegisteredClient}s are available.
 * 
 * @author Matías Hermosilla
 * @since 2021-09-04
 */
@Service
public class DefaultRegisteredClientRepository implements RegisteredClientRepository {

    /**
     * The {@link ApplicationService} to use to retrieve the {@link Application}s.
     */
    @Autowired
    private ApplicationService applicationService;

    /**
     * Saves the {@link RegisteredClient}s.
     * 
     * @param registeredClient The {@link RegisteredClient}s to save.
     */
    @Override
    public void save(RegisteredClient registeredClient) {
        // Create application object from registered client
        Application application = this.toApplication(registeredClient);

        // Save application
        this.applicationService.save(application);
    }

    /**
     * Finds the {@link RegisteredClient}s by their id.
     * 
     * @param clientId The id of the {@link RegisteredClient}s to find.
     */
    @Override
    public RegisteredClient findById(String id) {
        // Find application by id
        Application application = applicationService.findById(id);

        // If the application is not null
        if (application != null) {

            // Return the registered client object from the application
            return this.fromApplication(application);
        }

        // Return null if the application is null
        return null;
    }

    /**
     * Finds the {@link RegisteredClient}s by their client id.
     * 
     * @param clientId The client id of the {@link RegisteredClient}s to find.
     */
    @Override
    public RegisteredClient findByClientId(String clientId) {
        // Find all by client id
        List<Application> applications = applicationService.findAllByClientId(clientId);

        // If the list is not empty
        if (!applications.isEmpty()) {

            // Get the first application
            Application application = applications.get(0);

            // Return the registered client object from the application
            return this.fromApplication(application);
        }

        // Return null if the application is null
        return null;
    }

    /**
     * Converts the {@link RegisteredClient} to an {@link Application}.
     * 
     * @param registeredClient The {@link RegisteredClient} to convert.
     */
    private RegisteredClient fromApplication(Application application) {
        // Return a registered client object instance
        return RegisteredClient
            .withId(application.getId())
            .clientId(application.getClientId())
            .clientIdIssuedAt(application.getClientIdIssuedAt())
            .clientSecret(application.getClientSecret())
            .clientSecretExpiresAt(application.getClientSecretExpiresAt())
            .clientName(application.getClientName())
            .clientAuthenticationMethods(authenticationMethods -> authenticationMethods.addAll(application.getClientAuthenticationMethods()))
            .authorizationGrantTypes(authorizationGrantTypes -> authorizationGrantTypes.addAll(application.getAuthorizationGrantTypes()))
            .redirectUris(redirectUris -> redirectUris.addAll(application.getRedirectUris()))
            .scopes(scopes -> scopes.addAll(application.getScopes()))
            .clientSettings(ClientSettings.builder()
                    .requireAuthorizationConsent(application.isRequireAuthorizationConsent())
                    .requireProofKey(application.isRequireProofKey())
                    .build())
            .tokenSettings(TokenSettings.builder()
                    .accessTokenTimeToLive(application.getAccessTokenTimeToLive())
                    .reuseRefreshTokens(application.isReuseRefreshTokens())
                    .refreshTokenTimeToLive(application.getRefreshTokenTimeToLive())
                    .idTokenSignatureAlgorithm(application.getIdTokenSignatureAlgorithm())
                    .build())
            .build();
    }

    /**
     * Converts the {@link Application} to a {@link RegisteredClient}.
     * 
     * @param application The {@link Application} to convert.
     */
    private Application toApplication(RegisteredClient registeredClient) {
        // Build an application object
        return Application.builder()
            .id(registeredClient.getId())
            .clientId(registeredClient.getClientId())
            .clientIdIssuedAt(registeredClient.getClientIdIssuedAt())
            .clientSecret(registeredClient.getClientSecret())
            .clientSecretExpiresAt(registeredClient.getClientSecretExpiresAt())
            .clientName(registeredClient.getClientName())
            .clientAuthenticationMethods(registeredClient.getClientAuthenticationMethods())
            .authorizationGrantTypes(registeredClient.getAuthorizationGrantTypes())
            .redirectUris(registeredClient.getRedirectUris())
            .scopes(registeredClient.getScopes())
            .requireAuthorizationConsent(registeredClient.getClientSettings().isRequireAuthorizationConsent())
            .requireProofKey(registeredClient.getClientSettings().isRequireProofKey())
            .accessTokenTimeToLive(registeredClient.getTokenSettings().getAccessTokenTimeToLive())
            .reuseRefreshTokens(registeredClient.getTokenSettings().isReuseRefreshTokens())
            .refreshTokenTimeToLive(registeredClient.getTokenSettings().getRefreshTokenTimeToLive())
            .idTokenSignatureAlgorithm(registeredClient.getTokenSettings().getIdTokenSignatureAlgorithm())
            .build();
    }
    
}
