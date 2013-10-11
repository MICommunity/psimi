package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.internal.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * The Xml implementation of Interactor
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "interactor", propOrder = {
        "JAXBNames",
        "JAXBXref",
        "JAXBInteractorType",
        "JAXBOrganism",
        "JAXBSequence",
        "JAXBAttributes"
})
@XmlSeeAlso({
        XmlBioactiveEntity.class, XmlGene.class, XmlInteractorSet.class, XmlMolecule.class,
        XmlPolymer.class, XmlNucleciAcid.class, XmlProtein.class, XmlComplex.class, XmlModelledInteractionWrapper.class,
        XmlInteractionEvidenceWrapper.class
})
public class XmlInteractor implements Interactor, FileSourceContext{

    private Collection<Checksum> checksums;
    private psidev.psi.mi.jami.model.Organism organism;
    private CvTerm interactorType;

    private PsiXmLocator sourceLocator;

    NamesContainer namesContainer;
    InteractorXrefContainer xrefContainer;
    private String xmlSequence;
    private int id;
    private Collection<Annotation> annotations;

    private Map<Integer, Object> mapOfReferencedObjects;

    public XmlInteractor(){
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
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
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public XmlInteractor(String name, String fullName, CvTerm type){
        this(name, type);
        setFullName(fullName);
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public XmlInteractor(String name, CvTerm type, Organism organism){
        this(name, type);
        this.organism = organism;
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public XmlInteractor(String name, String fullName, CvTerm type, Organism organism){
        this(name, fullName, type);
        this.organism = organism;
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public XmlInteractor(String name, CvTerm type, Xref uniqueId){
        this(name, type);
        getIdentifiers().add(uniqueId);
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public XmlInteractor(String name, String fullName, CvTerm type, Xref uniqueId){
        this(name, fullName, type);
        getIdentifiers().add(uniqueId);
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public XmlInteractor(String name, CvTerm type, Organism organism, Xref uniqueId){
        this(name, type, organism);
        getIdentifiers().add(uniqueId);
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public XmlInteractor(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId){
        this(name, fullName, type, organism);
        getIdentifiers().add(uniqueId);
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public XmlInteractor(String name){
        if (name == null || (name != null && name.length() == 0)){
            throw new IllegalArgumentException("The short name cannot be null or empty.");
        }
        setShortName(name);
        createDefaultInteractorType();
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public XmlInteractor(String name, String fullName){
        this(name);
        setFullName(fullName);
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public XmlInteractor(String name, Organism organism){
        this(name);
        this.organism = organism;
        createDefaultInteractorType();
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public XmlInteractor(String name, String fullName, Organism organism){
        this(name, fullName);
        this.organism = organism;
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public XmlInteractor(String name, Xref uniqueId){
        this(name);
        getIdentifiers().add(uniqueId);
        createDefaultInteractorType();
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public XmlInteractor(String name, String fullName, Xref uniqueId){
        this(name, fullName);
        getIdentifiers().add(uniqueId);
        createDefaultInteractorType();
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public XmlInteractor(String name, Organism organism, Xref uniqueId){
        this(name, organism);
        getIdentifiers().add(uniqueId);
        createDefaultInteractorType();
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    public XmlInteractor(String name, String fullName, Organism organism, Xref uniqueId){
        this(name, fullName, organism);
        getIdentifiers().add(uniqueId);
        createDefaultInteractorType();
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
    }

    /**
     * Gets the value of the interactorType property.
     *
     * @return
     *     possible object is
     *     {@link XmlCvTerm }
     *
     */
    public CvTerm getInteractorType() {
        if (this.interactorType == null){
            createDefaultInteractorType();
        }

        return this.interactorType;
    }

    public void setInteractorType(CvTerm interactorType) {
        this.interactorType = interactorType;
    }

    public psidev.psi.mi.jami.model.Organism getOrganism() {
        return this.organism;
    }

    public void setOrganism(psidev.psi.mi.jami.model.Organism organism) {
        this.organism = organism;
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

    public String getShortName() {
        return getJAXBNames().getJAXBShortLabel();
    }

    public void setShortName(String name) {
        if (name == null || (name != null && name.length() == 0)){
            throw new IllegalArgumentException("The short name cannot be null or empty.");
        }
        getJAXBNames().setJAXBShortLabel(name);
    }

    public String getFullName() {
        return getJAXBNames().getJAXBFullName();
    }

    public void setFullName(String name) {
        getJAXBNames().setJAXBFullName(name);
    }

    public Collection<Xref> getIdentifiers() {
        if (xrefContainer == null){
            initialiseXrefContainer();
        }
        return getJAXBXref().getAllIdentifiers();
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
    public Xref getPreferredIdentifier() {
        return !getIdentifiers().isEmpty() ? getIdentifiers().iterator().next() : null;
    }

    public Collection<Checksum> getChecksums() {
        if (checksums == null){
            initialiseChecksums();
        }
        return this.checksums;
    }

    public Collection<Xref> getXrefs() {
        if (xrefContainer == null){
            initialiseXrefContainer();
        }
        return xrefContainer.getAllXrefs();
    }

    public Collection<Annotation> getAnnotations() {
        if (annotations == null){
            initialiseAnnotations();
        }
        return this.annotations;
    }

    public Collection<Alias> getAliases() {
        return getJAXBNames().getJAXBAliases();
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
    public NamesContainer getJAXBNames() {
        if (namesContainer == null){
            initialiseNamesContainer();
            namesContainer.setJAXBShortLabel(PsiXmlUtils.UNSPECIFIED);
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
    public void setJAXBNames(NamesContainer value) {
        if (value == null){
            namesContainer = new NamesContainer();
            namesContainer.setJAXBShortLabel(PsiXmlUtils.UNSPECIFIED);
        }
        else {
            this.namesContainer = value;
            if (this.namesContainer.getJAXBShortLabel() == null){
                namesContainer.setJAXBShortLabel(PsiXmlUtils.UNSPECIFIED);
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
    public InteractorXrefContainer getJAXBXref() {
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
    public void setJAXBXref(InteractorXrefContainer value) {
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
    public CvTerm getJAXBInteractorType() {
        return getInteractorType();
    }

    public void setJAXBInteractorType(XmlCvTerm interactorType) {
        this.interactorType = interactorType;
    }

    @XmlElement(name = "organism", type = XmlOrganism.class)
    public psidev.psi.mi.jami.model.Organism getJAXBOrganism() {
        return this.organism;
    }

    public void setJAXBOrganism(XmlOrganism organism) {
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
    public String getJAXBSequence() {
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
    public void setJAXBSequence(String value) {
        this.xmlSequence = value;
    }

    /**
     * Gets the value of the id property.
     *
     */
    @XmlAttribute(name = "id", required = true)
    public int getJAXBId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     */
    public void setJAXBId(int value) {
        this.id = value;
        this.mapOfReferencedObjects.put(this.id, this);
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
    public ArrayList<Annotation> getJAXBAttributes() {
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
    public void setJAXBAttributes(ArrayList<XmlAnnotation> value) {
        getAnnotations().clear();
        if (value != null && !value.isEmpty()){
            for (Annotation a : value){
                if (AnnotationUtils.doesAnnotationHaveTopic(a, Checksum.CHECKSUM_MI, Checksum.CHECKUM)
                        || AnnotationUtils.doesAnnotationHaveTopic(a, Checksum.SMILE_MI, Checksum.SMILE)
                        || AnnotationUtils.doesAnnotationHaveTopic(a, null, Checksum.SMILE_SHORT)
                        || AnnotationUtils.doesAnnotationHaveTopic(a, Checksum.INCHI_MI, Checksum.INCHI)
                        || AnnotationUtils.doesAnnotationHaveTopic(a, null, Checksum.INCHI_SHORT)
                        || AnnotationUtils.doesAnnotationHaveTopic(a, Checksum.INCHI_KEY_MI, Checksum.INCHI_KEY)
                        || AnnotationUtils.doesAnnotationHaveTopic(a, Checksum.STANDARD_INCHI_KEY_MI, Checksum.STANDARD_INCHI_KEY)
                        || AnnotationUtils.doesAnnotationHaveTopic(a, null, Checksum.ROGID)
                        || AnnotationUtils.doesAnnotationHaveTopic(a, null, Checksum.RIGID)){
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

    protected void createDefaultInteractorType() {
        this.interactorType = new XmlCvTerm(Interactor.UNKNOWN_INTERACTOR, Interactor.UNKNOWN_INTERACTOR_MI);
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

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), id);
    }
}
