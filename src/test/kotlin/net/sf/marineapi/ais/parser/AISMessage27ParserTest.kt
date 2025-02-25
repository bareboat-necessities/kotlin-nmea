package net.sf.marineapi.ais.parser

import net.sf.marineapi.ais.message.AISMessage27
import net.sf.marineapi.ais.util.Sixbit

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

/**
 * AIS message 27 parser tests
 *
 *
 * According to the specification: https://www.navcen.uscg.gov/?pageName=AISMessage27
 */
class AISMessage27ParserTest {
    // !AIVDM,1,1,,,Kk:qFP0?fhT8=7m@,0*50
    private val payload = "Kk:qFP0?fhT8=7m@"
    private val sixbit = Sixbit(payload, 0)
    private val message: AISMessage27 = AisMessage27Parser(sixbit)

    @Test
    fun getRepeatIndicator() {
        assertEquals(3, message.repeatIndicator.toLong())
    }

    @Test
    fun getMMSI() {
        assertEquals(212752000, message.mMSI.toLong())
    }

    @Test
    fun isAccurate() {
        assertFalse(message.isAccurate)
    }

    @Test
    fun getRaimFlag() {
        assertFalse(message.rAIMFlag)
    }

    @Test
    fun getNavigationalStatus() {
        assertEquals(0, message.navigationalStatus.toLong())
    }

    @Test
    fun getLongitude() {
        assertEquals(-7.3566666666666665, message.longitudeInDegrees, 0.0)
    }

    @Test
    fun getLatitude() {
        assertEquals(56.36333333333334, message.latitudeInDegrees, 0.0)
    }

    @Test
    fun getSpeedOverGround() {
        assertEquals(15.0, message.speedOverGround, 0.0)
    }

    @Test
    fun getCourseOverGround() {
        assertEquals(340.0, message.courseOverGround, 0.0)
    }

    @Test
    fun getPositionLatency() {
        assertEquals(0, message.positionLatency.toLong())
    }
}