package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.util.Time
import org.junit.Before

class GBSTest {
    private var gbs: GBSSentence? = null
    private var empty: GBSSentence? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        gbs = GBSParser(EXAMPLE)
        empty = GBSParser(TalkerId.GP)
    }

    @get:Throws(Exception::class)
    @get:Test
    val latitudeError: Unit
        get() {
            assertEquals(-0.031, gbs.latitudeError, 0.001)
        }

    @Test
    @Throws(Exception::class)
    fun setLatitudeError() {
        empty.latitudeError = -0.123
        assertEquals(-0.123, empty.latitudeError, 0.001)
    }

    @get:Throws(Exception::class)
    @get:Test
    val longitudeError: Unit
        get() {
            assertEquals(-0.186, gbs.longitudeError, 0.001)
        }

    @Test
    @Throws(Exception::class)
    fun setLongitudeError() {
        empty.longitudeError = -0.456
        assertEquals(-0.456, empty.longitudeError, 0.001)
    }

    @get:Throws(Exception::class)
    @get:Test
    val altitudeError: Unit
        get() {
            assertEquals(0.219, gbs.altitudeError, 0.001)
        }

    @Test
    @Throws(Exception::class)
    fun setAltitudeError() {
        empty.altitudeError = -0.456
        assertEquals(-0.456, empty.altitudeError, 0.001)
    }

    @get:Throws(Exception::class)
    @get:Test
    val satelliteId: Unit
        get() {
            assertEquals("19", gbs.satelliteId)
        }

    @Test
    @Throws(Exception::class)
    fun setSatelliteId() {
        empty.satelliteId = "07"
        assertEquals("07", empty.satelliteId)
    }

    @get:Throws(Exception::class)
    @get:Test
    val probability: Unit
        get() {
            assertEquals(0.000, gbs.probability, 0.001)
        }

    @Test
    @Throws(Exception::class)
    fun setProbability() {
        empty.probability = 0.123
        assertEquals(0.123, empty.probability, 0.001)
    }

    @get:Throws(Exception::class)
    @get:Test
    val estimate: Unit
        get() {
            assertEquals(-0.354, gbs.estimate, 0.001)
        }

    @Test
    @Throws(Exception::class)
    fun setEstimate() {
        empty.estimate = -0.234
        assertEquals(-0.234, empty.estimate, 0.001)
    }

    @get:Throws(Exception::class)
    @get:Test
    val deviation: Unit
        get() {
            assertEquals(6.972, gbs.deviation, 0.001)
        }

    @Test
    @Throws(Exception::class)
    fun setDeviation() {
        empty.deviation = 1.234
        assertEquals(1.234, empty.deviation, 0.001)
    }

    @get:Throws(Exception::class)
    @get:Test
    val time: Unit
        get() {
            assertEquals("015509.000", gbs.time.toString())
        }

    @Test
    @Throws(Exception::class)
    fun setTime() {
        empty.time = Time(10, 31, 12.345)
        assertEquals("103112.345", empty.time.toString())
    }

    companion object {
        const val EXAMPLE = "\$GPGBS,015509.00,-0.031,-0.186,0.219,19,0.000,-0.354,6.972*4D"
    }
}