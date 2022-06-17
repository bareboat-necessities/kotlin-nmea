/**
 * NMEA 0183 events and listeners.
 */
package net.sf.marineapi.nmea.event

import net.sf.marineapi.ais.util.MMSI
import net.sf.marineapi.ais.util.Angle9
import java.lang.IllegalArgumentException
import net.sf.marineapi.ais.util.Sixbit
import java.lang.StringBuilder
import net.sf.marineapi.ais.util.Angle12
import java.text.DecimalFormat
import net.sf.marineapi.ais.util.ShipType
import net.sf.marineapi.ais.util.TimeStamp
import net.sf.marineapi.ais.util.Latitude17
import net.sf.marineapi.ais.util.Latitude27
import net.sf.marineapi.ais.util.RateOfTurn
import net.sf.marineapi.ais.util.Longitude18
import net.sf.marineapi.ais.util.Longitude28
import net.sf.marineapi.ais.util.SpeedOverGround
import net.sf.marineapi.ais.util.Violation
import net.sf.marineapi.ais.util.ManeuverIndicator
import net.sf.marineapi.ais.util.NavigationalStatus
import net.sf.marineapi.ais.message.AISMessage
import net.sf.marineapi.nmea.event.AbstractSentenceListener
import net.sf.marineapi.nmea.sentence.AISSentence
import java.util.LinkedList
import net.sf.marineapi.ais.parser.AISMessageFactory
import net.sf.marineapi.util.GenericTypeResolver
import net.sf.marineapi.ais.event.AbstractAISMessageListener
import net.sf.marineapi.ais.parser.AISMessageParser
import net.sf.marineapi.ais.message.AISUTCReport
import net.sf.marineapi.ais.parser.AISUTCParser
import net.sf.marineapi.ais.util.AISRuleViolation
import net.sf.marineapi.ais.util.PositioningDevice
import java.lang.IllegalStateException
import net.sf.marineapi.ais.parser.AISMessage01Parser
import net.sf.marineapi.ais.parser.AISMessage02Parser
import net.sf.marineapi.ais.parser.AISMessage03Parser
import net.sf.marineapi.ais.parser.AISMessage04Parser
import net.sf.marineapi.ais.parser.AISMessage05Parser
import net.sf.marineapi.ais.parser.AISMessage09Parser
import net.sf.marineapi.ais.parser.AISMessage18Parser
import net.sf.marineapi.ais.parser.AISMessage19Parser
import net.sf.marineapi.ais.parser.AISMessage21Parser
import net.sf.marineapi.ais.parser.AISMessage24Parser
import net.sf.marineapi.ais.parser.AisMessage27Parser
import net.sf.marineapi.ais.parser.AISPositionReportParser
import net.sf.marineapi.ais.message.AISMessage01
import net.sf.marineapi.ais.message.AISMessage02
import net.sf.marineapi.ais.message.AISMessage03
import net.sf.marineapi.ais.message.AISMessage04
import net.sf.marineapi.ais.message.AISMessage05
import net.sf.marineapi.ais.message.AISMessage09
import net.sf.marineapi.ais.parser.AISPositionReportBParser
import net.sf.marineapi.ais.message.AISMessage18
import net.sf.marineapi.ais.message.AISMessage19
import net.sf.marineapi.ais.message.AISMessage21
import net.sf.marineapi.ais.util.NavAidType
import net.sf.marineapi.ais.message.AISMessage24
import net.sf.marineapi.ais.message.AISMessage27
import net.sf.marineapi.ais.message.AISPositionReport
import net.sf.marineapi.ais.message.AISPositionReportB
import net.sf.marineapi.ais.message.AISPositionInfo
import java.net.DatagramSocket
import net.sf.marineapi.nmea.io.SentenceReader
import net.sf.marineapi.nmea.io.AbstractDataReader
import java.net.DatagramPacket
import java.util.concurrent.ConcurrentMap
import net.sf.marineapi.nmea.event.SentenceListener
import java.util.concurrent.ConcurrentHashMap
import net.sf.marineapi.nmea.io.DataListener
import net.sf.marineapi.nmea.io.UDPDataReader
import net.sf.marineapi.nmea.io.DefaultDataReader
import net.sf.marineapi.nmea.sentence.SentenceId
import net.sf.marineapi.nmea.sentence.Sentence
import net.sf.marineapi.nmea.event.SentenceEvent
import java.io.BufferedReader
import java.lang.Runnable
import net.sf.marineapi.nmea.io.ActivityMonitor
import net.sf.marineapi.nmea.parser.SentenceFactory
import net.sf.marineapi.nmea.sentence.SentenceValidator
import net.sf.marineapi.nmea.parser.UnsupportedSentenceException
import java.lang.InterruptedException
import java.util.GregorianCalendar
import java.util.Calendar
import java.text.DecimalFormatSymbols
import net.sf.marineapi.nmea.util.FaaMode
import net.sf.marineapi.nmea.util.Datum
import net.sf.marineapi.nmea.util.CompassPoint
import net.sf.marineapi.nmea.util.Waypoint
import net.sf.marineapi.nmea.util.TurnMode
import net.sf.marineapi.nmea.util.RouteType
import net.sf.marineapi.nmea.util.DataStatus
import net.sf.marineapi.nmea.util.GpsFixStatus
import net.sf.marineapi.nmea.util.SteeringMode
import net.sf.marineapi.nmea.util.TargetStatus
import net.sf.marineapi.nmea.util.GpsFixQuality
import net.sf.marineapi.nmea.util.AcquisitionType
import net.sf.marineapi.nmea.util.DisplayRotation
import net.sf.marineapi.nmea.util.ReferenceSystem
import java.util.EventObject
import net.sf.marineapi.nmea.parser.SentenceParser
import net.sf.marineapi.nmea.sentence.TalkerId
import net.sf.marineapi.nmea.parser.AISParser
import net.sf.marineapi.nmea.sentence.APBSentence
import net.sf.marineapi.nmea.parser.APBParser
import java.lang.IllegalAccessError
import net.sf.marineapi.nmea.sentence.BODSentence
import net.sf.marineapi.nmea.parser.BODParser
import net.sf.marineapi.nmea.sentence.CURSentence
import net.sf.marineapi.nmea.parser.CURParser
import net.sf.marineapi.nmea.sentence.DBTSentence
import net.sf.marineapi.nmea.parser.DBTParser
import net.sf.marineapi.nmea.sentence.DPTSentence
import net.sf.marineapi.nmea.parser.DPTParser
import net.sf.marineapi.nmea.sentence.DTASentence
import net.sf.marineapi.nmea.parser.DTAParser
import java.text.DateFormat
import java.text.SimpleDateFormat
import net.sf.marineapi.nmea.sentence.DTBSentence
import net.sf.marineapi.nmea.sentence.DTMSentence
import net.sf.marineapi.nmea.parser.DTMParser
import net.sf.marineapi.nmea.sentence.GBSSentence
import net.sf.marineapi.nmea.parser.GBSParser
import net.sf.marineapi.nmea.parser.PositionParser
import net.sf.marineapi.nmea.sentence.GGASentence
import net.sf.marineapi.nmea.parser.GGAParser
import net.sf.marineapi.nmea.sentence.GLLSentence
import net.sf.marineapi.nmea.parser.GLLParser
import net.sf.marineapi.nmea.sentence.GNSSentence
import net.sf.marineapi.nmea.parser.GNSParser
import java.lang.StringBuffer
import net.sf.marineapi.nmea.sentence.GSASentence
import net.sf.marineapi.nmea.parser.GSAParser
import net.sf.marineapi.nmea.sentence.GSTSentence
import net.sf.marineapi.nmea.parser.GSTParser
import net.sf.marineapi.nmea.sentence.GSVSentence
import net.sf.marineapi.nmea.parser.GSVParser
import net.sf.marineapi.nmea.util.SatelliteInfo
import net.sf.marineapi.nmea.parser.DataNotAvailableException
import java.lang.IndexOutOfBoundsException
import net.sf.marineapi.nmea.sentence.HDGSentence
import net.sf.marineapi.nmea.parser.HDGParser
import net.sf.marineapi.nmea.sentence.HDMSentence
import net.sf.marineapi.nmea.parser.HDMParser
import net.sf.marineapi.nmea.sentence.HDTSentence
import net.sf.marineapi.nmea.parser.HDTParser
import net.sf.marineapi.nmea.sentence.HTCSentence
import net.sf.marineapi.nmea.parser.HTCParser
import net.sf.marineapi.nmea.sentence.HTDSentence
import net.sf.marineapi.nmea.parser.HTDParser
import net.sf.marineapi.nmea.sentence.MDASentence
import net.sf.marineapi.nmea.parser.MDAParser
import net.sf.marineapi.nmea.sentence.MHUSentence
import net.sf.marineapi.nmea.parser.MHUParser
import net.sf.marineapi.nmea.sentence.MMBSentence
import net.sf.marineapi.nmea.parser.MMBParser
import net.sf.marineapi.nmea.sentence.MTASentence
import net.sf.marineapi.nmea.parser.MTAParser
import net.sf.marineapi.nmea.sentence.MTWSentence
import net.sf.marineapi.nmea.parser.MTWParser
import net.sf.marineapi.nmea.sentence.MWDSentence
import net.sf.marineapi.nmea.parser.MWDParser
import net.sf.marineapi.nmea.sentence.MWVSentence
import net.sf.marineapi.nmea.parser.MWVParser
import net.sf.marineapi.nmea.sentence.OSDSentence
import net.sf.marineapi.nmea.parser.OSDParser
import net.sf.marineapi.nmea.sentence.RMBSentence
import net.sf.marineapi.nmea.parser.RMBParser
import net.sf.marineapi.nmea.sentence.RMCSentence
import net.sf.marineapi.nmea.parser.RMCParser
import net.sf.marineapi.nmea.sentence.ROTSentence
import net.sf.marineapi.nmea.parser.ROTParser
import net.sf.marineapi.nmea.sentence.RPMSentence
import net.sf.marineapi.nmea.parser.RPMParser
import net.sf.marineapi.nmea.sentence.RSASentence
import net.sf.marineapi.nmea.parser.RSAParser
import net.sf.marineapi.nmea.sentence.RSDSentence
import net.sf.marineapi.nmea.parser.RSDParser
import net.sf.marineapi.nmea.sentence.RTESentence
import net.sf.marineapi.nmea.parser.RTEParser
import net.sf.marineapi.nmea.sentence.TLBSentence
import net.sf.marineapi.nmea.parser.TLBParser
import net.sf.marineapi.nmea.sentence.TLLSentence
import net.sf.marineapi.nmea.parser.TLLParser
import net.sf.marineapi.nmea.sentence.TTMSentence
import net.sf.marineapi.nmea.parser.TTMParser
import net.sf.marineapi.nmea.sentence.TXTSentence
import net.sf.marineapi.nmea.parser.TXTParser
import net.sf.marineapi.nmea.sentence.UBXSentence
import net.sf.marineapi.nmea.sentence.VBWSentence
import net.sf.marineapi.nmea.parser.VBWParser
import net.sf.marineapi.nmea.sentence.VDRSentence
import net.sf.marineapi.nmea.parser.VDRParser
import net.sf.marineapi.nmea.sentence.VHWSentence
import net.sf.marineapi.nmea.parser.VHWParser
import net.sf.marineapi.nmea.sentence.VLWSentence
import net.sf.marineapi.nmea.parser.VLWParser
import net.sf.marineapi.nmea.sentence.VTGSentence
import net.sf.marineapi.nmea.parser.VTGParser
import net.sf.marineapi.nmea.sentence.VWRSentence
import net.sf.marineapi.nmea.parser.VWRParser
import net.sf.marineapi.nmea.sentence.VWTSentence
import net.sf.marineapi.nmea.parser.VWTParser
import net.sf.marineapi.nmea.sentence.WPLSentence
import net.sf.marineapi.nmea.parser.WPLParser
import net.sf.marineapi.nmea.sentence.XDRSentence
import net.sf.marineapi.nmea.parser.XDRParser
import net.sf.marineapi.nmea.util.Measurement
import net.sf.marineapi.nmea.sentence.XTESentence
import net.sf.marineapi.nmea.parser.XTEParser
import net.sf.marineapi.nmea.sentence.ZDASentence
import net.sf.marineapi.nmea.parser.ZDAParser
import net.sf.marineapi.nmea.sentence.STALKSentence
import net.sf.marineapi.nmea.parser.STALKParser
import java.lang.NumberFormatException
import java.lang.SecurityException
import java.lang.InstantiationException
import java.lang.IllegalAccessException
import java.lang.reflect.InvocationTargetException
import net.sf.marineapi.nmea.parser.UBXParser
import net.sf.marineapi.nmea.parser.VDOParser
import net.sf.marineapi.nmea.parser.DTBParser
import java.lang.RuntimeException
import net.sf.marineapi.nmea.sentence.DepthSentence
import net.sf.marineapi.nmea.sentence.TimeSentence
import net.sf.marineapi.nmea.sentence.PositionSentence
import net.sf.marineapi.nmea.sentence.HeadingSentence
import net.sf.marineapi.nmea.sentence.DateSentence
import java.lang.reflect.ParameterizedType
import net.sf.marineapi.ublox.util.UbloxSatelliteStatus
import net.sf.marineapi.ublox.util.UbloxNavigationStatus
import net.sf.marineapi.ublox.message.UBXMessage
import net.sf.marineapi.ublox.parser.UBXMessageFactory
import net.sf.marineapi.ublox.event.AbstractUBXMessageListener
import net.sf.marineapi.ublox.parser.UBXMessage00Parser
import net.sf.marineapi.ublox.parser.UBXMessage03Parser
import net.sf.marineapi.ublox.parser.UBXMessageParser
import net.sf.marineapi.ublox.message.UBXMessage00
import net.sf.marineapi.ublox.message.UBXMessage03
import net.sf.marineapi.ublox.util.UbloxSatelliteInfo
import java.io.FileInputStream
import net.sf.marineapi.example.FileExample
import java.io.IOException
import java.util.Enumeration
import net.sf.marineapi.example.SerialPortExample
import net.sf.marineapi.example.AISListenerExample
import net.sf.marineapi.example.UBXListenerExample.UBXMessage00Listener
import net.sf.marineapi.example.UBXListenerExample.UBXMessage03Listener
import net.sf.marineapi.example.UBXListenerExample
import net.sf.marineapi.provider.event.HeadingListener
import net.sf.marineapi.provider.HeadingProvider
import net.sf.marineapi.provider.event.HeadingEvent
import net.sf.marineapi.example.HeadingProviderExample
import net.sf.marineapi.provider.event.PositionListener
import net.sf.marineapi.provider.PositionProvider
import net.sf.marineapi.provider.event.PositionEvent
import net.sf.marineapi.example.PositionProviderExample
import net.sf.marineapi.example.SentenceListenerExamples.GSAListener
import net.sf.marineapi.example.SentenceListenerExamples.MultiSentenceListener
import net.sf.marineapi.example.SentenceListenerExamples.SingleSentenceListener
import net.sf.marineapi.example.SentenceListenerExamples
import net.sf.marineapi.provider.event.SatelliteInfoListener
import net.sf.marineapi.provider.SatelliteInfoProvider
import net.sf.marineapi.provider.event.SatelliteInfoEvent
import net.sf.marineapi.example.SatelliteInfoProviderExample
import net.sf.marineapi.example.TypedSentenceListenerExample
import net.sf.marineapi.provider.event.ProviderEvent
import net.sf.marineapi.provider.event.ProviderListener
import net.sf.marineapi.provider.AbstractProvider
