/*
 * PositionParserTest.java
 * Copyright (C) 2010 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.util.CompassPoint
import net.sf.marineapi.nmea.util.Position
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for PositionParser class.
 *
 * @author Kimmo Tuukkanen
 */
class PositionParserTest {
    private var instance: PositionParser? = null

    /**
     * @throws Exception
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
        instance = object : PositionParser(GLLTest.EXAMPLE, SentenceId.GLL) {}
    }

    /**
     * Test method for
     * [PositionParser.parseHemisphereLat]
     * .
     */
    @Test
    fun testParseHemisphereLat() {
        Assert.assertEquals(CompassPoint.NORTH, instance!!.parseHemisphereLat(1))
    }

    /**
     * Test method for
     * [PositionParser.parseHemisphereLon]
     * .
     */
    @Test
    fun testParseHemisphereLon() {
        Assert.assertEquals(CompassPoint.EAST, instance!!.parseHemisphereLon(3))
    }

    /**
     * Test method for
     * [PositionParser.parseDegrees].
     */
    @Test
    fun testParseDegreesWithLatitude() {
        // 6011.552 = 60 deg 11.552 min
        val lat = 60 + 11.552 / 60
        Assert.assertEquals(lat, instance!!.parseDegrees(0), 0.000001)
    }

    @Test
    fun testParseDegreesLatitudeWithLeadingZero() {
        // 0611.552 = 6 deg 11.552 min
        val lat = 6 + 11.552 / 60
        val pp: PositionParser = object : PositionParser("\$GPGLL,0611.552,N,0.0,E", SentenceId.GLL) {}
        Assert.assertEquals(lat, pp.parseDegrees(0), 0.000001)
    }

    @Test
    fun testParseDegreesLatitudeWithoutLeadingZero() {
        // 611.552 = 6 deg 11.552 min
        val lat = 6 + 11.552 / 60
        val pp: PositionParser = object : PositionParser("\$GPGLL,611.552,N,0.0,E", SentenceId.GLL) {}
        Assert.assertEquals(lat, pp.parseDegrees(0), 0.000001)
    }

    /**
     * Test method for
     * [PositionParser.parseDegrees].
     */
    @Test
    fun testParseDegreesLongitudeWithLeadingZero() {
        // 02501.941 = 25 deg 1.941 min
        val lon = 25 + 1.941 / 60
        Assert.assertEquals(lon, instance!!.parseDegrees(2), 0.000001)
    }

    @Test
    fun testParseDegreesWithoutLeadingZero() {
        // one leading zero omitted
        // 2501.941 = 25 deg 1.941 min
        val lon = 25 + 01.941 / 60
        val pp: PositionParser = object : PositionParser("\$GPGLL,0.0,N,2501.941,E", SentenceId.GLL) {}
        Assert.assertEquals(lon, pp.parseDegrees(2), 0.000001)
    }

    @Test
    fun testParseDegreesWithoutLeadingZeros() {
        // two leading zeros omitted
        // 501.941 = 5 deg 1.941 min
        val lon = 5 + 1.941 / 60
        val pp: PositionParser = object : PositionParser("\$GPGLL,0.0,N,501.941,E", SentenceId.GLL) {}
        Assert.assertEquals(lon, pp.parseDegrees(2), 0.000001)
    }

    @Test
    fun testParseDegreesWithoutFullDegrees() {
        // full degrees omitted
        // 28.844957 = 0 deg 28.844957 min
        val lon = 0 + 28.844957 / 60
        val pp: PositionParser = object : PositionParser("\$GPGLL,0.0,N,28.844957,E", SentenceId.GLL) {}
        Assert.assertEquals(lon, pp.parseDegrees(2), 0.000001)
    }

    @Test
    fun testParseDegreesWithoutFullDegreesAndLeadingZero() {
        // full degrees and a leading zero of minutes omitted
        // 8.844957 = 0 deg 8.844957 min
        val lon = 0 + 8.844957 / 60
        val pp: PositionParser = object : PositionParser("\$GPGLL,0.0,N,8.844957,E", SentenceId.GLL) {}
        Assert.assertEquals(lon, pp.parseDegrees(2), 0.000001)
    }

