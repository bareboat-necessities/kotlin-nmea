package net.sf.marineapi.nmea.parser

import org.junit.Assert.assertEquals

/**
 * Tests the GSA sentence parser.
 *
 * @author Kimmo Tuukkanen
 */
class GSATest {
    private var empty: GSASentence? = null
    private var instance: GSASentence? = null
    @Before
    fun setUp() {
        try {
            empty = GSAParser(TalkerId.GP)
            instance = GSAParser(EXAMPLE)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    @Test
    fun testConstructor() {
        assertEquals(17, empty.fieldCount)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.GSAParser.getFixStatus] .
     */
    @Test
    fun testGetFixStatus() {
        assertEquals(GpsFixStatus.GPS_3D, instance.fixStatus)
    }

    /**
     * Test method for [net.sf.marineapi.nmea.parser.GSAParser.getMode].
     */
    @Test
    fun testGetFaaMode() {
        assertEquals(FaaMode.AUTOMATIC, instance.mode)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.GSAParser.getHorizontalDOP].
     */
    @Test
    fun testGetHorizontalDOP() {
        val hdop: Double = instance.horizontalDOP
        assertEquals(1.6, hdop, 0.001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.GSAParser.getPositionDOP].
     */
    @Test
    fun testGetPositionDOP() {
        val pdop: Double = instance.positionDOP
        assertEquals(1.6, pdop, 0.001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.GSAParser.getSatelliteIds].
     */
    @Test
    fun testGetSatelliteIds() {
        val satellites: Array<String> = instance.satelliteIds
        assertEquals(5, satellites.size)
        assertEquals("02", satellites[0])
        assertEquals("07", satellites[1])
        assertEquals("09", satellites[2])
        assertEquals("24", satellites[3])
        assertEquals("26", satellites[4])
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.GSAParser.getVerticalDOP].
     */
    @Test
    fun testGetVerticalDOP() {
        val vdop: Double = instance.verticalDOP
        assertEquals(1.0, vdop, 0.001)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.GSAParser.setFixStatus]
     * .
     */
    @Test
    fun testSetFixStatus() {
        instance.fixStatus = GpsFixStatus.GPS_NA
        assertTrue(instance.toString().contains(",A,1,"))
        assertEquals(GpsFixStatus.GPS_NA, instance.fixStatus)
        instance.fixStatus = GpsFixStatus.GPS_2D
        assertTrue(instance.toString().contains(",A,2,"))
        assertEquals(GpsFixStatus.GPS_2D, instance.fixStatus)
        instance.fixStatus = GpsFixStatus.GPS_3D
        assertTrue(instance.toString().contains(",A,3,"))
        assertEquals(GpsFixStatus.GPS_3D, instance.fixStatus)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.GSAParser.setMode].
     */
    @Test
    fun testSetFaaMode() {
        instance.mode = FaaMode.DGPS
        assertTrue(instance.toString().contains(",D,"))
        assertEquals(FaaMode.DGPS, instance.mode)
        instance.mode = FaaMode.SIMULATED
        assertTrue(instance.toString().contains(",S,"))
        assertEquals(FaaMode.SIMULATED, instance.mode)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.GSAParser.setHorizontalDOP].
     */
    @Test
    fun testSetHorizontalDOP() {
        val hdop = 1.98765
        instance.horizontalDOP = hdop
        assertEquals(hdop, instance.horizontalDOP, 0.1)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.GSAParser.setPositionDOP].
     */
    @Test
    fun testSetPositionDOP() {
        val pdop = 1.56788
        instance.positionDOP = pdop
        assertEquals(pdop, instance.positionDOP, 0.1)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.GSAParser.setSatellitesIds]
     * .
     */
    @Test
    fun testSetSatelliteIds() {
        val ids = arrayOf("02", "04", "06", "08", "10", "12")
        instance.satelliteIds = ids
        val satellites: Array<String> = instance.satelliteIds
        assertEquals(ids.size, satellites.size)
        var i = 0
        for (id in ids) {
            assertEquals(id, satellites[i++])
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.GSAParser.setVerticalDOP].
     */
    @Test
    fun testSetVerticalDOP() {
        val vdop = 1.56789
        instance.verticalDOP = vdop
        assertEquals(vdop, instance.verticalDOP, 0.1)
    }

    companion object {
        /** Example sentence  */
        const val EXAMPLE = "\$GPGSA,A,3,02,,,07,,09,24,26,,,,,1.6,1.6,1.0*3D"
    }
}