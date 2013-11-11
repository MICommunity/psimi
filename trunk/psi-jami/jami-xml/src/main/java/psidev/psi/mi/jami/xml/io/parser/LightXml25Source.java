package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * PSI-XML 2.5 data source that provides light interactions (no experimental details).
 * It will load the full interaction dataset
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class LightXml25Source extends AbstractPsiXml25Source<Interaction<?extends Participant>>{

    public LightXml25Source() {
    }

    public LightXml25Source(File file) {
        super(file);
    }

    public LightXml25Source(InputStream input) {
        super(input);
    }

    public LightXml25Source(Reader reader) {
        super(reader);
    }

    public LightXml25Source(URL url) {
        super(url);
    }

    @Override
    protected void initialiseXmlParser(Reader reader) {
        try {
            FullXml25Parser parser = new FullXml25Parser(reader);
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
            FullXml25Parser parser = new FullXml25Parser(file);
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
            FullXml25Parser parser = new FullXml25Parser(input);
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
            FullXml25Parser parser = new FullXml25Parser(url);
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
