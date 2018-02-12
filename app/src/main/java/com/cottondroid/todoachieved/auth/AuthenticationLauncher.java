package com.cottondroid.todoachieved.auth;

import android.content.Intent;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AuthenticationLauncher {
    public static final int RC_SIGN_IN = 123;

    private static final List<AuthUI.IdpConfig> PROVIDERS = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build()
    );

    @Inject
    public AuthenticationLauncher() {
    }

    public Intent startLoginActivityIntent() {
        return AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(PROVIDERS)
                .build();
    }

    public void onAuthenticationAnswer(int requestCode, int resultCode, Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Successfully signed in
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    // on no network
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    // on unknown error
                    return;
                }
            }
        }
    }
}
