package com.apboutos.moneytrack.model.repository.remote

import com.google.gson.annotations.Expose

data class AuthenticationRequestData(@Expose var username : String,@Expose var password : String)
{
}