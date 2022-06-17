package net.sf.marineapi.ais.event

import net.sf.marineapi.ais.message.AISMessage
import net.sf.marineapi.ais.message.AISMessage01
import net.sf.marineapi.ais.message.AISMessage05
import net.sf.marineapi.ais.parser.AISMessageFactory
import net.sf.marineapi.nmea.parser.SentenceFactory
import net.sf.marineapi.nmea.sentence.AISSentence
import org.junit.Test
import junit.framework.TestCase.*

class AbstractAISMessageListenerTest {
    private val sf = SentenceFactory.getInstance()
    private val mf = AISMessageFactory.instance
    private val AIS_01 = sf.createParser("!AIVDM,1,1,,A,13aEOK?P00PD2wVMdLDRhgvL289?,0*26") as AISSentence
    private val MSG_01 = mf.create(AIS_01) as AISMessage01
    private val AIS_05_1 =
        sf.createParser("!AIVDM,2,1,3,B,55P5TL01VIaAL@7WKO@mBplU@<PDhh000000001S;AJ::4A80?4i@E53,0*3E") as AISSentence
    private val AIS_05_2 = sf.createParser("!AIVDM,2,2,3,B,1@0000000000000,2*55") as AISSentence
    private val MSG_05 = mf.create(AIS_05_1, AIS_05_2) as AISMessage05

    @Test
    fun testConstructor() {
        val bl: BasicListener = BasicListener()
        assertNull(bl.received)
        assertEquals(bl.messageType, AISMessage01::class.java)
    }

    @Test
    fun testParametrizedConstructor() {
        val ebl: ExtendedBasicListener = ExtendedBasicListener()
        assertNull(ebl.get())
        assertEquals(ebl.messageType, AISMessage01::class.java)
    }

    @Test
    fun testOnMessageWithExpectedMessage() {
        val bl: BasicListener = BasicListener()
        bl.sentenceRead(AIS_01)
        assertEquals(bl.received.toString(), MSG_01.toString())
    }

    @Test
    fun testSequenceListener() {
        val sl = SequenceListener()
        sl.sentenceRead(AIS_05_1)
        assertNull(sl.received)
        sl.sentenceRead(AIS_05_2)
        assertEquals(sl.received.toString(), MSG_05.toString())
    }

    @Test
    fun testSequenceListenerWithIncorrectOrder() {
        val sl = SequenceListener()
        sl.sentenceRead(AIS_05_2)
        assertNull(sl.received)
        sl.sentenceRead(AIS_05_1)
        assertNull(sl.received)
        sl.sentenceRead(AIS_05_2)
        assertEquals(sl.received.toString(), MSG_05.toString())
    }

    @Test
    fun testSequenceListenerWithMixedOrder() {
        val sl = SequenceListener()
        sl.sentenceRead(AIS_05_1)
        assertNull(sl.received)
        sl.sentenceRead(AIS_01)
        assertNull(sl.received)
        sl.sentenceRead(AIS_05_2)
        assertNull(sl.received)
    }

    @Test
    fun testBasicListenerWithUnexpectedMessage() {
        val bl: BasicListener = BasicListener()
        bl.sentenceRead(AIS_05_1)
        bl.sentenceRead(AIS_05_2)
        assertNull(bl.received)
    }

    @Test
    fun testGenericsListener() {
        val gl: GenericsListener<Int, AISMessage01> = GenericsListener<Int, AISMessage01>(
            AISMessage01::class.java
        )
        gl.sentenceRead(AIS_01)
        assertEquals(gl.received.toString(), MSG_01.toString())
        assertEquals("1", gl.dummy(1))
    }

    @Test
    fun testGenericsListenerDefaultConstructorThrows() {
        try {
            val gl: GenericsListener<Int, AISMessage01> = GenericsListener<Int, AISMessage01>()
            fail("exception not thrown, resolved to " + gl.messageType)
        } catch (ise: IllegalStateException) {
            assertEquals("Cannot resolve generic type <T>, use constructor with Class<T> param.", ise.message)
        } catch (e: Exception) {
            fail("unexpected exception thrown: " + e.message)
        }
    }

    /** Listeners  */
    internal open inner class BasicListener : AbstractAISMessageListener<AISMessage01?>() {
        var received: AISMessage01? = null
        override fun onMessage(msg: AISMessage01?) {
            received = msg
        }
    }

    internal inner class ExtendedBasicListener : BasicListener() {
        fun get(): AISMessage01? {
            return super.received
        }
    }

    internal inner class SequenceListener : AbstractAISMessageListener<AISMessage05?>() {
        var received: AISMessage05? = null
        override fun onMessage(msg: AISMessage05?) {
            received = msg
        }
    }

    internal inner class GenericsListener<A, B : AISMessage?> : AbstractAISMessageListener<B> {
        var received: B? = null

        constructor() {}
        constructor(c: Class<B>?) : super(c) {}

        fun dummy(obj: A): String {
            return obj.toString()
        }

        override fun onMessage(msg: B?) {
            received = msg
        }
    }
}