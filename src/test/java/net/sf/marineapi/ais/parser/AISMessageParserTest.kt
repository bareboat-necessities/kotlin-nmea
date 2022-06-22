package net.sf.marineapi.ais.parser

import net.sf.marineapi.ais.util.Sixbit
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

/**
 * Test common AIS message parser.
 */
class AISMessageParserTest {
    private val payload = "13aEOK?P00PD2wVMdLDRhgvL289?"
    private val sixbit = Sixbit(payload, 0)
    private val parser = AISMessageParser(sixbit)
    @Test
    fun testGetMessageType() {
        assertEquals(1, parser.messageType.toLong())
    }

    @Test
    fun testGetMMSI() {
        assertEquals(244670316, parser.mMSI.toLong())
    }

    @Test
    fun testGetRepeatIndicator() {
        assertEquals(0, parser.repeatIndicator.toLong())
    }

    @Test
    fun testGetSixbit() {
        val decoder = parser.sixbit
        assertEquals(sixbit.payload, decoder.payload)
    }

    @Test
    fun testAppend() {
        val msg = AISMessageParser()
        msg.append(payload, 1, 0)
        assertEquals(1, msg.messageType.toLong())
        assertEquals(0, msg.repeatIndicator.toLong())
        assertEquals(244670316, msg.mMSI.toLong())
        assertEquals(payload, msg.sixbit.payload)
    }

    @Test
    fun testAppendIncorrectOrder() {
        try {
            val msg = AISMessageParser()
            msg.append(payload, 2, 0)
        } catch (iae: IllegalArgumentException) {
            assertEquals("Invalid fragment index or sequence order", iae.message)
        } catch (e: Exception) {
            fail("Unexpected exception was thrown; " + e.message)
        }
    }

    @Test
    fun testAppendInvalidTail() {
        try {
            val msg = AISMessageParser()
            msg.append(payload, 1, 0)
            msg.append(payload, 1, 0)
            fail("AISMessageParser.append() did not throw exception")
        } catch (iae: IllegalArgumentException) {
            assertEquals("Invalid fragment index or sequence order", iae.message)
        } catch (e: Exception) {
            fail("Unexpected exception thrown from AISMessageParser.append()")
        }
    }

    @Test
    fun testAppendEmptyString() {
        try {
            val msg = AISMessageParser()
            msg.append("", 1, 0)
            fail("AISMessageParser.append() did not throw exception")
        } catch (iae: IllegalArgumentException) {
            assertEquals("Message fragment cannot be null or empty", iae.message)
        } catch (e: Exception) {
            fail("Unexpected exception thrown from AISMessageParser.append()")
        }
    }

    @Test
    fun testAppendNull() {
        try {
            val msg = AISMessageParser()
            msg.append(null, 1, 0)
            fail("AISMessageParser.append() did not throw exception")
        } catch (iae: IllegalArgumentException) {
            assertEquals("Message fragment cannot be null or empty", iae.message)
        } catch (e: Exception) {
            fail("Unexpected exception thrown from AISMessageParser.append()")
        }
    }

    @Test
    fun testAppendNegativeFillBits() {
        try {
            val msg = AISMessageParser()
            msg.append(payload, 1, -1)
            fail("AISMessageParser.append() did not throw exception")
        } catch (iae: IllegalArgumentException) {
            assertEquals("Fill bits cannot be negative", iae.message)
        } catch (e: Exception) {
            fail("Unexpected exception thrown from AISMessageParser.append()")
        }
    }

    @Test
    fun testAppendInvalidIndex() {
        try {
            val msg = AISMessageParser()
            msg.append(payload, 0, 0)
            fail("AISMessageParser.append() did not throw exception")
        } catch (iae: IllegalArgumentException) {
            assertEquals("Invalid fragment index or sequence order", iae.message)
        } catch (e: Exception) {
            fail("Unexpected exception thrown from AISMessageParser.append()")
        }
    }

    @Test
    fun testGetWithoutMessage() {
        try {
            val msg = AISMessageParser()
            msg.mMSI
            fail("Getter did not throw exception")
        } catch (ise: IllegalStateException) {
            assertEquals("Message is empty!", ise.message)
        } catch (e: Exception) {
            fail("Unexpected exception: " + e.message)
        }
    }
}