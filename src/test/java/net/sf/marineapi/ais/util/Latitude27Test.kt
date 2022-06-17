package net.sf.marineapi.ais.util

import org.junit.Test

class Latitude27Test {
    @Test
    fun zeroIsAvailable() {
        assertTrue(Latitude27.isAvailable(0))
    }

    @Test
    fun zeroIsCorrect() {
        assertTrue(Latitude27.isCorrect(0))
    }

    @Test
    fun minValueIsAvailable() {
        assertTrue(Latitude27.isAvailable(-90 * 60 * 10000))
    }

    @Test
    fun minValueIsCorrect() {
        assertTrue(Latitude27.isCorrect(-90 * 60 * 10000))
    }

    @Test
    fun maxValueIsAvailable() {
        assertTrue(Latitude27.isAvailable(90 * 60 * 10000))
    }

    @Test
    fun maxValueIsCorrect() {
        assertTrue(Latitude27.isCorrect(90 * 60 * 10000))
    }

    @Test
    fun defaultValueIsNotAvailable() {
        assertFalse(Latitude27.isAvailable(91 * 60 * 10000))
    }

    @Test
    fun defaultValueIsCorrect() {
        assertTrue(Latitude27.isCorrect(91 * 60 * 10000))
    }

    @Test
    fun largeNegativeValueIsNotAvailable() {
        assertFalse(Latitude27.isAvailable(-1 - 90 * 60 * 10000))
    }

    @Test
    fun largeNegativeValueIsNotCorrect() {
        assertFalse(Latitude27.isCorrect(-1 - 90 * 60 * 10000))
    }

    @Test
    fun largeValueIsNotAvailable() {
        assertFalse(Latitude27.isAvailable(1 + 90 * 60 * 10000))
    }

    @Test
    fun largeValueIsNotCorrect() {
        assertFalse(Latitude27.isCorrect(1 + 90 * 60 * 10000))
    }

    @Test
    fun conversionToKnotsWorks() {
        assertEquals(-90.0, Latitude27.toDegrees(java.lang.Double.valueOf(-90.0 * 60 * 10000).toInt()), DELTA)
        assertEquals(-45.1, Latitude27.toDegrees(java.lang.Double.valueOf(-45.1 * 60 * 10000).toInt()), DELTA)
        assertEquals(0.0, Latitude27.toDegrees(0), 0.00001)
        assertEquals(45.9, Latitude27.toDegrees(java.lang.Double.valueOf(45.9 * 60 * 10000).toInt()), DELTA)
        assertEquals(90.0, Latitude27.toDegrees(java.lang.Double.valueOf(90.0 * 60 * 10000).toInt()), DELTA)
    }

    @Test
    fun conversionReturnsOnInvalidValues() {
        assertEquals(-101.1, Latitude27.toDegrees(java.lang.Double.valueOf(-101.1 * 60 * 10000).toInt()), DELTA)
        assertEquals(91.1, Latitude27.toDegrees(java.lang.Double.valueOf(91.1 * 60 * 10000).toInt()), DELTA)
        assertEquals(102.3, Latitude27.toDegrees(java.lang.Double.valueOf(102.3 * 60 * 10000).toInt()), DELTA)
    }

    companion object {
        private const val DELTA = 0.00001
    }
}