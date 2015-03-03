package psidev.psi.mi.jami.xml.model;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.model.extension.PsiXmlLocator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * Abstract class for Psi-XML root node.
 * This contains JAXB bindings that are readonly
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/11/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public abstract class AbstractEntrySet<T extends AbstractEntry> implements Locatable, FileSourceContext {
    private PsiXmlLocator sourceLocator;
    private List<T> entries;

    @XmlLocation
    @XmlTransient
    private Locator locator;

    @Override
    public Locator sourceLocation() {
        return (Locator)getSourceLocator();
    }

    public FileSourceLocator getSourceLocator() {
        if (sourceLocator == null && locator != null){
            sourceLocator = new PsiXmlLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
        }
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else if (sourceLocator instanceof PsiXmlLocator){
            this.sourceLocator = (PsiXmlLocator)sourceLocator;
        }
        else {
            this.sourceLocator = new PsiXmlLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
        }
    }

    public List<T> getEntries() {
        if (entries == null){
            entries = new EntryList();
        }
        return entries;
    }

    @Override
    public String toString() {
        return "EntrySet: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
    }

    private class EntryList extends AbstractListHavingProperties<T> {

        @Override
        protected void processAddedObjectEvent(T added) {
            // resolve references
            XmlEntryContext context = XmlEntryContext.getInstance();
            context.resolveInteractorAndExperimentRefs();
            context.resolveInferredInteractionRefs();
            context.getCurrentEntry().setHasLoadedFullEntry(true);
            context.clear();
        }

        @Override
        protected void processRemovedObjectEvent(T removed) {
            // nothing
        }

        @Override
        protected void clearProperties() {
            // nothing
        }
    }
}
