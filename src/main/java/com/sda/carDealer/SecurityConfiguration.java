package com.sda.carDealer;

import com.sda.carDealer.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final String employee = "EMPLOYEE";
    private final String owner = "OWNER";
    private final String admin = "ADMIN";
    private final String customer = "CUSTOMER";
    @Autowired
    private UserDetailsService appUserDetailsService;
    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(appUserDetailsService);
        authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return authenticationProvider;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        http.authorizeRequests().antMatchers("/reports/").hasAnyRole(owner, admin)
                .antMatchers("/reports/*").hasAnyRole(owner, admin)
                .antMatchers("/addCar").hasAnyRole(owner, admin, employee)
                .antMatchers("/buyForm").hasAnyRole(owner, admin, employee)
                .antMatchers("/buy").hasAnyRole(owner, admin, employee)
                .antMatchers("/**/buy").hasAnyRole(owner, admin, employee)
                .antMatchers("/buyPrepared").hasAnyRole(owner, admin, employee)
                .antMatchers("/**/delete").hasAnyRole(admin)
                .antMatchers("/**/sell").hasAnyRole(owner, admin, employee)
                .antMatchers("/operators").hasAnyRole(owner, admin, employee)
                .antMatchers("/operatorRegistration").hasAnyRole(owner, admin, employee)
                .antMatchers("/showUsers").hasAnyRole(admin)
                .antMatchers("/{id}/editUser").hasAnyRole(admin)
                .antMatchers("/editUser").hasAnyRole(admin)
                .anyRequest().permitAll()
                .and().formLogin().defaultSuccessUrl("/")
                .and().logout().logoutSuccessUrl("/").and().httpBasic();
    }
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
        //an old one!
//        auth
//                .inMemoryAuthentication()
//                .withUser("user").password(passwordEncoder().encode("password")).roles("USER")
//                .and()
//                .withUser("employee").password(passwordEncoder().encode("employee")).roles("EMPLOYEE");
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
