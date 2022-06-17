package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.Sentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.test.util.FOOParser
import net.sf.marineapi.test.util.FOOSentence
import org.junit.Assert
import org.junit.Assume
import org.junit.Before
import org.junit.Test

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
        Assert.assertEquals("\$GPGLL,,,,,*7C", s.toString())
    }

    /**
     * Test method for SenteceParser constructor.
     */
    @Test
    fun testConstructorForEmptyProprietary() {
        val s: Sentence = SentenceParser(TalkerId.P, "RWIILOG", 5)
        Assert.assertEquals("\$PRWIILOG,,,,,*3D", s.toString())
    }

    /**
     * Test method for SentenceParser constructors called from derived custom
     * parsers.
     */
    @Test
    fun testConstructorWithCustomParser() {
        val foo = "FOO"
        val sf: SentenceFactory = SentenceFactory.getInstance()
        sf.registerParser(foo, FOOParser::class.java)
        val fooSentence = "\$GPFOO,B,A,R"
        val fp: FOOSentence = FOOParser(fooSentence)
        val s = sf.createParser(fooSentence)
        Assert.assertTrue(s is SentenceParser)
        Assert.assertTrue(s is FOOParser)
        Assert.assertEquals(foo, s!!.getSentenceId())
        Assert.assertEquals(TalkerId.GP, s.getTalkerId())
        Assert.assertEquals(s, fp)
    }

    /**
     * Test method for SenteceParser constructor.
     */
    @Test
    fun testConstructorWithInvalidSentence() {
        try {
            val sent = "GPBOD,234.9,T,228.8,M,RUSKI,*1D"
            SentenceParser(sent)
            Assert.fail("Did not throw exception")
        } catch (se: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            Assert.fail(e.message)
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
        Assert.assertTrue(s.isValid())
        Assert.assertFalse(s.isProprietary())
        Assert.assertEquals(Sentence.ALTERNATIVE_BEGIN_CHAR.code.toLong(), s.getBeginChar().code.toLong())
        Assert.assertEquals(ais, s.toString())
    }

    /**
     * Test method for SenteceParser constructor with proprietary sentence.
     */
    @Test
    fun testConstructorWithProprietary() {
        val s: Sentence = SentenceParser("\$PRWIILOG,GGA,A,T,1,0", "RWIILOG")
        Assert.assertTrue(s.isValid())
        Assert.assertTrue(s.isProprietary())
        Assert.assertEquals(Sentence.BEGIN_CHAR.code.toLong(), s.getBeginChar().code.toLong())
        Assert.assertEquals(TalkerId.P, s.getTalkerId())
        Assert.assertEquals("RWIILOG", s.getSentenceId())
    }

    /**
     * Test method for SenteceParser constructor.
     */
    @Test
    fun testConstructorWithNulls() {
        try {
            SentenceParser(null, null as String?)
            Assert.fail("Did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // OK
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    /**
     * Test method for SenteceParser constructor.
     */
    @Test
    fun testConstructorWithNullType() {
        try {
            SentenceParser(RMCTest.Companion.EXAMPLE, null as String?)
            Assert.fail("Did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // OK
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    /**
     * Test method for SenteceParser constructor.
     */
    @Test
    fun testConstructorWithUnsupportedTalker() {
        try {
            SentenceParser("\$XZGGA,VALID,BUT,TALKER,NOT,SUPPORTED")
            Assert.fail("Did not throw exception")
        } catch (se: IllegalArgumentException) {
            Assert.assertTrue(se.message!!.endsWith(".TalkerId.XZ"))
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    /**
     * Test method for SenteceParser constructor.
     */
    @Test
    fun testConstructorWithWrongType() {
        try {
            SentenceParser(BODTest.EXAMPLE, SentenceId.GLL.toString())
            Assert.fail("Did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // OK
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    /**
     * Test method for
     * [SentenceParser.getCharValue].
     */
    @Test
    fun testGetCharValueWithEmptyFields() {
        val nmea = "\$GPGLL,,,,,,"
        val s = SentenceParser(nmea)
        try {
            s.getCharValue(3)
            Assert.fail("Did not throw exception")
        } catch (ex: DataNotAvailableException) {
            // pass
        } catch (ex: Exception) {
            Assert.fail(ex.message)
        }
    }

    /**
     * Test method for
     * [SentenceParser.getDoubleValue].
     */
    @Test
    fun testGetDoubleValueWithEmptyFields() {
        val nmea = "\$GPGLL,,,,,,"
        val s = SentenceParser(nmea)
        try {
            s.getDoubleValue(2)
            Assert.fail("Did not throw exception")
        } catch (ex: DataNotAvailableException) {
            // pass
        } catch (ex: Exception) {
            Assert.fail(ex.message)
        }
    }

    /**
     * Test method for
     * [SentenceParser.getDoubleValue].
     */
    @Test
    fun testGetDoubleValueWithInvalidValue() {
        val nmea = "\$GPGLL,a,b,c,d,e,f"
        val s = SentenceParser(nmea)
        try {
            s.getDoubleValue(2)
            Assert.fail("Did not throw exception")
        } catch (ex: ParseException) {
            // pass
        } catch (ex: Exception) {
            Assert.fail(ex.message)
        }
    }

    /**
     * Test method for
     * [SentenceParser.getSentenceId].
     */
    @Test
    fun testGetSentenceId() {
        val sid = SentenceId.valueOf(instance!!.getSentenceId())
        Assert.assertEquals(SentenceId.RMC, sid)
    }

    /**
     * Test method for
     * [SentenceParser.getStringValue].
     */
    @Test
    fun testGetStringValue() {
        val nmea = "\$GPGLL,6011.552,N,02501.941,E,120045,A"
        val s = SentenceParser(nmea)
        val data = "6011.552,N,02501.941,E,120045,A"
        val expected = data.split(",".toRegex()).toTypedArray()
        for (i in expected.indices) {
            Assert.assertEquals(expected[i], s.getStringValue(i))
        }
    }

    /**
     * Test method for
     * [SentenceParser.getStringValue].
     */
    @Test
    fun testGetStringValueWithEmptyFields() {
        val nmea = "\$GPGLL,,,,,,"
        val s = SentenceParser(nmea)
        try {
            s.getStringValue(2)
            Assert.fail("Did not throw exception")
        } catch (ex: DataNotAvailableException) {
            // pass
        } catch (ex: Exception) {
            Assert.fail(ex.message)
        }
    }

    /**
     * Test method for
     * [SentenceParser.getStringValue].
     */
    @Test
    fun testGetStringValueWithIndexGreaterThanAllowed() {
        try {
            instance!!.getStringValue(instance!!.getFieldCount())
            Assert.fail("Did not throw IndexOutOfBoundsException")
        } catch (e: IndexOutOfBoundsException) {
            // pass
        } catch (e: Exception) {
            Assert.fail("Unexpected exception was thrown")
        }
    }

    /**
     * Test method for
     * [SentenceParser.getStringValue].
     */
    @Test
    fun testGetStringValueWithNegativeIndex() {
        try {
            instance!!.getStringValue(-1)
            Assert.fail("Did not throw IndexOutOfBoundsException")
        } catch (e: IndexOutOfBoundsException) {
            // pass
        } catch (e: Exception) {
            Assert.fail("Unexpected exception was thrown")
        }
    }

    /**
     * Test method for
     * [SentenceParser.getStringValue].
     */
    @Test
    fun testGetStringValueWithValidIndex() {
        try {
            var `val` = instance!!.getStringValue(0)
            Assert.assertEquals("120044.567", `val`)
            `val` = instance!!.getStringValue(instance!!.getFieldCount() - 1)
            Assert.assertEquals("A", `val`)
        } catch (e: IndexOutOfBoundsException) {
            Assert.fail("Unexpected IndexOutOfBoundsException")
        } catch (e: Exception) {
            Assert.fail("Unexpected exception was thrown")
        }
    }

    /**
     * Test method for
     * [SentenceParser.getTalkerId].
     */
    @Test
    fun testGetTalkerId() {
        Assert.assertEquals(TalkerId.GP, instance!!.getTalkerId())
    }

    /**
     * Test method for
     * [SentenceParser.isProprietary].
     */
    @Test
    fun testIsProprietary() {
        Assert.assertFalse(instance!!.isProprietary())
    }

    /**
     * Test method for
     * [SentenceParser.setBeginChar].
     */
    @Test
    fun testSetBeginChar() {
        Assert.assertEquals(Sentence.BEGIN_CHAR.code.toLong(), instance!!.getBeginChar().code.toLong())
        instance!!.setBeginChar(Sentence.ALTERNATIVE_BEGIN_CHAR)
        Assert.assertEquals(Sentence.ALTERNATIVE_BEGIN_CHAR.code.toLong(), instance!!.getBeginChar().code.toLong())
    }

    /**
     * Test method for
     * [SentenceParser.isValid].
     */
    @Test
    fun testIsValid() {
        Assert.assertTrue(instance!!.isValid())
        instance!!.setStringValue(0, "\t")
        Assert.assertFalse(instance!!.isValid())
    }

    /**
     * Test method for
     * [SentenceParser.setDoubleValue]
     * .
     */
    @Test
    fun testSetDoubleValue() {
        val field = 0
        val value = 123.456789
        instance!!.setDoubleValue(field, value)
        Assert.assertEquals(value.toString(), instance!!.getStringValue(field))
    }

    /**
     * Test method for
     * [SentenceParser.setDoubleValue]
     * .
     */
    @Test
    fun testSetDoubleValueWithPrecision() {
        instance!!.setDoubleValue(0, 3.14, 0, 0)
        Assert.assertEquals("3", instance!!.getStringValue(0))
        instance!!.setDoubleValue(0, 3.14, 2, 0)
        Assert.assertEquals("03", instance!!.getStringValue(0))
        instance!!.setDoubleValue(0, 3.14, 1, 4)
        Assert.assertEquals("3.1400", instance!!.getStringValue(0))
        instance!!.setDoubleValue(0, 678.910, 3, 3)
        Assert.assertEquals("678.910", instance!!.getStringValue(0))
        instance!!.setDoubleValue(0, 123.456, 4, 1)
        Assert.assertEquals("0123.5", instance!!.getStringValue(0))
        instance!!.setDoubleValue(0, 78.910, 1, 1)
        Assert.assertEquals("78.9", instance!!.getStringValue(0))
        instance!!.setDoubleValue(0, 0.910, 0, 3)
        Assert.assertEquals(".910", instance!!.getStringValue(0))
        instance!!.setDoubleValue(0, 0.910, 3, 2)
        Assert.assertEquals("000.91", instance!!.getStringValue(0))
        instance!!.setDoubleValue(0, 0.910, 0, 2)
        Assert.assertEquals(".91", instance!!.getStringValue(0))
    }

    @Test
    fun testSetDoubleValueJDK7RoundingIssue() {

        // 2016-11-06: open-jdk7 has rounding issues that were not caught by this test
        // until now. All good in jdk8, thus testing conditionally.
        // https://bugs.openjdk.java.net/browse/JDK-8029896
        // https://bugs.openjdk.java.net/browse/JDK-8039915
        val version = System.getProperty("java.version")
        Assume.assumeTrue(!version.startsWith("1.7."))

        // would fail in jdk7 claiming "12.35" not equal to "12.34"
        instance!!.setDoubleValue(0, 12.345, 1, 2)
        Assert.assertEquals("12.35", instance!!.getStringValue(0))
    }

    /**
     * Test method for
     * [SentenceParser.setIntValue]
     * .
     */
    @Test
    fun testSetIntValueWithLeading() {
        instance!!.setIntValue(0, 0, 0)
        Assert.assertEquals("0", instance!!.getStringValue(0))
        instance!!.setIntValue(0, 0, 1)
        Assert.assertEquals("0", instance!!.getStringValue(0))
        instance!!.setIntValue(0, 1, 2)
        Assert.assertEquals("01", instance!!.getStringValue(0))
        instance!!.setIntValue(0, 1, 3)
        Assert.assertEquals("001", instance!!.getStringValue(0))
        instance!!.setIntValue(0, 12, 1)
        Assert.assertEquals("12", instance!!.getStringValue(0))
        instance!!.setIntValue(0, 12, 2)
        Assert.assertEquals("12", instance!!.getStringValue(0))
        instance!!.setIntValue(0, 12, 3)
        Assert.assertEquals("012", instance!!.getStringValue(0))
        instance!!.setIntValue(0, -1, 3)
        Assert.assertEquals("-01", instance!!.getStringValue(0))
    }

    /**
     * Test method for
     * [SentenceParser.toString].
     */
    @Test
    fun testToString() {
        Assert.assertEquals(RMCTest.Companion.EXAMPLE, instance.toString())
        Assert.assertEquals(instance.toString(), instance!!.toSentence())
    }

    /**
     * Test method for
     * [SentenceParser.toSentence].
     */
    @Test
    fun testToSentenceWithMaxLength() {
        val max = instance.toString().length + 1
        Assert.assertEquals(RMCTest.Companion.EXAMPLE, instance!!.toSentence(max))
    }

    /**
     * Test method for
     * [§(int)][SentenceParser.].
     */
    @Test
    fun testToSentenceWithMaxLengthOnLimit() {
        val max = instance.toString().length
        Assert.assertEquals(RMCTest.Companion.EXAMPLE, instance!!.toSentence(max))
    }

    /**
     * Test method for
     * [SentenceParser.toSentence].
     */
    @Test
    fun testToSentenceWithMaxLengthExceeded() {
        try {
            val max = instance.toString().length - 1
            Assert.assertEquals(RMCTest.Companion.EXAMPLE, instance!!.toSentence(max))
            Assert.fail("didn't throw exception")
        } catch (e: Exception) {
            // pass
        }
    }

    /**
     * Test method for
     * [SentenceParser.equals]
     */
    @Test
    fun testEquals() {
        Assert.assertTrue(instance!!.equals(SentenceParser(RMCTest.Companion.EXAMPLE)))
    }

    /**
     * Test method for
     * [SentenceParser.equals]
     */
    @Test
    fun testEqualsWithNonEqual() {
        Assert.assertFalse(instance!!.equals(SentenceParser(RMBTest.Companion.EXAMPLE)))
    }

    /**
     * Test method for
     * [SentenceParser.equals]
     */
    @Test
    fun testEqualsWithNull() {
        Assert.assertFalse(instance!!.equals(null))
    }

    /**
     * Test method for
     * [SentenceParser.equals]
     */
    @Test
    fun testEqualsWithSelf() {
        Assert.assertTrue(instance!!.equals(instance))
    }

    @Test
    fun testSetFieldCountLowerByOne() {
        val count = instance!!.getFieldCount() - 1
        val lastIndex = instance!!.getFieldCount() - 2
        val value = instance!!.getStringValue(lastIndex)
        instance!!.setFieldCount(count)
        Assert.assertEquals(count.toLong(), instance!!.getFieldCount().toLong())
        Assert.assertEquals(value, instance!!.getStringValue(lastIndex))
    }

    @Test
    fun testSetFieldCountLower() {
        val parser = SentenceParser("\$GPGGA,1,2,3,4")
        parser.setFieldCount(2)
        Assert.assertEquals(2, parser.getFieldCount().toLong())
        Assert.assertEquals("1", parser.getStringValue(0))
        Assert.assertEquals("2", parser.getStringValue(1))
        Assert.assertTrue(parser.toString().startsWith("\$GPGGA,1,2*"))
    }

    @Test
    fun testSetFieldCountHigherByOne() {
        val count = instance!!.getFieldCount() + 1
        val lastIndex = instance!!.getFieldCount() - 1
        val value = instance!!.getStringValue(lastIndex)
        instance!!.setFieldCount(count)
        Assert.assertEquals(count.toLong(), instance!!.getFieldCount().toLong())
        Assert.assertEquals(value, instance!!.getStringValue(lastIndex))
    }

    @Test
    fun testSetFieldCountHigher() {
        val parser = SentenceParser("\$GPGGA,1,2,3,4")
        parser.setFieldCount(8)
        Assert.assertEquals(8, parser.getFieldCount().toLong())
        Assert.assertTrue(parser.toString().startsWith("\$GPGGA,1,2,3,4,,,,*"))
    }

    @Test
    fun testSetStringValuesReplaceAll() {
        val parser = SentenceParser("\$GPGGA,1,2,3,4")
        val values = arrayOf<String?>("5", "6", "7")
        parser.setStringValues(0, values)
        Assert.assertEquals(3, parser.getFieldCount().toLong())
        Assert.assertEquals("5", parser.getStringValue(0))
        Assert.assertEquals("6", parser.getStringValue(1))
        Assert.assertEquals("7", parser.getStringValue(2))
    }

    @Test
    fun testSetStringValuesReplaceTail() {
        val parser = SentenceParser("\$GPGGA,1,2,3,4")
        val values = arrayOf<String?>("5", "6", "7")
        parser.setStringValues(1, values)
        Assert.assertEquals(4, parser.getFieldCount().toLong())
        Assert.assertEquals("1", parser.getStringValue(0))
        Assert.assertEquals("5", parser.getStringValue(1))
        Assert.assertEquals("6", parser.getStringValue(2))
        Assert.assertEquals("7", parser.getStringValue(3))
    }

    companion object {
        const val VDO_EXAMPLE = "!AIVDO,1,1,,,13:r`R5P1orpG60JeHgRSj4l0000,0*56"
        const val VDM_EXAMPLE = "!AIVDM,1,1,,B,177KQJ5000G?tO`K>RA1wUbN0TKH,0*5C"
    }
}