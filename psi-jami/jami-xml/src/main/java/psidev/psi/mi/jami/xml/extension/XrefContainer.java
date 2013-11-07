package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Xref;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Xref container in XML implementation
 * The JAXB binding is designed to be read-only and is not designed for writing
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({
        CvTermXrefContainer.class, PublicationXrefContainer.class, InteractorXrefContainer.class, FeatureXrefContainer.class,
        ExperimentXrefContainer.class, InteractionXrefContainer.class
})
public class XrefContainer implements FileSourceContext, Locatable{

    private List<Xref> xrefs;
    private PsiXmLocator sourceLocator;
    @XmlLocation
    @XmlTransient
    private Locator locator;
    private JAXBSecondaryXrefList jaxbSecondaryRefs;

    /**
     * Sets the value of the primaryRef property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlXref }
     *
     */
    @XmlElement(name = "primaryRef",required = true, type = XmlXref.class)
    public void setJAXBPrimaryRef(Xref value) {
        if (value != null){
            processAddedPrimaryRef(value);
        }
    }

    /**
     * Gets the value of the secondaryReves property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the secondaryReves property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSecondaryReves().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XmlXref }
     *
     *
     */
    @XmlElement(name = "secondaryRef", type = XmlXref.class)
    public List<Xref> getJAXBSecondaryRefs() {
        if (this.jaxbSecondaryRefs == null) {
            initialiseSecondaryRefs();
        }
        return this.jaxbSecondaryRefs;
    }

    public List<Xref> getXrefs() {
        if (xrefs == null) {
            initialiseXrefs();
        }
        return xrefs;
    }

    public boolean isEmpty(){
        if (getXrefs().isEmpty()){
            return true;
        }
        return false;
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

    @Override
    public String toString() {
        return "Xref: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }

    protected void initialiseXrefs(){
        this.xrefs = new ArrayList<Xref>();
    }

    protected void initialiseXrefsWith(List<Xref> list){
        if (list == null){
             this.xrefs = Collections.EMPTY_LIST;
        }
        else{
            this.xrefs = list;
        }
    }

    protected void initialiseSecondaryRefs(){
        this.jaxbSecondaryRefs = new JAXBSecondaryXrefList();
    }

    protected void initialiseSecondaryResWith(JAXBSecondaryXrefList list){
        if (list == null){
            this.jaxbSecondaryRefs = new JAXBSecondaryXrefList();
        }
        else{
            this.jaxbSecondaryRefs = list;
        }
    }

    protected void processAddedPrimaryRef(Xref added) {
        if (xrefs == null){
            initialiseXrefs();
        }
        xrefs.add(0, added);
    }

    ///////////////////////////// classes
    //////////////////////////////// private class
    protected class JAXBSecondaryXrefList extends ArrayList<Xref>{

        protected JAXBSecondaryXrefList() {
        }

        @Override
        public boolean add(Xref xref) {
            if (xref == null){
                return false;
            }
            return addXref(null, xref);
        }

        @Override
        public boolean addAll(Collection<? extends Xref> c) {
            if (c == null){
                return false;
            }
            boolean added = false;

            for (Xref a : c){
                if (add(a)){
                    added = true;
                }
            }
            return added;
        }

        @Override
        public void add(int index, Xref element) {
            addXref(index, element);
        }

        @Override
        public boolean addAll(int index, Collection<? extends Xref> c) {
            int newIndex = index;
            if (c == null){
                return false;
            }
            boolean add = false;
            for (Xref a : c){
                if (addXref(newIndex, a)){
                    newIndex++;
                    add = true;
                }
            }
            return add;
        }

        protected boolean addXref(Integer index, Xref xref) {
            if (index == null){
                return getXrefs().add(xref);
            }
            getXrefs().add(index, xref);
            return true;
        }
    }
}
