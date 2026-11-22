package nest.com.budget_app.configs;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

@Configuration
public class JwtConfig {

  @Bean
  MacAlgorithm jwtAlgorithm(@Value("${jwt.algorithm:HS256}") String algorithm) {
    return MacAlgorithm.valueOf(algorithm);
  }

  @Bean
  SecretKey jwtKey(
      @Value("${jwt.secret}") String secret, @Value("${jwt.algorithm:HS256}") String algorithm) {
    return new SecretKeySpec(
        secret.getBytes(StandardCharsets.UTF_8), "HmacSHA" + algorithm.substring(2));
  }

  @Bean
  JwtEncoder jwtEncoder(SecretKey jwtKey) {
    return new NimbusJwtEncoder(new ImmutableSecret<>(jwtKey));
  }

  @Bean
  JwtDecoder jwtDecoder(SecretKey jwtKey, @Value("${jwt.algorithm:HS256}") String algorithm) {
    return NimbusJwtDecoder.withSecretKey(jwtKey)
        .macAlgorithm(MacAlgorithm.valueOf(algorithm))
        .build();
  }
}
