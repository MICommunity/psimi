package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.datasource.ModelledBinaryInteractionSource;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.ModelledInteraction;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * PSI-XML 2.5 data source that provides modelled binary interactions (ignore experimental details).
 * It will load the full interaction dataset
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/13</pre>
 */

public class Xml25ModelledBinarySource extends AbstractPsiXml25BinarySource<ModelledInteraction, ModelledBinaryInteraction> implements ModelledBinaryInteractionSource {

    public Xml25ModelledBinarySource() {
    }

    public Xml25ModelledBinarySource(File file) throws IOException {
        super(file);
    }

    public Xml25ModelledBinarySource(InputStream input) {
        super(input);
    }

    public Xml25ModelledBinarySource(Reader reader) {
        super(reader);
    }

    public Xml25ModelledBinarySource(URL url) {
        super(url);
    }

    @Override
    protected void initialiseXmlParser(Reader reader) {
        try {
            FullXml25ModelledBinaryParser parser = new FullXml25ModelledBinaryParser(reader);
            parser.setListener(this);
            setParser(parser);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to read with provided reader",e);
        }
    }

    @Override
    protected void initialiseXmlParser(File file) {
        try {
            FullXml25ModelledBinaryParser parser = new FullXml25ModelledBinaryParser(file);
            parser.setListener(this);
            setParser(parser);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to read with provided file",e);
        }
    }

    @Override
    protected void initialiseXmlParser(InputStream input) {
        try {
            FullXml25ModelledBinaryParser parser = new FullXml25ModelledBinaryParser(input);
            parser.setListener(this);
            setParser(parser);
        } catch (JAXBException e) {
            throw new MIIOException("Impossible to read with provided Input stream",e);
        }
    }

    @Override
    protected void initialiseXmlParser(URL url) {
        try {
            FullXml25ModelledBinaryParser parser = new FullXml25ModelledBinaryParser(url);
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
