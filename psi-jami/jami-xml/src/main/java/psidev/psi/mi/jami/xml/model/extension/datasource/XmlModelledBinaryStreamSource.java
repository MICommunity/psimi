package psidev.psi.mi.jami.xml.model.extension.datasource;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.datasource.ModelledBinaryInteractionStream;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.xml.io.iterator.XmlModelledBinaryInteractionIterator;
import psidev.psi.mi.jami.xml.io.parser.XmlModelledBinaryParser;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.URL;
import java.util.Iterator;

/**
 * Psi-xml 2.5 datasource for modelled binary interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public class XmlModelledBinaryStreamSource extends AbstractPsiXmlBinaryStream<ModelledInteraction, ModelledBinaryInteraction> implements ModelledBinaryInteractionStream{

    public XmlModelledBinaryStreamSource() {
    }

    public XmlModelledBinaryStreamSource(File file) {
        super(file);
    }

    public XmlModelledBinaryStreamSource(InputStream input) {
        super(input);
    }

    public XmlModelledBinaryStreamSource(Reader reader) {
        super(reader);
    }

    public XmlModelledBinaryStreamSource(URL url) {
        super(url);
    }

    @Override
    protected void initialiseXmlParser(Reader reader) {
        try {
            XmlModelledBinaryParser parser = new XmlModelledBinaryParser(reader);
            parser.setListener(this);
            parser.setCacheOfObjects(getElementCache());
            parser.setExpansionMethod(getComplexExpansion());
            setParser(parser);
        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to read with provided reader ",e);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to read with provided reader",e);
        }
    }

    @Override
    protected void initialiseXmlParser(File file) {
        try {
            XmlModelledBinaryParser parser = new XmlModelledBinaryParser(file);
            parser.setListener(this);
            parser.setCacheOfObjects(getElementCache());
            parser.setExpansionMethod(getComplexExpansion());
            setParser(parser);
        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to parse the file "+file.getName(),e);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to parse the file "+file.getName(),e);
        } catch (FileNotFoundException e) {
            throw new MIIOException("Impossible to parse the file "+file.getName(),e);
        }
    }

    @Override
    protected void initialiseXmlParser(InputStream input) {
        try {
            XmlModelledBinaryParser parser = new XmlModelledBinaryParser(input);
            parser.setListener(this);
            parser.setCacheOfObjects(getElementCache());
            parser.setExpansionMethod(getComplexExpansion());
            setParser(parser);
        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to parse the inputstream ",e);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to parse the inputstream ",e);
        }
    }

    @Override
    protected void initialiseXmlParser(URL url) {
        try {
            XmlModelledBinaryParser parser = new XmlModelledBinaryParser(url);
            parser.setListener(this);
            parser.setCacheOfObjects(getElementCache());
            parser.setExpansionMethod(getComplexExpansion());
            setParser(parser);
        } catch (IOException e) {
            throw new MIIOException("Impossible to read the url "+url.toExternalForm(),e);
        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to parse the url "+url.toExternalForm(),e);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to parse the url "+url.toExternalForm(),e);
        }
    }

    @Override
    protected Iterator<ModelledBinaryInteraction> createXmlIterator() {
        return new XmlModelledBinaryInteractionIterator(getParser());
    }
}
