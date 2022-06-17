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
import org.junit.Assert
import org.junit.Test

/**
 * @author Kimmo Tuukkanen
 */
class SentenceValidatorTest {
    @Test
    fun testIsValid() {

        // "normal"
        val a = "\$ABCDE,1,2,3,4,5,6,7,8,9"
        Assert.assertTrue(isValid(a))
        Assert.assertTrue(isValid(add(a)))

        // empty sentence, single field
        val b = "\$ABCDE,"
        Assert.assertTrue(isValid(b))
        Assert.assertTrue(isValid(add(b)))

        // empty sentence, multiple fields
        val c = "\$ABCDE,,,,,,"
        Assert.assertTrue(isValid(c))
        Assert.assertTrue(isValid(add(c)))
        val d = "\$ABCDE,1,TWO,three,FOUR?,5,6.0,-7.0,Eigth-8,N1N3,#T3n"
        Assert.assertTrue(isValid(d))
        Assert.assertTrue(isValid(add(d)))

        // '!' begin char
        val e = "!ABCDE,1,2,3,4,5,6,7,8,9"
        Assert.assertTrue(isValid(e))
        Assert.assertTrue(isValid(add(e)))
    }

    @Test
    fun testIsValidWithInvalidInput() {
        // invalid checksum, otherwise valid
        Assert.assertFalse(isValid("\$ABCDE,1,2,3,4,5,6,7,8,9*00"))
        // something weird
        Assert.assertFalse(isValid(null))
        Assert.assertFalse(isValid(""))
        Assert.assertFalse(isValid("$"))
        Assert.assertFalse(isValid("*"))
        Assert.assertFalse(isValid("$,*"))
        Assert.assertFalse(isValid("\$GPGSV*"))
        Assert.assertFalse(isValid("foobar"))
        Assert.assertFalse(isValid("\$gpgga,1,2,3,4,5,6,7,8,9"))
        Assert.assertFalse(isValid("GPGGA,1,2,3,4,5,6,7,8,9"))
        Assert.assertFalse(isValid("\$GpGGA,1,2,3,4,5,6,7,8,9"))
        Assert.assertFalse(isValid("\$GPGGa,1,2,3,4,5,6,7,8,9"))
        Assert.assertFalse(isValid("\$GPGG#,1,2,3,4,5,6,7,8,9"))
        Assert.assertFalse(isValid("\$AB,1,2,3,4,5,6,7,8,9"))
        Assert.assertFalse(isValid("\$ABCDEFGHIJK,1,2,3,4,5,6,7,8,9"))
        Assert.assertFalse(isValid("\$GPGGA,1,2,3,4,5,6,7,8,9*00"))
    }

    @Test
    fun testIsValidWithValidInput() {
        Assert.assertTrue(isValid(BODTest.EXAMPLE))
        Assert.assertTrue(isValid(GGATest.EXAMPLE))
        Assert.assertTrue(isValid(GLLTest.EXAMPLE))
        Assert.assertTrue(isValid(GSATest.EXAMPLE))
        Assert.assertTrue(isValid(GSVTest.EXAMPLE))
        Assert.assertTrue(isValid(RMBTest.EXAMPLE))
        Assert.assertTrue(isValid(RMCTest.EXAMPLE))
        Assert.assertTrue(isValid(RTETest.EXAMPLE))
        Assert.assertTrue(isValid(VTGTest.EXAMPLE))
        Assert.assertTrue(isValid(WPLTest.EXAMPLE))
        Assert.assertTrue(isValid(ZDATest.EXAMPLE))
    }

    @Test
    fun testIsValidWithLongProprietaryId() {
        val str = "\$PRWIILOG,GGA,A,T,1,0"
        Assert.assertTrue(isSentence(str))
        Assert.assertTrue(isValid(str))
    }

    @Test
    fun testIsValidWithShortProprietaryId() {
        val str = "\$PUBX,03,GT{,ID,s,AZM,EL,SN,LK},"
        Assert.assertTrue(isSentence(str))
        Assert.assertTrue(isValid(str))
    }

    @Test
    fun testIsSentenceWithChecksum() {
        var nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*20"
        Assert.assertTrue(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*20xy"
        Assert.assertFalse(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*201"
        Assert.assertFalse(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*2"
        Assert.assertFalse(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*"
        Assert.assertFalse(isSentence(nmea))
    }

    @Test
    fun testIsSentenceWithoutChecksum() {
        val nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,"
        Assert.assertTrue(isSentence(nmea))
    }

    @Test
    fun testIsSentenceWithChecksumAndNewline() {
        var nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*20\r\n"
        Assert.assertTrue(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*20\n\r"
        Assert.assertTrue(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*20\r"
        Assert.assertTrue(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*20\n"
        Assert.assertTrue(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,*20\r\n\r\n"
        Assert.assertFalse(isSentence(nmea))
    }

    @Test
    fun testIsSentenceNoChecksumWithNewline() {
        var nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,\r\n"
        Assert.assertTrue(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,\n\r"
        Assert.assertTrue(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,\r"
        Assert.assertTrue(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,\n"
        Assert.assertTrue(isSentence(nmea))
        nmea = "\$GPRMC,142312.000,V,,,,,,,080514,,\r\n\r\n"
        Assert.assertFalse(isSentence(nmea))
    }
}