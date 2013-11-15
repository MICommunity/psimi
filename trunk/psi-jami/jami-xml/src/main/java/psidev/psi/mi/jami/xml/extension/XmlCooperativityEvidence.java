package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CooperativityEvidence;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.utils.comparator.cooperativity.UnambiguousCooperativityEvidenceComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Xml implementation for cooperativity evidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */

public class XmlCooperativityEvidence implements CooperativityEvidence, FileSourceContext {
    private PsiXmLocator sourceLocator;
    private Experiment exp;
    private Publication publication;
    private Collection<CvTerm> evidenceMethods;

    public XmlCooperativityEvidence(Experiment exp) {
        if (exp == null){
            throw new IllegalArgumentException("The experiment is mandatory");
        }
        this.exp = exp;
    }

    public Publication getPublication() {
        if (this.publication == null){
            if (exp.getPublication() == null){
                this.publication = new BibRef();
            }
            else{
                this.publication = exp.getPublication();
            }
            this.exp = null;
        }
        return this.publication;
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator locator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else{
            this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getColumnNumber(), null);
        }
    }

    public void setSourceLocator(PsiXmLocator locator) {
        this.sourceLocator = locator;
    }

    @Override
    public String toString() {
        return "Cooperativity evidence: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }

    protected void initialiseEvidenceMethods(){
        this.evidenceMethods = new ArrayList<CvTerm>();
    }

    protected void initialiseEvidenceMethodsWith(Collection<CvTerm> methods){
        if (methods == null){
            this.evidenceMethods = Collections.EMPTY_LIST;
        }
        else{
            this.evidenceMethods = methods;
        }
    }

    public void setPublication(Publication publication) {
        if (publication == null){
            throw new IllegalArgumentException("The publication cannot be null in a CooperativityEvidence");
        }
        this.publication = publication;
    }

    public Collection<CvTerm> getEvidenceMethods() {

        if (evidenceMethods == null){
            initialiseEvidenceMethods();
        }
        return evidenceMethods;
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
}
