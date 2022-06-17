package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.util.Direction
import org.junit.Before

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
        assertEquals(17, empty.fieldCount)
    }

    @Test
    @Throws(Exception::class)
    fun testGetOverride() {
        assertEquals(DataStatus.VOID, htd!!.override)
    }

    @Test
    @Throws(Exception::class)
    fun testGetRudderAngle() {
        assertEquals(0.1, htd!!.rudderAngle, 0.01)
    }

    @Test
    @Throws(Exception::class)
    fun testGetRudderDirection() {
        assertEquals(Direction.RIGHT, htd!!.rudderDirection)
    }

    @Test
    @Throws(Exception::class)
    fun testGetSteeringMode() {
        assertEquals(SteeringMode.MANUAL, htd!!.steeringMode)
    }

    @Test
    @Throws(Exception::class)
    fun testGetTurnMode() {
        assertNull(htd!!.turnMode)
    }

    @Test
    @Throws(Exception::class)
    fun testGetRudderLimit() {
        assertEquals(15, htd!!.rudderLimit, 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun testGetOffHeadingLimit() {
        assertEquals(15, htd!!.offHeadingLimit, 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun testGetRadiusOfTurnForHEadingChanges() {
        assertTrue(java.lang.Double.isNaN(htd!!.radiusOfTurn))
    }

    @Test
    @Throws(Exception::class)
    fun testGetRateOfTurn() {
        assertTrue(java.lang.Double.isNaN(htd!!.rateOfTurn))
    }

    @Test
    @Throws(Exception::class)
    fun testGetHeadingToSteer() {
        assertTrue(java.lang.Double.isNaN(htd!!.headingToSteer))
    }

    @Test
    @Throws(Exception::class)
    fun testGetOffTrackLimit() {
        assertTrue(java.lang.Double.isNaN(htd!!.offTrackLimit))
    }

    @Test
    @Throws(Exception::class)
    fun testGetTrack() {
        assertTrue(java.lang.Double.isNaN(htd!!.track))
    }

    @Test
    @Throws(Exception::class)
    fun testIsHeadingTrue() {
        assertTrue(htd!!.isHeadingTrue)
    }

    @Test
    @Throws(Exception::class)
    fun testGetRudderStatus() {
        assertEquals(DataStatus.ACTIVE, htd!!.rudderStatus)
    }

    @Test
    @Throws(Exception::class)
    fun testGetOffHeadinStatus() {
        assertEquals(DataStatus.ACTIVE, htd!!.offHeadingStatus)
    }

    @Test
    @Throws(Exception::class)
    fun testGetOffTrackStatus() {
        assertEquals(DataStatus.ACTIVE, htd!!.offTrackStatus)
    }

    @Test
    @Throws(Exception::class)
    fun testGetHeading() {
        assertEquals(90.3, htd!!.heading, 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun testIsTrue() {
        assertTrue(htd!!.isTrue)
    }

    companion object {
        private const val EXAMPLE = "\$AGHTD,V,0.1,R,M,,15.0,15.0,,,,,,T,A,A,A,90.3,*39"
    }
}