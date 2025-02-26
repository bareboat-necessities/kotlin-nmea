package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.RMBSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.CompassPoint
import net.sf.marineapi.nmea.util.DataStatus
import net.sf.marineapi.nmea.util.Direction
import net.sf.marineapi.nmea.util.Waypoint
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Tests the RMB sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
class RMBTest {
    private var empty: RMBSentence? = null
    private var rmb: RMBSentence? = null

    /**
     * setUp
     */
    @Before
    fun setUp() {
        try {
            empty = RMBParser(TalkerId.GP)
            rmb = RMBParser(EXAMPLE)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    @Test
    fun testConstructor() {
        assertEquals(13, empty!!.getFieldCount().toLong())
    }

    /**
     * Test method for
     * [RMBParser.getArrivalStatus].
     */
    @Test
    fun testArrivalStatus() {
        assertEquals(DataStatus.VOID, rmb!!.getArrivalStatus())
        assertFalse(rmb!!.hasArrived())
        rmb!!.setArrivalStatus(DataStatus.ACTIVE)
        assertEquals(DataStatus.ACTIVE, rmb!!.getArrivalStatus())
        assertTrue(rmb!!.hasArrived())
        rmb!!.setArrivalStatus(DataStatus.VOID)
        assertEquals(DataStatus.VOID, rmb!!.getArrivalStatus())
        assertFalse(rmb!!.hasArrived())
    }

    /**
     * Test method for
     * [RMBParser.getBearing] .
     */
    @Test
    fun testGetBearing() {
        assertEquals(234.9, rmb!!.getBearing(), 0.001)
    }

    /**
     * Test method for
     * [RMBParser.getCrossTrackError].
     */
    @Test
    fun testGetCrossTrackError() {
        assertEquals(0.0, rmb!!.getCrossTrackError(), 0.001)
    }

    /**
     * Test method for
     * [RMBParser.getDestination] .
     */
    @Test
    fun testGetDestination() {
        val id = "RUSKI"
        val lat = 55 + 36.200 / 60
        val lon = 14 + 36.500 / 60
        val wp = rmb!!.getDestination()
        assertNotNull(wp)
        assertEquals(id, wp!!.id)
        assertEquals(lat, wp.latitude, 0.0000001)
        assertEquals(lon, wp.longitude, 0.0000001)
        assertEquals(CompassPoint.NORTH, wp.latitudeHemisphere)
        assertEquals(CompassPoint.EAST, wp.longitudeHemisphere)
    }

    /**
     * Test method for
     * [RMBParser.getOriginId].
     */
    @Test
    fun testGetOriginId() {
        // FIXME test data should contain ID
        try {
            assertEquals("", rmb!!.getOriginId())
            fail("Did not throw ParseException")
        } catch (e: Exception) {
            assertTrue(e is DataNotAvailableException)
        }
    }

    /**
     * Test method for [RMBParser.getRange]
     * .
     */
    @Test
    fun testGetRange() {
        assertEquals(432.3, rmb!!.getRange(), 0.001)
    }

    /**
     * Test method for
     * [RMBParser.getStatus].
     */
    @Test
    fun testGetStatus() {
        assertEquals(DataStatus.ACTIVE, rmb!!.getStatus())
    }

    /**
     * Test method for
     * [RMBParser.getSteerTo].
     */
    @Test
    fun testGetSteerTo() {
        assertEquals(Direction.RIGHT, rmb!!.getSteerTo())
    }

    /**
     * Test method for
     * [RMBParser.getVelocity].
     */
    @Test
    fun testGetVelocity() {
        // FIXME test data should contain velocity
        try {
            assertEquals(0.0, rmb!!.getVelocity(), 0.001)
            fail("Did not throw ParseException")
        } catch (e: Exception) {
            assertTrue(e is DataNotAvailableException)
        }
    }

    /**
     * Test method for
     * [RMBParser.setBearing] .
     */
    @Test
    fun testSetBearing() {
        val brg = 90.56789
        rmb!!.setBearing(brg)
        assertTrue(rmb.toString().contains(",090.6,"))
        assertEquals(brg, rmb!!.getBearing(), 0.1)
    }

    /**
     * Test method for
     * [RMBParser.setBearing] .
     */
    @Test
    fun testSetBearingWithNegativeValue() {
        try {
            rmb!!.setBearing(-0.001)
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message!!.contains("0..360"))
        }
    }

    /**
     * Test method for
     * [RMBParser.setBearing] .
     */
    @Test
    fun testSetBearingWithValueGreaterThanAllowed() {
        try {
            rmb!!.setBearing(360.001)
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message!!.contains("0..360"))
        }
    }

