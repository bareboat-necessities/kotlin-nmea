package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.util.Date
import net.sf.marineapi.nmea.util.Position
import net.sf.marineapi.nmea.util.Time
import org.junit.Assert.assertEquals

/**
 * Tests the RMC sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
class RMCTest {
    var empty: RMCParser? = null
    var rmc: RMCParser? = null
    var legacy: RMCParser? = null
    @Before
    fun setUp() {
        try {
            empty = RMCParser(TalkerId.GP)
            rmc = RMCParser(EXAMPLE)
            legacy = RMCParser(EXAMPLE_LEGACY)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    @Test
    fun testConstructor() {
        assertEquals(12, empty!!.fieldCount)
        assertEquals(11, legacy!!.fieldCount)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMCParser.getCourse] .
     */
    @Test
    fun testGetCorrectedCourse() {
        val expected = rmc!!.course + rmc!!.variation
        assertEquals(expected, rmc!!.correctedCourse, 0.001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMCParser.getCourse] .
     */
    @Test
    fun testGetCourse() {
        assertEquals(360.0, rmc!!.course, 0.001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMCParser.getDataStatus].
     */
    @Test
    fun testGetDataStatus() {
        assertEquals(DataStatus.ACTIVE, rmc!!.status)
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.RMCParser.getDate].
     */
    @Test
    fun testGetDate() {
        val expected = Date(2005, 7, 16)
        val parsed: Date = rmc!!.date
        assertEquals(expected, parsed)
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.RMCParser.getDay].
     */
    @Test
    fun testGetDay() {
        assertEquals(16, rmc!!.date.getDay())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMCParser.getDirectionOfVariation]
     * .
     */
    @Test
    fun testGetDirectionOfVariation() {
        assertTrue(rmc!!.variation < 0)
        assertEquals(CompassPoint.EAST, rmc!!.directionOfVariation)
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.RMCParser.getMode].
     */
    @Test
    fun testGetFaaMode() {
        assertEquals(FaaMode.AUTOMATIC, rmc!!.mode)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMCParser.getVariation] .
     */
    @Test
    fun testGetMagneticVariation() {
        assertEquals(-6.1, rmc!!.variation, 0.001)
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.RMCParser.getMonth]
     * .
     */
    @Test
    fun testGetMonth() {
        assertEquals(7, rmc!!.date.getMonth())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMCParser.getPosition].
     */
    @Test
    fun testGetPosition() {
        val lat = 60 + 11.552 / 60
        val lon = 25 + 1.941 / 60
        val p: Position? = rmc!!.getPosition()
        assertNotNull(p)
        assertEquals(lat, p!!.latitude, 0.0000001)
        assertEquals(lon, p!!.longitude, 0.0000001)
        assertEquals(CompassPoint.NORTH, p!!.latitudeHemisphere)
        assertEquals(CompassPoint.EAST, p!!.longitudeHemisphere)
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.RMCParser.getSpeed]
     * .
     */
    @Test
    fun testGetSpeed() {
        assertEquals(0.0, rmc!!.speed, 0.001)
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.RMCParser.getTime].
     */
    @Test
    fun testGetTime() {
        val t: Time = rmc!!.time
        assertNotNull(t)
        assertEquals(12, t.getHour())
        assertEquals(0, t.getMinutes())
        assertEquals(44.567, t.getSeconds(), 0.001)
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.RMCParser.getYear].
     */
    @Test
    fun testGetYear() {
        assertEquals(2005, rmc!!.date.getYear())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMCParser.setCourse] .
     */
    @Test
    fun testSetCourse() {
        val cog = 90.55555
        rmc!!.course = cog
        assertTrue(rmc.toString().contains(",090.6,"))
        assertEquals(cog, rmc!!.course, 0.1)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMCParser.setDataStatus].
     */
    @Test
    fun testSetDataStatus() {
        rmc!!.status = DataStatus.ACTIVE
        assertEquals(DataStatus.ACTIVE, rmc!!.status)
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.ZDAParser.getTime].
     */
    @Test
    fun testSetDate() {
        rmc!!.date = Date(2010, 6, 9)
        assertTrue(rmc.toString().contains(",360.0,090610,006.1,"))
        rmc!!.date = Date(2010, 11, 12)
        assertTrue(rmc.toString().contains(",360.0,121110,006.1,"))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMCParser.getDirectionOfVariation]
     * .
     */
    @Test
    fun testSetDirectionOfVariation() {
        rmc!!.directionOfVariation = CompassPoint.WEST
        assertEquals(CompassPoint.WEST, rmc!!.directionOfVariation)
        rmc!!.directionOfVariation = CompassPoint.EAST
        assertEquals(CompassPoint.EAST, rmc!!.directionOfVariation)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMCParser.getDirectionOfVariation]
     * .
     */
    @Test
    fun testSetDirectionOfVariationWithInvalidDirection() {
        try {
            rmc!!.directionOfVariation = CompassPoint.NORTH
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMCParser.setFaaMode].
     */
    @Test
    fun testSetFaaMode() {
        rmc!!.mode = FaaMode.SIMULATED
        assertEquals(FaaMode.SIMULATED, rmc!!.mode)
        rmc!!.mode = FaaMode.ESTIMATED
        assertEquals(FaaMode.ESTIMATED, rmc!!.mode)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMCParser.setFaaMode].
     */
    @Test
    fun testSetFaaModeWhenOmitted() {
        val parser = RMCParser("\$GPRMC,120044.567,A,6011.552,N,02501.941,E,000.0,360.0,160705,006.1,E")
        parser.mode = FaaMode.SIMULATED
        assertEquals(FaaMode.SIMULATED, parser.mode)
        parser.mode = FaaMode.ESTIMATED
        assertEquals(FaaMode.ESTIMATED, parser.mode)
    }

    @Test
    fun testSetPosition() {
        val lat = 61 + 1.111 / 60
        val lon = 27 + 7.777 / 60
        val p = Position(lat, lon)
        rmc!!.setPosition(p)
        val str = rmc.toString()
        val wp: Position? = rmc!!.getPosition()
        assertTrue(str.contains(",6101.111,N,02707.777,E,"))
        assertNotNull(wp)
        assertEquals(lat, wp!!.latitude, 0.0000001)
        assertEquals(lon, wp!!.longitude, 0.0000001)
        assertEquals(CompassPoint.NORTH, wp!!.latitudeHemisphere)
        assertEquals(CompassPoint.EAST, wp!!.longitudeHemisphere)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMCParser.setSpeed] .
     */
    @Test
    fun testSetSpeed() {
        val sog = 35.23456
        rmc!!.speed = sog
        assertTrue(rmc.toString().contains(",35.2,"))
        assertEquals(sog, rmc!!.speed, 0.1)
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.RMCParser.getTime].
     */
    @Test
    fun testSetTime() {
        val t = Time(1, 2, 3.456)
        rmc!!.time = t
        assertTrue(rmc.toString().contains("\$GPRMC,010203.456,A,"))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RMCParser.setVariation] .
     */
    @Test
    fun testSetVariation() {
        val `var` = 1.55555
        rmc!!.variation = `var`
        rmc!!.directionOfVariation = CompassPoint.WEST
        assertTrue(rmc.toString().contains(",001.6,W,"))
        assertEquals(`var`, rmc!!.variation, 0.1)
        assertEquals(CompassPoint.WEST, rmc!!.directionOfVariation)
    }

    companion object {
        /** Example sentence  */
        const val EXAMPLE = "\$GPRMC,120044.567,A,6011.552,N,02501.941,E,000.0,360.0,160705,006.1,E,A*0B"

        /** Example of legacy format (short by one field)  */
        const val EXAMPLE_LEGACY = "\$GPRMC,183729,A,3907.356,N,12102.482,W,000.0,360.0,080301,015.5,E*6F"
    }
}