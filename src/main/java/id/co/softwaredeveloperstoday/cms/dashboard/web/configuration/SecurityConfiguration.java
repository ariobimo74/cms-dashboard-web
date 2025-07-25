package id.co.softwaredeveloperstoday.cms.dashboard.web.configuration;

import id.co.softwaredeveloperstoday.cms.dashboard.web.service.impl.CustomAuthenticationProviderImpl;
import id.co.softwaredeveloperstoday.cms.dashboard.web.service.impl.CustomUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationProviderImpl customAuthenticationProvider;
    private final CustomUserDetailsServiceImpl customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf(
                        csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/js/**", "/dist/**", "/plugins/**", "/css/**", "/fonts/**", "/login/**", "api/v1/value/**", "/api/v1/register"
                )
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/dashboard", true)
                .loginPage("/login")
                .permitAll()
                .and()
                .rememberMe()
                .key("softwaredeveloperstoday")
                .rememberMeCookieName("remember-me")
                .tokenValiditySeconds(604800)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID","remember-me");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
        auth.userDetailsService(customUserDetailsService);
    }
}
