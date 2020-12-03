package com.apboutos.moneytrack.model.repository.remote

import com.google.gson.annotations.Expose

data class PullDataRequestBody (@Expose var username : String, @Expose var lastPullRequestDatetime : String)