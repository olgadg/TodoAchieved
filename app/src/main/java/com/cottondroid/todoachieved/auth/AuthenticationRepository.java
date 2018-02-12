package com.cottondroid.todoachieved.auth;

import com.google.firebase.auth.FirebaseAuth;

public class AuthenticationRepository {

    private FirebaseAuth firebaseAuth;

    public AuthenticationRepository(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public boolean isLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }
}
