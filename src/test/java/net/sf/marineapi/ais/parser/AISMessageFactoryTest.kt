package net.sf.marineapi.ais.parser

import net.sf.marineapi.ais.message.AISMessage01
import net.sf.marineapi.ais.message.AISMessage05
import net.sf.marineapi.nmea.parser.SentenceFactory
import net.sf.marineapi.nmea.sentence.AISSentence

import org.junit.Assert.*
import org.junit.Test

/**
 * Test AISMessageFactory
 */
class AISMessageFactoryTest {
    private val sf: SentenceFactory = SentenceFactory.instance
    private val amf: AISMessageFactory? = AISMessageFactory.instance
    private val s1 = "!AIVDM,1,1,,A,13aEOK?P00PD2wVMdLDRhgvL289?,0*26"
    private val s2_1 = "!AIVDM,2,1,9,B,53nFBv01SJ<thHp6220H4heHTf2222222222221?50:454o<`9QSlUDp,0*09"
    private val s2_2 = "!AIVDM,2,2,9,B,888888888888880,2*2E"
    private val single = sf.createParser(s1) as AISSentence?
    private val split1 = sf.createParser(s2_1) as AISSentence?
    private val split2 = sf.createParser(s2_2) as AISSentence?

    @Test
    fun testCreate() {
        val msg = amf!!.create(single!!)
        assertTrue(msg is AISMessage01)
        assertEquals(1, msg.messageType.toLong())
    }

    @Test
    fun testCreateWithTwo() {
        val msg = amf!!.create(split1!!, split2!!)
        assertTrue(msg is AISMessage05)
        assertEquals(5, msg.messageType.toLong())
    }

    @Test
    fun testCreateWithIncorrectOrder() {
        try {
            amf!!.create(split2!!, split1!!)
            fail("AISMessageFactory didn't throw on incorrect order")
        } catch (iae: IllegalArgumentException) {
            assertEquals("Incorrect order of AIS sentences", iae.message)
        } catch (e: Exception) {
            fail("Unexpected exception thrown from AISMessageFactory")
        }
    }
}