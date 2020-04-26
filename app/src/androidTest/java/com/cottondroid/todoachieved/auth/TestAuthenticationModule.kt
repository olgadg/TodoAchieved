package com.cottondroid.todoachieved.auth

import dagger.Module
import dagger.Provides

@Module
class TestAuthenticationModule(var loggedIn: Boolean = false) {

    @Provides
    fun authenticationRepository(): AuthenticationRepository {
        return object : AuthenticationRepository {
            override val isLoggedIn: Boolean = loggedIn
            override val userId: String? = "userId"
        }
    }
}