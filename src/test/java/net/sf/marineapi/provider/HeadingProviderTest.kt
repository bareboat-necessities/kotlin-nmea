package net.sf.marineapi.provider

import org.junit.Assert.assertEquals
import java.io.File

/**
 * @author Kimmo Tuukkanen
 */
class HeadingProviderTest : HeadingListener {
    private var factory: SentenceFactory? = null
    private var instance: HeadingProvider? = null
    private var event: HeadingEvent? = null

    /**
     * @throws java.lang.Exception
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
        factory = SentenceFactory.getInstance()
        val file = File("target/test-classes/data/sample1.txt")
        val str = FileInputStream(file)
        val r = SentenceReader(str)
        instance = HeadingProvider(r)
        instance!!.addListener(this)
        event = null
    }

    @After
    fun tearDown() {
        instance!!.removeListener(this)
    }

    /**
     * Test method for
     * [net.sf.marineapi.provider.AbstractProvider.sentenceRead]
     * .
     */
    @Test
    fun testHDMSentenceRead() {
        val s: Sentence = factory.createParser(HDMTest.Companion.EXAMPLE)
        assertNull(event)
        instance!!.sentenceRead(SentenceEvent(this, s))
        assertNotNull(event)
        assertEquals(90.0, event.getHeading(), 0.1)
        assertFalse(event.isTrue())
    }

    /**
     * Test method for
     * [net.sf.marineapi.provider.AbstractProvider.sentenceRead]
     * .
     */
    @Test
    fun testHDTSentenceRead() {
        val s: Sentence = factory.createParser(HDTTest.Companion.EXAMPLE)
        assertNull(event)
        instance!!.sentenceRead(SentenceEvent(this, s))
        assertNotNull(event)
        assertEquals(90.1, event.getHeading(), 0.1)
        assertTrue(event.isTrue())
    }

    /**
     * Test method for
     * [net.sf.marineapi.provider.AbstractProvider.sentenceRead]
     * .
     */
    @Test
    fun testHDGSentenceRead() {
        val s: Sentence = factory.createParser(HDGTest.Companion.EXAMPLE)
        assertNull(event)
        instance!!.sentenceRead(SentenceEvent(this, s))
        assertNotNull(event)
        assertEquals(123.4, event.getHeading(), 0.1)
        assertFalse(event.isTrue())
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.provider.event.HeadingListener#providerUpdate(net.sf
	 * .marineapi.provider.event.HeadingEvent)
	 */
    override fun providerUpdate(evt: HeadingEvent) {
        event = evt
    }
}