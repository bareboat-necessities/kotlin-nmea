/*
 * SentenceReader.java
 * Copyright (C) 2010-2014 Kimmo Tuukkanen
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
package net.sf.marineapi.nmea.io

import net.sf.marineapi.nmea.event.SentenceEvent
import net.sf.marineapi.nmea.event.SentenceListener
import net.sf.marineapi.nmea.sentence.Sentence
import net.sf.marineapi.nmea.sentence.SentenceId
import java.io.InputStream
import java.net.DatagramSocket
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.function.Consumer

import java.util.logging.Level
import java.util.logging.Logger
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

/**
 * Sentence reader detects supported NMEA 0183 sentences from the specified
 * data source and dispatches them to registered listeners as sentence events.
 * Each event contains a parser for the read sentence.
 *
 *
 * Parsers dispatched by the reader are created using [ ], where you can also add your
 * own parsers at runtime.
 *
 * @author Kimmo Tuukkanen
 * @see net.sf.marineapi.nmea.event.AbstractSentenceListener
 *
 * @see net.sf.marineapi.nmea.event.SentenceListener
 *
 * @see net.sf.marineapi.nmea.event.SentenceEvent
 *
 * @see net.sf.marineapi.nmea.parser.SentenceFactory
 */
class SentenceReader {
    // Thread for running the worker
    private var thread: Thread? = null

    // worker that reads the data source (input stream, socket etc)
    private var reader: AbstractDataReader?

    // map of sentence listeners
    private val listeners: ConcurrentMap<String?, MutableList<SentenceListener>> = ConcurrentHashMap()
    /**
     * Returns the current reading paused timeout.
     *
     * @return Timeout limit in milliseconds.
     * @see .setPauseTimeout
     */
    /**
     * Set timeout time for reading paused events. Default is 5000 ms.
     *
     * @param millis Timeout in milliseconds.
     */
    // timeout for "reading paused" in ms
    @Volatile
    var pauseTimeout = DEFAULT_TIMEOUT

    // Non-NMEA data listener
    private var dataListener: DataListener? = null
    /**
     * Returns the exception call-back listener.
     *
     * @return Currently set ExceptionListener, or `null` if none.
     */
    /**
     * Set exception call-back listener.
     *
     * @param exceptionListener Listener to set, or `null` to reset.
     */
    // Exception listener
    var exceptionListener: ExceptionListener? = null

    /**
     * Creates a SentenceReader for UDP/DatagramSocket.
     *
     * @param source Socket from which to read NMEA data
     */
    constructor(source: DatagramSocket) {
        reader = UDPDataReader(source, this)
    }

    /**
     * Creates a new instance of SentenceReader.
     *
     * @param source Stream from which to read NMEA data
     */
    constructor(source: InputStream?) {
        reader = DefaultDataReader(source, this)
    }

    /**
     * Creates a new instance of SentenceReader with custom data reader.
     *
     * @param reader Custom data reader to use.
     * @see AbstractDataReader
     */
    constructor(reader: AbstractDataReader?) {
        requireNotNull(reader) { "Data reader cannot be null" }
        reader.parent = this
        this.reader = reader
    }

    /**
     * Adds a [SentenceListener] to receive all incoming sentences.
     *
     * @param listener [SentenceListener] to be registered.
     * @see SentenceListener
     */
    fun addSentenceListener(listener: SentenceListener) {
        registerListener(listener, DISPATCH_ALL)
    }

    /**
     * Adds a [SentenceListener] to receive sentences of specified type.
     *
     * @param sl SentenceListener to add
     * @param type Sentence type for which the listener is registered.
     * @see SentenceListener
     */
    fun addSentenceListener(sl: SentenceListener, type: SentenceId) {
        registerListener(sl, type.toString())
    }

    /**
     * Adds a [SentenceListener] to receive sentences of specified type.
     *
     * @param sl SentenceListener to add
     * @param type Sentence type for which the listener is registered.
     * @see SentenceListener
     */
    fun addSentenceListener(sl: SentenceListener, type: String) {
        registerListener(sl, type)
    }

    /**
     * Pass data to DataListener.
     *
     * @param data Data to be delivered.
     */
    fun fireDataEvent(data: String?) {
        try {
            if (dataListener != null) {
                dataListener!!.dataRead(data)
            }
        } catch (e: Exception) {
            LOGGER.log(Level.WARNING, "Exception thrown by DataListener", e)
        }
    }

    /**
     * Notifies all listeners that reader has paused due to timeout.
     */
    fun fireReadingPaused() {
        for (listener in sentenceListeners) {
            try {
                listener.readingPaused()
            } catch (e: Exception) {
                LOGGER.log(Level.WARNING, LOG_MSG, e)
            }
        }
    }

    /**
     * Notifies all listeners that NMEA data has been detected in the stream and
     * events will be dispatched until stopped or timeout occurs.
     */
    fun fireReadingStarted() {
        for (listener in sentenceListeners) {
            try {
                listener.readingStarted()
            } catch (e: Exception) {
                LOGGER.log(Level.WARNING, LOG_MSG, e)
            }
        }
    }

