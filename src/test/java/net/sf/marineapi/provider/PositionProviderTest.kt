/* 
 * PositionProviderTest.java
 * Copyright (C) 2012 Kimmo Tuukkanen
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
package net.sf.marineapi.provider

import org.junit.Assert.assertNotNull
import java.io.File

/**
 * @author Kimmo Tuukkanen
 */
class PositionProviderTest : PositionListener {
    var event: PositionEvent? = null
    var instance: PositionProvider? = null

    /**
     * @throws java.lang.Exception
     */
    @Before
    @Throws(Exception::class)
    fun setUp() {
        val f = File("target/test-classes/data/Navibe-GM720.txt")
        val str = FileInputStream(f)
        val r = SentenceReader(str)
        instance = PositionProvider(r)
        instance!!.addListener(this)
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    @Throws(Exception::class)
    fun tearDown() {
        instance!!.removeListener(this)
    }

    /**
     * Test method for
     * [net.sf.marineapi.provider.AbstractProvider.sentenceRead]
     * .
     */
    @Test
    fun testSentenceReadWithGGA() {
        val sf: SentenceFactory = SentenceFactory.getInstance()
        val gga: Sentence = sf.createParser(GGATest.Companion.EXAMPLE)
        assertNull(event)
        instance!!.sentenceRead(SentenceEvent(this, gga))
        assertNull(event)
        val rmc: Sentence = sf.createParser(RMCTest.Companion.EXAMPLE)
        assertNull(event)
        instance!!.sentenceRead(SentenceEvent(this, rmc))
        assertNotNull(event)
    }

    /**
     * Test method for
     * [net.sf.marineapi.provider.AbstractProvider.sentenceRead]
     * .
     */
    @Test
    fun testSentenceReadWithGLL() {
        val sf: SentenceFactory = SentenceFactory.getInstance()
        val gll: Sentence = sf.createParser(GLLTest.Companion.EXAMPLE)
        assertNull(event)
        instance!!.sentenceRead(SentenceEvent(this, gll))
        assertNull(event)
        val rmc: Sentence = sf.createParser(RMCTest.Companion.EXAMPLE)
        instance!!.sentenceRead(SentenceEvent(this, rmc))
        assertNotNull(event)
    }

    @Test
    fun testSentenceReadWithLegacyRMC() {
        val sf: SentenceFactory = SentenceFactory.getInstance()
        val gll: Sentence = sf.createParser(GLLTest.Companion.EXAMPLE)
        assertNull(event)
        instance!!.sentenceRead(SentenceEvent(this, gll))
        assertNull(event)
        val rmc: Sentence = sf.createParser(RMCTest.Companion.EXAMPLE_LEGACY)
        instance!!.sentenceRead(SentenceEvent(this, rmc))
        assertNotNull(event)
    }

    /*
	 * (non-Javadoc)
	 * @see
	 * net.sf.marineapi.provider.event.PositionListener#providerUpdate(net.sf.marineapi
	 * .provider.event.PositionEvent)
	 */
    override fun providerUpdate(evt: PositionEvent) {
        event = evt
    }
}