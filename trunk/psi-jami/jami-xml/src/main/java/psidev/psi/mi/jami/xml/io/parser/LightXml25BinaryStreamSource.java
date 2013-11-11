package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.io.iterator.Xml25BinaryInteractionIterator;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.URL;
import java.util.Iterator;

/**
 * Psi-XML 2.5 binary interaction datasource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public class LightXml25BinaryStreamSource extends AbstractPsiXml25BinaryStream<Interaction<? extends Participant>, BinaryInteraction> {

    public LightXml25BinaryStreamSource() {
    }

    public LightXml25BinaryStreamSource(File file) {
        super(file);
    }

    public LightXml25BinaryStreamSource(InputStream input) {
        super(input);
    }

    public LightXml25BinaryStreamSource(Reader reader) {
        super(reader);
    }

    public LightXml25BinaryStreamSource(URL url) {
        super(url);
    }

    @Override
    protected void initialiseXmlParser(Reader reader) {
        try {
            Xml25BinaryParser parser = new Xml25BinaryParser(reader);
            parser.setListener(this);
            parser.setCacheOfComplexes(getComplexCache());
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
            Xml25BinaryParser parser = new Xml25BinaryParser(file);
            parser.setListener(this);
            parser.setCacheOfComplexes(getComplexCache());
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
            Xml25BinaryParser parser = new Xml25BinaryParser(input);
            parser.setListener(this);
            parser.setCacheOfComplexes(getComplexCache());
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
            Xml25BinaryParser parser = new Xml25BinaryParser(url);
            parser.setListener(this);
            parser.setCacheOfComplexes(getComplexCache());
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
    protected Iterator<BinaryInteraction> createXmlIterator() {
        return new Xml25BinaryInteractionIterator(getParser());
    }
}
