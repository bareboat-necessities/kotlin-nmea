package net.sf.marineapi.nmea.parser

import org.junit.Assert.assertEquals

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
            fail(e.message)
        }
    }

    @Test
    fun testConstructor() {
        assertEquals(5, empty.fieldCount)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.WPLParser.getWaypoint].
     */
    @Test
    fun testGetWaypoint() {
        val lat = java.lang.Double.valueOf(55 + 36.200 / 60)
        val lon = java.lang.Double.valueOf(14 + 36.500 / 60)
        val wp: Waypoint = wpl.getWaypoint()
        assertNotNull(wp)
        assertEquals("RUSKI", wp.id)
        assertEquals(CompassPoint.NORTH, wp.latitudeHemisphere)
        assertEquals(CompassPoint.EAST, wp.longitudeHemisphere)
        assertEquals(lat, java.lang.Double.valueOf(wp.latitude))
        assertEquals(lon, java.lang.Double.valueOf(wp.longitude))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.WPLParser.setWaypoint].
     */
    @Test
    fun testSetWaypointWithNonZeroValues() {
        val lat = 60 + 11.552 / 60
        val lon = 25 + 1.941 / 60
        val p2 = Waypoint("WAYP2", lat, lon)
        wpl.setWaypoint(p2)
        val s2: String = wpl.toString()
        assertTrue(s2.contains(",6011.552,N,02501.941,E,WAYP2*"))
        val p: Waypoint = wpl.getWaypoint()
        assertNotNull(p)
        assertEquals(lat, p.latitude, 0.0000001)
        assertEquals(lon, p.longitude, 0.0000001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.WPLParser.setWaypoint].
     */
    @Test
    fun testSetWaypointWithZeroValues() {
        val p1 = Waypoint("WAYP1", 0.0, 0.0)
        wpl.setWaypoint(p1)
        val s1: String = wpl.toString()
        assertTrue(s1.contains(",0000.000,N,00000.000,E,WAYP1*"))
        val p: Waypoint = wpl.getWaypoint()
        assertNotNull(p)
        assertEquals(0.0, p.latitude, 0.0000001)
        assertEquals(0.0, p.longitude, 0.0000001)
    }

    companion object {
        /** Example sentence  */
        const val EXAMPLE = "\$GPWPL,5536.200,N,01436.500,E,RUSKI*1F"
    }
}