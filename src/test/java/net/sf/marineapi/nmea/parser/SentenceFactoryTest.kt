package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.BODSentence
import net.sf.marineapi.nmea.sentence.Sentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.test.util.BARParser
import net.sf.marineapi.test.util.FOOParser
import net.sf.marineapi.test.util.FOOSentence
import net.sf.marineapi.test.util.VDMParser
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * @author Kimmo Tuukkanen
 */
class SentenceFactoryTest {
    private val instance: SentenceFactory = SentenceFactory.instance

    /**
     * @throws Exception
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
        instance.reset()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        instance.reset()
    }

    /**
     * Test method for
     * [SentenceFactory.createParser]
     * .
     */
    @Test
    fun testSupportedTypesRegistered() {
        for (id in SentenceId.values()) {
            val msg = "Parser not registered: $id"
            Assert.assertTrue(msg, instance.hasParser(id.toString()))
        }
    }

    /**
     * Test method for
     * [SentenceFactory.createParser]
     * .
     */
    @Test
    fun testCreateParser() {
        val bod = instance.createParser(BODTest.EXAMPLE)
        Assert.assertNotNull(bod)
        Assert.assertTrue(bod is Sentence)
        Assert.assertTrue(bod is BODSentence)
        Assert.assertTrue(bod is BODParser)
        assertEquals(BODTest.EXAMPLE, bod!!.toSentence())
    }

    /**
     * Test method for
     * [ .][SentenceFactory.createParser]
     */
    @Test
    fun testCreateEmptyParserWithSentenceId() {
        for (id in SentenceId.values()) {
            val s = instance.createParser(TalkerId.ST, id)
            Assert.assertNotNull(s)
            Assert.assertTrue(s is Sentence)
            Assert.assertTrue(s is SentenceParser)
            assertEquals(TalkerId.ST, s!!.getTalkerId())
            assertEquals(id.name, s.getSentenceId())
        }
    }

    /**
     * Test method for
     * [SentenceFactory.createParser]
     * .
     */
    @Test
    fun testCreateEmptyParserWithSentenceIdStr() {
        for (id in SentenceId.values()) {
            val s = instance.createParser(TalkerId.ST, id.name)
            Assert.assertNotNull(s)
            Assert.assertTrue(s is Sentence)
            Assert.assertTrue(s is SentenceParser)
        }
    }

    /**
     * Test method for
     * [SentenceFactory.createParser]
     * .
     */
    @Test
    fun testCreateCustomParser() {
        try {
            instance.registerParser("FOO", FOOParser::class.java)
            Assert.assertTrue(instance.hasParser("FOO"))
        } catch (e: Exception) {
            Assert.fail("parser registering failed")
        }
        var s: Sentence? = null
        try {
            s = instance.createParser("\$IIFOO,aa,bb,cc")
        } catch (e: Exception) {
            Assert.fail("sentence parsing failed")
        }
        Assert.assertNotNull(s)
        Assert.assertTrue(s is Sentence)
        Assert.assertTrue(s is SentenceParser)
        Assert.assertTrue(s is FOOParser)
        assertEquals(TalkerId.II, s!!.getTalkerId())
        assertEquals("FOO", s.getSentenceId())
        assertEquals("aa", (s as FOOSentence?)!!.valueA)
        assertEquals("bb", (s as FOOSentence?)!!.valueB)
        assertEquals("cc", (s as FOOSentence?)!!.valueC)
    }

    /**
     * Test method for
     * [SentenceFactory.createParser]
     * .
     */
    @Test
    fun testCreateEmptyCustomParser() {
        try {
            instance.registerParser("FOO", FOOParser::class.java)
            Assert.assertTrue(instance.hasParser("FOO"))
        } catch (e: Exception) {
            Assert.fail("parser registering failed")
        }
        val s = instance.createParser(TalkerId.II, "FOO")
        Assert.assertNotNull(s)
        Assert.assertTrue(s is Sentence)
        Assert.assertTrue(s is SentenceParser)
        Assert.assertTrue(s is FOOParser)
        assertEquals("FOO", s!!.getSentenceId())
    }

    /**
     * Test method for
     * [SentenceFactory.createParser]
     * .
     */
    @Test
    fun testCreateParserWithEmptyString() {
        try {
            instance.createParser("")
            Assert.fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            // pass
        }
    }

    /**
     * Test method for
     * [SentenceFactory.createParser]
     * .
     */
    @Test
    fun testCreateParserWithRandom() {
        try {
            instance.createParser("asdqas,dwersa,dsdfas,das")
            Assert.fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            // pass
        }
    }

    /**
     * Test method for
     * [SentenceFactory.createParser]
     * .
     */
    @Test
    fun testCreateParserWithUnregistered() {
        try {
            instance.createParser("\$GPXYZ,1,2,3,4,5,6,7,8")
            Assert.fail("Did not throw exception")
        } catch (e: UnsupportedSentenceException) {
            // pass
        }
    }

    /**
     * Test method for
     * [SentenceFactory.registerParser]
     * .
     */
    @Test
    fun testRegisterParserWithAlternativeBeginChar() {
        try {
            instance.registerParser("VDM", VDMParser::class.java)
            Assert.assertTrue(instance.hasParser("VDM"))
        } catch (e: Exception) {
            Assert.fail("parser registering failed")
        }
        val s = instance.createParser("!AIVDM,1,2,3")
        Assert.assertNotNull(s)
        Assert.assertTrue(s is Sentence)
        Assert.assertTrue(s is SentenceParser)
        Assert.assertTrue(s is VDMParser)
        instance.unregisterParser(VDMParser::class.java)
        Assert.assertFalse(instance.hasParser("VDM"))
    }

    /**
     * Test method for
     * [SentenceFactory.registerParser]
     * .
     */
    @Test
    fun testRegisterInvalidParser() {
        try {
            instance.registerParser("BAR", BARParser::class.java)
            Assert.fail("did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    /**
     * Test method for
     * [SentenceFactory.registerParser]
     * .
     */
    @Test
    fun testUnregisterParser() {
        instance.registerParser("FOO", FOOParser::class.java)
        Assert.assertTrue(instance.hasParser("FOO"))
        instance.unregisterParser(FOOParser::class.java)
        Assert.assertFalse(instance.hasParser("FOO"))
    }

    /**
     * Test method for
     * [SentenceFactory.hasParser]
     * .
     */
    @Test
    fun testHasParser() {
        Assert.assertTrue(instance.hasParser("GLL"))
        Assert.assertFalse(instance.hasParser("ABC"))
    }

    /**
     * Test method for
     * [ .][SentenceFactory.createParser]
     */
    @Test
    fun testListParsers() {
        val types = instance.listParsers()
        assertEquals(SentenceId.values().size.toLong(), types.size.toLong())
        for (id in SentenceId.values()) {
            Assert.assertTrue(types.contains(id.name))
        }
    }

    /**
     * Test method for
     * [SentenceFactory.getInstance].
     */
    @Test
    fun testGetInstance() {
        Assert.assertNotNull(instance)
        Assert.assertTrue(instance == SentenceFactory.instance)
        assertEquals(instance, SentenceFactory.instance)
    }
}