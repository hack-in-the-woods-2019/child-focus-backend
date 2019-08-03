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

    private static final RequestMatcher PROTECTED_URLS = new OrRequestMatcher(
      new AntPathRequestMatcher("/api/**")
    );

    private AuthenticationProvider authenticationProvider;
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private CustomUrlAuthentificationSuccessHandler successHandler;
    private SimpleUrlAuthenticationFailureHandler failureHandler;

    public SecurityConfiguration(
      AuthenticationProvider authenticationProvider, RestAuthenticationEntryPoint restAuthenticationEntryPoint,
      CustomUrlAuthentificationSuccessHandler successHandler) {
        this.authenticationProvider = authenticationProvider;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.successHandler = successHandler;
        this.failureHandler = new SimpleUrlAuthenticationFailureHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
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
                .requestMatchers(PROTECTED_URLS)
                .authenticated()

                .and()
                .csrf().disable()
                .formLogin()
                .successHandler(successHandler)
                .failureHandler(failureHandler)

                .and()
                .httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    @Bean
    AuthenticationFilter authenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(PROTECTED_URLS);
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Bean
    AuthenticationEntryPoint forbiddenEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
    }
}
