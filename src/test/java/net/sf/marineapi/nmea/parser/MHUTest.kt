/*
 * MHUTest.java
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
 * MHU parser tests.
 *
 * @author Kimmo Tuukkanen
 */
class MHUTest {
    private var mhu: MHUSentence? = null
    private var empty: MHUSentence? = null
    @Before
    fun setUp() {
        mhu = MHUParser(EXAMPLE)
        empty = MHUParser(TalkerId.II)
        assertEquals(4, mhu.fieldCount, 1)
    }

    @Test
    fun testEmptySentenceConstructor() {
        assertEquals(TalkerId.II, empty.talkerId)
        assertEquals(SentenceId.MHU.toString(), empty.sentenceId)
        assertEquals(4, empty.fieldCount)
        assertEquals('C', empty.dewPointUnit)
    }

    @Test
    @Throws(Exception::class)
    fun testGetRelativeHumidity() {
        assertEquals(66.0, mhu.relativeHumidity, 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun testGetAbsoluteHumidity() {
        assertEquals(5.0, mhu.absoluteHumidity, 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun testGetDewPoint() {
        assertEquals(3.0, mhu.dewPoint, 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun testGetDewPointUnit() {
        assertEquals('C', mhu.dewPointUnit)
    }

    @Test
    @Throws(Exception::class)
    fun testSetRelativeHumidity() {
        mhu.relativeHumidity = 55.55555
        assertEquals(55.6, mhu.relativeHumidity, 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun testSetAbsoluteHumidity() {
        mhu.absoluteHumidity = 6.1234
        assertEquals(6.1, mhu.absoluteHumidity, 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun testSetDewPoint() {
        mhu.dewPoint = 1.2356
        assertEquals(1.2, mhu.dewPoint, 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun testSetDewPointUnit() {
        mhu.dewPointUnit = 'F'
        assertEquals('F', mhu.dewPointUnit)
    }

    companion object {
        const val EXAMPLE = "\$IIMHU,66.0,5.0,3.0,C"
    }
}