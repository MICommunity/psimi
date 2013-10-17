package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.xml.io.iterator.Xml25ModelledBinaryInteractionIterator;

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

public class Xml25ModelledBinaryInteractionDatasource extends AbstractPsiXmlBinaryDataSource<ModelledInteraction, ModelledBinaryInteraction>{

    public Xml25ModelledBinaryInteractionDatasource() {
    }

    public Xml25ModelledBinaryInteractionDatasource(File file) throws IOException {
        super(file);
    }

    public Xml25ModelledBinaryInteractionDatasource(InputStream input) {
        super(input);
    }

    public Xml25ModelledBinaryInteractionDatasource(Reader reader) {
        super(reader);
    }

    public Xml25ModelledBinaryInteractionDatasource(URL url) {
        super(url);
    }

    @Override
    protected void initialiseXmlParser(Reader reader) {
        try {
            Xml25ModelledBinaryInteractionParser parser = new Xml25ModelledBinaryInteractionParser(reader);
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
            Xml25ModelledBinaryInteractionParser parser = new Xml25ModelledBinaryInteractionParser(file);
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
            Xml25ModelledBinaryInteractionParser parser = new Xml25ModelledBinaryInteractionParser(input);
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
            Xml25ModelledBinaryInteractionParser parser = new Xml25ModelledBinaryInteractionParser(url);
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
    protected Iterator<ModelledBinaryInteraction> createXmlIterator() {
        return new Xml25ModelledBinaryInteractionIterator(getParser());
    }
}
