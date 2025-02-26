package net.sf.marineapi.ais.parser

import net.sf.marineapi.ais.message.AISPositionReport
import net.sf.marineapi.ais.util.Sixbit

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

/**
 * AISPositionReportParser test, covering parsers for types 01, 02 and 03.
 *
 * Expected values based on http://www.maritec.co.za/tools/aisvdmvdodecoding/
 */
class AISPositionReportParserTest {
    // !AIVDM,1,1,,A,13u?etPv2;0n:dDPwUM1U1Cb069D,0*23
    private val payload = "13u?etPv2;0n:dDPwUM1U1Cb069D"
    private val sixbit = Sixbit(payload, 0)
    private val msg: AISPositionReport = AISPositionReportParser(sixbit)

    @Test
    @Throws(Exception::class)
    fun getNavigationalStatus() {
        assertEquals(0, msg.navigationalStatus.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getRateOfTurn() {
        assertEquals(-2.9, msg.rateOfTurn, 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun getSpeedOverGround() {
        assertEquals(13.9, msg.speedOverGround, 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun getPositionAccuracy() {
        // 0 == low (> 10 meters)
        assertFalse(msg.isAccurate)
    }

    @Test
    @Throws(Exception::class)
    fun getLongitudeInDegrees() {
        assertEquals(11.8329767, msg.longitudeInDegrees, 0.0000001)
    }

    @Test
    @Throws(Exception::class)
    fun getLatitudeInDegrees() {
        assertEquals(57.6603533, msg.latitudeInDegrees, 0.0000001)
    }

    @Test
    @Throws(Exception::class)
    fun getCourseOverGround() {
        assertEquals(40.4, msg.courseOverGround, 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun getTrueHeading() {
        assertEquals(41, msg.trueHeading.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getTimeStamp() {
        assertEquals(53, msg.timeStamp.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getManouverIndicator() {
        assertEquals(0, msg.manouverIndicator.toLong())
    }

    @Test
    fun hasLatitude() {
        assertEquals(true, msg.hasLatitude())
    }

    @Test
    fun hasLongitude() {
        assertEquals(true, msg.hasLongitude())
    }

    @Test
    fun hasRateOfTurn() {
        assertEquals(true, msg.hasRateOfTurn())
    }

    @Test
    fun hasCourseOverGround() {
        assertEquals(true, msg.hasCourseOverGround())
    }

    @Test
    fun hasSpeedOverGround() {
        assertEquals(true, msg.hasSpeedOverGround())
    }

    @Test
    fun hasTimeStamp() {
        assertEquals(true, msg.hasTimeStamp())
    }
}