package psidev.psi.mi.jami.xml;

import com.sun.xml.bind.Locatable;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;
import psidev.psi.mi.jami.xml.extension.PsiXmLocator;

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
@XmlTransient
public abstract class AbstractEntrySet<T extends AbstractEntry> implements Locatable, FileSourceContext {
    private PsiXmLocator sourceLocator;
    private List<T> entries;

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
