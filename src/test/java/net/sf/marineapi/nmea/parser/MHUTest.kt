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

import net.sf.marineapi.nmea.sentence.MHUSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

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
        assertEquals(4f, mhu!!.getFieldCount().toFloat(), 1f)
    }

    @Test
    fun testEmptySentenceConstructor() {
        assertEquals(TalkerId.II, empty!!.getTalkerId())
        assertEquals(SentenceId.MHU.toString(), empty!!.getSentenceId())
        assertEquals(4, empty!!.getFieldCount().toLong())
        assertEquals('C'.code.toLong(), empty!!.getDewPointUnit().code.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testGetRelativeHumidity() {
        assertEquals(66.0, mhu!!.getRelativeHumidity(), 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun testGetAbsoluteHumidity() {
        assertEquals(5.0, mhu!!.getAbsoluteHumidity(), 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun testGetDewPoint() {
        assertEquals(3.0, mhu!!.getDewPoint(), 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun testGetDewPointUnit() {
        assertEquals('C'.code.toLong(), mhu!!.getDewPointUnit().code.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testSetRelativeHumidity() {
        mhu!!.setRelativeHumidity(55.55555)
        assertEquals(55.6, mhu!!.getRelativeHumidity(), 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun testSetAbsoluteHumidity() {
        mhu!!.setAbsoluteHumidity(6.1234)
        assertEquals(6.1, mhu!!.getAbsoluteHumidity(), 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun testSetDewPoint() {
        mhu!!.setDewPoint(1.2356)
        assertEquals(1.2, mhu!!.getDewPoint(), 0.1)
    }

    @Test
    @Throws(Exception::class)
    fun testSetDewPointUnit() {
        mhu!!.setDewPointUnit('F')
        assertEquals('F'.code.toLong(), mhu!!.getDewPointUnit().code.toLong())
    }

    companion object {
        const val EXAMPLE = "\$IIMHU,66.0,5.0,3.0,C"
    }
}