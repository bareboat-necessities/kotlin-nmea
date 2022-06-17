package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.sentence.WPLSentence
import net.sf.marineapi.nmea.util.CompassPoint
import net.sf.marineapi.nmea.util.Waypoint
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * WPLTest
 *
 * @author Kimmo Tuukkanen
 */
class WPLTest {
    private var empty: WPLSentence? = null
    private var wpl: WPLSentence? = null
    @Before
    fun setUp() {
        try {
            empty = WPLParser(TalkerId.GP)
            wpl = WPLParser(EXAMPLE)
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    @Test
    fun testConstructor() {
        Assert.assertEquals(5, empty!!.getFieldCount().toLong())
    }

    /**
     * Test method for
     * [WPLParser.getWaypoint].
     */
    @Test
    fun testGetWaypoint() {
        val lat = java.lang.Double.valueOf(55 + 36.200 / 60)
        val lon = java.lang.Double.valueOf(14 + 36.500 / 60)
        val wp = wpl!!.getWaypoint()
        Assert.assertNotNull(wp)
        Assert.assertEquals("RUSKI", wp!!.id)
        Assert.assertEquals(CompassPoint.NORTH, wp.latitudeHemisphere)
        Assert.assertEquals(CompassPoint.EAST, wp.longitudeHemisphere)
        Assert.assertEquals(lat, java.lang.Double.valueOf(wp.latitude))
        Assert.assertEquals(lon, java.lang.Double.valueOf(wp.longitude))
    }

    /**
     * Test method for
     * [WPLParser.setWaypoint].
     */
    @Test
    fun testSetWaypointWithNonZeroValues() {
        val lat = 60 + 11.552 / 60
        val lon = 25 + 1.941 / 60
        val p2 = Waypoint("WAYP2", lat, lon)
        wpl!!.setWaypoint(p2)
        val s2 = wpl.toString()
        Assert.assertTrue(s2.contains(",6011.552,N,02501.941,E,WAYP2*"))
        val p = wpl!!.getWaypoint()
        Assert.assertNotNull(p)
        Assert.assertEquals(lat, p!!.latitude, 0.0000001)
        Assert.assertEquals(lon, p.longitude, 0.0000001)
    }

    /**
     * Test method for
     * [WPLParser.setWaypoint].
     */
    @Test
    fun testSetWaypointWithZeroValues() {
        val p1 = Waypoint("WAYP1", 0.0, 0.0)
        wpl!!.setWaypoint(p1)
        val s1 = wpl.toString()
        Assert.assertTrue(s1.contains(",0000.000,N,00000.000,E,WAYP1*"))
        val p = wpl!!.getWaypoint()
        Assert.assertNotNull(p)
        Assert.assertEquals(0.0, p!!.latitude, 0.0000001)
        Assert.assertEquals(0.0, p.longitude, 0.0000001)
    }

    companion object {
        /** Example sentence  */
        const val EXAMPLE = "\$GPWPL,5536.200,N,01436.500,E,RUSKI*1F"
    }
}