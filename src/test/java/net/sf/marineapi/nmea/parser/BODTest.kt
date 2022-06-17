package net.sf.marineapi.nmea.parser

import junit.framework.TestCase

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
    fun setUp() {
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
    fun tearDown() {
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.BODParser.BODParser] .
     */
    @Test
    fun testConstructor() {
        assertEquals(6, empty.fieldCount)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.BODParser.BODParser]
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
     * [net.sf.marineapi.nmea.parser.BODParser.BODParser]
     * .
     */
    @Test
    fun testConstructorWithNullString() {
        try {
            BODParser(null as String?)
        } catch (e: IllegalArgumentException) {
            // OK
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.BODParser.BODParser]
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
     * [net.sf.marineapi.nmea.parser.BODParser.BODParser]
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
     * [net.sf.marineapi.nmea.parser.BODParser.getDestinationWaypointId]
     * .
     */
    @Test
    fun testGetDestinationWaypointId() {
        try {
            val id: String = bod.destinationWaypointId
            assertEquals("RUSKI", id)
        } catch (e: ParseException) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.BODParser.getMagneticBearing].
     */
    @Test
    fun testGetMagneticBearing() {
        try {
            val b: Double = bod.magneticBearing
            assertEquals(228.8, b, 0.001)
        } catch (e: ParseException) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.BODParser.getOriginWaypointId].
     */
    @Test
    fun testGetOriginWaypointId() {
        try {
            bod.originWaypointId
        } catch (e: DataNotAvailableException) {
            // ok, field is empty
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.BODParser.getTrueBearing].
     */
    @Test
    fun testGetTrueBearing() {
        try {
            val b: Double = bod.trueBearing
            assertEquals(234.9, b, 0.001)
        } catch (e: ParseException) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.BODParser.getDestinationWaypointId]
     * .
     */
    @Test
    fun testSetDestinationWaypointId() {
        try {
            bod.destinationWaypointId = "TIISKERI"
            assertEquals("TIISKERI", bod.destinationWaypointId)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.BODParser.getDestinationWaypointId]
     * .
     */
    @Test
    fun testSetDestinationWaypointIdWithEmptyStr() {
        try {
            bod.destinationWaypointId = ""
            bod.destinationWaypointId
        } catch (e: Exception) {
            assertTrue(e is DataNotAvailableException)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.BODParser.getDestinationWaypointId]
     * .
     */
    @Test
    fun testSetDestinationWaypointIdWithNull() {
        try {
            bod.destinationWaypointId = null
            bod.destinationWaypointId
        } catch (e: Exception) {
            assertTrue(e is DataNotAvailableException)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.BODParser.getMagneticBearing].
     */
    @Test
    fun testSetMagneticBearing() {
        val bearing = 180.0
        try {
            bod.magneticBearing = bearing
            assertEquals(bearing, bod.magneticBearing)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.BODParser.getMagneticBearing].
     */
    @Test
    fun testSetMagneticBearingWithRounding() {
        val bearing = 65.654321
        try {
            bod.magneticBearing = bearing
            assertTrue(bod.toString().contains(",065.7,"))
            assertEquals(bearing, bod.magneticBearing, 0.1)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.BODParser.getMagneticBearing].
     */
    @Test
    fun testSetMagneticBearingWithGreaterThanAllowed() {
        try {
            bod.magneticBearing = 360.01
            fail("Did not throw exception")
        } catch (e: Exception) {
            assertTrue(e is IllegalArgumentException)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.BODParser.getMagneticBearing].
     */
    @Test
    fun testSetMagneticBearingWithNegativeValue() {
        try {
            bod.magneticBearing = -0.01
            fail("Did not throw exception")
        } catch (e: Exception) {
            assertTrue(e is IllegalArgumentException)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.BODParser.getOriginWaypointId].
     */
    @Test
    fun testSetOriginWaypointId() {
        try {
            bod.originWaypointId = "TAINIO"
            assertEquals("TAINIO", bod.originWaypointId)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.BODParser.getTrueBearing].
     */
    @Test
    fun testSetTrueBearing() {
        val bearing = 180.0
        try {
            bod.trueBearing = bearing
            assertEquals(bearing, bod.trueBearing)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.BODParser.getTrueBearing].
     */
    @Test
    fun testSetTrueBearingWithRounding() {
        val bearing = 90.654321
        try {
            bod.trueBearing = bearing
            assertTrue(bod.toString().contains(",090.7,"))
            assertEquals(bearing, bod.trueBearing, 0.1)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.BODParser.getTrueBearing].
     */
    @Test
    fun testSetTrueBearingGreaterThanAllowed() {
        try {
            bod.trueBearing = 360.01
            fail("Did not throw exception")
        } catch (e: Exception) {
            assertTrue(e is IllegalArgumentException)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.BODParser.getTrueBearing].
     */
    @Test
    fun testSetTrueBearingWithNegativeValue() {
        try {
            bod.trueBearing = -0.01
            fail("Did not throw exception")
        } catch (e: Exception) {
            assertTrue(e is IllegalArgumentException)
        }
    }

    companion object {
        const val EXAMPLE = "\$GPBOD,234.9,T,228.8,M,RUSKI,*1D"
    }
}