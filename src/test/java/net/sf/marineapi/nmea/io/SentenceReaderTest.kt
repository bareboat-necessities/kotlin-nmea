package net.sf.marineapi.nmea.io

import net.sf.marineapi.nmea.event.AbstractSentenceListener
import net.sf.marineapi.nmea.event.SentenceEvent
import net.sf.marineapi.nmea.event.SentenceListener
import net.sf.marineapi.nmea.parser.BODTest
import net.sf.marineapi.nmea.parser.GGATest
import net.sf.marineapi.nmea.parser.SentenceFactory.Companion.instance
import net.sf.marineapi.nmea.parser.TXTTest
import net.sf.marineapi.nmea.sentence.Sentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TXTSentence
import net.sf.marineapi.test.util.UDPServerMock
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
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

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val file = File(TEST_DATA)
        val stream = FileInputStream(file)
        reader = SentenceReader(stream)
        dummyListener = DummySentenceListener()
        testListener = TestSentenceListener()
        reader!!.addSentenceListener(dummyListener!!)
        reader!!.addSentenceListener(testListener!!, SentenceId.GGA)
    }

    @Test
    @Throws(Throwable::class)
    fun testConstructorWithCustomReader() {
        val reader = SentenceReader(DummyDataReader(TXTTest.EXAMPLE))
        reader.addSentenceListener(TestSentenceListener())
        reader.start()
        Thread.sleep(200)
        reader.stop()
        Assert.assertEquals(sentence.toString(), TXTTest.EXAMPLE)
    }

    @Test
    fun testAddSentenceListenerSentenceListenerString() {
        val dummy = DummySentenceListener()
        reader!!.addSentenceListener(dummy, "GLL")
    }

    @Test
    fun testGetPauseTimeout() {
        Assert.assertEquals(SentenceReader.DEFAULT_TIMEOUT.toLong(), reader!!.pauseTimeout.toLong())
    }

    @Test
    fun testRemoveSentenceListener() {
        Assert.assertFalse(started)
        reader!!.removeSentenceListener(testListener!!)
        reader!!.fireReadingStarted()
        Assert.assertFalse(started)
    }

    @Test
    fun testRemoveSentenceListenerByType() {
        reader!!.removeSentenceListener(testListener!!)
        reader!!.removeSentenceListener(dummyListener!!)
        Assert.assertEquals(0, reader!!.sentenceListeners.size.toLong())
        reader!!.addSentenceListener(testListener!!, SentenceId.GLL)
        reader!!.addSentenceListener(dummyListener!!, SentenceId.GGA)
        Assert.assertEquals(2, reader!!.sentenceListeners.size.toLong())
        reader!!.removeSentenceListener(testListener!!, SentenceId.GLL)
        Assert.assertEquals(1, reader!!.sentenceListeners.size.toLong())
        reader!!.removeSentenceListener(dummyListener!!, SentenceId.GNS)
        Assert.assertEquals(1, reader!!.sentenceListeners.size.toLong())
        reader!!.removeSentenceListener(dummyListener!!, SentenceId.GGA)
        Assert.assertEquals(0, reader!!.sentenceListeners.size.toLong())
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
        val host = InetAddress.getLocalHost()
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
        Assert.assertFalse(received.isEmpty())
        Assert.assertEquals(server.TXT, received[0].toString())
    }

    @Test
    fun testSetPauseTimeout() {
        val timeout = 2500
        reader!!.pauseTimeout = timeout
        Assert.assertEquals(timeout.toLong(), reader!!.pauseTimeout.toLong())
    }

    @Test
    fun testFireReadingPaused() {
        Assert.assertFalse(paused)
        reader!!.fireReadingPaused()
        Assert.assertTrue(paused)
    }

    @Test
    fun testFireReadingStarted() {
        Assert.assertFalse(started)
        reader!!.fireReadingStarted()
        Assert.assertTrue(started)
    }

    @Test
    fun testFireReadingStopped() {
        Assert.assertFalse(stopped)
        reader!!.fireReadingStopped()
        Assert.assertTrue(stopped)
    }

    @Test
    fun testFireSentenceEventWithExpectedType() {
        Assert.assertNull(sentence)
        val sf = instance
        val s = sf.createParser(GGATest.EXAMPLE)
        reader!!.fireSentenceEvent(s)
        Assert.assertEquals(s, sentence)
    }

    @Test
    fun testFireSentenceEventWithUnexpectedType() {
        Assert.assertNull(sentence)
        val sf = instance
        val s = sf.createParser(BODTest.EXAMPLE)
        reader!!.fireSentenceEvent(s)
        Assert.assertNull(sentence)
    }

    @Test
    fun testStartAndStop() {
        try {
            Assert.assertNull(sentence)
            Assert.assertFalse(started)
            Assert.assertFalse(paused)
            Assert.assertFalse(stopped)
            reader!!.start()
            Thread.sleep(500)
            Assert.assertNotNull(sentence)
            Assert.assertTrue(started)
            Assert.assertFalse(paused)
            reader!!.stop()
            Thread.sleep(100)
            Assert.assertFalse(paused)
            Assert.assertTrue(stopped)
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    @Test
    fun testHandleException() {
        val ERR_MSG = "test error"
        reader!!.exceptionListener = object : ExceptionListener {
            private var calls = 0
            override fun onException(e: Exception?) {
                Assert.assertEquals(calls++.toLong(), 0)
                Assert.assertTrue(e is IllegalStateException)
                Assert.assertEquals(ERR_MSG, e!!.message)
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
                Assert.assertEquals(expected, data)
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