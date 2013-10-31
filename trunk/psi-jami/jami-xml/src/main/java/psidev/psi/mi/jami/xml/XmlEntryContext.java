package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;

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
    private Map<Integer, Complex> mapOfReferencedComplexes;
    private Collection<XmlIdReference> references;
    private Collection<InferredInteraction> inferredInteractions;
    private XmlEntry currentEntry;
    private PsiXmlParserListener listener;

    private XmlEntryContext(){
        this.mapOfReferencedObjects = new HashMap<Integer, Object>();
        this.references = new ArrayList<XmlIdReference>();
        this.inferredInteractions = new ArrayList<InferredInteraction>();
        this.mapOfReferencedComplexes = new HashMap<Integer, Complex>();
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

    public Collection<XmlIdReference> getReferences() {
        return references;
    }

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

    public Collection<InferredInteraction> getInferredInteractions() {
        return inferredInteractions;
    }

    public Map<Integer, Complex> getMapOfReferencedComplexes() {
        return mapOfReferencedComplexes;
    }

    public PsiXmlParserListener getListener() {
        return listener;
    }

    public void setListener(PsiXmlParserListener listener) {
        this.listener = listener;
    }
}
