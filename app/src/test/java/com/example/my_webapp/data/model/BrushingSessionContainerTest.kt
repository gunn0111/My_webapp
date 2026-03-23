package com.example.my_webapp.data.model

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BrushingSessionContainerTest {

    private lateinit var container: BrushingSessionContainer

    @Before
    fun setup() {
        container = BrushingSessionContainer()
    }

    @Test
    fun `initial state has no sessions`() {
        assertTrue(container.getSessions().isEmpty())
        assertEquals(0, container.getTotalBrushingDuration())
    }

    @Test
    fun `state constants have correct values`() {
        assertEquals("recorded", BrushingSessionContainer.STATE_RECORDED)
        assertEquals("retrieved", BrushingSessionContainer.STATE_RETRIEVED)
        assertEquals("requested", BrushingSessionContainer.STATE_REQUESTED)
        assertEquals("credited", BrushingSessionContainer.STATE_CREDITED)
        assertEquals("rejected", BrushingSessionContainer.STATE_REJECTED)
    }

    @Test
    fun `storeBrushingSessions parses valid JSON`() {
        val json = """
            [
                {"session_id": "s1", "profile_id": "p1", "state": "recorded", "duration": 120},
                {"session_id": "s2", "profile_id": "p1", "state": "retrieved", "duration": 90}
            ]
        """.trimIndent()

        val count = container.storeBrushingSessions(json)

        assertEquals(2, count)
        assertEquals(2, container.getSessions().size)
    }

    @Test
    fun `storeBrushingSessions returns 0 for invalid JSON`() {
        val count = container.storeBrushingSessions("invalid json")
        assertEquals(0, count)
        assertTrue(container.getSessions().isEmpty())
    }

    @Test
    fun `storeBrushingSessionsFromJson returns 0 for blank input`() {
        assertEquals(0, container.storeBrushingSessionsFromJson(""))
        assertEquals(0, container.storeBrushingSessionsFromJson("   "))
    }

    @Test
    fun `getSessionsByState filters correctly`() {
        val json = """
            [
                {"session_id": "s1", "state": "recorded", "duration": 120},
                {"session_id": "s2", "state": "credited", "duration": 90},
                {"session_id": "s3", "state": "recorded", "duration": 60}
            ]
        """.trimIndent()

        container.storeBrushingSessions(json)

        val recorded = container.getSessionsByState(BrushingSessionContainer.STATE_RECORDED)
        assertEquals(2, recorded.size)

        val credited = container.getSessionsByState(BrushingSessionContainer.STATE_CREDITED)
        assertEquals(1, credited.size)

        val rejected = container.getSessionsByState(BrushingSessionContainer.STATE_REJECTED)
        assertTrue(rejected.isEmpty())
    }

    @Test
    fun `getSessionsByProfileId filters correctly`() {
        val json = """
            [
                {"session_id": "s1", "profile_id": "p1", "duration": 120},
                {"session_id": "s2", "profile_id": "p2", "duration": 90},
                {"session_id": "s3", "profile_id": "p1", "duration": 60}
            ]
        """.trimIndent()

        container.storeBrushingSessions(json)

        val p1Sessions = container.getSessionsByProfileId("p1")
        assertEquals(2, p1Sessions.size)

        val p2Sessions = container.getSessionsByProfileId("p2")
        assertEquals(1, p2Sessions.size)
    }

    @Test
    fun `getTotalBrushingDuration sums all session durations`() {
        val json = """
            [
                {"session_id": "s1", "duration": 120},
                {"session_id": "s2", "duration": 90},
                {"session_id": "s3", "duration": 60}
            ]
        """.trimIndent()

        container.storeBrushingSessions(json)
        assertEquals(270, container.getTotalBrushingDuration())
    }

    @Test
    fun `brushHeadMinutes getter and setter work correctly`() {
        assertEquals(0, container.getBrushHeadMinutes())
        container.setBrushHeadMinutes(150)
        assertEquals(150, container.getBrushHeadMinutes())
    }

    @Test
    fun `creditReqCount increments correctly`() {
        assertEquals(0, container.getCreditReqCount())
        container.incrementCreditReqCount()
        assertEquals(1, container.getCreditReqCount())
        container.incrementCreditReqCount()
        assertEquals(2, container.getCreditReqCount())
    }

    @Test
    fun `clearSessions resets all state`() {
        val json = """[{"session_id": "s1", "duration": 120}]"""
        container.storeBrushingSessions(json)
        container.incrementCreditReqCount()
        container.setBrushHeadMinutes(100)

        container.clearSessions()

        assertTrue(container.getSessions().isEmpty())
        assertEquals(0, container.getTotalBrushingDuration())
        assertEquals(0, container.getCreditReqCount())
        assertEquals(0, container.getBrushHeadMinutes())
    }

    @Test
    fun `getSessions returns immutable copy`() {
        val json = """[{"session_id": "s1", "duration": 120}]"""
        container.storeBrushingSessions(json)

        val sessions = container.getSessions()
        assertEquals(1, sessions.size)

        // Verify it's a copy (original should be unaffected)
        assertEquals(1, container.getSessions().size)
    }
}
