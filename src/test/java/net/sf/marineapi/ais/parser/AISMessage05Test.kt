package net.sf.marineapi.ais.parser

import org.junit.Test

/**
 * AIS Message 05 parser test.
 *
 * Expected values according to http://www.maritec.co.za/aisvdmvdodecoding1.php
 */
class AISMessage05Test {
    // !AIVDM,2,1,0,A,58wt8Ui`g??r21`7S=:22058<v05Htp000000015>8OA;0sk,0*7B
    // !AIVDM,2,2,0,A,eQ8823mDm3kP00000000000,2*5D
    private val payload = "58wt8Ui`g??r21`7S=:22058<v05Htp000000015>8OA;0skeQ8823mDm3kP00000000000"
    private val sixbit: Sixbit = Sixbit(payload, 2)
    private val msg: AISMessage05 = AISMessage05Parser(sixbit)

    @get:Throws(Exception::class)
    @get:Test
    val aISVersionIndicator: Unit
        get() {
            assertEquals(0, msg.aISVersionIndicator)
        }

    @get:Throws(Exception::class)
    @get:Test
    val iMONumber: Unit
        get() {
            assertEquals(439303422, msg.iMONumber)
        }

    @get:Throws(Exception::class)
    @get:Test
    val callSign: Unit
        get() {
            assertEquals("ZA83R", msg.callSign)
        }

    @get:Throws(Exception::class)
    @get:Test
    val name: Unit
        get() {
            assertEquals("ARCO AVON", msg.name)
        }

    @get:Throws(Exception::class)
    @get:Test
    val typeOfShipAndCargoType: Unit
        get() {
            assertEquals(69, msg.typeOfShipAndCargoType)
        }

    @get:Throws(Exception::class)
    @get:Test
    val bow: Unit
        get() {
            assertEquals(113, msg.bow)
        }

    @get:Throws(Exception::class)
    @get:Test
    val stern: Unit
        get() {
            assertEquals(31, msg.stern)
        }

    @get:Throws(Exception::class)
    @get:Test
    val port: Unit
        get() {
            assertEquals(17, msg.port)
        }

    @get:Throws(Exception::class)
    @get:Test
    val starboard: Unit
        get() {
            assertEquals(11, msg.starboard)
        }

    @get:Throws(Exception::class)
    @get:Test
    val typeOfEPFD: Unit
        get() {
            assertEquals(0, msg.typeOfEPFD)
        }

    @get:Throws(Exception::class)
    @get:Test
    val eTAMonth: Unit
        get() {
            assertEquals(3, msg.eTAMonth)
        }

    @get:Throws(Exception::class)
    @get:Test
    val eTADay: Unit
        get() {
            assertEquals(23, msg.eTADay)
        }

    @get:Throws(Exception::class)
    @get:Test
    val eTAHour: Unit
        get() {
            assertEquals(19, msg.eTAHour)
        }

    @get:Throws(Exception::class)
    @get:Test
    val eTAMinute: Unit
        get() {
            assertEquals(45, msg.eTAMinute)
        }

    @get:Throws(Exception::class)
    @get:Test
    val maximumDraught: Unit
        get() {
            assertEquals(13.2, msg.maximumDraught, 0.1)
        }

    @get:Throws(Exception::class)
    @get:Test
    val destination: Unit
        get() {
            assertEquals("HOUSTON", msg.destination)
        }

    @Test
    fun testIsDteReady() {
        assertFalse(msg.isDteReady)
    }
}