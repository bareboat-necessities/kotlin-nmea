package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.APBSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.DataStatus
import net.sf.marineapi.nmea.util.Direction

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

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
        assertEquals(TalkerId.GP, apb!!.getTalkerId())
        assertEquals("APB", apb!!.getSentenceId())
        assertEquals(14, apb!!.getFieldCount().toLong())
    }

    @Test
    fun testAPBParserTalkerId() {
        assertEquals(TalkerId.AG, empty!!.getTalkerId())
        assertEquals("APB", empty!!.getSentenceId())
        assertEquals(14, empty!!.getFieldCount().toLong())
    }

    @Test
    fun testGetBearingPositionToDestination() {
        empty!!.setBearingPositionToDestination(123.45)
        assertEquals(123.5, empty!!.getBearingPositionToDestination(), 0.1)
    }

    @Test
    fun testGetBearingOriginToDestination() {
        empty!!.setBearingOriginToDestination(234.56)
        assertEquals(234.6, empty!!.getBearingOriginToDestination(), 0.1)
    }

    @Test
    fun testGetCrossTrackError() {
        empty!!.setCrossTrackError(12.345)
        assertEquals(12.3, empty!!.getCrossTrackError(), 0.1)
    }

    @Test
    fun testGetCrossTrackUnits() {
        empty!!.setCrossTrackUnits(APBSentence.NM)
        assertEquals(APBSentence.NM.code.toLong(), empty!!.getCrossTrackUnits().code.toLong())
    }

    @Test
    fun testGetCycleLockStatus() {
        empty!!.setCycleLockStatus(DataStatus.ACTIVE)
        assertEquals(DataStatus.ACTIVE, empty!!.getCycleLockStatus())
    }

    @Test
    fun testGetDestionationWaypointId() {
        empty!!.setDestinationWaypointId("WP001")
        assertEquals("WP001", empty!!.getDestionationWaypointId())
    }

    @Test
    fun testGetHeadingToDestionation() {
        empty!!.setHeadingToDestination(98.765)
        assertEquals(98.8, empty!!.getHeadingToDestionation(), 0.1)
    }

    @Test
    fun testGetStatus() {
        empty!!.setStatus(DataStatus.VOID)
        assertEquals(DataStatus.VOID, empty!!.getStatus())
    }

    @Test
    fun testGetSteerTo() {
        empty!!.setSteerTo(Direction.LEFT)
        assertEquals(Direction.LEFT, empty!!.getSteerTo())
    }

    @Test
    fun testIsArrivalCircleEntered() {
        empty!!.setArrivalCircleEntered(true)
        assertTrue(empty!!.isArrivalCircleEntered())
    }

    @Test
    fun testIsBearingOriginToDestionationTrue() {
        empty!!.setBearingOriginToDestionationTrue(true)
        assertTrue(empty!!.isBearingOriginToDestionationTrue())
    }

    @Test
    fun testIsBearingPositionToDestinationTrue() {
        empty!!.setBearingPositionToDestinationTrue(false)
        assertFalse(empty!!.isBearingPositionToDestinationTrue())
    }

    @Test
    fun testIsHeadingToDestinationTrue() {
        empty!!.setHeadingToDestinationTrue(true)
        assertTrue(empty!!.isHeadingToDestinationTrue())
    }

    @Test
    fun testIsPerpendicularPassed() {
        empty!!.setPerpendicularPassed(false)
        assertFalse(empty!!.isPerpendicularPassed())
    }

    @Test
    fun testSetArrivalCircleEntered() {
        empty!!.setArrivalCircleEntered(true)
        assertTrue(empty!!.isArrivalCircleEntered())
    }

    companion object {
        const val EXAMPLE = "\$GPAPB,A,A,0.10,R,N,V,V,011,M,DEST,011,M,011,M"
    }
}