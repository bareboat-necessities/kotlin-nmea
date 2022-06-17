package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.CompassPoint
import net.sf.marineapi.nmea.util.Position
import net.sf.marineapi.nmea.util.Time
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * This is a test class for the TLL NMEA Sentence
 * @author Epameinondas Pantzopoulos
 */
class TLLTest {
    var tll: TLLParser? = null
    var empty: TLLParser? = null

    @Before
    fun setUp() {
        try {
            empty = TLLParser(TalkerId.RA)
            tll = TLLParser(EXAMPLE)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    @Test
    fun testConstructor() {
        assertEquals(9, empty!!.fieldCount)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TLLParser.getNumber] .
     */
    @Test
    fun testGetNumber() {
        assertEquals(1, tll!!.getNumber())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TLLParser.getName] .
     */
    @Test
    fun testGetName() {
        assertEquals("ANDROS", tll!!.getName())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TLLParser.getPosition] .
     */
    @Test
    fun testGetPosition() {
        val p: Position? = tll!!.getPosition()
        val lat = 37 + 31.51205 / 60
        val lon = 24 + 36.0 / 60
        assertNotNull(p)
        assertEquals(lat, p!!.latitude, 0.0000001)
        assertEquals(CompassPoint.NORTH, p.latitudeHemisphere)
        assertEquals(lon, p.longitude, 0.0000001)
        assertEquals(CompassPoint.EAST, p.longitudeHemisphere)
    }

    @Test
    fun testGetTime() {
        val t: Time = tll!!.getTime()
        assertNotNull(t)
        assertEquals(16, t.getHour())
        assertEquals(37, t.getMinutes())
        assertEquals(00.86, t.getSeconds(), 0.001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.setNumber] .
     */
    @Test
    fun testSetNumber() {
        val number = 999
        tll!!.setNumber(number)
        assertTrue(tll.toString().contains(",999,"))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.setName] .
     */
    @Test
    fun testSetName() {
        tll!!.setName("VRACHNOU")
        assertTrue(tll.toString().contains(",VRACHNOU,"))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.setTime] .
     */
    @Test
    fun testSetTime() {
        val t = Time(16, 7, 19.27)
        tll!!.setTime(t)
        assertTrue(tll.toString().contains(",160719.27,"))
    }

    companion object {
        /** Example sentence  */
        const val EXAMPLE = "\$RATLL,01,3731.51205,N,02436.00000,E,ANDROS,163700.86,T,*25"
    }
}