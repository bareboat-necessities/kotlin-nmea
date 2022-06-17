package net.sf.marineapi.nmea.parser

import org.junit.Assert.assertEquals

/**
 * Tests the VTG sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
class VTGTest {
    private var empty: VTGSentence? = null
    private var vtg: VTGSentence? = null
    @Before
    fun setUp() {
        try {
            empty = VTGParser(TalkerId.GP)
            vtg = VTGParser(EXAMPLE)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    @Test
    fun testConstructor() {
        assertEquals(9, empty.fieldCount)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VTGParser.getMagneticCourse].
     */
    @Test
    fun testGetMagneticCourse() {
        assertEquals(348.7, vtg.getMagneticCourse(), 0.001)
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.VTGParser.getMode].
     */
    @Test
    fun testGetMode() {
        assertEquals(FaaMode.AUTOMATIC, vtg.getMode())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VTGParser.getSpeedKmh].
     */
    @Test
    fun testGetSpeedKmh() {
        assertEquals(31.28, vtg.getSpeedKmh(), 0.001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VTGParser.getSpeedKnots].
     */
    @Test
    fun testGetSpeedKnots() {
        assertEquals(16.89, vtg.getSpeedKnots(), 0.001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VTGParser.getTrueCourse].
     */
    @Test
    fun testGetTrueCourse() {
        assertEquals(360.0, vtg.getTrueCourse(), 0.001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VTGParser.setMagneticCourse].
     */
    @Test
    fun testSetMagneticCourse() {
        val mcog = 95.56789
        vtg.setMagneticCourse(mcog)
        assertTrue(vtg.toString().contains(",095.6,M,"))
        assertEquals(mcog, vtg.getMagneticCourse(), 0.1)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VTGParser.setMagneticCourse].
     */
    @Test
    fun testSetMagneticCourseWithNegativeValue() {
        try {
            vtg.setMagneticCourse(-0.001)
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message!!.contains("0..360"))
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VTGParser.setMagneticCourse].
     */
    @Test
    fun testSetMagneticCourseWithValueGreaterThanAllowed() {
        try {
            vtg.setMagneticCourse(360.001)
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message!!.contains("0..360"))
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VTGParser.setMode].
     */
    @Test
    fun testSetMode() {
        vtg.setMode(FaaMode.MANUAL)
        assertEquals(FaaMode.MANUAL, vtg.getMode())
        vtg.setMode(FaaMode.SIMULATED)
        assertEquals(FaaMode.SIMULATED, vtg.getMode())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VTGParser.setMode].
     */
    @Test
    fun testSetModeWhenOmitted() {
        val parser = VTGParser("\$GPVTG,360.0,T,348.7,M,16.89,N,31.28,K")
        parser.setMode(FaaMode.MANUAL)
        assertEquals(FaaMode.MANUAL, parser.getMode())
        parser.setMode(FaaMode.SIMULATED)
        assertEquals(FaaMode.SIMULATED, parser.getMode())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VTGParser.setSpeedKmh].
     */
    @Test
    fun testSetSpeedKmh() {
        val kmh = 12.56789
        vtg.setSpeedKmh(kmh)
        assertTrue(vtg.toString().contains(",12.57,K,"))
        assertEquals(kmh, vtg.getSpeedKmh(), 0.01)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VTGParser.setSpeedKmh].
     */
    @Test
    fun testSetSpeedKmhWithNegativeValue() {
        try {
            vtg.setSpeedKmh(-0.0001)
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message!!.contains("cannot be negative"))
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VTGParser.setSpeedKnots].
     */
    @Test
    fun testSetSpeedKnots() {
        val kn = 11.6789
        vtg.setSpeedKnots(kn)
        assertTrue(vtg.toString().contains(",11.68,N,"))
        assertEquals(kn, vtg.getSpeedKnots(), 0.01)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VTGParser.setSpeedKnots].
     */
    @Test
    fun testSetSpeedKnotsWithNegativeValue() {
        try {
            vtg.setSpeedKnots(-0.0001)
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message!!.contains("cannot be negative"))
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VTGParser.setTrueCourse].
     */
    @Test
    fun testSetTrueCourse() {
        val tcog = 90.56789
        vtg.setTrueCourse(tcog)
        assertTrue(vtg.toString().contains(",090.6,T,"))
        assertEquals(tcog, vtg.getTrueCourse(), 0.1)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VTGParser.setTrueCourse].
     */
    @Test
    fun testSetTrueCourseWithNegativeValue() {
        try {
            vtg.setTrueCourse(-0.001)
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message!!.contains("0..360"))
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VTGParser.setTrueCourse].
     */
    @Test
    fun testSetTrueCourseWithValueGreaterThanAllowed() {
        try {
            vtg.setTrueCourse(360.001)
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message!!.contains("0..360"))
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    companion object {
        /** Example sentence  */
        const val EXAMPLE = "\$GPVTG,360.0,T,348.7,M,16.89,N,31.28,K,A"
    }
}