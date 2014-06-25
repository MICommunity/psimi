package psidev.psi.mi.jami.xml.model.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Participant;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for xml interactions implementing ExtendedPsiXmlInteraction interface
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/07/13</pre>
 */
@XmlTransient
public abstract class AbstractPsiXmlInteraction<T extends Participant> extends AbstractXmlInteraction<T> implements ExtendedPsiXmlInteraction<T>{

    private List<CvTerm> interactionTypes;
    private JAXBInferredInteractionWrapper jaxbInferredInteractionWrapper;

    public AbstractPsiXmlInteraction(){
        super();
    }

    public AbstractPsiXmlInteraction(String shortName){
        super(shortName);
    }

    public AbstractPsiXmlInteraction(String shortName, CvTerm type){
        super(shortName);
        setInteractionType(type);
    }

    public CvTerm getInteractionType() {
        return (this.interactionTypes != null && !this.interactionTypes.isEmpty())? this.interactionTypes.iterator().next() : null;
    }

    public void setInteractionType(CvTerm term) {
        if (this.interactionTypes == null && term != null){
            this.interactionTypes = new ArrayList<CvTerm>();
            this.interactionTypes.add(term);
        }
        else if (this.interactionTypes != null){
            if (!this.interactionTypes.isEmpty() && term == null){
                this.interactionTypes.remove(0);
            }
            else if (term != null){
                this.interactionTypes.remove(0);
                this.interactionTypes.add(0, term);
            }
        }
    }

    /**
     * Gets the value of the interactionTypeList property.
     *
     * @return
     *     possible object is
     *     {@link XmlCvTerm }
     *
     */
    public List<CvTerm> getInteractionTypes() {
        if (this.interactionTypes == null){
           this.interactionTypes = new ArrayList<CvTerm>();
        }
        return this.interactionTypes;
    }

    public List<InferredInteraction> getInferredInteractions() {
        if (this.jaxbInferredInteractionWrapper == null){
            this.jaxbInferredInteractionWrapper = new JAXBInferredInteractionWrapper();
        }
        return this.jaxbInferredInteractionWrapper.inferredInteractions;
    }

    public void setJAXBInferredInteractionWrapper(JAXBInferredInteractionWrapper jaxbInferredWrapper) {
        this.jaxbInferredInteractionWrapper = jaxbInferredWrapper;
    }

    ////////////////////////////////////////////////////////////////// classes

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="inferredInteractionWrapper")
    public static class JAXBInferredInteractionWrapper implements Locatable, FileSourceContext{
        private PsiXmlLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<InferredInteraction> inferredInteractions;

        public JAXBInferredInteractionWrapper(){
            initialiseInferredInteractions();
        }

        @Override
        public Locator sourceLocation() {
            return (Locator)getSourceLocator();
        }

        public FileSourceLocator getSourceLocator() {
            if (sourceLocator == null && locator != null){
                sourceLocator = new PsiXmlLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
            }
            return sourceLocator;
        }

        public void setSourceLocator(FileSourceLocator sourceLocator) {
            if (sourceLocator == null){
                this.sourceLocator = null;
            }
            else if (sourceLocator instanceof PsiXmlLocator){
                this.sourceLocator = (PsiXmlLocator)sourceLocator;
            }
            else {
                this.sourceLocator = new PsiXmlLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
            }
        }

        protected void initialiseInferredInteractions(){
            inferredInteractions = new ArrayList<InferredInteraction>();
        }

        @XmlElement(name="inferredInteraction", type = InferredInteraction.class, required = true)
        public List<InferredInteraction> getJAXBInferredInteractions() {
            return inferredInteractions;
        }

        @Override
        public String toString() {
            return "Inferred Interaction List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }
}
