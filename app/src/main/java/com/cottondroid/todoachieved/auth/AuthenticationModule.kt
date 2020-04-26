package com.cottondroid.todoachieved.auth

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides

@Module
class AuthenticationModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun authenticationRepository(firebaseAuth: FirebaseAuth): AuthenticationRepository {
        return FirebaseAuthenticationRepository(firebaseAuth)
    }
}