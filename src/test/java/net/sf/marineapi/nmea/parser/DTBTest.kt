/* 
 * DTBTest.java
 * Copyright (C) 2019 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.DTBSentence
import net.sf.marineapi.nmea.sentence.TalkerId
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * DTBTest - test class for Boreal GasFinder Channel B
 *
 * @author Bob Schwarz
 * @see [marine-api fork](https://github.com/LoadBalanced/marine-api)
 */
class DTBTest {
    private var gasFinderMC: DTBSentence? = null
    private var gasFinder2: DTBSentence? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        gasFinderMC = DTBParser(EXAMPLE_MC)
        gasFinder2 = DTBParser(EXAMPLE2)
    }

    @Test
    fun testDTBParserTalkerId() {
        val mwdp = DTBParser(TalkerId.GF)
        Assert.assertEquals(TalkerId.GF, mwdp.getTalkerId())
        Assert.assertEquals("DTB", mwdp.getSentenceId())
    }

    @Test
    fun testGetChannelNumber() {
        Assert.assertEquals(2, gasFinderMC!!.getChannelNumber().toLong())
        Assert.assertEquals(2, gasFinder2!!.getChannelNumber().toLong())
    }

    @Test
    fun testGetGasConcentration() {
        Assert.assertEquals(1.5, gasFinderMC!!.getGasConcentration(), 0.1)
        Assert.assertEquals(7.7, gasFinder2!!.getGasConcentration(), 0.1)
    }

    @Test
    fun testGetConfidenceFactorR2() {
        Assert.assertEquals(99, gasFinderMC!!.getConfidenceFactorR2().toLong())
        Assert.assertEquals(98, gasFinder2!!.getConfidenceFactorR2().toLong())
    }

    @Test
    fun testGetDistance() {
        Assert.assertEquals(600.0, gasFinderMC!!.getDistance(), 0.1)
        Assert.assertEquals(600.0, gasFinder2!!.getDistance(), 0.1)
    }

    @Test
    fun testGetLightLevel() {
        Assert.assertEquals(11067, gasFinderMC!!.getLightLevel().toLong())
        Assert.assertEquals(5527, gasFinder2!!.getLightLevel().toLong())
    }

    @Test
    @Throws(ParseException::class)
    fun testGetDateTime() {
        val DATE_PARSER: DateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        Assert.assertEquals(DATE_PARSER.parse("2002/03/01 00:30:28"), gasFinderMC!!.getDateTime())
        Assert.assertEquals(DATE_PARSER.parse("2011/01/27 13:29:28"), gasFinder2!!.getDateTime())
    }

    @Test
    fun testGetSerialNumber() {
        Assert.assertEquals("HF-1xxx", gasFinderMC!!.getSerialNumber())
        Assert.assertEquals("HFH2O-1xxx", gasFinder2!!.getSerialNumber())
    }

    @Test
    fun testGetStatusCode() {
        Assert.assertEquals(1, gasFinderMC!!.getStatusCode().toLong())
        Assert.assertEquals(1, gasFinder2!!.getStatusCode().toLong())
    }

    companion object {
        // Boreal GasFinderMC has an additional channel number
        const val EXAMPLE_MC = "\$GFDTB,1,1.5,99,600,11067,2002/03/01 00:30:28,HF-1xxx,1*3F"
        const val EXAMPLE2 = "\$GFDTB,7.7,98,600,5527,2011/01/27 13:29:28,HFH2O-1xxx,1*28"
    }
}