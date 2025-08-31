package com.letgo.auth.domain.provider.facebook;

import com.letgo.auth.domain.provider.*;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
class FacebookTokenHandler implements ProviderTokenHandler {

  @Value("${spring.security.oauth2.resourceserver.facebook.app-id}")
  private String facebookAppId;

  @Value("${spring.security.oauth2.resourceserver.facebook.app-secret}")
  private String facebookAppSecret;

  private final RestTemplate restTemplate = new RestTemplate();

  @Override
  public boolean supports(LetGoAuthProvider type) {
    return LetGoAuthProvider.FACEBOOK.equals(type) ;
  }

  @Override
  public ProvidedUser getuserFromAuthHeader(Authentication auth) {
    String token = ((BearerTokenAuthenticationToken) auth).getToken();
    return getUserFromToken(token);
  }

  @Override
  public ProvidedUser getUserFromToken(String token) {
    // Call Facebook's debug_token API
    String appAccessToken = facebookAppId + "|" + facebookAppSecret;
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("https://graph.facebook.com/debug_token")
      .queryParam("input_token", token)
      .queryParam("access_token", appAccessToken);

    // Send the request and validate the response
    Map<String, Object> response = restTemplate.getForObject(uriBuilder.toUriString(), Map.class);
    Map<String, Object> data = (Map<String, Object>) response.get("data");

    if (data != null && (Boolean) data.get("is_valid")) {
      // If valid, create a successful authentication object
      String userId = (String) data.get("user_id");

      Map<String, Object> userDetails = getFacebookUserDetails(token, userId);

      return new ProvidedUser(
        userId,
        LetGoAuthProvider.FACEBOOK,
        (String) userDetails.get("name"),
        (String) userDetails.get("email")
      );
    }
    return null;
  }

  private Map<String, Object> getFacebookUserDetails(String token, String userId) {
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("https://graph.facebook.com/" + userId)
      .queryParam("fields", "id,name,email")
      .queryParam("access_token", token);
    return restTemplate.getForObject(uriBuilder.toUriString(), Map.class);
  }
}
