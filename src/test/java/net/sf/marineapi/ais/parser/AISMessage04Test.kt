package net.sf.marineapi.ais.parser

import net.sf.marineapi.ais.message.AISMessage04
import net.sf.marineapi.ais.util.Sixbit
import org.junit.Assert
import org.junit.Test

/**
 * AIS message 04 test.
 *
 * Expected values based on http://www.maritec.co.za/tools/aisvdmvdodecoding/
 */
class AISMessage04Test {
    // !AIVDM,1,1,,A,400TcdiuiT7VDR>3nIfr6>i00000,0*78
    private val payload = "400TcdiuiT7VDR>3nIfr6>i00000"
    private val sixbit = Sixbit(payload, 0)
    private val msg: AISMessage04 = AISMessage04Parser(sixbit)

    @get:Throws(Exception::class)
    @get:Test
    val utcYear: Unit
        get() {
            Assert.assertEquals(2012, msg.utcYear.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val utcMonth: Unit
        get() {
            Assert.assertEquals(6, msg.utcMonth.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val utcDay: Unit
        get() {
            Assert.assertEquals(8, msg.utcDay.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val utcHour: Unit
        get() {
            Assert.assertEquals(7, msg.utcHour.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val utcMinute: Unit
        get() {
            Assert.assertEquals(38, msg.utcMinute.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val utcSecond: Unit
        get() {
            Assert.assertEquals(20, msg.utcSecond.toLong())
        }

    @get:Throws(Exception::class)
    @get:Test
    val latitudeInDegrees: Unit
        get() {
            Assert.assertEquals(-29.870835, msg.latitudeInDegrees, 0.000001)
        }

    @get:Throws(Exception::class)
    @get:Test
    val longitudeInDegrees: Unit
        get() {
            Assert.assertEquals(31.033513, msg.longitudeInDegrees, 0.000001)
        }

    // 1 = GPS
    @get:Throws(Exception::class)
    @get:Test
    val typeOfEPFD: Unit
        get() {
            // 1 = GPS
            Assert.assertEquals(1, msg.typeOfEPFD.toLong())
        }
}