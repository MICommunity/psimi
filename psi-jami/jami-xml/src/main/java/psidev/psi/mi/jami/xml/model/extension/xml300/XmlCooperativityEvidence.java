package psidev.psi.mi.jami.xml.model.extension.xml300;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CooperativityEvidence;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.utils.comparator.cooperativity.UnambiguousCooperativityEvidenceComparator;
import psidev.psi.mi.jami.xml.model.extension.BibRef;
import psidev.psi.mi.jami.xml.model.extension.PsiXmLocator;
import psidev.psi.mi.jami.xml.model.extension.XmlCvTerm;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Xml 3.0 implementation for cooperativity evidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public class XmlCooperativityEvidence implements CooperativityEvidence, FileSourceContext, Locatable {
    private PsiXmLocator sourceLocator;
    private Publication publication;

    @XmlLocation
    @XmlTransient
    private Locator locator;
    private JAXBEvidenceMethodWrapper jaxbEvidenceMethodWrapper;

    public XmlCooperativityEvidence() {
    }

    public XmlCooperativityEvidence(Publication pub) {
        if (pub == null){
            throw new IllegalArgumentException("The publication is mandatory");
        }
        this.publication = pub;
    }

    public Publication getPublication() {
        if (this.publication == null){
            this.publication = new BibRef();
        }
        return this.publication;
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

    @Override
    public String toString() {
        return "Cooperativity evidence: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }

    protected void initialiseEvidenceMethods(){
        this.jaxbEvidenceMethodWrapper = new JAXBEvidenceMethodWrapper();
    }

    public void setPublication(Publication publication) {
        if (publication == null){
            throw new IllegalArgumentException("The publication cannot be null in a CooperativityEvidence");
        }
        this.publication = publication;
    }

    public Collection<CvTerm> getEvidenceMethods() {

        if (this.jaxbEvidenceMethodWrapper == null){
            initialiseEvidenceMethods();
        }
        return this.jaxbEvidenceMethodWrapper.evidenceMethods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof CooperativityEvidence)){
            return false;
        }

        return UnambiguousCooperativityEvidenceComparator.areEquals(this, (CooperativityEvidence) o);
    }

    @Override
    public int hashCode() {
        return UnambiguousCooperativityEvidenceComparator.hashCode(this);
    }

    @XmlElement(name = "bibref", required = true)
    public void setJAXBPublication(BibRef bibRef) {
        this.publication = bibRef;
    }

    @XmlElement(name="evidenceMethodList")
    public void setJAXBEvidenceMethodWrapper(JAXBEvidenceMethodWrapper wrapper) {
        this.jaxbEvidenceMethodWrapper = wrapper;
    }

    ////////////////////////////////////////////////////////////////// classes

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name = "evidenceMethodList")
    public static class JAXBEvidenceMethodWrapper implements Locatable, FileSourceContext {
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<CvTerm> evidenceMethods;

        public JAXBEvidenceMethodWrapper(){
            evidenceMethods = new ArrayList<CvTerm>();
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

        @XmlElement(name = "evidenceMethod", type = XmlCvTerm.class, required = true)
        public List<CvTerm> getJAXBEvidenceMethods() {
            return evidenceMethods;
        }

        @Override
        public String toString() {
            return "Evidence methods list: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }
}
