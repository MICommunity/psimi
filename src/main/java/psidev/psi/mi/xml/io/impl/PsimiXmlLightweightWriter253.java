/**
 * Copyright 2008 The European Bioinformatics Institute, and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package psidev.psi.mi.xml.io.impl;

import psidev.psi.mi.xml.PsimiXmlWriterException;
import psidev.psi.mi.xml.io.PsimiXmlLightweightWriter;
import psidev.psi.mi.xml.model.Attribute;
import psidev.psi.mi.xml.model.Availability;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Source;

import java.io.*;
import java.util.List;

/**
 * Lightweight XML writter that allows to build piece by piece an XML file. Providing memory efficient alternative to
 * having the wholeobject model loaded at once. This works particularly well when using the PsimiXmlLightweightReader.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.5.0
 */
public class PsimiXmlLightweightWriter253 implements PsimiXmlLightweightWriter {

    private static final String LINE_RETURN = System.getProperty( "line.separator" );
    private static final String INDENT = "    ";

    private PsimiXmlStringWriter253 stringWriter = null;

    private BufferedWriter buffer;

    private boolean writerProvided = false;

    private boolean wasInteractionListWritten = false;

    public PsimiXmlLightweightWriter253( File outputFile ) throws PsimiXmlWriterException {
        if ( outputFile == null ) {
            throw new IllegalArgumentException( "You must provide a non null output file" );
        }
        if ( !outputFile.exists() ) {
            if ( !outputFile.getParentFile().canWrite() ) {
                throw new IllegalArgumentException( "You must provide writeable parent directory for the output file" );
            }
        } else {
            if ( !outputFile.canWrite() ) {
                throw new IllegalArgumentException( "You must provide a writeable output file" );
            }
        }

        try {
            this.buffer = new BufferedWriter( new FileWriter( outputFile ) );
        } catch ( IOException e ) {
            throw new PsimiXmlWriterException( "Error while setting up the writer", e );
        }
        stringWriter = new PsimiXmlStringWriter253();
    }

    public PsimiXmlLightweightWriter253( Writer writer ) {
        if ( writer == null ) {
            throw new IllegalArgumentException( "You must provide a non null writer" );
        }

        this.buffer = new BufferedWriter( writer );
        this.writerProvided = true;
        stringWriter = new PsimiXmlStringWriter253();
    }

    ////////////////////
    // PsimiXmlWriter

    public void writeStartDocument() throws PsimiXmlWriterException {
        writeln( "<entrySet version=\"5\" minorVersion=\"3\" level=\"2\" xsi:schemaLocation=\"net:sf:psidev:mi http://psidev.sourceforge.net/mi/rel25/src/MIF25.xsd\" xmlns=\"net:sf:psidev:mi\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" );
    }

    public void writeStartEntry( Source source, List<Availability> availabilities ) throws PsimiXmlWriterException {

        writeln( INDENT + "<entry>" );
        // write source
        if ( source != null ) {
            // convert source to a String
            write( stringWriter.write( source ) + LINE_RETURN );
        }

        // write availabilities
        if ( availabilities != null && !availabilities.isEmpty() ) {
            writeln( INDENT + INDENT + "<availabilityList>" );
            for ( Availability availability : availabilities ) {
                write( INDENT + INDENT + INDENT + stringWriter.write( availability ) );
            }
            writeln( INDENT + INDENT + "</availabilityList>" );
        }
    }

    public void writeInteraction( Interaction interaction ) throws PsimiXmlWriterException {
        if ( !wasInteractionListWritten ) {
            writeln( "<interactionList>" );
            wasInteractionListWritten = true;
        }

        write( stringWriter.write( interaction ) + LINE_RETURN );
    }

    public void writeEndEntry( List<Attribute> attributes ) throws PsimiXmlWriterException {
        writeln( INDENT + INDENT + "</interactionList>" );

        // write attributes
        if ( attributes != null && !attributes.isEmpty() ) {
            writeln( INDENT + INDENT + "<attributeList>" );
            for ( Attribute attribute : attributes ) {
                writeln( INDENT + INDENT + INDENT + stringWriter.write( attribute ) );
            }
            writeln( INDENT + INDENT + "</attributeList>" );
        }

        writeln( INDENT + "</entry>" );

        wasInteractionListWritten = false;
    }

    public void writeEndDocument() throws PsimiXmlWriterException {
        writeln( "</entrySet>" );
        closeOutputFile();
    }

    public void closeOutputFile() throws PsimiXmlWriterException {
        try {
            buffer.flush();

            if ( !writerProvided ) {
                buffer.close();
            }
        } catch ( Exception e ) {
            throw new PsimiXmlWriterException( "An error occured while closing the output buffer" );
        }
    }

    ///////////////////
    // Private methods

    private void writeln( String s ) throws PsimiXmlWriterException {
        try {
            buffer.write( s + LINE_RETURN );
        } catch ( IOException e ) {
            throw new PsimiXmlWriterException( "Error while writing to the output", e );
        }
    }

    private void write( String s ) throws PsimiXmlWriterException {
        try {
            buffer.write( s );
        } catch ( IOException e ) {
            throw new PsimiXmlWriterException( "Error while writing to the output", e );
        }
    }
}