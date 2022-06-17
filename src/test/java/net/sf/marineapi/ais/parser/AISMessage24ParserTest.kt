package net.sf.marineapi.ais.parser

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
    private val sixbitA: Sixbit = Sixbit(payloadA, 2)
    private val sixbitB: Sixbit = Sixbit(payloadB, 0)
    private val partA: AISMessage24 = AISMessage24Parser(sixbitA)
    private val partB: AISMessage24 = AISMessage24Parser(sixbitB)

    @get:Throws(Exception::class)
    @get:Test
    val partNumber: Unit
        get() {
            assertEquals(0, partA.partNumber)
            assertEquals(1, partB.partNumber)
        }

    @get:Throws(Exception::class)
    @get:Test
    val name: Unit
        get() {
            assertEquals("THIS IS A CLASS B UN", partA.name)
        }

    @get:Throws(Exception::class)
    @get:Test
    val typeOfShipAndCargoType: Unit
        get() {
            assertEquals(36, partB.typeOfShipAndCargoType)
        }

    // TODO correct? should be "1234567" according to http://www.maritec.co.za/tools/aisvdmvdodecoding/
    @get:Throws(Exception::class)
    @get:Test
    val vendorId: Unit
        get() {
            // TODO correct? should be "1234567" according to http://www.maritec.co.za/tools/aisvdmvdodecoding/
            assertEquals("123", partB.vendorId)
        }

    // TODO correct?
    @get:Throws(Exception::class)
    @get:Test
    val unitModelCode: Unit
        get() {
            // TODO correct?
            assertEquals(13, partB.unitModelCode)
        }

    // TODO correct?
    @get:Throws(Exception::class)
    @get:Test
    val serialNumber: Unit
        get() {
            // TODO correct?
            assertEquals(220599, partB.serialNumber)
        }

    @get:Throws(Exception::class)
    @get:Test
    val callSign: Unit
        get() {
            assertEquals("CALLSIG", partB.callSign)
        }

    @get:Throws(Exception::class)
    @get:Test
    val bow: Unit
        get() {
            assertEquals(5, partB.bow)
        }

    @get:Throws(Exception::class)
    @get:Test
    val stern: Unit
        get() {
            assertEquals(4, partB.stern)
        }

    @get:Throws(Exception::class)
    @get:Test
    val port: Unit
        get() {
            assertEquals(3, partB.port)
        }

    @get:Throws(Exception::class)
    @get:Test
    val starboard: Unit
        get() {
            assertEquals(12, partB.starboard)
        }
}