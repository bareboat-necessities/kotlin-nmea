package net.sf.marineapi.ais.parser

import net.sf.marineapi.ais.message.AISPositionReportB
import net.sf.marineapi.ais.util.Sixbit
import org.junit.Assert
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
        Assert.assertEquals(1.4, msg.speedOverGround, 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun getPositionAccuracy() {
        Assert.assertEquals(false, msg.isAccurate)
    }

    @Test
    @Throws(Exception::class)
    fun getLongitudeInDegrees() {
        Assert.assertEquals(53.010996667, msg.longitudeInDegrees, 0.000000001)
    }

    @Test
    @Throws(Exception::class)
    fun getLatitudeInDegrees() {
        Assert.assertEquals(40.005283333, msg.latitudeInDegrees, 0.000000001)
    }

    @Test
    @Throws(Exception::class)
    fun getCourseOverGround() {
        Assert.assertEquals(177.0, msg.courseOverGround, 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun getTrueHeading() {
        Assert.assertEquals(177.0, msg.trueHeading.toDouble(), 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun getTimeStamp() {
        Assert.assertEquals(34, msg.timeStamp.toLong())
    }

    @Test
    fun hasLatitude() {
        Assert.assertEquals(true, msg.hasLatitude())
    }

    @Test
    fun hasLongitude() {
        Assert.assertEquals(true, msg.hasLongitude())
    }

    @Test
    fun hasCourseOverGround() {
        Assert.assertEquals(true, msg.hasCourseOverGround())
    }

    @Test
    fun hasSpeedOverGround() {
        Assert.assertEquals(true, msg.hasSpeedOverGround())
    }

    @Test
    fun hasTimeStamp() {
        Assert.assertEquals(true, msg.hasTimeStamp())
    }
}