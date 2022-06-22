package net.sf.marineapi.ais.parser

import net.sf.marineapi.ais.message.AISPositionReportB
import net.sf.marineapi.ais.util.Sixbit

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * AIS position report "type B" tests, covering parsers 18 and 19.
 *
 * TODO: missing getters for "Class B" flags 13 - 20.
 *
 * Expected values based on http://www.maritec.co.za/tools/aisvdmvdodecoding/
 */
class AISPositionReportBParserTest {
    // !AIVDM,1,1,,A,B6CdCm0t3`tba35f@V9faHi7kP06,0*58
    private val payload = "B6CdCm0t3`tba35f@V9faHi7kP06"
    private val sixbit = Sixbit(payload, 0)
    private val msg: AISPositionReportB = AISPositionReportBParser(sixbit)

    @Test
    @Throws(Exception::class)
    fun getSpeedOverGround() {
        assertEquals(1.4, msg.speedOverGround, 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun getPositionAccuracy() {
        assertEquals(false, msg.isAccurate)
    }

    @Test
    @Throws(Exception::class)
    fun getLongitudeInDegrees() {
        assertEquals(53.010996667, msg.longitudeInDegrees, 0.000000001)
    }

    @Test
    @Throws(Exception::class)
    fun getLatitudeInDegrees() {
        assertEquals(40.005283333, msg.latitudeInDegrees, 0.000000001)
    }

    @Test
    @Throws(Exception::class)
    fun getCourseOverGround() {
        assertEquals(177.0, msg.courseOverGround, 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun getTrueHeading() {
        assertEquals(177.0, msg.trueHeading.toDouble(), 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun getTimeStamp() {
        assertEquals(34, msg.timeStamp.toLong())
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