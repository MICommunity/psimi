package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.exception.PsiXmlParserException;
import psidev.psi.mi.jami.xml.extension.*;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.URL;

/**
 * Parser generating basic interaction objects and ignore experimental details
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */

public class Xml25Parser extends AbstractPsiXml25Parser<Interaction<? extends Participant>>{
    public Xml25Parser(File file) throws XMLStreamException, JAXBException {
        super(file);
    }

    public Xml25Parser(InputStream inputStream) throws XMLStreamException, JAXBException {
        super(inputStream);
    }

    public Xml25Parser(URL url) throws IOException, XMLStreamException, JAXBException {
        super(url);
    }

    public Xml25Parser(Reader reader) throws XMLStreamException, JAXBException {
        super(reader);
    }

    @Override
    protected Unmarshaller createJAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(XmlBasicInteraction.class, XmlExperiment.class, XmlInteractor.class,
                Availability.class, XmlSource.class, XmlAnnotation.class);
        return ctx.createUnmarshaller();
    }

    @Override
    protected void parseExperimentList() throws PsiXmlParserException {
        // read experiment list
        Location experimentList = getStreamReader().getLocation();
        try {
            if (getStreamReader().hasNext()){
                getStreamReader().next();
            }
        } catch (XMLStreamException e) {
            throw createPsiXmlExceptionFrom("Impossible to read first experiment in the experiment list", e);
        }

        setCurrentElement(getNextPsiXml25StartElement());
        // process experiments. Each experiment will be loaded in entryContext so no needs to do something else
        if (getCurrentElement() != null){
            String evt = getCurrentElement();
            String name = null;
            // skip experimentDescription up to the end of experiment list
            while (evt != null && (name == null || (name != null && !PsiXmlUtils.EXPERIMENTLIST_TAG.equals(name)))) {
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
                if (experimentList != null){
                    context = new DefaultFileSourceContext(new PsiXmLocator(experimentList.getLineNumber(), experimentList.getColumnNumber(), null));
                }
                getListener().onInvalidSyntax(context, new PsiXmlParserException("ExperimentList element does not contain any experimentDescription node. PSI-XML is not valid."));
            }
        }
        setCurrentElement(getNextPsiXml25StartElement());
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
            while (evt != null && (name == null || (name != null && !PsiXmlUtils.AVAILABILITYLIST_TAG.equals(name)))) {
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
    protected Interaction<? extends Participant> unmarshallInteraction() throws JAXBException {
        return super.unmarshallInteraction(XmlBasicInteraction.class);
    }
}
