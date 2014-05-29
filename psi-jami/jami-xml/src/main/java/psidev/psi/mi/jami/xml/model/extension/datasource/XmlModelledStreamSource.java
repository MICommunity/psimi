package psidev.psi.mi.jami.xml.model.extension.datasource;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.datasource.ModelledInteractionStream;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.xml.io.iterator.XmlModelledInteractionIterator;
import psidev.psi.mi.jami.xml.io.parser.XmlModelledParser;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Iterator;

/**
 * Datasource for Psi-XML 2.5 abstract interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public class XmlModelledStreamSource extends AbstractPsiXmlStream<ModelledInteraction> implements ModelledInteractionStream<ModelledInteraction>{

    public XmlModelledStreamSource() {
    }

    public XmlModelledStreamSource(File file) {
        super(file);
    }

    public XmlModelledStreamSource(InputStream input) {
        super(input);
    }

    public XmlModelledStreamSource(Reader reader) {
        super(reader);
    }

    public XmlModelledStreamSource(URL url) {
        super(url);
    }

    @Override
    protected void initialiseXmlParser(Reader reader) {
        XmlModelledParser parser = new XmlModelledParser(reader);
        parser.setListener(this);
        parser.setCacheOfObjects(getElementCache());
        setParser(parser);
    }

    @Override
    protected void initialiseXmlParser(File file) {
        XmlModelledParser parser = new XmlModelledParser(file);
        parser.setListener(this);
        parser.setCacheOfObjects(getElementCache());
        setParser(parser);
    }

    @Override
    protected void initialiseXmlParser(InputStream input) {
        XmlModelledParser parser = new XmlModelledParser(input);
        parser.setListener(this);
        parser.setCacheOfObjects(getElementCache());
        setParser(parser);
    }

    @Override
    protected void initialiseXmlParser(URL url) {
        XmlModelledParser parser = new XmlModelledParser(url);
        parser.setListener(this);
        parser.setCacheOfObjects(getElementCache());
        setParser(parser);
    }

    @Override
    protected void initialiseExpansionMethod(ComplexExpansionMethod<? extends Interaction, ? extends BinaryInteraction> expansionMethod) {
        // nothing to do as we don't expand
    }

    @Override
    protected Iterator<ModelledInteraction> createXmlIterator() {
        return new XmlModelledInteractionIterator(getParser());
    }
}
