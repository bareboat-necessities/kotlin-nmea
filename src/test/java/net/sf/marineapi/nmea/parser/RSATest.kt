package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.util.Side
import org.junit.Assert.assertEquals

class RSATest {
    var empty: RSASentence? = null
    var instance: RSASentence? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        empty = RSAParser(TalkerId.II)
        instance = RSAParser(EXAMPLE)
    }

    @Test
    fun testRSAParserString() {
        assertEquals(TalkerId.II, instance.talkerId)
        assertEquals(SentenceId.RSA.name, instance.sentenceId)
    }

    @Test
    fun testRSAParserTalkerId() {
        assertEquals(TalkerId.II, empty.talkerId)
        assertEquals(SentenceId.RSA.name, empty.sentenceId)
        assertEquals(DataStatus.VOID, empty.getStatus(Side.STARBOARD))
        assertEquals(DataStatus.VOID, empty.getStatus(Side.PORT))
    }

    @Test
    fun testGetRudderAngle() {
        assertEquals(1.2, instance.getRudderAngle(Side.STARBOARD), 0.1)
        assertEquals(2.3, instance.getRudderAngle(Side.PORT), 0.1)
    }

    @Test
    fun testSetRudderAngle() {
        val portAngle = 4.5
        val starboardAngle = 5.6
        instance.setRudderAngle(Side.PORT, portAngle)
        instance.setRudderAngle(Side.STARBOARD, starboardAngle)
        assertEquals(portAngle, instance.getRudderAngle(Side.PORT), 0.1)
        assertEquals(starboardAngle, instance.getRudderAngle(Side.STARBOARD), 0.1)
    }

    @Test
    fun testGetStatus() {
        assertEquals(DataStatus.ACTIVE, instance.getStatus(Side.STARBOARD))
        assertEquals(DataStatus.VOID, instance.getStatus(Side.PORT))
    }

    @Test
    fun testSetStatusPort() {
        empty.setStatus(Side.PORT, DataStatus.ACTIVE)
        assertEquals(DataStatus.ACTIVE, empty.getStatus(Side.PORT))
    }

    @Test
    fun testSetStatusStarboard() {
        empty.setStatus(Side.STARBOARD, DataStatus.ACTIVE)
        assertEquals(DataStatus.ACTIVE, empty.getStatus(Side.STARBOARD))
    }

    companion object {
        const val EXAMPLE = "\$IIRSA,1.2,A,2.3,V"
    }
}