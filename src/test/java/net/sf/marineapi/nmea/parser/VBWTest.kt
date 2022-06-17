package net.sf.marineapi.nmea.parser

import org.junit.Assert.assertEquals

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
        assertEquals(TalkerId.II, vbw.talkerId)
        assertEquals(SentenceId.VBW.name, vbw.sentenceId)
        assertEquals(10, vbw.fieldCount)
    }

    @Test
    fun testVBWParserTalkerId() {
        assertEquals(TalkerId.II, empty.talkerId)
        assertEquals(SentenceId.VBW.name, empty.sentenceId)
        assertEquals(10, empty.fieldCount)
        assertTrue(empty.toString().startsWith("\$IIVBW,"))
    }

    @Test
    fun testGetLongWaterSpeed() {
        assertEquals(11.0, vbw.getLongWaterSpeed(), 0.1)
    }

    @Test
    fun testGetTravWaterSpeed() {
        assertEquals(2.0, vbw.getTravWaterSpeed(), 0.1)
    }

    @Test
    fun testGetWaterSpeedStatus() {
        assertEquals(DataStatus.ACTIVE, vbw.getWaterSpeedStatus())
    }

    @Test
    fun testGetLongGroundSpeed() {
        assertEquals(07.5, vbw.getLongGroundSpeed(), 0.1)
    }

    @Test
    fun testGetTravGroundSpeed() {
        assertEquals(13.3, vbw.getTravGroundSpeed(), 0.1)
    }

    @Test
    fun testGetGroundSpeedStatus() {
        assertEquals(DataStatus.ACTIVE, vbw.getGroundSpeedStatus())
    }

    @Test
    fun testGetSternWaterSpeed() {
        assertEquals(06.65, vbw.getSternWaterSpeed(), 0.1)
    }

    @Test
    fun testGetSternWaterSpeedStatus() {
        assertEquals(DataStatus.ACTIVE, vbw.getSternWaterSpeedStatus())
    }

    @Test
    fun testGetSternGroundSpeed() {
        assertEquals(12.3, vbw.getSternGroundSpeed(), 0.1)
    }

    @Test
    fun testGetSternGroundSpeedStatus() {
        assertEquals(DataStatus.ACTIVE, vbw.getSternGroundSpeedStatus())
    }

    @Test
    fun testSetLongWaterSpeed() {
        val dir = 23.3
        empty.setLongWaterSpeed(dir)
        assertEquals(dir, empty.getLongWaterSpeed(), 0.1)
    }

    @Test
    fun testSetTravWaterSpeed() {
        val dir = 23.3
        empty.setTravWaterSpeed(dir)
        assertEquals(dir, empty.getTravWaterSpeed(), 0.1)
    }

    @Test
    fun testSetWaterSpeedStatus() {
        empty.setWaterSpeedStatus(DataStatus.VOID)
        assertEquals(DataStatus.VOID, empty.getWaterSpeedStatus())
    }

    @Test
    fun testSetLongGroundSpeed() {
        val dir = 23.3
        empty.setLongGroundSpeed(dir)
        assertEquals(dir, empty.getLongGroundSpeed(), 0.1)
    }

    @Test
    fun testSetTravGroundSpeed() {
        val dir = 23.3
        empty.setTravGroundSpeed(dir)
        assertEquals(dir, empty.getTravGroundSpeed(), 0.1)
    }

    @Test
    fun testSetGroundSpeedStatus() {
        empty.setGroundSpeedStatus(DataStatus.VOID)
        assertEquals(DataStatus.VOID, empty.getGroundSpeedStatus())
    }

    @Test
    fun testSetSternWaterSpeed() {
        val dir = 23.3
        empty.setSternWaterSpeed(dir)
        assertEquals(dir, empty.getSternWaterSpeed(), 0.1)
    }

    @Test
    fun testSetSternWaterSpeedStatus() {
        empty.setSternWaterSpeedStatus(DataStatus.VOID)
        assertEquals(DataStatus.VOID, empty.getSternWaterSpeedStatus())
    }

    @Test
    fun testSetSternGroundSpeed() {
        val dir = 23.3
        empty.setSternGroundSpeed(dir)
        assertEquals(dir, empty.getSternGroundSpeed(), 0.1)
    }

    @Test
    fun testSetSternGroundSpeedStatus() {
        empty.setSternGroundSpeedStatus(DataStatus.VOID)
        assertEquals(DataStatus.VOID, empty.getSternGroundSpeedStatus())
    }

    companion object {
        const val EXAMPLE = "\$IIVBW,11.0,02.0,A,07.5,13.3,A,06.65,A,12.3,A"
    }
}