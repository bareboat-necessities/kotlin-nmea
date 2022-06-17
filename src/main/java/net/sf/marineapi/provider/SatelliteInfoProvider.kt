/* 
 * SatelliteInfoProvider.java
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

import net.sf.marineapi.nmea.io.SentenceReader
import net.sf.marineapi.nmea.sentence.GSASentence
import net.sf.marineapi.nmea.sentence.GSVSentence
import net.sf.marineapi.nmea.util.SatelliteInfo
import net.sf.marineapi.provider.event.SatelliteInfoEvent

/**
 * SatelliteInfoProvider collects GPS satellite information from sequence of GSV sentences
 * and reports all the information in a single event.
 *
 * @author Kimmo Tuukkanen
 */
class SatelliteInfoProvider
/**
 * Creates a new instance of SatelliteInfoProvider with specified reader.
 *
 * @param reader Reader to scan for GSV sentences.
 */
    (reader: SentenceReader) : AbstractProvider<SatelliteInfoEvent>(reader, "GSA", "GSV") {
    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.provider.AbstractProvider#createProviderEvent()
	 */
    override fun createProviderEvent(): SatelliteInfoEvent {
        var gsa: GSASentence? = null
        val info: MutableList<SatelliteInfo?> = ArrayList()
        for (sentence in getSentences()) {
            if ("GSA" == sentence!!.getSentenceId()) {
                gsa = sentence as GSASentence?
            } else if ("GSV" == sentence.getSentenceId()) {
                val gsv = sentence as GSVSentence
                info.addAll(gsv.getSatelliteInfo()!!)
            }
        }
        return SatelliteInfoEvent(this, gsa, info)
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.provider.AbstractProvider#isReady()
	 */
    override fun isReady(): Boolean {
        var hasFirstGSV = false
        var hasLastGSV = false
        var hasAllGSV = false
        var count = 0
        for (s in getSentences()) {
            if ("GSV" == s!!.getSentenceId()) {
                val gsv = s as GSVSentence
                if (!hasFirstGSV) {
                    hasFirstGSV = gsv.isFirst()
                }
                if (!hasLastGSV) {
                    hasLastGSV = gsv.isLast()
                }
                hasAllGSV = gsv.getSentenceCount() == ++count
            }
        }
        return hasOne("GSA") && hasAllGSV && hasFirstGSV && hasLastGSV
    }

    /*
	 * (non-Javadoc)
	 * @see net.sf.marineapi.provider.AbstractProvider#isValid()
	 */
    override fun isValid(): Boolean {
        return true
    }
}