    /**
     * Test method for
     * [RMBParser.setCrossTrackError]
     * .
     */
    @Test
    fun testSetCrossTrackError() {
        val xte = 2.56789
        rmb!!.setCrossTrackError(xte)
        assertTrue(rmb.toString().contains(",2.57,"))
        assertEquals(xte, rmb!!.getCrossTrackError(), 0.2)
    }

    /**
     * Test method for
     * [RMBParser.setDestination] .
     */
    @Test
    fun testSetDestination() {
        val id = "MYDEST"
        val lat = 61 + 1.111 / 60
        val lon = 27 + 7.777 / 60
        val d = Waypoint(id, lat, lon)
        rmb!!.setDestination(d)
        val str = rmb.toString()
        val wp = rmb!!.getDestination()
        assertTrue(str.contains(",MYDEST,6101.111,N,02707.777,E,"))
        assertNotNull(wp)
        assertEquals(id, wp!!.id)
        assertEquals(lat, wp.latitude, 0.0000001)
        assertEquals(lon, wp.longitude, 0.0000001)
        assertEquals(CompassPoint.NORTH, wp.latitudeHemisphere)
        assertEquals(CompassPoint.EAST, wp.longitudeHemisphere)
    }

    /**
     * Test method for
     * [RMBParser.setOriginId].
     */
    @Test
    fun testSetOriginId() {
        rmb!!.setOriginId("ORIGIN")
        assertTrue(rmb.toString().contains(",ORIGIN,RUSKI,"))
        assertEquals("ORIGIN", rmb!!.getOriginId())
    }

    /**
     * Test method for
     * [RMBParser.setRange] .
     */
    @Test
    fun testSetRange() {
        val range = 12.3456
        rmb!!.setRange(range)
        assertTrue(rmb.toString().contains(",12.3,"))
        assertEquals(range, rmb!!.getRange(), 0.1)
    }

    /**
     * Test method for
     * [RMBParser.setStatus].
     */
    @Test
    fun testSetStatus() {
        rmb!!.setStatus(DataStatus.ACTIVE)
        assertEquals(DataStatus.ACTIVE, rmb!!.getStatus())
    }

    /**
     * Test method for
     * [RMBParser.setSteerTo].
     */
    @Test
    fun testSetSteerTo() {
        rmb!!.setSteerTo(Direction.LEFT)
        assertTrue(rmb.toString().contains(",L,"))
        assertEquals(Direction.LEFT, rmb!!.getSteerTo())
    }

    /**
     * Test method for
     * [RMBParser.setSteerTo].
     */
    @Test
    fun testSetSteerToWithNull() {
        try {
            rmb!!.setSteerTo(null)
            fail("Did not throw IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message!!.contains("LEFT or RIGHT"))
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [RMBParser.setVelocity].
     */
    @Test
    fun testSetVelocity() {
        val v = 40.66666
        rmb!!.setVelocity(v)
        assertTrue(rmb.toString().contains(",40.7,"))
        assertEquals(v, rmb!!.getVelocity(), 0.1)
    }

    /**
     * Test method for
     * [RMBParser.setVelocity].
     */
    @Test
    fun testSetVelocityWithNegativeValue() {
        val v = -0.123
        rmb!!.setVelocity(v)
        assertTrue(rmb.toString().contains(",-0.1,"))
        assertEquals(v, rmb!!.getVelocity(), 0.1)
    }

    companion object {
        /** Example sentence  */
        const val EXAMPLE = "\$GPRMB,A,0.00,R,,RUSKI,5536.200,N,01436.500,E,432.3,234.9,,V*58"
    }
}