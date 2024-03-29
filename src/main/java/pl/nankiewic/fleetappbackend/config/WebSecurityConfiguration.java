package pl.nankiewic.fleetappbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.nankiewic.fleetappbackend.config.jwt.JwtAuthorizationFilter;
import pl.nankiewic.fleetappbackend.config.jwt.JWTAuthenticationEntryPoint;
import pl.nankiewic.fleetappbackend.config.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService userService;
    private final JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfiguration(final CustomUserDetailsService userService,
                                    final JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                                    final JwtAuthorizationFilter jwtAuthorizationFilter,
                                    final PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().httpStrictTransportSecurity().disable()
                .and().cors()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/test/**").permitAll()
                .antMatchers("/user/activation-account").permitAll()
                .antMatchers("/user/reset-password").permitAll()
                .antMatchers("/user/reset-password/new").permitAll()
                .antMatchers("/user/new-account").permitAll()
                .antMatchers("/user/new-account/password").permitAll()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/v2/api-docs", "/webjars/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
    }

}
