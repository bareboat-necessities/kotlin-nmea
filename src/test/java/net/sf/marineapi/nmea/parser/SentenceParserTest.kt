package net.sf.marineapi.nmea.parser

import org.junit.Assert.assertEquals

/**
 * Tests the sentence parser base class.
 *
 * @author Kimmo Tuukkanen
 */
class SentenceParserTest {
    private var instance: SentenceParser? = null

    /**
     * setUp
     */
    @Before
    fun setUp() {
        instance = SentenceParser(RMCTest.Companion.EXAMPLE)
    }

    /**
     * Test method for SenteceParser constructor.
     */
    @Test
    fun testConstructorForEmptySentence() {
        val s: Sentence = SentenceParser(TalkerId.GP, SentenceId.GLL, 5)
        assertEquals("\$GPGLL,,,,,*7C", s.toString())
    }

    /**
     * Test method for SenteceParser constructor.
     */
    @Test
    fun testConstructorForEmptyProprietary() {
        val s: Sentence = SentenceParser(TalkerId.P, "RWIILOG", 5)
        assertEquals("\$PRWIILOG,,,,,*3D", s.toString())
    }

    /**
     * Test method for SentenceParser constructors called from derived custom
     * parsers.
     */
    @Test
    fun testConstructorWithCustomParser() {
        val foo = "FOO"
        val sf = SentenceFactory.getInstance()
        sf.registerParser(foo, FOOParser::class.java)
        val fooSentence = "\$GPFOO,B,A,R"
        val fp: FOOSentence = FOOParser(fooSentence)
        val s: Sentence? = sf.createParser(fooSentence)
        assertTrue(s is SentenceParser)
        assertTrue(s is FOOParser)
        assertEquals(foo, s.sentenceId)
        assertEquals(TalkerId.GP, s.talkerId)
        assertEquals(s, fp)
    }

