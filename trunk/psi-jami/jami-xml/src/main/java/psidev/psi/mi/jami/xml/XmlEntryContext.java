package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.xml.exception.PsiXmlParserException;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.extension.InferredInteractionParticipant;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;

import java.util.*;

/**
 * The xml entry context is a context threadlocal that will be valid for the whole xml entry
 * but will be cleared after each entry in the entrySet
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/07/13</pre>
 */

public class XmlEntryContext {

    private PsiXml25IdIndex mapOfReferencedObjects;
    private PsiXml25IdIndex mapOfReferencedComplexes;
    private Collection<XmlIdReference> references;
    private Collection<InferredInteraction> inferredInteractions;
    private XmlEntry currentEntry;
    private PsiXmlParserListener listener;

    private XmlEntryContext(){
        this.references = new ArrayList<XmlIdReference>();
        this.inferredInteractions = new ArrayList<InferredInteraction>();
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
        this.mapOfReferencedObjects.clear();
        this.references.clear();
        this.currentEntry = null;
        this.inferredInteractions.clear();
        this.mapOfReferencedComplexes.clear();
    }

    public XmlEntry getCurrentEntry() {
        return currentEntry;
    }

    public void setCurrentSource(XmlEntry entry){
        this.currentEntry = entry;
    }

    public PsiXmlParserListener getListener() {
        return listener;
    }

    public void setListener(PsiXmlParserListener listener) {
        this.listener = listener;
    }

    public void setMapOfReferencedObjects(PsiXml25IdIndex mapOfReferencedObjects) {
        this.mapOfReferencedObjects = mapOfReferencedObjects;
    }

    public void setMapOfReferencedComplexes(PsiXml25IdIndex mapOfReferencedComplexes) {
        this.mapOfReferencedComplexes = mapOfReferencedComplexes;
    }

    public void registerObject(int id, Object o){
        if (this.mapOfReferencedObjects != null){
            this.mapOfReferencedObjects.put(id, o);
        }
    }

    public void registerComplex(int id, Complex o){
        if (this.mapOfReferencedComplexes != null){
            this.mapOfReferencedComplexes.put(id, o);
        }
    }

    public void registerInferredInteraction(InferredInteraction infer){
        this.inferredInteractions.add(infer);
    }

    public void registerReference(XmlIdReference ref){
        this.references.add(ref);
    }

    public boolean hasInferredInteractions(){
        return !inferredInteractions.isEmpty();
    }

    public boolean hasUnresolvedReferences(){
        return !references.isEmpty();
    }

    public void resolveInteractorAndExperimentRefs(){
        Iterator<XmlIdReference> refIterator = references.iterator();
        while(refIterator.hasNext()){
            XmlIdReference ref = refIterator.next();
            // when we have complex reference, look at complexes already indexed
            if (ref.isComplexReference()){
                if (this.mapOfReferencedComplexes != null && !ref.resolve(this.mapOfReferencedComplexes)){
                    if (this.mapOfReferencedObjects == null ||
                            (this.mapOfReferencedObjects != null && !ref.resolve(this.mapOfReferencedObjects))){
                        if (listener != null){
                            listener.onUnresolvedReference(ref, "Cannot resolve a reference in the xml file");
                        }
                    }
                }
                else if (this.mapOfReferencedObjects == null ||
                        (this.mapOfReferencedObjects != null && !ref.resolve(this.mapOfReferencedObjects))){
                    if (listener != null){
                        listener.onUnresolvedReference(ref, "Cannot resolve a reference in the xml file");
                    }
                }
            }
            else if (this.mapOfReferencedObjects == null ||
                    (this.mapOfReferencedObjects != null && !ref.resolve(this.mapOfReferencedObjects))){
                if (listener != null){
                    listener.onUnresolvedReference(ref, "Cannot resolve a reference in the xml file");
                }
            }
            refIterator.remove();
        }
    }

    public void resolveInferredInteractionRefs(){
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
