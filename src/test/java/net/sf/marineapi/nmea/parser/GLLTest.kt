package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.*
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Tests the GLL sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
class GLLTest {
    private var empty: GLLParser? = null
    private var instance: GLLParser? = null

    /**
     * setUp
     */
    @Before
    fun setUp() {
        try {
            empty = GLLParser(TalkerId.GP)
            instance = GLLParser(EXAMPLE)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    @Test
    fun testConstructor() {
        assertEquals(7, empty!!.getFieldCount().toLong())
    }

    /**
     * Test method for
     * [GLLParser.getStatus].
     */
    @Test
    fun testGetDataStatus() {
        assertEquals(DataStatus.ACTIVE, instance!!.getStatus())
    }

    /**
     * Test method for
     * [GLLParser.getPosition].
     */
    @Test
    fun testGetPosition() {
        val lat = 60 + 11.552 / 60
        val lon = 25 + 1.941 / 60
        val p = instance!!.getPosition()
        assertNotNull(p)
        assertEquals(lat, p.latitude, 0.0000001)
        assertEquals(lon, p.longitude, 0.0000001)
        assertEquals(CompassPoint.NORTH, p.latitudeHemisphere)
        assertEquals(CompassPoint.EAST, p.longitudeHemisphere)
    }

    /**
     * Test method for [GLLParser.getTime].
     */
    @Test
    fun testGetTime() {
        val t = instance!!.getTime()
        assertNotNull(t)
        assertEquals(12, t.getHour().toLong())
        assertEquals(0, t.getMinutes().toLong())
        assertEquals(45.0, t.getSeconds(), 0.1)
    }

    /**
     * Test method for
     * [GLLParser.setStatus].
     */
    @Test
    fun testSetStatus() {
        assertEquals(DataStatus.ACTIVE, instance!!.getStatus())
        instance!!.setStatus(DataStatus.VOID)
        assertEquals(DataStatus.VOID, instance!!.getStatus())
    }

    /**
     * Test method for
     * [GLLParser.setPosition].
     */
    @Test
    fun testSetPositionWithNonZeroValues() {
        val lat = 60 + 11.552 / 60
        val lon = 25 + 1.941 / 60
        val p2 = Position(lat, lon)
        instance!!.setPosition(p2)
        val s2 = instance.toString()
        val p = instance!!.getPosition()
        assertTrue(s2.contains(",6011.552,N,"))
        assertTrue(s2.contains(",02501.941,E,"))
        assertNotNull(p)
        assertEquals(lat, p.latitude, 0.0000001)
        assertEquals(lon, p.longitude, 0.0000001)
    }

    /**
     * Test method for
     * [GLLParser.setPosition].
     */
    @Test
    fun testSetPositionWithZeroValues() {
        val p1 = Position(0.0, 0.0)
        instance!!.setPosition(p1)
        val s1 = instance.toString()
        val p = instance!!.getPosition()
        assertTrue(s1.contains(",0000.000,N,"))
        assertTrue(s1.contains(",00000.000,E,"))
        assertNotNull(p)
        assertEquals(0.0, p.latitude, 0.0000001)
        assertEquals(0.0, p.longitude, 0.0000001)
    }

    /**
     * Test method for
     * [GLLParser.setTime].
     */
    @Test
    fun testSetTime() {
        val t = Time(1, 2, 3.4)
        instance!!.setTime(t)
        assertTrue(instance.toString().contains(",E,010203.400,A*"))
    }

    @Test
    fun testSetMode() {
        empty!!.setMode(FaaMode.DGPS)
        assertEquals(FaaMode.DGPS, empty!!.getMode())
        assertTrue(empty.toString().startsWith("\$GPGLL,,,,,,,D*"))
    }

    @Test
    fun testSetModeInLegacySentence() {
        instance!!.setMode(FaaMode.PRECISE)
        assertEquals(FaaMode.PRECISE, instance!!.getMode())
        assertTrue(instance.toString().startsWith("\$GPGLL,6011.552,N,02501.941,E,120045,A,P*"))
    }

    companion object {
        /**
         * Example sentence
         */
        const val EXAMPLE = "\$GPGLL,6011.552,N,02501.941,E,120045,A*26"
    }
}