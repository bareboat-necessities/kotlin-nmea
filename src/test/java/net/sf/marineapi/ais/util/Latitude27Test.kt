package net.sf.marineapi.ais.util

import org.junit.Assert
import org.junit.Test

class Latitude27Test {
    @Test
    fun zeroIsAvailable() {
        Assert.assertTrue(Latitude27.isAvailable(0))
    }

    @Test
    fun zeroIsCorrect() {
        Assert.assertTrue(Latitude27.isCorrect(0))
    }

    @Test
    fun minValueIsAvailable() {
        Assert.assertTrue(Latitude27.isAvailable(-90 * 60 * 10000))
    }

    @Test
    fun minValueIsCorrect() {
        Assert.assertTrue(Latitude27.isCorrect(-90 * 60 * 10000))
    }

    @Test
    fun maxValueIsAvailable() {
        Assert.assertTrue(Latitude27.isAvailable(90 * 60 * 10000))
    }

    @Test
    fun maxValueIsCorrect() {
        Assert.assertTrue(Latitude27.isCorrect(90 * 60 * 10000))
    }

    @Test
    fun defaultValueIsNotAvailable() {
        Assert.assertFalse(Latitude27.isAvailable(91 * 60 * 10000))
    }

    @Test
    fun defaultValueIsCorrect() {
        Assert.assertTrue(Latitude27.isCorrect(91 * 60 * 10000))
    }

    @Test
    fun largeNegativeValueIsNotAvailable() {
        Assert.assertFalse(Latitude27.isAvailable(-1 - 90 * 60 * 10000))
    }

    @Test
    fun largeNegativeValueIsNotCorrect() {
        Assert.assertFalse(Latitude27.isCorrect(-1 - 90 * 60 * 10000))
    }

    @Test
    fun largeValueIsNotAvailable() {
        Assert.assertFalse(Latitude27.isAvailable(1 + 90 * 60 * 10000))
    }

    @Test
    fun largeValueIsNotCorrect() {
        Assert.assertFalse(Latitude27.isCorrect(1 + 90 * 60 * 10000))
    }

    @Test
    fun conversionToKnotsWorks() {
        Assert.assertEquals(-90.0, Latitude27.toDegrees(java.lang.Double.valueOf(-90.0 * 60 * 10000).toInt()), DELTA)
        Assert.assertEquals(-45.1, Latitude27.toDegrees(java.lang.Double.valueOf(-45.1 * 60 * 10000).toInt()), DELTA)
        Assert.assertEquals(0.0, Latitude27.toDegrees(0), 0.00001)
        Assert.assertEquals(45.9, Latitude27.toDegrees(java.lang.Double.valueOf(45.9 * 60 * 10000).toInt()), DELTA)
        Assert.assertEquals(90.0, Latitude27.toDegrees(java.lang.Double.valueOf(90.0 * 60 * 10000).toInt()), DELTA)
    }

    @Test
    fun conversionReturnsOnInvalidValues() {
        Assert.assertEquals(-101.1, Latitude27.toDegrees(java.lang.Double.valueOf(-101.1 * 60 * 10000).toInt()), DELTA)
        Assert.assertEquals(91.1, Latitude27.toDegrees(java.lang.Double.valueOf(91.1 * 60 * 10000).toInt()), DELTA)
        Assert.assertEquals(102.3, Latitude27.toDegrees(java.lang.Double.valueOf(102.3 * 60 * 10000).toInt()), DELTA)
    }

    companion object {
        private const val DELTA = 0.00001
    }
}