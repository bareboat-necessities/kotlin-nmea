package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.util.Direction
import org.junit.Assert.assertEquals

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
        assertEquals(13, empty.fieldCount)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMBParser.getArrivalStatus].
     */
    @Test
    fun testArrivalStatus() {
        assertEquals(DataStatus.VOID, rmb.arrivalStatus)
        assertFalse(rmb.hasArrived())
        rmb.arrivalStatus = DataStatus.ACTIVE
        assertEquals(DataStatus.ACTIVE, rmb.arrivalStatus)
        assertTrue(rmb.hasArrived())
        rmb.arrivalStatus = DataStatus.VOID
        assertEquals(DataStatus.VOID, rmb.arrivalStatus)
        assertFalse(rmb.hasArrived())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMBParser.getBearing] .
     */
    @Test
    fun testGetBearing() {
        assertEquals(234.9, rmb.bearing, 0.001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMBParser.getCrossTrackError].
     */
    @Test
    fun testGetCrossTrackError() {
        assertEquals(0.0, rmb.crossTrackError, 0.001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMBParser.getDestination] .
     */
    @Test
    fun testGetDestination() {
        val id = "RUSKI"
        val lat = 55 + 36.200 / 60
        val lon = 14 + 36.500 / 60
        val wp: Waypoint = rmb.getDestination()
        assertNotNull(wp)
        assertEquals(id, wp.id)
        assertEquals(lat, wp.latitude, 0.0000001)
        assertEquals(lon, wp.longitude, 0.0000001)
        assertEquals(CompassPoint.NORTH, wp.latitudeHemisphere)
        assertEquals(CompassPoint.EAST, wp.longitudeHemisphere)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMBParser.getOriginId].
     */
    @Test
    fun testGetOriginId() {
        // FIXME test data should contain ID
        try {
            assertEquals("", rmb.originId)
            fail("Did not throw ParseException")
        } catch (e: Exception) {
            assertTrue(e is DataNotAvailableException)
        }
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.RMBParser.getRange]
     * .
     */
    @Test
    fun testGetRange() {
        assertEquals(432.3, rmb.range, 0.001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMBParser.getStatus].
     */
    @Test
    fun testGetStatus() {
        assertEquals(DataStatus.ACTIVE, rmb.status)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMBParser.getSteerTo].
     */
    @Test
    fun testGetSteerTo() {
        assertEquals(Direction.RIGHT, rmb.steerTo)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMBParser.getVelocity].
     */
    @Test
    fun testGetVelocity() {
        // FIXME test data should contain velocity
        try {
            assertEquals(0.0, rmb.velocity, 0.001)
            fail("Did not throw ParseException")
        } catch (e: Exception) {
            assertTrue(e is DataNotAvailableException)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMBParser.setBearing] .
     */
    @Test
    fun testSetBearing() {
        val brg = 90.56789
        rmb.bearing = brg
        assertTrue(rmb.toString().contains(",090.6,"))
        assertEquals(brg, rmb.bearing, 0.1)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMBParser.setBearing] .
     */
    @Test
    fun testSetBearingWithNegativeValue() {
        try {
            rmb.bearing = -0.001
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message!!.contains("0..360"))
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMBParser.setBearing] .
     */
    @Test
    fun testSetBearingWithValueGreaterThanAllowed() {
        try {
            rmb.bearing = 360.001
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message!!.contains("0..360"))
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMBParser.setCrossTrackError]
     * .
     */
    @Test
    fun testSetCrossTrackError() {
        val xte = 2.56789
        rmb.crossTrackError = xte
        assertTrue(rmb.toString().contains(",2.57,"))
        assertEquals(xte, rmb.crossTrackError, 0.2)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMBParser.setDestination] .
     */
    @Test
    fun testSetDestination() {
        val id = "MYDEST"
        val lat = 61 + 1.111 / 60
        val lon = 27 + 7.777 / 60
        val d = Waypoint(id, lat, lon)
        rmb.setDestination(d)
        val str: String = rmb.toString()
        val wp: Waypoint = rmb.getDestination()
        assertTrue(str.contains(",MYDEST,6101.111,N,02707.777,E,"))
        assertNotNull(wp)
        assertEquals(id, wp.id)
        assertEquals(lat, wp.latitude, 0.0000001)
        assertEquals(lon, wp.longitude, 0.0000001)
        assertEquals(CompassPoint.NORTH, wp.latitudeHemisphere)
        assertEquals(CompassPoint.EAST, wp.longitudeHemisphere)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMBParser.setOriginId].
     */
    @Test
    fun testSetOriginId() {
        rmb.originId = "ORIGIN"
        assertTrue(rmb.toString().contains(",ORIGIN,RUSKI,"))
        assertEquals("ORIGIN", rmb.originId)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMBParser.setRange] .
     */
    @Test
    fun testSetRange() {
        val range = 12.3456
        rmb.range = range
        assertTrue(rmb.toString().contains(",12.3,"))
        assertEquals(range, rmb.range, 0.1)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMBParser.setStatus].
     */
    @Test
    fun testSetStatus() {
        rmb.status = DataStatus.ACTIVE
        assertEquals(DataStatus.ACTIVE, rmb.status)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMBParser.setSteerTo].
     */
    @Test
    fun testSetSteerTo() {
        rmb.steerTo = Direction.LEFT
        assertTrue(rmb.toString().contains(",L,"))
        assertEquals(Direction.LEFT, rmb.steerTo)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMBParser.setSteerTo].
     */
    @Test
    fun testSetSteerToWithNull() {
        try {
            rmb.steerTo = null
            fail("Did not throw IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message!!.contains("LEFT or RIGHT"))
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMBParser.setVelocity].
     */
    @Test
    fun testSetVelocity() {
        val v = 40.66666
        rmb.velocity = v
        assertTrue(rmb.toString().contains(",40.7,"))
        assertEquals(v, rmb.velocity, 0.1)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMBParser.setVelocity].
     */
    @Test
    fun testSetVelocityWithNegativeValue() {
        val v = -0.123
        rmb.velocity = v
        assertTrue(rmb.toString().contains(",-0.1,"))
        assertEquals(v, rmb.velocity, 0.1)
    }

    companion object {
        /** Example sentence  */
        const val EXAMPLE = "\$GPRMB,A,0.00,R,,RUSKI,5536.200,N,01436.500,E,432.3,234.9,,V*58"
    }
}