package net.sf.marineapi.ais.parser

import junit.framework.TestCase.*
import net.sf.marineapi.ais.message.AISMessage21
import net.sf.marineapi.ais.util.Sixbit
import org.junit.Test

/**
 * AIS message 21 tests (Aids To Navigation Report).
 *
 * Expected values based on http://www.maritec.co.za/tools/aisvdmvdodecoding/
 */
class AISMessage21ParserTest {
    // !AIVDO,2,1,5,B,E1c2;q@b44ah4ah0h:2ab@70VRpU<Bgpm4:gP50HH`Th`QF5,0*7B
    // !AIVDO,2,2,5,B,1CQ1A83PCAH0,0*60
    private val payload = "E1c2;q@b44ah4ah0h:2ab@70VRpU<Bgpm4:gP50HH`Th`QF51CQ1A83PCAH0"
    private val sixbit = Sixbit(payload, 0)
    private val msg = AISMessage21Parser(sixbit)

    // Nav Type?
    @get:Throws(Exception::class)
    @get:Test
    val aidType: Unit
        get() {
            // Nav Type?
            assertEquals(1, msg.aidType)
        }

    @get:Throws(Exception::class)
    @get:Test
    val name: Unit
        get() {
            assertEquals("THIS IS A TEST NAME1", msg.name)
        }

    @get:Throws(Exception::class)
    @get:Test
    val positionAccuracy: Unit
        get() {
            assertFalse(msg.isAccurate)
        }

    @get:Throws(Exception::class)
    @get:Test
    val longitudeInDegrees: Unit
        get() {
            assertEquals(145.181, msg.longitudeInDegrees, 0.001)
        }

    @get:Throws(Exception::class)
    @get:Test
    val latitudeInDegrees: Unit
        get() {
            assertEquals(-38.220167, msg.latitudeInDegrees, 0.000001)
        }

    @get:Throws(Exception::class)
    @get:Test
    val bow: Unit
        get() {
            assertEquals(5, msg.bow)
        }

    @get:Throws(Exception::class)
    @get:Test
    val stern: Unit
        get() {
            assertEquals(3, msg.stern)
        }

    @get:Throws(Exception::class)
    @get:Test
    val port: Unit
        get() {
            assertEquals(3, msg.port)
        }

    @get:Throws(Exception::class)
    @get:Test
    val starboard: Unit
        get() {
            assertEquals(5, msg.starboard)
        }

    @get:Throws(Exception::class)
    @get:Test
    val typeOfEPFD: Unit
        get() {
            assertEquals(1, msg.typeOfEPFD)
        }

    // UTC time stamp?
    @get:Throws(Exception::class)
    @get:Test
    val utcSecond: Unit
        get() {
            // UTC time stamp?
            assertEquals(9, msg.utcSecond)
        }

    @get:Throws(Exception::class)
    @get:Test
    val offPositionIndicator: Unit
        get() {
            assertEquals(true, msg.offPositionIndicator)
        }

    // "00001010" ?
    @get:Throws(Exception::class)
    @get:Test
    val regional: Unit
        get() {
            // "00001010" ?
            assertEquals(10, msg.regional)
        }

    @get:Throws(Exception::class)
    @get:Test
    val rAIMFlag: Unit
        get() {
            assertFalse(msg.rAIMFlag)
        }

    @get:Throws(Exception::class)
    @get:Test
    val virtualAidFlag: Unit
        get() {
            assertFalse(msg.virtualAidFlag)
        }

    @get:Throws(Exception::class)
    @get:Test
    val assignedModeFlag: Unit
        get() {
            assertTrue(msg.assignedModeFlag)
        }

    @get:Throws(Exception::class)
    @get:Test
    val nameExtension: Unit
        get() {
            assertEquals("EXTENDED NAME", msg.nameExtension)
        }

    @Test
    fun hasLatitude() {
        assertEquals(true, msg.hasLatitude())
    }

    @Test
    fun hasLongitude() {
        assertEquals(true, msg.hasLongitude())
    }
}