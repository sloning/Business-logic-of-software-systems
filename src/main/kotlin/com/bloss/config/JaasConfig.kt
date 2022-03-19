package com.bloss.config

import com.bloss.security.UserDetailsLoginModule
import com.bloss.security.UserRepositoryAuthorityGranter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.jaas.AbstractJaasAuthenticationProvider
import org.springframework.security.authentication.jaas.DefaultJaasAuthenticationProvider
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import javax.security.auth.login.AppConfigurationEntry
import javax.security.auth.login.Configuration as javaxConfiguration

@Configuration
class JaasConfig {
    @Bean
    fun configuration(
        userDetailsService: UserDetailsService,
        passwordEncoder: PasswordEncoder
    ): javaxConfiguration {
        val configurationEntries = arrayOf(
            AppConfigurationEntry(
                UserDetailsLoginModule::class.java.canonicalName,
                AppConfigurationEntry.LoginModuleControlFlag.REQUIRED,
                mapOf("userDetailsService" to userDetailsService, "passwordEncoder" to passwordEncoder)
            )
        )
        return InMemoryConfiguration(mapOf("SPRINGSECURITY" to configurationEntries))
    }

    @Bean
    fun jaasAuthenticationProvider(configuration: javaxConfiguration): AbstractJaasAuthenticationProvider {
        val defaultJaasAuthenticationProvider = DefaultJaasAuthenticationProvider()
        defaultJaasAuthenticationProvider.setConfiguration(configuration)
        defaultJaasAuthenticationProvider.setAuthorityGranters(arrayOf(UserRepositoryAuthorityGranter()))
        return defaultJaasAuthenticationProvider
    }
}