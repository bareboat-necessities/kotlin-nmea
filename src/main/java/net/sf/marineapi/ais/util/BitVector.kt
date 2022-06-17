/*
 * BitVector.java
 * Copyright (C) 2015 Lázár József
 *
 * This file is part of Java Marine API.
 * <http://ktuukkan.github.io/marine-api/>
 *
 * Java Marine API is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Java Marine API is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Java Marine API. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.marineapi.ais.util

import java.util.*

/**
 * Class holding bit values in an array and implementing put/get
 * integer/string operations on it.
 *
 * @author Lázár József
 */
class BitVector {
    private var fBitVector: BitSet

    /**
     * Creates a new instance with given length.
     *
     * @param bits Vector length
     */
    constructor(bits: Int) {
        fBitVector = BitSet(bits)
    }

    /**
     * Creates a new instance with given BitSet and length.
     *
     * @param vector BitSet
     */
    constructor(vector: BitSet) {
        fBitVector = vector
    }

    /**
     * Set bit at given index.
     *
     * @param index Index of bit to set.
     */
    fun set(index: Int) {
        fBitVector.set(index)
    }

    /**
     * Gets a vector subset.
     *
     * @param from Start index
     * @param to End index
     * @return BitVector with specified range
     */
    operator fun get(from: Int, to: Int): BitVector {
        var from = from
        var to = to
        to++
        from++
        return BitVector(fBitVector[from, to])
    }

    /**
     * Return bit as boolean from the bit vector
     *
     * @param index start index of bit
     * @return `true` if bit is set, otherwise `false`.
     */
    fun getBoolean(index: Int): Boolean {
        return fBitVector[index]
    }

    /**
     * Returns the requested bits interpreted as an integer (MSB first) from the message.
     *
     * @param from begin index (inclusive)
     * @param to end index (inclusive)
     * @return unsigned int value
     */
    fun getUInt(from: Int, to: Int): Int {
        var value = 0
        var i = fBitVector.previousSetBit(to)
        while (i > from) {
            (value += 1 shl to) - i
            i = fBitVector.previousSetBit(i - 1)
        }
        return value
    }

    /**
     * Convert to 8-bit integer.
     *
     * @param from Start index
     * @param to End index
     * @return Integer value
     */
    fun getAs8BitInt(from: Int, to: Int): Int {
        var retval = getUInt(from, to)
        if (retval >= 0x80) retval -= 0x100
        return retval
    }

    /**
     * Convert to 17-bit integer.
     *
     * @param from Start index
     * @param to End index
     * @return Integer value
     */
    fun getAs17BitInt(from: Int, to: Int): Int {
        var retval = getUInt(from, to)
        if (retval >= 0x10000) retval -= 0x20000
        return retval
    }

    /**
     * Convert to 18-bit integer.
     *
     * @param from Start index
     * @param to End index
     * @return Integer value
     */
    fun getAs18BitInt(from: Int, to: Int): Int {
        var retval = getUInt(from, to)
        if (retval >= 0x20000) retval -= 0x40000
        return retval
    }

    /**
     * Convert to 27-bit integer.
     *
     * @param from Start index
     * @param to End index
     * @return Integer value
     */
    fun getAs27BitInt(from: Int, to: Int): Int {
        var retval = getUInt(from, to)
        if (retval >= 0x4000000) retval -= 0x8000000
        return retval
    }

    /**
     * Convert to 28-bit integer.
     *
     * @param from Start index
     * @param to End index
     * @return Integer value
     */
    fun getAs28BitInt(from: Int, to: Int): Int {
        var retval = getUInt(from, to)
        if (retval >= 0x8000000) retval -= 0x10000000
        return retval
    }
}