    /**
     * Notifies all listeners that data reading has stopped.
     */
    fun fireReadingStopped() {
        for (listener in sentenceListeners) {
            try {
                listener.readingStopped()
            } catch (e: Exception) {
                LOGGER.log(Level.WARNING, LOG_MSG, e)
            }
        }
    }

    /**
     * Dispatch data to all listeners.
     *
     * @param sentence sentence string.
     */
    fun fireSentenceEvent(sentence: Sentence?) {
        val type = sentence!!.sentenceId
        val targets: MutableSet<SentenceListener> = HashSet()
        if (listeners.containsKey(type)) {
            targets.addAll(listeners[type]!!)
        }
        if (listeners.containsKey(DISPATCH_ALL)) {
            targets.addAll(listeners[DISPATCH_ALL]!!)
        }
        for (listener in targets) {
            try {
                val se = SentenceEvent(this, sentence)
                listener.sentenceRead(se)
            } catch (e: Exception) {
                LOGGER.log(Level.WARNING, LOG_MSG, e)
            }
        }
    }

    /**
     * Returns all currently registered SentenceListeners.
     *
     * @return List of SentenceListeners or empty list.
     */
    val sentenceListeners: List<SentenceListener>
        get() {
            val all: MutableSet<SentenceListener> = HashSet()
            for (sl in listeners.values) {
                all.addAll(sl!!)
            }
            return ArrayList(all)
        }

    /**
     * Handles an exception by passing it to ExceptionHandler. If no handler
     * is present, logs the error at level WARNING.
     *
     * @param msg Error message for logging
     * @param ex Exception to handle
     */
    fun handleException(msg: String?, ex: Exception?) {
        if (exceptionListener == null) {
            LOGGER.log(Level.WARNING, msg, ex)
        } else {
            try {
                exceptionListener!!.onException(ex)
            } catch (e: Exception) {
                LOGGER.log(Level.WARNING, "Exception thrown by ExceptionListener", e)
            }
        }
    }

    /**
     * Registers a SentenceListener to hash map with given key.
     *
     * @param listener SentenceListener to register
     * @param type Sentence type to register for
     */
    private fun registerListener(listener: SentenceListener, type: String) {
        if (listeners.containsKey(type)) {
            listeners[type]!!.add(listener)
        } else {
            val list: MutableList<SentenceListener> = Vector()
            list.add(listener)
            listeners[type] = list
        }
    }

    /**
     * Removes the specified sentence listener regardless of sentence type(s)
     * it was added for.
     *
     * @param listener [SentenceListener] to be removed.
     */
    fun removeSentenceListener(listener: SentenceListener) {
        listeners.values.forEach(Consumer { v: MutableList<SentenceListener> -> v.remove(listener) })
    }

    /**
     * Removes the sentence listener for specified sentence type.
     *
     * @param listener [SentenceListener] to be removed.
     * @param sid Sentence Id
     */
    fun removeSentenceListener(listener: SentenceListener, sid: SentenceId) {
        this.removeSentenceListener(listener, sid.name)
    }

    /**
     * Removes the sentence listener for specified sentence type.
     *
     * @param listener [SentenceListener] to be removed.
     * @param type Sentence type
     * @see .removeSentenceListener
     */
    fun removeSentenceListener(listener: SentenceListener, type: String?) {
        listeners.getOrDefault(type, ArrayList()).remove(listener)
    }

    /**
     * Sets the DatagramSocket to be used as data source. If reader is running,
     * it is first stopped and you must call [.start] to resume reading.
     *
     * @param socket DatagramSocket to set
     */
    fun setDatagramSocket(socket: DatagramSocket) {
        if (reader!!.isRunning) {
            stop()
        }
        reader = UDPDataReader(socket, this)
    }

    /**
     * Set listener for any data that is not recognized as NMEA 0183.
     * devices and environments that produce mixed content with both NMEA and
     * non-NMEA data.
     *
     * @param listener Listener to set, `null` to remove.
     */
    fun setDataListener(listener: DataListener?) {
        dataListener = listener
    }

    /**
     * Sets the InputStream to be used as data source. If reader is running, it
     * is first stopped and you must call [.start] to resume reading.
     *
     * @param stream InputStream to set.
     */
    fun setInputStream(stream: InputStream?) {
        if (reader!!.isRunning) {
            stop()
        }
        reader = DefaultDataReader(stream, this)
    }

    /**
     * Starts reading the input stream and dispatching events.
     *
     * @throws IllegalStateException If reader is already running.
     */
    fun start() {
        check(!(thread != null && thread!!.isAlive && reader != null && reader!!.isRunning)) { "Reader is already running" }
        thread = Thread(reader)
        thread!!.start()
    }

    /**
     * Stops the reader and event dispatching.
     */
    fun stop() {
        if (reader != null && reader!!.isRunning) {
            reader!!.stop()
        }
    }

    companion object {
        /** Default timeout value in milliseconds.  */
        const val DEFAULT_TIMEOUT = 5000

        // Map key for listeners that listen any kind of sentences, type
        // specific listeners are registered with sentence type String
        private const val DISPATCH_ALL = "DISPATCH_ALL"

        // logging
        private val LOGGER = Logger.getLogger(SentenceReader::class.java.name)
        private const val LOG_MSG = "Exception caught from SentenceListener"
    }
}