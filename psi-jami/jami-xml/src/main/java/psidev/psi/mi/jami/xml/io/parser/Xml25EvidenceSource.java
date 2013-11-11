package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * PSI-XML 2.5 data source that provides interaction evidences (with full experimental details).
 * It will load the full interaction dataset
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class Xml25EvidenceSource extends AbstractPsiXml25Source<InteractionEvidence>{

    public Xml25EvidenceSource() {
    }

    public Xml25EvidenceSource(File file) throws IOException {
        super(file);
    }

    public Xml25EvidenceSource(InputStream input) {
        super(input);
    }

    public Xml25EvidenceSource(Reader reader) {
        super(reader);
    }

    public Xml25EvidenceSource(URL url) {
        super(url);
    }

    @Override
    protected void initialiseXmlParser(Reader reader) {
        try {
            FullXml25EvidenceParser parser = new FullXml25EvidenceParser(reader);
            parser.setListener(this);
            parser.setCacheOfComplexes(getComplexCache());
            parser.setCacheOfObjects(getElementCache());
            setParser(parser);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to read with provided reader",e);
        }
    }

    @Override
    protected void initialiseXmlParser(File file) {
        try {
            FullXml25EvidenceParser parser = new FullXml25EvidenceParser(file);
            parser.setListener(this);
            parser.setCacheOfComplexes(getComplexCache());
            parser.setCacheOfObjects(getElementCache());
            setParser(parser);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to read with provided file",e);
        }
    }

    @Override
    protected void initialiseXmlParser(InputStream input) {
        try {
            FullXml25EvidenceParser parser = new FullXml25EvidenceParser(input);
            parser.setListener(this);
            parser.setCacheOfComplexes(getComplexCache());
            parser.setCacheOfObjects(getElementCache());
            setParser(parser);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to read with provided Input stream",e);
        }
    }

    @Override
    protected void initialiseXmlParser(URL url) {
        try {
            FullXml25EvidenceParser parser = new FullXml25EvidenceParser(url);
            parser.setListener(this);
            parser.setCacheOfComplexes(getComplexCache());
            parser.setCacheOfObjects(getElementCache());
            setParser(parser);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to read with provided URL",e);
        }
        catch (IOException e) {
            throw new MIIOException("Impossible to read the url "+url.toExternalForm(),e);
        }
    }

    @Override
    protected void initialiseExpansionMethod(ComplexExpansionMethod<? extends Interaction, ? extends BinaryInteraction> expansionMethod) {
        //do nothing
    }
}
