package pl.nankiewic.fleetappbackend.Security.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.nankiewic.fleetappbackend.Security.AuthorizationFilter;
import pl.nankiewic.fleetappbackend.Security.JWTAuthenticationEntryPoint;
import pl.nankiewic.fleetappbackend.Security.JWTokenUtility;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final PasswordEncoder passwordEncoder;
    private final JWTokenUtility jwTokenUtility;

    @Autowired
    public WebSecurityConfiguration(UserDetailsService userDetailsService,
                                    JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                                    PasswordEncoder passwordEncoder,
                                    JWTokenUtility jwTokenUtility) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.passwordEncoder = passwordEncoder;
        this.jwTokenUtility = jwTokenUtility;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthorizationFilter authenticationJwtTokenFilter(JWTokenUtility tokenUtility) {
        return new AuthorizationFilter(tokenUtility);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
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
                .antMatchers("/api/**").permitAll()
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
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
        http.addFilterBefore(authenticationJwtTokenFilter(jwTokenUtility), UsernamePasswordAuthenticationFilter.class);
    }
}
