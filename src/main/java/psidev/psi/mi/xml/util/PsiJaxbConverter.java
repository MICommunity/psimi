/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.util;

// com.sun.xml.bind.DatatypeConverterImpl

import com.sun.xml.bind.DatatypeConverterImpl;

import javax.xml.bind.DatatypeConverterInterface;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

/**
 * TODO comment this
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05-Jul-2006</pre>
 */
public class PsiJaxbConverter implements DatatypeConverterInterface {

    DatatypeConverterInterface converter = DatatypeConverterImpl.theInstance;

    ///////////////////////////////
    // Constructor

    public PsiJaxbConverter() {
    }

    ///////////////////////////////
    // Overriden methods

    public String printDate( Calendar calendar ) {

        StringBuffer sb = new StringBuffer( 10 );

        sb.append( calendar.get( Calendar.YEAR ) );
        sb.append( "-" );

        int month = calendar.get( Calendar.MONTH ) + 1;
        if ( month < 10 ) {
            sb.append( "0" );
        }
        sb.append( month );
        sb.append( "-" );

        int day = calendar.get( Calendar.DAY_OF_MONTH );
        if ( day < 10 ) {
            sb.append( "0" );
        }
        sb.append( day );

        return sb.toString();
    }

    ////////////////////////////
    // Delegated methods

    public String parseString( String s ) {
        return converter.parseString( s );
    }

    public BigInteger parseInteger( String s ) {
        return converter.parseInteger( s );
    }

    public int parseInt( String s ) {
        return converter.parseInt( s );
    }

    public long parseLong( String s ) {
        return converter.parseLong( s );
    }

    public short parseShort( String s ) {
        return converter.parseShort( s );
    }

    public BigDecimal parseDecimal( String s ) {
        return converter.parseDecimal( s );
    }

    public float parseFloat( String s ) {
        return converter.parseFloat( s );
    }

    public double parseDouble( String s ) {
        return converter.parseDouble( s );
    }

    public boolean parseBoolean( String s ) {
        return converter.parseBoolean( s );
    }

    public byte parseByte( String s ) {
        return converter.parseByte( s );
    }

    public QName parseQName( String s, NamespaceContext namespaceContext ) {
        return converter.parseQName( s, namespaceContext );
    }

    public Calendar parseDateTime( String s ) {
        return converter.parseDateTime( s );
    }

    public byte[] parseBase64Binary( String s ) {
        return converter.parseBase64Binary( s );
    }

    public byte[] parseHexBinary( String s ) {
        return converter.parseHexBinary( s );
    }

    public long parseUnsignedInt( String s ) {
        return converter.parseUnsignedInt( s );
    }

    public int parseUnsignedShort( String s ) {
        return converter.parseUnsignedShort( s );
    }

    public Calendar parseTime( String s ) {
        return converter.parseTime( s );
    }

    public Calendar parseDate( String s ) {
        return converter.parseDate( s );
    }

    public String parseAnySimpleType( String s ) {
        return converter.parseAnySimpleType( s );
    }

    public String printString( String s ) {
        return converter.printString( s );
    }

    public String printInteger( BigInteger bigInteger ) {
        return converter.printInteger( bigInteger );
    }

    public String printInt( int i ) {
        return converter.printInt( i );
    }

    public String printLong( long l ) {
        return converter.printLong( l );
    }

    public String printShort( short i ) {
        return converter.printShort( i );
    }

    public String printDecimal( BigDecimal bigDecimal ) {
        return converter.printDecimal( bigDecimal );
    }

    public String printFloat( float v ) {
        return converter.printFloat( v );
    }

    public String printDouble( double v ) {
        return converter.printDouble( v );
    }

    public String printBoolean( boolean b ) {
        return converter.printBoolean( b );
    }

    public String printByte( byte b ) {
        return converter.printByte( b );
    }

    public String printQName( QName qName, NamespaceContext namespaceContext ) {
        return converter.printQName( qName, namespaceContext );
    }

    public String printDateTime( Calendar calendar ) {
        return converter.printDateTime( calendar );
    }

    public String printBase64Binary( byte[] bytes ) {
        return converter.printBase64Binary( bytes );
    }

    public String printHexBinary( byte[] bytes ) {
        return converter.printHexBinary( bytes );
    }

    public String printUnsignedInt( long l ) {
        return converter.printUnsignedInt( l );
    }

    public String printUnsignedShort( int i ) {
        return converter.printUnsignedShort( i );
    }

    public String printTime( Calendar calendar ) {
        return converter.printTime( calendar );
    }

    public String printAnySimpleType( String s ) {
        return converter.printAnySimpleType( s );
    }
}
