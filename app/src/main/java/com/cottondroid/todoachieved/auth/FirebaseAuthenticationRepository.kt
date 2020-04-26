package com.cottondroid.todoachieved.auth

import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthenticationRepository(
        private val firebaseAuth: FirebaseAuth
) : AuthenticationRepository {
    override val isLoggedIn: Boolean
        get() = firebaseAuth.currentUser != null

    override val userId: String?
        get() {
            return firebaseAuth.currentUser?.uid
        }
}