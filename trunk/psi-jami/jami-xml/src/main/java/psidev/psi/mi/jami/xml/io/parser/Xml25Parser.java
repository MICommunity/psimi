package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.Xml25EntryContext;
import psidev.psi.mi.jami.xml.exception.PsiXmlParserException;
import psidev.psi.mi.jami.xml.extension.PsiXmLocator;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * Parser generating basic interaction objects and ignore experimental details
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */

public class Xml25Parser extends AbstractPsiXml25Parser<Interaction<? extends Participant>>{
    public Xml25Parser(File file){
        super(file);
    }

    public Xml25Parser(InputStream inputStream){
        super(inputStream);
    }

    public Xml25Parser(URL url){
        super(url);
    }

    public Xml25Parser(Reader reader){
        super(reader);
    }

    @Override
    protected Unmarshaller createXml254JAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(
                psidev.psi.mi.jami.xml.extension.xml254.XmlBasicInteraction.class,
                psidev.psi.mi.jami.xml.extension.xml254.XmlInteractor.class,
                psidev.psi.mi.jami.xml.extension.xml254.XmlSource.class,
                psidev.psi.mi.jami.xml.extension.xml254.XmlAnnotation.class);
        return ctx.createUnmarshaller();
    }
    @Override
    protected Unmarshaller createXml253JAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(
                psidev.psi.mi.jami.xml.extension.xml253.XmlBasicInteraction.class,
                psidev.psi.mi.jami.xml.extension.xml253.XmlInteractor.class,
                psidev.psi.mi.jami.xml.extension.xml253.XmlSource.class,
                psidev.psi.mi.jami.xml.extension.xml253.XmlAnnotation.class);
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
            while (evt != null && (name == null || (name != null && !PsiXml25Utils.EXPERIMENTLIST_TAG.equals(name)))) {
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
                getListener().onInvalidSyntax(context, new PsiXmlParserException("ExperimentList elements does not contains any experimentDescription node. PSI-XML is not valid."));
            }
        }
        setCurrentElement(getNextPsiXml25StartElement());
    }

    @Override
    protected void parseAvailabilityList(Xml25EntryContext entryContext) throws PsiXmlParserException {
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
                getListener().onInvalidSyntax(context, new PsiXmlParserException("AvailabilityList elements does not contains any availability node. PSI-XML is not valid."));
            }
        }
        setCurrentElement(getNextPsiXml25StartElement());
    }
}
