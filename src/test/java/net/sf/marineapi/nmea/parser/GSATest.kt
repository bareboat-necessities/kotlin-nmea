package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.GSASentence
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.FaaMode
import net.sf.marineapi.nmea.util.GpsFixStatus
import org.junit.Assert
import org.junit.Before
import org.junit.Test

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
            Assert.fail(e.message)
        }
    }

    @Test
    fun testConstructor() {
        Assert.assertEquals(17, empty!!.getFieldCount().toLong())
    }

    /**
     * Test method for
     * [GSAParser.getFixStatus] .
     */
    @Test
    fun testGetFixStatus() {
        Assert.assertEquals(GpsFixStatus.GPS_3D, instance!!.getFixStatus())
    }

    /**
     * Test method for [GSAParser.getMode].
     */
    @Test
    fun testGetFaaMode() {
        Assert.assertEquals(FaaMode.AUTOMATIC, instance!!.getMode())
    }

    /**
     * Test method for
     * [GSAParser.getHorizontalDOP].
     */
    @Test
    fun testGetHorizontalDOP() {
        val hdop = instance!!.getHorizontalDOP()
        Assert.assertEquals(1.6, hdop, 0.001)
    }

    /**
     * Test method for
     * [GSAParser.getPositionDOP].
     */
    @Test
    fun testGetPositionDOP() {
        val pdop = instance!!.getPositionDOP()
        Assert.assertEquals(1.6, pdop, 0.001)
    }

    /**
     * Test method for
     * [GSAParser.getSatelliteIds].
     */
    @Test
    fun testGetSatelliteIds() {
        val satellites = instance!!.getSatelliteIds()
        Assert.assertEquals(5, satellites!!.size.toLong())
        Assert.assertEquals("02", satellites[0])
        Assert.assertEquals("07", satellites[1])
        Assert.assertEquals("09", satellites[2])
        Assert.assertEquals("24", satellites[3])
        Assert.assertEquals("26", satellites[4])
    }

    /**
     * Test method for
     * [GSAParser.getVerticalDOP].
     */
    @Test
    fun testGetVerticalDOP() {
        val vdop = instance!!.getVerticalDOP()
        Assert.assertEquals(1.0, vdop, 0.001)
    }

    /**
     * Test method for
     * [GSAParser.setFixStatus]
     * .
     */
    @Test
    fun testSetFixStatus() {
        instance!!.setFixStatus(GpsFixStatus.GPS_NA)
        Assert.assertTrue(instance.toString().contains(",A,1,"))
        Assert.assertEquals(GpsFixStatus.GPS_NA, instance!!.getFixStatus())
        instance!!.setFixStatus(GpsFixStatus.GPS_2D)
        Assert.assertTrue(instance.toString().contains(",A,2,"))
        Assert.assertEquals(GpsFixStatus.GPS_2D, instance!!.getFixStatus())
        instance!!.setFixStatus(GpsFixStatus.GPS_3D)
        Assert.assertTrue(instance.toString().contains(",A,3,"))
        Assert.assertEquals(GpsFixStatus.GPS_3D, instance!!.getFixStatus())
    }

    /**
     * Test method for
     * [GSAParser.setMode].
     */
    @Test
    fun testSetFaaMode() {
        instance!!.setMode(FaaMode.DGPS)
        Assert.assertTrue(instance.toString().contains(",D,"))
        Assert.assertEquals(FaaMode.DGPS, instance!!.getMode())
        instance!!.setMode(FaaMode.SIMULATED)
        Assert.assertTrue(instance.toString().contains(",S,"))
        Assert.assertEquals(FaaMode.SIMULATED, instance!!.getMode())
    }

    /**
     * Test method for
     * [GSAParser.setHorizontalDOP].
     */
    @Test
    fun testSetHorizontalDOP() {
        val hdop = 1.98765
        instance!!.setHorizontalDOP(hdop)
        Assert.assertEquals(hdop, instance!!.getHorizontalDOP(), 0.1)
    }

    /**
     * Test method for
     * [GSAParser.setPositionDOP].
     */
    @Test
    fun testSetPositionDOP() {
        val pdop = 1.56788
        instance!!.setPositionDOP(pdop)
        Assert.assertEquals(pdop, instance!!.getPositionDOP(), 0.1)
    }

    /**
     * Test method for
     * [GSAParser.setSatellitesIds]
     * .
     */
    @Test
    fun testSetSatelliteIds() {
        val ids = arrayOf<String?>("02", "04", "06", "08", "10", "12")
        instance!!.setSatelliteIds(ids)
        val satellites = instance!!.getSatelliteIds()
        Assert.assertEquals(ids.size.toLong(), satellites!!.size.toLong())
        for ((i, id) in ids.withIndex()) {
            Assert.assertEquals(id, satellites[i])
        }
    }

    /**
     * Test method for
     * [GSAParser.setVerticalDOP].
     */
    @Test
    fun testSetVerticalDOP() {
        val vdop = 1.56789
        instance!!.setVerticalDOP(vdop)
        Assert.assertEquals(vdop, instance!!.getVerticalDOP(), 0.1)
    }

    companion object {
        /** Example sentence  */
        const val EXAMPLE = "\$GPGSA,A,3,02,,,07,,09,24,26,,,,,1.6,1.6,1.0*3D"
    }
}