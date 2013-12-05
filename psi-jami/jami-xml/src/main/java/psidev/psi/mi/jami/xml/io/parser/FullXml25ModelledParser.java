package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.xml.Xml253ModelledEntrySet;
import psidev.psi.mi.jami.xml.Xml254ModelledEntrySet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URL;

/**
 * Full Parser generating modelled interaction objects and ignore experimental details.
 *
 * It will load the all entrySet so is consuming a lot of memory in case of large files but is very performant for small files
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class FullXml25ModelledParser extends AbstractFullPsiXml25Parser<ModelledInteraction>{
    public FullXml25ModelledParser(File file) throws JAXBException, FileNotFoundException {
        super(file);
    }

    public FullXml25ModelledParser(InputStream inputStream) throws JAXBException {
        super(inputStream);
    }

    public FullXml25ModelledParser(Reader reader) throws JAXBException {
        super(reader);
    }

    public FullXml25ModelledParser(URL url) throws IOException, JAXBException {
        super(url);
    }

    @Override
    protected Unmarshaller createXml254JAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(Xml254ModelledEntrySet.class);
        return ctx.createUnmarshaller();
    }

    @Override
    protected Unmarshaller createXml253JAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(Xml253ModelledEntrySet.class);
        return ctx.createUnmarshaller();
    }
}
