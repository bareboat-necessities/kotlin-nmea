package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.APBSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.DataStatus
import net.sf.marineapi.nmea.util.Direction
import org.junit.Assert
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
        Assert.assertEquals(TalkerId.GP, apb!!.getTalkerId())
        Assert.assertEquals("APB", apb!!.getSentenceId())
        Assert.assertEquals(14, apb!!.getFieldCount().toLong())
    }

    @Test
    fun testAPBParserTalkerId() {
        Assert.assertEquals(TalkerId.AG, empty!!.getTalkerId())
        Assert.assertEquals("APB", empty!!.getSentenceId())
        Assert.assertEquals(14, empty!!.getFieldCount().toLong())
    }

    @Test
    fun testGetBearingPositionToDestination() {
        empty!!.setBearingPositionToDestination(123.45)
        Assert.assertEquals(123.5, empty!!.getBearingPositionToDestination(), 0.1)
    }

    @Test
    fun testGetBearingOriginToDestination() {
        empty!!.setBearingOriginToDestination(234.56)
        Assert.assertEquals(234.6, empty!!.getBearingOriginToDestination(), 0.1)
    }

    @Test
    fun testGetCrossTrackError() {
        empty!!.setCrossTrackError(12.345)
        Assert.assertEquals(12.3, empty!!.getCrossTrackError(), 0.1)
    }

    @Test
    fun testGetCrossTrackUnits() {
        empty!!.setCrossTrackUnits(APBSentence.NM)
        Assert.assertEquals(APBSentence.NM.code.toLong(), empty!!.getCrossTrackUnits().code.toLong())
    }

    @Test
    fun testGetCycleLockStatus() {
        empty!!.setCycleLockStatus(DataStatus.ACTIVE)
        Assert.assertEquals(DataStatus.ACTIVE, empty!!.getCycleLockStatus())
    }

    @Test
    fun testGetDestionationWaypointId() {
        empty!!.setDestinationWaypointId("WP001")
        Assert.assertEquals("WP001", empty!!.getDestionationWaypointId())
    }

    @Test
    fun testGetHeadingToDestionation() {
        empty!!.setHeadingToDestination(98.765)
        Assert.assertEquals(98.8, empty!!.getHeadingToDestionation(), 0.1)
    }

    @Test
    fun testGetStatus() {
        empty!!.setStatus(DataStatus.VOID)
        Assert.assertEquals(DataStatus.VOID, empty!!.getStatus())
    }

    @Test
    fun testGetSteerTo() {
        empty!!.setSteerTo(Direction.LEFT)
        Assert.assertEquals(Direction.LEFT, empty!!.getSteerTo())
    }

    @Test
    fun testIsArrivalCircleEntered() {
        empty!!.setArrivalCircleEntered(true)
        Assert.assertTrue(empty!!.isArrivalCircleEntered())
    }

    @Test
    fun testIsBearingOriginToDestionationTrue() {
        empty!!.setBearingOriginToDestionationTrue(true)
        Assert.assertTrue(empty!!.isBearingOriginToDestionationTrue())
    }

    @Test
    fun testIsBearingPositionToDestinationTrue() {
        empty!!.setBearingPositionToDestinationTrue(false)
        Assert.assertFalse(empty!!.isBearingPositionToDestinationTrue())
    }

    @Test
    fun testIsHeadingToDestinationTrue() {
        empty!!.setHeadingToDestinationTrue(true)
        Assert.assertTrue(empty!!.isHeadingToDestinationTrue())
    }

    @Test
    fun testIsPerpendicularPassed() {
        empty!!.setPerpendicularPassed(false)
        Assert.assertFalse(empty!!.isPerpendicularPassed())
    }

    @Test
    fun testSetArrivalCircleEntered() {
        empty!!.setArrivalCircleEntered(true)
        Assert.assertTrue(empty!!.isArrivalCircleEntered())
    }

    companion object {
        const val EXAMPLE = "\$GPAPB,A,A,0.10,R,N,V,V,011,M,DEST,011,M,011,M"
    }
}