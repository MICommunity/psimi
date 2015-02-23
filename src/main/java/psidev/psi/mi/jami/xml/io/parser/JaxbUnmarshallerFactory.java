package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.xml.PsiXmlVersion;
import psidev.psi.mi.jami.xml.model.xml25.*;
import psidev.psi.mi.jami.xml.model.xml30.Xml300EntrySet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Factory to initialise JAXB context depending on the version and the interaction object category
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/04/14</pre>
 */

public class JaxbUnmarshallerFactory {

    private static final JaxbUnmarshallerFactory instance = new JaxbUnmarshallerFactory();

    private JaxbUnmarshallerFactory(){
        // nothing to do here
    }

    public static JaxbUnmarshallerFactory getInstance() {
        return instance;
    }

    /**
     *
     * @param version : version of PSI XML format
     * @param category : Interaction object category (modelled, evidence, basic, ...)
     * @return JAXB unmarshaller initialised to parse interaction objects of a certain category in a specific version of the PSI-MI XML format.
     * The unmarhsaller is initialised so it can be used in a streaming way
     * @throws JAXBException
     */
    public Unmarshaller createUnmarshaller(PsiXmlVersion version, InteractionCategory category) throws JAXBException {
        if (category.equals(InteractionCategory.mixed) && !version.equals(PsiXmlVersion.v3_0_0)){
            category = InteractionCategory.evidence;
        }

        // create unmarshaller knowing the version
        switch (version){
            case v2_5_4:
                return createXml254JAXBUnmarshaller(category);
            case v2_5_3:
                return createXml253JAXBUnmarshaller(category);
            case v3_0_0:
                return createXml300JAXBUnmarshaller(category);
            default:
                return createXml254JAXBUnmarshaller(category);
        }
    }

    /**
     *
     * @param version : version of PSI XML format
     * @param category : Interaction object category (modelled, evidence, basic, ...)
     * @return JAXB unmarshaller initialised to parse interaction objects of a certain category in a specific version of the PSI-MI XML format.
     * The unmarhsaller is initialised so it can only be used to parse the all entrySet (no streaming)
     * @throws JAXBException
     */
    public Unmarshaller createFullUnmarshaller(PsiXmlVersion version, InteractionCategory category) throws JAXBException {
        if (category.equals(InteractionCategory.mixed) && !version.equals(PsiXmlVersion.v3_0_0)){
            category = InteractionCategory.evidence;
        }

        // create unmarshaller knowing the version
        switch (version){
            case v2_5_4:
                return createFullXml254JAXBUnmarshaller(category);
            case v2_5_3:
                return createFullXml253JAXBUnmarshaller(category);
            case v3_0_0:
                return createFullXml300JAXBUnmarshaller(category);
            default:
                return createFullXml254JAXBUnmarshaller(category);
        }
    }

    public Unmarshaller createXml300JAXBUnmarshaller(InteractionCategory category) throws JAXBException {
        // create unmarshaller knowing the interaction category we want to parse
        return createXml300JAXBUnmarshaller();
    }

    public Unmarshaller createXml253JAXBUnmarshaller(InteractionCategory category) throws JAXBException {
        // create unmarshaller knowing the interaction category we want to parse
        switch (category){
            case basic:
                return createBasicXml253JAXBUnmarshaller();
            case evidence:
                return createEvidenceXml253JAXBUnmarshaller();
            case modelled:
                return createModelledXml253JAXBUnmarshaller();
            case mixed:
                return createEvidenceXml253JAXBUnmarshaller();
            case complex:
                return createComplexXml253JAXBUnmarshaller();
            default:
                throw new IllegalArgumentException("Cannot create unmarshaller for interaction category: "+category);
        }
    }

    public Unmarshaller createXml254JAXBUnmarshaller(InteractionCategory category) throws JAXBException {
        // create unmarshaller knowing the interaction category we want to parse
        switch (category){
            case basic:
                return createBasicXml254JAXBUnmarshaller();
            case evidence:
                return createEvidenceXml254JAXBUnmarshaller();
            case modelled:
                return createModelledXml254JAXBUnmarshaller();
            case mixed:
                return createEvidenceXml254JAXBUnmarshaller();
            case complex:
                return createComplexXml254JAXBUnmarshaller();
            default:
                throw new IllegalArgumentException("Cannot create unmarshaller for interaction category: "+category);
        }
    }

    public Unmarshaller createFullXml300JAXBUnmarshaller(InteractionCategory category) throws JAXBException {

        // create unmarshaller ignoring the interaction category we want to parse because always a mix
        return createFullXml300JAXBUnmarshaller();

    }

    public Unmarshaller createFullXml253JAXBUnmarshaller(InteractionCategory category) throws JAXBException {

        // create unmarshaller knowing the interaction category we want to parse
        switch (category){
            case basic:
                return createBasicFullXml253JAXBUnmarshaller();
            case evidence:
                return createEvidenceFullXml253JAXBUnmarshaller();
            case modelled:
                return createModelledFullXml253JAXBUnmarshaller();
            case mixed:
                return createEvidenceFullXml253JAXBUnmarshaller();
            case complex:
                return createComplexFullXml253JAXBUnmarshaller();
            default:
                throw new IllegalArgumentException("Cannot create unmarshaller for interaction category: "+category);
        }
    }

