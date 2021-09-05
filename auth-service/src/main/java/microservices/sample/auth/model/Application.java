package microservices.sample.auth.model;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Application model.
 * 
 * @author Matías Hermosilla
 * @since 04-09-2021
 */
@Builder
@Data
@EqualsAndHashCode(of = "id")
public class Application {
    
    /**
     * Application id.
     */
    private String id;

    /**
     * Client id.
     */
	private String clientId;

    /**
     * The instant when the application was created.
     */
	private Instant clientIdIssuedAt;

    /**
     * Client secret.
     */
	private String clientSecret;

    /**
     * The instant when the client secret expires.
     */
	private Instant clientSecretExpiresAt;

    /**
     * The name of the client.
     */
	private String clientName;

    /**
     * The supported authentication methods.
     */
    private Set<ClientAuthenticationMethod> clientAuthenticationMethods;

    /**
     * The supported grant types.
     */
	private Set<AuthorizationGrantType> authorizationGrantTypes;

    /**
     * The redirect uris.
     */
	private Set<String> redirectUris;

    /**
     * The scopes.
     */
	private Set<String> scopes;

    /**
     * Determines if the application requires authorization consent.
     */
    private boolean requireAuthorizationConsent;

    /**
     * Determines if the application requires a proof key.
     */
    private boolean requireProofKey;

    /**
     * The duration of the access token.
     */
    private Duration accessTokenTimeToLive;

    /**
     * Determines if the refresh tokens can be reused.
     */
    private boolean reuseRefreshTokens;

    /**
     * The duration of the refresh token.
     */
    private Duration refreshTokenTimeToLive;

    /**
     * The signature algorithm used to sign the id token.
     */
    private SignatureAlgorithm idTokenSignatureAlgorithm;

}
