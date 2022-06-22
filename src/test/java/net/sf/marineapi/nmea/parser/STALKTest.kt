package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.STALKSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

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
        assertEquals(4, stalk!!.getFieldCount().toLong())
        assertEquals(TalkerId.ST, stalk!!.getTalkerId())
        assertEquals(SentenceId.ALK.name, stalk!!.getSentenceId())
        assertEquals(2, empty!!.getFieldCount().toLong())
        assertEquals(TalkerId.ST, empty!!.getTalkerId())
        assertEquals(SentenceId.ALK.name, empty!!.getSentenceId())
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
        assertEquals("52", stalk!!.getCommand())
    }

    @Test
    fun testSetCommand() {
        empty!!.setCommand("25")
        assertEquals("25", empty!!.getCommand())
    }

    @Test
    fun testGetParameters() {
        val params = stalk!!.getParameters()
        assertEquals(3, params!!.size.toLong())
        assertEquals("A1", params[0])
        assertEquals("00", params[1])
        assertEquals("00", params[2])
    }

    @Test
    fun testSetParameters() {
        empty!!.setParameters("A1", "A2", "A3", "A4")
        val params = empty!!.getParameters()
        assertEquals(5, empty!!.getFieldCount().toLong())
        assertEquals(4, params!!.size.toLong())
        assertEquals("A1", params[0])
        assertEquals("A2", params[1])
        assertEquals("A3", params[2])
        assertEquals("A4", params[3])
    }

    @Test
    fun testAddParameter() {
        stalk!!.addParameter("B1")
        val params = stalk!!.getParameters()
        assertEquals("B1", params!![params.size - 1])
    }

    companion object {
        const val EXAMPLE = "\$STALK,52,A1,00,00*36"
    }
}