package com.bloss.mainservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.jaas.AbstractJaasAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val jaasAuthenticationProvider: AbstractJaasAuthenticationProvider,
    private val passwordEncoder: PasswordEncoder,
) : WebSecurityConfigurerAdapter() {
    override fun configure(webSecurity: WebSecurity) {
        webSecurity
            .ignoring()
            .antMatchers(
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/*",
                "/camunda/**",
                "/api/v1/user/register",
                "/api/v1/user/login"
            )
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
//            .antMatchers("/api/*/admin/**").hasRole("ADMIN")
//            .antMatchers("/api/*/moderator/**").hasAnyRole("ADMIN", "MODERATOR")
//            .antMatchers(HttpMethod.GET, "/api/*/user/data").authenticated()
//            .antMatchers("/api/*/post").authenticated()
//            .antMatchers(HttpMethod.GET, "/api/*/post").permitAll()
            .anyRequest().permitAll()
            .and()
//            .addFilterBefore(JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

//    @Bean
//    fun authenticationProvider(): DaoAuthenticationProvider {
//        val authProvider = DaoAuthenticationProvider()
//        authProvider.setUserDetailsService(userDetailsService)
//        authProvider.setPasswordEncoder(passwordEncoder)
//        return authProvider
//    }
}
