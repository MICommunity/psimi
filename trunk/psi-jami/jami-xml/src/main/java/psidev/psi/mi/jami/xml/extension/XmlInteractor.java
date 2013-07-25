package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.internal.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * The Xml implementation of Interactor
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "interactor", propOrder = {
        "names",
        "xref",
        "interactorType",
        "organism",
        "sequence",
        "attributes"
})
@XmlSeeAlso({
        XmlBioactiveEntity.class, XmlGene.class, XmlInteractorSet.class, XmlMolecule.class,
        XmlPolymer.class, XmlNucleciAcid.class, XmlProtein.class
})
public class XmlInteractor implements Interactor, FileSourceContext, Serializable{

    private Collection<Checksum> checksums;
    private psidev.psi.mi.jami.model.Organism organism;
    private CvTerm interactorType;

    private PsiXmLocator sourceLocator;

    NamesContainer namesContainer;
    InteractorXrefContainer xrefContainer;
    private String xmlSequence;
    private int id;
    private Collection<Annotation> annotations;

    public XmlInteractor(){

    }

    public XmlInteractor(String name, CvTerm type){
        if (name == null || (name != null && name.length() == 0)){
            throw new IllegalArgumentException("The short name cannot be null or empty.");
        }
        setShortName(name);
        if (type == null){
            createDefaultInteractorType();
        }
        else {
            this.interactorType = type;
        }
    }

    public XmlInteractor(String name, String fullName, CvTerm type){
        this(name, type);
        setFullName(fullName);
    }

    public XmlInteractor(String name, CvTerm type, Organism organism){
        this(name, type);
        this.organism = organism;
    }

    public XmlInteractor(String name, String fullName, CvTerm type, Organism organism){
        this(name, fullName, type);
        this.organism = organism;
    }

    public XmlInteractor(String name, CvTerm type, Xref uniqueId){
        this(name, type);
        getIdentifiers().add(uniqueId);
    }

    public XmlInteractor(String name, String fullName, CvTerm type, Xref uniqueId){
        this(name, fullName, type);
        getIdentifiers().add(uniqueId);
    }

    public XmlInteractor(String name, CvTerm type, Organism organism, Xref uniqueId){
        this(name, type, organism);
        getIdentifiers().add(uniqueId);
    }

    public XmlInteractor(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId){
        this(name, fullName, type, organism);
        getIdentifiers().add(uniqueId);
    }

    public XmlInteractor(String name){
        if (name == null || (name != null && name.length() == 0)){
            throw new IllegalArgumentException("The short name cannot be null or empty.");
        }
        setShortName(name);
        this.interactorType = CvTermUtils.createUnknownInteractorType();
    }

    public XmlInteractor(String name, String fullName){
        this(name);
        setFullName(fullName);
    }

    public XmlInteractor(String name, Organism organism){
        this(name);
        this.organism = organism;
        this.interactorType = CvTermUtils.createUnknownInteractorType();
    }

    public XmlInteractor(String name, String fullName, Organism organism){
        this(name, fullName);
        this.organism = organism;
    }

    public XmlInteractor(String name, Xref uniqueId){
        this(name);
        getIdentifiers().add(uniqueId);
        this.interactorType = CvTermUtils.createUnknownInteractorType();
    }

    public XmlInteractor(String name, String fullName, Xref uniqueId){
        this(name, fullName);
        getIdentifiers().add(uniqueId);
        this.interactorType = CvTermUtils.createUnknownInteractorType();
    }

    public XmlInteractor(String name, Organism organism, Xref uniqueId){
        this(name, organism);
        getIdentifiers().add(uniqueId);
        this.interactorType = CvTermUtils.createUnknownInteractorType();
    }

    public XmlInteractor(String name, String fullName, Organism organism, Xref uniqueId){
        this(name, fullName, organism);
        getIdentifiers().add(uniqueId);
        this.interactorType = CvTermUtils.createUnknownInteractorType();
    }

