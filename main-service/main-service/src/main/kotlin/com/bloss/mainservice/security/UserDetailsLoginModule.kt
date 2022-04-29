package com.bloss.mainservice.security

import com.sun.security.auth.UserPrincipal
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import java.io.IOException
import javax.security.auth.Subject
import javax.security.auth.callback.*
import javax.security.auth.login.LoginException
import javax.security.auth.spi.LoginModule

class UserDetailsLoginModule : LoginModule {
    private var subject: Subject? = null
    private var callbackHandler: CallbackHandler? = null
    private var userDetailsService: UserDetailsService? = null
    private var passwordEncoder: PasswordEncoder? = null
    private var email: String? = null
    private var loginSucceeded = false

    override fun initialize(
        subject: Subject,
        callbackHandler: CallbackHandler,
        map: Map<String?, *>?,
        map1: Map<String?, *>
    ) {
        this.subject = subject
        this.callbackHandler = callbackHandler
        userDetailsService = map1["userDetailsService"] as UserDetailsService?
        passwordEncoder = map1["passwordEncoder"] as PasswordEncoder?
    }

    override fun login(): Boolean {
        val nameCallback = NameCallback("email")
        val passwordCallback = PasswordCallback("password", false)
        try {
            callbackHandler!!.handle(arrayOf<Callback>(nameCallback, passwordCallback))
            email = nameCallback.name
            val password = String(passwordCallback.password)
            val user = userDetailsService!!.loadUserByUsername(email)
            loginSucceeded = passwordEncoder!!.matches(password, user.password)
        } catch (e: UsernameNotFoundException) {
            loginSucceeded = false
        } catch (e: IOException) {
            loginSucceeded = false
        } catch (e: UnsupportedCallbackException) {
            loginSucceeded = false
        }
        return loginSucceeded
    }

    override fun commit(): Boolean {
        if (!loginSucceeded) {
            return false
        }
        if (email == null) {
            throw LoginException("Username is null during the commit")
        }
        val principal = UserPrincipal(email)
        subject!!.principals.add(principal)
        return true
    }

    override fun abort(): Boolean {
        return false
    }

    override fun logout(): Boolean {
        return false
    }
}
