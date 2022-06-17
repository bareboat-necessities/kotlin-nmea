package net.sf.marineapi.nmea.parser

import org.junit.Assert.assertEquals

class RPMTest {
    var rpm: RPMSentence? = null
    var empty: RPMSentence? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        rpm = RPMParser(EXAMPLE)
        empty = RPMParser(TalkerId.II)
    }

    @Test
    fun testRPMParserString() {
        assertEquals(TalkerId.II, rpm.talkerId)
        assertEquals("RPM", rpm.sentenceId)
        assertEquals(5, rpm.fieldCount)
    }

    @Test
    fun testRPMParserTalkerId() {
        assertEquals(TalkerId.II, empty.talkerId)
        assertEquals("RPM", empty.sentenceId)
        assertEquals(5, empty.fieldCount)
    }

    @Test
    fun testGetId() {
        assertEquals(1, rpm.id)
    }

    @Test
    fun testGetPitch() {
        assertEquals(10.5, rpm.pitch, 0.1)
    }

    @Test
    fun testGetRPM() {
        assertEquals(2418.2, rpm.rPM, 0.1)
    }

    @Test
    fun testGetSource() {
        assertEquals('E', rpm.source)
    }

    @Test
    fun testGetStatus() {
        assertEquals(DataStatus.ACTIVE, rpm.status)
    }

    @Test
    fun testIsEngine() {
        assertTrue(rpm.isEngine)
    }

    @Test
    fun testIsShaft() {
        assertFalse(rpm.isShaft)
    }

    @Test
    fun testSetId() {
        empty.id = 2
        assertEquals(2, empty.id)
    }

    @Test
    fun testSetPitch() {
        empty.pitch = 3.14
        assertEquals(3.1, empty.pitch, 0.1)
    }

    @Test
    fun testSetRPM() {
        empty.rPM = 1234.56
        assertEquals(1234.56, empty.rPM, 0.01)
    }

    @Test
    fun testSetSource() {
        empty.source = RPMSentence.SHAFT
        assertTrue(empty.isShaft)
        assertEquals(RPMSentence.SHAFT, empty.source)
    }

    @Test
    fun testSetInvalidSource() {
        try {
            empty.source = 'A'
            fail("Didn't throw exception")
        } catch (e: Exception) {
            // pass
        }
    }

    @Test
    fun testSetStatus() {
        empty.status = DataStatus.VOID
        assertEquals(DataStatus.VOID, empty.status)
    }

    companion object {
        const val EXAMPLE = "\$IIRPM,E,1,2418.2,10.5,A"
    }
}