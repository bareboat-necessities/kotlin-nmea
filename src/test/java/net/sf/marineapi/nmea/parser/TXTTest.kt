package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.TalkerId
import org.junit.Assert
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
            Assert.fail(e.message)
        }
    }

    @Test
    fun testStringConstructor() {
        Assert.assertEquals(TalkerId.GP, txt!!.getTalkerId())
        Assert.assertEquals("TXT", txt!!.getSentenceId())
        Assert.assertEquals(4, txt!!.getFieldCount().toLong())
    }

    @Test
    fun testTalkerIdConstructor() {
        Assert.assertEquals(TalkerId.II, empty!!.getTalkerId())
        Assert.assertEquals("TXT", empty!!.getSentenceId())
        Assert.assertEquals(4, empty!!.getFieldCount().toLong())
    }

    @Test
    fun testGetMessageIndex() {
        Assert.assertEquals(1, txt!!.getMessageIndex().toLong())
    }

    @Test
    fun testSetMessageIndex() {
        empty!!.setMessageIndex(1)
        Assert.assertEquals(1, empty!!.getMessageIndex().toLong())
    }

    @Test
    fun testSetMessageIndexThrows() {
        try {
            empty!!.setMessageIndex(-1)
            Assert.fail("setMessageIndex didn't throw on -1")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    @Test
    fun testGetMessageCount() {
        Assert.assertEquals(1, txt!!.getMessageCount().toLong())
    }

    @Test
    fun testSetMessageCount() {
        empty!!.setMessageCount(1)
        Assert.assertEquals(1, empty!!.getMessageCount().toLong())
    }

    @Test
    fun testSetMessageCountThrows() {
        try {
            empty!!.setMessageCount(0)
            Assert.fail("setMessageIndex didn't throw on 0")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    @Test
    fun testGetIndentifier() {
        Assert.assertEquals("TARG1", txt!!.getIdentifier())
    }

    @Test
    fun testSetIndentifier() {
        empty!!.setIdentifier("FOOBAR")
        Assert.assertEquals("FOOBAR", empty!!.getIdentifier())
    }

    @Test
    fun testGetMessage() {
        Assert.assertEquals("Message", txt!!.getMessage())
    }

    @Test
    fun testSetMessage() {
        empty!!.setMessage("xyzzy")
        Assert.assertEquals("xyzzy", empty!!.getMessage())
    }

    @Test
    fun testSetMessageNonASCII() {
        try {
            empty!!.setMessage("€€ääööåå")
            Assert.fail("setMessage() did not throw on non-ASCII input")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    companion object {
        const val EXAMPLE = "\$GPTXT,01,01,TARG1,Message*35"
    }
}