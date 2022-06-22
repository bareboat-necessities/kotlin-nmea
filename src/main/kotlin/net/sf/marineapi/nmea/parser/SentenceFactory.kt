/*
 * SentenceFactory.java
 * Copyright (C) 2010 Kimmo Tuukkanen
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
package net.sf.marineapi.nmea.parser

import net.sf.marineapi.nmea.sentence.Sentence
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.TalkerId
import java.lang.reflect.InvocationTargetException
import java.util.concurrent.ConcurrentHashMap

/**
 * Factory for creating sentence parsers.
 *
 *
 * Custom parsers may be implemented and registered in the factory at runtime
 * by following these steps:
 *
 *  1. Define a sentence interface by extending the [Sentence] interface
 * (e.g. `com.acme.XYZSentence`).
 *  1. Implement the interface in a class that extends [SentenceParser],
 * (e.g. `com.acme.XYZParser`).
 *  1. Use the protected getters and setters in `SentenceParser` to
 * read and write sentence data.
 *  1. Add a constructor in `XYZParser` with `String`
 * parameter, i.e. the sentence to be parsed. Pass this parameter to
 * [SentenceParser] with expected sentence
 * type (e.g. `"XYZ"`).
 *  1. Add another constructor with [TalkerId] parameter. Pass this
 * parameter to [SentenceParser]
 * with sentence type and the expected number of data fields.
 *  1. Register `XYZParser` in `SentenceFactory` by using
 * the [.registerParser] method.
 *  1. Use [SentenceFactory.createParser] or
 * [SentenceFactory.createParser] to obtain an instance
 * of your parser. In addition, [net.sf.marineapi.nmea.io.SentenceReader]
 * will now dispatch instances of `XYZSentence` when "XYZ" sentences
 * are read from the data source.
 *
 *
 *
 * Notice that there is no need to compile the whole library and the added
 * parser source code may be located in your own codebase. Additionally, it is
 * also possible to override any existing parsers of the library as needed.
 *
 *
 * @author Kimmo Tuukkanen
 * @author Gunnar Hillert
 */
class SentenceFactory private constructor() {
    /**
     * Constructor.
     */
    init {
        reset()
    }

    /**
     * Creates a parser for specified NMEA 0183 sentence String. The parser
     * implementation is selected from registered parsers according to sentence
     * type. The returned instance must be cast in to correct sentence
     * interface, for which the type should first be checked by using the
     * [Sentence.getSentenceId] method.
     *
     * @param nmea NMEA 0183 sentence String
     * @return Sentence parser instance for specified sentence
     * @throws IllegalArgumentException If there is no parser registered for the
     * given sentence type
     * @throws IllegalStateException If parser is found, but it does not
     * implement expected constructors or is otherwise unusable.
     */
    fun createParser(nmea: String): Sentence? {
        val sid = SentenceId.parseStr(nmea)
        return createParserImpl(sid, nmea)
    }

    /**
     * Creates a parser for specified talker and sentence type. The returned
     * instance needs to be cast to corresponding sentence interface.
     *
     * @param talker Sentence talker id
     * @param type Sentence type
     * @return Sentence parser of requested type.
     * @throws IllegalArgumentException If talker id is null or if there is no
     * parser registered for given sentence type.
     * @throws IllegalStateException If parser instantiation fails.
     */
    fun createParser(talker: TalkerId?, type: SentenceId): Sentence? {
        return createParser(talker, type.toString())
    }

    /**
     * Creates a parser for specified talker and sentence type. This method is
     * mainly intended to be used when custom parsers have been registered in
     * the factory. The returned instance needs to be cast to corresponding
     * sentence interface.
     *
     * @param talker Talker ID to use in parser
     * @param type Type of the parser to create
     * @return Sentence parser for requested type
     * @throws IllegalArgumentException If talker id is null or if there is no
     * parser registered for given sentence type.
     * @throws IllegalStateException If parser is found, but it does not
     * implement expected constructors or is otherwise unusable.
     */
    fun createParser(talker: TalkerId?, type: String): Sentence? {
        requireNotNull(talker) { "TalkerId cannot be null" }
        return createParserImpl(type, talker)
    }

