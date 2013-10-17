package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.xml.io.iterator.Xml25ModelledInteractionIterator;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.URL;
import java.util.Iterator;

/**
 * Datasource for Psi-XML 2.5 modelled interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public class Xml25ModelledInteractionDatasource extends AbstractPsiXmlDataSource<ModelledInteraction>{

    public Xml25ModelledInteractionDatasource() {
    }

    public Xml25ModelledInteractionDatasource(File file) throws IOException {
        super(file);
    }

    public Xml25ModelledInteractionDatasource(InputStream input) {
        super(input);
    }

    public Xml25ModelledInteractionDatasource(Reader reader) {
        super(reader);
    }

    public Xml25ModelledInteractionDatasource(URL url) {
        super(url);
    }

    @Override
    protected void initialiseXmlParser(Reader reader) {
        try {
            setParser(new Xml25ModelledInteractionParser(reader));
            getParser().setListener(this);
        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to read with provided reader ",e);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to read with provided reader",e);
        }      }

    @Override
    protected void initialiseXmlParser(File file) {
        try {
            setParser(new Xml25ModelledInteractionParser(file));
            getParser().setListener(this);
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
            setParser(new Xml25ModelledInteractionParser(input));
            getParser().setListener(this);
        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to parse the inputstream ",e);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to parse the inputstream ",e);
        }
    }

    @Override
    protected void initialiseXmlParser(URL url) {
        try {
            setParser(new Xml25ModelledInteractionParser(url));
            getParser().setListener(this);
        } catch (IOException e) {
            throw new MIIOException("Impossible to read the url "+url.toExternalForm(),e);
        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to parse the url "+url.toExternalForm(),e);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to parse the url "+url.toExternalForm(),e);
        }
    }

    @Override
    protected void initialiseExpansionMethod(ComplexExpansionMethod<? extends Interaction, ? extends BinaryInteraction> expansionMethod) {
        // nothing to do as we don't expand
    }

    @Override
    protected Iterator<ModelledInteraction> createXmlIterator() {
        return new Xml25ModelledInteractionIterator(getParser());
    }
}
