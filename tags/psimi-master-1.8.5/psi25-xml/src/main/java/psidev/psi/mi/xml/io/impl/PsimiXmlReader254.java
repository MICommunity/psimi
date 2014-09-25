/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.io.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.converter.impl254.EntrySetConverter;
import psidev.psi.mi.xml.dao.DAOFactory;
import psidev.psi.mi.xml.dao.inMemory.InMemoryDAOFactory;
import psidev.psi.mi.xml.io.PsimiXmlReader;
import psidev.psi.mi.xml254.jaxb.EntrySet;
import psidev.psi.mi.xml254.jaxb.ObjectFactory;

/**
 * Read PSI MI data from various sources.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07-Jun-2006</pre>
 */
public class PsimiXmlReader254 implements PsimiXmlReader {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( PsimiXmlReader254.class );
    
    // Custom buffer size for buffered readers.  This is for performance tuning.
    private static final int BUFFER_SIZE = 100000;

    //////////////////////////
    // Public methods

    public psidev.psi.mi.xml.model.EntrySet read(final String s ) throws PsimiXmlReaderException {
        return read(new StringReader(s));
    }

    public psidev.psi.mi.xml.model.EntrySet read(final File file ) throws PsimiXmlReaderException {
        try {
            return read(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new PsimiXmlReaderException(e);
        }
    }

    public psidev.psi.mi.xml.model.EntrySet read(final InputStream is ) throws PsimiXmlReaderException {
        return read (new InputStreamReader(is));
    }

    public psidev.psi.mi.xml.model.EntrySet read(final URL url ) throws PsimiXmlReaderException {
        try {
            return read(new InputStreamReader(url.openStream()));
        } catch (IOException e) {
            throw new PsimiXmlReaderException("Problem reading from String");
        }
    }

    public psidev.psi.mi.xml.model.EntrySet read(final Reader reader ) throws PsimiXmlReaderException {
    		// Read using buffered stream for performance.
		final Reader bufferedReader = new BufferedReader(reader, BUFFER_SIZE);
		
        psidev.psi.mi.xml254.jaxb.EntrySet jEntrySet = unmarshall(bufferedReader);
        return convertInMemory( jEntrySet );
    }

    ////////////////////////
    // Private methods

    private psidev.psi.mi.xml254.jaxb.EntrySet unmarshall(final Reader reader) throws PsimiXmlReaderException {

        if ( reader == null ) {
            throw new IllegalArgumentException( "You must give a non null reader." );
        }

        try {
            // create an Unmarshaller
            final Unmarshaller u = getUnmarshaller();

            // unmarshal an entrySet instance document into a tree of Java content objects composed of classes from the jaxb package.
            final psidev.psi.mi.xml254.jaxb.EntrySet es = ( psidev.psi.mi.xml254.jaxb.EntrySet ) u.unmarshal( reader );

            return es;
        } catch ( Exception e ) {
            throw new PsimiXmlReaderException( "An error occured while unmarshalling data", e );
        }
    }


    private Unmarshaller getUnmarshaller() throws PsimiXmlReaderException {

        try {
            // create a JAXBContext capable of handling classes generated into the jaxb package
        	
        		// This is necessary on OSGi environment to specify correct classloader.
            final ClassLoader cl = ObjectFactory.class.getClassLoader();
            final JAXBContext jc = JAXBContext.newInstance( EntrySet.class.getPackage().getName(), cl );

            // create and return Unmarshaller
            return jc.createUnmarshaller();
        } catch ( Exception e ) {
            throw new PsimiXmlReaderException( "An error occured while building an unmarshaller", e );
        }
    }

    private psidev.psi.mi.xml.model.EntrySet convertInMemory( psidev.psi.mi.xml254.jaxb.EntrySet jEntrySet ) throws PsimiXmlReaderException {

        // create a converter
        EntrySetConverter converter = new EntrySetConverter();

        // initialise DAO
        DAOFactory dao = new InMemoryDAOFactory();
        converter.setDAOFactory( dao );

        try {
            // convert JAXB model
            psidev.psi.mi.xml.model.EntrySet mEntrySet = converter.fromJaxb( jEntrySet );

            return mEntrySet;
        } catch ( ConverterException e ) {
            throw new PsimiXmlReaderException( "An error occured while converting the data model", e );
        }
    }
}