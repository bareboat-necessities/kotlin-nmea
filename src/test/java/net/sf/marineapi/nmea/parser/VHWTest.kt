/* 
 * VHWTest.java
 * Copyright (C) 2011 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.parser.VHWParser
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * @author Kimmo Tuukkanen
 */
class VHWTest {
    private var vhw: VHWParser? = null

    /**
     * @throws Exception
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
        vhw = VHWParser(EXAMPLE)
    }

    /**
     * Test method for
     * [VHWParser.VHWParser]
     * .
     */
    @Test
    fun testConstructorString() {
        Assert.assertTrue(vhw!!.getTalkerId() === TalkerId.VW)
        Assert.assertTrue(SentenceId.valueOf(vhw!!.getSentenceId()) === SentenceId.VHW)
    }

    /**
     * Test method for
     * [VHWParser.VHWParser]
     * .
     */
    @Test
    fun testConstructorTalkerId() {
        val empty = VHWParser(TalkerId.II)
        Assert.assertEquals(8, empty.getFieldCount().toLong())
        Assert.assertTrue('T' == empty.getCharValue(1))
        Assert.assertTrue('M' == empty.getCharValue(3))
        Assert.assertTrue('N' == empty.getCharValue(5))
        Assert.assertTrue('K' == empty.getCharValue(7))
        Assert.assertEquals("VHW", empty.getSentenceId())
        Assert.assertTrue(empty.getTalkerId() === TalkerId.II)
    }

    /**
     * Test method for
     * [VHWParser.getHeading].
     */
    @Test
    fun testGetHeading() {
        Assert.assertEquals(0.0, vhw!!.getHeading(), 0.1)
    }

    /**
     * Test method for
     * [VHWParser.getMagneticHeading].
     */
    @Test
    fun testGetMagneticHeading() {
        Assert.assertEquals(1.5, vhw!!.getMagneticHeading(), 0.1)
    }

    /**
     * Test method for
     * [VHWParser.getSpeedKmh].
     */
    @Test
    fun testGetSpeedKilometres() {
        Assert.assertEquals(1.85, vhw!!.getSpeedKmh(), 0.01)
    }

    /**
     * Test method for
     * [VHWParser.getSpeedKnots].
     */
    @Test
    fun testGetSpeedKnots() {
        Assert.assertEquals(1.0, vhw!!.getSpeedKnots(), 0.1)
    }

    /**
     * Test method for [VHWParser.isTrue].
     */
    @Test
    fun testIsTrue() {
        // should always return true
        Assert.assertTrue(vhw!!.isTrue())
    }

    /**
     * Test method for
     * [VHWParser.setHeading].
     */
    @Test
    fun testSetHeading() {
        vhw!!.setHeading(90.456)
        Assert.assertEquals(90.5, vhw!!.getHeading(), 0.1)
    }

    /**
     * Test method for
     * [VHWParser.setMagneticHeading].
     */
    @Test
    fun testSetMagneticHeading() {
        vhw!!.setMagneticHeading(123.4567)
        Assert.assertEquals(123.5, vhw!!.getMagneticHeading(), 0.1)
    }

    /**
     * Test method for
     * [VHWParser.setSpeedKilometres].
     */
    @Test
    fun testSetSpeedKilometres() {
        vhw!!.setSpeedKmh(5.5555)
        Assert.assertEquals(5.6, vhw!!.getSpeedKmh(), 0.1)
    }

    /**
     * Test method for
     * [VHWParser.setSpeedKnots].
     */
    @Test
    fun testSetSpeedKnots() {
        vhw!!.setSpeedKnots(12.155)
        Assert.assertEquals(12.2, vhw!!.getSpeedKnots(), 0.1)
    }

    companion object {
        const val EXAMPLE = "\$VWVHW,000.0,T,001.5,M,1.0,N,1.85,K"
    }
}