package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.util.Time
import org.junit.Assert.assertEquals

/**
 * Test the GST sentence parser.
 *
 * @author Tero Laitinen
 */
class GSTTest {
    private var gst: GSTParser? = null
    private var empty: GSTParser? = null
    @Before
    fun setUp() {
        try {
            empty = GSTParser(TalkerId.GP)
            gst = GSTParser(EXAMPLE)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    @Test
    fun testConstructor() {
        assertEquals(8, empty!!.fieldCount)
    }

    @Test
    fun testGetPseudoRangeResidualsRMS() {
        assertEquals(0.006, gst!!.pseudoRangeResidualsRMS, 0.001)
    }

    @Test
    fun testGetSemiMajorError() {
        assertEquals(0.023, gst!!.semiMajorError, 0.001)
    }

    @Test
    fun testGetSemiMinorError() {
        assertEquals(0.020, gst!!.semiMinorError, 0.001)
    }

    @Test
    fun testGetErrorEllipseOrientation() {
        assertEquals(273.6, gst!!.errorEllipseOrientation, 0.001)
    }

    @Test
    fun testGetLatitudeError() {
        assertEquals(0.023, gst!!.latitudeError, 0.001)
    }

    @Test
    fun testGetLongitudeError() {
        assertEquals(0.020, gst!!.longitudeError, 0.001)
    }

    @Test
    fun testGetAltitudeError() {
        assertEquals(0.031, gst!!.altitudeError, 0.001)
    }

    @Test
    fun testGetTime() {
        val t: Time = gst!!.time
        assertNotNull(t)
        assertEquals(17, t.getHour())
        assertEquals(28, t.getMinutes())
        assertEquals(14.0, t.getSeconds(), 0.001)
    }

    @Test
    fun testGSTParser() {
        val instance = GSTParser(EXAMPLE)
        val sid: SentenceId = SentenceId.valueOf(instance.sentenceId)
        assertEquals(SentenceId.GST, sid)
    }

    @Test
    fun testSetPseudoRangeResidualsRMS() {
        gst!!.pseudoRangeResidualsRMS = 0.012
        assertEquals(0.012, gst!!.pseudoRangeResidualsRMS, 0.001)
    }

    @Test
    fun testSetSemiMajorError() {
        gst!!.semiMajorError = 0.015
        assertEquals(0.015, gst!!.semiMajorError, 0.001)
    }

    @Test
    fun testSetSemiMinorError() {
        gst!!.semiMinorError = 0.032
        assertEquals(0.032, gst!!.semiMinorError, 0.001)
    }

    @Test
    fun testSetErrorEllipseOrientation() {
        gst!!.errorEllipseOrientation = 121.3
        assertEquals(121.3, gst!!.errorEllipseOrientation, 0.001)
    }

    @Test
    fun testSetLatitudeError() {
        gst!!.latitudeError = 0.068
        assertEquals(0.068, gst!!.latitudeError, 0.001)
    }

    @Test
    fun testSetLongitudeError() {
        gst!!.longitudeError = 0.011
        assertEquals(0.011, gst!!.longitudeError, 0.001)
    }

    @Test
    fun testSetAltitudeError() {
        gst!!.altitudeError = 0.013
        assertEquals(0.013, gst!!.altitudeError, 0.001)
    }

    @Test
    fun testSetTime() {
        gst!!.time = Time(1, 2, 3.456)
        val t: Time = gst!!.time
        assertNotNull(t)
        assertEquals(1, t.getHour())
        assertEquals(2, t.getMinutes())
        assertEquals(3.456, t.getSeconds(), 0.001)
    }

    companion object {
        const val EXAMPLE = "\$GPGST,172814.0,0.006,0.023,0.020,273.6,0.023,0.020,0.031*6A"
    }
}