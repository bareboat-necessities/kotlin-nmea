package net.sf.marineapi.nmea.parser

import junit.framework.TestCase
import net.sf.marineapi.nmea.sentence.BODSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Tests the BOD sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
class BODTest : TestCase() {
    private var empty: BODSentence? = null
    private var bod: BODSentence? = null

    /**
     * setUp
     */
    @Before
    @Throws(Exception::class)
    public override fun setUp() {
        try {
            empty = BODParser(TalkerId.GP)
            bod = BODParser(EXAMPLE)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * tearDown
     */
    @After
    @Throws(Exception::class)
    public override fun tearDown() {
    }

    /**
     * Test method for
     * [BODParser] .
     */
    @Test
    fun testConstructor() {
        assertEquals(6, empty!!.getFieldCount())
    }

    /**
     * Test method for
     * [BODParser]
     * .
     */
    @Test
    fun testConstructorWithInvalidSentence() {
        try {
            BODParser("\$HUBBA,habba,doo,dah,doo")
        } catch (e: IllegalArgumentException) {
            // OK
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [BODParser]
     * .
     */
    @Test
    fun testConstructorWithNullString() {
        try {
            BODParser(null as TalkerId?)
        } catch (e: IllegalArgumentException) {
            // OK
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [BODParser]
     * .
     */
    @Test
    fun testConstructorWithNullTalkerId() {
        try {
            BODParser(null as TalkerId?)
        } catch (e: IllegalArgumentException) {
            // OK
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [BODParser]
     * .
     */
    @Test
    fun testConstructorWithRandomString() {
        try {
            BODParser("foobar and haystack")
        } catch (e: IllegalArgumentException) {
            // OK
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [BODParser.getDestinationWaypointId]
     * .
     */
    @Test
    fun testGetDestinationWaypointId() {
        try {
            val id = bod!!.getDestinationWaypointId()
            assertEquals("RUSKI", id)
        } catch (e: ParseException) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [BODParser.getMagneticBearing].
     */
    @Test
    fun testGetMagneticBearing() {
        try {
            val b = bod!!.getMagneticBearing()
            assertEquals(228.8, b, 0.001)
        } catch (e: ParseException) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [BODParser.getOriginWaypointId].
     */
    @Test
    fun testGetOriginWaypointId() {
        try {
            bod!!.getOriginWaypointId()
        } catch (e: DataNotAvailableException) {
            // ok, field is empty
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [BODParser.getTrueBearing].
     */
    @Test
    fun testGetTrueBearing() {
        try {
            val b = bod!!.getTrueBearing()
            assertEquals(234.9, b, 0.001)
        } catch (e: ParseException) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [BODParser.getDestinationWaypointId]
     * .
     */
    @Test
    fun testSetDestinationWaypointId() {
        try {
            bod!!.setDestinationWaypointId("TIISKERI")
            assertEquals("TIISKERI", bod!!.getDestinationWaypointId())
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [BODParser.getDestinationWaypointId]
     * .
     */
    @Test
    fun testSetDestinationWaypointIdWithEmptyStr() {
        try {
            bod!!.setDestinationWaypointId("")
            bod!!.getDestinationWaypointId()
        } catch (e: Exception) {
            assertTrue(e is DataNotAvailableException)
        }
    }

    /**
     * Test method for
     * [BODParser.getDestinationWaypointId]
     * .
     */
    @Test
    fun testSetDestinationWaypointIdWithNull() {
        try {
            bod!!.setDestinationWaypointId(null)
            bod!!.getDestinationWaypointId()
        } catch (e: Exception) {
            assertTrue(e is DataNotAvailableException)
        }
    }

    /**
     * Test method for
     * [BODParser.getMagneticBearing].
     */
    @Test
    fun testSetMagneticBearing() {
        val bearing = 180.0
        try {
            bod!!.setMagneticBearing(bearing)
            assertEquals(bearing, bod!!.getMagneticBearing())
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [BODParser.getMagneticBearing].
     */
    @Test
    fun testSetMagneticBearingWithRounding() {
        val bearing = 65.654321
        try {
            bod!!.setMagneticBearing(bearing)
            assertTrue(bod.toString().contains(",065.7,"))
            assertEquals(bearing, bod!!.getMagneticBearing(), 0.1)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [BODParser.getMagneticBearing].
     */
    @Test
    fun testSetMagneticBearingWithGreaterThanAllowed() {
        try {
            bod!!.setMagneticBearing(360.01)
            fail("Did not throw exception")
        } catch (e: Exception) {
            assertTrue(e is IllegalArgumentException)
        }
    }

    /**
     * Test method for
     * [BODParser.getMagneticBearing].
     */
    @Test
    fun testSetMagneticBearingWithNegativeValue() {
        try {
            bod!!.setMagneticBearing(-0.01)
            fail("Did not throw exception")
        } catch (e: Exception) {
            assertTrue(e is IllegalArgumentException)
        }
    }

    /**
     * Test method for
     * [BODParser.getOriginWaypointId].
     */
    @Test
    fun testSetOriginWaypointId() {
        try {
            bod!!.setOriginWaypointId("TAINIO")
            assertEquals("TAINIO", bod!!.getOriginWaypointId())
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [BODParser.getTrueBearing].
     */
    @Test
    fun testSetTrueBearing() {
        val bearing = 180.0
        try {
            bod!!.setTrueBearing(bearing)
            assertEquals(bearing, bod!!.getTrueBearing())
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [BODParser.getTrueBearing].
     */
    @Test
    fun testSetTrueBearingWithRounding() {
        val bearing = 90.654321
        try {
            bod!!.setTrueBearing(bearing)
            assertTrue(bod.toString().contains(",090.7,"))
            assertEquals(bearing, bod!!.getTrueBearing(), 0.1)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [BODParser.getTrueBearing].
     */
    @Test
    fun testSetTrueBearingGreaterThanAllowed() {
        try {
            bod!!.setTrueBearing(360.01)
            fail("Did not throw exception")
        } catch (e: Exception) {
            assertTrue(e is IllegalArgumentException)
        }
    }

    /**
     * Test method for
     * [BODParser.getTrueBearing].
     */
    @Test
    fun testSetTrueBearingWithNegativeValue() {
        try {
            bod!!.setTrueBearing(-0.01)
            fail("Did not throw exception")
        } catch (e: Exception) {
            assertTrue(e is IllegalArgumentException)
        }
    }

    companion object {
        const val EXAMPLE = "\$GPBOD,234.9,T,228.8,M,RUSKI,*1D"
    }
}