package net.sf.marineapi.nmea.parser

import java.util.Date
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Tests the RMC sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
class RMCTest {
    private var empty: RMCParser? = null
    private var rmc: RMCParser? = null
    private var legacy: RMCParser? = null

    @Before
    fun setUp() {
        try {
            empty = RMCParser(TalkerId.GP)
            rmc = RMCParser(EXAMPLE)
            legacy = RMCParser(EXAMPLE_LEGACY)
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    @Test
    fun testConstructor() {
        Assert.assertEquals(12, empty!!.getFieldCount().toLong())
        Assert.assertEquals(11, legacy!!.getFieldCount().toLong())
    }

    /**
     * Test method for
     * [RMCParser.getCourse] .
     */
    @Test
    fun testGetCorrectedCourse() {
        val expected = rmc!!.getCourse() + rmc!!.getVariation()
        Assert.assertEquals(expected, rmc!!.getCorrectedCourse(), 0.001)
    }

    /**
     * Test method for
     * [RMCParser.getCourse] .
     */
    @Test
    fun testGetCourse() {
        Assert.assertEquals(360.0, rmc!!.getCourse(), 0.001)
    }

    /**
     * Test method for
     * [RMCParser.getDataStatus].
     */
    @Test
    fun testGetDataStatus() {
        Assert.assertEquals(DataStatus.ACTIVE, rmc!!.getStatus())
    }

    /**
     * Test method for [RMCParser.getDate].
     */
    @Test
    fun testGetDate() {
        val expected = net.sf.marineapi.nmea.util.Date(2005, 7, 16)
        val parsed = rmc!!.getDate()
        Assert.assertEquals(expected, parsed)
    }

    /**
     * Test method for [RMCParser.getDay].
     */
    @Test
    fun testGetDay() {
        Assert.assertEquals(16, rmc!!.getDate().getDay().toLong())
    }

    /**
     * Test method for
     * [RMCParser.getDirectionOfVariation]
     * .
     */
    @Test
    fun testGetDirectionOfVariation() {
        Assert.assertTrue(rmc!!.getVariation() < 0)
        Assert.assertEquals(CompassPoint.EAST, rmc!!.getDirectionOfVariation())
    }

    /**
     * Test method for [RMCParser.getMode].
     */
    @Test
    fun testGetFaaMode() {
        Assert.assertEquals(FaaMode.AUTOMATIC, rmc!!.getMode())
    }

    /**
     * Test method for
     * [RMCParser.getVariation] .
     */
    @Test
    fun testGetMagneticVariation() {
        Assert.assertEquals(-6.1, rmc!!.getVariation(), 0.001)
    }

    /**
     * Test method for [RMCParser.getMonth]
     * .
     */
    @Test
    fun testGetMonth() {
        Assert.assertEquals(7, rmc!!.getDate().getMonth().toLong())
    }

    /**
     * Test method for
     * [RMCParser.getPosition].
     */
    @Test
    fun testGetPosition() {
        val lat = 60 + 11.552 / 60
        val lon = 25 + 1.941 / 60
        val p = rmc!!.getPosition()
        Assert.assertNotNull(p)
        Assert.assertEquals(lat, p.latitude, 0.0000001)
        Assert.assertEquals(lon, p.longitude, 0.0000001)
        Assert.assertEquals(CompassPoint.NORTH, p.latitudeHemisphere)
        Assert.assertEquals(CompassPoint.EAST, p.longitudeHemisphere)
    }

    /**
     * Test method for [RMCParser.getSpeed]
     * .
     */
    @Test
    fun testGetSpeed() {
        Assert.assertEquals(0.0, rmc!!.getSpeed(), 0.001)
    }

    /**
     * Test method for [RMCParser.getTime].
     */
    @Test
    fun testGetTime() {
        val t = rmc!!.getTime()
        Assert.assertNotNull(t)
        Assert.assertEquals(12, t.getHour().toLong())
        Assert.assertEquals(0, t.getMinutes().toLong())
        Assert.assertEquals(44.567, t.getSeconds(), 0.001)
    }

    /**
     * Test method for [RMCParser.getYear].
     */
    @Test
    fun testGetYear() {
        Assert.assertEquals(2005, rmc!!.getDate().getYear().toLong())
    }

    /**
     * Test method for
     * [RMCParser.setCourse] .
     */
    @Test
    fun testSetCourse() {
        val cog = 90.55555
        rmc!!.setCourse(cog)
        Assert.assertTrue(rmc.toString().contains(",090.6,"))
        Assert.assertEquals(cog, rmc!!.getCourse(), 0.1)
    }

    /**
     * Test method for
     * [RMCParser.setDataStatus].
     */
    @Test
    fun testSetDataStatus() {
        rmc!!.setStatus(DataStatus.ACTIVE)
        Assert.assertEquals(DataStatus.ACTIVE, rmc!!.getStatus())
    }

    /**
     * Test method for [ZDAParser.getTime].
     */
    @Test
    fun testSetDate() {
        rmc!!.setDate(net.sf.marineapi.nmea.util.Date(2010, 6, 9))
        Assert.assertTrue(rmc.toString().contains(",360.0,090610,006.1,"))
        rmc!!.setDate(net.sf.marineapi.nmea.util.Date(2010, 11, 12))
        Assert.assertTrue(rmc.toString().contains(",360.0,121110,006.1,"))
    }

    /**
     * Test method for
     * [RMCParser.getDirectionOfVariation]
     * .
     */
    @Test
    fun testSetDirectionOfVariation() {
        rmc!!.setDirectionOfVariation(CompassPoint.WEST)
        Assert.assertEquals(CompassPoint.WEST, rmc!!.getDirectionOfVariation())
        rmc!!.setDirectionOfVariation(CompassPoint.EAST)
        Assert.assertEquals(CompassPoint.EAST, rmc!!.getDirectionOfVariation())
    }

    /**
     * Test method for
     * [RMCParser.getDirectionOfVariation]
     * .
     */
    @Test
    fun testSetDirectionOfVariationWithInvalidDirection() {
        try {
            rmc!!.setDirectionOfVariation(CompassPoint.NORTH)
            Assert.fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    /**
     * Test method for
     * [RMCParser.setFaaMode].
     */
    @Test
    fun testSetFaaMode() {
        rmc!!.setMode(FaaMode.SIMULATED)
        Assert.assertEquals(FaaMode.SIMULATED, rmc!!.getMode())
        rmc!!.setMode(FaaMode.ESTIMATED)
        Assert.assertEquals(FaaMode.ESTIMATED, rmc!!.getMode())
    }

    /**
     * Test method for
     * [RMCParser.setFaaMode].
     */
    @Test
    fun testSetFaaModeWhenOmitted() {
        val parser = RMCParser("\$GPRMC,120044.567,A,6011.552,N,02501.941,E,000.0,360.0,160705,006.1,E")
        parser.setMode(FaaMode.SIMULATED)
        Assert.assertEquals(FaaMode.SIMULATED, parser.getMode())
        parser.setMode(FaaMode.ESTIMATED)
        Assert.assertEquals(FaaMode.ESTIMATED, parser.getMode())
    }

    @Test
    fun testSetPosition() {
        val lat = 61 + 1.111 / 60
        val lon = 27 + 7.777 / 60
        val p = Position(lat, lon)
        rmc!!.setPosition(p)
        val str = rmc.toString()
        val wp = rmc!!.getPosition()
        Assert.assertTrue(str.contains(",6101.111,N,02707.777,E,"))
        Assert.assertNotNull(wp)
        Assert.assertEquals(lat, wp.latitude, 0.0000001)
        Assert.assertEquals(lon, wp.longitude, 0.0000001)
        Assert.assertEquals(CompassPoint.NORTH, wp.latitudeHemisphere)
        Assert.assertEquals(CompassPoint.EAST, wp.longitudeHemisphere)
    }

    /**
     * Test method for
     * [RMCParser.setSpeed] .
     */
    @Test
    fun testSetSpeed() {
        val sog = 35.23456
        rmc!!.setSpeed(sog)
        Assert.assertTrue(rmc.toString().contains(",35.2,"))
        Assert.assertEquals(sog, rmc!!.getSpeed(), 0.1)
    }

    /**
     * Test method for [RMCParser.getTime].
     */
    @Test
    fun testSetTime() {
        val t = Time(1, 2, 3.456)
        rmc!!.setTime(t)
        Assert.assertTrue(rmc.toString().contains("\$GPRMC,010203.456,A,"))
    }

    /**
     * Test method for
     * [RMCParser.setVariation] .
     */
    @Test
    fun testSetVariation() {
        val `var` = 1.55555
        rmc!!.setVariation(`var`)
        rmc!!.setDirectionOfVariation(CompassPoint.WEST)
        Assert.assertTrue(rmc.toString().contains(",001.6,W,"))
        Assert.assertEquals(`var`, rmc!!.getVariation(), 0.1)
        Assert.assertEquals(CompassPoint.WEST, rmc!!.getDirectionOfVariation())
    }

    companion object {
        /** Example sentence  */
        const val EXAMPLE = "\$GPRMC,120044.567,A,6011.552,N,02501.941,E,000.0,360.0,160705,006.1,E,A*0B"

        /** Example of legacy format (short by one field)  */
        const val EXAMPLE_LEGACY = "\$GPRMC,183729,A,3907.356,N,12102.482,W,000.0,360.0,080301,015.5,E*6F"
    }
}