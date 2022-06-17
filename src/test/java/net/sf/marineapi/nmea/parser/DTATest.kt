/* 
 * DTATest.java
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

import org.junit.Assert.assertEquals
import java.text.ParseException

/**
 * DTATest - test class for Boreal GasFinder
 *
 * @author Bob Schwarz
 * @see [marine-api fork](https://github.com/LoadBalanced/marine-api)
 */
class DTATest {
    private var gasFinderMC: DTASentence? = null
    private var gasFinder2: DTASentence? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        gasFinderMC = DTAParser(EXAMPLE_MC)
        gasFinder2 = DTAParser(EXAMPLE2)
    }

    @Test
    fun testDTAParserTalkerId() {
        val mwdp = DTAParser(TalkerId.GF)
        assertEquals(TalkerId.GF, mwdp.talkerId)
        assertEquals("DTA", mwdp.sentenceId)
    }

    @Test
    fun testGetChannelNumber() {
        assertEquals(1, gasFinderMC.channelNumber)
        assertEquals(1, gasFinder2.channelNumber)
    }

    @Test
    fun testGetGasConcentration() {
        assertEquals(1.5, gasFinderMC.gasConcentration, 0.1)
        assertEquals(7.7, gasFinder2.gasConcentration, 0.1)
    }

    @Test
    fun testGetConfidenceFactorR2() {
        assertEquals(99, gasFinderMC.confidenceFactorR2)
        assertEquals(98, gasFinder2.confidenceFactorR2)
    }

    @Test
    fun testGetDistance() {
        assertEquals(600, gasFinderMC.distance, 0.1)
        assertEquals(600, gasFinder2.distance, 0.1)
    }

    @Test
    fun testGetLightLevel() {
        assertEquals(11067, gasFinderMC.lightLevel)
        assertEquals(5527, gasFinder2.lightLevel)
    }

    @Test
    @Throws(ParseException::class)
    fun testGetDateTime() {
        val DATE_PARSER: DateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        assertEquals(DATE_PARSER.parse("2002/03/01 00:30:28"), gasFinderMC.dateTime)
        assertEquals(DATE_PARSER.parse("2011/01/27 13:29:28"), gasFinder2.dateTime)
    }

    @Test
    fun testGetSerialNumber() {
        assertEquals("HF-1xxx", gasFinderMC.serialNumber)
        assertEquals("HFH2O-1xxx", gasFinder2.serialNumber)
    }

    @Test
    fun testGetStatusCode() {
        assertEquals(1, gasFinderMC.statusCode)
        assertEquals(1, gasFinder2.statusCode)
    }

    companion object {
        // Boreal GasFinderMC has an additional channel number
        const val EXAMPLE_MC = "\$GFDTA,1,1.5,99,600,11067,2002/03/01 00:30:28,HF-1xxx,1*3C"
        const val EXAMPLE2 = "\$GFDTA,7.7,98,600,5527,2011/01/27 13:29:28,HFH2O-1xxx,1*2B"
    }
}