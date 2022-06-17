package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.AISSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import org.junit.Assert
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
        Assert.assertEquals(TalkerId.AI, empty.getTalkerId())
        Assert.assertEquals("VDM", empty.getSentenceId())
        Assert.assertEquals(6, empty.getFieldCount().toLong())
    }

    /**
     * Test method for
     * [VDMParser.getNumberOfFragments].
     */
    @Test
    fun testGetNumberOfFragments() {
        Assert.assertEquals(1, vdm!!.getNumberOfFragments().toLong())
        Assert.assertEquals(2, frag1!!.getNumberOfFragments().toLong())
        Assert.assertEquals(2, frag2!!.getNumberOfFragments().toLong())
    }

    /**
     * Test method for
     * [VDMParser.getFragmentNumber].
     */
    @Test
    fun testGetFragmentNumber() {
        Assert.assertEquals(1, vdm!!.getFragmentNumber().toLong())
        Assert.assertEquals(1, frag1!!.getFragmentNumber().toLong())
        Assert.assertEquals(2, frag2!!.getFragmentNumber().toLong())
    }

    /**
     * Test method for
     * [VDMParser.getMessageId].
     */
    @Test
    fun testGetMessageId() {
        Assert.assertEquals("1", frag1!!.getMessageId())
        Assert.assertEquals("1", frag2!!.getMessageId())
    }

    /**
     * Test method for
     * [VDMParser.getRadioChannel].
     */
    @Test
    fun testGetRadioChannel() {
        Assert.assertEquals("A", vdm!!.getRadioChannel())
        Assert.assertEquals("A", frag1!!.getRadioChannel())
        Assert.assertEquals("A", frag2!!.getRadioChannel())
    }

    /**
     * Test method for
     * [VDMParser.getPayload].
     */
    @Test
    fun testGetPayload() {
        Assert.assertEquals("403OviQuMGCqWrRO9>E6fE700@GO", vdm!!.getPayload())
        Assert.assertEquals("88888888880", frag2!!.getPayload())
    }

    /**
     * Test method for
     * [VDMParser.getFillBits].
     */
    @Test
    fun testGetFillBits() {
        Assert.assertEquals(0, vdm!!.getFillBits().toLong())
        Assert.assertEquals(0, frag1!!.getFillBits().toLong())
        Assert.assertEquals(2, frag2!!.getFillBits().toLong())
    }

    /**
     * Test method for
     * [VDMParser.isFragmented].
     */
    @Test
    fun testIsFragmented() {
        Assert.assertFalse(vdm!!.isFragmented())
        Assert.assertTrue(frag1!!.isFragmented())
        Assert.assertTrue(frag2!!.isFragmented())
    }

    /**
     * Test method for
     * [VDMParser.isFirstFragment].
     */
    @Test
    fun testIsFirstFragment() {
        Assert.assertTrue(vdm!!.isFirstFragment())
        Assert.assertTrue(frag1!!.isFirstFragment())
        Assert.assertFalse(frag2!!.isFirstFragment())
    }

    /**
     * Test method for
     * [VDMParser.isLastFragment].
     */
    @Test
    fun testIsLastFragment() {
        Assert.assertTrue(vdm!!.isLastFragment())
        Assert.assertFalse(frag1!!.isLastFragment())
        Assert.assertTrue(frag2!!.isLastFragment())
    }

    /**
     * Test method for
     * [VDMParser.isPartOfMessage]
     * .
     */
    @Test
    fun testIsPartOfMessage() {
        Assert.assertFalse(vdm!!.isPartOfMessage(frag1))
        Assert.assertFalse(vdm!!.isPartOfMessage(frag2))
        Assert.assertFalse(frag1!!.isPartOfMessage(vdm))
        Assert.assertFalse(frag2!!.isPartOfMessage(vdm))
        Assert.assertTrue(frag1!!.isPartOfMessage(frag2))
        Assert.assertFalse(frag2!!.isPartOfMessage(frag1))
    }

    @Test
    fun testToStringWithAIS() {
        val vdm: AISSentence = VDMParser(EXAMPLE)
        val empty: AISSentence = VDMParser(TalkerId.AI)
        Assert.assertEquals(EXAMPLE, vdm.toString())
        Assert.assertEquals("!AIVDM,,,,,,*57", empty.toString())
    }

    companion object {
        const val EXAMPLE = "!AIVDM,1,1,,A,403OviQuMGCqWrRO9>E6fE700@GO,0*4D"
        const val PART1 = "!AIVDM,2,1,1,A,55?MbV02;H;s<HtKR20EHE:0@T4@Dn2222222216L961O5Gf0NSQEp6ClRp8,0*1C"
        const val PART2 = "!AIVDM,2,2,1,A,88888888880,2*25"
    }
}