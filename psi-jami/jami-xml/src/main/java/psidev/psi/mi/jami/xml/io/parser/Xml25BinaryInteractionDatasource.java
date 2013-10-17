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

public class Xml25BinaryInteractionDatasource extends AbstractPsiXmlBinaryDataSource<Interaction<? extends Participant>, BinaryInteraction>{

    public Xml25BinaryInteractionDatasource() {
    }

    public Xml25BinaryInteractionDatasource(File file) throws IOException {
        super(file);
    }

    public Xml25BinaryInteractionDatasource(InputStream input) {
        super(input);
    }

    public Xml25BinaryInteractionDatasource(Reader reader) {
        super(reader);
    }

    public Xml25BinaryInteractionDatasource(URL url) {
        super(url);
    }

    @Override
    protected void initialiseXmlParser(Reader reader) {
        try {
            Xml25BinaryInteractionParser parser = new Xml25BinaryInteractionParser(reader);
            parser.setListener(this);
            parser.setExpansionMethod(getComplexExpansion());
            setParser(parser);
        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to read with provided reader ",e);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to read with provided reader",e);
        } catch (FileNotFoundException e) {
            throw new MIIOException("Impossible to read with provided reader",e);
        }
    }

    @Override
    protected void initialiseXmlParser(File file) {
        try {
            Xml25BinaryInteractionParser parser = new Xml25BinaryInteractionParser(file);
            parser.setListener(this);
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
            Xml25BinaryInteractionParser parser = new Xml25BinaryInteractionParser(input);
            parser.setListener(this);
            parser.setExpansionMethod(getComplexExpansion());
            setParser(parser);
        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to parse the inputstream ",e);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to parse the inputstream ",e);
        } catch (FileNotFoundException e) {
            throw new MIIOException("Impossible to parse the inputstream ",e);
        }
    }

    @Override
    protected void initialiseXmlParser(URL url) {
        try {
            Xml25BinaryInteractionParser parser = new Xml25BinaryInteractionParser(url);
            parser.setListener(this);
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
