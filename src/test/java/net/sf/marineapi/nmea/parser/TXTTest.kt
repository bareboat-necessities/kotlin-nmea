package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.TalkerId
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class TXTTest {
    private var txt: TXTParser? = null
    private var empty: TXTParser? = null
    @Before
    fun setUp() {
        try {
            txt = TXTParser(EXAMPLE)
            empty = TXTParser(TalkerId.II)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    @Test
    fun testStringConstructor() {
        assertEquals(TalkerId.GP, txt!!.getTalkerId())
        assertEquals("TXT", txt!!.getSentenceId())
        assertEquals(4, txt!!.getFieldCount().toLong())
    }

    @Test
    fun testTalkerIdConstructor() {
        assertEquals(TalkerId.II, empty!!.getTalkerId())
        assertEquals("TXT", empty!!.getSentenceId())
        assertEquals(4, empty!!.getFieldCount().toLong())
    }

    @Test
    fun testGetMessageIndex() {
        assertEquals(1, txt!!.getMessageIndex().toLong())
    }

    @Test
    fun testSetMessageIndex() {
        empty!!.setMessageIndex(1)
        assertEquals(1, empty!!.getMessageIndex().toLong())
    }

    @Test
    fun testSetMessageIndexThrows() {
        try {
            empty!!.setMessageIndex(-1)
            fail("setMessageIndex didn't throw on -1")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    @Test
    fun testGetMessageCount() {
        assertEquals(1, txt!!.getMessageCount().toLong())
    }

    @Test
    fun testSetMessageCount() {
        empty!!.setMessageCount(1)
        assertEquals(1, empty!!.getMessageCount().toLong())
    }

    @Test
    fun testSetMessageCountThrows() {
        try {
            empty!!.setMessageCount(0)
            fail("setMessageIndex didn't throw on 0")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    @Test
    fun testGetIndentifier() {
        assertEquals("TARG1", txt!!.getIdentifier())
    }

    @Test
    fun testSetIndentifier() {
        empty!!.setIdentifier("FOOBAR")
        assertEquals("FOOBAR", empty!!.getIdentifier())
    }

    @Test
    fun testGetMessage() {
        assertEquals("Message", txt!!.getMessage())
    }

    @Test
    fun testSetMessage() {
        empty!!.setMessage("xyzzy")
        assertEquals("xyzzy", empty!!.getMessage())
    }

    @Test
    fun testSetMessageNonASCII() {
        try {
            empty!!.setMessage("€€ääööåå")
            fail("setMessage() did not throw on non-ASCII input")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    companion object {
        const val EXAMPLE = "\$GPTXT,01,01,TARG1,Message*35"
    }
}