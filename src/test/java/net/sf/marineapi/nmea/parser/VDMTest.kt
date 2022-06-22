package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.AISSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * @author Kimmo Tuukkanen
 */
class VDMTest {
    private var vdm: AISSentence? = null
    private var frag1: AISSentence? = null
    private var frag2: AISSentence? = null

    /**
     * @throws Exception
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
        vdm = VDMParser(EXAMPLE)
        frag1 = VDMParser(PART1)
        frag2 = VDMParser(PART2)
    }

    /**
     * Test method for
     * [VDMParser.VDMParser]
     * .
     */
    @Test
    fun testVDMParserTalkerId() {
        val empty: AISSentence = VDMParser(TalkerId.AI)
        assertEquals(TalkerId.AI, empty.getTalkerId())
        assertEquals("VDM", empty.getSentenceId())
        assertEquals(6, empty.getFieldCount().toLong())
    }

    /**
     * Test method for
     * [VDMParser.getNumberOfFragments].
     */
    @Test
    fun testGetNumberOfFragments() {
        assertEquals(1, vdm!!.getNumberOfFragments().toLong())
        assertEquals(2, frag1!!.getNumberOfFragments().toLong())
        assertEquals(2, frag2!!.getNumberOfFragments().toLong())
    }

    /**
     * Test method for
     * [VDMParser.getFragmentNumber].
     */
    @Test
    fun testGetFragmentNumber() {
        assertEquals(1, vdm!!.getFragmentNumber().toLong())
        assertEquals(1, frag1!!.getFragmentNumber().toLong())
        assertEquals(2, frag2!!.getFragmentNumber().toLong())
    }

    /**
     * Test method for
     * [VDMParser.getMessageId].
     */
    @Test
    fun testGetMessageId() {
        assertEquals("1", frag1!!.getMessageId())
        assertEquals("1", frag2!!.getMessageId())
    }

    /**
     * Test method for
     * [VDMParser.getRadioChannel].
     */
    @Test
    fun testGetRadioChannel() {
        assertEquals("A", vdm!!.getRadioChannel())
        assertEquals("A", frag1!!.getRadioChannel())
        assertEquals("A", frag2!!.getRadioChannel())
    }

    /**
     * Test method for
     * [VDMParser.getPayload].
     */
    @Test
    fun testGetPayload() {
        assertEquals("403OviQuMGCqWrRO9>E6fE700@GO", vdm!!.getPayload())
        assertEquals("88888888880", frag2!!.getPayload())
    }

    /**
     * Test method for
     * [VDMParser.getFillBits].
     */
    @Test
    fun testGetFillBits() {
        assertEquals(0, vdm!!.getFillBits().toLong())
        assertEquals(0, frag1!!.getFillBits().toLong())
        assertEquals(2, frag2!!.getFillBits().toLong())
    }

    /**
     * Test method for
     * [VDMParser.isFragmented].
     */
    @Test
    fun testIsFragmented() {
        assertFalse(vdm!!.isFragmented())
        assertTrue(frag1!!.isFragmented())
        assertTrue(frag2!!.isFragmented())
    }

    /**
     * Test method for
     * [VDMParser.isFirstFragment].
     */
    @Test
    fun testIsFirstFragment() {
        assertTrue(vdm!!.isFirstFragment())
        assertTrue(frag1!!.isFirstFragment())
        assertFalse(frag2!!.isFirstFragment())
    }

    /**
     * Test method for
     * [VDMParser.isLastFragment].
     */
    @Test
    fun testIsLastFragment() {
        assertTrue(vdm!!.isLastFragment())
        assertFalse(frag1!!.isLastFragment())
        assertTrue(frag2!!.isLastFragment())
    }

    /**
     * Test method for
     * [VDMParser.isPartOfMessage]
     * .
     */
    @Test
    fun testIsPartOfMessage() {
        assertFalse(vdm!!.isPartOfMessage(frag1))
        assertFalse(vdm!!.isPartOfMessage(frag2))
        assertFalse(frag1!!.isPartOfMessage(vdm))
        assertFalse(frag2!!.isPartOfMessage(vdm))
        assertTrue(frag1!!.isPartOfMessage(frag2))
        assertFalse(frag2!!.isPartOfMessage(frag1))
    }

    @Test
    fun testToStringWithAIS() {
        val vdm: AISSentence = VDMParser(EXAMPLE)
        val empty: AISSentence = VDMParser(TalkerId.AI)
        assertEquals(EXAMPLE, vdm.toString())
        assertEquals("!AIVDM,,,,,,*57", empty.toString())
    }

    companion object {
        const val EXAMPLE = "!AIVDM,1,1,,A,403OviQuMGCqWrRO9>E6fE700@GO,0*4D"
        const val PART1 = "!AIVDM,2,1,1,A,55?MbV02;H;s<HtKR20EHE:0@T4@Dn2222222216L961O5Gf0NSQEp6ClRp8,0*1C"
        const val PART2 = "!AIVDM,2,2,1,A,88888888880,2*25"
    }
}