package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Tests the RMC sentence parser.
 *
 * @author Johan Bergkvist, Joshua Sweaney
 */
class TTMTest {
    private var empty: TTMParser? = null
    private var ttm: TTMParser? = null
    @Before
    fun setUp() {
        try {
            empty = TTMParser(TalkerId.RA)
            ttm = TTMParser(EXAMPLE)
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    @Test
    fun testConstructor() {
        Assert.assertEquals(15, empty!!.getFieldCount().toLong())
    }

    /**
     * Test method for
     * [TTMParser.getNumber] .
     */
    @Test
    fun testGetNumber() {
        Assert.assertEquals(11, ttm!!.getNumber().toLong())
    }

    /**
     * Test method for
     * [TTMParser.getDistance] .
     */
    @Test
    fun testGetDistance() {
        Assert.assertEquals(25.3, ttm!!.getDistance(), 0.001)
    }

    /**
     * Test method for
     * [TTMParser.getBearing] .
     */
    @Test
    fun testGetBearing() {
        Assert.assertEquals(13.7, ttm!!.getBearing(), 0.001)
    }

    /**
     * Test method for
     * [TTMParser.getSpeed] .
     */
    @Test
    fun testGetSpeed() {
        Assert.assertEquals(7.0, ttm!!.getSpeed(), 0.001)
    }

    /**
     * Test method for
     * [TTMParser.getCourse] .
     */
    @Test
    fun testGetCourse() {
        Assert.assertEquals(20.0, ttm!!.getCourse(), 0.001)
    }

    /**
     * Test method for
     * [TTMParser.getDistanceOfCPA] .
     */
    @Test
    fun testGetDistanceOfCPA() {
        Assert.assertEquals(10.1, ttm!!.getDistanceOfCPA(), 0.001)
    }

    /**
     * Test method for
     * [TTMParser.getTimeToCPA] .
     */
    @Test
    fun testGetTimeToCPA() {
        Assert.assertEquals(20.2, ttm!!.getTimeToCPA(), 0.001)
    }

    /**
     * Test method for
     * [TTMParser.getUnits]
     */
    @Test
    fun testGetUnits() {
        Assert.assertEquals(Units.NAUTICAL_MILES, ttm!!.getUnits())
    }

    /**
     * Test method for
     * [TTMParser.getTimeToCPA] .
     */
    @Test
    fun testGetName() {
        Assert.assertEquals("NAME", ttm!!.getName())
    }

    /**
     * Test method for
     * [TTMParser.getStatus] .
     */
    @Test
    fun testGetStatus() {
        Assert.assertEquals(TargetStatus.QUERY, ttm!!.getStatus())
    }

    /**
     * Test method for
     * [TTMParser.getTime] .
     */
    @Test
    fun testGetTime() {
        val t = ttm!!.getTime()
        Assert.assertNotNull(t)
        Assert.assertEquals(17, t.getHour().toLong())
        Assert.assertEquals(55, t.getMinutes().toLong())
        Assert.assertEquals(50.24, t.getSeconds(), 0.001)
    }

    /**
     * Test method for
     * [TTMParser.getAcquisitionType] .
     */
    @Test
    fun testGetAcquisitionType() {
        Assert.assertEquals(AcquisitionType.AUTO, ttm!!.getAcquisitionType())
    }

    /**
     * Test method for
     * [TTMParser.setNumber] .
     */
    @Test
    fun testSetNumber() {
        val number = 90
        ttm!!.setNumber(number)
        Assert.assertTrue(ttm.toString().contains(",90,"))
    }

    /**
     * Test method for
     * [TTMParser.setDistance] .
     */
    @Test
    fun testSetDistance() {
        ttm!!.setDistance(56.4)
        Assert.assertTrue(ttm.toString().contains(",56.4,"))
        Assert.assertTrue(ttm.toString().contains(",N,"))
    }

    /**
     * Test method for
     * [TTMParser.setBearing] .
     */
    @Test
    fun testSetTrueBearing() {
        ttm!!.setTrueBearing(34.1)
        Assert.assertTrue(ttm!!.isTrueBearing())
        Assert.assertTrue(ttm.toString().contains(",34.1,T,"))
    }

    @Test
    fun testSetRelativeBearing() {
        ttm!!.setRelativeBearing(56.7)
        Assert.assertFalse(ttm!!.isTrueBearing())
        Assert.assertTrue(ttm.toString().contains(",56.7,R,"))
    }

    /**
     * Test method for
     * [TTMParser.setSpeed] .
     */
    @Test
    fun testSetSpeed() {
        ttm!!.setSpeed(44.1)
        Assert.assertTrue(ttm.toString().contains(",44.1,"))
        Assert.assertTrue(ttm.toString().contains(",N,"))
    }

    /**
     * Test method for
     * [TTMParser.setTrueCourse] .
     */
    @Test
    fun testSetTrueCourse() {
        ttm!!.setTrueCourse(234.9)
        Assert.assertTrue(ttm!!.isTrueCourse())
        Assert.assertTrue(ttm.toString().contains(",234.9,T,"))
    }

    /**
     * Test method for
     * [TTMParser.setTrueCourse] .
     */
    @Test
    fun testSetRelativeCourse() {
        ttm!!.setRelativeCourse(123.4)
        Assert.assertFalse(ttm!!.isTrueCourse())
        Assert.assertTrue(ttm.toString().contains(",123.4,R,"))
    }

    /**
     * Test method for
     * [TTMParser.setDistanceOfCPA] .
     */
    @Test
    fun testSetDistanceOfCPA() {
        ttm!!.setDistanceOfCPA(55.2)
        Assert.assertTrue(ttm.toString().contains(",55.2,"))
    }

    /**
     * Test method for
     * [TTMParser.setTimeToCPA] .
     */
    @Test
    fun testSetTimeToCPA() {
        ttm!!.setTimeToCPA(15.0)
        Assert.assertTrue(ttm.toString().contains(",15.0,"))
    }

    /**
     * Test method for
     * [TTMParser.setUnits] .
     */
    @Test
    fun testSetUnits() {
        ttm!!.setUnits(Units.KILOMETERS)
        Assert.assertTrue(ttm.toString().contains(",K,"))
    }

    /**
     * Test method for
     * [TTMParser.setName] .
     */
    @Test
    fun testSetName() {
        ttm!!.setName("FRED")
        Assert.assertTrue(ttm.toString().contains(",FRED,"))
    }

    /**
     * Test method for
     * [TTMParser.setStatus] .
     */
    @Test
    fun testSetStatus() {
        ttm!!.setStatus(TargetStatus.LOST)
        Assert.assertTrue(ttm.toString().contains(",T,"))
    }

    /**
     * Test method for
     * [TTMParser.setStatus] .
     */
    @Test
    fun testSetReferenceTrue() {
        ttm!!.setReference(true)
        Assert.assertTrue(ttm.toString().contains(",R,"))
    }

    @Test
    fun testSetReferenceFalse() {
        ttm!!.setReference(false)
        Assert.assertTrue(!ttm.toString().contains(",R,"))
    }

    /**
     * Test method for
     * [TTMParser.setTime] .
     */
    @Test
    fun testSetTime() {
        val t = Time(1, 2, 3.45)
        ttm!!.setTime(t)
        Assert.assertTrue(ttm.toString().contains(",010203.45,"))
    }

    /**
     * Test method for
     * [TTMParser.setAcquisitionType] .
     */
    @Test
    fun testSetAcquisitionType() {
        ttm!!.setAcquisitionType(AcquisitionType.MANUAL)
        Assert.assertTrue(ttm.toString().contains(",M*"))
    }

    companion object {
        /** Example sentence  */
        const val EXAMPLE = "\$RATTM,11,25.3,13.7,T,7.0,20.0,T,10.1,20.2,N,NAME,Q,,175550.24,A*34"
    }
}