package net.sf.marineapi.ais.parser

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import net.sf.marineapi.ais.util.Sixbit
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
    private val message = AisMessage27Parser(sixbit)

    @get:Test
    val repeatIndicator: Unit
        get() {
            assertEquals(3, message.repeatIndicator)
        }

    @get:Test
    val mMSI: Unit
        get() {
            assertEquals(212752000, message.mMSI)
        }

    @get:Test
    val isAccurate: Unit
        get() {
            assertFalse(message.isAccurate)
        }

    @get:Test
    val raimFlag: Unit
        get() {
            assertFalse(message.rAIMFlag)
        }

    @get:Test
    val navigationalStatus: Unit
        get() {
            assertEquals(0, message.navigationalStatus)
        }

    @get:Test
    val longitude: Unit
        get() {
            assertEquals(-7.3566666666666665, message.longitudeInDegrees, 0)
        }

    @get:Test
    val latitude: Unit
        get() {
            assertEquals(56.36333333333334, message.latitudeInDegrees, 0)
        }

    @get:Test
    val speedOverGround: Unit
        get() {
            assertEquals(15.0, message.speedOverGround, 0)
        }

    @get:Test
    val courseOverGround: Unit
        get() {
            assertEquals(340.0, message.courseOverGround, 0)
        }

    @get:Test
    val positionLatency: Unit
        get() {
            assertEquals(0, message.positionLatency)
        }
}