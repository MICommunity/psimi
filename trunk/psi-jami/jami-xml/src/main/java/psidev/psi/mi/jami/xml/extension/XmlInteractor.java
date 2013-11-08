package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.*;
import java.util.*;

/**
 * The Xml implementation of Interactor
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/07/13</pre>
 */
@XmlRootElement(name = "interactor", namespace = "http://psi.hupo.org/mi/mif")
@XmlAccessorType(XmlAccessType.NONE)
public class XmlInteractor implements Interactor, FileSourceContext, Locatable, ExtendedPsi25Interactor{

    private psidev.psi.mi.jami.model.Organism organism;
    private CvTerm interactorType;
    private PsiXmLocator sourceLocator;
    NamesContainer namesContainer;
    InteractorXrefContainer xrefContainer;
    private String xmlSequence;
    private int id;
    @XmlLocation
    @XmlTransient
    private Locator locator;
    private JAXBAttributeWrapper jaxbAttributeWrapper;

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
        createDefaultInteractorType();
    }

    public XmlInteractor(String name, String fullName){
        this(name);
        setFullName(fullName);
    }

    public XmlInteractor(String name, Organism organism){
        this(name);
        this.organism = organism;
        createDefaultInteractorType();
    }

    public XmlInteractor(String name, String fullName, Organism organism){
        this(name, fullName);
        this.organism = organism;
    }

    public XmlInteractor(String name, Xref uniqueId){
        this(name);
        getIdentifiers().add(uniqueId);
        createDefaultInteractorType();
    }

    public XmlInteractor(String name, String fullName, Xref uniqueId){
        this(name, fullName);
        getIdentifiers().add(uniqueId);
        createDefaultInteractorType();
    }

    public XmlInteractor(String name, Organism organism, Xref uniqueId){
        this(name, organism);
        getIdentifiers().add(uniqueId);
        createDefaultInteractorType();
    }

    public XmlInteractor(String name, String fullName, Organism organism, Xref uniqueId){
        this(name, fullName, organism);
        getIdentifiers().add(uniqueId);
        createDefaultInteractorType();
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

    public String getShortName() {
        return getNamesContainer().getShortLabel();
    }

    public void setShortName(String name) {
        if (name == null || (name != null && name.length() == 0)){
            throw new IllegalArgumentException("The short name cannot be null or empty.");
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
        if (xrefContainer == null){
            initialiseXrefContainer();
        }
        return xrefContainer.getIdentifiers();
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
        if (this.jaxbAttributeWrapper == null){
            initialiseAnnotationWrapper();
        }
        return this.jaxbAttributeWrapper.checksums;
    }

    public Collection<Xref> getXrefs() {
        if (xrefContainer == null){
            initialiseXrefContainer();
        }
        return xrefContainer.getXrefs();
    }

    public Collection<Annotation> getAnnotations() {
        if (this.jaxbAttributeWrapper == null){
            initialiseAnnotationWrapper();
        }
        return this.jaxbAttributeWrapper.annotations;
    }

    public Collection<Alias> getAliases() {
        return getNamesContainer().getAliases();
    }

    /**
     * Sets the value of the names property.
     *
     * @param value
     *     allowed object is
     *     {@link NamesContainer }
     *
     */
    @XmlElement(name = "names", required = true)
    public void setJAXBNames(NamesContainer value) {
        this.namesContainer = value;
        if (this.namesContainer != null){
            if (this.namesContainer.isEmpty()){
                this.namesContainer.setShortLabel(PsiXmlUtils.UNSPECIFIED);
                PsiXmlParserListener listener = XmlEntryContext.getInstance().getListener();
                if (listener != null){
                    listener.onMissingInteractorName(this, this);
                }
            }
            else if (this.namesContainer.getShortLabel() == null){
                this.namesContainer.setShortLabel(this.namesContainer.getFullName() != null ? this.namesContainer.getFullName() : this.namesContainer.getAliases().iterator().next().getName());
            }
        }
        else{
            PsiXmlParserListener listener = XmlEntryContext.getInstance().getListener();
            if (listener != null){
                listener.onMissingInteractorName(this, this );
            }
        }
    }

    /**
     * Sets the value of the xrefContainer property.
     *
     * @param value
     *     allowed object is
     *     {@link InteractorXrefContainer }
     *
     */
    @XmlElement(name = "xref")
    public void setJAXBXref(InteractorXrefContainer value) {
        this.xrefContainer = value;
    }

    @XmlElement(name = "interactorType", required = true, type = XmlCvTerm.class)
    public void setJAXBInteractorType(CvTerm interactorType) {
        this.interactorType = interactorType;
    }

    @XmlElement(name = "organism", type = XmlOrganism.class)
    public void setJAXBOrganism(Organism organism) {
        this.organism = organism;
    }

    /**
     * Sets the value of the sequence property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    @XmlElement(name = "sequence")
    public void setSequence(String value) {
        this.xmlSequence = value;
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
        XmlEntryContext.getInstance().registerObject(this.id, this);
        if (getSourceLocator() != null){
            sourceLocator.setObjectId(this.id);
        }
    }

    @XmlAttribute(name = "id", required = true)
    public void setJAXBId(int value) {
        setId(value);
    }

    /**
     * Gets the value of the attributeList property.
     *
     * @return
     *     possible object is
     *     {@link XmlAnnotation }
     *
     */
    @XmlElement(name="attributeList")
    public void setJAXBAttributeWrapper(JAXBAttributeWrapper wrapper) {
        this.jaxbAttributeWrapper = wrapper;
    }

    protected void createDefaultInteractorType() {
        this.interactorType = new XmlCvTerm(Interactor.UNKNOWN_INTERACTOR, Interactor.UNKNOWN_INTERACTOR_MI);
    }

    public String getSequence() {
        return xmlSequence;
    }

    @Override
    public String toString() {
        return "Interactor: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
    }

    @Override
    public Locator sourceLocation() {
        return (Locator)getSourceLocator();
    }

    public FileSourceLocator getSourceLocator() {
        if (sourceLocator == null && locator != null){
            sourceLocator = new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), id);
        }
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

    public void setSourceLocation(PsiXmLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    protected void initialiseAnnotationWrapper(){
        this.jaxbAttributeWrapper = new JAXBAttributeWrapper();
    }

    protected NamesContainer getNamesContainer() {
        if (namesContainer == null){
            namesContainer = new NamesContainer();
            namesContainer.setShortLabel(PsiXmlUtils.UNSPECIFIED);
        }
        return namesContainer;
    }

    ////////////////////////////////////////////////////////////////// classes

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="interactorAttributeWrapper")
    public static class JAXBAttributeWrapper implements Locatable, FileSourceContext{
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<Annotation> annotations;
        private List<Checksum> checksums;
        private JAXBAttributeList jaxbAttributeList;

        public JAXBAttributeWrapper(){
            initialiseAnnotations();
            initialiseChecksums();
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

        protected void initialiseChecksums(){
            checksums = new ArrayList<Checksum>();
        }

        protected void initialiseAnnotationsWith(List<Annotation> annotations){
            if (annotations == null){
                this.annotations = Collections.EMPTY_LIST;
            }
            else {
                this.annotations = annotations;
            }
        }

        protected void initialiseChecksumsWith(List<Checksum> checksums){
            if (checksums == null){
                this.checksums = Collections.EMPTY_LIST;
            }
            else {
                this.checksums = checksums;
            }
        }

        @XmlElement(type=XmlAnnotation.class, name="attribute", required = true)
        public List<Annotation> getJAXBAttributes() {
            if (this.jaxbAttributeList == null){
                this.jaxbAttributeList = new JAXBAttributeList();
            }
            return this.jaxbAttributeList;
        }

        /**
         * The attribute list used by JAXB to populate interactor annotations
         */
        private class JAXBAttributeList extends ArrayList<Annotation>{

            public JAXBAttributeList(){
                super();
            }

            @Override
            public boolean add(Annotation a) {
                return processAnnotations(null, a);
            }

            @Override
            public boolean addAll(Collection<? extends Annotation> c) {
                if (c == null){
                    return false;
                }
                boolean added = false;

                for (Annotation a : c){
                    if (add(a)){
                        added = true;
                    }
                }
                return added;
            }

            @Override
            public void add(int index, Annotation element) {
                addToSpecificIndex(index, element);
            }

            @Override
            public boolean addAll(int index, Collection<? extends Annotation> c) {
                int newIndex = index;
                if (c == null){
                    return false;
                }
                boolean add = false;
                for (Annotation a : c){
                    if (addToSpecificIndex(newIndex, a)){
                        newIndex++;
                        add = true;
                    }
                }
                return add;
            }

            private boolean addToSpecificIndex(int index, Annotation a) {
                return processAnnotations(index, a);
            }

            private boolean processAnnotations(Integer index, Annotation a) {
                if (a == null){
                    return false;
                }
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
                    checksum.setSourceLocator((PsiXmLocator)((FileSourceContext)a).getSourceLocator());
                    checksums.add(checksum);
                    return false;
                }
                else {
                    return addAnnotation(index, a);
                }
            }

            private boolean addAnnotation(Integer index, Annotation a) {
                if (index == null){
                    return annotations.add(a);
                }
                annotations.add(index, a);
                return true;
            }

            @Override
            public String toString() {
                return "Interactor Attribute List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
            }
        }
    }
}
