/* 
 * TLBTest.java
 * Copyright (C) 2020 Joshua Sweaney
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

import net.sf.marineapi.nmea.sentence.TLBSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * TLBTest
 *
 * @author Joshua Sweaney
 */
class TLBTest {
    private var empty: TLBSentence? = null
    private var threeTargets: TLBSentence? = null
    @Before
    fun setUp() {
        empty = TLBParser(TalkerId.RA)
        threeTargets = TLBParser(EXAMPLE)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testTLBParser() {
        // Target 5 has odd number of fields (missing lable for target 5). This should throw an exception on construct.
        TLBParser("\$RATLB,3,SHIPTHREE,5*2F")
    }

    /**
     * Test method for
     * [TLBParser.addTargetLabel].
     */
    @Test
    fun testAddTargetLabel() {
        empty!!.addTargetLabel(3, "SHIPTHREE")
        assertTrue(empty.toString().contains("3,SHIPTHREE*"))
        empty!!.addTargetLabel(5, "SHIPFIVE")
        assertTrue(empty.toString().contains("3,SHIPTHREE,5,SHIPFIVE*")) // SHIPFIVE is now at the end
        empty!!.addTargetLabel(99, "SHIP99")
        assertTrue(empty.toString().contains("3,SHIPTHREE,5,SHIPFIVE,99,SHIP99*"))

        // Adding to existing sentence
        threeTargets!!.addTargetLabel(4, "SHIPFOUR")
        assertTrue(threeTargets.toString().contains("1,SHIPONE,2,SHIPTWO,3,SHIPTHREE,4,SHIPFOUR*"))
    }

    /**
     * Test method for
     * [TLBParser.setTargetPairs].
     */
    @Test(expected = IllegalArgumentException::class)
    fun testSetTargetPairs() {
        val ids = intArrayOf(1, 2, 3)
        val labels = arrayOf<String?>("SHIPONE", "SHIPTWO", "SHIPTHREE")
        empty!!.setTargetPairs(ids, labels)
        assertTrue(empty.toString().contains("1,SHIPONE,2,SHIPTWO,3,SHIPTHREE*"))
        val ids_two = intArrayOf(5, 6)
        val labels_two = arrayOf<String?>("SHIPFIVE", "SHIPSIX", "SHIPSEVEN") // Intentionally larger than ids_two[]
        empty!!.setTargetPairs(ids_two, labels_two) // Will throw exception
    }

    /**
     * Test method for
     * [TLBParser.getTargetIds].
     */
    @Test
    fun testGetTargetIds() {
        val ids = intArrayOf(1, 2, 3)
        assertArrayEquals(ids, threeTargets!!.getTargetIds())
        val ids_empty = intArrayOf()
        assertArrayEquals(ids_empty, empty!!.getTargetIds())
    }

    /**
     * Test method for
     * [TLBParser.getTargetLabels].
     */
    @Test
    fun testGetTargetLabels() {
        val labels = arrayOf("SHIPONE", "SHIPTWO", "SHIPTHREE")
        assertArrayEquals(labels, threeTargets!!.getTargetLabels())
        val labels_empty = arrayOf<String>()
        assertArrayEquals(labels_empty, empty!!.getTargetLabels())
    }

    companion object {
        const val EXAMPLE = "\$RATLB,1,SHIPONE,2,SHIPTWO,3,SHIPTHREE*3D"
    }
}