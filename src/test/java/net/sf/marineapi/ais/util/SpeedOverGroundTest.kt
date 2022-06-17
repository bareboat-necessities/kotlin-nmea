package net.sf.marineapi.ais.util

import net.sf.marineapi.ais.util.SpeedOverGround.toKnots
import org.junit.Assert
import org.junit.Test

class SpeedOverGroundTest {
    @Test
    fun minValueIsAvailable() {
        Assert.assertTrue(SpeedOverGround.isAvailable(0))
    }

    @Test
    fun minValueIsCorrect() {
        Assert.assertTrue(SpeedOverGround.isCorrect(0))
    }

    @Test
    fun maxValueIsAvailable() {
        Assert.assertTrue(SpeedOverGround.isAvailable(1022))
    }

    @Test
    fun maxValueIsCorrect() {
        Assert.assertTrue(SpeedOverGround.isCorrect(1022))
    }

    @Test
    fun defaultValueIsNotAvailable() {
        Assert.assertFalse(SpeedOverGround.isAvailable(1023))
    }

    @Test
    fun defaultValueIsCorrect() {
        Assert.assertTrue(SpeedOverGround.isCorrect(1023))
    }

    @Test
    fun negativeValueIsNotAvailable() {
        Assert.assertFalse(SpeedOverGround.isAvailable(-1))
    }

    @Test
    fun negativeValueIsNotCorrect() {
        Assert.assertFalse(SpeedOverGround.isCorrect(-1))
    }

    @Test
    fun largeValueIsNotAvailable() {
        Assert.assertFalse(SpeedOverGround.isAvailable(1100))
    }

    @Test
    fun largeValueIsNotCorrect() {
        Assert.assertFalse(SpeedOverGround.isCorrect(1100))
    }

    @Test
    fun conversionToKnotsWorks() {
        Assert.assertEquals(0.0, toKnots(0), DELTA)
        Assert.assertEquals(0.1, toKnots(1), DELTA)
        Assert.assertEquals(90.9, toKnots(909), DELTA)
        Assert.assertEquals(102.2, toKnots(1022), DELTA)
    }

    @Test
    fun conversionReturnsOnInvalidValues() {
        Assert.assertEquals(-10.1, toKnots(-101), DELTA)
        Assert.assertEquals(102.3, toKnots(1023), DELTA)
        Assert.assertEquals(4567.8, toKnots(45678), DELTA)
    }

    companion object {
        private const val DELTA = 0.001
    }
}