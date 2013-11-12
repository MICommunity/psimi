package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.exception.PsiXmlParserException;
import psidev.psi.mi.jami.xml.extension.*;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.URL;

/**
 * Parser generating modelled interaction objects and ignore experimental details
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */

public class Xml25ModelledParser extends AbstractPsiXml25Parser<ModelledInteraction>{
    public Xml25ModelledParser(File file) throws XMLStreamException, JAXBException {
        super(file);
    }

    public Xml25ModelledParser(InputStream inputStream) throws XMLStreamException, JAXBException {
        super(inputStream);
    }

    public Xml25ModelledParser(URL url) throws IOException, XMLStreamException, JAXBException {
        super(url);
    }

    public Xml25ModelledParser(Reader reader) throws XMLStreamException, JAXBException {
        super(reader);
    }

    @Override
    protected Unmarshaller createJAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(XmlModelledInteraction.class, XmlExperiment.class, XmlInteractor.class,
                Availability.class, XmlSource.class, XmlAnnotation.class);
        return ctx.createUnmarshaller();
    }

    @Override
    protected void parseAvailabilityList(XmlEntryContext entryContext) throws PsiXmlParserException {
        // read availabilityList
        Location availabilityList = getStreamReader().getLocation();
        try {
            if (getStreamReader().hasNext()){
                getStreamReader().next();
            }
        } catch (XMLStreamException e) {
            throw createPsiXmlExceptionFrom("Impossible to read the next availability in the availability list", e);
        }

        setCurrentElement(getNextPsiXml25StartElement());
        // process experiments. Each experiment will be loaded in entryContext so no needs to do something else
        if (getCurrentElement() != null){
            String evt = getCurrentElement();
            String name = null;
            // skip experimentDescription up to the end of experiment list
            while (evt != null && (name == null || (name != null && !PsiXml25Utils.AVAILABILITYLIST_TAG.equals(name)))) {
                while (evt != null && !getStreamReader().isEndElement()){
                    skipNextElement();
                    evt = getStreamReader().getLocalName();
                }

                if (evt != null && getStreamReader().isEndElement()){
                    name = getStreamReader().getLocalName();
                    skipNextElement();
                    evt = getStreamReader().getLocalName();
                }
            }
        }
        else{
            if (getListener() != null){
                FileSourceContext context = null;
                if (availabilityList != null){
                    context = new DefaultFileSourceContext(new PsiXmLocator(availabilityList.getLineNumber(), availabilityList.getColumnNumber(), null));
                }
                getListener().onInvalidSyntax(context, new PsiXmlParserException("AvailabilityList element does not contain any availability node. PSI-XML is not valid."));
            }
        }
        setCurrentElement(getNextPsiXml25StartElement());
    }

    @Override
    protected ModelledInteraction unmarshallInteraction() throws JAXBException {
        return super.unmarshallInteraction(XmlModelledInteraction.class);
    }
}
