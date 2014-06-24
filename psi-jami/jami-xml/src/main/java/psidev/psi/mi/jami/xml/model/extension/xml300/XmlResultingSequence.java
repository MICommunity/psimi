package psidev.psi.mi.jami.xml.model.extension.xml300;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.ResultingSequence;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.range.ResultingSequenceComparator;
import psidev.psi.mi.jami.xml.model.extension.PsiXmLocator;
import psidev.psi.mi.jami.xml.model.extension.XrefContainer;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Xml implementation of resulting sequence for XML 3.0
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/04/14</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(namespace = "http://psi.hupo.org/mi/mif300")
public class XmlResultingSequence implements ResultingSequence, FileSourceContext, Locatable, Serializable {
    private String originalSequence;
    private String newSequence;
    private XrefContainer xrefContainer;

    @XmlLocation
    @XmlTransient
    private Locator locator;
    private PsiXmLocator sourceLocator;

    public XmlResultingSequence(){
        this.originalSequence = null;
        this.newSequence = null;
    }

    public XmlResultingSequence(String oldSequence, String newSequence){
        this.originalSequence = oldSequence;
        this.newSequence = newSequence;
    }

    public String getNewSequence() {
        return newSequence;
    }

    public String getOriginalSequence() {
        return originalSequence;
    }

    public Collection<Xref> getXrefs() {
        if (xrefContainer == null){
            xrefContainer = new XrefContainer();
        }
        return this.xrefContainer.getXrefs();
    }

    public void setNewSequence(String sequence) {
        this.newSequence = sequence;
    }

    public void setOriginalSequence(String sequence) {
        this.originalSequence = sequence;
    }

    @XmlElement(name = "newSequence", required = true, namespace = "http://psi.hupo.org/mi/mif300")
    public void setJAXBNewSequence(String sequence) {
        setNewSequence(sequence);
    }

    @XmlElement(name = "originalSequence", required = true, namespace = "http://psi.hupo.org/mi/mif300")
    public void setJAXBOriginalSequence(String sequence) {
        setOriginalSequence(sequence);
    }

    @XmlElement(name = "xref", namespace = "http://psi.hupo.org/mi/mif300")
    public void setJAXBXref(XrefContainer value) {
        this.xrefContainer = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof ResultingSequence)){
            return false;
        }

        return ResultingSequenceComparator.areEquals(this, (ResultingSequence) o);
    }

    @Override
    public int hashCode() {
        return ResultingSequenceComparator.hashCode(this);
    }

    @Override
    public String toString() {
        return (originalSequence != null ? "original sequence: "+originalSequence : "") +
                (newSequence != null ? "new sequence: "+newSequence : "");
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

    public void setSourceLocation(PsiXmLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
