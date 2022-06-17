package net.sf.marineapi.ais.util

import org.junit.Assert
import org.junit.Test

class Longitude28Test {
    @Test
    fun zeroIsAvailable() {
        Assert.assertTrue(Longitude28.isAvailable(0))
    }

    @Test
    fun zeroIsCorrect() {
        Assert.assertTrue(Longitude28.isCorrect(0))
    }

    @Test
    fun minValueIsAvailable() {
        Assert.assertTrue(Longitude28.isAvailable(-180 * 60 * 10000))
    }

    @Test
    fun minValueIsCorrect() {
        Assert.assertTrue(Longitude28.isCorrect(-180 * 60 * 10000))
    }

    @Test
    fun maxValueIsAvailable() {
        Assert.assertTrue(Longitude28.isAvailable(180 * 60 * 10000))
    }

    @Test
    fun maxValueIsCorrect() {
        Assert.assertTrue(Longitude28.isCorrect(180 * 60 * 10000))
    }

    @Test
    fun defaultValueIsNotAvailable() {
        Assert.assertFalse(Longitude28.isAvailable(181 * 60 * 10000))
    }

    @Test
    fun defaultValueIsCorrect() {
        Assert.assertTrue(Longitude28.isCorrect(181 * 60 * 10000))
    }

    @Test
    fun largeNegativeValueIsNotAvailable() {
        Assert.assertFalse(Longitude28.isAvailable(-1 - 180 * 60 * 10000))
    }

    @Test
    fun largeNegativeValueIsNotCorrect() {
        Assert.assertFalse(Longitude28.isCorrect(-1 - 180 * 60 * 10000))
    }

    @Test
    fun largeValueIsNotAvailable() {
        Assert.assertFalse(Longitude28.isAvailable(1 + 180 * 60 * 10000))
    }

    @Test
    fun largeValueIsNotCorrect() {
        Assert.assertFalse(Longitude28.isCorrect(1 + 180 * 60 * 10000))
    }

    @Test
    fun conversionToKnotsWorks() {
        Assert.assertEquals(-180.0, Longitude28.toDegrees(java.lang.Double.valueOf(-180.0 * 60 * 10000).toInt()), DELTA)
        Assert.assertEquals(-45.1, Longitude28.toDegrees(java.lang.Double.valueOf(-45.1 * 60 * 10000).toInt()), DELTA)
        Assert.assertEquals(0.0, Longitude28.toDegrees(0), 0.00001)
        Assert.assertEquals(45.9, Longitude28.toDegrees(java.lang.Double.valueOf(45.9 * 60 * 10000).toInt()), DELTA)
        Assert.assertEquals(180.0, Longitude28.toDegrees(java.lang.Double.valueOf(180.0 * 60 * 10000).toInt()), DELTA)
    }

    @Test
    fun conversionReturnsOnInvalidValues() {
        Assert.assertEquals(-201.1, Longitude28.toDegrees(java.lang.Double.valueOf(-201.1 * 60 * 10000).toInt()), DELTA)
        Assert.assertEquals(181.1, Longitude28.toDegrees(java.lang.Double.valueOf(181.1 * 60 * 10000).toInt()), DELTA)
        Assert.assertEquals(202.3, Longitude28.toDegrees(java.lang.Double.valueOf(202.3 * 60 * 10000).toInt()), DELTA)
    }

    companion object {
        private const val DELTA = 0.00001
    }
}