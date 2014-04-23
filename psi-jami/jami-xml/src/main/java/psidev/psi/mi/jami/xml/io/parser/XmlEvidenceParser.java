package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.model.InteractionEvidence;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * Parser generating interaction evidence objects with all experimental details
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */

public class XmlEvidenceParser extends AbstractPsiXmlParser<InteractionEvidence> {

    public XmlEvidenceParser(File file) {
        super(file);
    }

    public XmlEvidenceParser(InputStream inputStream) {
        super(inputStream);
    }

    public XmlEvidenceParser(URL url) {
        super(url);
    }

    public XmlEvidenceParser(Reader reader) {
        super(reader);
    }

    @Override
    protected Unmarshaller createXml254JAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlInteractionEvidence.class,
                psidev.psi.mi.jami.xml.model.extension.xml254.Availability.class,
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlExperiment.class,
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlInteractor.class,
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlSource.class,
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlAnnotation.class);
        return ctx.createUnmarshaller();
    }
    @Override
    protected Unmarshaller createXml253JAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlInteractionEvidence.class,
                psidev.psi.mi.jami.xml.model.extension.xml253.Availability.class,
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlExperiment.class,
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlInteractor.class,
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlSource.class,
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlAnnotation.class);
        return ctx.createUnmarshaller();
    }
}