    /**
     * Test method for SenteceParser constructor.
     */
    @Test
    fun testConstructorWithInvalidSentence() {
        try {
            val sent = "GPBOD,234.9,T,228.8,M,RUSKI,*1D"
            SentenceParser(sent)
            fail("Did not throw exception")
        } catch (se: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for SenteceParser constructor.
     */
    @Test
    fun testConstructorWithAIVDO() {
        testConstructorWithAIS(VDO_EXAMPLE)
    }

    /**
     * Test method for SenteceParser constructor.
     */
    @Test
    fun testConstructorWithAIVDM() {
        testConstructorWithAIS(VDM_EXAMPLE)
    }

    /**
     * Parse and test given AIS sentence.
     */
    private fun testConstructorWithAIS(ais: String) {
        val s: Sentence = SentenceParser(ais)
        assertTrue(s.isValid)
        assertFalse(s.isProprietary)
        assertEquals(Sentence.ALTERNATIVE_BEGIN_CHAR, s.beginChar)
        assertEquals(ais, s.toString())
    }

    /**
     * Test method for SenteceParser constructor with proprietary sentence.
     */
    @Test
    fun testConstructorWithProprietary() {
        val s: Sentence = SentenceParser("\$PRWIILOG,GGA,A,T,1,0", "RWIILOG")
        assertTrue(s.isValid)
        assertTrue(s.isProprietary)
        assertEquals(Sentence.BEGIN_CHAR, s.beginChar)
        assertEquals(TalkerId.P, s.talkerId)
        assertEquals("RWIILOG", s.sentenceId)
    }

    /**
     * Test method for SenteceParser constructor.
     */
    @Test
    fun testConstructorWithNulls() {
        try {
            SentenceParser(null, null as String?)
            fail("Did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // OK
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for SenteceParser constructor.
     */
    @Test
    fun testConstructorWithNullType() {
        try {
            SentenceParser(RMCTest.Companion.EXAMPLE, null as String?)
            fail("Did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // OK
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for SenteceParser constructor.
     */
    @Test
    fun testConstructorWithUnsupportedTalker() {
        try {
            SentenceParser("\$XZGGA,VALID,BUT,TALKER,NOT,SUPPORTED")
            fail("Did not throw exception")
        } catch (se: IllegalArgumentException) {
            assertTrue(se.message!!.endsWith(".TalkerId.XZ"))
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for SenteceParser constructor.
     */
    @Test
    fun testConstructorWithWrongType() {
        try {
            SentenceParser(BODTest.Companion.EXAMPLE, SentenceId.GLL.toString())
            fail("Did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // OK
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceParser.getCharValue].
     */
    @Test
    fun testGetCharValueWithEmptyFields() {
        val nmea = "\$GPGLL,,,,,,"
        val s = SentenceParser(nmea)
        try {
            s.getCharValue(3)
            fail("Did not throw exception")
        } catch (ex: DataNotAvailableException) {
            // pass
        } catch (ex: Exception) {
            fail(ex.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceParser.getDoubleValue].
     */
    @Test
    fun testGetDoubleValueWithEmptyFields() {
        val nmea = "\$GPGLL,,,,,,"
        val s = SentenceParser(nmea)
        try {
            s.getDoubleValue(2)
            fail("Did not throw exception")
        } catch (ex: DataNotAvailableException) {
            // pass
        } catch (ex: Exception) {
            fail(ex.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceParser.getDoubleValue].
     */
    @Test
    fun testGetDoubleValueWithInvalidValue() {
        val nmea = "\$GPGLL,a,b,c,d,e,f"
        val s = SentenceParser(nmea)
        try {
            s.getDoubleValue(2)
            fail("Did not throw exception")
        } catch (ex: ParseException) {
            // pass
        } catch (ex: Exception) {
            fail(ex.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceParser.getSentenceId].
     */
    @Test
    fun testGetSentenceId() {
        val sid: SentenceId = SentenceId.valueOf(instance!!.sentenceId)
        assertEquals(SentenceId.RMC, sid)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceParser.getStringValue].
     */
    @Test
    fun testGetStringValue() {
        val nmea = "\$GPGLL,6011.552,N,02501.941,E,120045,A"
        val s = SentenceParser(nmea)
        val data = "6011.552,N,02501.941,E,120045,A"
        val expected = data.split(",".toRegex()).toTypedArray()
        for (i in expected.indices) {
            assertEquals(expected[i], s.getStringValue(i))
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceParser.getStringValue].
     */
    @Test
    fun testGetStringValueWithEmptyFields() {
        val nmea = "\$GPGLL,,,,,,"
        val s = SentenceParser(nmea)
        try {
            s.getStringValue(2)
            fail("Did not throw exception")
        } catch (ex: DataNotAvailableException) {
            // pass
        } catch (ex: Exception) {
            fail(ex.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceParser.getStringValue].
     */
    @Test
    fun testGetStringValueWithIndexGreaterThanAllowed() {
        try {
            instance!!.getStringValue(instance!!.fieldCount)
            fail("Did not throw IndexOutOfBoundsException")
        } catch (e: IndexOutOfBoundsException) {
            // pass
        } catch (e: Exception) {
            fail("Unexpected exception was thrown")
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceParser.getStringValue].
     */
    @Test
    fun testGetStringValueWithNegativeIndex() {
        try {
            instance!!.getStringValue(-1)
            fail("Did not throw IndexOutOfBoundsException")
        } catch (e: IndexOutOfBoundsException) {
            // pass
        } catch (e: Exception) {
            fail("Unexpected exception was thrown")
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceParser.getStringValue].
     */
    @Test
    fun testGetStringValueWithValidIndex() {
        try {
            var `val` = instance!!.getStringValue(0)
            assertEquals("120044.567", `val`)
            `val` = instance!!.getStringValue(instance!!.fieldCount - 1)
            assertEquals("A", `val`)
        } catch (e: IndexOutOfBoundsException) {
            fail("Unexpected IndexOutOfBoundsException")
        } catch (e: Exception) {
            fail("Unexpected exception was thrown")
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceParser.getTalkerId].
     */
    @Test
    fun testGetTalkerId() {
        assertEquals(TalkerId.GP, instance!!.talkerId)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceParser.isProprietary].
     */
    @Test
    fun testIsProprietary() {
        assertFalse(instance!!.isProprietary)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceParser.setBeginChar].
     */
    @Test
    fun testSetBeginChar() {
        assertEquals(Sentence.BEGIN_CHAR, instance!!.getBeginChar())
        instance!!.setBeginChar(Sentence.ALTERNATIVE_BEGIN_CHAR)
        assertEquals(Sentence.ALTERNATIVE_BEGIN_CHAR, instance!!.getBeginChar())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceParser.isValid].
     */
    @Test
    fun testIsValid() {
        assertTrue(instance!!.isValid)
        instance!!.setStringValue(0, "\t")
        assertFalse(instance!!.isValid)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceParser.setDoubleValue]
     * .
     */
    @Test
    fun testSetDoubleValue() {
        val field = 0
        val value = 123.456789
        instance!!.setDoubleValue(field, value)
        assertEquals(value.toString(), instance!!.getStringValue(field))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceParser.setDoubleValue]
     * .
     */
    @Test
    fun testSetDoubleValueWithPrecision() {
        instance!!.setDoubleValue(0, 3.14, 0, 0)
        assertEquals("3", instance!!.getStringValue(0))
        instance!!.setDoubleValue(0, 3.14, 2, 0)
        assertEquals("03", instance!!.getStringValue(0))
        instance!!.setDoubleValue(0, 3.14, 1, 4)
        assertEquals("3.1400", instance!!.getStringValue(0))
        instance!!.setDoubleValue(0, 678.910, 3, 3)
        assertEquals("678.910", instance!!.getStringValue(0))
        instance!!.setDoubleValue(0, 123.456, 4, 1)
        assertEquals("0123.5", instance!!.getStringValue(0))
        instance!!.setDoubleValue(0, 78.910, 1, 1)
        assertEquals("78.9", instance!!.getStringValue(0))
        instance!!.setDoubleValue(0, 0.910, 0, 3)
        assertEquals(".910", instance!!.getStringValue(0))
        instance!!.setDoubleValue(0, 0.910, 3, 2)
        assertEquals("000.91", instance!!.getStringValue(0))
        instance!!.setDoubleValue(0, 0.910, 0, 2)
        assertEquals(".91", instance!!.getStringValue(0))
    }

    @Test
    fun testSetDoubleValueJDK7RoundingIssue() {

        // 2016-11-06: open-jdk7 has rounding issues that were not caught by this test
        // until now. All good in jdk8, thus testing conditionally.
        // https://bugs.openjdk.java.net/browse/JDK-8029896
        // https://bugs.openjdk.java.net/browse/JDK-8039915
        val version = System.getProperty("java.version")
        org.junit.Assume.assumeTrue(!version.startsWith("1.7."))

        // would fail in jdk7 claiming "12.35" not equal to "12.34"
        instance!!.setDoubleValue(0, 12.345, 1, 2)
        assertEquals("12.35", instance!!.getStringValue(0))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceParser.setIntValue]
     * .
     */
    @Test
    fun testSetIntValueWithLeading() {
        instance!!.setIntValue(0, 0, 0)
        assertEquals("0", instance!!.getStringValue(0))
        instance!!.setIntValue(0, 0, 1)
        assertEquals("0", instance!!.getStringValue(0))
        instance!!.setIntValue(0, 1, 2)
        assertEquals("01", instance!!.getStringValue(0))
        instance!!.setIntValue(0, 1, 3)
        assertEquals("001", instance!!.getStringValue(0))
        instance!!.setIntValue(0, 12, 1)
        assertEquals("12", instance!!.getStringValue(0))
        instance!!.setIntValue(0, 12, 2)
        assertEquals("12", instance!!.getStringValue(0))
        instance!!.setIntValue(0, 12, 3)
        assertEquals("012", instance!!.getStringValue(0))
        instance!!.setIntValue(0, -1, 3)
        assertEquals("-01", instance!!.getStringValue(0))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceParser.toString].
     */
    @Test
    fun testToString() {
        assertEquals(RMCTest.Companion.EXAMPLE, instance.toString())
        assertEquals(instance.toString(), instance!!.toSentence())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceParser.toSentence].
     */
    @Test
    fun testToSentenceWithMaxLength() {
        val max = instance.toString().length + 1
        assertEquals(RMCTest.Companion.EXAMPLE, instance!!.toSentence(max))
    }

    /**
     * Test method for
     * [§(int)][net.sf.marineapi.nmea.parser.SentenceParser.].
     */
    @Test
    fun testToSentenceWithMaxLengthOnLimit() {
        val max = instance.toString().length
        assertEquals(RMCTest.Companion.EXAMPLE, instance!!.toSentence(max))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceParser.toSentence].
     */
    @Test
    fun testToSentenceWithMaxLengthExceeded() {
        try {
            val max = instance.toString().length - 1
            assertEquals(RMCTest.Companion.EXAMPLE, instance!!.toSentence(max))
            fail("didn't throw exception")
        } catch (e: Exception) {
            // pass
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceParser.equals]
     */
    @Test
    fun testEquals() {
        assertTrue(instance!!.equals(SentenceParser(RMCTest.Companion.EXAMPLE)))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceParser.equals]
     */
    @Test
    fun testEqualsWithNonEqual() {
        assertFalse(instance!!.equals(SentenceParser(RMBTest.Companion.EXAMPLE)))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceParser.equals]
     */
    @Test
    fun testEqualsWithNull() {
        assertFalse(instance!!.equals(null))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceParser.equals]
     */
    @Test
    fun testEqualsWithSelf() {
        assertTrue(instance!!.equals(instance))
    }

    @Test
    fun testSetFieldCountLowerByOne() {
        val count = instance!!.fieldCount - 1
        val lastIndex = instance!!.fieldCount - 2
        val value = instance!!.getStringValue(lastIndex)
        instance!!.fieldCount = count
        assertEquals(count, instance!!.fieldCount)
        assertEquals(value, instance!!.getStringValue(lastIndex))
    }

    @Test
    fun testSetFieldCountLower() {
        val parser = SentenceParser("\$GPGGA,1,2,3,4")
        parser.fieldCount = 2
        assertEquals(2, parser.fieldCount)
        assertEquals("1", parser.getStringValue(0))
        assertEquals("2", parser.getStringValue(1))
        assertTrue(parser.toString().startsWith("\$GPGGA,1,2*"))
    }

    @Test
    fun testSetFieldCountHigherByOne() {
        val count = instance!!.fieldCount + 1
        val lastIndex = instance!!.fieldCount - 1
        val value = instance!!.getStringValue(lastIndex)
        instance!!.fieldCount = count
        assertEquals(count, instance!!.fieldCount)
        assertEquals(value, instance!!.getStringValue(lastIndex))
    }

    @Test
    fun testSetFieldCountHigher() {
        val parser = SentenceParser("\$GPGGA,1,2,3,4")
        parser.fieldCount = 8
        assertEquals(8, parser.fieldCount)
        assertTrue(parser.toString().startsWith("\$GPGGA,1,2,3,4,,,,*"))
    }

    @Test
    fun testSetStringValuesReplaceAll() {
        val parser = SentenceParser("\$GPGGA,1,2,3,4")
        val values = arrayOf<String?>("5", "6", "7")
        parser.setStringValues(0, values)
        assertEquals(3, parser.fieldCount)
        assertEquals("5", parser.getStringValue(0))
        assertEquals("6", parser.getStringValue(1))
        assertEquals("7", parser.getStringValue(2))
    }

    @Test
    fun testSetStringValuesReplaceTail() {
        val parser = SentenceParser("\$GPGGA,1,2,3,4")
        val values = arrayOf<String?>("5", "6", "7")
        parser.setStringValues(1, values)
        assertEquals(4, parser.fieldCount)
        assertEquals("1", parser.getStringValue(0))
        assertEquals("5", parser.getStringValue(1))
        assertEquals("6", parser.getStringValue(2))
        assertEquals("7", parser.getStringValue(3))
    }

    companion object {
        const val VDO_EXAMPLE = "!AIVDO,1,1,,,13:r`R5P1orpG60JeHgRSj4l0000,0*56"
        const val VDM_EXAMPLE = "!AIVDM,1,1,,B,177KQJ5000G?tO`K>RA1wUbN0TKH,0*5C"
    }
}