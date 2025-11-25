package ma.ens.security.oauth2_client.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/css/**").permitAll() // page d'accueil et ressources publiques
                        .anyRequest().authenticated()               // tout le reste nécessite login
                )
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/profile", true)      // après login, redirection vers /profile
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")                     // après logout, retour à la page d'accueil
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }
}