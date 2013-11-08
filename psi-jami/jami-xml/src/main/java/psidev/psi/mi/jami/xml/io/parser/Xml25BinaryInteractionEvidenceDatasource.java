package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.xml.io.iterator.Xml25BinaryInteractionEvidenceIterator;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.URL;
import java.util.Iterator;

/**
 * PsiXml 2.5 datasource for binaryInteractionEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public class Xml25BinaryInteractionEvidenceDatasource extends AbstractPsiXmlBinaryDataSource<InteractionEvidence, BinaryInteractionEvidence>{

    public Xml25BinaryInteractionEvidenceDatasource() {
    }

    public Xml25BinaryInteractionEvidenceDatasource(File file) throws IOException {
        super(file);
    }

    public Xml25BinaryInteractionEvidenceDatasource(InputStream input) {
        super(input);
    }

    public Xml25BinaryInteractionEvidenceDatasource(Reader reader) {
        super(reader);
    }

    public Xml25BinaryInteractionEvidenceDatasource(URL url) {
        super(url);
    }

    @Override
    protected void initialiseXmlParser(Reader reader) {
        try {
            Xml25BinaryInteractionEvidenceParser parser = new Xml25BinaryInteractionEvidenceParser(reader);
            parser.setListener(this);
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
            Xml25BinaryInteractionEvidenceParser parser = new Xml25BinaryInteractionEvidenceParser(file);
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
            Xml25BinaryInteractionEvidenceParser parser = new Xml25BinaryInteractionEvidenceParser(input);
            parser.setListener(this);
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
            Xml25BinaryInteractionEvidenceParser parser = new Xml25BinaryInteractionEvidenceParser(url);
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
    protected Iterator<BinaryInteractionEvidence> createXmlIterator() {
        return new Xml25BinaryInteractionEvidenceIterator(getParser());
    }
}
