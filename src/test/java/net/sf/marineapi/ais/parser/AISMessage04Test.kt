package net.sf.marineapi.ais.parser

import net.sf.marineapi.ais.util.Sixbit
import org.junit.Test
import junit.framework.TestCase.*

/**
 * AIS message 04 test.
 *
 * Expected values based on http://www.maritec.co.za/tools/aisvdmvdodecoding/
 */
class AISMessage04Test {
    // !AIVDM,1,1,,A,400TcdiuiT7VDR>3nIfr6>i00000,0*78
    private val payload = "400TcdiuiT7VDR>3nIfr6>i00000"
    private val sixbit: Sixbit = Sixbit(payload, 0)
    private val msg = AISMessage04Parser(sixbit)

    @get:Throws(Exception::class)
    @get:Test
    val utcYear: Unit
        get() {
            assertEquals(2012, msg.utcYear)
        }

    @get:Throws(Exception::class)
    @get:Test
    val utcMonth: Unit
        get() {
            assertEquals(6, msg.utcMonth)
        }

    @get:Throws(Exception::class)
    @get:Test
    val utcDay: Unit
        get() {
            assertEquals(8, msg.utcDay)
        }

    @get:Throws(Exception::class)
    @get:Test
    val utcHour: Unit
        get() {
            assertEquals(7, msg.utcHour)
        }

    @get:Throws(Exception::class)
    @get:Test
    val utcMinute: Unit
        get() {
            assertEquals(38, msg.utcMinute)
        }

    @get:Throws(Exception::class)
    @get:Test
    val utcSecond: Unit
        get() {
            assertEquals(20, msg.utcSecond)
        }

    @get:Throws(Exception::class)
    @get:Test
    val latitudeInDegrees: Unit
        get() {
            assertEquals(-29.870835, msg.latitudeInDegrees, 0.000001)
        }

    @get:Throws(Exception::class)
    @get:Test
    val longitudeInDegrees: Unit
        get() {
            assertEquals(31.033513, msg.longitudeInDegrees, 0.000001)
        }

    // 1 = GPS
    @get:Throws(Exception::class)
    @get:Test
    val typeOfEPFD: Unit
        get() {
            // 1 = GPS
            assertEquals(1, msg.typeOfEPFD)
        }
}