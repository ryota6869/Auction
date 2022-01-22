package oit.is.uno.auction.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class AuctionAuthConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder().encode("p@ss1")).roles("USER");
    auth.inMemoryAuthentication().withUser("user2").password(passwordEncoder().encode("p@ss2")).roles("USER");
    auth.inMemoryAuthentication().withUser("user3").password(passwordEncoder().encode("p@ss3")).roles("USER");
    auth.inMemoryAuthentication().withUser("user4").password(passwordEncoder().encode("p@ss4")).roles("USER");
    auth.inMemoryAuthentication().withUser("teacher").password(passwordEncoder().encode("oit")).roles("ADMIN");
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.formLogin();

    http.authorizeRequests().antMatchers("/home").authenticated();

    http.logout().logoutSuccessUrl("/");
    http.csrf().disable();
    http.headers().frameOptions().disable();
  }

}
