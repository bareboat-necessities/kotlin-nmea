/*
 * AbstractProvider.java
 * Copyright (C) 2011 Kimmo Tuukkanen
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

import net.sf.marineapi.nmea.event.SentenceEvent
import net.sf.marineapi.nmea.event.SentenceListener
import net.sf.marineapi.nmea.io.SentenceReader
import net.sf.marineapi.nmea.sentence.Sentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.provider.event.ProviderEvent
import net.sf.marineapi.provider.event.ProviderListener
import java.util.*
import java.util.stream.Collectors

/**
 *
 * Abstract base class for providers. Defines methods that all providers must
 * implement and provides general services for capturing and validating the
 * required sentences.
 *
 * When constructing [net.sf.marineapi.provider.event.PositionEvent],
 * the maximum age for all captured sentences is 1000 ms, i.e. all sentences are
 * from within the default NMEA update rate (1/s).
 *
 * @author Kimmo Tuukkanen
 * @param <T> The [ProviderEvent] to be dispatched.
</T> */
abstract class AbstractProvider<T : ProviderEvent?> : SentenceListener {
    private var reader: SentenceReader
    private val events: MutableList<SentenceEvent> = ArrayList()
    private val listeners: MutableList<ProviderListener<T>> = ArrayList()
    private var timeout = DEFAULT_TIMEOUT

    /**
     * Creates a new instance of AbstractProvider.
     *
     * @param reader Sentence reader to be used as data source
     * @param ids Types of sentences to capture for creating provider events
     */
    constructor(reader: SentenceReader, vararg ids: String) {
        this.reader = reader
        for (id in ids) {
            reader.addSentenceListener(this, id)
        }
    }

    /**
     * Creates a new instance of AbstractProvider.
     *
     * @param reader Sentence reader to be used as data source
     * @param ids Types of sentences to capture for creating provider events
     */
    constructor(reader: SentenceReader, vararg ids: SentenceId) {
        this.reader = reader
        for (id in ids) {
            reader.addSentenceListener(this, id)
        }
    }

    /**
     * Inserts a listener to provider.
     *
     * @param listener Listener to add
     */
    fun addListener(listener: ProviderListener<T>) {
        listeners.add(listener)
    }

    /**
     * Creates a `ProviderEvent` of type `T`.
     *
     * @return Created event, or null if failed.
     */
    protected abstract fun createProviderEvent(): T

    /**
     * Dispatch the TPV event to all listeners.
     *
     * @param event TPVUpdateEvent to dispatch
     */
    private fun fireProviderEvent(event: T) {
        for (listener in listeners) {
            listener.providerUpdate(event)
        }
    }

    /**
     * Returns the collected sentences.
     *
     * @return List of sentences.
     */
    protected fun getSentences(): List<Sentence?> {
        val s: MutableList<Sentence?> = ArrayList()
        for (e in events) {
            s.add(e.sentence)
        }
        return s
    }

    /**
     * Tells if the provider has captured all the specified sentences.
     *
     * @param id Sentence type IDs to look for.
     * @return True if all specified IDs match the captured sentences.
     */
    protected fun hasAll(vararg id: String?): Boolean {
        for (s in id) {
            if (!hasOne(s)) {
                return false
            }
        }
        return true
    }

    /**
     * Tells if the provider has captured at least one of the specified
     * sentences.
     *
     * @param id Sentence type IDs to look for, in prioritized order.
     * @return True if any of the specified IDs matches the type of at least one
     * captured sentences.
     */
    protected fun hasOne(vararg id: String?): Boolean {
        val ids = Arrays.asList(*id)
        for (s in getSentences()) {
            if (ids.contains(s!!.sentenceId)) {
                return true
            }
        }
        return false
    }

    /**
     * Tells if provider has captured the required sentences for creating new
     * ProviderEvent.
     *
     * @return true if ready to create ProviderEvent, otherwise false.
     */
    protected abstract fun isReady(): Boolean

    /**
     * Tells if the captured sentence events contain valid data to be dispatched
     * to ProviderListeners.
     *
     * @return true if valid, otherwise false.
     */
    protected abstract fun isValid(): Boolean

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.event.SentenceListener#readingPaused()
	 */
    override fun readingPaused() {
        // nothing
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.event.SentenceListener#readingStarted()
	 */
    override fun readingStarted() {
        reset()
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.event.SentenceListener#readingStopped()
	 */
    override fun readingStopped() {
        reset()
        reader.removeSentenceListener(this)
    }

    /**
     * Removes the specified listener from provider.
     *
     * @param listener Listener to remove
     */
    fun removeListener(listener: ProviderListener<T>) {
        listeners.remove(listener)
    }

    /**
     * Clears the list of collected events.
     */
    private fun reset() {
        events.clear()
    }

    /**
     * Expunge collected events that are older than `timeout` to prevent
     * the `events` list growing when sentences are being delivered
     * without valid data (e.g. during device warm-up or offline state).
     */
    private fun expunge() {
        val now = System.currentTimeMillis()
        val expired = events.stream()
            .filter { event: SentenceEvent -> now - event.timeStamp > timeout }
            .collect(Collectors.toList())
        events.removeAll(expired.toSet())
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.nmea.event.SentenceListener#sentenceRead(
	 * net.sf.marineapi.nmea.event.SentenceEvent)
	 */
    override fun sentenceRead(event: SentenceEvent) {
        events.add(event)
        if (isReady()) {
            if (validate()) {
                val pEvent = createProviderEvent()
                fireProviderEvent(pEvent)
            }
            reset()
        } else {
            expunge()
        }
    }

    /**
     *
     * Sets the timeout for receiving a burst of sentences. The default value
     * is 1000 ms as per default update rate of NMEA 0183 (1/s). However, the
     * length of data bursts may vary depending on the device. If the timeout is
     * exceeded before receiving all needed sentences, the sentences are
     * discarded and waiting period for next burst of data is started. Use this
     * method to increase the timeout if the bursts are constantly being
     * discarded and thus no events are dispatched by the provider.
     *
     * @param millis Timeout to set, in milliseconds.
     * @see .DEFAULT_TIMEOUT
     */
    fun setTimeout(millis: Int) {
        require(millis >= 1) { "Timeout value must be > 0" }
        timeout = millis
    }

    /**
     * Validates the collected sentences by checking the ages of each sentence
     * and then by calling [.isValid]. If extending implementation has
     * no validation criteria, it should return always `true`.
     *
     * @return true if valid, otherwise false
     */
    private fun validate(): Boolean {
        val now = System.currentTimeMillis()
        for (se in events) {
            val age = now - se.timeStamp
            if (age > timeout) {
                return false
            }
        }
        return isValid()
    }

    companion object {
        /** The default timeout for receicing a burst of sentences.  */
        var DEFAULT_TIMEOUT = 1000
    }
}