package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.*

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Test the GGA sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
class GGATest {
    private var gga: GGAParser? = null
    private var empty: GGAParser? = null
    @Before
    fun setUp() {
        try {
            empty = GGAParser(TalkerId.GP)
            gga = GGAParser(EXAMPLE)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    @Test
    fun testConstructor() {
        assertEquals(14, empty!!.getFieldCount().toLong())
    }

    @Test
    fun testGetAltitude() {
        assertEquals(28.0, gga!!.getAltitude(), 0.001)
    }

    @Test
    fun testGetAltitudeUnits() {
        assertEquals(Units.METER, gga!!.getAltitudeUnits())
    }

    @Test
    fun testGetDgpsAge() {
        try {
            gga!!.getDgpsAge()
            fail("Did not throw ParseException")
        } catch (e: DataNotAvailableException) {
            // ok
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun testGetDgpsStationId() {
        try {
            gga!!.getDgpsStationId()
            fail("Did not throw ParseException")
        } catch (e: DataNotAvailableException) {
            // ok
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    @Test
    fun testGetFixQuality() {
        assertEquals(GpsFixQuality.NORMAL, gga!!.getFixQuality())
    }

    @Test
    fun testGetGeoidalHeight() {
        assertEquals(19.6, gga!!.getGeoidalHeight(), 0.001)
    }

    @Test
    fun testGetGeoidalHeightUnits() {
        assertEquals(Units.METER, gga!!.getGeoidalHeightUnits())
    }

    @Test
    fun testGetHorizontalDOP() {
        assertEquals(2.0, gga!!.getHorizontalDOP(), 0.001)
    }

    @Test
    fun testGetNumberOfSatellites() {
        assertEquals(0, gga!!.getSatelliteCount().toLong())
    }

    @Test
    fun testGetPosition() {
        // expected lat/lon values
        val lat = 60 + 11.552 / 60
        val lon = 25 + 1.941 / 60
        val alt = 28.0
        val p = gga!!.getPosition()
        assertNotNull(p)
        assertEquals(lat, p.latitude, 0.0000001)
        assertEquals(CompassPoint.NORTH, p.latitudeHemisphere)
        assertEquals(lon, p.longitude, 0.0000001)
        assertEquals(CompassPoint.EAST, p.longitudeHemisphere)
        assertEquals(Datum.WGS84, p.datum)
        assertEquals(alt, p.altitude, 0.01)
    }

    @Test
    fun testGetTime() {
        val t = gga!!.getTime()
        assertNotNull(t)
        assertEquals(12, t.getHour().toLong())
        assertEquals(0, t.getMinutes().toLong())
        assertEquals(44.567, t.getSeconds(), 0.001)
    }

    /**
     *
     */
    @Test
    fun testGGAParser() {
        val instance = GGAParser(EXAMPLE)
        val sid = SentenceId.valueOf(instance.getSentenceId())
        assertEquals(SentenceId.GGA, sid)
    }

    @Test
    fun testSetAltitude() {
        val alt = 11.11111
        gga!!.setAltitude(alt)
        assertEquals(alt, gga!!.getAltitude(), 0.1)
    }

    @Test
    fun testSetAltitudeUnits() {
        assertEquals(Units.METER, gga!!.getAltitudeUnits())
        gga!!.setAltitudeUnits(Units.FEET)
        assertEquals(Units.FEET, gga!!.getAltitudeUnits())
    }

    @Test
    fun testSetDgpsAge() {
        val age = 33.333333
        gga!!.setDgpsAge(age)
        assertEquals(age, gga!!.getDgpsAge(), 0.1)
    }

    @Test
    fun testSetDgpsStationId() {
        gga!!.setDgpsStationId("0001")
        assertEquals("0001", gga!!.getDgpsStationId())
    }

    @Test
    fun testSetFixQuality() {
        assertEquals(GpsFixQuality.NORMAL, gga!!.getFixQuality())
        gga!!.setFixQuality(GpsFixQuality.INVALID)
        assertEquals(GpsFixQuality.INVALID, gga!!.getFixQuality())
    }

    @Test
    fun testSetGeoidalHeight() {
        val height = 3.987654
        gga!!.setGeoidalHeight(height)
        assertEquals(height, gga!!.getGeoidalHeight(), 0.1)
    }

    @Test
    fun testSetGeoidalHeightUnits() {
        assertEquals(Units.METER, gga!!.getGeoidalHeightUnits())
        gga!!.setGeoidalHeightUnits(Units.FEET)
        assertEquals(Units.FEET, gga!!.getGeoidalHeightUnits())
    }

    @Test
    fun testSetHorizontalDOP() {
        val hdop = 0.123456
        gga!!.setHorizontalDOP(hdop)
        assertEquals(hdop, gga!!.getHorizontalDOP(), 0.1)
    }

    @Test
    fun testSetPosition() {
        val lat = 61 + 1.111 / 60
        val lon = 27 + 7.777 / 60
        val alt = 11.1
        val p = Position(lat, lon)
        p.altitude = alt
        gga!!.setPosition(p)
        val str = gga.toString()
        assertTrue(str.contains(",6101.111,N,"))
        assertTrue(str.contains(",02707.777,E,"))
        val wp = gga!!.getPosition()
        assertNotNull(wp)
        assertEquals(lat, wp.latitude, 0.0000001)
        assertEquals(lon, wp.longitude, 0.0000001)
        assertEquals(CompassPoint.NORTH, wp.latitudeHemisphere)
        assertEquals(CompassPoint.EAST, wp.longitudeHemisphere)
        assertEquals(alt, wp.altitude, 0.01)
    }

    /**
     * Test method for [GGAParser.getTime].
     */
    @Test
    fun testSetTime() {
        val t = Time(1, 2, 3.456)
        gga!!.setTime(t)
        assertTrue(gga.toString().contains("GPGGA,010203.456,6011"))
    }

    @Test
    fun testSetNumberOfSatellites() {
        gga!!.setSatelliteCount(5)
        assertEquals(5, gga!!.getSatelliteCount().toLong())
        assertTrue(gga.toString().contains(",E,1,05,2.0,28.0,"))
    }

    @Test
    fun testSetNumberOfSatellitesThrows() {
        try {
            gga!!.setSatelliteCount(-1)
            fail("setSatelliteCount() did not throw exception")
        } catch (e: IllegalArgumentException) {
            assertEquals(0, gga!!.getSatelliteCount().toLong())
            assertEquals("Satelite count cannot be negative", e.message)
        }
    }

    companion object {
        const val EXAMPLE = "\$GPGGA,120044.567,6011.552,N,02501.941,E,1,00,2.0,28.0,M,19.6,M,,*63"
    }
}