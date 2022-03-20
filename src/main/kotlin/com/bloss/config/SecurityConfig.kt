package com.bloss.config

import com.bloss.security.CustomUserDetailsService
import com.bloss.security.JwtTokenFilter
import com.bloss.security.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.authentication.jaas.AbstractJaasAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val jaasAuthenticationProvider: AbstractJaasAuthenticationProvider,
    private val passwordEncoder: PasswordEncoder,
    private val userDetailsService: CustomUserDetailsService
) : WebSecurityConfigurerAdapter() {
    override fun configure(webSecurity: WebSecurity) {
        webSecurity
            .ignoring()
            .antMatchers(HttpMethod.GET, "/api/*/post")
            .antMatchers("/api/*/user/**")
            .antMatchers("/swagger-ui/**", "/v3/api-docs/**")
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(jaasAuthenticationProvider)
    }

    override fun configure(http: HttpSecurity) {
        http
            .httpBasic()
            .disable()
            .cors()
            .and()
            .csrf()
            .disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/api/*/admin/**").hasRole("ADMIN")
            .antMatchers("/api/*/moderator/**").hasAnyRole("ADMIN", "MODERATOR")
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder)
        return authProvider
    }
}
