package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.sentence.VTGSentence
import net.sf.marineapi.nmea.util.FaaMode
import org.junit.Assert
import org.junit.Before
import org.junit.Test

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
            Assert.fail(e.message)
        }
    }

    @Test
    fun testConstructor() {
        Assert.assertEquals(9, empty!!.getFieldCount().toLong())
    }

    /**
     * Test method for
     * [VTGParser.getMagneticCourse].
     */
    @Test
    fun testGetMagneticCourse() {
        Assert.assertEquals(348.7, vtg!!.getMagneticCourse(), 0.001)
    }

    /**
     * Test method for [VTGParser.getMode].
     */
    @Test
    fun testGetMode() {
        Assert.assertEquals(FaaMode.AUTOMATIC, vtg!!.getMode())
    }

    /**
     * Test method for
     * [VTGParser.getSpeedKmh].
     */
    @Test
    fun testGetSpeedKmh() {
        Assert.assertEquals(31.28, vtg!!.getSpeedKmh(), 0.001)
    }

    /**
     * Test method for
     * [VTGParser.getSpeedKnots].
     */
    @Test
    fun testGetSpeedKnots() {
        Assert.assertEquals(16.89, vtg!!.getSpeedKnots(), 0.001)
    }

    /**
     * Test method for
     * [VTGParser.getTrueCourse].
     */
    @Test
    fun testGetTrueCourse() {
        Assert.assertEquals(360.0, vtg!!.getTrueCourse(), 0.001)
    }

    /**
     * Test method for
     * [VTGParser.setMagneticCourse].
     */
    @Test
    fun testSetMagneticCourse() {
        val mcog = 95.56789
        vtg!!.setMagneticCourse(mcog)
        Assert.assertTrue(vtg.toString().contains(",095.6,M,"))
        Assert.assertEquals(mcog, vtg!!.getMagneticCourse(), 0.1)
    }

    /**
     * Test method for
     * [VTGParser.setMagneticCourse].
     */
    @Test
    fun testSetMagneticCourseWithNegativeValue() {
        try {
            vtg!!.setMagneticCourse(-0.001)
            Assert.fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            Assert.assertTrue(e.message!!.contains("0..360"))
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    /**
     * Test method for
     * [VTGParser.setMagneticCourse].
     */
    @Test
    fun testSetMagneticCourseWithValueGreaterThanAllowed() {
        try {
            vtg!!.setMagneticCourse(360.001)
            Assert.fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            Assert.assertTrue(e.message!!.contains("0..360"))
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    /**
     * Test method for
     * [VTGParser.setMode].
     */
    @Test
    fun testSetMode() {
        vtg!!.setMode(FaaMode.MANUAL)
        Assert.assertEquals(FaaMode.MANUAL, vtg!!.getMode())
        vtg!!.setMode(FaaMode.SIMULATED)
        Assert.assertEquals(FaaMode.SIMULATED, vtg!!.getMode())
    }

    /**
     * Test method for
     * [VTGParser.setMode].
     */
    @Test
    fun testSetModeWhenOmitted() {
        val parser = VTGParser("\$GPVTG,360.0,T,348.7,M,16.89,N,31.28,K")
        parser.setMode(FaaMode.MANUAL)
        Assert.assertEquals(FaaMode.MANUAL, parser.getMode())
        parser.setMode(FaaMode.SIMULATED)
        Assert.assertEquals(FaaMode.SIMULATED, parser.getMode())
    }

    /**
     * Test method for
     * [VTGParser.setSpeedKmh].
     */
    @Test
    fun testSetSpeedKmh() {
        val kmh = 12.56789
        vtg!!.setSpeedKmh(kmh)
        Assert.assertTrue(vtg.toString().contains(",12.57,K,"))
        Assert.assertEquals(kmh, vtg!!.getSpeedKmh(), 0.01)
    }

    /**
     * Test method for
     * [VTGParser.setSpeedKmh].
     */
    @Test
    fun testSetSpeedKmhWithNegativeValue() {
        try {
            vtg!!.setSpeedKmh(-0.0001)
        } catch (e: IllegalArgumentException) {
            Assert.assertTrue(e.message!!.contains("cannot be negative"))
        }
    }

    /**
     * Test method for
     * [VTGParser.setSpeedKnots].
     */
    @Test
    fun testSetSpeedKnots() {
        val kn = 11.6789
        vtg!!.setSpeedKnots(kn)
        Assert.assertTrue(vtg.toString().contains(",11.68,N,"))
        Assert.assertEquals(kn, vtg!!.getSpeedKnots(), 0.01)
    }

    /**
     * Test method for
     * [VTGParser.setSpeedKnots].
     */
    @Test
    fun testSetSpeedKnotsWithNegativeValue() {
        try {
            vtg!!.setSpeedKnots(-0.0001)
        } catch (e: IllegalArgumentException) {
            Assert.assertTrue(e.message!!.contains("cannot be negative"))
        }
    }

    /**
     * Test method for
     * [VTGParser.setTrueCourse].
     */
    @Test
    fun testSetTrueCourse() {
        val tcog = 90.56789
        vtg!!.setTrueCourse(tcog)
        Assert.assertTrue(vtg.toString().contains(",090.6,T,"))
        Assert.assertEquals(tcog, vtg!!.getTrueCourse(), 0.1)
    }

    /**
     * Test method for
     * [VTGParser.setTrueCourse].
     */
    @Test
    fun testSetTrueCourseWithNegativeValue() {
        try {
            vtg!!.setTrueCourse(-0.001)
            Assert.fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            Assert.assertTrue(e.message!!.contains("0..360"))
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    /**
     * Test method for
     * [VTGParser.setTrueCourse].
     */
    @Test
    fun testSetTrueCourseWithValueGreaterThanAllowed() {
        try {
            vtg!!.setTrueCourse(360.001)
            Assert.fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            Assert.assertTrue(e.message!!.contains("0..360"))
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    companion object {
        /** Example sentence  */
        const val EXAMPLE = "\$GPVTG,360.0,T,348.7,M,16.89,N,31.28,K,A"
    }
}