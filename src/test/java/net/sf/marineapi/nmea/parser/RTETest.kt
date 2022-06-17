package net.sf.marineapi.nmea.parser

import org.junit.Assert.assertEquals
import java.util.*

/**
 * RTETest
 *
 * @author Kimmo Tuukkanen
 */
class RTETest {
    private var empty: RTESentence? = null
    private var rte: RTESentence? = null
    @Before
    fun setUp() {
        try {
            empty = RTEParser(TalkerId.GP)
            rte = RTEParser(EXAMPLE)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    @Test
    fun testConstructor() {
        assertEquals(4, empty.fieldCount)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RTEParser.addWaypointId].
     */
    @Test
    fun testAddWaypointId() {
        empty.addWaypointId("1ST")
        assertTrue(empty.toString().contains(",1ST*"))
        empty.addWaypointId("2ND")
        assertTrue(empty.toString().contains(",1ST,2ND*"))
        empty.addWaypointId("3RD")
        assertTrue(empty.toString().contains(",1ST,2ND,3RD*"))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RTEParser.getRouteId].
     */
    @Test
    fun testGetRouteId() {
        assertEquals("0", rte.routeId)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RTEParser.getSentenceCount].
     */
    @Test
    fun testGetSentenceCount() {
        assertEquals(1, rte.sentenceCount)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RTEParser.getSentenceIndex].
     */
    @Test
    fun testGetSentenceIndex() {
        assertEquals(1, rte.sentenceIndex)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RTEParser.getWaypointCount].
     */
    @Test
    fun testGetWaypointCount() {
        assertEquals(3, rte.waypointCount)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RTEParser.getWaypointIds].
     */
    @Test
    fun testGetWaypointIds() {
        val ids: Array<String> = rte.waypointIds
        assertNotNull(ids)
        assertEquals(3, ids.size)
        assertEquals("MELIN", ids[0])
        assertEquals("RUSKI", ids[1])
        assertEquals("KNUDAN", ids[2])
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RTEParser.isActiveRoute].
     */
    @Test
    fun testIsActiveRoute() {
        assertTrue(rte.isActiveRoute)
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.RTEParser.isFirst].
     */
    @Test
    fun testIsFirst() {
        assertTrue(rte.isFirst)
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.RTEParser.isLast].
     */
    @Test
    fun testIsLast() {
        assertTrue(rte.isLast)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RTEParser.isWorkingRoute].
     */
    @Test
    fun testIsWorkingRoute() {
        assertFalse(rte.isWorkingRoute)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RTEParser.getRouteId].
     */
    @Test
    fun testSetRouteId() {
        rte.routeId = "ID"
        assertEquals("ID", rte.routeId)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RTEParser.isActiveRoute].
     */
    @Test
    fun testSetRouteTypeActive() {
        rte.setRouteType(RouteType.ACTIVE)
        assertTrue(rte.isActiveRoute)
        assertFalse(rte.isWorkingRoute)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RTEParser.isActiveRoute].
     */
    @Test
    fun testSetRouteTypeWorking() {
        rte.setRouteType(RouteType.WORKING)
        assertTrue(rte.isWorkingRoute)
        assertFalse(rte.isActiveRoute)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RTEParser.setSentenceCount].
     */
    @Test
    fun testSetSentenceCount() {
        rte.sentenceCount = 3
        assertEquals(3, rte.sentenceCount)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RTEParser.setSentenceCount].
     */
    @Test
    fun testSetSentenceCountWithNegativeValue() {
        try {
            rte.sentenceCount = -1
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message!!.contains("cannot be negative"))
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RTEParser.setSentenceIndex].
     */
    @Test
    fun testSetSentenceIndex() {
        rte.sentenceIndex = 2
        assertEquals(2, rte.sentenceIndex)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RTEParser.setSentenceIndex].
     */
    @Test
    fun testSetSentenceIndexWithNegativeValue() {
        try {
            rte.sentenceIndex = -1
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message!!.contains("cannot be negative"))
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.RTEParser.setWaypointIds].
     */
    @Test
    fun testSetWaypointIds() {
        val ids = arrayOf("ONE", "TWO", "THREE", "FOUR", "FIVE")
        val expected = "\$GPRTE,1,1,c,0,ONE,TWO,THREE,FOUR,FIVE*7F"
        rte.waypointIds = ids
        assertEquals(5, rte.waypointCount)
        assertEquals(expected, rte.toString())
        assertTrue(Arrays.equals(ids, rte.waypointIds))
        empty.waypointIds = ids
        assertEquals(5, empty.waypointCount)
        assertTrue(
            empty.toString().startsWith(
                "\$GPRTE,,,,,ONE,TWO,THREE,FOUR,FIVE*"
            )
        )
    }

    companion object {
        /** Example sentence  */
        const val EXAMPLE = "\$GPRTE,1,1,c,0,MELIN,RUSKI,KNUDAN*25"
    }
}