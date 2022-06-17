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

import org.junit.Before

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
     * @throws java.lang.Exception
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
     * [net.sf.marineapi.nmea.parser.VDOParser.VDOParser]
     * .
     */
    @Test
    fun testVDOParserTalkerId() {
        val empty: AISSentence = VDOParser(TalkerId.AI)
        assertEquals('!', empty.beginChar)
        assertEquals(TalkerId.AI, empty.talkerId)
        assertEquals("VDO", empty.sentenceId)
        assertEquals(6, empty.fieldCount)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VDOParser.getNumberOfFragments].
     */
    @Test
    fun testGetNumberOfFragments() {
        assertEquals(1, vdo.numberOfFragments)
        assertEquals(2, frag1.numberOfFragments)
        assertEquals(2, frag2.numberOfFragments)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VDOParser.getFragmentNumber].
     */
    @Test
    fun testGetFragmentNumber() {
        assertEquals(1, vdo.fragmentNumber)
        assertEquals(1, frag1.fragmentNumber)
        assertEquals(2, frag2.fragmentNumber)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VDOParser.getMessageId].
     */
    @Test
    fun testGetMessageId() {
        assertEquals("5", frag1.messageId)
        assertEquals("5", frag2.messageId)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VDOParser.getRadioChannel].
     */
    @Test
    fun testGetRadioChannel() {
        assertEquals("B", vdo.radioChannel)
        assertEquals("B", frag1.radioChannel)
        assertEquals("B", frag2.radioChannel)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VDOParser.getPayload].
     */
    @Test
    fun testGetPayload() {
        val pl = "H1c2;qA@PU>0U>060<h5=>0:1Dp"
        val f1 = "E1c2;q@b44ah4ah0h:2ab@70VRpU<Bgpm4:gP50HH`Th`QF5"
        val f2 = "1CQ1A83PCAH0"
        assertEquals(pl, vdo.payload)
        assertEquals(f1, frag1.payload)
        assertEquals(f2, frag2.payload)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VDOParser.getFillBits].
     */
    @Test
    fun testGetFillBits() {
        assertEquals(2, vdo.fillBits)
        assertEquals(0, frag1.fillBits)
        assertEquals(0, frag2.fillBits)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VDOParser.isFragmented].
     */
    @Test
    fun testIsFragmented() {
        assertFalse(vdo.isFragmented)
        assertTrue(frag1.isFragmented)
        assertTrue(frag2.isFragmented)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VDOParser.isFirstFragment].
     */
    @Test
    fun testIsFirstFragment() {
        assertTrue(vdo.isFirstFragment)
        assertTrue(frag1.isFirstFragment)
        assertFalse(frag2.isFirstFragment)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VDOParser.isLastFragment].
     */
    @Test
    fun testIsLastFragment() {
        assertTrue(vdo.isLastFragment)
        assertFalse(frag1.isLastFragment)
        assertTrue(frag2.isLastFragment)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.VDOParser.isPartOfMessage]
     * .
     */
    @Test
    fun testIsPartOfMessage() {
        assertFalse(vdo.isPartOfMessage(frag1))
        assertFalse(vdo.isPartOfMessage(frag2))
        assertFalse(frag1.isPartOfMessage(vdo))
        assertFalse(frag2.isPartOfMessage(vdo))
        assertTrue(frag1.isPartOfMessage(frag2))
        assertFalse(frag2.isPartOfMessage(frag1))
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