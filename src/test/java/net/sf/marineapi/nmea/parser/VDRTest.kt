package net.sf.marineapi.nmea.parser

import org.junit.Assert.assertEquals

class VDRTest {
    private var vdr: VDRSentence? = null
    private var empty: VDRSentence? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        vdr = VDRParser(EXAMPLE)
        empty = VDRParser(TalkerId.IN)
    }

    @Test
    fun testVDRParserString() {
        assertEquals(TalkerId.II, vdr.talkerId)
        assertEquals(SentenceId.VDR.name, vdr.sentenceId)
        assertEquals(6, vdr.fieldCount)
    }

    @Test
    fun testVDRParserTalkerId() {
        assertEquals(TalkerId.IN, empty.talkerId)
        assertEquals(SentenceId.VDR.name, empty.sentenceId)
        assertEquals(6, empty.fieldCount)
        assertTrue(empty.toString().startsWith("\$INVDR,,T,,M,,N*"))
    }

    @Test
    fun testGetMagneticDirection() {
        assertEquals(124.5, vdr.getMagneticDirection(), 0.1)
    }

    @Test
    fun testGetSpeed() {
        assertEquals(1.2, vdr.getSpeed(), 0.1)
    }

    @Test
    fun testGetTrueDirection() {
        assertEquals(123.4, vdr.getTrueDirection(), 0.1)
    }

    @Test
    fun testSetMagneticDirection() {
        val dir = 45.123
        empty.setMagneticDirection(dir)
        assertEquals(dir, empty.getMagneticDirection(), 0.1)
    }

    @Test
    fun testSetSpeed() {
        val speed = 2.124
        empty.setSpeed(speed)
        assertEquals(speed, empty.getSpeed(), 0.1)
    }

    @Test
    fun testSetTrueDirection() {
        val dir = 55.234
        empty.setTrueDirection(dir)
        assertEquals(dir, empty.getTrueDirection(), 0.1)
    }

    companion object {
        const val EXAMPLE = "\$IIVDR,123.4,T,124.5,M,1.2,N"
    }
}