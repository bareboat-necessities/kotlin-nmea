/*
 * Copyright (C) 2020 Gunnar Hillert
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
package net.sf.marineapi.ublox.event

import net.sf.marineapi.nmea.event.AbstractSentenceListener
import net.sf.marineapi.nmea.event.SentenceListener
import net.sf.marineapi.nmea.sentence.UBXSentence
import net.sf.marineapi.ublox.message.UBXMessage
import net.sf.marineapi.ublox.parser.UBXMessageFactory
import net.sf.marineapi.util.GenericTypeResolver

/**
 *
 *
 * Abstract listener for UBX messages. Extend this class to create a listener
 * for a specific UBX message type and register it in a
 * [net.sf.marineapi.nmea.io.SentenceReader].
 *
 *
 * To listen to all incoming UBX sentences, extend the [ ] using [UBXSentence] as type.
 *
 *
 * This class is based on [AbstractSentenceListener] and thus it has the
 * same recommendations and limitations regarding the usage of generics and
 * inheritance.
 *
 *
 * @author Gunnar Hillert
 * @param <T> UBX message type to be listened.
 * @see AbstractSentenceListener
 *
 * @see GenericTypeResolver
</T> */
abstract class AbstractUBXMessageListener<T : UBXMessage?> : AbstractSentenceListener<UBXSentence> {
    val messageType: Class<*>?
    private val factory: UBXMessageFactory? = UBXMessageFactory.getInstance()

    /**
     * Default constructor with automatic generic type resolving. Notice that
     * the [GenericTypeResolver] may not always succeed.
     *
     * @see .AbstractUBXMessageListener
     * @throws IllegalStateException If the generic type cannot be resolved
     * at runtime.
     */
    constructor() {
        messageType = GenericTypeResolver.resolve(
            javaClass, AbstractUBXMessageListener::class.java
        )
    }

    /**
     * Constructor with explicit generic type parameter. This constructor may
     * be used when the default constructor fails to resolve the generic type
     * `T` at runtime.
     *
     * @param c Message type `T` to be listened.
     * @see .AbstractUBXMessageListener
     */
    constructor(c: Class<T>?) {
        messageType = c
    }

    /**
     *
     *
     * Invoked when [UBXSentence] of any type is received. Pre-parses
     * the message to determine it's type and invokes the
     * [.onMessage] method when the type matches the generic
     * type `T`.
     *
     *
     * This method has been declared `final` to ensure the correct
     * handling of received sentences.
     */
    override fun sentenceRead(sentence: UBXSentence?) {
        try {
            val message = factory!!.create(sentence!!)
            if (messageType!!.isAssignableFrom(message.javaClass)) {
                onMessage(message as T)
            }
        } catch (iae: IllegalArgumentException) {
            // never mind incorrect order or unsupported message types
        }
    }

    /**
     * Invoked when a UBX message has been received.
     * @param msg UBXMessage of type `T`
     */
    abstract fun onMessage(msg: T?)

    /**
     * Empty implementation.
     * @see SentenceListener.readingPaused
     */
    override fun readingPaused() {}

    /**
     * Empty implementation.
     * @see SentenceListener.readingStarted
     */
    override fun readingStarted() {}

    /**
     * Empty implementation.
     * @see SentenceListener.readingStopped
     */
    override fun readingStopped() {}
}