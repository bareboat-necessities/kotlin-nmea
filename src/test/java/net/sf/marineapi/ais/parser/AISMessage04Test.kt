package net.sf.marineapi.ais.parser

import net.sf.marineapi.ais.message.AISMessage04
import net.sf.marineapi.ais.util.Sixbit

import org.junit.Assert.assertEquals
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

    @Test
    @Throws(Exception::class)
    fun getUtcYear() {
        assertEquals(2012, msg.utcYear.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getUtcMonth() {
        assertEquals(6, msg.utcMonth.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getUtcDay() {
        assertEquals(8, msg.utcDay.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getUtcHour() {
        assertEquals(7, msg.utcHour.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getUtcMinute() {
        assertEquals(38, msg.utcMinute.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getUtcSecond() {
        assertEquals(20, msg.utcSecond.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getLatitudeInDegrees() {
        assertEquals(-29.870835, msg.latitudeInDegrees, 0.000001)
    }

    @Test
    @Throws(Exception::class)
    fun getLongitudeInDegrees() {
        assertEquals(31.033513, msg.longitudeInDegrees, 0.000001)
    }

    @Test
    @Throws(Exception::class)
    fun getTypeOfEPFD() {
        // 1 = GPS
        assertEquals(1, msg.typeOfEPFD.toLong())
    }
}