package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The xml entry context is a context threadlocal that will be valid for the whole xml entry
 * but will be cleared after each entry in the entrySet
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/07/13</pre>
 */

public class XmlEntryContext {

    private Map<Integer, Object> mapOfReferencedObjects;
    private Map<Xref, Publication> mapOfPublications;
    private Collection<XmlIdReference> references;
    private Collection<InferredInteraction> inferredInteractions;
    private Source currentSource;

    private XmlEntryContext(){
        this.mapOfReferencedObjects = new HashMap<Integer, Object>();
        this.mapOfPublications = new HashMap<Xref, Publication>();
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

    public Map<Integer, Object> getMapOfReferencedObjects() {
        return mapOfReferencedObjects;
    }

    public Map<Xref, Publication> getMapOfPublications() {
        return mapOfPublications;
    }

    public Collection<XmlIdReference> getReferences() {
        return references;
    }

    public static void remove(){
        instance.remove();
    }

    public void clear(){
        this.mapOfPublications.clear();
        this.mapOfReferencedObjects.clear();
        this.references.clear();
        this.currentSource = null;
        this.inferredInteractions.clear();
    }

    public Source getCurrentSource() {
        return currentSource;
    }

    public void setCurrentSource(Source source){
        this.currentSource = source;
    }

    public Collection<InferredInteraction> getInferredInteractions() {
        return inferredInteractions;
    }
}
