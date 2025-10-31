package PMS.SmartPay.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .csrf(csrf -> csrf.disable())
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/api/auth/register", "/api/auth/login" ,"/api/employees/*").permitAll()
//                .anyRequest().authenticated()
//            )
//            .httpBasic(); // For testing with Postman
//
//        return http.build();
//    }
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF (safe for testing APIs)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // ✅ Allow all API endpoints without authentication
            )
            .httpBasic(httpBasic -> httpBasic.disable()); // Disable basic auth completely

        return http.build();
    }
}