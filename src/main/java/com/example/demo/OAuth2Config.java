package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
   private String clientid = "tutorialspoint";
   private String clientSecret = "my-secret-key";
   private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\r\n" + 
   		"MIIEpAIBAAKCAQEAr5XHjP6sGKq2tJJxpvF5STqkPW+E7GKqCV4T6qRWgltTzPVB\r\n" + 
   		"IPye7KDr36iIiRlo8sxIMfBq2tODjuiFGiKOTPQPoAac09hHHgAR+zgp6uokO/O0\r\n" + 
   		"tMcAzWOlOcMsug0ICFLnxrU1v53KwzLlw50h4PyeZY0cwcvzXkhOgSK0aI8myRQF\r\n" + 
   		"tfV7RfuSZ7B+hOCv8fikV4CLlgLkThEdpFBdmovvFp1adBogB+h9wtFS946UE63J\r\n" + 
   		"zjcB00L1zusW1AW27eMyuo9JHB1S1Kmh7gqETJ3jZcr661l+suuKjrsbWNjrROej\r\n" + 
   		"Vqiw2JW5nG1+FiUzMe9RfuZU8yUtpJ3TguZjZQIDAQABAoIBAQCn6tK7CTbmJCS0\r\n" + 
   		"jbR5FkeKe7VkN4lkU3sPmFPtuxosclCzGv0dIdqOq9rHuzJQAuf2At0sPSLDgTI4\r\n" + 
   		"cjAQbWmvOFzVCl3iztw8WY7YtL6JtT/PweJYXFRtF9WEZx4YE6xR4Y9c3iG3zZyC\r\n" + 
   		"juNi7pvewVht/8S3pWdqDDZfkyP7NQ45R+JDyHENpSILajRcprR0fHXQWJQaFDCj\r\n" + 
   		"Wr3czZ+HpCX2oniELtSKwVRXmf6rliJWwThNhQ3oXkz+E8sqapugDYdGzYTbYrSm\r\n" + 
   		"VdS8lqG8929Y1lgIrOvjKSHurT962P9eZugXBKKjfBFHy/ZEtNgJDywxLeArvvvP\r\n" + 
   		"MTh51C/BAoGBANVhM9n3bpB+IUyegdT502TESocxSHdbh6qA+JBNGulQBOjjSEoW\r\n" + 
   		"k3tGmK1YJflYL92iIeD7fNzjR5qCosxiq/62AypjHW09p36qnwD+Y23IbD65nLPo\r\n" + 
   		"QWYr6f2OWgqGcKFMWqcc9bra93DA6lVvZevF+lqMrQkORFtYyYYH5AQVAoGBANKo\r\n" + 
   		"A98vPu5ywQskPQlpnICJ1AaKwkx+pLKQKSzm4qq+1SWuXquSbYQ3EPCm/zitnAv5\r\n" + 
   		"iSLEkIIy0yvVdIR3m+Os4YKFkpPd37Z3Fzu7MGW63oJEZsWBwKWoKAK6QCS716ER\r\n" + 
   		"iO1h5G0mL/g8poKrFq+EZvOWVUdDgjyvpMaqSyYRAoGAJ3YKeMzhjadF0T2fFRv+\r\n" + 
   		"lCwY/FeI50nuFQI09brMt0ktH0SpIcp7C9lKcwRSQYKll39cdJcNyGvf2hs+sD2v\r\n" + 
   		"QTO38ibSUlmMmEhFj5F8Idlm61K4QC07qwGEDHg+/qNOQQY13aHlb+D+0cNbkuE/\r\n" + 
   		"IBNE8WoCtiIFUj9hRYBAEuECgYEAmj0cwKBGMq7g+3hoHGkwhsjj7npQs1OS4Y8+\r\n" + 
   		"JrtSwXQCepmGhZcsCr+K7rlHa8dp1za5GzTTHhF6nsCmlItDH9uoLjoAzbWJpH7k\r\n" + 
   		"Cv8O+51sZ/MPUJOla2WJDh4y8vJ6elttZ2nip6xrORWnUUtVy+NkwfuTqx8Zz7dH\r\n" + 
   		"LZLofRECgYBA5t7uX422sJVt30XYbyMo/bFB2PQhPvQVE57RieNpEYLNk5Qo3mYu\r\n" + 
   		"SfVWAJDx1PECxsMBRYua0/BsliKnljs/Lmjr7+SNW3Gnkp8G/a8YlOXntuKYK1ZS\r\n" + 
   		"E0ueBHbtZOPy1nENYipIc+5PUJheud1OqeyU5RmTzBnVzQBttRgEmA==\r\n" + 
   		"-----END RSA PRIVATE KEY-----";
   private String publicKey = "-----BEGIN PUBLIC KEY-----\r\n" + 
   		"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr5XHjP6sGKq2tJJxpvF5\r\n" + 
   		"STqkPW+E7GKqCV4T6qRWgltTzPVBIPye7KDr36iIiRlo8sxIMfBq2tODjuiFGiKO\r\n" + 
   		"TPQPoAac09hHHgAR+zgp6uokO/O0tMcAzWOlOcMsug0ICFLnxrU1v53KwzLlw50h\r\n" + 
   		"4PyeZY0cwcvzXkhOgSK0aI8myRQFtfV7RfuSZ7B+hOCv8fikV4CLlgLkThEdpFBd\r\n" + 
   		"movvFp1adBogB+h9wtFS946UE63JzjcB00L1zusW1AW27eMyuo9JHB1S1Kmh7gqE\r\n" + 
   		"TJ3jZcr661l+suuKjrsbWNjrROejVqiw2JW5nG1+FiUzMe9RfuZU8yUtpJ3TguZj\r\n" + 
   		"ZQIDAQAB\r\n" + 
   		"-----END PUBLIC KEY-----";

   @Autowired
   @Qualifier("authenticationManagerBean")
   private AuthenticationManager authenticationManager;
   
   @Bean
   public JwtAccessTokenConverter tokenEnhancer() {
      JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
      converter.setSigningKey(privateKey);
      converter.setVerifierKey(publicKey);
      return converter;
   }
   @Bean
   public JwtTokenStore tokenStore() {
      return new JwtTokenStore(tokenEnhancer());
   }
   @Override
   public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
      .accessTokenConverter(tokenEnhancer());
   }
   @Override
   public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
      security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
   }
   @Override
   public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      clients.inMemory().withClient(clientid).secret(clientSecret).scopes("read", "write")
         .authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(20000)
         .refreshTokenValiditySeconds(20000);

   }
} 