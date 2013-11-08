package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.xml.io.iterator.Xml25InteractionEvidenceIterator;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.URL;
import java.util.Iterator;

/**
 * Datasource for Psi-xml 2.5 interaction evidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public class Xml25InteractionEvidenceDatasource extends AbstractPsiXmlDataSource<InteractionEvidence>{
    public Xml25InteractionEvidenceDatasource() {
    }

    public Xml25InteractionEvidenceDatasource(File file) throws IOException {
        super(file);
    }

    public Xml25InteractionEvidenceDatasource(InputStream input) {
        super(input);
    }

    public Xml25InteractionEvidenceDatasource(Reader reader) {
        super(reader);
    }

    public Xml25InteractionEvidenceDatasource(URL url) {
        super(url);
    }

    @Override
    protected void initialiseXmlParser(Reader reader) {
        try {
            setParser(new Xml25InteractionEvidenceParser(reader));
            getParser().setListener(this);
        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to read with provided reader ",e);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to read with provided reader",e);
        }      }

    @Override
    protected void initialiseXmlParser(File file) {
        try {
            setParser(new Xml25InteractionEvidenceParser(file));
            getParser().setListener(this);
        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to parse the file "+file.getName(),e);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to parse the file "+file.getName(),e);
        }
    }

    @Override
    protected void initialiseXmlParser(InputStream input) {
        try {
            setParser(new Xml25InteractionEvidenceParser(input));
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
            setParser(new Xml25InteractionEvidenceParser(url));
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
    protected Iterator<InteractionEvidence> createXmlIterator() {
        return new Xml25InteractionEvidenceIterator(getParser());
    }
}
