package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlIdCache;
import psidev.psi.mi.jami.xml.exception.PsiXmlParserException;
import psidev.psi.mi.jami.xml.model.Entry;
import psidev.psi.mi.jami.xml.model.extension.AbstractAvailability;
import psidev.psi.mi.jami.xml.model.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.model.extension.InferredInteractionParticipant;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;
import psidev.psi.mi.jami.xml.model.extension.xml300.BindingFeatures;
import psidev.psi.mi.jami.xml.model.reference.XmlIdReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * The xml entry context is a context threadlocal that will be valid for the whole xml entry
 * but will be cleared after each entry in the entrySet
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/07/13</pre>
 */

public class XmlEntryContext {

    private PsiXmlIdCache elementCache;
    private Collection<XmlIdReference> references;
    private Collection<InferredInteraction> inferredInteractions;
    private Collection<BindingFeatures> bindingFeatures;
    private Entry currentEntry;
    private PsiXmlParserListener listener;

    private XmlEntryContext(){
    }

    public static XmlEntryContext getInstance() {
        return instance.get();
    }

    private static ThreadLocal<XmlEntryContext> instance = new ThreadLocal<XmlEntryContext>() {
        @Override
        protected XmlEntryContext initialValue() {
            final XmlEntryContext context = new XmlEntryContext();
            return context;
        }
    };

    public static void remove(){
        instance.remove();
    }

    public void clear(){
        if (this.elementCache != null){
            this.elementCache.clear();
        }
        if (this.references != null){
            this.references.clear();
        }
        this.currentEntry = null;
        if (this.inferredInteractions != null){
            this.inferredInteractions.clear();
        }
        if (this.bindingFeatures != null){
            this.bindingFeatures.clear();
        }
    }

    public Entry getCurrentEntry() {
        return currentEntry;
    }

    public void setCurrentSource(Entry entry){
        this.currentEntry = entry;
    }

    public PsiXmlParserListener getListener() {
        return listener;
    }

    public void setListener(PsiXmlParserListener listener) {
        this.listener = listener;
    }

    public void setElementCache(PsiXmlIdCache elementCache) {
        this.elementCache = elementCache;
    }

    public void registerAvailability(int id, AbstractAvailability o){
        if (this.elementCache != null){
            this.elementCache.registerAvailability(id, o);
        }
    }

    public void registerExperiment(int id, Experiment o){
        if (this.elementCache != null){
            this.elementCache.registerExperiment(id, o);
        }
    }

    public void registerInteractor(int id, Interactor o){
        if (this.elementCache != null){
            this.elementCache.registerInteractor(id, o);
        }
    }

    public void registerInteraction(int id, Interaction o){
        if (this.elementCache != null){
            this.elementCache.registerInteraction(id, o);
        }
    }

    public void registerParticipant(int id, Participant o){
        if (this.elementCache != null){
            this.elementCache.registerParticipant(id, o);
        }
    }

    public void registerFeature(int id, Feature o){
        if (this.elementCache != null){
            this.elementCache.registerFeature(id, o);
        }
    }

    public void registerVariableParameterValue(int id, VariableParameterValue o){
        if (this.elementCache != null){
            this.elementCache.registerVariableParameterValue(id, o);
        }
    }

    public void registerComplex(int id, Complex o){
        if (this.elementCache != null){
            this.elementCache.registerComplex(id, o);
        }
    }

    public void registerInferredInteraction(InferredInteraction infer){
        if (this.inferredInteractions != null){
            this.inferredInteractions.add(infer);
        }
    }

    public void registerBindingFeature(BindingFeatures infer){
        if (this.bindingFeatures != null){
            this.bindingFeatures.add(infer);
        }
    }

    public void registerReference(XmlIdReference ref){
        if (this.references != null){
            this.references.add(ref);
        }
    }

    public boolean hasInferredInteractions(){
        return !inferredInteractions.isEmpty() || !bindingFeatures.isEmpty();
    }

    public boolean hasUnresolvedReferences(){
        return !references.isEmpty();
    }

    public void initialiseInferredInteractionList(){
        this.inferredInteractions = new ArrayList<InferredInteraction>();
        this.bindingFeatures = new ArrayList<BindingFeatures>();
    }

    public void initialiseReferencesList(){
        this.references = new ArrayList<XmlIdReference>();
    }

    public void resolveInteractorAndExperimentRefs(){
        if (references != null){
            Iterator<XmlIdReference> refIterator = references.iterator();
            while(refIterator.hasNext()){
                XmlIdReference ref = refIterator.next();
                if (this.elementCache == null ||
                        (this.elementCache != null && !ref.resolve(this.elementCache))){
                    if (listener != null){
                        listener.onUnresolvedReference(ref, "Cannot resolve a reference in the xml file");
                    }
                }
                refIterator.remove();
            }
        }
    }

    public void resolveInferredInteractionRefs(){
        if (inferredInteractions != null){
            Iterator<InferredInteraction> inferredIterator = inferredInteractions.iterator();
            while(inferredIterator.hasNext()){
                InferredInteraction inferred = inferredIterator.next();
                if (!inferred.getParticipants().isEmpty()){
                    Iterator<InferredInteractionParticipant> partIterator = inferred.getParticipants().iterator();
                    List<InferredInteractionParticipant> partIterator2 = new ArrayList<InferredInteractionParticipant>(inferred.getParticipants());
                    int currentIndex = 0;

                    while (partIterator.hasNext()){
                        currentIndex++;
                        InferredInteractionParticipant p1 = partIterator.next();
                        for (int i = currentIndex; i < partIterator2.size();i++){
                            InferredInteractionParticipant p2 = partIterator2.get(i);

                            if (p1.getFeature() != null && p2.getFeature() != null){
                                p1.getFeature().getLinkedFeatures().add(p2.getFeature());
                                if (p1.getFeature() != p2.getFeature()){
                                    p2.getFeature().getLinkedFeatures().add(p1.getFeature());
                                }
                            }
                        }
                    }
                }
                else{
                    if (listener != null){
                        listener.onInvalidSyntax(inferred, new PsiXmlParserException("InferredInteraction must have at least one inferredInteractionParticipant."));
                    }
                }

                inferredIterator.remove();
            }
        }

        if (bindingFeatures != null){
            Iterator<BindingFeatures> inferredIterator = bindingFeatures.iterator();
            while(inferredIterator.hasNext()){
                BindingFeatures inferred = inferredIterator.next();
                if (!inferred.getLinkedFeatures().isEmpty()){
                    Iterator<ModelledFeature> partIterator = inferred.getLinkedFeatures().iterator();
                    List<ModelledFeature> partIterator2 = new ArrayList<ModelledFeature>(inferred.getLinkedFeatures());
                    int currentIndex = 0;

                    while (partIterator.hasNext()){
                        currentIndex++;
                        ModelledFeature p1 = partIterator.next();
                        for (int i = currentIndex; i < partIterator2.size();i++){
                            ModelledFeature p2 = partIterator2.get(i);

                            p1.getLinkedFeatures().add(p2);
                            if (p1 != p2){
                                p2.getLinkedFeatures().add(p1);
                            }
                        }
                    }
                }
                else{
                    if (listener != null){
                        listener.onInvalidSyntax(inferred, new PsiXmlParserException("BindingFeatures must have at least one participantFeatureRef."));
                    }
                }

                inferredIterator.remove();
            }
        }
    }
}
