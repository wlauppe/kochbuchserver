package de.psekochbuch.exzellenzkoch.config

import de.psekochbuch.exzellenzkoch.security.JwtAuthenticationEntryPoint
import de.psekochbuch.exzellenzkoch.security.JwtRequestFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint? = null
    @Autowired
    private val jwtUserDetailsService: UserDetailsService? = null
    @Autowired
    private val jwtRequestFilter: JwtRequestFilter? = null

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) { // configure AuthenticationManager so that it knows from where to load
// user for matching credentials
// Use BCryptPasswordEncoder
        // auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder())
    }

    override fun configure(web: WebSecurity?) {
        super.configure(web)
        web?.ignoring()?.antMatchers("/api/recipes/{id}")
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    /*  @Bean
      @Throws(Exception::class)
      override fun authenticationManagerBean(): AuthenticationManager {
          return super.authenticationManagerBean()
      }*/

    @Throws(Exception::class)
    override fun configure(httpSecurity: HttpSecurity) { // We don't need CSRF for this example
        httpSecurity.csrf().disable() // dont authenticate this particular request
                .authorizeRequests().antMatchers("/authenticate").permitAll().anyRequest() // all other requests need to be authenticated
                .authenticated().and().exceptionHandling() // make sure we use stateless session; session won't be used to
// store user's state.
                .authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}