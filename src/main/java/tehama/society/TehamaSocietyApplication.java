package tehama.society;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import tehama.society.model.User;
import tehama.society.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableAsync
public class TehamaSocietyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TehamaSocietyApplication.class, args);
	}
	//@Bean
	public User insertAdmin(UserRepository userRepository) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User user = new User();
		user.setFirstName("Zainab");
		user.setLastName("Khamis");
		user.setUsername("Zainab");
		user.setPassword(passwordEncoder.encode("123"));
		user.setEmail("zainab@gmail.com");
		user.setPhoneNumber("0777 914 000");
		user.setAddress("Jumbi");
		user.setGender("Female");
		user.setRole("ROLE_ADMIN");
		user.setActive(true);
		user.setNotLocked(true);
		userRepository.save(user);
		return user;
	}



	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200", "http://10.0.2.2"));
		corsConfiguration.setAllowedHeaders(Arrays.asList(
				"Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Jwt-Token", "Authorization", "Origin", "Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"
		));
		corsConfiguration.setExposedHeaders(Arrays.asList(
				"Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization", "Access-Control-Allow-Origin",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"
		));
		corsConfiguration.setAllowedMethods(Arrays.asList(
				"GET", "POST", "PUT", "DELETE", "OPTIONS"
		));

		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
}
