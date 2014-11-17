package psidev.psi.mi.jami.xml.model.extension.datasource;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.datasource.ComplexStream;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.xml.io.iterator.XmlComplexIterator;
import psidev.psi.mi.jami.xml.io.parser.XmlComplexParser;

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

public class XmlComplexStreamSource extends AbstractPsiXmlStream<Complex> implements ComplexStream {

    public XmlComplexStreamSource() {
    }

    public XmlComplexStreamSource(File file) {
        super(file);
    }

    public XmlComplexStreamSource(InputStream input) {
        super(input);
    }

    public XmlComplexStreamSource(Reader reader) {
        super(reader);
    }

    public XmlComplexStreamSource(URL url) {
        super(url);
    }

    @Override
    protected void initialiseXmlParser(Reader reader) {
        XmlComplexParser parser = new XmlComplexParser(reader);
        parser.setListener(this);
        parser.setCacheOfObjects(getElementCache());
        setParser(parser);
    }

    @Override
    protected void initialiseXmlParser(File file) {
        XmlComplexParser parser = new XmlComplexParser(file);
        parser.setListener(this);
        parser.setCacheOfObjects(getElementCache());
        setParser(parser);
    }

    @Override
    protected void initialiseXmlParser(InputStream input) {
        XmlComplexParser parser = new XmlComplexParser(input);
        parser.setListener(this);
        parser.setCacheOfObjects(getElementCache());
        setParser(parser);
    }

    @Override
    protected void initialiseXmlParser(URL url) {
        XmlComplexParser parser = new XmlComplexParser(url);
        parser.setListener(this);
        parser.setCacheOfObjects(getElementCache());
        setParser(parser);
    }

    @Override
    protected void initialiseExpansionMethod(ComplexExpansionMethod<? extends Interaction, ? extends BinaryInteraction> expansionMethod) {
        // nothing to do as we don't expand
    }

    @Override
    protected Iterator<Complex> createXmlIterator() {
        return new XmlComplexIterator(getParser());
    }

    @Override
    public Iterator<Complex> getInteractorsIterator() throws MIIOException {
        return new XmlComplexIterator(getParser());
    }
}
