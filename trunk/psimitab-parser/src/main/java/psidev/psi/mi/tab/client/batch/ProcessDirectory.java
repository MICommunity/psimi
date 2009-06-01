/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.client.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.PsimiTabWriter;
import psidev.psi.mi.tab.converter.xml2tab.TabConversionException;
import psidev.psi.mi.tab.converter.xml2tab.Xml2Tab;
import psidev.psi.mi.tab.expansion.SpokeExpansion;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.processor.ClusterInteractorPairProcessor;
import psidev.psi.mi.xml.converter.ConverterException;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * TODO comment this
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01-Nov-2006</pre>
 */
public class ProcessDirectory {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( ProcessDirectory.class );

    public static void main( String[] args ) throws JAXBException, IOException, TabConversionException, ConverterException {

        if( args.length != 1 ) {
            System.err.println( "usage: ProcessDirectory <dir>" );
            System.exit( 1 );
        }

        File dir = new File( args[0] );
        if( ! dir.exists() ) {
            throw new IllegalArgumentException( "Directory doesn't exists: " + dir.getAbsolutePath() );
        }

        if( ! dir.canRead() ) {
            throw new IllegalArgumentException( "Cannot read directory: " + dir.getAbsolutePath() );
        }

        long start = System.currentTimeMillis();

        // Prepare XML to MITAB25 converter
        Xml2Tab x2t = new Xml2Tab();

        // set strategies
        x2t.setPostProcessor( new ClusterInteractorPairProcessor() );
        System.out.println( "Applying Spoke model expansion wherever appropriate." );
        x2t.setExpansionStrategy( new SpokeExpansion() );

        Collection<BinaryInteraction> interactions = x2t.convert( dir );

        PsimiTabWriter writer = new PsimiTabWriter();
        writer.write( interactions, new File( "C:\\psimitab.csv" ) );

        long stop = System.currentTimeMillis();
        log.debug( "Conversion took: " + ( stop - start ) + "ms" );
    }
}