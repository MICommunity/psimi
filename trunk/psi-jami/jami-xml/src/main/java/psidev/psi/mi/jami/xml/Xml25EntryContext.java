package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXml25IdCache;
import psidev.psi.mi.jami.xml.exception.PsiXmlParserException;
import psidev.psi.mi.jami.xml.extension.AbstractAvailability;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.extension.InferredInteractionParticipant;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;
import psidev.psi.mi.jami.xml.reference.XmlIdReference;

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

public class Xml25EntryContext {

    private PsiXml25IdCache elementCache;
    private Collection<XmlIdReference> references;
    private Collection<InferredInteraction> inferredInteractions;
    private Entry currentEntry;
    private PsiXmlParserListener listener;

    private Xml25EntryContext(){
    }

    public static Xml25EntryContext getInstance() {
        return instance.get();
    }

    private static ThreadLocal<Xml25EntryContext> instance = new ThreadLocal<Xml25EntryContext>() {
        @Override
        protected Xml25EntryContext initialValue() {
            final Xml25EntryContext context = new Xml25EntryContext();
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

    public void setElementCache(PsiXml25IdCache elementCache) {
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

    public void registerParticipant(int id, Entity o){
        if (this.elementCache != null){
            this.elementCache.registerParticipant(id, o);
        }
    }

    public void registerFeature(int id, Feature o){
        if (this.elementCache != null){
            this.elementCache.registerFeature(id, o);
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

    public void registerReference(XmlIdReference ref){
        if (this.references != null){
            this.references.add(ref);
        }
    }

    public boolean hasInferredInteractions(){
        return !inferredInteractions.isEmpty();
    }

    public boolean hasUnresolvedReferences(){
        return !references.isEmpty();
    }

    public void initialiseInferredInteractionList(){
        this.inferredInteractions = new ArrayList<InferredInteraction>();
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
    }
}
