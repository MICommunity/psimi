package psidev.psi.mi.jami.xml.cache;

import org.codehaus.stax2.XMLInputFactory2;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXmlVersion;
import psidev.psi.mi.jami.xml.io.parser.JaxbUnmarshallerFactory;
import psidev.psi.mi.jami.xml.listener.XmlLocationListener;
import psidev.psi.mi.jami.xml.model.extension.*;
import psidev.psi.mi.jami.xml.model.extension.xml300.XmlVariableParameterValue;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.nio.channels.Channels;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cache using a file and a weak map to cache the objects
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/06/14</pre>
 */

public class PsiXmlFileIndexCache implements PsiXmlIdCache {

    private static final Logger logger = Logger.getLogger("PsiXmlFileIndexCache");

    private File file;
    private Unmarshaller unmarshaller;
    private RandomAccessFile randomAccessFile;
    private PsiXmlVersion version;
    private InteractionCategory interactionCategory;

    private Map<Integer, AbstractAvailability> mapOfReferencedAvailabilities;

    private Map<Integer, Integer> experimentPositions;
    private Map<Integer, Integer> interactorPositions;
    private Map<Integer, Integer> interactionPositions;
    private Map<Integer, Integer> participantPositions;
    private Map<Integer, Integer> featurePositions;
    private Map<Integer, Integer> variableParameterValuePositions;
    private Map<Integer, Integer> complexPositions;

    private Map<Integer, Experiment> experimentWeakMap;
    private Map<Integer, Interactor> interactorWeakMap;
    private Map<Integer, Interaction> interactionWeakMap;
    private Map<Integer, Participant> participantWeakMap;
    private Map<Integer, Feature> featureWeakMap;
    private Map<Integer, VariableParameterValue> variableParameterValueWeakMap;
    private Map<Integer, Complex> complexWeakMap;

    private Interaction currentInteraction=null;
    private Experiment currentExperiment=null;

    public PsiXmlFileIndexCache(File file, PsiXmlVersion version, InteractionCategory category) throws FileNotFoundException {
        if (file == null){
            throw new IllegalArgumentException("The file index cache needs the original file containing data.");
        }
        this.file = file;
        this.version = version != null ? version : PsiXmlVersion.v2_5_4;
        this.interactionCategory = category != null ? category : InteractionCategory.evidence;
        this.randomAccessFile = new RandomAccessFile(this.file, "r");

        this.mapOfReferencedAvailabilities = new HashMap<Integer, AbstractAvailability>();

        this.experimentPositions = new HashMap<Integer, Integer>();
        this.interactorPositions = new HashMap<Integer, Integer>();
        this.interactionPositions = new HashMap<Integer, Integer>();
        this.participantPositions = new HashMap<Integer, Integer>();
        this.featurePositions = new HashMap<Integer, Integer>();
        this.variableParameterValuePositions = new HashMap<Integer, Integer>();
        this.complexPositions = new HashMap<Integer, Integer>();

        this.experimentWeakMap = new WeakHashMap<Integer, Experiment>();
        this.interactorWeakMap = new WeakHashMap<Integer, Interactor>();
        this.interactionWeakMap = new WeakHashMap<Integer, Interaction>();
        this.participantWeakMap = new WeakHashMap<Integer, Participant>();
        this.featureWeakMap = new WeakHashMap<Integer, Feature>();
        this.variableParameterValueWeakMap = new WeakHashMap<Integer, VariableParameterValue>();
        this.complexWeakMap = new WeakHashMap<Integer, Complex>();
    }

    @Override
    public void registerAvailability(int id, AbstractAvailability object) {
        this.mapOfReferencedAvailabilities.put(id, object);
    }

    @Override
    public AbstractAvailability getAvailability(int id) {
        return this.mapOfReferencedAvailabilities.get(id);
    }

    @Override
    public void registerExperiment(int id, Experiment object) {
        this.currentExperiment = object;
        this.experimentWeakMap.put(id, object);
        if (!this.experimentPositions.containsKey(id)){
            if (object instanceof FileSourceContext){
                FileSourceContext context = (FileSourceContext)object;
                if (context.getSourceLocator() instanceof PsiXmlLocator){
                    PsiXmlLocator locator = (PsiXmlLocator)context.getSourceLocator();
                    this.experimentPositions.put(id, locator.getCharacterOffset());
                }
            }
        }
    }

