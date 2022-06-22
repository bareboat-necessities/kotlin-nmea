/*
 * SentenceValidatorTest.java
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
package net.sf.marineapi.nmea.sentence

import net.sf.marineapi.nmea.parser.*
import net.sf.marineapi.nmea.sentence.Checksum.add
import net.sf.marineapi.nmea.sentence.SentenceValidator.isSentence
import net.sf.marineapi.nmea.sentence.SentenceValidator.isValid
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * @author Kimmo Tuukkanen
 */
class SentenceValidatorTest {
    @Test
    fun testIsValid() {

        // "normal"
        val a = "\$ABCDE,1,2,3,4,5,6,7,8,9"
        assertTrue(isValid(a))
        assertTrue(isValid(add(a)))

        // empty sentence, single field
        val b = "\$ABCDE,"
        assertTrue(isValid(b))
        assertTrue(isValid(add(b)))

        // empty sentence, multiple fields
        val c = "\$ABCDE,,,,,,"
        assertTrue(isValid(c))
        assertTrue(isValid(add(c)))
        val d = "\$ABCDE,1,TWO,three,FOUR?,5,6.0,-7.0,Eigth-8,N1N3,#T3n"
        assertTrue(isValid(d))
        assertTrue(isValid(add(d)))

        // '!' begin char
        val e = "!ABCDE,1,2,3,4,5,6,7,8,9"
        assertTrue(isValid(e))
        assertTrue(isValid(add(e)))
    }

    @Test
    fun testIsValidWithInvalidInput() {
        // invalid checksum, otherwise valid
        assertFalse(isValid("\$ABCDE,1,2,3,4,5,6,7,8,9*00"))
        // something weird
        assertFalse(isValid(""))
        assertFalse(isValid("$"))
        assertFalse(isValid("*"))
        assertFalse(isValid("$,*"))
        assertFalse(isValid("\$GPGSV*"))
        assertFalse(isValid("foobar"))
        assertFalse(isValid("\$gpgga,1,2,3,4,5,6,7,8,9"))
        assertFalse(isValid("GPGGA,1,2,3,4,5,6,7,8,9"))
        assertFalse(isValid("\$GpGGA,1,2,3,4,5,6,7,8,9"))
        assertFalse(isValid("\$GPGGa,1,2,3,4,5,6,7,8,9"))
        assertFalse(isValid("\$GPGG#,1,2,3,4,5,6,7,8,9"))
        assertFalse(isValid("\$AB,1,2,3,4,5,6,7,8,9"))
        assertFalse(isValid("\$ABCDEFGHIJK,1,2,3,4,5,6,7,8,9"))
        assertFalse(isValid("\$GPGGA,1,2,3,4,5,6,7,8,9*00"))
    }

    @Test
    fun testIsValidWithValidInput() {
        assertTrue(isValid(BODTest.EXAMPLE))
        assertTrue(isValid(GGATest.EXAMPLE))
        assertTrue(isValid(GLLTest.EXAMPLE))
        assertTrue(isValid(GSATest.EXAMPLE))
        assertTrue(isValid(GSVTest.EXAMPLE))
        assertTrue(isValid(RMBTest.EXAMPLE))
        assertTrue(isValid(RMCTest.EXAMPLE))
        assertTrue(isValid(RTETest.EXAMPLE))
        assertTrue(isValid(VTGTest.EXAMPLE))
        assertTrue(isValid(WPLTest.EXAMPLE))
        assertTrue(isValid(ZDATest.EXAMPLE))
    }

    @Test
    fun testIsValidWithLongProprietaryId() {
        val str = "\$PRWIILOG,GGA,A,T,1,0"
        assertTrue(isSentence(str))
        assertTrue(isValid(str))
    }

    @Test
    fun testIsValidWithShortProprietaryId() {
        val str = "\$PUBX,03,GT{,ID,s,AZM,EL,SN,LK},"
        assertTrue(isSentence(str))
        assertTrue(isValid(str))
    }

    @Test
    fun testIsSentenceWithChecksum() {
        var nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*20"
        assertTrue(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*20xy"
        assertFalse(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*201"
        assertFalse(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*2"
        assertFalse(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*"
        assertFalse(isSentence(nmea))
    }

    @Test
    fun testIsSentenceWithoutChecksum() {
        val nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,"
        assertTrue(isSentence(nmea))
    }

    @Test
    fun testIsSentenceWithChecksumAndNewline() {
        var nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*20\r\n"
        assertTrue(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*20\n\r"
        assertTrue(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*20\r"
        assertTrue(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*20\n"
        assertTrue(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*20\r\n\r\n"
        assertFalse(isSentence(nmea))
    }

    @Test
    fun testIsSentenceNoChecksumWithNewline() {
        var nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,\r\n"
        assertTrue(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,\n\r"
        assertTrue(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,\r"
        assertTrue(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,\n"
        assertTrue(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,\r\n\r\n"
        assertFalse(isSentence(nmea))
    }
}