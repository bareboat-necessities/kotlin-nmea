package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.Time
import org.junit.Assert
import org.junit.Before
import org.junit.Test

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
            Assert.fail(e.message)
        }
    }

    @Test
    fun testConstructor() {
        Assert.assertEquals(8, empty!!.getFieldCount().toLong())
    }

    @Test
    fun testGetPseudoRangeResidualsRMS() {
        Assert.assertEquals(0.006, gst!!.getPseudoRangeResidualsRMS(), 0.001)
    }

    @Test
    fun testGetSemiMajorError() {
        Assert.assertEquals(0.023, gst!!.getSemiMajorError(), 0.001)
    }

    @Test
    fun testGetSemiMinorError() {
        Assert.assertEquals(0.020, gst!!.getSemiMinorError(), 0.001)
    }

    @Test
    fun testGetErrorEllipseOrientation() {
        Assert.assertEquals(273.6, gst!!.getErrorEllipseOrientation(), 0.001)
    }

    @Test
    fun testGetLatitudeError() {
        Assert.assertEquals(0.023, gst!!.getLatitudeError(), 0.001)
    }

    @Test
    fun testGetLongitudeError() {
        Assert.assertEquals(0.020, gst!!.getLongitudeError(), 0.001)
    }

    @Test
    fun testGetAltitudeError() {
        Assert.assertEquals(0.031, gst!!.getAltitudeError(), 0.001)
    }

    @Test
    fun testGetTime() {
        val t = gst!!.getTime()
        Assert.assertNotNull(t)
        Assert.assertEquals(17, t.getHour().toLong())
        Assert.assertEquals(28, t.getMinutes().toLong())
        Assert.assertEquals(14.0, t.getSeconds(), 0.001)
    }

    @Test
    fun testGSTParser() {
        val instance = GSTParser(EXAMPLE)
        val sid = SentenceId.valueOf(instance.getSentenceId())
        Assert.assertEquals(SentenceId.GST, sid)
    }

    @Test
    fun testSetPseudoRangeResidualsRMS() {
        gst!!.setPseudoRangeResidualsRMS(0.012)
        Assert.assertEquals(0.012, gst!!.getPseudoRangeResidualsRMS(), 0.001)
    }

    @Test
    fun testSetSemiMajorError() {
        gst!!.setSemiMajorError(0.015)
        Assert.assertEquals(0.015, gst!!.getSemiMajorError(), 0.001)
    }

    @Test
    fun testSetSemiMinorError() {
        gst!!.setSemiMinorError(0.032)
        Assert.assertEquals(0.032, gst!!.getSemiMinorError(), 0.001)
    }

    @Test
    fun testSetErrorEllipseOrientation() {
        gst!!.setErrorEllipseOrientation(121.3)
        Assert.assertEquals(121.3, gst!!.getErrorEllipseOrientation(), 0.001)
    }

    @Test
    fun testSetLatitudeError() {
        gst!!.setLatitudeError(0.068)
        Assert.assertEquals(0.068, gst!!.getLatitudeError(), 0.001)
    }

    @Test
    fun testSetLongitudeError() {
        gst!!.setLongitudeError(0.011)
        Assert.assertEquals(0.011, gst!!.getLongitudeError(), 0.001)
    }

    @Test
    fun testSetAltitudeError() {
        gst!!.setAltitudeError(0.013)
        Assert.assertEquals(0.013, gst!!.getAltitudeError(), 0.001)
    }

    @Test
    fun testSetTime() {
        gst!!.setTime(Time(1, 2, 3.456))
        val t = gst!!.getTime()
        Assert.assertNotNull(t)
        Assert.assertEquals(1, t.getHour().toLong())
        Assert.assertEquals(2, t.getMinutes().toLong())
        Assert.assertEquals(3.456, t.getSeconds(), 0.001)
    }

    companion object {
        const val EXAMPLE = "\$GPGST,172814.0,0.006,0.023,0.020,273.6,0.023,0.020,0.031*6A"
    }
}