    @Test
    fun testParseDegreesWithoutFullDegreesAndMinutes() {
        // full degrees and full minutes omitted
        // .844957 = 0 deg 0.844957 min
        val lon = 0 + 0.844957 / 60
        val pp: PositionParser = object : PositionParser("\$GPGLL,0.0,N,.844957,E", SentenceId.GLL) {}
        Assert.assertEquals(lon, pp.parseDegrees(2), 0.000001)
    }

    @Test
    fun testParseDegreesWithZeroInt() {
        // 0 = 0 deg 0 min
        val pp: PositionParser = object : PositionParser("\$GPGLL,0,N,0,E", SentenceId.GLL) {}
        Assert.assertEquals(0.0, pp.parseDegrees(2), 0.000001)
    }

    @Test
    fun testParseDegreesWithZeroDecimal() {
        // 0.0 = 0 deg 0 min
        val pp: PositionParser = object : PositionParser("\$GPGLL,0.0,N,0.0,E", SentenceId.GLL) {}
        Assert.assertEquals(0.0, pp.parseDegrees(2), 0.000001)
    }

    /**
     * Test method for
     * [PositionParser.setLatHemisphere]
     * .
     */
    @Test
    fun testSetLatHemisphere() {
        instance!!.setLatHemisphere(1, CompassPoint.SOUTH)
        Assert.assertTrue(instance.toString().contains(",S,"))
        Assert.assertEquals(CompassPoint.SOUTH, instance!!.parseHemisphereLat(1))
    }

    /**
     * Test method for
     * [PositionParser.setLatitude]
     * .
     */
    @Test
    fun testSetLatitude() {
        // 2501.941
        val lat = 25 + 01.941 / 60
        instance!!.setLatitude(0, lat)
        Assert.assertTrue(instance.toString().contains(",02501.941"))
        Assert.assertEquals(lat, instance!!.parseDegrees(0), 0.000001)
    }

    /**
     * Test method for
     * [PositionParser.setLongitude]
     * .
     */
    @Test
    fun testSetLongitude() {
        // 02801.941
        val lon = 28 + 01.941 / 60
        instance!!.setLongitude(2, lon)
        Assert.assertTrue(instance.toString().contains(",02801.941"))
        Assert.assertEquals(lon, instance!!.parseDegrees(2), 0.000001)
    }

    /**
     * Test method for
     * [PositionParser.setLonHemisphere]
     * .
     */
    @Test
    fun testSetLonHemisphere() {
        instance!!.setLonHemisphere(3, CompassPoint.WEST)
        Assert.assertTrue(instance.toString().contains(",W,"))
        Assert.assertEquals(CompassPoint.WEST, instance!!.parseHemisphereLon(3))
    }

    /**
     * Test method for
     * [PositionParser.setPositionValues]
     */
    @Test
    fun testSetPositionValuesNE() {
        val lat = 60 + 11.552 / 60
        val lon = 25 + 1.941 / 60
        val p2 = Position(lat, lon)
        instance!!.setPositionValues(p2, 0, 1, 2, 3)
        val s2 = instance.toString()
        val p = instance!!.parsePosition(0, 1, 2, 3)
        Assert.assertTrue(s2.contains(",6011.552,N,"))
        Assert.assertTrue(s2.contains(",02501.941,E,"))
        Assert.assertNotNull(p)
        Assert.assertEquals(lat, p.latitude, 0.0000001)
        Assert.assertEquals(lon, p.longitude, 0.0000001)
    }

    /**
     * Test method for
     * [PositionParser.setPositionValues]
     */
    @Test
    fun testSetPositionValuesSW() {
        val lat = -60 - 11.552 / 60
        val lon = -25 - 1.941 / 60
        val p2 = Position(lat, lon)
        instance!!.setPositionValues(p2, 0, 1, 2, 3)
        val s2 = instance.toString()
        val p = instance!!.parsePosition(0, 1, 2, 3)
        Assert.assertTrue(s2.contains(",6011.552,S,"))
        Assert.assertTrue(s2.contains(",02501.941,W,"))
        Assert.assertNotNull(p)
        Assert.assertEquals(lat, p.latitude, 0.0000001)
        Assert.assertEquals(lon, p.longitude, 0.0000001)
    }
}