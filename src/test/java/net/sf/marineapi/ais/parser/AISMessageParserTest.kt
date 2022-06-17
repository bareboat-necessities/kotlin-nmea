package net.sf.marineapi.ais.parser

import net.sf.marineapi.ais.util.Sixbit
import org.junit.Assert
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
        Assert.assertEquals(1, parser.messageType.toLong())
    }

    @Test
    fun testGetMMSI() {
        Assert.assertEquals(244670316, parser.mMSI.toLong())
    }

    @Test
    fun testGetRepeatIndicator() {
        Assert.assertEquals(0, parser.repeatIndicator.toLong())
    }

    @Test
    fun testGetSixbit() {
        val decoder = parser.sixbit
        Assert.assertEquals(sixbit.payload, decoder.payload)
    }

    @Test
    fun testAppend() {
        val msg = AISMessageParser()
        msg.append(payload, 1, 0)
        Assert.assertEquals(1, msg.messageType.toLong())
        Assert.assertEquals(0, msg.repeatIndicator.toLong())
        Assert.assertEquals(244670316, msg.mMSI.toLong())
        Assert.assertEquals(payload, msg.sixbit.payload)
    }

    @Test
    fun testAppendIncorrectOrder() {
        try {
            val msg = AISMessageParser()
            msg.append(payload, 2, 0)
        } catch (iae: IllegalArgumentException) {
            Assert.assertEquals("Invalid fragment index or sequence order", iae.message)
        } catch (e: Exception) {
            Assert.fail("Unexpected exception was thrown; " + e.message)
        }
    }

    @Test
    fun testAppendInvalidTail() {
        try {
            val msg = AISMessageParser()
            msg.append(payload, 1, 0)
            msg.append(payload, 1, 0)
            Assert.fail("AISMessageParser.append() did not throw exception")
        } catch (iae: IllegalArgumentException) {
            Assert.assertEquals("Invalid fragment index or sequence order", iae.message)
        } catch (e: Exception) {
            Assert.fail("Unexpected exception thrown from AISMessageParser.append()")
        }
    }

    @Test
    fun testAppendEmptyString() {
        try {
            val msg = AISMessageParser()
            msg.append("", 1, 0)
            Assert.fail("AISMessageParser.append() did not throw exception")
        } catch (iae: IllegalArgumentException) {
            Assert.assertEquals("Message fragment cannot be null or empty", iae.message)
        } catch (e: Exception) {
            Assert.fail("Unexpected exception thrown from AISMessageParser.append()")
        }
    }

    @Test
    fun testAppendNull() {
        try {
            val msg = AISMessageParser()
            msg.append(null, 1, 0)
            Assert.fail("AISMessageParser.append() did not throw exception")
        } catch (iae: IllegalArgumentException) {
            Assert.assertEquals("Message fragment cannot be null or empty", iae.message)
        } catch (e: Exception) {
            Assert.fail("Unexpected exception thrown from AISMessageParser.append()")
        }
    }

    @Test
    fun testAppendNegativeFillBits() {
        try {
            val msg = AISMessageParser()
            msg.append(payload, 1, -1)
            Assert.fail("AISMessageParser.append() did not throw exception")
        } catch (iae: IllegalArgumentException) {
            Assert.assertEquals("Fill bits cannot be negative", iae.message)
        } catch (e: Exception) {
            Assert.fail("Unexpected exception thrown from AISMessageParser.append()")
        }
    }

    @Test
    fun testAppendInvalidIndex() {
        try {
            val msg = AISMessageParser()
            msg.append(payload, 0, 0)
            Assert.fail("AISMessageParser.append() did not throw exception")
        } catch (iae: IllegalArgumentException) {
            Assert.assertEquals("Invalid fragment index or sequence order", iae.message)
        } catch (e: Exception) {
            Assert.fail("Unexpected exception thrown from AISMessageParser.append()")
        }
    }

    @Test
    fun testGetWithoutMessage() {
        try {
            val msg = AISMessageParser()
            msg.mMSI
            Assert.fail("Getter did not throw exception")
        } catch (ise: IllegalStateException) {
            Assert.assertEquals("Message is empty!", ise.message)
        } catch (e: Exception) {
            Assert.fail("Unexpected exception: " + e.message)
        }
    }
}