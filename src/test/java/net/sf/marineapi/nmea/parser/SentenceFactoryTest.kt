package net.sf.marineapi.nmea.parser

import net.sf.marineapi.test.util.VDMParser
import org.junit.Assert.assertEquals

/**
 * @author Kimmo Tuukkanen
 */
class SentenceFactoryTest {
    private val instance = SentenceFactory.getInstance()

    /**
     * @throws java.lang.Exception
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
     * [net.sf.marineapi.nmea.parser.SentenceFactory.createParser]
     * .
     */
    @Test
    fun testSupportedTypesRegistered() {
        for (id in SentenceId.values()) {
            val msg = "Parser not registered: $id"
            assertTrue(msg, instance.hasParser(id.toString()))
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceFactory.createParser]
     * .
     */
    @Test
    fun testCreateParser() {
        val bod: Sentence? = instance.createParser(BODTest.Companion.EXAMPLE)
        assertNotNull(bod)
        assertTrue(bod is Sentence)
        assertTrue(bod is BODSentence)
        assertTrue(bod is BODParser)
        assertEquals(BODTest.Companion.EXAMPLE, bod.toSentence())
    }

    /**
     * Test method for
     * [ .][net.sf.marineapi.nmea.parser.SentenceFactory.createParser]
     */
    @Test
    fun testCreateEmptyParserWithSentenceId() {
        for (id in SentenceId.values()) {
            val s: Sentence = instance.createParser(TalkerId.ST, id)
            assertNotNull(s)
            assertTrue(s is Sentence)
            assertTrue(s is SentenceParser)
            assertEquals(TalkerId.ST, s.talkerId)
            assertEquals(id.name, s.sentenceId)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceFactory.createParser]
     * .
     */
    @Test
    fun testCreateEmptyParserWithSentenceIdStr() {
        for (id in SentenceId.values()) {
            val s: Sentence = instance.createParser(TalkerId.ST, id.name)
            assertNotNull(s)
            assertTrue(s is Sentence)
            assertTrue(s is SentenceParser)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceFactory.createParser]
     * .
     */
    @Test
    fun testCreateCustomParser() {
        try {
            instance.registerParser("FOO", FOOParser::class.java)
            assertTrue(instance.hasParser("FOO"))
        } catch (e: Exception) {
            fail("parser registering failed")
        }
        var s: Sentence? = null
        try {
            s = instance.createParser("\$IIFOO,aa,bb,cc")
        } catch (e: Exception) {
            fail("sentence parsing failed")
        }
        assertNotNull(s)
        assertTrue(s is Sentence)
        assertTrue(s is SentenceParser)
        assertTrue(s is FOOParser)
        assertEquals(TalkerId.II, s.talkerId)
        assertEquals("FOO", s.sentenceId)
        assertEquals("aa", (s as FOOSentence?).getValueA())
        assertEquals("bb", (s as FOOSentence?).getValueB())
        assertEquals("cc", (s as FOOSentence?).getValueC())
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceFactory.createParser]
     * .
     */
    @Test
    fun testCreateEmptyCustomParser() {
        try {
            instance.registerParser("FOO", FOOParser::class.java)
            assertTrue(instance.hasParser("FOO"))
        } catch (e: Exception) {
            fail("parser registering failed")
        }
        val s: Sentence? = instance.createParser(TalkerId.II, "FOO")
        assertNotNull(s)
        assertTrue(s is Sentence)
        assertTrue(s is SentenceParser)
        assertTrue(s is FOOParser)
        assertEquals("FOO", s.sentenceId)
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceFactory.createParser]
     * .
     */
    @Test
    fun testCreateParserWithEmptyString() {
        try {
            instance.createParser("")
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            // pass
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceFactory.createParser]
     * .
     */
    @Test
    fun testCreateParserWithNull() {
        try {
            instance.createParser(null)
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            // pass
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceFactory.createParser]
     * .
     */
    @Test
    fun testCreateParserWithRandom() {
        try {
            instance.createParser("asdqas,dwersa,dsdfas,das")
            fail("Did not throw exception")
        } catch (e: IllegalArgumentException) {
            // pass
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceFactory.createParser]
     * .
     */
    @Test
    fun testCreateParserWithUnregistered() {
        try {
            instance.createParser("\$GPXYZ,1,2,3,4,5,6,7,8")
            fail("Did not throw exception")
        } catch (e: UnsupportedSentenceException) {
            // pass
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceFactory.registerParser]
     * .
     */
    @Test
    fun testRegisterParserWithAlternativeBeginChar() {
        try {
            instance.registerParser("VDM", VDMParser::class.java)
            assertTrue(instance.hasParser("VDM"))
        } catch (e: Exception) {
            fail("parser registering failed")
        }
        val s: Sentence? = instance.createParser("!AIVDM,1,2,3")
        assertNotNull(s)
        assertTrue(s is Sentence)
        assertTrue(s is SentenceParser)
        assertTrue(s is VDMParser)
        instance.unregisterParser(VDMParser::class.java)
        assertFalse(instance.hasParser("VDM"))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceFactory.registerParser]
     * .
     */
    @Test
    fun testRegisterInvalidParser() {
        try {
            instance.registerParser("BAR", BARParser::class.java)
            fail("did not throw exception")
        } catch (iae: IllegalArgumentException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceFactory.registerParser]
     * .
     */
    @Test
    fun testUnregisterParser() {
        instance.registerParser("FOO", FOOParser::class.java)
        assertTrue(instance.hasParser("FOO"))
        instance.unregisterParser(FOOParser::class.java)
        assertFalse(instance.hasParser("FOO"))
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceFactory.hasParser]
     * .
     */
    @Test
    fun testHasParser() {
        assertTrue(instance.hasParser("GLL"))
        assertFalse(instance.hasParser("ABC"))
    }

    /**
     * Test method for
     * [ .][net.sf.marineapi.nmea.parser.SentenceFactory.createParser]
     */
    @Test
    fun testListParsers() {
        val types = instance.listParsers()
        assertEquals(SentenceId.values().size, types.size)
        for (id in SentenceId.values()) {
            assertTrue(types.contains(id.name))
        }
    }

    /**
     * Test method for
     * [net.sf.marineapi.nmea.parser.SentenceFactory.getInstance].
     */
    @Test
    fun testGetInstance() {
        assertNotNull(instance)
        assertTrue(instance == SentenceFactory.getInstance())
        assertEquals(instance, SentenceFactory.getInstance())
    }
}