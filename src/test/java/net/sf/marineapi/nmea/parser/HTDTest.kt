package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.DataStatus
import net.sf.marineapi.nmea.util.Direction
import net.sf.marineapi.nmea.util.SteeringMode
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class HTDTest {
    private var htd: HTDParser? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        htd = HTDParser(EXAMPLE)
    }

    @Test
    @Throws(Exception::class)
    fun testConstructor() {
        val empty = HTDParser(TalkerId.AG)
        Assert.assertEquals(17, empty.getFieldCount().toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testGetOverride() {
        Assert.assertEquals(DataStatus.VOID, htd!!.getOverride())
    }

    @Test
    @Throws(Exception::class)
    fun testGetRudderAngle() {
        Assert.assertEquals(0.1, htd!!.getRudderAngle(), 0.01)
    }

    @Test
    @Throws(Exception::class)
    fun testGetRudderDirection() {
        Assert.assertEquals(Direction.RIGHT, htd!!.getRudderDirection())
    }

    @Test
    @Throws(Exception::class)
    fun testGetSteeringMode() {
        Assert.assertEquals(SteeringMode.MANUAL, htd!!.getSteeringMode())
    }

    @Test
    @Throws(Exception::class)
    fun testGetTurnMode() {
        Assert.assertNull(htd!!.getTurnMode())
    }

    @Test
    @Throws(Exception::class)
    fun testGetRudderLimit() {
        Assert.assertEquals(15.0, htd!!.getRudderLimit(), 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun testGetOffHeadingLimit() {
        Assert.assertEquals(15.0, htd!!.getOffHeadingLimit(), 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun testGetRadiusOfTurnForHEadingChanges() {
        Assert.assertTrue(java.lang.Double.isNaN(htd!!.getRadiusOfTurn()))
    }

    @Test
    @Throws(Exception::class)
    fun testGetRateOfTurn() {
        Assert.assertTrue(java.lang.Double.isNaN(htd!!.getRateOfTurn()))
    }

    @Test
    @Throws(Exception::class)
    fun testGetHeadingToSteer() {
        Assert.assertTrue(java.lang.Double.isNaN(htd!!.getHeadingToSteer()))
    }

    @Test
    @Throws(Exception::class)
    fun testGetOffTrackLimit() {
        Assert.assertTrue(java.lang.Double.isNaN(htd!!.getOffTrackLimit()))
    }

    @Test
    @Throws(Exception::class)
    fun testGetTrack() {
        Assert.assertTrue(java.lang.Double.isNaN(htd!!.getTrack()))
    }

    @Test
    @Throws(Exception::class)
    fun testIsHeadingTrue() {
        Assert.assertTrue(htd!!.isHeadingTrue())
    }

    @Test
    @Throws(Exception::class)
    fun testGetRudderStatus() {
        Assert.assertEquals(DataStatus.ACTIVE, htd!!.getRudderStatus())
    }

    @Test
    @Throws(Exception::class)
    fun testGetOffHeadinStatus() {
        Assert.assertEquals(DataStatus.ACTIVE, htd!!.getOffHeadingStatus())
    }

    @Test
    @Throws(Exception::class)
    fun testGetOffTrackStatus() {
        Assert.assertEquals(DataStatus.ACTIVE, htd!!.getOffTrackStatus())
    }

    @Test
    @Throws(Exception::class)
    fun testGetHeading() {
        Assert.assertEquals(90.3, htd!!.getHeading(), 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun testIsTrue() {
        Assert.assertTrue(htd!!.isTrue())
    }

    companion object {
        private const val EXAMPLE = "\$AGHTD,V,0.1,R,M,,15.0,15.0,,,,,,T,A,A,A,90.3,*39"
    }
}