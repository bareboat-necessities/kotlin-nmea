package net.sf.marineapi.nmea.util

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class PositionTest {
    var instance: Position? = null

    /**
     * Setup
     */
    @Before
    fun setUp() {
        instance = Position(60.0, 25.0, Datum.WGS84)
    }

    /**
     * Test for distanceTo()
     */
    @Test
    fun testDistanceTo() {

        // TODO: not exactly correct as 1 degree is *approximately* 60 NM
        val origin = Position(0.123, 25.0)
        for (n in 0..89) {

            // 1 degree north from instance's position
            val destination = Position(0.123 + n, 25.0)
            val distance = origin.distanceTo(destination)

            // one degree equals 60 NM
            val expected = 60 * n * 1852.0
            assertEquals(expected, distance, 1.0)
        }
    }

    /**
     * Test for distanceTo()
     */
    @Test
    fun testDistanceToSelf() {
        val origin = Position(60.567, 26.123)
        assertEquals(0.0, origin.distanceTo(origin), 0.00001)
    }

    /**
     * Test for getDatum()
     */
    @Test
    fun testGetDatum() {
        assertEquals(Datum.WGS84, instance!!.datum)
    }

    /**
     * Test for getLatitude()
     */
    @Test
    fun testGetLatitude() {
        assertEquals(60.0, instance!!.latitude, 0.0000001)
    }

    /**
     * Test for getLatHemisphere()
     */
    @Test
    fun testGetLatitudeHemisphere() {
        assertEquals(CompassPoint.NORTH, instance!!.latitudeHemisphere)
    }

    /**
     * Test for getLongitude()
     */
    @Test
    fun testGetLongitude() {
        assertEquals(25.0, instance!!.longitude, 0.0000001)
    }

    /**
     * Test for getLonHemisphere()
     */
    @Test
    fun testGetLongitudeHemisphere() {
        assertEquals(CompassPoint.EAST, instance!!.longitudeHemisphere)
    }

    /**
     * Test for setLatitude()
     */
    @Test
    fun testSetIllegalLatitudeNorth() {
        try {
            instance!!.latitude = 90.001
            fail("Did not throw IllegalArgumentExcetpion")
        } catch (e: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test for setLatitude()
     */
    @Test
    fun testSetIllegalLatitudeSouth() {
        try {
            instance!!.latitude = -90.001
            fail("Did not throw IllegalArgumentExcetpion")
        } catch (e: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test for setLongitude()
     */
    @Test
    fun testSetIllegalLongitudeEast() {
        try {
            instance!!.longitude = 180.0001
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            // pass
        }
    }

    /**
     * Test for setLongitude()
     */
    @Test
    fun testSetIllegalLongitudeWest() {
        try {
            instance!!.longitude = -180.0001
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            // pass
        }
    }

    /**
     * Test for setLatitude()
     */
    @Test
    fun testSetLatitudeNorth() {
        assertEquals(60.0, instance!!.latitude, 0.0000001)
        instance!!.latitude = 90.0
        assertEquals(90.0, instance!!.latitude, 0.0000001)
        assertEquals(CompassPoint.NORTH, instance!!.latitudeHemisphere)
    }

    /**
     * Test for setLatitude()
     */
    @Test
    fun testSetLatitudeSouth() {
        assertEquals(60.0, instance!!.latitude, 0.0000001)
        instance!!.latitude = -90.0
        assertEquals(-90.0, instance!!.latitude, 0.0000001)
        assertEquals(CompassPoint.SOUTH, instance!!.latitudeHemisphere)
    }

    /**
     * Test for setLongitude()
     */
    @Test
    fun testSetLongitudeEast() {
        assertEquals(25.0, instance!!.longitude, 0.0000001)
        instance!!.longitude = 180.0
        assertEquals(180.0, instance!!.longitude, 0.0000001)
        assertEquals(CompassPoint.EAST, instance!!.longitudeHemisphere)
    }

    /**
     * Test for setLongitude()
     */
    @Test
    fun testSetLongitudeWest() {
        assertEquals(25.0, instance!!.longitude, 0.0000001)
        instance!!.longitude = -180.0
        assertEquals(-180.0, instance!!.longitude, 0.0000001)
        assertEquals(CompassPoint.WEST, instance!!.longitudeHemisphere)
    }

    /**
     * Test for toWaypoint()
     */
    @Test
    fun testToWaypoint() {
        val name = "TEST"
        val wp = instance!!.toWaypoint(name)
        assertEquals(name, wp.id)
        assertEquals("", wp.description)
        assertEquals(instance!!.latitude, wp.latitude, 0.00001)
        assertEquals(instance!!.longitude, wp.longitude, 0.00001)
        assertEquals(instance!!.latitudeHemisphere, wp.latitudeHemisphere)
        assertEquals(instance!!.longitudeHemisphere, wp.longitudeHemisphere)
        assertEquals(instance!!.datum, wp.datum)
    }
}