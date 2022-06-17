package net.sf.marineapi.nmea.parser

import org.junit.Before

/**
 * $STALK parser test
 */
class STALKTest {
    private var stalk: STALKSentence? = null
    private var empty: STALKSentence? = null
    @Before
    fun setUp() {
        try {
            stalk = STALKParser(EXAMPLE)
            empty = STALKParser(TalkerId.ST)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    @Test
    fun testConstructor() {
        assertEquals(4, stalk.fieldCount)
        assertEquals(TalkerId.ST, stalk.talkerId)
        assertEquals(SentenceId.ALK.name, stalk.sentenceId)
        assertEquals(2, empty.fieldCount)
        assertEquals(TalkerId.ST, empty.talkerId)
        assertEquals(SentenceId.ALK.name, empty.sentenceId)
    }

    @Test
    fun testConstructorWithWrongTalkerId() {
        try {
            STALKParser(TalkerId.GP)
            fail("STALK parser did not throw exception on invalid talker-id")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    @Test
    fun testGetCommand() {
        assertEquals("52", stalk.getCommand())
    }

    @Test
    fun testSetCommand() {
        empty.setCommand("25")
        assertEquals("25", empty.getCommand())
    }

    @Test
    fun testGetParameters() {
        val params: Array<String> = stalk.getParameters()
        assertEquals(3, params.size)
        assertEquals("A1", params[0])
        assertEquals("00", params[1])
        assertEquals("00", params[2])
    }

    @Test
    fun testSetParameters() {
        empty.setParameters("A1", "A2", "A3", "A4")
        val params: Array<String> = empty.getParameters()
        assertEquals(5, empty.fieldCount)
        assertEquals(4, params.size)
        assertEquals("A1", params[0])
        assertEquals("A2", params[1])
        assertEquals("A3", params[2])
        assertEquals("A4", params[3])
    }

    @Test
    fun testAddParameter() {
        stalk.addParameter("B1")
        val params: Array<String> = stalk.getParameters()
        assertEquals("B1", params[params.size - 1])
    }

    companion object {
        const val EXAMPLE = "\$STALK,52,A1,00,00*36"
    }
}