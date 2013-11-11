package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.datasource.BinaryInteractionEvidenceSource;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.InteractionEvidence;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * PSI-XML 2.5 data source that provides binary interactions evidence (full experimental details).
 * It will load the full interaction dataset
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class Xml25BinaryEvidenceSource extends AbstractPsiXml25BinarySource<InteractionEvidence, BinaryInteractionEvidence> implements BinaryInteractionEvidenceSource{

    public Xml25BinaryEvidenceSource() {
    }

    public Xml25BinaryEvidenceSource(File file) throws IOException {
        super(file);
    }

    public Xml25BinaryEvidenceSource(InputStream input) {
        super(input);
    }

    public Xml25BinaryEvidenceSource(Reader reader) {
        super(reader);
    }

    public Xml25BinaryEvidenceSource(URL url) {
        super(url);
    }

    @Override
    protected void initialiseXmlParser(Reader reader) {
        try {
            FullXml25BinaryEvidenceParser parser = new FullXml25BinaryEvidenceParser(reader);
            parser.setListener(this);
            setParser(parser);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to read with provided reader",e);
        }
    }

    @Override
    protected void initialiseXmlParser(File file) {
        try {
            FullXml25BinaryEvidenceParser parser = new FullXml25BinaryEvidenceParser(file);
            parser.setListener(this);
            setParser(parser);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to read with provided file",e);
        }
    }

    @Override
    protected void initialiseXmlParser(InputStream input) {
        try {
            FullXml25BinaryEvidenceParser parser = new FullXml25BinaryEvidenceParser(input);
            parser.setListener(this);
            setParser(parser);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to read with provided Input stream",e);
        }
    }

    @Override
    protected void initialiseXmlParser(URL url) {
        try {
            FullXml25BinaryEvidenceParser parser = new FullXml25BinaryEvidenceParser(url);
            parser.setListener(this);
            setParser(parser);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to read with provided URL",e);
        }
        catch (IOException e) {
            throw new MIIOException("Impossible to read the url "+url.toExternalForm(),e);
        }
    }
}
