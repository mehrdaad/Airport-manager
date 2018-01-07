package cz.fi.muni.pa165.security;

import cz.fi.muni.pa165.config.RestSpringMvcConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = {AirplaneManagerAuthenticationProvider.class})
@Import(RestSpringMvcConfig.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AirplaneManagerAuthenticationProvider authenticationProvider;

    @Autowired
    private RestAuthenticationEntryPoint entryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/partials/**").permitAll()
                .antMatchers("/index.jsp").permitAll()
                .antMatchers(HttpMethod.GET, "/api/flights/current").permitAll()
                .antMatchers(HttpMethod.GET, "/api/user").permitAll()
                .antMatchers(HttpMethod.POST, "/api/airplanes").hasRole(SecurityRoles.ROLE_ADMIN)
                .antMatchers(HttpMethod.DELETE, "/api/airplanes").hasRole(SecurityRoles.ROLE_ADMIN)
                .antMatchers(HttpMethod.POST, "/api/destinations").hasRole(SecurityRoles.ROLE_ADMIN)
                .antMatchers(HttpMethod.DELETE, "/api/destinations").hasRole(SecurityRoles.ROLE_ADMIN)
                .antMatchers(HttpMethod.POST, "/api/flights").hasRole(SecurityRoles.ROLE_ADMIN)
                .antMatchers(HttpMethod.DELETE, "/api/flights").hasRole(SecurityRoles.ROLE_ADMIN)
                .antMatchers(HttpMethod.POST, "/api/stewards").hasRole(SecurityRoles.ROLE_ADMIN)
                .antMatchers(HttpMethod.DELETE, "/api/stewards").hasRole(SecurityRoles.ROLE_ADMIN)
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.sendRedirect("/pa165");
                    }
                })
                .permitAll()
                .and()
                .logout().logoutUrl("/logout.html").logoutSuccessUrl("/index.jsp").permitAll()
                .and()
                .csrf().disable();

    }
}
