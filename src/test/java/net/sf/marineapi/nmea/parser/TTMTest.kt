package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.util.Time
import net.sf.marineapi.nmea.util.Units
import org.junit.Before

/**
 * Tests the RMC sentence parser.
 *
 * @author Johan Bergkvist, Joshua Sweaney
 */
class TTMTest {
    var empty: TTMParser? = null
    var ttm: TTMParser? = null
    @Before
    fun setUp() {
        try {
            empty = TTMParser(TalkerId.RA)
            ttm = TTMParser(EXAMPLE)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    @Test
    fun testConstructor() {
        assertEquals(15, empty!!.fieldCount)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.getNumber] .
     */
    @Test
    fun testGetNumber() {
        assertEquals(11, ttm!!.getNumber())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.getDistance] .
     */
    @Test
    fun testGetDistance() {
        assertEquals(25.3, ttm!!.getDistance(), 0.001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.getBearing] .
     */
    @Test
    fun testGetBearing() {
        assertEquals(13.7, ttm!!.getBearing(), 0.001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.getSpeed] .
     */
    @Test
    fun testGetSpeed() {
        assertEquals(7.0, ttm!!.getSpeed(), 0.001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.getCourse] .
     */
    @Test
    fun testGetCourse() {
        assertEquals(20.0, ttm!!.getCourse(), 0.001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.getDistanceOfCPA] .
     */
    @Test
    fun testGetDistanceOfCPA() {
        assertEquals(10.1, ttm!!.getDistanceOfCPA(), 0.001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.getTimeToCPA] .
     */
    @Test
    fun testGetTimeToCPA() {
        assertEquals(20.2, ttm!!.getTimeToCPA(), 0.001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.getUnits]
     */
    @Test
    fun testGetUnits() {
        assertEquals(Units.NAUTICAL_MILES, ttm!!.getUnits())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.getTimeToCPA] .
     */
    @Test
    fun testGetName() {
        assertEquals("NAME", ttm!!.getName())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.getStatus] .
     */
    @Test
    fun testGetStatus() {
        assertEquals(TargetStatus.QUERY, ttm!!.getStatus())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.getTime] .
     */
    @Test
    fun testGetTime() {
        val t: Time = ttm!!.getTime()
        assertNotNull(t)
        assertEquals(17, t.getHour())
        assertEquals(55, t.getMinutes())
        assertEquals(50.24, t.getSeconds(), 0.001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.getAcquisitionType] .
     */
    @Test
    fun testGetAcquisitionType() {
        assertEquals(AcquisitionType.AUTO, ttm!!.getAcquisitionType())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.setNumber] .
     */
    @Test
    fun testSetNumber() {
        val number = 90
        ttm!!.setNumber(number)
        assertTrue(ttm.toString().contains(",90,"))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.setDistance] .
     */
    @Test
    fun testSetDistance() {
        ttm!!.setDistance(56.4)
        assertTrue(ttm.toString().contains(",56.4,"))
        assertTrue(ttm.toString().contains(",N,"))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.setBearing] .
     */
    @Test
    fun testSetTrueBearing() {
        ttm!!.setTrueBearing(34.1)
        assertTrue(ttm!!.isTrueBearing())
        assertTrue(ttm.toString().contains(",34.1,T,"))
    }

    @Test
    fun testSetRelativeBearing() {
        ttm!!.setRelativeBearing(56.7)
        assertFalse(ttm!!.isTrueBearing())
        assertTrue(ttm.toString().contains(",56.7,R,"))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.setSpeed] .
     */
    @Test
    fun testSetSpeed() {
        ttm!!.setSpeed(44.1)
        assertTrue(ttm.toString().contains(",44.1,"))
        assertTrue(ttm.toString().contains(",N,"))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.setTrueCourse] .
     */
    @Test
    fun testSetTrueCourse() {
        ttm!!.setTrueCourse(234.9)
        assertTrue(ttm!!.isTrueCourse())
        assertTrue(ttm.toString().contains(",234.9,T,"))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.setTrueCourse] .
     */
    @Test
    fun testSetRelativeCourse() {
        ttm!!.setRelativeCourse(123.4)
        assertFalse(ttm!!.isTrueCourse())
        assertTrue(ttm.toString().contains(",123.4,R,"))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.setDistanceOfCPA] .
     */
    @Test
    fun testSetDistanceOfCPA() {
        ttm!!.setDistanceOfCPA(55.2)
        assertTrue(ttm.toString().contains(",55.2,"))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.setTimeToCPA] .
     */
    @Test
    fun testSetTimeToCPA() {
        ttm!!.setTimeToCPA(15.0)
        assertTrue(ttm.toString().contains(",15.0,"))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.setUnits] .
     */
    @Test
    fun testSetUnits() {
        ttm!!.setUnits(Units.KILOMETERS)
        assertTrue(ttm.toString().contains(",K,"))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.setName] .
     */
    @Test
    fun testSetName() {
        ttm!!.setName("FRED")
        assertTrue(ttm.toString().contains(",FRED,"))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.setStatus] .
     */
    @Test
    fun testSetStatus() {
        ttm!!.setStatus(TargetStatus.LOST)
        assertTrue(ttm.toString().contains(",T,"))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.setStatus] .
     */
    @Test
    fun testSetReferenceTrue() {
        ttm!!.setReference(true)
        assertTrue(ttm.toString().contains(",R,"))
    }

    @Test
    fun testSetReferenceFalse() {
        ttm!!.setReference(false)
        assertTrue(!ttm.toString().contains(",R,"))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.setTime] .
     */
    @Test
    fun testSetTime() {
        val t = Time(1, 2, 3.45)
        ttm!!.setTime(t)
        assertTrue(ttm.toString().contains(",010203.45,"))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.TTMParser.setAcquisitionType] .
     */
    @Test
    fun testSetAcquisitionType() {
        ttm!!.setAcquisitionType(AcquisitionType.MANUAL)
        assertTrue(ttm.toString().contains(",M*"))
    }

    companion object {
        /** Example sentence  */
        const val EXAMPLE = "\$RATTM,11,25.3,13.7,T,7.0,20.0,T,10.1,20.2,N,NAME,Q,,175550.24,A*34"
    }
}