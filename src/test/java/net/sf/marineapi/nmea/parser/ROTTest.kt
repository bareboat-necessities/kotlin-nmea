package net.sf.marineapi.nmea.parser

import org.junit.Assert.assertEquals

/**
 * Created by SJK on 22/01/14.
 */
class ROTTest {
    var rot: ROTSentence? = null
    var irot: ROTSentence? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        rot = ROTParser(EXAMPLE)
        irot = ROTParser(INVALID_EXAMPLE)
    }

    @Test
    fun testConstructor() {
        val empty: ROTSentence = ROTParser(TalkerId.HE)
        assertEquals(TalkerId.HE, empty.talkerId)
        assertEquals(SentenceId.ROT.toString(), empty.sentenceId)
        try {
            empty.rateOfTurn
        } catch (e: DataNotAvailableException) {
            // pass
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    @Test
    fun testGetStatus() {
        assertEquals(DataStatus.ACTIVE, rot.status)
        assertEquals(DataStatus.VOID, irot.status)
    }

    @Test
    fun testSetStatus() {
        rot.status = DataStatus.VOID
        assertEquals(DataStatus.VOID, rot.status)
    }

    @Test
    fun testGetRateOfTurn() {
        val value: Double = rot.rateOfTurn
        assertEquals(-0.3, value, 0.1)
    }

    @Test
    fun testSetRateOfTurn() {
        val newValue = 0.5
        rot.rateOfTurn = newValue
        assertEquals(newValue, rot.rateOfTurn, 0.1)
    }

    @Test
    fun testSetRateOfTurnNegative() {
        val newValue = -12.3
        rot.rateOfTurn = newValue
        assertEquals(newValue, rot.rateOfTurn, 0.1)
    }

    companion object {
        const val EXAMPLE = "\$HCROT,-0.3,A"
        const val INVALID_EXAMPLE = "\$HCROT,-0.3,V"
    }
}