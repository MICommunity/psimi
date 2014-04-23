package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.model.Xml253BasicEntrySet;
import psidev.psi.mi.jami.xml.model.Xml254BasicEntrySet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URL;

/**
 * Full Parser generating basic interaction objects and ignore experimental details.
 *
 * It will load the all entrySet so is consuming a lot of memory in case of large files but is very performant for small files
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class FullXmlParser extends AbstractFullPsiXmlParser<Interaction<? extends Participant>> {
    public FullXmlParser(File file) throws JAXBException, FileNotFoundException {
        super(file);
    }

    public FullXmlParser(InputStream inputStream) throws JAXBException {
        super(inputStream);
    }

    public FullXmlParser(URL url) throws IOException, JAXBException {
        super(url);
    }

    public FullXmlParser(Reader reader) throws JAXBException {
        super(reader);
    }

    @Override
    protected Unmarshaller createXml254JAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(Xml254BasicEntrySet.class);
        return ctx.createUnmarshaller();
    }

    @Override
    protected Unmarshaller createXml253JAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(Xml253BasicEntrySet.class);
        return ctx.createUnmarshaller();
    }
}
