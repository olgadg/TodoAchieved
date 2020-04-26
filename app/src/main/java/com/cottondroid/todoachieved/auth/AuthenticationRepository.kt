package com.cottondroid.todoachieved.auth

interface AuthenticationRepository {
    val isLoggedIn: Boolean
    val userId: String?
}