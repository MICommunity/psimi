package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.xml.XmlEntryContext;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Xml implementation for complex
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public abstract class AbstractXmlComplex extends AbstractXmlModelledInteraction implements Complex,ExtendedPsiXmlInteractor {

    private CvTerm interactorType;
    private Organism organism;

    public AbstractXmlComplex(){
        super();
        setInteractorType(null);
        this.organism = null;
    }

    public AbstractXmlComplex(String name, CvTerm type){
        super(name);
        if (name == null || (name != null && name.length() == 0)){
            throw new IllegalArgumentException("The short name cannot be null or empty.");
        }
        if (type == null){
            this.interactorType = new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier()));
        }
        else {
            this.interactorType = type;
        }
    }

    public AbstractXmlComplex(String name, String fullName, CvTerm type){
        this(name, type);
        setFullName(fullName);
    }

    public AbstractXmlComplex(String name, CvTerm type, Organism organism){
        this(name, type);
        this.organism = organism;
    }

    public AbstractXmlComplex(String name, String fullName, CvTerm type, Organism organism){
        this(name, fullName, type);
        this.organism = organism;
    }

    public AbstractXmlComplex(String name, CvTerm type, Xref uniqueId){
        this(name, type);
        getIdentifiers().add(uniqueId);
    }

    public AbstractXmlComplex(String name, String fullName, CvTerm type, Xref uniqueId){
        this(name, fullName, type);
        getIdentifiers().add(uniqueId);
    }

    public AbstractXmlComplex(String name, CvTerm type, Organism organism, Xref uniqueId){
        this(name, type, organism);
        getIdentifiers().add(uniqueId);
    }

    public AbstractXmlComplex(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId){
        this(name, fullName, type, organism);
        getIdentifiers().add(uniqueId);
    }

    public AbstractXmlComplex(String name){
        super(name);
        if (name == null || (name != null && name.length() == 0)){
            throw new IllegalArgumentException("The short name cannot be null or empty.");
        }
        this.interactorType = new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier()));
    }

    public AbstractXmlComplex(String name, String fullName){
        this(name);
        setFullName(fullName);
    }

    public AbstractXmlComplex(String name, Organism organism){
        this(name);
        this.organism = organism;
        this.interactorType = new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier()));
    }

    public AbstractXmlComplex(String name, String fullName, Organism organism){
        this(name, fullName);
        this.organism = organism;
    }

    public AbstractXmlComplex(String name, Xref uniqueId){
        this(name);
        getIdentifiers().add(uniqueId);
        this.interactorType = new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier()));
    }

    public AbstractXmlComplex(String name, String fullName, Xref uniqueId){
        this(name, fullName);
        getIdentifiers().add(uniqueId);
        this.interactorType = new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier()));
    }

    public AbstractXmlComplex(String name, Organism organism, Xref uniqueId){
        this(name, organism);
        getIdentifiers().add(uniqueId);
        this.interactorType = new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier()));
    }

    public AbstractXmlComplex(String name, String fullName, Organism organism, Xref uniqueId){
        this(name, fullName, organism);
        getIdentifiers().add(uniqueId);
        this.interactorType = new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier()));
    }

    @Override
    public void setInteractorType(CvTerm interactorType) {
        if (interactorType == null){
            this.interactorType = new XmlCvTerm(Complex.COMPLEX, new XmlXref(CvTermUtils.createPsiMiDatabase(), Complex.COMPLEX_MI, CvTermUtils.createIdentityQualifier()));
        }
        else {
            this.interactorType = interactorType;
        }
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

    /**
     * Sets the value of the id property.
     *
     */
    public void setId(int value) {
        super.setId(value);
        XmlEntryContext.getInstance().registerComplex(getId(), this);
    }

    @Override
    public String getPhysicalProperties() {
        Annotation physical = AnnotationUtils.collectFirstAnnotationWithTopic(getAnnotations(), Annotation.COMPLEX_PROPERTIES_MI, Annotation.COMPLEX_PROPERTIES);
        return physical != null ? physical.getValue() : null;
    }

    @Override
    public String getRecommendedName() {
        Alias recommended = AliasUtils.collectFirstAliasWithType(getAliases(), Alias.COMPLEX_RECOMMENDED_NAME_MI, Alias.COMPLEX_RECOMMENDED_NAME);
        return recommended != null ? recommended.getName() : null;
    }

    @Override
    public void setRecommendedName(String name) {
        Collection<Alias> complexAliasesList = getAliases();

        // add new physical properties if not null
        if (name != null){

            CvTerm complexRecommendedName = CvTermUtils.createComplexRecommendedName();
            // first remove old name property if not null
            if (getRecommendedName() != null){
                Collection<Alias> names = AliasUtils.collectAllAliasesHavingType(complexAliasesList, Alias.COMPLEX_RECOMMENDED_NAME_MI,
                        Alias.COMPLEX_RECOMMENDED_NAME);
                Alias aliasName = null;
                for (Alias phys : names){
                    if (phys.getName().equalsIgnoreCase(getRecommendedName())){
                        aliasName = phys;
                        break;
                    }
                }
                complexAliasesList.remove(aliasName);
            }
            complexAliasesList.add(new XmlAlias(name, complexRecommendedName));
        }
        // remove all physical properties if the collection is not empty
        else if (!complexAliasesList.isEmpty()) {
            AliasUtils.removeAllAliasesWithType(complexAliasesList, Alias.COMPLEX_RECOMMENDED_NAME_MI,
                    Alias.COMPLEX_RECOMMENDED_NAME);
        }
    }

    @Override
    public String getSystematicName() {
        Alias systematic = AliasUtils.collectFirstAliasWithType(getAliases(), Alias.COMPLEX_SYSTEMATIC_NAME_MI, Alias.COMPLEX_SYSTEMATIC_NAME);
        return systematic != null ? systematic.getName() : null;
    }

    @Override
    public void setSystematicName(String name) {
        Collection<Alias> complexAliasesList = getAliases();

        // add new physical properties if not null
        if (name != null){

            CvTerm complexRecommendedName = CvTermUtils.createComplexRecommendedName();
            // first remove old name property if not null
            if (getRecommendedName() != null){
                Collection<Alias> names = AliasUtils.collectAllAliasesHavingType(complexAliasesList, Alias.COMPLEX_SYSTEMATIC_NAME_MI,
                        Alias.COMPLEX_SYSTEMATIC_NAME);
                Alias aliasName = null;
                for (Alias phys : names){
                    if (phys.getName().equalsIgnoreCase(getRecommendedName())){
                        aliasName = phys;
                        break;
                    }
                }
                complexAliasesList.remove(aliasName);
            }
            complexAliasesList.add(new XmlAlias(name, complexRecommendedName));
        }
        // remove all physical properties if the collection is not empty
        else if (!complexAliasesList.isEmpty()) {
            AliasUtils.removeAllAliasesWithType(complexAliasesList, Alias.COMPLEX_SYSTEMATIC_NAME_MI,
                    Alias.COMPLEX_SYSTEMATIC_NAME);
        }
    }

    @Override
    public Xref getPreferredIdentifier() {
        return !getIdentifiers().isEmpty() ? getIdentifiers().iterator().next():null;
    }

    @Override
    public Organism getOrganism() {
        // initialise organism from experiments
        if (this.organism == null && !getExperiments().isEmpty()){
            Iterator<Experiment> expIterator = getExperiments().iterator();
            while (expIterator.hasNext() && this.organism == null){
                Experiment exp = expIterator.next();
                this.organism = exp.getHostOrganism();
            }
        }
        return this.organism;
    }

    @Override
    public void setOrganism(Organism organism) {
        this.organism = organism;
    }

    @Override
    public CvTerm getInteractorType() {
        return this.interactorType;
    }

    @Override
    public void setJAXBXref(InteractionXrefContainer value) {
        super.setJAXBXref(value);
        // set evidence type from complex xrefs
        if (value != null){
            Collection<Xref> ecoRefs = XrefUtils.collectAllXrefsHavingDatabase(value.getXrefs(), Complex.ECO_MI, Complex.ECO);
            if (!ecoRefs.isEmpty()){
                CvTerm eco = new XmlCvTerm(ecoRefs.iterator().next().getId());
                eco.getIdentifiers().addAll(ecoRefs);
                setEvidenceType(eco);
            }
        }
    }

    @Override
    public String toString() {
        return (getSourceLocator() != null ? "Complex : "+getSourceLocator().toString():super.toString());
    }

    @Override
    public List<Alias> getAliases() {
        return super.getAliases();
    }

    @Override
    public Collection<Xref> getIdentifiers() {
        return super.getIdentifiers();
    }

    @Override
    public Collection<Xref> getXrefs() {
        return super.getXrefs();
    }

    @Override
    public Collection<Checksum> getChecksums() {
        return super.getChecksums();
    }

    @Override
    public Collection<Annotation> getAnnotations() {
        return super.getAnnotations();
    }
}