    @Override
    public Experiment getExperiment(int id) {
        if (this.experimentWeakMap.containsKey(id)){
            return this.experimentWeakMap.get(id);
        }
        else if (!this.experimentPositions.containsKey(id)){
           return null;
        }
        else {
            try {
                return loadFromFile(this.experimentPositions.get(id));
            } catch (IOException e) {
                logger.log(Level.SEVERE, "cannot reload experiment "+id, e);
            } catch (JAXBException e) {
                logger.log(Level.SEVERE, "cannot reload experiment "+id, e);
            } catch (XMLStreamException e) {
                logger.log(Level.SEVERE, "cannot reload experiment "+id, e);
            }
            return null;
        }
    }

    @Override
    public void registerInteraction(int id, Interaction object) {
        this.currentInteraction = object;
        this.interactionWeakMap.put(id, object);
        if (!this.interactionPositions.containsKey(id)){
            if (object instanceof FileSourceContext){
                FileSourceContext context = (FileSourceContext)object;
                if (context.getSourceLocator() instanceof PsiXmlLocator){
                    PsiXmlLocator locator = (PsiXmlLocator)context.getSourceLocator();
                    this.interactionPositions.put(id, locator.getCharacterOffset());
                }
            }
        }
    }

    @Override
    public Interaction getInteraction(int id) {
        if (this.interactionWeakMap.containsKey(id)){
            return this.interactionWeakMap.get(id);
        }
        else if (!this.interactionPositions.containsKey(id)){
            return null;
        }
        else {
            try {
                return loadFromFile(this.interactionPositions.get(id));
            } catch (IOException e) {
                logger.log(Level.SEVERE, "cannot reload interaction "+id, e);
            } catch (JAXBException e) {
                logger.log(Level.SEVERE, "cannot reload interaction "+id, e);
            } catch (XMLStreamException e) {
                logger.log(Level.SEVERE, "cannot reload interaction "+id, e);
            }
            return null;
        }
    }

    @Override
    public void registerInteractor(int id, Interactor object) {
        this.interactorWeakMap.put(id, object);
        if (!this.interactorPositions.containsKey(id)){
            if (object instanceof FileSourceContext){
                FileSourceContext context = (FileSourceContext)object;
                if (context.getSourceLocator() instanceof PsiXmlLocator){
                    PsiXmlLocator locator = (PsiXmlLocator)context.getSourceLocator();
                    this.interactorPositions.put(id, locator.getCharacterOffset());
                }
            }
        }
    }

    @Override
    public Interactor getInteractor(int id) {
        if (this.interactorWeakMap.containsKey(id)){
            return this.interactorWeakMap.get(id);
        }
        else if (!this.interactorPositions.containsKey(id)){
            return null;
        }
        else {
            try {
                return loadFromFile(this.interactorPositions.get(id));
            } catch (IOException e) {
                logger.log(Level.SEVERE, "cannot reload interactor "+id, e);
            } catch (JAXBException e) {
                logger.log(Level.SEVERE, "cannot reload interactor "+id, e);
            } catch (XMLStreamException e) {
                logger.log(Level.SEVERE, "cannot reload interactor "+id, e);
            }
            return null;
        }
    }

    @Override
    public void registerParticipant(int id, Participant object) {
        this.participantWeakMap.put(id, object);
        if (!this.participantPositions.containsKey(id)){
            // store position of interaction because we always load the participant with its interaction!!!!
            if (this.currentInteraction instanceof FileSourceContext){
                FileSourceContext context = (FileSourceContext)this.currentInteraction;
                if (context.getSourceLocator() instanceof PsiXmlLocator){
                    PsiXmlLocator locator = (PsiXmlLocator)context.getSourceLocator();
                    this.participantPositions.put(id, locator.getCharacterOffset());
                }
            }
        }
    }

    @Override
    public Participant getParticipant(int id) {
        if (this.participantWeakMap.containsKey(id)){
            return this.participantWeakMap.get(id);
        }
        else if (!this.participantPositions.containsKey(id)){
            return null;
        }
        else {
            try {
                Interaction originalInteraction = loadFromFile(this.participantPositions.get(id));
                if (originalInteraction == null){
                    logger.log(Level.SEVERE, "cannot reload participant "+id);
                }
                else{
                    for (Object p : originalInteraction.getParticipants()){
                        ExtendedPsiXmlParticipant participant = (ExtendedPsiXmlParticipant)p;
                        if (participant.getId() == id){
                            return participant;
                        }
                    }
                    logger.log(Level.SEVERE, "cannot reload participant "+id);
                    return null;
                }

            } catch (IOException e) {
                logger.log(Level.SEVERE, "cannot reload participant "+id, e);
            } catch (JAXBException e) {
                logger.log(Level.SEVERE, "cannot reload participant "+id, e);
            } catch (XMLStreamException e) {
                logger.log(Level.SEVERE, "cannot reload participant "+id, e);
            }
            return null;
        }
    }