    /**
     * Tells if the factory is able to create parser for specified sentence
     * type. All [SentenceId] enum values should result returning
     * `true` at all times.
     *
     * @param type Sentence type id, e.g. "GLL" or "GGA".
     * @return true if type is supported, otherwise false.
     */
    fun hasParser(type: String): Boolean {
        return parsers!!.containsKey(type)
    }

    /**
     * Returns a list of currently parseable sentence types.
     *
     * @return List of sentence ids
     */
    fun listParsers(): List<String> {
        val keys: Set<String> = parsers!!.keys
        return listOf(*keys.toTypedArray())
    }

    /**
     * Registers a sentence parser to the factory. After registration,
     * [.createParser] method can be used to obtain instances of
     * registered parser.
     *
     *
     * Sentences supported by the library are registered automatically, but they
     * can be overridden simply be registering a new parser implementation for
     * chosen sentence type. That is, each sentence type can have only one
     * parser registered at a time.
     *
     * @param type Sentence type id, e.g. "GGA" or "GLL".
     * @param parser Class of parser implementation for given `type`.
     */
    fun registerParser(
        type: String,
        parser: Class<out SentenceParser>
    ) {
        registerParser(parsers, type, parser)
    }

    /**
     * Registers a sentence parser to the given factory. After registration,
     * [.createParser] method can be used to obtain instances of
     * registered parser.
     *
     *
     * Sentences supported by the library are registered automatically, but they
     * can be overridden simply be registering a new parser implementation for
     * chosen sentence type. That is, each sentence type can have only one
     * parser registered at a time.
     *
     * @param parsers The provided factory to register the sentence parsers to.
     * @param type Sentence type id, e.g. "GGA" or "GLL".
     * @param parser Class of parser implementation for given `type`.
     */
    private fun registerParser(
        parsers: MutableMap<String, Class<out SentenceParser>>?, type: String,
        parser: Class<out SentenceParser>
    ) {
        try {
            parser.getConstructor(String::class.java)
            parser.getConstructor(TalkerId::class.java)
            parsers!![type] = parser
        } catch (e: SecurityException) {
            val msg = "Unable to register parser due security violation"
            throw IllegalArgumentException(msg, e)
        } catch (e: NoSuchMethodException) {
            val msg = ("Required constructors not found; SentenceParser(String),"
                    + " SentenceParser(TalkerId)")
            throw IllegalArgumentException(msg, e)
        }
    }

    /**
     * Unregisters a parser class, regardless of sentence type(s) it is
     * registered for.
     *
     * @param parser Parser implementation class for `type`.
     * @see .registerParser
     */
    fun unregisterParser(parser: Class<out SentenceParser?>) {
        for (key in parsers!!.keys) {
            if (parsers!![key] == parser) {
                parsers!!.remove(key)
                break
            }
        }
    }

    /**
     * Creates a new parser instance with specified parameters.
     *
     * @param sid Sentence/parser type ID, e.g. "GGA" or "GLL"
     * @param param Object to pass as parameter to parser constructor
     * @return Sentence parser
     */
    private fun createParserImpl(sid: String, param: Any): Sentence? {
        if (!hasParser(sid)) {
            val msg = String.format("Parser for type '%s' not found", sid)
            throw UnsupportedSentenceException(msg)
        }
        val parser: Sentence?
        val klass: Class<*> = param.javaClass
        parser = try {
            val c = parsers!![sid]!!
            val co = c.getConstructor(klass)
            co.newInstance(param)
        } catch (e: NoSuchMethodException) {
            val name = klass.name
            val msg = "Constructor with %s parameter not found"
            throw IllegalStateException(String.format(msg, name), e)
        } catch (e: InstantiationException) {
            throw IllegalStateException("Unable to instantiate parser", e)
        } catch (e: IllegalAccessException) {
            throw IllegalStateException("Unable to access parser", e)
        } catch (e: InvocationTargetException) {
            throw IllegalStateException(
                "Unable to invoke parser constructor", e
            )
        }
        return parser
    }