    /**
     * Gets the value of the names property.
     *
     * @return
     *     possible object is
     *     {@link NamesContainer }
     *
     */
    @XmlElement(name = "names", required = true)
    public NamesContainer getNames() {
        if (namesContainer == null){
            initialiseNamesContainer();
            namesContainer.setShortLabel(PsiXmlUtils.UNSPECIFIED);
        }
        return namesContainer;
    }

    /**
     * Sets the value of the names property.
     *
     * @param value
     *     allowed object is
     *     {@link NamesContainer }
     *
     */
    public void setNames(NamesContainer value) {
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

    /**
     * Gets the value of the xrefContainer property.
     *
     * @return
     *     possible object is
     *     {@link InteractorXrefContainer }
     *
     */
    @XmlElement(name = "xref")
    public InteractorXrefContainer getXref() {
        if (xrefContainer != null){
            if (xrefContainer.isEmpty()){
                return null;
            }
        }
        return xrefContainer;
    }

    /**
     * Sets the value of the xrefContainer property.
     *
     * @param value
     *     allowed object is
     *     {@link InteractorXrefContainer }
     *
     */
    public void setXref(InteractorXrefContainer value) {
        this.xrefContainer = value;
    }

    /**
     * Gets the value of the interactorType property.
     *
     * @return
     *     possible object is
     *     {@link XmlCvTerm }
     *
     */
    @XmlElement(name = "interactorType", required = true, type = XmlCvTerm.class)
    public CvTerm getInteractorType() {
        if (this.interactorType == null){
            createDefaultInteractorType();
        }

        return this.interactorType;
    }

    protected void createDefaultInteractorType() {
        this.interactorType = new XmlCvTerm(Interactor.UNKNOWN_INTERACTOR, Interactor.UNKNOWN_INTERACTOR_MI);
    }

    public void setInteractorType(CvTerm interactorType) {
        this.interactorType = interactorType;
    }

    @XmlElement(name = "organism", type = XmlOrganism.class)
    public psidev.psi.mi.jami.model.Organism getOrganism() {
        return this.organism;
    }

    public void setOrganism(psidev.psi.mi.jami.model.Organism organism) {
        this.organism = organism;
    }


    /**
     * Gets the value of the sequence property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    @XmlElement(name = "sequence")
    public String getSequence() {
        return xmlSequence;
    }

    /**
     * Sets the value of the sequence property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSequence(String value) {
        this.xmlSequence = value;
    }

    /**
     * Gets the value of the id property.
     *
     */
    @XmlAttribute(name = "id", required = true)
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     */
    public void setId(int value) {
        this.id = value;
        if (sourceLocator != null){
            sourceLocator.setObjectId(this.id);
        }
    }

    /**
     * Gets the value of the attributeList property.
     *
     * @return
     *     possible object is
     *     {@link XmlAnnotation }
     *
     */
    @XmlElementWrapper(name="attributeList")
    @XmlElement(name="attribute", required = true)
    @XmlElementRefs({ @XmlElementRef(type=XmlAnnotation.class)})
    public ArrayList<Annotation> getAttributes() {
        if (getAnnotations().isEmpty() && getChecksums().isEmpty()){
            return null;
        }

        ArrayList<Annotation> annots = new ArrayList<Annotation>(getAnnotations());
        if (!getChecksums().isEmpty()){
            for (Checksum c : getChecksums()){
                annots.add(new XmlAnnotation(c.getMethod(), c.getValue()));
            }
        }
        return annots;
    }

    /**
     * Sets the value of the attributeList property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlAnnotation }
     *
     */
    public void setAttributes(ArrayList<Annotation> value) {
        if (value != null && !value.isEmpty()){
            for (Annotation a : value){
                if (AnnotationUtils.doesAnnotationHaveTopic(a, Checksum.CHECKSUM_MI, Checksum.CHECKUM)
                        || AnnotationUtils.doesAnnotationHaveTopic(a, Checksum.SMILE_MI, Checksum.SMILE)
                        || AnnotationUtils.doesAnnotationHaveTopic(a, null, Checksum.SMILE_SHORT)
                        || AnnotationUtils.doesAnnotationHaveTopic(a, Checksum.INCHI_MI, Checksum.INCHI)
                        || AnnotationUtils.doesAnnotationHaveTopic(a, null, Checksum.INCHI_SHORT)
                        || AnnotationUtils.doesAnnotationHaveTopic(a, Checksum.INCHI_KEY_MI, Checksum.INCHI_KEY)
                        || AnnotationUtils.doesAnnotationHaveTopic(a, Checksum.STANDARD_INCHI_KEY_MI, Checksum.STANDARD_INCHI_KEY)){
                    XmlChecksum checksum = new XmlChecksum(a.getTopic(), a.getValue() != null ? a.getValue() : PsiXmlUtils.UNSPECIFIED);
                    checksum.setSourceLocator(((FileSourceContext)a).getSourceLocator());
                    getChecksums().add(checksum);
                }
                else {
                    getAnnotations().add(a);
                }
            }
        }
    }

    protected void initialiseAnnotations(){
        this.annotations = new ArrayList<Annotation>();
    }

    protected void initialiseChecksums(){
        this.checksums = new ArrayList<Checksum>();
    }

    protected void initialiseAnnotationsWith(Collection<Annotation> annotations){
        if (annotations == null){
            this.annotations = Collections.EMPTY_LIST;
        }
        else {
            this.annotations = annotations;
        }
    }

    protected void initialiseChecksumsWith(Collection<Checksum> checksums){
        if (checksums == null){
            this.checksums = Collections.EMPTY_LIST;
        }
        else {
            this.checksums = checksums;
        }
    }

    @XmlTransient
    public String getShortName() {
        return getNames().getShortLabel();
    }

    public void setShortName(String name) {
        if (name == null || (name != null && name.length() == 0)){
            throw new IllegalArgumentException("The short name cannot be null or empty.");
        }
        getNames().setShortLabel(name);
    }

    @XmlTransient
    public String getFullName() {
        return getNames().getFullName();
    }

    public void setFullName(String name) {
        getNames().setFullName(name);
    }

    @XmlTransient
    public Collection<Xref> getIdentifiers() {
        if (xrefContainer == null){
            initialiseXrefContainer();
        }
        return getXref().getAllIdentifiers();
    }

    protected void initialiseXrefContainer() {
        xrefContainer = new InteractorXrefContainer();
    }

    protected void initialiseNamesContainer() {
        namesContainer = new NamesContainer();
    }

    /**
     *
     * @return the first identifier in the list of identifiers or null if the list is empty
     */
    @XmlTransient
    public Xref getPreferredIdentifier() {
        return !getIdentifiers().isEmpty() ? getIdentifiers().iterator().next() : null;
    }

    @XmlTransient
    public Collection<Checksum> getChecksums() {
        if (checksums == null){
            initialiseChecksums();
        }
        return this.checksums;
    }

    @XmlTransient
    public Collection<Xref> getXrefs() {
        if (xrefContainer == null){
            initialiseXrefContainer();
        }
        return xrefContainer.getAllXrefs();
    }

    @XmlTransient
    public Collection<Annotation> getAnnotations() {
        if (annotations == null){
            initialiseAnnotations();
        }
        return this.annotations;
    }

    @XmlTransient
    public Collection<Alias> getAliases() {
        return getNames().getAliases();
    }

    @Override
    public String toString() {
        return getShortName() + (organism != null ? ", " + organism.toString() : "") + (interactorType != null ? ", " + interactorType.toString() : "")  ;
    }

    @XmlLocation
    @XmlTransient
    public Locator getSaxLocator() {
        return sourceLocator;
    }

    public void setSaxLocator(Locator sourceLocator) {
        this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getColumnNumber(), id);
    }

    @XmlTransient
    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), id);
    }
}
