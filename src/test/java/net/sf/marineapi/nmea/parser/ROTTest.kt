package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.ROTSentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.util.DataStatus
import org.junit.Assert
import org.junit.Before
import org.junit.Test

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
        Assert.assertEquals(TalkerId.HE, empty.getTalkerId())
        Assert.assertEquals(SentenceId.ROT.toString(), empty.getSentenceId())
        try {
            empty.getRateOfTurn()
        } catch (e: DataNotAvailableException) {
            // pass
        } catch (e: Exception) {
            Assert.fail(e.message)
        }
    }

    @Test
    fun testGetStatus() {
        Assert.assertEquals(DataStatus.ACTIVE, rot!!.getStatus())
        Assert.assertEquals(DataStatus.VOID, irot!!.getStatus())
    }

    @Test
    fun testSetStatus() {
        rot!!.setStatus(DataStatus.VOID)
        Assert.assertEquals(DataStatus.VOID, rot!!.getStatus())
    }

    @Test
    fun testGetRateOfTurn() {
        val value = rot!!.getRateOfTurn()
        Assert.assertEquals(-0.3, value, 0.1)
    }

    @Test
    fun testSetRateOfTurn() {
        val newValue = 0.5
        rot!!.setRateOfTurn(newValue)
        Assert.assertEquals(newValue, rot!!.getRateOfTurn(), 0.1)
    }

    @Test
    fun testSetRateOfTurnNegative() {
        val newValue = -12.3
        rot!!.setRateOfTurn(newValue)
        Assert.assertEquals(newValue, rot!!.getRateOfTurn(), 0.1)
    }

    companion object {
        const val EXAMPLE = "\$HCROT,-0.3,A"
        const val INVALID_EXAMPLE = "\$HCROT,-0.3,V"
    }
}