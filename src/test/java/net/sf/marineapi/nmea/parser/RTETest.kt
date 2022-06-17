package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.RTESentence
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.RouteType
import org.junit.Assert
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
            Assert.fail(e.message)
        }
    }

    @Test
    fun testConstructor() {
        Assert.assertEquals(4, empty!!.getFieldCount().toLong())
    }

    /**
     * Test method for
     * [RTEParser.addWaypointId].
     */
    @Test
    fun testAddWaypointId() {
        empty!!.addWaypointId("1ST")
        Assert.assertTrue(empty.toString().contains(",1ST*"))
        empty!!.addWaypointId("2ND")
        Assert.assertTrue(empty.toString().contains(",1ST,2ND*"))
        empty!!.addWaypointId("3RD")
        Assert.assertTrue(empty.toString().contains(",1ST,2ND,3RD*"))
    }

    /**
     * Test method for
     * [RTEParser.getRouteId].
     */
    @Test
    fun testGetRouteId() {
        Assert.assertEquals("0", rte!!.getRouteId())
    }

    /**
     * Test method for
     * [RTEParser.getSentenceCount].
     */
    @Test
    fun testGetSentenceCount() {
        Assert.assertEquals(1, rte!!.getSentenceCount().toLong())
    }

    /**
     * Test method for
     * [RTEParser.getSentenceIndex].
     */
    @Test
    fun testGetSentenceIndex() {
        Assert.assertEquals(1, rte!!.getSentenceIndex().toLong())
    }

    /**
     * Test method for
     * [RTEParser.getWaypointCount].
     */
    @Test
    fun testGetWaypointCount() {
        Assert.assertEquals(3, rte!!.getWaypointCount().toLong())
    }

    /**
     * Test method for
     * [RTEParser.getWaypointIds].
     */
    @Test
    fun testGetWaypointIds() {
        val ids = rte!!.getWaypointIds()
        Assert.assertNotNull(ids)
        Assert.assertEquals(3, ids!!.size.toLong())
        Assert.assertEquals("MELIN", ids[0])
        Assert.assertEquals("RUSKI", ids[1])
        Assert.assertEquals("KNUDAN", ids[2])
    }

    /**
     * Test method for
     * [RTEParser.isActiveRoute].
     */
    @Test
    fun testIsActiveRoute() {
        Assert.assertTrue(rte!!.isActiveRoute())
    }

    /**
     * Test method for [RTEParser.isFirst].
     */
    @Test
    fun testIsFirst() {
        Assert.assertTrue(rte!!.isFirst())
    }

    /**
     * Test method for [RTEParser.isLast].
     */
    @Test
    fun testIsLast() {
        Assert.assertTrue(rte!!.isLast())
    }

    /**
     * Test method for
     * [RTEParser.isWorkingRoute].
     */
    @Test
    fun testIsWorkingRoute() {
        Assert.assertFalse(rte!!.isWorkingRoute())
    }

    /**
     * Test method for
     * [RTEParser.getRouteId].
     */
    @Test
    fun testSetRouteId() {
        rte!!.setRouteId("ID")
        Assert.assertEquals("ID", rte!!.getRouteId())
    }

    /**
     * Test method for
     * [RTEParser.isActiveRoute].
     */
    @Test
    fun testSetRouteTypeActive() {
        rte!!.setRouteType(RouteType.ACTIVE)
        Assert.assertTrue(rte!!.isActiveRoute())
        Assert.assertFalse(rte!!.isWorkingRoute())
    }

    /**
     * Test method for
     * [RTEParser.isActiveRoute].
     */
    @Test
    fun testSetRouteTypeWorking() {
        rte!!.setRouteType(RouteType.WORKING)
        Assert.assertTrue(rte!!.isWorkingRoute())
        Assert.assertFalse(rte!!.isActiveRoute())
    }

    /**
     * Test method for
     * [RTEParser.setSentenceCount].
     */
    @Test
    fun testSetSentenceCount() {
        rte!!.setSentenceCount(3)
        Assert.assertEquals(3, rte!!.getSentenceCount().toLong())
    }

    /**
     * Test method for
     * [RTEParser.setSentenceCount].
     */
    @Test
    fun testSetSentenceCountWithNegativeValue() {
        try {
            rte!!.setSentenceCount(-1)
            Assert.fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            Assert.assertTrue(e.message!!.contains("cannot be negative"))
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    /**
     * Test method for
     * [RTEParser.setSentenceIndex].
     */
    @Test
    fun testSetSentenceIndex() {
        rte!!.setSentenceIndex(2)
        Assert.assertEquals(2, rte!!.getSentenceIndex().toLong())
    }

    /**
     * Test method for
     * [RTEParser.setSentenceIndex].
     */
    @Test
    fun testSetSentenceIndexWithNegativeValue() {
        try {
            rte!!.setSentenceIndex(-1)
            Assert.fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            Assert.assertTrue(e.message!!.contains("cannot be negative"))
        } catch (e: Exception) {
            Assert.fail(e.message)
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
        Assert.assertEquals(5, rte!!.getWaypointCount().toLong())
        Assert.assertEquals(expected, rte.toString())
        Assert.assertTrue(Arrays.equals(ids, rte!!.getWaypointIds()))
        empty!!.setWaypointIds(ids)
        Assert.assertEquals(5, empty!!.getWaypointCount().toLong())
        Assert.assertTrue(
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