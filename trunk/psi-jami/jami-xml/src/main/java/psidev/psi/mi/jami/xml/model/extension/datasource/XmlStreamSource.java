package psidev.psi.mi.jami.xml.model.extension.datasource;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.xml.io.iterator.XmlInteractionIterator;
import psidev.psi.mi.jami.xml.io.parser.XmlParser;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Iterator;

/**
 * Datasource for PSI-xml 2.5 returning mix of abstract interactions and interaction evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public class XmlStreamSource extends AbstractPsiXmlStream<Interaction> {

    public XmlStreamSource() {
    }

    public XmlStreamSource(File file) {
        super(file);
    }

    public XmlStreamSource(InputStream input) {
        super(input);
    }

    public XmlStreamSource(Reader reader) {
        super(reader);
    }

    public XmlStreamSource(URL url) {
        super(url);
    }

    @Override
    protected void initialiseXmlParser(Reader reader) {
        XmlParser parser = new XmlParser(reader);
        parser.setListener(this);
        parser.setCacheOfObjects(getElementCache());
        setParser(parser);
    }

    @Override
    protected void initialiseXmlParser(File file) {
        XmlParser parser = new XmlParser(file);
        parser.setListener(this);
        parser.setCacheOfObjects(getElementCache());
        setParser(parser);
    }

    @Override
    protected void initialiseXmlParser(InputStream input) {
        XmlParser parser = new XmlParser(input);
        parser.setListener(this);
        parser.setCacheOfObjects(getElementCache());
        setParser(parser);
    }

    @Override
    protected void initialiseXmlParser(URL url) {
        XmlParser parser = new XmlParser(url);
        parser.setListener(this);
        parser.setCacheOfObjects(getElementCache());
        setParser(parser);
    }

    @Override
    protected void initialiseExpansionMethod(ComplexExpansionMethod<? extends Interaction, ? extends BinaryInteraction> expansionMethod) {
        // nothing to do as we don't expand
    }

    @Override
    protected Iterator<Interaction> createXmlIterator() {
        return new XmlInteractionIterator(getParser());
    }
}
