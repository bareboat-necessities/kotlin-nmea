/*
 * AbstractAISMessageListener.java
 * Copyright (C) 2015 Kimmo Tuukkanen
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
package net.sf.marineapi.ais.event

import net.sf.marineapi.ais.message.AISMessage

import net.sf.marineapi.ais.parser.AISMessageFactory
import net.sf.marineapi.nmea.event.AbstractSentenceListener
import net.sf.marineapi.nmea.event.SentenceListener
import net.sf.marineapi.nmea.sentence.AISSentence
import net.sf.marineapi.util.GenericTypeResolver

import java.util.Queue
import java.util.LinkedList

/**
 *
 *
 * Abstract listener for AIS messages. Extend this class to create a listener
 * for a specific AIS message type and register it in a
 * [net.sf.marineapi.nmea.io.SentenceReader].
 *
 *
 * To listen to all incoming AIS sentences, extend the [ ] using [AISSentence] as type. However, in this
 * case you also need to implement the message concatenation to parse messages
 * being delivered over multiple sentences.
 *
 *
 * This class is based on [AbstractSentenceListener] and thus it has the
 * same recommendations and limitations regarding the usage of generics and
 * inheritance.
 *
 *
 * @author Kimmo Tuukkanen
 * @param <T> AIS message type to be listened.
 * @see AbstractSentenceListener
 *
 * @see GenericTypeResolver
</T> */
abstract class AbstractAISMessageListener<T : AISMessage?> : AbstractSentenceListener<AISSentence> {
    @JvmField
    val messageType: Class<*>?
    private val queue: Queue<AISSentence> = LinkedList()
    private val factory: AISMessageFactory? = AISMessageFactory.instance

    /**
     * Default constructor with automatic generic type resolving. Notice that
     * the [GenericTypeResolver] may not always succeed.
     *
     * @see .AbstractAISMessageListener
     * @throws IllegalStateException If the generic type cannot be resolved
     * at runtime.
     */
    constructor() {
        messageType = GenericTypeResolver.resolve(
            javaClass, AbstractAISMessageListener::class.java
        )
    }

    /**
     * Constructor with explicit generic type parameter. This constructor may
     * be used when the default constructor fails to resolve the generic type
     * `T` at runtime.
     *
     * @param c Message type `T` to be listened.
     * @see .AbstractAISMessageListener
     */
    constructor(c: Class<T>?) {
        messageType = c
    }

    /**
     *
     *
     * Invoked when [AISSentence] of any type is received. Pre-parses
     * the message to determine it's type and invokes the
     * [.onMessage] method when the type matches the generic
     * type `T`.
     *
     *
     * This method has been declared `final` to ensure the correct
     * handling of received sentences.
     */
    override fun sentenceRead(sentence: AISSentence?) {
        if (sentence!!.isFirstFragment()) {
            queue.clear()
        }
        queue.add(sentence)
        if (sentence.isLastFragment()) {
            val sentences = queue.toTypedArray()
            try {
                val message = factory!!.create(*sentences)
                if (messageType!!.isAssignableFrom(message.javaClass)) {
                    onMessage(message as T)
                }
            } catch (iae: IllegalArgumentException) {
                // never mind incorrect order or unsupported message types
            }
        }
    }

    /**
     * Invoked when AIS message has been received.
     * @param msg AISMessage of type `T`
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