    @Override
    public void registerFeature(int id, Feature object) {
        this.featureWeakMap.put(id, object);
        if (!this.featurePositions.containsKey(id)){
            // store position of interaction because we always load the feature with its participant and interaction!!!!
            if (this.currentInteraction instanceof FileSourceContext){
                FileSourceContext context = (FileSourceContext)this.currentInteraction;
                if (context.getSourceLocator() instanceof PsiXmlLocator){
                    PsiXmlLocator locator = (PsiXmlLocator)context.getSourceLocator();
                    this.featurePositions.put(id, locator.getCharacterOffset());
                }
            }
        }
    }

    @Override
    public Feature getFeature(int id) {
        if (this.featureWeakMap.containsKey(id)){
            return this.featureWeakMap.get(id);
        }
        else if (!this.featurePositions.containsKey(id)){
            return null;
        }
        else {
            try {
                Interaction originalInteraction = loadFromFile(this.featurePositions.get(id));
                if (originalInteraction == null){
                    logger.log(Level.SEVERE, "cannot reload feature "+id);
                }
                else{
                    for (Object p : originalInteraction.getParticipants()){
                        ExtendedPsiXmlParticipant participant = (ExtendedPsiXmlParticipant)p;
                        for (Object f : participant.getFeatures()){
                            ExtendedPsiXmlFeature feature = (ExtendedPsiXmlFeature)f;
                            if (feature.getId() == id){
                                 return feature;
                            }
                        }
                    }
                    logger.log(Level.SEVERE, "cannot reload feature "+id);
                    return null;
                }

            } catch (IOException e) {
                logger.log(Level.SEVERE, "cannot reload feature "+id, e);
            } catch (JAXBException e) {
                logger.log(Level.SEVERE, "cannot reload feature "+id, e);
            } catch (XMLStreamException e) {
                logger.log(Level.SEVERE, "cannot reload feature "+id, e);
            }
            return null;
        }
    }

    @Override
    public void registerComplex(int id, Complex object) {
        this.complexWeakMap.put(id, object);
        if (!this.complexPositions.containsKey(id)){
            if (object instanceof FileSourceContext){
                FileSourceContext context = (FileSourceContext)object;
                if (context.getSourceLocator() instanceof PsiXmlLocator){
                    PsiXmlLocator locator = (PsiXmlLocator)context.getSourceLocator();
                    this.complexPositions.put(id, locator.getCharacterOffset());
                }
            }
        }
    }

    @Override
    public Complex getComplex(int id) {
        if (this.complexWeakMap.containsKey(id)){
            return this.complexWeakMap.get(id);
        }
        else if (!this.complexPositions.containsKey(id)){
            return null;
        }
        else {
            try {
                Interaction originalInteraction = loadFromFile(this.complexPositions.get(id));
                if (originalInteraction == null){
                    logger.log(Level.SEVERE, "cannot reload complex "+id);
                }
                else if (originalInteraction instanceof Complex){
                    return (Complex)originalInteraction;
                }
                // convert interaction evidence in a complex
                else if (originalInteraction instanceof ExtendedPsiXmlInteractionEvidence){
                    return new XmlInteractionEvidenceComplexWrapper((ExtendedPsiXmlInteractionEvidence)originalInteraction);
                }
                // wrap modelled interaction
                else if (originalInteraction instanceof ExtendedPsiXmlModelledInteraction){
                    return new XmlModelledInteractionComplexWrapper((ExtendedPsiXmlModelledInteraction)originalInteraction);
                }
                // wrap basic interaction
                else if (originalInteraction instanceof ExtendedPsiXmlInteraction){
                    return new XmlBasicInteractionComplexWrapper((ExtendedPsiXmlInteraction)originalInteraction);
                }
                else{
                    logger.log(Level.SEVERE, "cannot reload complex "+id);
                    return null;
                }

            } catch (IOException e) {
                logger.log(Level.SEVERE, "cannot reload complex "+id, e);
            } catch (JAXBException e) {
                logger.log(Level.SEVERE, "cannot reload complex "+id, e);
            } catch (XMLStreamException e) {
                logger.log(Level.SEVERE, "cannot reload complex "+id, e);
            }
            return null;
        }
    }

    @Override
    public void registerVariableParameterValue(int id, VariableParameterValue object) {
        this.variableParameterValueWeakMap.put(id, object);
        if (!this.variableParameterValuePositions.containsKey(id)){
            // we don't store positions of variable parameter value but the ones of current experiment as we will reload the full experiment
            if (this.currentExperiment instanceof FileSourceContext){
                FileSourceContext context = (FileSourceContext)this.currentExperiment;
                if (context.getSourceLocator() instanceof PsiXmlLocator){
                    PsiXmlLocator locator = (PsiXmlLocator)context.getSourceLocator();
                    this.variableParameterValuePositions.put(id, locator.getCharacterOffset());
                }
            }
        }
    }

