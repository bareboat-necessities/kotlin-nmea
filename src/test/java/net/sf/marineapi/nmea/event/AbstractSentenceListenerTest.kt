package net.sf.marineapi.nmea.event

import net.sf.marineapi.nmea.parser.BODTest
import net.sf.marineapi.nmea.parser.GGATest
import net.sf.marineapi.nmea.parser.SentenceFactory.Companion.instance
import net.sf.marineapi.nmea.sentence.BODSentence
import net.sf.marineapi.nmea.sentence.GGASentence
import net.sf.marineapi.nmea.sentence.Sentence
import org.junit.Assert
import org.junit.Test

class AbstractSentenceListenerTest {
    private val factory = instance
    private val BOD = factory.createParser(BODTest.EXAMPLE)
    private val GGA = factory.createParser(GGATest.EXAMPLE)
    private val BOD_EVENT = SentenceEvent(this, BOD)
    private val GGA_EVENT = SentenceEvent(this, GGA)
    @Test
    fun testDefaultConstructor() {
        val bl = BasicListener()
        Assert.assertEquals(BODSentence::class.java, bl.sentenceType)
    }

    @Test
    fun testDefaultConstructorWhenExtended() {
        val ebl = ExtendedBasicListener()
        Assert.assertEquals(BODSentence::class.java, ebl.sentenceType)
    }

    @Test
    fun testParametrizedConstructor() {
        val gl: GenericsListener<String, BODSentence> = GenericsListener(
            BODSentence::class.java
        )
        Assert.assertEquals(BODSentence::class.java, gl.sentenceType)
    }

    @Test
    fun testParametrizedConstructorWhenExtended() {
        val gl = ExtendedGenericsListener<String, Int, BODSentence>(
            BODSentence::class.java
        )
        Assert.assertEquals(BODSentence::class.java, gl.sentenceType)
    }

    @Test
    fun testDefaultConstructorThrows() {
        try {
            val gl: GenericsListener<String, BODSentence> = GenericsListener()
            Assert.fail("default constructor didn't throw, resolved to " + gl.sentenceType)
        } catch (ise: IllegalStateException) {
            val msg = "Cannot resolve generic type <T>, use constructor with Class<T> param."
            Assert.assertEquals(msg, ise.message)
        } catch (e: Exception) {
            Assert.fail("unexpected exception was thrown: " + e.message)
        }
    }

    @Test
    fun testDefaultConstructorThrowsWhenExtended() {
        try {
            val egl = ExtendedGenericsListener<String, Int, GGASentence>()
            Assert.fail("default constructor didn't throw, resolved to " + egl.sentenceType)
        } catch (ise: IllegalStateException) {
            val msg = "Cannot resolve generic type <T>, use constructor with Class<T> param."
            Assert.assertEquals(msg, ise.message)
        } catch (e: Exception) {
            Assert.fail("unexpected exception was thrown: " + e.message)
        }
    }

    @Test
    fun testBasicListenerWithExpectedSentence() {
        val bl: BasicListener = BasicListener()
        bl.sentenceRead(BOD_EVENT)
        Assert.assertNotNull(bl.received)
        Assert.assertEquals(BOD!!.toSentence(), bl.received!!.toSentence())
    }

    @Test
    fun testBasicListenerWithOtherSentence() {
        val bl: BasicListener = BasicListener()
        bl.sentenceRead(GGA_EVENT)
        Assert.assertNull(bl.received)
    }

    @Test
    fun testExtendedBasicListenerWithExpectedSentence() {
        val ebl: ExtendedBasicListener = ExtendedBasicListener()
        ebl.sentenceRead(BOD_EVENT)
        Assert.assertNotNull(ebl.received)
        Assert.assertEquals(BOD!!.toSentence(), ebl.received!!.toSentence())
    }

    @Test
    fun testExtendedBasicListenerWithUnexpectedSentence() {
        val ebl: ExtendedBasicListener = ExtendedBasicListener()
        ebl.sentenceRead(GGA_EVENT)
        Assert.assertNull(ebl.received)
    }

    @Test
    fun testGenericsListenerWithExpectedSentence() {
        val gl: GenericsListener<Int, GGASentence> = GenericsListener(
            GGASentence::class.java
        )
        gl.sentenceRead(GGA_EVENT)
        Assert.assertNotNull(gl.received)
        Assert.assertEquals(GGA!!.toSentence(), gl.received!!.toSentence())
        Assert.assertEquals("1", gl.stringify(1))
    }

    @Test
    fun testGenericsListenerWithUnexpectedSentence() {
        val sl: GenericsListener<Int, GGASentence> = GenericsListener(
            GGASentence::class.java
        )
        sl.sentenceRead(BOD_EVENT)
        Assert.assertNull(sl.received)
    }

    @Test
    fun testExtendedGenericsListenerWithExpectedSentence() {
        val egl: ExtendedGenericsListener<String, Int, GGASentence> =
            ExtendedGenericsListener(
                GGASentence::class.java
            )
        egl.sentenceRead(GGA_EVENT)
        Assert.assertNotNull(egl.received)
        Assert.assertEquals(GGA!!.toSentence(), egl.received!!.toSentence())
        Assert.assertEquals(3556498, egl.hashify("test").toLong())
        Assert.assertEquals("3", egl.stringify(3))
    }

    @Test
    fun testExtendedGenericsListenerWithUnexpectedSentence() {
        val egl: ExtendedGenericsListener<String, Int, GGASentence> =
            ExtendedGenericsListener(
                GGASentence::class.java
            )
        egl.sentenceRead(BOD_EVENT)
        Assert.assertNull(egl.received)
    }

    @Test
    fun testGenericsHidingListenerWithExpectedSentence() {
        val ghl: GenericsHidingListener<Double> = GenericsHidingListener()
        ghl.sentenceRead(BOD_EVENT)
        Assert.assertNotNull(ghl.received)
        Assert.assertEquals(BOD!!.toSentence(), ghl.received!!.toSentence())
        Assert.assertEquals("4.5", ghl.dummy(4.5))
        Assert.assertEquals("5", ghl.stringify(5))
    }

    @Test
    fun testGenericsHidingListenerWithUnexpectedSentence() {
        val ghl: GenericsHidingListener<Double> = GenericsHidingListener()
        ghl.sentenceRead(GGA_EVENT)
        Assert.assertNull(ghl.received)
    }

    /** Listeners  */
    internal open inner class BasicListener : AbstractSentenceListener<BODSentence?>() {
        open var received: BODSentence? = null
        override fun sentenceRead(sentence: BODSentence?) {
            received = sentence
        }
    }

    internal inner class ExtendedBasicListener : BasicListener() {
        override var received: BODSentence?
            get() = super.received
            set(received) {
                super.received = received
            }
    }

    internal open inner class GenericsListener<A, B : Sentence?> : AbstractSentenceListener<B> {
        open var received: B? = null

        constructor()
        constructor(type: Class<B>?) : super(type)

        fun stringify(obj: A): String {
            return obj.toString()
        }

        override fun sentenceRead(sentence: B?) {
            received = sentence
        }
    }

    internal inner class ExtendedGenericsListener<A, B, C : Sentence?> : GenericsListener<B, C> {
        constructor()
        constructor(type: Class<C>?) : super(type)

        fun hashify(obj: A): Int {
            return obj.hashCode()
        }

        override var received: C?
            get() = super.received
            set(received) {
                super.received = received
            }
    }

    internal inner class GenericsHidingListener<A> : GenericsListener<Int?, BODSentence?>() {
        fun dummy(a: A): String {
            return a.toString()
        }
    }
}