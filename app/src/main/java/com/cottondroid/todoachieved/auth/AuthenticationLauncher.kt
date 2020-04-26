package com.cottondroid.todoachieved.auth

import android.app.Activity
import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig.EmailBuilder
import com.firebase.ui.auth.AuthUI.IdpConfig.GoogleBuilder
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import javax.inject.Inject

class AuthenticationLauncher @Inject constructor() {
    fun startLoginActivityIntent(): Intent {
        return AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(PROVIDERS)
                .build()
    }

    fun onAuthenticationAnswer(requestCode: Int, resultCode: Int, data: Intent?) {
        val response = IdpResponse.fromResultIntent(data)
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    return
                }
                if (response.error != null
                        && response.error!!.errorCode == ErrorCodes.NO_NETWORK) {
                    // on no network
                    return
                }
                if (response.error != null
                        && response.error!!.errorCode == ErrorCodes.UNKNOWN_ERROR) {
                    // on unknown error
                    return
                }
            }
        }
    }

    companion object {
        const val RC_SIGN_IN = 123
        private val PROVIDERS = listOf(
                EmailBuilder().build(),
                GoogleBuilder().build()
        )
    }
}