    @Override
    public VariableParameterValue getVariableParameterValue(int id) {
        if (this.variableParameterValueWeakMap.containsKey(id)){
            return this.variableParameterValueWeakMap.get(id);
        }
        else if (!this.variableParameterValuePositions.containsKey(id)){
            return null;
        }
        else {
            try {
                Experiment originalExperiment = loadFromFile(this.variableParameterValuePositions.get(id));
                if (originalExperiment == null){
                    logger.log(Level.SEVERE, "cannot reload variable parameter value "+id);
                }
                else{
                    for (VariableParameter p : originalExperiment.getVariableParameters()){
                        for (VariableParameterValue value : p.getVariableValues()){
                            XmlVariableParameterValue xmlValue = (XmlVariableParameterValue)value;
                            if (xmlValue.getId() == id){
                                return xmlValue;
                            }
                        }
                    }
                    logger.log(Level.SEVERE, "cannot reload variable parameter value "+id);
                    return null;
                }

            } catch (IOException e) {
                logger.log(Level.SEVERE, "cannot reload variable parameter value "+id, e);
            } catch (JAXBException e) {
                logger.log(Level.SEVERE, "cannot reload variable parameter value "+id, e);
            } catch (XMLStreamException e) {
                logger.log(Level.SEVERE, "cannot reload variable parameter value "+id, e);
            }
            return null;
        }
    }

    @Override
    public void clear() {
        this.currentExperiment = null;
        this.currentInteraction = null;

        this.mapOfReferencedAvailabilities.clear();

        this.experimentPositions.clear();
        this.interactorPositions.clear();
        this.interactionPositions.clear();
        this.complexPositions.clear();
        this.participantPositions.clear();
        this.featurePositions.clear();
        this.variableParameterValuePositions.clear();

        this.experimentWeakMap.clear();
        this.interactorWeakMap.clear();
        this.interactionWeakMap.clear();
        this.complexWeakMap.clear();
        this.participantWeakMap.clear();
        this.featureWeakMap.clear();
        this.variableParameterValueWeakMap.clear();
    }

    @Override
    public void close() {
        clear();

        try {
            this.randomAccessFile.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "cannot close random access file", e);
        }
    }

    @Override
    public boolean containsExperiment(int id) {
        return this.experimentWeakMap.containsKey(id) || this.experimentPositions.containsKey(id);
    }

    @Override
    public boolean containsAvailability(int id) {
        return this.mapOfReferencedAvailabilities.containsKey(id);
    }

    @Override
    public boolean containsInteraction(int id) {
        return this.interactionWeakMap.containsKey(id) || this.interactionPositions.containsKey(id);
    }

    @Override
    public boolean containsInteractor(int id) {
        return this.interactorWeakMap.containsKey(id) || this.interactorPositions.containsKey(id);    }

    @Override
    public boolean containsParticipant(int id) {
        return this.participantWeakMap.containsKey(id) || this.participantPositions.containsKey(id);    }

    @Override
    public boolean containsFeature(int id) {
        return this.featureWeakMap.containsKey(id) || this.featurePositions.containsKey(id);    }

    @Override
    public boolean containsVariableParameter(int id) {
        return this.variableParameterValueWeakMap.containsKey(id) || this.variableParameterValuePositions.containsKey(id);    }

    @Override
    public boolean containsComplex(int id) {
        return this.complexWeakMap.containsKey(id) || this.complexPositions.containsKey(id);    }

    private Unmarshaller getUnmarshaller() throws JAXBException {
        if (this.unmarshaller == null){
            this.unmarshaller = JaxbUnmarshallerFactory.getInstance().createUnmarshaller(this.version, this.interactionCategory);
        }
        return this.unmarshaller;
    }

    private <T extends Object> T loadFromFile(int id) throws IOException, JAXBException, XMLStreamException {

        InputStream in = Channels.newInputStream(this.randomAccessFile.getChannel().position(id));
        T obj = null;
        XMLStreamReader reader = null;
        try{
            Unmarshaller unMarshaller = getUnmarshaller();

            XMLInputFactory xmlif = XMLInputFactory2.newInstance();
            reader = xmlif.createXMLStreamReader(in);
            unMarshaller.setListener(new XmlLocationListener(reader));

            obj = (T)unMarshaller.unmarshal(reader);
        }
        finally {
            if (reader != null){
                reader.close();
            }
            in.close();
        }
        return obj;
    }
}
