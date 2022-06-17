package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.util.Position
import net.sf.marineapi.nmea.util.Time
import net.sf.marineapi.nmea.util.Units
import org.junit.Assert.assertEquals

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
        assertEquals(14, empty!!.fieldCount)
    }

    @Test
    fun testGetAltitude() {
        assertEquals(28.0, gga!!.altitude, 0.001)
    }

    @Test
    fun testGetAltitudeUnits() {
        assertEquals(Units.METER, gga!!.altitudeUnits)
    }

    @Test
    fun testGetDgpsAge() {
        try {
            gga!!.dgpsAge
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
            gga!!.dgpsStationId
            fail("Did not throw ParseException")
        } catch (e: DataNotAvailableException) {
            // ok
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    @Test
    fun testGetFixQuality() {
        assertEquals(GpsFixQuality.NORMAL, gga!!.fixQuality)
    }

    @Test
    fun testGetGeoidalHeight() {
        assertEquals(19.6, gga!!.geoidalHeight, 0.001)
    }

    @Test
    fun testGetGeoidalHeightUnits() {
        assertEquals(Units.METER, gga!!.geoidalHeightUnits)
    }

    @Test
    fun testGetHorizontalDOP() {
        assertEquals(2.0, gga!!.horizontalDOP, 0.001)
    }

    @Test
    fun testGetNumberOfSatellites() {
        assertEquals(0, gga!!.satelliteCount)
    }

    @Test
    fun testGetPosition() {
        // expected lat/lon values
        val lat = 60 + 11.552 / 60
        val lon = 25 + 1.941 / 60
        val alt = 28.0
        val p: Position? = gga!!.getPosition()
        assertNotNull(p)
        assertEquals(lat, p!!.latitude, 0.0000001)
        assertEquals(CompassPoint.NORTH, p!!.latitudeHemisphere)
        assertEquals(lon, p!!.longitude, 0.0000001)
        assertEquals(CompassPoint.EAST, p!!.longitudeHemisphere)
        assertEquals(Datum.WGS84, p!!.datum)
        assertEquals(alt, p!!.altitude, 0.01)
    }

    @Test
    fun testGetTime() {
        val t: Time = gga!!.time
        assertNotNull(t)
        assertEquals(12, t.getHour())
        assertEquals(0, t.getMinutes())
        assertEquals(44.567, t.getSeconds(), 0.001)
    }

    /**
     *
     */
    @Test
    fun testGGAParser() {
        val instance = GGAParser(EXAMPLE)
        val sid: SentenceId = SentenceId.valueOf(instance.sentenceId)
        assertEquals(SentenceId.GGA, sid)
    }

    @Test
    fun testSetAltitude() {
        val alt = 11.11111
        gga!!.altitude = alt
        assertEquals(alt, gga!!.altitude, 0.1)
    }

    @Test
    fun testSetAltitudeUnits() {
        assertEquals(Units.METER, gga!!.altitudeUnits)
        gga!!.altitudeUnits = Units.FEET
        assertEquals(Units.FEET, gga!!.altitudeUnits)
    }

    @Test
    fun testSetDgpsAge() {
        val age = 33.333333
        gga!!.dgpsAge = age
        assertEquals(age, gga!!.dgpsAge, 0.1)
    }

    @Test
    fun testSetDgpsStationId() {
        gga!!.dgpsStationId = "0001"
        assertEquals("0001", gga!!.dgpsStationId)
    }

    @Test
    fun testSetFixQuality() {
        assertEquals(GpsFixQuality.NORMAL, gga!!.fixQuality)
        gga!!.fixQuality = GpsFixQuality.INVALID
        assertEquals(GpsFixQuality.INVALID, gga!!.fixQuality)
    }

    @Test
    fun testSetGeoidalHeight() {
        val height = 3.987654
        gga!!.geoidalHeight = height
        assertEquals(height, gga!!.geoidalHeight, 0.1)
    }

    @Test
    fun testSetGeoidalHeightUnits() {
        assertEquals(Units.METER, gga!!.geoidalHeightUnits)
        gga!!.geoidalHeightUnits = Units.FEET
        assertEquals(Units.FEET, gga!!.geoidalHeightUnits)
    }

    @Test
    fun testSetHorizontalDOP() {
        val hdop = 0.123456
        gga!!.horizontalDOP = hdop
        assertEquals(hdop, gga!!.horizontalDOP, 0.1)
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
        val wp: Position? = gga!!.getPosition()
        assertNotNull(wp)
        assertEquals(lat, wp!!.latitude, 0.0000001)
        assertEquals(lon, wp!!.longitude, 0.0000001)
        assertEquals(CompassPoint.NORTH, wp!!.latitudeHemisphere)
        assertEquals(CompassPoint.EAST, wp!!.longitudeHemisphere)
        assertEquals(alt, wp!!.altitude, 0.01)
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.GGAParser.getTime].
     */
    @Test
    fun testSetTime() {
        val t = Time(1, 2, 3.456)
        gga!!.time = t
        assertTrue(gga.toString().contains("GPGGA,010203.456,6011"))
    }

    @Test
    fun testSetNumberOfSatellites() {
        gga!!.satelliteCount = 5
        assertEquals(5, gga!!.satelliteCount)
        assertTrue(gga.toString().contains(",E,1,05,2.0,28.0,"))
    }

    @Test
    fun testSetNumberOfSatellitesThrows() {
        try {
            gga!!.satelliteCount = -1
            fail("setSatelliteCount() did not throw exception")
        } catch (e: IllegalArgumentException) {
            assertEquals(0, gga!!.satelliteCount)
            assertEquals("Satelite count cannot be negative", e.message)
        }
    }

    companion object {
        const val EXAMPLE = "\$GPGGA,120044.567,6011.552,N,02501.941,E,1,00,2.0,28.0,M,19.6,M,,*63"
    }
}