package com.cottondroid.todoachieved.auth;

import com.google.firebase.auth.FirebaseAuth;

import dagger.Module;
import dagger.Provides;

@Module
public class AuthenticationModule {

    @Provides
    public FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    public AuthenticationRepository authenticationRepository(FirebaseAuth firebaseAuth) {
        return new AuthenticationRepository(firebaseAuth);
    }
}
