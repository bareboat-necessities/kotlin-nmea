package net.sf.marineapi.ais.parser

import net.sf.marineapi.ais.message.AISMessage24
import net.sf.marineapi.ais.util.Sixbit
import org.junit.Assert
import org.junit.Test

/**
 * AIS message 24 parser tests (parts A & B)
 *
 * Expected values according to http://www.maritec.co.za/tools/aisvdmvdodecoding/
 */
class AISMessage24ParserTest {
    // !AIVDO,1,1,,B,H1c2;qA@PU>0U>060<h5=>0:1Dp,2*7D (part A)
    // !AIVDO,1,1,,B,H1c2;qDTijklmno31<<C970`43<1,0*28 (part B)
    private val payloadA = "H1c2;qA@PU>0U>060<h5=>0:1Dp"
    private val payloadB = "H1c2;qDTijklmno31<<C970`43<1"
    private val sixbitA = Sixbit(payloadA, 2)
    private val sixbitB = Sixbit(payloadB, 0)
    private val partA: AISMessage24 = AISMessage24Parser(sixbitA)
    private val partB: AISMessage24 = AISMessage24Parser(sixbitB)

    @Test
    @Throws(Exception::class)
    fun getPartNumber() {
        Assert.assertEquals(0, partA.partNumber.toLong())
        Assert.assertEquals(1, partB.partNumber.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getName() {
        Assert.assertEquals("THIS IS A CLASS B UN", partA.name)
    }

    @Test
    @Throws(Exception::class)
    fun getTypeOfShipAndCargoType() {
        Assert.assertEquals(36, partB.typeOfShipAndCargoType.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getVendorId() {
        // TODO correct? should be "1234567" according to http://www.maritec.co.za/tools/aisvdmvdodecoding/
        Assert.assertEquals("123", partB.vendorId)
    }

    @Test
    @Throws(Exception::class)
    fun getUnitModelCode() {
        // TODO correct?
        Assert.assertEquals(13, partB.unitModelCode.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getSerialNumber() {
        // TODO correct?
        Assert.assertEquals(220599, partB.serialNumber.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getCallSign() {
        Assert.assertEquals("CALLSIG", partB.callSign)
    }

    @Test
    @Throws(Exception::class)
    fun getBow() {
        Assert.assertEquals(5, partB.bow.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getStern() {
        Assert.assertEquals(4, partB.stern.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getPort() {
        Assert.assertEquals(3, partB.port.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getStarboard() {
        Assert.assertEquals(12, partB.starboard.toLong())
    }

}