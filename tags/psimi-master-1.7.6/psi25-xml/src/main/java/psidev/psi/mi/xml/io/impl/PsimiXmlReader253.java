/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.io.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.converter.impl253.EntrySetConverter;
import psidev.psi.mi.xml.dao.DAOFactory;
import psidev.psi.mi.xml.dao.inMemory.InMemoryDAOFactory;
import psidev.psi.mi.xml.io.PsimiXmlReader;
import psidev.psi.mi.xml253.jaxb.EntrySet;
import psidev.psi.mi.xml253.jaxb.ObjectFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URL;

/**
 * Read PSI MI data from various sources.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07-Jun-2006</pre>
 */
public class PsimiXmlReader253 implements PsimiXmlReader {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( PsimiXmlReader253.class );

    //////////////////////////
    // Public methods

    public psidev.psi.mi.xml.model.EntrySet read( String s ) throws PsimiXmlReaderException {
        return read(new StringReader(s));
    }

    public psidev.psi.mi.xml.model.EntrySet read( File file ) throws PsimiXmlReaderException {
        try {
            return read(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new PsimiXmlReaderException(e);
        }
    }

    public psidev.psi.mi.xml.model.EntrySet read( InputStream is ) throws PsimiXmlReaderException {
        return read ( new InputStreamReader(is) );
    }

    public psidev.psi.mi.xml.model.EntrySet read( URL url ) throws PsimiXmlReaderException {
        try {
            return read(url.openStream());
        } catch (IOException e) {
            throw new PsimiXmlReaderException("Problem reading from String");
        }
    }

    public psidev.psi.mi.xml.model.EntrySet read( Reader reader ) throws PsimiXmlReaderException {

        psidev.psi.mi.xml253.jaxb.EntrySet jEntrySet = unmarshall( reader );

        return convertInMemory( jEntrySet );
    }

    ////////////////////////
    // Private methods

    private psidev.psi.mi.xml253.jaxb.EntrySet unmarshall( Reader reader ) throws PsimiXmlReaderException {

        if ( reader == null ) {
            throw new IllegalArgumentException( "You must give a non null reader." );
        }

        try {
            // create an Unmarshaller
            Unmarshaller u = getUnmarshaller();

            // unmarshal an entrySet instance document into a tree of Java content objects composed of classes from the jaxb package.
            psidev.psi.mi.xml253.jaxb.EntrySet es = ( psidev.psi.mi.xml253.jaxb.EntrySet ) u.unmarshal( reader );

            return es;
        } catch ( Exception e ) {
            throw new PsimiXmlReaderException( "An error occured while unmarshalling data", e );
        }
    }


    private Unmarshaller getUnmarshaller() throws PsimiXmlReaderException {

        try {
            // create a JAXBContext capable of handling classes generated into the jaxb package
            ClassLoader cl = ObjectFactory.class.getClassLoader();
            JAXBContext jc = JAXBContext.newInstance( EntrySet.class.getPackage().getName(), cl );

            // create and return Unmarshaller
            return jc.createUnmarshaller();
        } catch ( Exception e ) {
            throw new PsimiXmlReaderException( "An error occured while building an unmarshaller", e );
        }
    }

    private psidev.psi.mi.xml.model.EntrySet convertInMemory( psidev.psi.mi.xml253.jaxb.EntrySet jEntrySet ) throws PsimiXmlReaderException {

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