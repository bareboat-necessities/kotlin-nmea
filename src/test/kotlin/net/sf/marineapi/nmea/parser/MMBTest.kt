package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.MMBSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * MMBTest
 *
 * @author Kimmo Tuukkanen
 */
class MMBTest {
    var mmb: MMBSentence? = null
    var empty: MMBSentence? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        mmb = MMBParser(EXAMPLE)
        empty = MMBParser(TalkerId.WI)
    }

    @Test
    fun testConstructors() {
        assertEquals(4, mmb!!.getFieldCount().toLong())
        assertEquals(4, empty!!.getFieldCount().toLong())
        assertEquals(TalkerId.II, mmb!!.getTalkerId())
        assertEquals(TalkerId.WI, empty!!.getTalkerId())
        assertEquals(SentenceId.MMB.name, empty!!.getSentenceId())
    }

    @Test
    @Throws(Exception::class)
    fun testGetInchesOfMercury() {
        assertEquals(29.9870, mmb!!.getInchesOfMercury(), 0.0001)
    }

    @Test
    @Throws(Exception::class)
    fun testGetBars() {
        assertEquals(1.0154, mmb!!.getBars(), 0.0001)
    }

    @Test
    @Throws(Exception::class)
    fun testSetInchesOfMercury() {
        mmb!!.setInchesOfMercury(29.9999)
        assertEquals(29.9999, mmb!!.getInchesOfMercury(), 0.0001)
    }

    @Test
    @Throws(Exception::class)
    fun testSetBars() {
        mmb!!.setBars(1.1234)
        assertEquals(1.1234, mmb!!.getBars(), 0.0001)
    }

    companion object {
        const val EXAMPLE = "\$IIMMB,29.9870,I,1.0154,B*75"
    }
}