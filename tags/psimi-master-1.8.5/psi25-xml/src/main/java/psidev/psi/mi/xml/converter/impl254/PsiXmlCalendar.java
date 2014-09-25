/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.converter.impl254;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.io.Serializable;

/**
 * TODO comment this
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25-Sep-2006</pre>
 */
public class PsiXmlCalendar extends XMLGregorianCalendar implements Serializable {

    private XMLGregorianCalendar calendar;

    public PsiXmlCalendar( XMLGregorianCalendar calendar ) {
        this.calendar = calendar;
    }

    ////////////////////////////
    // PSI specific behaviour

    public String toXMLFormat() {
        StringBuffer sb = new StringBuffer();

        sb.append( calendar.getYear() );
        sb.append( "-" );
        int month = calendar.getMonth();
        if ( month < 10 ) {
            sb.append( "0" );
        }
        sb.append( month );
        sb.append( "-" );
        int day = calendar.getDay();
        if ( day < 10 ) {
            sb.append( "0" );
        }
        sb.append( day );

        return sb.toString();
    }

    /////////////////////////////////////////////////////////////////////////
    // Delegate methods of XMLGregorianCalendar to local variable calendar

    public void clear() {
        calendar.clear();
    }

    public void reset() {
        calendar.reset();
    }

    public void setYear( BigInteger year ) {
        calendar.setYear( year );
    }

    public void setYear( int year ) {
        calendar.setYear( year );
    }

    public void setMonth( int month ) {
        calendar.setMonth( month );
    }

    public void setDay( int day ) {
        calendar.setDay( day );
    }

    public void setTimezone( int offset ) {
        calendar.setTimezone( offset );
    }

    public void setTime( int hour, int minute, int second ) {
        calendar.setTime( hour, minute, second );
    }

    public void setHour( int hour ) {
        calendar.setHour( hour );
    }

    public void setMinute( int minute ) {
        calendar.setMinute( minute );
    }

    public void setSecond( int second ) {
        calendar.setSecond( second );
    }

    public void setMillisecond( int millisecond ) {
        calendar.setMillisecond( millisecond );
    }

    public void setFractionalSecond( BigDecimal fractional ) {
        calendar.setFractionalSecond( fractional );
    }

    public void setTime( int hour, int minute, int second, BigDecimal fractional ) {
        calendar.setTime( hour, minute, second, fractional );
    }

    public void setTime( int hour, int minute, int second, int millisecond ) {
        calendar.setTime( hour, minute, second, millisecond );
    }

    public BigInteger getEon() {
        return calendar.getEon();
    }

    public int getYear() {
        return calendar.getYear();
    }

    public BigInteger getEonAndYear() {
        return calendar.getEonAndYear();
    }

    public int getMonth() {
        return calendar.getMonth();
    }

    public int getDay() {
        return calendar.getDay();
    }

    public int getTimezone() {
        return calendar.getTimezone();
    }

    public int getHour() {
        return calendar.getHour();
    }

    public int getMinute() {
        return calendar.getMinute();
    }

    public int getSecond() {
        return calendar.getSecond();
    }

    public int getMillisecond() {
        return calendar.getMillisecond();
    }

    public BigDecimal getFractionalSecond() {
        return calendar.getFractionalSecond();
    }

    public int compare( XMLGregorianCalendar xmlGregorianCalendar ) {
        return calendar.compare( xmlGregorianCalendar );
    }

    public XMLGregorianCalendar normalize() {
        return calendar.normalize();
    }

    public boolean equals( Object obj ) {
        return calendar.equals( obj );
    }

    public int hashCode() {
        return calendar.hashCode();
    }

    public QName getXMLSchemaType() {
        return calendar.getXMLSchemaType();
    }

    public String toString() {
        return calendar.toString();
    }

    public boolean isValid() {
        return calendar.isValid();
    }

    public void add( Duration duration ) {
        calendar.add( duration );
    }

    public GregorianCalendar toGregorianCalendar() {
        return calendar.toGregorianCalendar();
    }

    public GregorianCalendar toGregorianCalendar( TimeZone timezone, Locale aLocale, XMLGregorianCalendar defaults ) {
        return calendar.toGregorianCalendar( timezone, aLocale, defaults );
    }

    public TimeZone getTimeZone( int defaultZoneoffset ) {
        return calendar.getTimeZone( defaultZoneoffset );
    }

    public Object clone() {
        return calendar.clone();
    }

}