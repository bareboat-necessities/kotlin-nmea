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

import org.junit.Assert.assertFalse

/**
 * @author Kimmo Tuukkanen
 */
class SentenceValidatorTest {
    @Test
    fun testIsValid() {

        // "normal"
        val a = "\$ABCDE,1,2,3,4,5,6,7,8,9"
        assertTrue(SentenceValidator.isValid(a))
        assertTrue(SentenceValidator.isValid(Checksum.add(a)))

        // empty sentence, single field
        val b = "\$ABCDE,"
        assertTrue(SentenceValidator.isValid(b))
        assertTrue(SentenceValidator.isValid(Checksum.add(b)))

        // empty sentence, multiple fields
        val c = "\$ABCDE,,,,,,"
        assertTrue(SentenceValidator.isValid(c))
        assertTrue(SentenceValidator.isValid(Checksum.add(c)))
        val d = "\$ABCDE,1,TWO,three,FOUR?,5,6.0,-7.0,Eigth-8,N1N3,#T3n"
        assertTrue(SentenceValidator.isValid(d))
        assertTrue(SentenceValidator.isValid(Checksum.add(d)))

        // '!' begin char
        val e = "!ABCDE,1,2,3,4,5,6,7,8,9"
        assertTrue(SentenceValidator.isValid(e))
        assertTrue(SentenceValidator.isValid(Checksum.add(e)))
    }

    @Test
    fun testIsValidWithInvalidInput() {
        // invalid checksum, otherwise valid
        assertFalse(SentenceValidator.isValid("\$ABCDE,1,2,3,4,5,6,7,8,9*00"))
        // something weird
        assertFalse(SentenceValidator.isValid(null))
        assertFalse(SentenceValidator.isValid(""))
        assertFalse(SentenceValidator.isValid("$"))
        assertFalse(SentenceValidator.isValid("*"))
        assertFalse(SentenceValidator.isValid("$,*"))
        assertFalse(SentenceValidator.isValid("\$GPGSV*"))
        assertFalse(SentenceValidator.isValid("foobar"))
        assertFalse(SentenceValidator.isValid("\$gpgga,1,2,3,4,5,6,7,8,9"))
        assertFalse(SentenceValidator.isValid("GPGGA,1,2,3,4,5,6,7,8,9"))
        assertFalse(SentenceValidator.isValid("\$GpGGA,1,2,3,4,5,6,7,8,9"))
        assertFalse(SentenceValidator.isValid("\$GPGGa,1,2,3,4,5,6,7,8,9"))
        assertFalse(SentenceValidator.isValid("\$GPGG#,1,2,3,4,5,6,7,8,9"))
        assertFalse(SentenceValidator.isValid("\$AB,1,2,3,4,5,6,7,8,9"))
        assertFalse(SentenceValidator.isValid("\$ABCDEFGHIJK,1,2,3,4,5,6,7,8,9"))
        assertFalse(SentenceValidator.isValid("\$GPGGA,1,2,3,4,5,6,7,8,9*00"))
    }

    @Test
    fun testIsValidWithValidInput() {
        assertTrue(SentenceValidator.isValid(BODTest.Companion.EXAMPLE))
        assertTrue(SentenceValidator.isValid(GGATest.Companion.EXAMPLE))
        assertTrue(SentenceValidator.isValid(GLLTest.Companion.EXAMPLE))
        assertTrue(SentenceValidator.isValid(GSATest.Companion.EXAMPLE))
        assertTrue(SentenceValidator.isValid(GSVTest.Companion.EXAMPLE))
        assertTrue(SentenceValidator.isValid(RMBTest.Companion.EXAMPLE))
        assertTrue(SentenceValidator.isValid(RMCTest.Companion.EXAMPLE))
        assertTrue(SentenceValidator.isValid(RTETest.Companion.EXAMPLE))
        assertTrue(SentenceValidator.isValid(VTGTest.Companion.EXAMPLE))
        assertTrue(SentenceValidator.isValid(WPLTest.Companion.EXAMPLE))
        assertTrue(SentenceValidator.isValid(ZDATest.Companion.EXAMPLE))
    }

    @Test
    fun testIsValidWithLongProprietaryId() {
        val str = "\$PRWIILOG,GGA,A,T,1,0"
        assertTrue(SentenceValidator.isSentence(str))
        assertTrue(SentenceValidator.isValid(str))
    }

    @Test
    fun testIsValidWithShortProprietaryId() {
        val str = "\$PUBX,03,GT{,ID,s,AZM,EL,SN,LK},"
        assertTrue(SentenceValidator.isSentence(str))
        assertTrue(SentenceValidator.isValid(str))
    }

    @Test
    fun testIsSentenceWithChecksum() {
        var nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*20"
        assertTrue(SentenceValidator.isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*20xy"
        assertFalse(SentenceValidator.isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*201"
        assertFalse(SentenceValidator.isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*2"
        assertFalse(SentenceValidator.isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*"
        assertFalse(SentenceValidator.isSentence(nmea))
    }

    @Test
    fun testIsSentenceWithoutChecksum() {
        val nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,"
        assertTrue(SentenceValidator.isSentence(nmea))
    }

    @Test
    fun testIsSentenceWithChecksumAndNewline() {
        var nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*20\r\n"
        assertTrue(SentenceValidator.isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*20\n\r"
        assertTrue(SentenceValidator.isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*20\r"
        assertTrue(SentenceValidator.isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*20\n"
        assertTrue(SentenceValidator.isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*20\r\n\r\n"
        assertFalse(SentenceValidator.isSentence(nmea))
    }

    @Test
    fun testIsSentenceNoChecksumWithNewline() {
        var nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,\r\n"
        assertTrue(SentenceValidator.isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,\n\r"
        assertTrue(SentenceValidator.isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,\r"
        assertTrue(SentenceValidator.isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,\n"
        assertTrue(SentenceValidator.isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,\r\n\r\n"
        assertFalse(SentenceValidator.isSentence(nmea))
    }
}