    public Unmarshaller createFullXml254JAXBUnmarshaller(InteractionCategory category) throws JAXBException {
        // create unmarshaller knowing the interaction category we want to parse
        switch (category){
            case basic:
                return createBasicFullXml254JAXBUnmarshaller();
            case evidence:
                return createEvidenceFullXml254JAXBUnmarshaller();
            case modelled:
                return createModelledFullXml254JAXBUnmarshaller();
            case mixed:
                return createEvidenceFullXml254JAXBUnmarshaller();
            case complex:
                return createComplexFullXml254JAXBUnmarshaller();
            default:
                throw new IllegalArgumentException("Cannot create unmarshaller for interaction category: "+category);
        }

    }

    private Unmarshaller createXml300JAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(
                psidev.psi.mi.jami.xml.model.extension.xml300.XmlInteractionEvidence.class,
                psidev.psi.mi.jami.xml.model.extension.xml300.XmlModelledInteraction.class,
                psidev.psi.mi.jami.xml.model.extension.xml300.Availability.class,
                psidev.psi.mi.jami.xml.model.extension.xml300.XmlExperiment.class,
                psidev.psi.mi.jami.xml.model.extension.xml300.XmlInteractor.class,
                psidev.psi.mi.jami.xml.model.extension.xml300.XmlSource.class,
                psidev.psi.mi.jami.xml.model.extension.xml300.XmlAnnotation.class);
        return ctx.createUnmarshaller();
    }

    private Unmarshaller createModelledXml253JAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlModelledInteraction.class,
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlExperiment.class,
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlInteractor.class,
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlSource.class,
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlAnnotation.class);
        return ctx.createUnmarshaller();
    }

    private Unmarshaller createComplexXml253JAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlComplex.class,
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlExperiment.class,
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlInteractor.class,
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlSource.class,
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlAnnotation.class);
        return ctx.createUnmarshaller();
    }

    private Unmarshaller createEvidenceXml253JAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlInteractionEvidence.class,
                psidev.psi.mi.jami.xml.model.extension.xml253.Availability.class,
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlExperiment.class,
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlInteractor.class,
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlSource.class,
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlAnnotation.class);
        return ctx.createUnmarshaller();
    }

    private Unmarshaller createBasicXml253JAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlBasicInteraction.class,
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlInteractor.class,
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlSource.class,
                psidev.psi.mi.jami.xml.model.extension.xml253.XmlAnnotation.class);
        return ctx.createUnmarshaller();
    }

    private Unmarshaller createModelledXml254JAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlModelledInteraction.class,
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlExperiment.class,
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlInteractor.class,
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlSource.class,
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlAnnotation.class);
        return ctx.createUnmarshaller();
    }

    private Unmarshaller createComplexXml254JAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlComplex.class,
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlExperiment.class,
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlInteractor.class,
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlSource.class,
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlAnnotation.class);
        return ctx.createUnmarshaller();
    }

    private Unmarshaller createEvidenceXml254JAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlInteractionEvidence.class,
                psidev.psi.mi.jami.xml.model.extension.xml254.Availability.class,
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlExperiment.class,
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlInteractor.class,
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlSource.class,
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlAnnotation.class);
        return ctx.createUnmarshaller();
    }

    private Unmarshaller createBasicXml254JAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlBasicInteraction.class,
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlInteractor.class,
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlSource.class,
                psidev.psi.mi.jami.xml.model.extension.xml254.XmlAnnotation.class);
        return ctx.createUnmarshaller();
    }

    private Unmarshaller createFullXml300JAXBUnmarshaller() throws JAXBException {

        JAXBContext ctx = JAXBContext.newInstance(Xml300EntrySet.class);
        return ctx.createUnmarshaller();
    }

    private Unmarshaller createModelledFullXml253JAXBUnmarshaller() throws JAXBException {

        JAXBContext ctx = JAXBContext.newInstance(Xml253ModelledEntrySet.class);
        return ctx.createUnmarshaller();
    }

    private Unmarshaller createComplexFullXml253JAXBUnmarshaller() throws JAXBException {

        JAXBContext ctx = JAXBContext.newInstance(Xml253ComplexEntrySet.class);
        return ctx.createUnmarshaller();
    }

    private Unmarshaller createEvidenceFullXml253JAXBUnmarshaller() throws JAXBException {

        JAXBContext ctx = JAXBContext.newInstance(Xml253ExperimentalEntrySet.class);
        return ctx.createUnmarshaller();
    }

    private Unmarshaller createBasicFullXml253JAXBUnmarshaller() throws JAXBException {

        JAXBContext ctx = JAXBContext.newInstance(Xml253BasicEntrySet.class);
        return ctx.createUnmarshaller();
    }

    private Unmarshaller createBasicFullXml254JAXBUnmarshaller() throws JAXBException {

        JAXBContext ctx = JAXBContext.newInstance(Xml254BasicEntrySet.class);
        return ctx.createUnmarshaller();
    }

    private Unmarshaller createModelledFullXml254JAXBUnmarshaller() throws JAXBException {

        JAXBContext ctx = JAXBContext.newInstance(Xml254ModelledEntrySet.class);
        return ctx.createUnmarshaller();
    }

    private Unmarshaller createComplexFullXml254JAXBUnmarshaller() throws JAXBException {

        JAXBContext ctx = JAXBContext.newInstance(Xml254ComplexEntrySet.class);
        return ctx.createUnmarshaller();
    }

    private Unmarshaller createEvidenceFullXml254JAXBUnmarshaller() throws JAXBException {

        JAXBContext ctx = JAXBContext.newInstance(Xml254ExperimentalEntrySet.class);
        return ctx.createUnmarshaller();
    }


}
