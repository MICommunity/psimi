package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.datasource.ModelledInteractionStream;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.xml.io.iterator.Xml25ModelledInteractionIterator;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Iterator;

/**
 * Datasource for Psi-XML 2.5 modelled interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public class Xml25ModelledStreamSource extends AbstractPsiXml25Stream<ModelledInteraction> implements ModelledInteractionStream<ModelledInteraction>{

    public Xml25ModelledStreamSource() {
    }

    public Xml25ModelledStreamSource(File file) {
        super(file);
    }

    public Xml25ModelledStreamSource(InputStream input) {
        super(input);
    }

    public Xml25ModelledStreamSource(Reader reader) {
        super(reader);
    }

    public Xml25ModelledStreamSource(URL url) {
        super(url);
    }

    @Override
    protected void initialiseXmlParser(Reader reader) {
        Xml25ModelledParser parser = new Xml25ModelledParser(reader);
        parser.setListener(this);
        parser.setCacheOfObjects(getElementCache());
        setParser(parser);
    }

    @Override
    protected void initialiseXmlParser(File file) {
        Xml25ModelledParser parser = new Xml25ModelledParser(file);
        parser.setListener(this);
        parser.setCacheOfObjects(getElementCache());
        setParser(parser);
    }

    @Override
    protected void initialiseXmlParser(InputStream input) {
        Xml25ModelledParser parser = new Xml25ModelledParser(input);
        parser.setListener(this);
        parser.setCacheOfObjects(getElementCache());
        setParser(parser);
    }

    @Override
    protected void initialiseXmlParser(URL url) {
        Xml25ModelledParser parser = new Xml25ModelledParser(url);
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
        return new Xml25ModelledInteractionIterator(getParser());
    }
}