    /**
     * Resets the factory in it's initial state, i.e. restores and removes all
     * parsers the have been either removed or added.
     *
     */
    fun reset() {
        val tempParsers: MutableMap<String, Class<out SentenceParser>> = ConcurrentHashMap()
        registerParser(tempParsers, "APB", APBParser::class.java)
        registerParser(tempParsers, "ALK", STALKParser::class.java)
        registerParser(tempParsers, "BOD", BODParser::class.java)
        registerParser(tempParsers, "CUR", CURParser::class.java)
        registerParser(tempParsers, "DBT", DBTParser::class.java)
        registerParser(tempParsers, "DPT", DPTParser::class.java)
        registerParser(tempParsers, "DTM", DTMParser::class.java)
        registerParser(tempParsers, "GBS", GBSParser::class.java)
        registerParser(tempParsers, "GGA", GGAParser::class.java)
        registerParser(tempParsers, "GLL", GLLParser::class.java)
        registerParser(tempParsers, "GNS", GNSParser::class.java)
        registerParser(tempParsers, "GSA", GSAParser::class.java)
        registerParser(tempParsers, "GST", GSTParser::class.java)
        registerParser(tempParsers, "GSV", GSVParser::class.java)
        registerParser(tempParsers, "HDG", HDGParser::class.java)
        registerParser(tempParsers, "HDM", HDMParser::class.java)
        registerParser(tempParsers, "HDT", HDTParser::class.java)
        registerParser(tempParsers, "HTC", HTCParser::class.java)
        registerParser(tempParsers, "HTD", HTDParser::class.java)
        registerParser(tempParsers, "MHU", MHUParser::class.java)
        registerParser(tempParsers, "MMB", MMBParser::class.java)
        registerParser(tempParsers, "MTA", MTAParser::class.java)
        registerParser(tempParsers, "MTW", MTWParser::class.java)
        registerParser(tempParsers, "MWV", MWVParser::class.java)
        registerParser(tempParsers, "OSD", OSDParser::class.java)
        registerParser(tempParsers, "RMB", RMBParser::class.java)
        registerParser(tempParsers, "RMC", RMCParser::class.java)
        registerParser(tempParsers, "RPM", RPMParser::class.java)
        registerParser(tempParsers, "ROT", ROTParser::class.java)
        registerParser(tempParsers, "RTE", RTEParser::class.java)
        registerParser(tempParsers, "RSA", RSAParser::class.java)
        registerParser(tempParsers, "RSD", RSDParser::class.java)
        registerParser(tempParsers, "TLB", TLBParser::class.java)
        registerParser(tempParsers, "TLL", TLLParser::class.java)
        registerParser(tempParsers, "TTM", TTMParser::class.java)
        registerParser(tempParsers, "TXT", TXTParser::class.java)
        registerParser(tempParsers, "UBX", UBXParser::class.java)
        registerParser(tempParsers, "VBW", VBWParser::class.java)
        registerParser(tempParsers, "VDM", VDMParser::class.java)
        registerParser(tempParsers, "VDO", VDOParser::class.java)
        registerParser(tempParsers, "VDR", VDRParser::class.java)
        registerParser(tempParsers, "VHW", VHWParser::class.java)
        registerParser(tempParsers, "VLW", VLWParser::class.java)
        registerParser(tempParsers, "VTG", VTGParser::class.java)
        registerParser(tempParsers, "VWR", VWRParser::class.java)
        registerParser(tempParsers, "VWT", VWTParser::class.java)
        registerParser(tempParsers, "WPL", WPLParser::class.java)
        registerParser(tempParsers, "XTE", XTEParser::class.java)
        registerParser(tempParsers, "XDR", XDRParser::class.java)
        registerParser(tempParsers, "ZDA", ZDAParser::class.java)
        registerParser(tempParsers, "MDA", MDAParser::class.java)
        registerParser(tempParsers, "MWD", MWDParser::class.java)
        registerParser(tempParsers, "DTA", DTAParser::class.java)
        registerParser(tempParsers, "DTB", DTBParser::class.java)
        parsers = tempParsers
    }

    companion object {
        // map that holds registered sentence types and parser classes
        private var parsers: MutableMap<String, Class<out SentenceParser>>? = null

        /**
         * Returns the singleton instance of `SentenceFactory`.
         *
         * @return SentenceFactory instance
         */
        // singleton factory instance
        val instance = SentenceFactory()
    }
}