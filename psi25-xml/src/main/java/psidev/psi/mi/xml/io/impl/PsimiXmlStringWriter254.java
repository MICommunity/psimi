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
import psidev.psi.mi.xml.converter.impl254.AttributeConverter;
import psidev.psi.mi.xml.converter.impl254.AvailabilityConverter;
import psidev.psi.mi.xml.converter.impl254.InteractionConverter;
import psidev.psi.mi.xml.converter.impl254.SourceConverter;
import psidev.psi.mi.xml.dao.lazy.LazyDAOFactory;
import psidev.psi.mi.xml.io.PsimiXmlStringWriter;
import psidev.psi.mi.xml.model.Attribute;
import psidev.psi.mi.xml.model.Availability;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Source;
import psidev.psi.mi.xml.util.PsiJaxbConverter;
import psidev.psi.mi.xml254.jaxb.EntrySet;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Converts objects to XML as a String.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.5.0
 */
public class PsimiXmlStringWriter254 implements PsimiXmlStringWriter {

    private JAXBContext jaxbContext;
    private static final String PSI_MI_NAMESPACE = "http://psi.hupo.org/mi/mif";

    public PsimiXmlStringWriter254() {
        try {
            this.jaxbContext = JAXBContext.newInstance( EntrySet.class.getPackage().getName() );
        } catch ( JAXBException e ) {
            throw new RuntimeException( e );
        }
    }

    ///////////////////
    // Public methods

    public String write( Source source ) throws PsimiXmlWriterException {
        try {
            final SourceConverter sourceConverter = new SourceConverter();
            final psidev.psi.mi.xml254.jaxb.Source s = sourceConverter.toJaxb( source );

            Writer writer = new StringWriter( 4096 );
            getMarshaller().marshal( new JAXBElement( new QName( PSI_MI_NAMESPACE, "source" ),
                    psidev.psi.mi.xml254.jaxb.Source.class,
                    s ),
                    writer );

            writer.close();
            String xml = writer.toString();
            xml = removeNameSpace( xml );
            return xml;
        } catch ( Exception e ) {
            throw new PsimiXmlWriterException( "An error occured while write a source", e );
        }
    }

    public String write( Availability availability ) throws PsimiXmlWriterException {
        final AvailabilityConverter ac = new AvailabilityConverter();
        final psidev.psi.mi.xml254.jaxb.Availability a = ac.toJaxb( availability );

        Writer writer = new StringWriter( 4096 );
        try {
            getMarshaller().marshal( new JAXBElement( new QName( PSI_MI_NAMESPACE, "availability" ),
                    psidev.psi.mi.xml254.jaxb.Availability.class,
                    a ),
                    writer );

            writer.close();
            String xml = writer.toString();
            xml = removeNameSpace( xml );
            return xml;
        } catch ( Exception e ) {
            throw new PsimiXmlWriterException( "An error occured while write availability", e );
        }
    }

    public String write( Interaction interaction ) throws PsimiXmlWriterException {
        final InteractionConverter ic = new InteractionConverter();
        ic.setDAOFactory( new LazyDAOFactory() );
        try {
            final psidev.psi.mi.xml254.jaxb.Interaction i = ic.toJaxb( interaction );

            Writer writer = new StringWriter( 4096 );
            getMarshaller().marshal( new JAXBElement( new QName( PSI_MI_NAMESPACE, "interaction" ),
                    psidev.psi.mi.xml254.jaxb.Interaction.class,
                    i ),
                    writer );

            writer.close();
            String xml = writer.toString();
            xml = removeNameSpace( xml );
            return xml;
        } catch ( Exception e ) {
            throw new PsimiXmlWriterException( "An error occured while write an interaction", e );
        }
    }

    public String write( Attribute attribute ) throws PsimiXmlWriterException {
        final AttributeConverter ac = new AttributeConverter();
        final psidev.psi.mi.xml254.jaxb.Attribute a = ac.toJaxb( attribute );

        Writer writer = new StringWriter( 4096 );
        try {
            getMarshaller().marshal( new JAXBElement( new QName( PSI_MI_NAMESPACE, "attribute" ),
                    psidev.psi.mi.xml254.jaxb.Attribute.class,
                    a ),
                    writer );

            writer.close();
            String xml = writer.toString();
            xml = removeNameSpace( xml );
            return xml;
        } catch ( Exception e ) {
            throw new PsimiXmlWriterException( "An error occured while write attribute", e );
        }
    }

    /////////////////////
    // Private

    private Marshaller getMarshaller() throws JAXBException {
        // setup customized converter
        DatatypeConverter.setDatatypeConverter( new PsiJaxbConverter() );

        // create and return Unmarshaller
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
        marshaller.setProperty( Marshaller.JAXB_FRAGMENT, Boolean.TRUE );
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        return marshaller;
    }

    private String removeNameSpace( String xml ) {
        return xml.replace( " xmlns=\"" + PSI_MI_NAMESPACE + "\"", "" );
    }
}