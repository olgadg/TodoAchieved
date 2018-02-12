package com.cottondroid.todoachieved.auth;

import dagger.Module;
import dagger.Provides;

@Module
public class TestAuthenticationModule {
    private boolean loggedIn;

    public TestAuthenticationModule() {
    }

    public TestAuthenticationModule(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    @Provides
    public AuthenticationRepository authenticationRepository() {
        return new AuthenticationRepository(null) {
            @Override
            public boolean isLoggedIn() {
                return loggedIn;
            }
        };
    }
}
