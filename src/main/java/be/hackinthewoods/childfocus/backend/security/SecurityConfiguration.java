package be.hackinthewoods.childfocus.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final RequestMatcher API = new OrRequestMatcher(
      new AntPathRequestMatcher("/api/**")
    );

    private static final RequestMatcher ADMIN = new OrRequestMatcher(
      new AntPathRequestMatcher("/admin/**")
    );

    private AuthenticationProvider authenticationProvider;
    private CustomUrlAuthentificationSuccessHandler successHandler;
    private SimpleUrlAuthenticationFailureHandler failureHandler;
    private UserDetailsService customUserDetailsService;

    public SecurityConfiguration(
      AuthenticationProvider authenticationProvider,
      CustomUrlAuthentificationSuccessHandler successHandler, UserDetailsService customUserDetailsService) {
        this.authenticationProvider = authenticationProvider;
        this.successHandler = successHandler;
        this.customUserDetailsService = customUserDetailsService;
        this.failureHandler = new SimpleUrlAuthenticationFailureHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider)
        .userDetailsService(customUserDetailsService)
        .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers("/token/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and().exceptionHandling()

                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter(), AnonymousAuthenticationFilter.class)
                .authorizeRequests()
                .requestMatchers(ADMIN).hasRole("ADMIN")
                .requestMatchers(API).authenticated()

                .and()
                .csrf().disable()
                .formLogin()
                .successHandler(successHandler)
                .failureHandler(failureHandler)

                .and()
                .httpBasic()
                .authenticationEntryPoint(forbiddenEntryPoint());
    }

    @Bean
    AuthenticationFilter authenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(API);
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Bean
    AuthenticationEntryPoint forbiddenEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
    }
}
