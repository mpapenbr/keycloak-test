package de.mp.demo.keycloak.demo;

import java.security.Principal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloController {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private ObjectMapper om;

    @GetMapping("/all")
    public String allAccess() {
        return "Public content";
    }

    // @GetMapping("/login")
    // public ResponseEntity<?> signin() {
    // Map<String, Object> clientCredentials = new HashMap<>();
    // clientCredentials.put("secret", clientSecret);
    // clientCredentials.put("grant_type", "password");

    // Configuration configuration = new Configuration(authServerUrl, realm,
    // clientId, clientCredentials, null);
    // AuthzClient authzClient = AuthzClient.create(configuration);

    // AccessTokenResponse response = authzClient.obtainAccessToken("demo", "demo");

    // return ResponseEntity.ok(response);
    // }

    @GetMapping("/user")
    public String userAccess(Principal p) {
        return "User access for " + p.getName();
    }

    @GetMapping("/user/info")
    public String userInfo(Principal p) throws JsonProcessingException {
        

        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) p;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        log.info("accessToken {}", om.writeValueAsString(accessToken));
        
        return "BÄÄÄ User access for " + p.getName();
    }

}
