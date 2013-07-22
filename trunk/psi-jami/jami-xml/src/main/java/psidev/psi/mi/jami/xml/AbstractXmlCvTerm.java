package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Abstract cv term
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/07/13</pre>
 */
public abstract class AbstractXmlCvTerm implements CvTerm, Serializable{
    private CvTermXrefAndIdentifierContainer xrefContainer;
    private NamesContainer namesContainer;
    private Collection<Annotation> annotations;

    public AbstractXmlCvTerm(){

    }

    public AbstractXmlCvTerm(String shortName){
        if (shortName == null){
            throw new IllegalArgumentException("The short name is required and cannot be null");
        }
        setShortName(shortName);
    }

    public AbstractXmlCvTerm(String shortName, String miIdentifier){
        this(shortName);
        setMIIdentifier(miIdentifier);
    }

    public AbstractXmlCvTerm(String shortName, String fullName, String miIdentifier){
        this(shortName, miIdentifier);
        setFullName(fullName);
    }

    public AbstractXmlCvTerm(String shortName, Xref ontologyId){
        this(shortName);
        if (ontologyId != null){
            getIdentifiers().add(ontologyId);
        }
    }

    public AbstractXmlCvTerm(String shortName, String fullName, Xref ontologyId){
        this(shortName, ontologyId);
        setFullName(fullName);
    }


    protected CvTermXrefAndIdentifierContainer getXrefContainer() {
        if (xrefContainer != null){
            if (xrefContainer.isEmpty()){
                return null;
            }
        }
        return xrefContainer;
    }

    protected void setXrefContainer(CvTermXrefAndIdentifierContainer value) {
        this.xrefContainer = value;
    }

    protected NamesContainer getNamesContainer() {
        if (namesContainer == null){
            namesContainer = new NamesContainer();
            namesContainer.setShortLabel(PsiXmlUtils.UNSPECIFIED);
        }
        return namesContainer;
    }

    protected void setNamesContainer(NamesContainer value) {
        if (value == null){
            namesContainer = new NamesContainer();
            namesContainer.setShortLabel(PsiXmlUtils.UNSPECIFIED);
        }
        else {
            this.namesContainer = value;
            if (this.namesContainer.getShortLabel() == null){
                namesContainer.setShortLabel(PsiXmlUtils.UNSPECIFIED);
            }
        }
    }

    public Collection<Annotation> getAnnotations() {
        if (annotations == null){
            this.annotations = new ArrayList<Annotation>();
        }
        return this.annotations;
    }

    @Override
    public int hashCode() {
        return UnambiguousCvTermComparator.hashCode(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof CvTerm)){
            return false;
        }

        return UnambiguousCvTermComparator.areEquals(this, (CvTerm) o);
    }

    @Override
    public String toString() {
        if (xrefContainer == null){
            return getShortName();
        }
        else {
            return (xrefContainer.getMIIdentifier() != null ? xrefContainer.getMIIdentifier() : (xrefContainer.getMODIdentifier() != null ? xrefContainer.getMODIdentifier() : (xrefContainer.getPARIdentifier() != null ? xrefContainer.getPARIdentifier() : "-"))) + " ("+getShortName()+")";
        }
    }
}
