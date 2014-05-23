package psidev.psi.mi.jami.xml.model.extension.datasource;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.datasource.InteractionEvidenceStream;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.xml.io.iterator.XmlInteractionEvidenceIterator;
import psidev.psi.mi.jami.xml.io.parser.XmlEvidenceParser;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Iterator;

/**
 * Datasource for Psi-xml 2.5 interaction evidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public class XmlEvidenceStreamSource extends AbstractPsiXmlStream<InteractionEvidence> implements InteractionEvidenceStream<InteractionEvidence>{
    public XmlEvidenceStreamSource() {
    }

    public XmlEvidenceStreamSource(File file) {
        super(file);
    }

    public XmlEvidenceStreamSource(InputStream input) {
        super(input);
    }

    public XmlEvidenceStreamSource(Reader reader) {
        super(reader);
    }

    public XmlEvidenceStreamSource(URL url) {
        super(url);
    }

    @Override
    protected void initialiseXmlParser(Reader reader) {
        XmlEvidenceParser parser = new XmlEvidenceParser(reader);
        parser.setListener(this);
        parser.setCacheOfObjects(getElementCache());
        setParser(parser);
    }

    @Override
    protected void initialiseXmlParser(File file) {
        XmlEvidenceParser parser = new XmlEvidenceParser(file);
        parser.setListener(this);
        parser.setCacheOfObjects(getElementCache());
        setParser(parser);
    }

    @Override
    protected void initialiseXmlParser(InputStream input) {
        XmlEvidenceParser parser = new XmlEvidenceParser(input);
        parser.setListener(this);
        parser.setCacheOfObjects(getElementCache());
        setParser(parser);
    }

    @Override
    protected void initialiseXmlParser(URL url) {
        XmlEvidenceParser parser = new XmlEvidenceParser(url);
        parser.setListener(this);
        parser.setCacheOfObjects(getElementCache());
        setParser(parser);
    }

    @Override
    protected void initialiseExpansionMethod(ComplexExpansionMethod<? extends Interaction, ? extends BinaryInteraction> expansionMethod) {
        // nothing to do as we don't expand
    }

    @Override
    protected Iterator<InteractionEvidence> createXmlIterator() {
        return new XmlInteractionEvidenceIterator(getParser());
    }
}
