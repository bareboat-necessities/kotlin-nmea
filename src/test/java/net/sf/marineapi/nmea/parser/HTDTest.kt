package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.DataStatus
import net.sf.marineapi.nmea.util.Direction
import net.sf.marineapi.nmea.util.SteeringMode
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

import kotlin.Exception
import kotlin.Throws

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
        assertEquals(17, empty.getFieldCount().toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testGetOverride() {
        assertEquals(DataStatus.VOID, htd!!.getOverride())
    }

    @Test
    @Throws(Exception::class)
    fun testGetRudderAngle() {
        assertEquals(0.1, htd!!.getRudderAngle(), 0.01)
    }

    @Test
    @Throws(Exception::class)
    fun testGetRudderDirection() {
        assertEquals(Direction.RIGHT, htd!!.getRudderDirection())
    }

    @Test
    @Throws(Exception::class)
    fun testGetSteeringMode() {
        assertEquals(SteeringMode.MANUAL, htd!!.getSteeringMode())
    }

    @Test
    @Throws(Exception::class)
    fun testGetTurnMode() {
        assertNull(htd!!.getTurnMode())
    }

    @Test
    @Throws(Exception::class)
    fun testGetRudderLimit() {
        assertEquals(15.0, htd!!.getRudderLimit(), 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun testGetOffHeadingLimit() {
        assertEquals(15.0, htd!!.getOffHeadingLimit(), 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun testGetRadiusOfTurnForHEadingChanges() {
        assertTrue((htd!!.getRadiusOfTurn()).isNaN())
    }

    @Test
    @Throws(Exception::class)
    fun testGetRateOfTurn() {
        assertTrue((htd!!.getRateOfTurn()).isNaN())
    }

    @Test
    @Throws(Exception::class)
    fun testGetHeadingToSteer() {
        assertTrue((htd!!.getHeadingToSteer()).isNaN())
    }

    @Test
    @Throws(Exception::class)
    fun testGetOffTrackLimit() {
        assertTrue((htd!!.getOffTrackLimit()).isNaN())
    }

    @Test
    @Throws(Exception::class)
    fun testGetTrack() {
        assertTrue((htd!!.getTrack()).isNaN())
    }

    @Test
    @Throws(Exception::class)
    fun testIsHeadingTrue() {
        assertTrue(htd!!.isHeadingTrue())
    }

    @Test
    @Throws(Exception::class)
    fun testGetRudderStatus() {
        assertEquals(DataStatus.ACTIVE, htd!!.getRudderStatus())
    }

    @Test
    @Throws(Exception::class)
    fun testGetOffHeadinStatus() {
        assertEquals(DataStatus.ACTIVE, htd!!.getOffHeadingStatus())
    }

    @Test
    @Throws(Exception::class)
    fun testGetOffTrackStatus() {
        assertEquals(DataStatus.ACTIVE, htd!!.getOffTrackStatus())
    }

    @Test
    @Throws(Exception::class)
    fun testGetHeading() {
        assertEquals(90.3, htd!!.getHeading(), 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun testIsTrue() {
        assertTrue(htd!!.isTrue())
    }

    companion object {
        private const val EXAMPLE = "\$AGHTD,V,0.1,R,M,,15.0,15.0,,,,,,T,A,A,A,90.3,*39"
    }
}