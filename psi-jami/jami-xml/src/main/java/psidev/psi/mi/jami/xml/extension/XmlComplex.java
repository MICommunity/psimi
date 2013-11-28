package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultComplex;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.xml.Entry;
import psidev.psi.mi.jami.xml.Xml25EntryContext;

import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Xml implementation for complex
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlTransient
public class XmlComplex extends DefaultComplex implements ExtendedPsi25Interactor,FileSourceContext, ExtendedPsi25Interaction<ModelledParticipant>{

    private int id;
    private PsiXmLocator sourceLocator;
    private List<CvTerm> interactionTypes;
    private Entry entry;
    private Boolean intraMolecular;
    private List<InferredInteraction> inferredInteractions;

    public XmlComplex(String name, CvTerm type) {
        super(name, type != null ? type : new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier())));
    }

    public XmlComplex(String name, String fullName, CvTerm type) {
        super(name, fullName, type != null ? type : new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier())));
    }

    public XmlComplex(String name, CvTerm type, Organism organism) {
        super(name, type != null ? type : new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier()))
                , organism);
    }

    public XmlComplex(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type != null ? type : new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier()))
                , organism);
    }

    public XmlComplex(String name, CvTerm type, Xref uniqueId) {
        super(name, type != null ? type : new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier()))
                , uniqueId);
    }

    public XmlComplex(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type != null ? type : new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier()))
                , uniqueId);
    }

    public XmlComplex(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type != null ? type : new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier()))
                , organism, uniqueId);
    }

    public XmlComplex(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type != null ? type : new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier()))
                , organism, uniqueId);
    }

    public XmlComplex(String name) {
        super(name,  new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier())));
    }

    public XmlComplex(String name, String fullName) {
        super(name, fullName,  new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier())));
    }

    public XmlComplex(String name, Organism organism) {
        super(name,  new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier())), organism);
    }

    public XmlComplex(String name, String fullName, Organism organism) {
        super(name, fullName,  new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier())), organism);
    }

    public XmlComplex(String name, Xref uniqueId) {
        super(name,  new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier())), uniqueId);
    }

    public XmlComplex(String name, String fullName, Xref uniqueId) {
        super(name, fullName,  new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier())), uniqueId);
    }

    public XmlComplex(String name, Organism organism, Xref uniqueId) {
        super(name,  new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier())),
                organism, uniqueId);
    }

    public XmlComplex(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName,  new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier())),
                organism, uniqueId);
    }

    @Override
    public void setPhysicalProperties(String properties) {
        Collection<Annotation> complexAnnotationList = getAnnotations();

        // add new physical properties if not null
        if (properties != null){

            CvTerm complexPhysicalProperties = CvTermUtils.createComplexPhysicalProperties();
            // first remove old physical property if not null
            if (getPhysicalProperties() != null){
                Collection<Annotation> physicalAnnots = AnnotationUtils.collectAllAnnotationsHavingTopic(complexAnnotationList, Annotation.COMPLEX_PROPERTIES_MI, Annotation.COMPLEX_PROPERTIES);
                Annotation physical = null;
                for (Annotation phys : physicalAnnots){
                    if (phys.getValue() != null && phys.getValue().equalsIgnoreCase(getPhysicalProperties())){
                        physical = phys;
                        break;
                    }
                }
                complexAnnotationList.remove(physical);
            }
            complexAnnotationList.add(new XmlAnnotation(complexPhysicalProperties, properties));
        }
        // remove all physical properties if the collection is not empty
        else if (!complexAnnotationList.isEmpty()) {
            AnnotationUtils.removeAllAnnotationsWithTopic(complexAnnotationList, Annotation.COMPLEX_PROPERTIES_MI, Annotation.COMPLEX_PROPERTIES);
        }
    }

    @Override
    public CvTerm getInteractionType() {
        return (this.interactionTypes != null && !this.interactionTypes.isEmpty())? this.interactionTypes.iterator().next() : null;
    }

    @Override
    public void setInteractionType(CvTerm term) {
        if (this.interactionTypes == null){
            this.interactionTypes = new ArrayList<CvTerm>();
        }
        if (term == null){
            if (!this.interactionTypes.isEmpty()){
                this.interactionTypes.remove(0);
            }
            this.interactionTypes.add(0,  new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier())));
        }
        else {
            if (!this.interactionTypes.isEmpty()){
                this.interactionTypes.remove(0);
            }
            this.interactionTypes.add(0, term);
        }
    }

    @Override
    public List<CvTerm> getInteractionTypes() {
        if (this.interactionTypes == null){
            this.interactionTypes = new ArrayList<CvTerm>();
        }
        return this.interactionTypes;    }

    @Override
    public Entry getEntry() {
        return this.entry;
    }

    @Override
    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    @Override
    public List<InferredInteraction> getInferredInteractions() {
         if (this.inferredInteractions == null){
             this.inferredInteractions = new ArrayList<InferredInteraction>();
         }
        return this.inferredInteractions;
    }

    /**
     * Gets the value of the id property.
     *
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     */
    public void setId(int value) {
        this.id = value;
        Xml25EntryContext.getInstance().registerComplex(this.id, this);
        if (getSourceLocator() != null){
            sourceLocator.setObjectId(this.id);
        }
    }

    public boolean isIntraMolecular(){
        return intraMolecular != null ? intraMolecular : false;
    }

    /**
     * Sets the value of the intraMolecular property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setIntraMolecular(boolean value) {
        this.intraMolecular = value;
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else{
            this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), id);
        }
    }

    public void setSourceLocator(PsiXmLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public String toString() {
        return "Complex : "+(sourceLocator != null ? sourceLocator.toString():super.toString());
    }
}
