package net.sf.marineapi.ais.util

import org.junit.Test

class Longitude28Test {
    @Test
    fun zeroIsAvailable() {
        assertTrue(Longitude28.isAvailable(0))
    }

    @Test
    fun zeroIsCorrect() {
        assertTrue(Longitude28.isCorrect(0))
    }

    @Test
    fun minValueIsAvailable() {
        assertTrue(Longitude28.isAvailable(-180 * 60 * 10000))
    }

    @Test
    fun minValueIsCorrect() {
        assertTrue(Longitude28.isCorrect(-180 * 60 * 10000))
    }

    @Test
    fun maxValueIsAvailable() {
        assertTrue(Longitude28.isAvailable(180 * 60 * 10000))
    }

    @Test
    fun maxValueIsCorrect() {
        assertTrue(Longitude28.isCorrect(180 * 60 * 10000))
    }

    @Test
    fun defaultValueIsNotAvailable() {
        assertFalse(Longitude28.isAvailable(181 * 60 * 10000))
    }

    @Test
    fun defaultValueIsCorrect() {
        assertTrue(Longitude28.isCorrect(181 * 60 * 10000))
    }

    @Test
    fun largeNegativeValueIsNotAvailable() {
        assertFalse(Longitude28.isAvailable(-1 - 180 * 60 * 10000))
    }

    @Test
    fun largeNegativeValueIsNotCorrect() {
        assertFalse(Longitude28.isCorrect(-1 - 180 * 60 * 10000))
    }

    @Test
    fun largeValueIsNotAvailable() {
        assertFalse(Longitude28.isAvailable(1 + 180 * 60 * 10000))
    }

    @Test
    fun largeValueIsNotCorrect() {
        assertFalse(Longitude28.isCorrect(1 + 180 * 60 * 10000))
    }

    @Test
    fun conversionToKnotsWorks() {
        assertEquals(-180.0, Longitude28.toDegrees(java.lang.Double.valueOf(-180.0 * 60 * 10000).toInt()), DELTA)
        assertEquals(-45.1, Longitude28.toDegrees(java.lang.Double.valueOf(-45.1 * 60 * 10000).toInt()), DELTA)
        assertEquals(0.0, Longitude28.toDegrees(0), 0.00001)
        assertEquals(45.9, Longitude28.toDegrees(java.lang.Double.valueOf(45.9 * 60 * 10000).toInt()), DELTA)
        assertEquals(180.0, Longitude28.toDegrees(java.lang.Double.valueOf(180.0 * 60 * 10000).toInt()), DELTA)
    }

    @Test
    fun conversionReturnsOnInvalidValues() {
        assertEquals(-201.1, Longitude28.toDegrees(java.lang.Double.valueOf(-201.1 * 60 * 10000).toInt()), DELTA)
        assertEquals(181.1, Longitude28.toDegrees(java.lang.Double.valueOf(181.1 * 60 * 10000).toInt()), DELTA)
        assertEquals(202.3, Longitude28.toDegrees(java.lang.Double.valueOf(202.3 * 60 * 10000).toInt()), DELTA)
    }

    companion object {
        private const val DELTA = 0.00001
    }
}