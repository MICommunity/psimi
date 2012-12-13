/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.io.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.xml.PsimiXmlWriterException;
import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.converter.impl253.EntrySetConverter;
import psidev.psi.mi.xml.dao.DAOFactory;
import psidev.psi.mi.xml.dao.inMemory.InMemoryDAOFactory;
import psidev.psi.mi.xml.io.PsimiXmlWriter;
import psidev.psi.mi.xml.model.EntrySet;
import psidev.psi.mi.xml.util.PsiJaxbConverter;
import psidev.psi.mi.xml253.jaxb.ObjectFactory;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;

/**
 * Write PSI MI data to various format.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07-Jun-2006</pre>
 */
public class PsimiXmlWriter253 implements PsimiXmlWriter {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( PsimiXmlWriter253.class );

    /////////////////////////
    // Private methods

    private Marshaller getMarshaller( psidev.psi.mi.xml253.jaxb.EntrySet jEntrySet ) throws PsimiXmlWriterException {

        try {
            // create a JAXBContext capable of handling classes generated into the jaxb package
            ClassLoader cl = ObjectFactory.class.getClassLoader();
            JAXBContext jc = JAXBContext.newInstance( psidev.psi.mi.xml253.jaxb.EntrySet.class.getPackage().getName(), cl );

            // setup customized converter
            DatatypeConverter.setDatatypeConverter( new PsiJaxbConverter() );

            // create and return Unmarshaller
            Marshaller marshaller = jc.createMarshaller();
            // configure marshaller
            marshaller.setProperty( Marshaller.JAXB_SCHEMA_LOCATION, calculateSchemaLocation( jEntrySet.getLevel(), jEntrySet.getVersion(), jEntrySet.getMinorVersion() ) );

            return marshaller;
        } catch ( Exception e ) {
            throw new PsimiXmlWriterException( "En error occured while writing EntrySet", e );
        }
    }

    public void marshall( psidev.psi.mi.xml253.jaxb.EntrySet jEntrySet, OutputStream os ) throws PsimiXmlWriterException {

        if ( os == null ) {
            throw new IllegalArgumentException( "You must give a non null otuput stream." );
        }

        try {
            // create a marshaller
            Marshaller m = getMarshaller( jEntrySet );
            m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            m.marshal( jEntrySet, os );
        } catch ( Exception e ) {
            throw new PsimiXmlWriterException( "En error occured while writing EntrySet", e );
        }
    }

    public void marshall( psidev.psi.mi.xml253.jaxb.EntrySet jEntrySet, Writer writer ) throws PsimiXmlWriterException {

        if ( writer == null ) {
            throw new IllegalArgumentException( "You must give a non null writer." );
        }

        try {
            // create a marshaller
            Marshaller m = getMarshaller( jEntrySet );
            m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            m.marshal( jEntrySet, writer );
        } catch ( Exception e ) {
            throw new PsimiXmlWriterException( "En error occured while writing EntrySet", e );
        }
    }

    private void marshall( psidev.psi.mi.xml253.jaxb.EntrySet jEntrySet, File file ) throws PsimiXmlWriterException {
        try {
            // create a marshaller
            Marshaller m = getMarshaller( jEntrySet );

            m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            m.marshal( jEntrySet, new FileWriter( file ) );
        } catch ( Exception e ) {
            throw new PsimiXmlWriterException( "En error occured while writing EntrySet", e );
        }
    }

    private String marshall( psidev.psi.mi.xml253.jaxb.EntrySet jEntrySet ) throws PsimiXmlWriterException {

        Writer writer = new StringWriter( 4096 );

        try {
            Marshaller marshaller = getMarshaller( jEntrySet );
            marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal( jEntrySet, writer );

            writer.close();
            return writer.toString();
        } catch ( Exception e ) {
            throw new PsimiXmlWriterException( "En error occured while writing EntrySet", e );
        }
    }

    private psidev.psi.mi.xml253.jaxb.EntrySet convertInMemory( EntrySet mEntrySet ) throws PsimiXmlWriterException {

        // create a converter
        EntrySetConverter converter = new EntrySetConverter();

        // initialise DAO
        DAOFactory dao = new InMemoryDAOFactory();
        converter.setDAOFactory( dao );

        try {
            // convert JAXB model
            psidev.psi.mi.xml253.jaxb.EntrySet jEntrySet = converter.toJaxb( mEntrySet );

            return jEntrySet;
        } catch ( Exception e ) {
            throw new PsimiXmlWriterException( "En error occured while writing EntrySet", e );
        }
    }

    /////////////////////////
    // public methods

    public void write( EntrySet mEntrySet, File file ) throws PsimiXmlWriterException {

        if ( file == null ) {
            throw new IllegalArgumentException( "You must give a non null file." );
        }

        if ( file.exists() ) {
            log.warn( file.getAbsolutePath() + " is going to be overwritten" );

            if ( !file.canWrite() ) {
                throw new IllegalArgumentException( "You must give a writeable file." );
            }
        } else {
            try {
                if ( !file.createNewFile() ) {
                    throw new PsimiXmlWriterException( "Could not create file: " + file.getAbsolutePath() );
                }
            } catch ( IOException e ) {
                throw new PsimiXmlWriterException( "Could not create file: " + file.getAbsolutePath(), e );
            }
        }

        psidev.psi.mi.xml253.jaxb.EntrySet jEntrySet = convertInMemory( mEntrySet );

        // marshall it to a file
        marshall( jEntrySet, file );
    }

    public void write( EntrySet mEntrySet, OutputStream os ) throws PsimiXmlWriterException {
        psidev.psi.mi.xml253.jaxb.EntrySet jEntrySet = convertInMemory( mEntrySet );

        // marshall it to a file
        marshall( jEntrySet, os );
    }

    public void write( EntrySet mEntrySet, Writer writer ) throws IOException, ConverterException, JAXBException, PsimiXmlWriterException {
        psidev.psi.mi.xml253.jaxb.EntrySet jEntrySet = convertInMemory( mEntrySet );

        // marshall it to a file
        marshall( jEntrySet, writer );
    }

    public void write( EntrySet mEntrySet, PrintStream ps ) throws IOException, ConverterException, JAXBException, PsimiXmlWriterException {
        psidev.psi.mi.xml253.jaxb.EntrySet jEntrySet = convertInMemory( mEntrySet );

        // marshall it to a file
        String xml = marshall( jEntrySet );

        ps.println( xml );
    }

    public String getAsString( EntrySet mEntrySet ) throws PsimiXmlWriterException {
        psidev.psi.mi.xml253.jaxb.EntrySet jEntrySet = convertInMemory( mEntrySet );

        // marshall it to a file
        String xml = marshall( jEntrySet );

        return xml;
    }

    public static String calculateSchemaLocation( int majorVersion, int minorVersion, int bugfix ) {

        String versionSuffix;

        if ( majorVersion > 0 && minorVersion != 0 ) {
            versionSuffix = String.valueOf( majorVersion ) + String.valueOf( minorVersion ) + String.valueOf( bugfix );
        } else {
            versionSuffix = "253";
        }

        return "net:sf:psidev:mi http://psidev.sourceforge.net/mi/rel25/src/MIF" + versionSuffix + ".xsd";
    }
}