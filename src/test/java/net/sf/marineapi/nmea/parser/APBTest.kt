package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.util.Direction
import org.junit.Assert.assertEquals

class APBTest {
    private var apb: APBSentence? = null
    private var empty: APBSentence? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        apb = APBParser(EXAMPLE)
        empty = APBParser(TalkerId.AG)
    }

    @Test
    fun testAPBParserString() {
        assertEquals(TalkerId.GP, apb.talkerId)
        assertEquals("APB", apb.sentenceId)
        assertEquals(14, apb.fieldCount)
    }

    @Test
    fun testAPBParserTalkerId() {
        assertEquals(TalkerId.AG, empty.talkerId)
        assertEquals("APB", empty.sentenceId)
        assertEquals(14, empty.fieldCount)
    }

    @Test
    fun testGetBearingPositionToDestination() {
        empty.bearingPositionToDestination = 123.45
        assertEquals(123.5, empty.bearingPositionToDestination, 0.1)
    }

    @Test
    fun testGetBearingOriginToDestination() {
        empty.bearingOriginToDestination = 234.56
        assertEquals(234.6, empty.bearingOriginToDestination, 0.1)
    }

    @Test
    fun testGetCrossTrackError() {
        empty.crossTrackError = 12.345
        assertEquals(12.3, empty.crossTrackError, 0.1)
    }

    @Test
    fun testGetCrossTrackUnits() {
        empty.crossTrackUnits = APBSentence.NM
        assertEquals(APBSentence.NM, empty.crossTrackUnits)
    }

    @Test
    fun testGetCycleLockStatus() {
        empty.cycleLockStatus = DataStatus.ACTIVE
        assertEquals(DataStatus.ACTIVE, empty.cycleLockStatus)
    }

    @Test
    fun testGetDestionationWaypointId() {
        empty.setDestinationWaypointId("WP001")
        assertEquals("WP001", empty.destionationWaypointId)
    }

    @Test
    fun testGetHeadingToDestionation() {
        empty.setHeadingToDestination(98.765)
        assertEquals(98.8, empty.headingToDestionation, 0.1)
    }

    @Test
    fun testGetStatus() {
        empty.status = DataStatus.VOID
        assertEquals(DataStatus.VOID, empty.status)
    }

    @Test
    fun testGetSteerTo() {
        empty.steerTo = Direction.LEFT
        assertEquals(Direction.LEFT, empty.steerTo)
    }

    @Test
    fun testIsArrivalCircleEntered() {
        empty.isArrivalCircleEntered = true
        assertTrue(empty.isArrivalCircleEntered)
    }

    @Test
    fun testIsBearingOriginToDestionationTrue() {
        empty.isBearingOriginToDestionationTrue = true
        assertTrue(empty.isBearingOriginToDestionationTrue)
    }

    @Test
    fun testIsBearingPositionToDestinationTrue() {
        empty.isBearingPositionToDestinationTrue = false
        assertFalse(empty.isBearingPositionToDestinationTrue)
    }

    @Test
    fun testIsHeadingToDestinationTrue() {
        empty.isHeadingToDestinationTrue = true
        assertTrue(empty.isHeadingToDestinationTrue)
    }

    @Test
    fun testIsPerpendicularPassed() {
        empty.isPerpendicularPassed = false
        assertFalse(empty.isPerpendicularPassed)
    }

    @Test
    fun testSetArrivalCircleEntered() {
        empty.isArrivalCircleEntered = true
        assertTrue(empty.isArrivalCircleEntered)
    }

    companion object {
        const val EXAMPLE = "\$GPAPB,A,A,0.10,R,N,V,V,011,M,DEST,011,M,011,M"
    }
}