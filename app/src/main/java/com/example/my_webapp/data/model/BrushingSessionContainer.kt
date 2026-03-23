package com.example.my_webapp.data.model

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class BrushingSessionContainer {

    private var brushHeadMinutes: Int = 0
    private var creditReqCount: Int = 0

    private val sessions: MutableList<BrushingSession> = mutableListOf()

    fun storeBrushingSessionsFromJson(brushingSessionJson: String): Int {
        if (brushingSessionJson.isBlank()) return 0
        return storeBrushingSessions(brushingSessionJson)
    }

    fun storeBrushingSessions(brushingSessionJson: String): Int {
        val listType = object : TypeToken<List<BrushingSession>>() {}.type
        val gson: Gson = GsonBuilder().create()

        return try {
            val parsedSessions: List<BrushingSession> = gson.fromJson(brushingSessionJson, listType)
            sessions.addAll(parsedSessions)
            parsedSessions.size
        } catch (e: Exception) {
            0
        }
    }

    fun getSessions(): List<BrushingSession> = sessions.toList()

    fun getSessionsByState(state: String): List<BrushingSession> =
        sessions.filter { it.state == state }

    fun getSessionsByProfileId(profileId: String): List<BrushingSession> =
        sessions.filter { it.profileId == profileId }

    fun getTotalBrushingDuration(): Int =
        sessions.sumOf { it.duration }

    fun getBrushHeadMinutes(): Int = brushHeadMinutes

    fun setBrushHeadMinutes(minutes: Int) {
        brushHeadMinutes = minutes
    }

    fun getCreditReqCount(): Int = creditReqCount

    fun incrementCreditReqCount() {
        creditReqCount++
    }

    fun clearSessions() {
        sessions.clear()
        brushHeadMinutes = 0
        creditReqCount = 0
    }

    companion object {
        const val STATE_RECORDED = "recorded"
        const val STATE_RETRIEVED = "retrieved"
        const val STATE_REQUESTED = "requested"
        const val STATE_CREDITED = "credited"
        const val STATE_REJECTED = "rejected"
    }
}
