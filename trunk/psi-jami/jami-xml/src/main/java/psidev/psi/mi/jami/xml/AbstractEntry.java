package psidev.psi.mi.jami.xml;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;
import psidev.psi.mi.jami.xml.extension.*;
import psidev.psi.mi.jami.xml.extension.factory.XmlInteractorFactory;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract class for Entry
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/11/13</pre>
 */
@XmlTransient
public abstract class AbstractEntry<T extends Interaction> extends Entry implements Locatable, FileSourceContext {
    private PsiXmLocator sourceLocator;
    private JAXBInteractorsWrapper interactorsWrapper;
    private JAXBInteractionsWrapper<T> interactionsWrapper;
    private JAXBAvailabilitiesWrapper availabilitiesWrapper;
    private JAXBAnnotationsWrapper annotationsWrapper;

    public AbstractEntry() {
        super();
        Xml25EntryContext.getInstance().setCurrentSource(this);
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

    public List<Interactor> getInteractors(){
        return this.interactorsWrapper != null ? this.interactorsWrapper.interactors : Collections.EMPTY_LIST;
    }

    public List<T> getInteractions(){
        return this.interactionsWrapper != null ? this.interactionsWrapper.interactions : Collections.EMPTY_LIST;
    }

    @Override
    public boolean hasLoadedFullEntry() {
        return true;
    }

    @Override
    public void setHasLoadedFullEntry(boolean hasLoadedFullEntry) {
        if (!hasLoadedFullEntry){
            throw new UnsupportedOperationException("An abstract entry is always fully loaded because full JAXB bindings, no pull parsing");
        }
    }

    @Override
    public String toString() {
        return "Entry : "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
    }

    protected void setInteractorsWrapper(JAXBInteractorsWrapper wrapper){
        this.interactorsWrapper = wrapper;
    }

    protected void setInteractionsWrapper(JAXBInteractionsWrapper wrapper){
        this.interactionsWrapper = wrapper;
        if (this.interactionsWrapper != null){
            for (T interaction : this.interactionsWrapper.interactions){
                ((ExtendedPsi25Interaction)interaction).setEntry(this);
            }
        }
    }

    protected void setAvailabilitiesWrapper(JAXBAvailabilitiesWrapper wrapper){
        this.availabilitiesWrapper = wrapper;
    }

    protected void setAnnotationsWrapper(JAXBAnnotationsWrapper wrapper){
        this.annotationsWrapper = wrapper;
    }

    @Override
    protected void initialiseAvailabilities() {
        super.initialiseAvailabilitiesWith(this.availabilitiesWrapper != null ? this.availabilitiesWrapper.availabilities : null);
    }

    @Override
    protected void initialiseAnnotations() {
        super.initialiseAnnotationsWith(this.annotationsWrapper != null ? this.annotationsWrapper.annotations : null);
    }

    //////////////////////////////////////////////////// Inner classes
    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="interactorsWrapper")
    public static class JAXBInteractorsWrapper implements Locatable, FileSourceContext {
        private List<Interactor> interactors;
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;

        public JAXBInteractorsWrapper(){
            initialiseInteractors();
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

        protected void initialiseInteractors(){
            interactors = new InteractorList();
        }

        @XmlElement(type=XmlInteractor.class, name="interactor", required = true)
        public List<Interactor> getJAXBInteractors() {
            return interactors;
        }

        @Override
        public String toString() {
            return "Entry interactor List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }

        private class InteractorList extends AbstractListHavingProperties<Interactor> {
            XmlInteractorFactory interactorFactory = new XmlInteractorFactory();

            @Override
            protected void processAddedObjectEvent(Interactor added) {
                // register new interactor instance according to type
                this.interactorFactory.createInteractorFromXmlInteractorInstance((XmlInteractor)added);
            }

            @Override
            protected void processRemovedObjectEvent(Interactor removed) {
                // nothing
            }

            @Override
            protected void clearProperties() {
                // nothing
            }
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="entryAvailabilitiesWrapper")
    public static class JAXBAvailabilitiesWrapper implements Locatable, FileSourceContext {
        private List<Availability> availabilities;
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;

        public JAXBAvailabilitiesWrapper(){
            initialiseAvailabilities();
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

        protected void initialiseAvailabilities(){
            availabilities = new ArrayList<Availability>();
        }

        @XmlElement(type=Availability.class, name="availability", required = true)
        public List<Availability> getJAXBAvailabilities() {
            return availabilities;
        }

        @Override
        public String toString() {
            return "Entry availability List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="entryAnnotationsWrapper")
    public static class JAXBAnnotationsWrapper implements Locatable, FileSourceContext {
        private List<Annotation> annotations;
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;

        public JAXBAnnotationsWrapper(){
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

        @XmlElement(type=XmlAnnotation.class, name="attribute", required = true)
        public List<Annotation> getJAXBAttributes() {
            return annotations;
        }

        @Override
        public String toString() {
            return "Entry attribute List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="interactionsWrapper")
    public static class JAXBInteractionsWrapper<T extends Interaction> implements Locatable, FileSourceContext {
        private List<T> interactions;
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;

        public JAXBInteractionsWrapper(){
            initialiseInteractions();
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

        protected void initialiseInteractions(){
            interactions = new ArrayList<T>();
        }

        public List<T> getJAXBInteractions() {
            return interactions;
        }

        @Override
        public String toString() {
            return "Entry interaction List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }
}
