package be.hackinthewoods.childfocus.backend.security;

import be.hackinthewoods.childfocus.backend.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private CustomUrlAuthentificationSuccessHandler successHandler;
    private SimpleUrlAuthenticationFailureHandler failureHandler;

    public SecurityConfiguration(
      RestAuthenticationEntryPoint restAuthenticationEntryPoint,
      CustomUrlAuthentificationSuccessHandler successHandler) {
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.successHandler = successHandler;
        this.failureHandler = new SimpleUrlAuthenticationFailureHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth
                .inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .and()
                .withUser("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()

                .and().authorizeRequests()
                .antMatchers("/token").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .successHandler(successHandler)
                .failureHandler(failureHandler)

                .and()
                .httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserService userService) {
        return new AuthenticationProvider(userService);
    }
}
