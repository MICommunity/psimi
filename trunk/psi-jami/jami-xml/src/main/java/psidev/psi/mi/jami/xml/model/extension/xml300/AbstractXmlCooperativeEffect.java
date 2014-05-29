package psidev.psi.mi.jami.xml.model.extension.xml300;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.xml.cache.PsiXmlIdCache;
import psidev.psi.mi.jami.xml.model.extension.*;
import psidev.psi.mi.jami.xml.model.reference.AbstractComplexRef;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Abstract class for XML 3.0 cooperative effects
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/05/14</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public abstract class AbstractXmlCooperativeEffect implements CooperativeEffect, FileSourceContext, Locatable{

    private CvTerm outcome;
    private CvTerm response;

    private JAXBAttributeWrapper jaxbAttributeWrapper;
    private JAXBCooperativityEvidenceWrapper jaxbCooperativityEvidenceWrapper;
    private JAXBAffectedInteractionRefWrapper jaxbAffectedInteractionWrapper;

    @XmlLocation
    @XmlTransient
    private Locator locator;
    private PsiXmLocator sourceLocator;

    public AbstractXmlCooperativeEffect(){
    }

    public AbstractXmlCooperativeEffect(CvTerm outcome){
        if (outcome == null){
            throw new IllegalArgumentException("The outcome of a CooperativeEffect cannot be null");
        }
        this.outcome = outcome;
    }

    public AbstractXmlCooperativeEffect(CvTerm outcome, CvTerm response){
        this(outcome);
        this.response = response;
    }

    protected void initialiseCooperativityEvidences(){
        this.jaxbCooperativityEvidenceWrapper = new JAXBCooperativityEvidenceWrapper();
    }

    protected void initialiseAffectedInteractions(){
        this.jaxbAffectedInteractionWrapper = new JAXBAffectedInteractionRefWrapper();
    }

    protected void initialiseAnnotations(){
        this.jaxbAttributeWrapper = new JAXBAttributeWrapper();
    }

    public Collection<CooperativityEvidence> getCooperativityEvidences() {
        if (this.jaxbCooperativityEvidenceWrapper == null){
            initialiseCooperativityEvidences();
        }
        return this.jaxbCooperativityEvidenceWrapper.cooperativityEvidences;
    }

    public Collection<ModelledInteraction> getAffectedInteractions() {
        if (this.jaxbAffectedInteractionWrapper == null){
            initialiseAffectedInteractions();
        }
        return this.jaxbAffectedInteractionWrapper.affectedInteractions;
    }

    public Collection<Annotation> getAnnotations() {
        if (this.jaxbAttributeWrapper == null){
            initialiseAnnotations();
        }
        return this.jaxbAttributeWrapper.annotations;
    }

    public CvTerm getOutCome() {
        if (this.outcome == null){
            this.outcome = new XmlCvTerm(CooperativeEffect.EFFECT_OUTCOME,
                    new XmlXref(CvTermUtils.createPsiMiDatabase(),CooperativeEffect.EFFECT_OUTCOME_MI, CvTermUtils.createIdentityQualifier()));
        }
        return outcome;
    }

    public void setOutCome(CvTerm effect) {
        if (effect == null){
            throw new IllegalArgumentException("The outcome of a CooperativeEffect cannot be null");
        }
        this.outcome = effect;
    }

    public CvTerm getResponse() {
        return this.response;
    }

    public void setResponse(CvTerm response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "Outcome: "+(outcome != null ? outcome.toString() : "") + (response != null ? ", response: " + response.toString() : "");
    }

    @Override
    public Locator sourceLocation() {
        return (Locator)getSourceLocator();
    }

    public FileSourceLocator getSourceLocator() {
        if (sourceLocator == null && locator != null){
            sourceLocator = new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
        }
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else{
            this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
        }
    }

    public void setSourceLocation(PsiXmLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @XmlElement(name = "cooperativityEvidenceList", required = true)
    public void setJAXBCooperativityEvidenceWrapper(JAXBCooperativityEvidenceWrapper wrapper) {
        this.jaxbCooperativityEvidenceWrapper = wrapper;
    }

    @XmlElement(name = "affectedInteractionList", required = true)
    public void setJAXBAffectedInteractionWrapper(JAXBAffectedInteractionRefWrapper wrapper) {
        this.jaxbAffectedInteractionWrapper = wrapper;
    }

    @XmlElement(name = "cooperativeEffectOutcome", required = true)
    public void setJAXBOutCome(XmlCvTerm outcome) {
        this.outcome = outcome;
    }

    @XmlElement(name = "cooperativeEffectResponse")
    public void setJAXBResponse(XmlCvTerm response) {
        this.response = response;
    }

    @XmlElement(name = "attributeList")
    public void setJAXBAttributeWrapper(JAXBAttributeWrapper wrapper) {
        this.jaxbAttributeWrapper = wrapper;
    }

    ////////////////////////////////////////////////////////

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="cooperativityEvidenceWrapper")
    public static class JAXBCooperativityEvidenceWrapper implements Locatable, FileSourceContext {
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<CooperativityEvidence> cooperativityEvidences;

        public JAXBCooperativityEvidenceWrapper(){
            initialiseCooperativityEvidences();
        }

        @Override
        public Locator sourceLocation() {
            return (Locator)getSourceLocator();
        }

        public FileSourceLocator getSourceLocator() {
            if (sourceLocator == null && locator != null){
                sourceLocator = new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
            }
            return sourceLocator;
        }

        public void setSourceLocator(FileSourceLocator sourceLocator) {
            if (sourceLocator == null){
                this.sourceLocator = null;
            }
            else{
                this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
            }
        }

        protected void initialiseCooperativityEvidences(){
            cooperativityEvidences = new ArrayList<CooperativityEvidence>();
        }

        @XmlElement(type=XmlCooperativityEvidence.class, name="cooperativityEvidenceDescription", required = true)
        public List<CooperativityEvidence> getJAXBCooperativityEvidences() {
            return cooperativityEvidences;
        }

        @Override
        public String toString() {
            return "Cooperativity evidence List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="cooperativeEffectAttributeWrapper")
    public static class JAXBAttributeWrapper implements Locatable, FileSourceContext {
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<Annotation> annotations;

        public JAXBAttributeWrapper(){
            initialiseAnnotations();
        }

        @Override
        public Locator sourceLocation() {
            return (Locator)getSourceLocator();
        }

        public FileSourceLocator getSourceLocator() {
            if (sourceLocator == null && locator != null){
                sourceLocator = new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
            }
            return sourceLocator;
        }

        public void setSourceLocator(FileSourceLocator sourceLocator) {
            if (sourceLocator == null){
                this.sourceLocator = null;
            }
            else{
                this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
            }
        }

        protected void initialiseAnnotations(){
            annotations = new ArrayList<Annotation>();
        }

        @XmlElement(type=XmlAnnotation.class, name="attribute", required = true)
        public List<Annotation> getJAXBAttributes() {
            return annotations;
        }

        @Override
        public String toString() {
            return "Cooperative effect attribute List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name = "cooperativeEffectInteractionRefList")
    public static class JAXBAffectedInteractionRefWrapper implements Locatable, FileSourceContext {
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private JAXBAffectedInteractionRefList jaxbAffectedInteractionRefs;
        private List<ModelledInteraction> affectedInteractions;

        public JAXBAffectedInteractionRefWrapper(){
            affectedInteractions = new ArrayList<ModelledInteraction>();
        }

        @Override
        public Locator sourceLocation() {
            return (Locator)getSourceLocator();
        }

        public FileSourceLocator getSourceLocator() {
            if (sourceLocator == null && locator != null){
                sourceLocator = new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
            }
            return sourceLocator;
        }

        public void setSourceLocator(FileSourceLocator sourceLocator) {
            if (sourceLocator == null){
                this.sourceLocator = null;
            }
            else{
                this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
            }
        }

        @XmlElement(name = "affectedInteractionRef", type = Integer.class, required = true)
        public List<Integer> getJAXBAffectedInteractionRefs() {
            if (this.jaxbAffectedInteractionRefs == null){
                this.jaxbAffectedInteractionRefs = new JAXBAffectedInteractionRefList();
            }
            return jaxbAffectedInteractionRefs;
        }

        @Override
        public String toString() {
            return "Cooperative effect affected interaction list: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }

        //////////////////////////////////////////////////////////////
        /**
         * The interaction ref list used by JAXB to populate interaction refs
         */
        private class JAXBAffectedInteractionRefList extends ArrayList<Integer>{

            public JAXBAffectedInteractionRefList(){
                super();
            }

            @Override
            public boolean add(Integer val) {
                if (val == null){
                    return false;
                }
                return affectedInteractions.add(new ModelledInteractionRef(val));
            }

            @Override
            public boolean addAll(Collection<? extends Integer> c) {
                if (c == null){
                    return false;
                }
                boolean added = false;

                for (Integer a : c){
                    if (add(a)){
                        added = true;
                    }
                }
                return added;
            }

            @Override
            public void add(int index, Integer element) {
                addToSpecificIndex(index, element);
            }

            @Override
            public boolean addAll(int index, Collection<? extends Integer> c) {
                int newIndex = index;
                if (c == null){
                    return false;
                }
                boolean add = false;
                for (Integer a : c){
                    if (addToSpecificIndex(newIndex, a)){
                        newIndex++;
                        add = true;
                    }
                }
                return add;
            }

            private boolean addToSpecificIndex(int index, Integer val) {
                if (val == null){
                    return false;
                }
                affectedInteractions.add(index, new ModelledInteractionRef(val));
                return true;
            }

            @Override
            public String toString() {
                return "Cooperative effect affected interaction list: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
            }
        }

        ////////////////////////////////////////////////////// classes
        /**
         * interaction ref for affected cooperative interaction
         */
        private class ModelledInteractionRef extends AbstractComplexRef {
            private PsiXmLocator sourceLocator;

            public ModelledInteractionRef(int ref) {
                super(ref);
            }

            public boolean resolve(PsiXmlIdCache parsedObjects) {
                if (parsedObjects.contains(this.ref)){
                    Object object = parsedObjects.get(this.ref);
                    // convert interaction evidence in a complex
                    if (object instanceof AbstractXmlInteractionEvidence){
                        ModelledInteraction interaction = new XmlInteractionEvidenceComplexWrapper((AbstractXmlInteractionEvidence)object);
                        affectedInteractions.remove(this);
                        affectedInteractions.add(interaction);
                        return true;
                    }
                    // wrap modelled interaction
                    else if (object instanceof ModelledInteraction){
                        affectedInteractions.remove(this);
                        affectedInteractions.add((ModelledInteraction) object);
                    }
                    // wrap basic interaction
                    else if (object instanceof AbstractXmlBasicInteraction){
                        ModelledInteraction interaction = new XmlBasicInteractionComplexWrapper((AbstractXmlBasicInteraction)object);
                        affectedInteractions.remove(this);
                        affectedInteractions.add(interaction);
                        return true;
                    }
                }
                return false;
            }

            @Override
            public String toString() {
                return "Affected modelled interaction Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
            }

            public FileSourceLocator getSourceLocator() {
                return this.sourceLocator;
            }

            public void setSourceLocator(FileSourceLocator sourceLocator) {
                if (sourceLocator == null){
                    this.sourceLocator = null;
                }
                else{
                    this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
                }
            }
        }
    }
}
