package net.sf.marineapi.provider

import net.sf.marineapi.nmea.event.SentenceEvent
import net.sf.marineapi.nmea.io.SentenceReader
import net.sf.marineapi.nmea.parser.HDGTest
import net.sf.marineapi.nmea.parser.HDMTest
import net.sf.marineapi.nmea.parser.HDTTest
import net.sf.marineapi.nmea.parser.SentenceFactory
import net.sf.marineapi.provider.event.HeadingEvent
import net.sf.marineapi.provider.event.HeadingListener
import net.sf.marineapi.provider.event.ProviderListener
import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.File
import java.io.FileInputStream

/**
 * @author Kimmo Tuukkanen
 */
class HeadingProviderTest : HeadingListener {
    private var factory: SentenceFactory? = null
    private var instance: HeadingProvider? = null
    private var event: HeadingEvent? = null

    /**
     * @throws Exception
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
        factory = SentenceFactory.instance
        val file = File("build/resources/test/data/sample1.txt")
        val str = FileInputStream(file)
        val r = SentenceReader(str)
        instance = HeadingProvider(r)
        instance!!.addListener(this as ProviderListener<HeadingEvent?>)
        event = null
    }

    @After
    fun tearDown() {
        instance!!.removeListener(this as ProviderListener<HeadingEvent?>)
    }

    /**
     * Test method for
     * [AbstractProvider.sentenceRead]
     * .
     */
    @Test
    fun testHDMSentenceRead() {
        val s = factory!!.createParser(HDMTest.EXAMPLE)
        assertNull(event)
        instance!!.sentenceRead(SentenceEvent(this, s))
        assertNotNull(event)
        assertEquals(90.0, event!!.getHeading(), 0.1)
        assertFalse(event!!.isTrue())
    }

    /**
     * Test method for
     * [AbstractProvider.sentenceRead]
     * .
     */
    @Test
    fun testHDTSentenceRead() {
        val s = factory!!.createParser(HDTTest.EXAMPLE)
        assertNull(event)
        instance!!.sentenceRead(SentenceEvent(this, s))
        assertNotNull(event)
        assertEquals(90.1, event!!.getHeading(), 0.1)
        assertTrue(event!!.isTrue())
    }

    /**
     * Test method for
     * [AbstractProvider.sentenceRead]
     * .
     */
    @Test
    fun testHDGSentenceRead() {
        val s = factory!!.createParser(HDGTest.EXAMPLE)
        assertNull(event)
        instance!!.sentenceRead(SentenceEvent(this, s))
        assertNotNull(event)
        assertEquals(123.4, event!!.getHeading(), 0.1)
        assertFalse(event!!.isTrue())
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