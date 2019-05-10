package devlab.commons.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private CustomUserService customUserService;
    private PasswordEncoder passwordEncoder;

    public SecurityConfig(CustomUserService customUserService, PasswordEncoder passwordEncoder) {
        this.customUserService = customUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable();
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/signin")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(  // Udane logowanie
                        (req, res, auth) -> {
                            for (GrantedAuthority authority : auth.getAuthorities()) {
                                System.out.println(authority.getAuthority());
                            }
                            System.out.println(auth.getName());
                            res.sendRedirect("/");
                        }
                )
                .failureHandler(
                        (req, res, exception) -> {
                            String errorMessage;
                            if (exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
                                errorMessage = "Invalid username or password";
                            } else {
                                errorMessage = "unknow error " + exception.getMessage();
                            }
                            req.getSession().setAttribute("message", errorMessage);
                            res.sendRedirect("/");
                        })
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((req, res, auth) -> {
                    res.sendRedirect("/login");
                })
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/login")
                .and()
                .csrf().disable();
        http.headers().frameOptions().disable();


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService).passwordEncoder(passwordEncoder);
    }
}
