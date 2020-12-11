package com.contestmodule.contest.security;

import com.contestmodule.contest.jwt.JwtAuthenticationController;
import com.contestmodule.contest.jwt.JwtAuthenticationEntryPoint;
import com.contestmodule.contest.jwt.JwtRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

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
        logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        // We don't need CSRF for this example
        httpSecurity.csrf().disable()
                // dont authenticate this particular request
                .authorizeRequests()
                    .antMatchers("/authenticate").permitAll()
                    .antMatchers("/user/create").permitAll()
                    .and()
                // all other requests need to be authenticated
                .authorizeRequests()
                    .antMatchers("/user/**").hasAnyRole("USER" , "ADMIN")
                    .and()
                .authorizeRequests()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .and()
                .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                // make sure we use stateless session; session won't be used to
                // store user's state.
                        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
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

