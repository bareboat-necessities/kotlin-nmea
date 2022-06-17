package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.sentence.VBWSentence
import net.sf.marineapi.nmea.util.DataStatus
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class VBWTest {
    private var vbw: VBWSentence? = null
    private var empty: VBWSentence? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        vbw = VBWParser(EXAMPLE)
        empty = VBWParser(TalkerId.II)
    }

    @Test
    fun testVBWParserString() {
        Assert.assertEquals(TalkerId.II, vbw!!.getTalkerId())
        Assert.assertEquals(SentenceId.VBW.name, vbw!!.getSentenceId())
        Assert.assertEquals(10, vbw!!.getFieldCount().toLong())
    }

    @Test
    fun testVBWParserTalkerId() {
        Assert.assertEquals(TalkerId.II, empty!!.getTalkerId())
        Assert.assertEquals(SentenceId.VBW.name, empty!!.getSentenceId())
        Assert.assertEquals(10, empty!!.getFieldCount().toLong())
        Assert.assertTrue(empty.toString().startsWith("\$IIVBW,"))
    }

    @Test
    fun testGetLongWaterSpeed() {
        Assert.assertEquals(11.0, vbw!!.getLongWaterSpeed(), 0.1)
    }

    @Test
    fun testGetTravWaterSpeed() {
        Assert.assertEquals(2.0, vbw!!.getTravWaterSpeed(), 0.1)
    }

    @Test
    fun testGetWaterSpeedStatus() {
        Assert.assertEquals(DataStatus.ACTIVE, vbw!!.getWaterSpeedStatus())
    }

    @Test
    fun testGetLongGroundSpeed() {
        Assert.assertEquals(07.5, vbw!!.getLongGroundSpeed(), 0.1)
    }

    @Test
    fun testGetTravGroundSpeed() {
        Assert.assertEquals(13.3, vbw!!.getTravGroundSpeed(), 0.1)
    }

    @Test
    fun testGetGroundSpeedStatus() {
        Assert.assertEquals(DataStatus.ACTIVE, vbw!!.getGroundSpeedStatus())
    }

    @Test
    fun testGetSternWaterSpeed() {
        Assert.assertEquals(06.65, vbw!!.getSternWaterSpeed(), 0.1)
    }

    @Test
    fun testGetSternWaterSpeedStatus() {
        Assert.assertEquals(DataStatus.ACTIVE, vbw!!.getSternWaterSpeedStatus())
    }

    @Test
    fun testGetSternGroundSpeed() {
        Assert.assertEquals(12.3, vbw!!.getSternGroundSpeed(), 0.1)
    }

    @Test
    fun testGetSternGroundSpeedStatus() {
        Assert.assertEquals(DataStatus.ACTIVE, vbw!!.getSternGroundSpeedStatus())
    }

    @Test
    fun testSetLongWaterSpeed() {
        val dir = 23.3
        empty!!.setLongWaterSpeed(dir)
        Assert.assertEquals(dir, empty!!.getLongWaterSpeed(), 0.1)
    }

    @Test
    fun testSetTravWaterSpeed() {
        val dir = 23.3
        empty!!.setTravWaterSpeed(dir)
        Assert.assertEquals(dir, empty!!.getTravWaterSpeed(), 0.1)
    }

    @Test
    fun testSetWaterSpeedStatus() {
        empty!!.setWaterSpeedStatus(DataStatus.VOID)
        Assert.assertEquals(DataStatus.VOID, empty!!.getWaterSpeedStatus())
    }

    @Test
    fun testSetLongGroundSpeed() {
        val dir = 23.3
        empty!!.setLongGroundSpeed(dir)
        Assert.assertEquals(dir, empty!!.getLongGroundSpeed(), 0.1)
    }

    @Test
    fun testSetTravGroundSpeed() {
        val dir = 23.3
        empty!!.setTravGroundSpeed(dir)
        Assert.assertEquals(dir, empty!!.getTravGroundSpeed(), 0.1)
    }

    @Test
    fun testSetGroundSpeedStatus() {
        empty!!.setGroundSpeedStatus(DataStatus.VOID)
        Assert.assertEquals(DataStatus.VOID, empty!!.getGroundSpeedStatus())
    }

    @Test
    fun testSetSternWaterSpeed() {
        val dir = 23.3
        empty!!.setSternWaterSpeed(dir)
        Assert.assertEquals(dir, empty!!.getSternWaterSpeed(), 0.1)
    }

    @Test
    fun testSetSternWaterSpeedStatus() {
        empty!!.setSternWaterSpeedStatus(DataStatus.VOID)
        Assert.assertEquals(DataStatus.VOID, empty!!.getSternWaterSpeedStatus())
    }

    @Test
    fun testSetSternGroundSpeed() {
        val dir = 23.3
        empty!!.setSternGroundSpeed(dir)
        Assert.assertEquals(dir, empty!!.getSternGroundSpeed(), 0.1)
    }

    @Test
    fun testSetSternGroundSpeedStatus() {
        empty!!.setSternGroundSpeedStatus(DataStatus.VOID)
        Assert.assertEquals(DataStatus.VOID, empty!!.getSternGroundSpeedStatus())
    }

    companion object {
        const val EXAMPLE = "\$IIVBW,11.0,02.0,A,07.5,13.3,A,06.65,A,12.3,A"
    }
}