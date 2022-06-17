package net.sf.marineapi.nmea.io

import net.sf.marineapi.nmea.event.SentenceEvent
import net.sf.marineapi.nmea.event.SentenceListener
import org.junit.Assert.assertEquals
import java.io.File
import java.io.InputStream

import junit.framework.TestCase.*
import net.sf.marineapi.nmea.event.AbstractSentenceListener
import net.sf.marineapi.nmea.parser.BODTest
import net.sf.marineapi.nmea.parser.GGATest
import net.sf.marineapi.nmea.parser.SentenceFactory
import net.sf.marineapi.nmea.parser.TXTTest
import net.sf.marineapi.nmea.sentence.Sentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TXTSentence
import net.sf.marineapi.test.util.UDPServerMock
import org.junit.Before
import org.junit.Test
import java.io.FileInputStream
import java.net.DatagramSocket
import java.net.InetAddress

class SentenceReaderTest {
    private var sentence: Sentence? = null
    private var reader: SentenceReader? = null

    private var dummyListener: SentenceListener? = null
    private var testListener: SentenceListener? = null

    private var paused = false
    private var started = false
    private var stopped = false
    private var stream: InputStream? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val file = File(TEST_DATA)
        stream = FileInputStream(file)
        reader = SentenceReader(stream)
        dummyListener = DummySentenceListener()
        testListener = TestSentenceListener()
        reader!!.addSentenceListener(dummyListener as DummySentenceListener)
        reader!!.addSentenceListener(testListener as TestSentenceListener, SentenceId.GGA)
    }

    @Test
    @Throws(Throwable::class)
    fun testConstructorWithCustomReader() {
        val reader = SentenceReader(DummyDataReader(TXTTest.EXAMPLE))
        reader.addSentenceListener(TestSentenceListener())
        reader.start()
        Thread.sleep(200)
        reader.stop()
        assertEquals(sentence.toString(), TXTTest.EXAMPLE)
    }

    @Test
    fun testAddSentenceListenerSentenceListenerString() {
        val dummy = DummySentenceListener()
        reader!!.addSentenceListener(dummy, "GLL")
    }

    @Test
    fun testGetPauseTimeout() {
        assertEquals(SentenceReader.DEFAULT_TIMEOUT, reader!!.pauseTimeout)
    }

    @Test
    fun testRemoveSentenceListener() {
        assertFalse(started)
        reader!!.removeSentenceListener(testListener!!)
        reader!!.fireReadingStarted()
        assertFalse(started)
    }

    @Test
    fun testRemoveSentenceListenerByType() {
        reader!!.removeSentenceListener(testListener!!)
        reader!!.removeSentenceListener(dummyListener!!)
        assertEquals(0, reader!!.sentenceListeners.size)
        reader!!.addSentenceListener(testListener!!, SentenceId.GLL)
        reader!!.addSentenceListener(dummyListener!!, SentenceId.GGA)
        assertEquals(2, reader!!.sentenceListeners.size)
        reader!!.removeSentenceListener(testListener!!, SentenceId.GLL)
        assertEquals(1, reader!!.sentenceListeners.size)
        reader!!.removeSentenceListener(dummyListener!!, SentenceId.GNS)
        assertEquals(1, reader!!.sentenceListeners.size)
        reader!!.removeSentenceListener(dummyListener!!, SentenceId.GGA)
        assertEquals(0, reader!!.sentenceListeners.size)
    }

    @Test
    @Throws(Exception::class)
    fun testSetInputStream() {
        val file = File("src/test/resources/data/Garmin-GPS76.txt")
        val stream: InputStream = FileInputStream(file)
        reader!!.setInputStream(stream)
    }

    @Test
    @Throws(Exception::class)
    fun testSetDatagramSocket() {
        val server = UDPServerMock()
        val received: MutableList<TXTSentence?> = ArrayList()
        val host: InetAddress = InetAddress.getLocalHost()
        val socket = DatagramSocket(3810, host)
        reader!!.setDatagramSocket(socket)
        reader!!.addSentenceListener(object : AbstractSentenceListener<TXTSentence?>() {
            override fun sentenceRead(sentence: TXTSentence?) {
                received.add(sentence)
                if (received.size == 4) {
                    reader!!.stop()
                    server.stop()
                    socket.close()
                }
            }
        })
        reader!!.start()
        Thread.sleep(1000)
        assertFalse(received.isEmpty())
        assertEquals(server.TXT, received[0].toString())
    }

    @Test
    fun testSetPauseTimeout() {
        val timeout = 2500
        reader!!.pauseTimeout = timeout
        assertEquals(timeout, reader!!.pauseTimeout)
    }

    @Test
    fun testFireReadingPaused() {
        assertFalse(paused)
        reader!!.fireReadingPaused()
        assertTrue(paused)
    }

    @Test
    fun testFireReadingStarted() {
        assertFalse(started)
        reader!!.fireReadingStarted()
        assertTrue(started)
    }

    @Test
    fun testFireReadingStopped() {
        assertFalse(stopped)
        reader!!.fireReadingStopped()
        assertTrue(stopped)
    }

    @Test
    fun testFireSentenceEventWithExpectedType() {
        assertNull(sentence)
        val sf = SentenceFactory.getInstance()
        val s = sf.createParser(GGATest.EXAMPLE)
        reader!!.fireSentenceEvent(s)
        assertEquals(s, sentence)
    }

    @Test
    fun testFireSentenceEventWithUnexpectedType() {
        assertNull(sentence)
        val sf = SentenceFactory.getInstance()
        val s = sf.createParser(BODTest.EXAMPLE)
        reader!!.fireSentenceEvent(s)
        assertNull(sentence)
    }

    @Test
    fun testStartAndStop() {
        try {
            assertNull(sentence)
            assertFalse(started)
            assertFalse(paused)
            assertFalse(stopped)
            reader!!.start()
            Thread.sleep(500)
            assertNotNull(sentence)
            assertTrue(started)
            assertFalse(paused)
            reader!!.stop()
            Thread.sleep(100)
            assertFalse(paused)
            assertTrue(stopped)
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    @Test
    fun testHandleException() {
        val ERR_MSG = "test error"
        reader!!.exceptionListener = object : ExceptionListener {
            private var calls = 0
            override fun onException(e: Exception?) {
                assertEquals(calls++, 0)
                assertTrue(e is IllegalStateException)
                assertEquals(ERR_MSG, e!!.message)
            }
        }
        reader!!.handleException("test", IllegalStateException(ERR_MSG))
    }

    @Test
    fun testDataListener() {

        // expected non-NMEA line in TEST_DATA
        val expected = "=~=~=~=~=~=~=~=~=~=~=~= PuTTY log 2010.02.13 13:08:23 =~=~=~=~=~=~=~=~=~=~=~="
        val listener: DataListener = object : DataListener {
            override fun dataRead(data: String?) {
                assertEquals(expected, data)
                reader!!.stop()
            }
        }
        reader!!.setDataListener(listener)
        reader!!.start()
    }

    inner class DummySentenceListener : SentenceListener {
        override fun readingPaused() {}
        override fun readingStarted() {}
        override fun readingStopped() {}
        override fun sentenceRead(event: SentenceEvent) {}
    }

    inner class TestSentenceListener : SentenceListener {
        override fun readingPaused() {
            paused = true
        }

        override fun readingStarted() {
            started = true
        }

        override fun readingStopped() {
            stopped = true
        }

        override fun sentenceRead(event: SentenceEvent) {
            sentence = event.sentence
        }
    }

    // Test "reader" that only repeats the given sentence
    inner class DummyDataReader(private val sentence: String) : AbstractDataReader() {
        @Throws(Exception::class)
        override fun read(): String {
            return this.sentence
        }
    }

    companion object {
        const val TEST_DATA = "src/test/resources/data/Navibe-GM720.txt"
    }
}