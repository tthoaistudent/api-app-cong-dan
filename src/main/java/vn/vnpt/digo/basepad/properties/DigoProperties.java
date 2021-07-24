/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vnpt.digo.basepad.properties;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 *
 * @author DELL
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "digo")
public class DigoProperties {

    /**
     * @alias digo.locale-code
     * @example vi
     */
    private String localeCode;

    /**
     * @alias digo.supported-locales
     * @example {"vi":228,"en":46,"zh":232}
     */
    private String supportedLocales;

    /**
     * @alias digo.cors-mappings
     * @example
     * [{"path-pattern":"/**","allowed-origins":"http://bar.com,http://foo.com"},{"path-pattern":"/example","allowed-origins":"http://example.com"}]
     */
    private String corsMappings;

    /**
     * @alias digo.oauth2.resource-id
     * @example digo-api-basepad
     *
     * @alias digo.oauth2.jwk.private-key
     * @example value of base64 RSA private key in PKCS#8 format
     *
     * @alias digo.oauth2.jwk.public-key
     * @example value of base64 RSA public key in X.509 format
     */
    private OAuth2 oauth2;
    
    /**
     * @alias digo.api-gateway.base-url
     * @example https://digo-api.vnptioffice.vn
     * 
     * @alias digo.microservice.service-name-prefix
     * @example digo-api-
     * 
     * @alias digo.microservice.service-default-port
     * @example 8080
     * 
     * @alias digo.microservice.communication-strategy
     * @example cluster (default)
     * @example gateway
     */
    private Microservice microservice;

    public Map<String, Short> parseSupportedLocales() {
        if (supportedLocales != null && !supportedLocales.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(supportedLocales, new TypeReference<Map<String, Short>>() {
                    //Do nothing here
                });
            } catch (IOException ex) {
                LoggerFactory.getLogger(DigoProperties.class).error("DIGO: Reading value from digo.supported-locales property has been failed", ex.getMessage());
            }
        }
        return new HashMap<>();
    }
    
    public CorsConfigurationSource getCorsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        parseCorsMappings().stream().filter(s -> {
            return s.containsKey("path-pattern") && s.get("path-pattern") != null && !s.get("path-pattern").isEmpty();
        }).forEach(e -> {
            CorsConfiguration cr = new CorsConfiguration().applyPermitDefaultValues();
            String origins = e.containsKey("allowed-origins") ? e.get("allowed-origins") : null;
            if (origins != null && !origins.isEmpty()) {
                cr.setAllowedOrigins(new ArrayList<>(Arrays.asList(origins.split(","))));
            }
            String methods = e.containsKey("allowed-methods") ? e.get("allowed-methods") : null;
            if (methods != null && !methods.isEmpty()) {
                cr.setAllowedMethods(new ArrayList<>(Arrays.asList(methods.split(","))));
            }
            String headers = e.containsKey("allowed-headers") ? e.get("allowed-headers") : null;
            if (headers != null && !headers.isEmpty()) {
                cr.setAllowedHeaders(new ArrayList<>(Arrays.asList(headers.split(","))));
            }
            source.registerCorsConfiguration(e.get("path-pattern"), cr);
        });
        return source;
    }

    public List<Map<String, String>> parseCorsMappings() {
        if (corsMappings != null && !corsMappings.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(corsMappings, new TypeReference<List<Map<String, String>>>() {
                    //Do nothing here
                });
            } catch (IOException ex) {
                LoggerFactory.getLogger(DigoProperties.class).error("DIGO: Reading value from digo.cors-mappings property has been failed", ex);
            }
        }
        return new ArrayList<>();
    }

    @Getter
    @Setter
    public static class OAuth2 {

        private Jwk jwk;
        private String resourceId;

        @Getter
        @Setter
        public static class Jwk {

            private String privateKey;
            private String publicKey;

            public PrivateKey createPrivateKey() {
                PrivateKey key = null;
                try {
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey.getBytes()));
                    key = KeyFactory.getInstance("RSA").generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                    LoggerFactory.getLogger(DigoProperties.class).error("DIGO: Can not generate private key", ex);
                }
                return key;
            }

            public PublicKey createPublicKey() {
                PublicKey key = null;
                try {
                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey.getBytes()));
                    key = KeyFactory.getInstance("RSA").generatePublic(keySpec);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                    LoggerFactory.getLogger(DigoProperties.class).error("DIGO: Can not generate public key", ex);
                }
                return key;
            }

            @Override
            public String toString() {
                return "JwtProperties[" + createPrivateKey().getFormat() + "," + createPublicKey().getFormat() + "]";
            }

        }

        @Override
        public String toString() {
            return "OAuth2Properties[" + jwk.toString() + "]";
        }
    }
    
    @Getter
    @Setter
    public static class Microservice {
        
        private String serviceNamePrefix;
        private int serviceDefaultPort;
        private String serviceDefaultProtocol;
        private String communicationStrategy;
        private String gatewayUrl;
    }
}
