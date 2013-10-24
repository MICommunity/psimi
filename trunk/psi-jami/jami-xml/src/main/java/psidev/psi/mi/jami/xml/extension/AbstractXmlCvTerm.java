package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Abstract cv term
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/07/13</pre>
 */
@XmlTransient
public abstract class AbstractXmlCvTerm implements CvTerm, FileSourceContext, Locatable{
    private CvTermXrefContainer xrefContainer;
    private NamesContainer namesContainer;
    private Collection<Annotation> annotations;

    private PsiXmLocator sourceLocator;

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

    public String getShortName() {
        return getNamesContainer().getShortLabel();
    }

    public void setShortName(String name) {
        getNamesContainer().setShortLabel(name != null ? name : PsiXmlUtils.UNSPECIFIED);
    }

    public String getFullName() {
        return getNamesContainer().getFullName();
    }

    public void setFullName(String name) {
        getNamesContainer().setFullName(name);
    }

    public Collection<Xref> getIdentifiers() {
        return getXrefContainer().getAllIdentifiers();
    }

    public String getMIIdentifier() {
        return getXrefContainer().getMIIdentifier();
    }

    public String getMODIdentifier() {
        return getXrefContainer().getMODIdentifier();
    }

    public String getPARIdentifier() {
        return getXrefContainer().getPARIdentifier();
    }

    public void setMIIdentifier(String mi) {
        getXrefContainer().setMIIdentifier(mi);
    }

    public void setMODIdentifier(String mod) {
        getXrefContainer().setMODIdentifier(mod);
    }

    public void setPARIdentifier(String par) {
        getXrefContainer().setPARIdentifier(par);
    }

    public Collection<Xref> getXrefs() {
        return getXrefContainer().getAllXrefs();
    }

    public Collection<Alias> getSynonyms() {
        return getNamesContainer().getAliases();
    }

    public Collection<Annotation> getAnnotations() {
        if (annotations == null){
            initialiseAnnotations();
        }
        return this.annotations;
    }

    @Override
    public Locator sourceLocation() {
        return (Locator)getSourceLocator();
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = (PsiXmLocator)sourceLocator;
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

    protected void initialiseAnnotations(){
        this.annotations = new ArrayList<Annotation>();
    }

    protected CvTermXrefContainer getXrefContainer() {
        if (xrefContainer == null){
            xrefContainer = new CvTermXrefContainer();
        }
        return xrefContainer;
    }

    protected void setXrefContainer(CvTermXrefContainer value) {
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

    protected ArrayList<Annotation> getAttributes() {
        return (ArrayList<Annotation>)this.annotations;
    }

    protected void setAttributes(ArrayList<Annotation> annot){
        this.annotations = annot;
    }

    protected void initialiseAnnotationsWith(ArrayList<Annotation> annotations){
        if (annotations == null){
            this.annotations = Collections.EMPTY_LIST;
        }
        else {
            this.annotations = annotations;
        }
    }
}
