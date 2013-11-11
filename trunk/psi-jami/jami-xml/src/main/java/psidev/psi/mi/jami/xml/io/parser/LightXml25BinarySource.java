package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.datasource.BinaryInteractionSource;
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
 * PSI-XML 2.5 data source that provides light binary interactions (no experimental details).
 * It will load the full interaction dataset
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class LightXml25BinarySource extends AbstractPsiXml25BinarySource<Interaction<?extends Participant>, BinaryInteraction> implements BinaryInteractionSource<BinaryInteraction>{

    public LightXml25BinarySource() {
    }

    public LightXml25BinarySource(File file) throws IOException {
        super(file);
    }

    public LightXml25BinarySource(InputStream input) {
        super(input);
    }

    public LightXml25BinarySource(Reader reader) {
        super(reader);
    }

    public LightXml25BinarySource(URL url) {
        super(url);
    }

    @Override
    protected void initialiseXmlParser(Reader reader) {
        try {
            FullXml25BinaryParser parser = new FullXml25BinaryParser(reader);
            parser.setListener(this);
            setParser(parser);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to read with provided reader",e);
        }
    }

    @Override
    protected void initialiseXmlParser(File file) {
        try {
            FullXml25BinaryParser parser = new FullXml25BinaryParser(file);
            parser.setListener(this);
            setParser(parser);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to read with provided file",e);
        }
    }

    @Override
    protected void initialiseXmlParser(InputStream input) {
        try {
            FullXml25BinaryParser parser = new FullXml25BinaryParser(input);
            parser.setListener(this);
            setParser(parser);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to read with provided Input stream",e);
        }
    }

    @Override
    protected void initialiseXmlParser(URL url) {
        try {
            FullXml25BinaryParser parser = new FullXml25BinaryParser(url);
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
