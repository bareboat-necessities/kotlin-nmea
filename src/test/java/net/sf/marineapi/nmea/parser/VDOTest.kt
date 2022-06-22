/*
 * VDOTest.java
 * Copyright (C) 2016 Kimmo Tuukkanen
 * 
 * This file is part of Java Marine API.
 * <http://ktuukkan.github.io/marine-api/>
 * 
 * Java Marine API is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Java Marine API is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Java Marine API. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.parser.VDOParser
import net.sf.marineapi.nmea.sentence.AISSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * VDOTest
 *
 * @author Kimmo Tuukkanen
 */
class VDOTest {
    private var vdo: AISSentence? = null
    private var frag1: AISSentence? = null
    private var frag2: AISSentence? = null

    /**
     * @throws Exception
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
        vdo = VDOParser(EXAMPLE)
        frag1 = VDOParser(PART1)
        frag2 = VDOParser(PART2)
    }

    /**
     * Test method for
     * [VDOParser.VDOParser]
     * .
     */
    @Test
    fun testVDOParserTalkerId() {
        val empty: AISSentence = VDOParser(TalkerId.AI)
        assertEquals('!'.code.toLong(), empty.getBeginChar().code.toLong())
        assertEquals(TalkerId.AI, empty.getTalkerId())
        assertEquals("VDO", empty.getSentenceId())
        assertEquals(6, empty.getFieldCount().toLong())
    }

    /**
     * Test method for
     * [VDOParser.getNumberOfFragments].
     */
    @Test
    fun testGetNumberOfFragments() {
        assertEquals(1, vdo!!.getNumberOfFragments().toLong())
        assertEquals(2, frag1!!.getNumberOfFragments().toLong())
        assertEquals(2, frag2!!.getNumberOfFragments().toLong())
    }

    /**
     * Test method for
     * [VDOParser.getFragmentNumber].
     */
    @Test
    fun testGetFragmentNumber() {
        assertEquals(1, vdo!!.getFragmentNumber().toLong())
        assertEquals(1, frag1!!.getFragmentNumber().toLong())
        assertEquals(2, frag2!!.getFragmentNumber().toLong())
    }

    /**
     * Test method for
     * [VDOParser.getMessageId].
     */
    @Test
    fun testGetMessageId() {
        assertEquals("5", frag1!!.getMessageId())
        assertEquals("5", frag2!!.getMessageId())
    }

    /**
     * Test method for
     * [VDOParser.getRadioChannel].
     */
    @Test
    fun testGetRadioChannel() {
        assertEquals("B", vdo!!.getRadioChannel())
        assertEquals("B", frag1!!.getRadioChannel())
        assertEquals("B", frag2!!.getRadioChannel())
    }

    /**
     * Test method for
     * [VDOParser.getPayload].
     */
    @Test
    fun testGetPayload() {
        val pl = "H1c2;qA@PU>0U>060<h5=>0:1Dp"
        val f1 = "E1c2;q@b44ah4ah0h:2ab@70VRpU<Bgpm4:gP50HH`Th`QF5"
        val f2 = "1CQ1A83PCAH0"
        assertEquals(pl, vdo!!.getPayload())
        assertEquals(f1, frag1!!.getPayload())
        assertEquals(f2, frag2!!.getPayload())
    }

    /**
     * Test method for
     * [VDOParser.getFillBits].
     */
    @Test
    fun testGetFillBits() {
        assertEquals(2, vdo!!.getFillBits().toLong())
        assertEquals(0, frag1!!.getFillBits().toLong())
        assertEquals(0, frag2!!.getFillBits().toLong())
    }

    /**
     * Test method for
     * [VDOParser.isFragmented].
     */
    @Test
    fun testIsFragmented() {
        Assert.assertFalse(vdo!!.isFragmented())
        Assert.assertTrue(frag1!!.isFragmented())
        Assert.assertTrue(frag2!!.isFragmented())
    }

    /**
     * Test method for
     * [VDOParser.isFirstFragment].
     */
    @Test
    fun testIsFirstFragment() {
        Assert.assertTrue(vdo!!.isFirstFragment())
        Assert.assertTrue(frag1!!.isFirstFragment())
        Assert.assertFalse(frag2!!.isFirstFragment())
    }

    /**
     * Test method for
     * [VDOParser.isLastFragment].
     */
    @Test
    fun testIsLastFragment() {
        Assert.assertTrue(vdo!!.isLastFragment())
        Assert.assertFalse(frag1!!.isLastFragment())
        Assert.assertTrue(frag2!!.isLastFragment())
    }

    /**
     * Test method for
     * [VDOParser.isPartOfMessage]
     * .
     */
    @Test
    fun testIsPartOfMessage() {
        Assert.assertFalse(vdo!!.isPartOfMessage(frag1))
        Assert.assertFalse(vdo!!.isPartOfMessage(frag2))
        Assert.assertFalse(frag1!!.isPartOfMessage(vdo))
        Assert.assertFalse(frag2!!.isPartOfMessage(vdo))
        Assert.assertTrue(frag1!!.isPartOfMessage(frag2))
        Assert.assertFalse(frag2!!.isPartOfMessage(frag1))
    }

    @Test
    fun testToStringWithAIS() {
        val example: AISSentence = VDOParser(EXAMPLE)
        val empty: AISSentence = VDOParser(TalkerId.AI)
        assertEquals(EXAMPLE, example.toString())
        assertEquals("!AIVDO,,,,,,*55", empty.toString())
    }

    companion object {
        const val EXAMPLE = "!AIVDO,1,1,,B,H1c2;qA@PU>0U>060<h5=>0:1Dp,2*7D"
        const val PART1 = "!AIVDO,2,1,5,B,E1c2;q@b44ah4ah0h:2ab@70VRpU<Bgpm4:gP50HH`Th`QF5,0*7B"
        const val PART2 = "!AIVDO,2,2,5,B,1CQ1A83PCAH0,0*60"
    }
}