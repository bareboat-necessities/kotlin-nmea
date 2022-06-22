package net.sf.marineapi.ais.util

import net.sf.marineapi.ais.util.SpeedOverGround.toKnots
import org.junit.Assert.*
import org.junit.Test

class SpeedOverGroundTest {
    @Test
    fun minValueIsAvailable() {
        assertTrue(SpeedOverGround.isAvailable(0))
    }

    @Test
    fun minValueIsCorrect() {
        assertTrue(SpeedOverGround.isCorrect(0))
    }

    @Test
    fun maxValueIsAvailable() {
        assertTrue(SpeedOverGround.isAvailable(1022))
    }

    @Test
    fun maxValueIsCorrect() {
        assertTrue(SpeedOverGround.isCorrect(1022))
    }

    @Test
    fun defaultValueIsNotAvailable() {
        assertFalse(SpeedOverGround.isAvailable(1023))
    }

    @Test
    fun defaultValueIsCorrect() {
        assertTrue(SpeedOverGround.isCorrect(1023))
    }

    @Test
    fun negativeValueIsNotAvailable() {
        assertFalse(SpeedOverGround.isAvailable(-1))
    }

    @Test
    fun negativeValueIsNotCorrect() {
        assertFalse(SpeedOverGround.isCorrect(-1))
    }

    @Test
    fun largeValueIsNotAvailable() {
        assertFalse(SpeedOverGround.isAvailable(1100))
    }

    @Test
    fun largeValueIsNotCorrect() {
        assertFalse(SpeedOverGround.isCorrect(1100))
    }

    @Test
    fun conversionToKnotsWorks() {
        assertEquals(0.0, toKnots(0), DELTA)
        assertEquals(0.1, toKnots(1), DELTA)
        assertEquals(90.9, toKnots(909), DELTA)
        assertEquals(102.2, toKnots(1022), DELTA)
    }

    @Test
    fun conversionReturnsOnInvalidValues() {
        assertEquals(-10.1, toKnots(-101), DELTA)
        assertEquals(102.3, toKnots(1023), DELTA)
        assertEquals(4567.8, toKnots(45678), DELTA)
    }

    companion object {
        private const val DELTA = 0.001
    }
}