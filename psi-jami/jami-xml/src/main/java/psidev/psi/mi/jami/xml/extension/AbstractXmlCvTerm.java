package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

    private PsiXmLocator sourceLocator;
    private JAXBAttributeWrapper jaxbAttributeWrapper;

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
        if (name == null){
            throw new IllegalArgumentException("The short name cannot be null");
        }
        getNamesContainer().setShortLabel(name);
    }

    public String getFullName() {
        return getNamesContainer().getFullName();
    }

    public void setFullName(String name) {
        getNamesContainer().setFullName(name);
    }

    public Collection<Xref> getIdentifiers() {
        return getXrefContainer().getIdentifiers();
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
        return getXrefContainer().getXrefs();
    }

    public Collection<Alias> getSynonyms() {
        return getNamesContainer().getAliases();
    }

    public Collection<Annotation> getAnnotations() {
        if (jaxbAttributeWrapper == null){
            initialiseAnnotationWrapper();
        }
        return this.jaxbAttributeWrapper.annotations;
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

    protected void initialiseAnnotationWrapper(){
        this.jaxbAttributeWrapper = new JAXBAttributeWrapper();
    }

    protected CvTermXrefContainer getXrefContainer() {
        if (xrefContainer == null){
            xrefContainer = new CvTermXrefContainer();
        }
        return xrefContainer;
    }

    public void setJAXBXref(CvTermXrefContainer value) {
        this.xrefContainer = value;
    }

    protected NamesContainer getNamesContainer() {
        if (namesContainer == null){
            namesContainer = new NamesContainer();
            namesContainer.setShortLabel(PsiXmlUtils.UNSPECIFIED);
        }
        return namesContainer;
    }

    public void setJAXBNames(NamesContainer value) {
        this.namesContainer = value;
        if (this.namesContainer != null && this.namesContainer.getShortLabel() == null){
            this.namesContainer.setShortLabel(PsiXmlUtils.UNSPECIFIED);
        }
    }

    protected JAXBAttributeWrapper getAttributeWrapper() {
        if (this.jaxbAttributeWrapper == null){
           initialiseAnnotationWrapper();
        }
        return this.jaxbAttributeWrapper;
    }

    protected void setAttributeWrapper(JAXBAttributeWrapper jaxbAttributeWrapper) {
        this.jaxbAttributeWrapper = jaxbAttributeWrapper;
    }

    //////////////////////////////// class wrapper

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="cvAnnotationWrapper")
    public static class JAXBAttributeWrapper implements Locatable, FileSourceContext{
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private Collection<Annotation> annotations;

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

        protected void initialiseAnnotationsWith(List<Annotation> annotations){
            if (annotations == null){
                this.annotations = Collections.EMPTY_LIST;
            }
            else {
                this.annotations = annotations;
            }
        }

        @XmlElement(type=XmlAnnotation.class, name="attribute", required = true)
        public List<Annotation> getJAXBAttributes() {
            return (List<Annotation>)annotations;
        }
    }
}
