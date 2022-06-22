package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.RTESentence
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.RouteType
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
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
        assertEquals(4, empty!!.getFieldCount().toLong())
    }

    /**
     * Test method for
     * [RTEParser.addWaypointId].
     */
    @Test
    fun testAddWaypointId() {
        empty!!.addWaypointId("1ST")
        assertTrue(empty.toString().contains(",1ST*"))
        empty!!.addWaypointId("2ND")
        assertTrue(empty.toString().contains(",1ST,2ND*"))
        empty!!.addWaypointId("3RD")
        assertTrue(empty.toString().contains(",1ST,2ND,3RD*"))
    }

    /**
     * Test method for
     * [RTEParser.getRouteId].
     */
    @Test
    fun testGetRouteId() {
        assertEquals("0", rte!!.getRouteId())
    }

    /**
     * Test method for
     * [RTEParser.getSentenceCount].
     */
    @Test
    fun testGetSentenceCount() {
        assertEquals(1, rte!!.getSentenceCount().toLong())
    }

    /**
     * Test method for
     * [RTEParser.getSentenceIndex].
     */
    @Test
    fun testGetSentenceIndex() {
        assertEquals(1, rte!!.getSentenceIndex().toLong())
    }

    /**
     * Test method for
     * [RTEParser.getWaypointCount].
     */
    @Test
    fun testGetWaypointCount() {
        assertEquals(3, rte!!.getWaypointCount().toLong())
    }

    /**
     * Test method for
     * [RTEParser.getWaypointIds].
     */
    @Test
    fun testGetWaypointIds() {
        val ids = rte!!.getWaypointIds()
        assertNotNull(ids)
        assertEquals(3, ids!!.size.toLong())
        assertEquals("MELIN", ids[0])
        assertEquals("RUSKI", ids[1])
        assertEquals("KNUDAN", ids[2])
    }

    /**
     * Test method for
     * [RTEParser.isActiveRoute].
     */
    @Test
    fun testIsActiveRoute() {
        assertTrue(rte!!.isActiveRoute())
    }

    /**
     * Test method for [RTEParser.isFirst].
     */
    @Test
    fun testIsFirst() {
        assertTrue(rte!!.isFirst())
    }

    /**
     * Test method for [RTEParser.isLast].
     */
    @Test
    fun testIsLast() {
        assertTrue(rte!!.isLast())
    }

    /**
     * Test method for
     * [RTEParser.isWorkingRoute].
     */
    @Test
    fun testIsWorkingRoute() {
        assertFalse(rte!!.isWorkingRoute())
    }

    /**
     * Test method for
     * [RTEParser.getRouteId].
     */
    @Test
    fun testSetRouteId() {
        rte!!.setRouteId("ID")
        assertEquals("ID", rte!!.getRouteId())
    }

    /**
     * Test method for
     * [RTEParser.isActiveRoute].
     */
    @Test
    fun testSetRouteTypeActive() {
        rte!!.setRouteType(RouteType.ACTIVE)
        assertTrue(rte!!.isActiveRoute())
        assertFalse(rte!!.isWorkingRoute())
    }

    /**
     * Test method for
     * [RTEParser.isActiveRoute].
     */
    @Test
    fun testSetRouteTypeWorking() {
        rte!!.setRouteType(RouteType.WORKING)
        assertTrue(rte!!.isWorkingRoute())
        assertFalse(rte!!.isActiveRoute())
    }

    /**
     * Test method for
     * [RTEParser.setSentenceCount].
     */
    @Test
    fun testSetSentenceCount() {
        rte!!.setSentenceCount(3)
        assertEquals(3, rte!!.getSentenceCount().toLong())
    }

    /**
     * Test method for
     * [RTEParser.setSentenceCount].
     */
    @Test
    fun testSetSentenceCountWithNegativeValue() {
        try {
            rte!!.setSentenceCount(-1)
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message!!.contains("cannot be negative"))
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [RTEParser.setSentenceIndex].
     */
    @Test
    fun testSetSentenceIndex() {
        rte!!.setSentenceIndex(2)
        assertEquals(2, rte!!.getSentenceIndex().toLong())
    }

    /**
     * Test method for
     * [RTEParser.setSentenceIndex].
     */
    @Test
    fun testSetSentenceIndexWithNegativeValue() {
        try {
            rte!!.setSentenceIndex(-1)
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message!!.contains("cannot be negative"))
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [RTEParser.setWaypointIds].
     */
    @Test
    fun testSetWaypointIds() {
        val ids = arrayOf<String?>("ONE", "TWO", "THREE", "FOUR", "FIVE")
        val expected = "\$GPRTE,1,1,c,0,ONE,TWO,THREE,FOUR,FIVE*7F"
        rte!!.setWaypointIds(ids)
        assertEquals(5, rte!!.getWaypointCount().toLong())
        assertEquals(expected, rte.toString())
        assertTrue(Arrays.equals(ids, rte!!.getWaypointIds()))
        empty!!.setWaypointIds(ids)
        assertEquals(5, empty!!.getWaypointCount().toLong())
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