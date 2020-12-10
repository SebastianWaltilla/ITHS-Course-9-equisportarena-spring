package com.usermodule.user.security;

import com.usermodule.user.jwt.JwtAuthenticationEntryPoint;
import com.usermodule.user.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.usermodule.user.security.SecurityConstants.SIGN_UP_URL;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // We don't need CSRF for this example
        httpSecurity.csrf().disable()
                // dont authenticate this particular request
                .authorizeRequests().antMatchers("/authenticate").permitAll()
                .antMatchers("user/create").permitAll().
                // all other requests need to be authenticated
                        anyRequest().authenticated().and().
                // make sure we use stateless session; session won't be used to
                // store user's state.
                        exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }


    //@Bean
    //public DaoAuthenticationProvider authenticationProvider() {
    //    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    //    authProvider.setUserDetailsService(userDetailsService());
    //    authProvider.setPasswordEncoder(passwordEncoder());

    //    return authProvider;
    //}

    //@Override
    //protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //    auth.authenticationProvider(authenticationProvider());
    //}

    //@Override
    //protected void configure(HttpSecurity http) throws Exception {
    //    http.authorizeRequests()
    //            .antMatchers("/").hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN")
    //            .antMatchers("/new").hasAnyAuthority("ADMIN", "CREATOR")
    //            .antMatchers("/edit/**").hasAnyAuthority("ADMIN", "EDITOR")
    //            .antMatchers("/delete/**").hasAuthority("ADMIN")
    //            .antMatchers("/anyPage").hasAuthority("ADMIN")
    //            .antMatchers("/admin/**").hasAuthority("ADMIN")
    //            .anyRequest().authenticated()
    //            .and()
    //            .formLogin().permitAll()
    //            .and()
    //            .logout().permitAll()
    //            .and()
    //            .exceptionHandling().accessDeniedPage("/403")
    //    ;
    //}

}

