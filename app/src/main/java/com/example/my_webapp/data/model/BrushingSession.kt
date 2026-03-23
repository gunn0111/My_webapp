package com.example.my_webapp.data.model

import com.google.gson.annotations.SerializedName

data class BrushingSession(
    @SerializedName("session_id")
    val sessionId: String = "",
    @SerializedName("profile_id")
    val profileId: String = "",
    @SerializedName("handle_id")
    val handleId: String = "",
    @SerializedName("state")
    val state: String = BrushingSessionContainer.STATE_RECORDED,
    @SerializedName("duration")
    val duration: Int = 0,
    @SerializedName("timestamp")
    val timestamp: Long = 0L,
    @SerializedName("brushing_data")
    val brushingData: String = ""
)
