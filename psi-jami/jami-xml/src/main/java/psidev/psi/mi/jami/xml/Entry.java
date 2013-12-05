package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.xml.extension.AbstractAvailability;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Source;

import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An XML entry is associated with a set of interactions, experiments, source and attributes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/10/13</pre>
 */
@XmlTransient
public class Entry {

    private ExtendedPsi25Source source;
    private List<Annotation> annotations;
    private List<AbstractAvailability> availabilities;
    private boolean hasLoadedFullEntry = false;

    public Entry(){
    }

    public ExtendedPsi25Source getSource() {
        return source;
    }

    public void setSource(ExtendedPsi25Source source) {
        this.source = source;
    }

    public List<AbstractAvailability> getAvailabilities() {
        if (availabilities == null){
            initialiseAvailabilities();
        }
        return availabilities;
    }

    public List<Annotation> getAnnotations() {
        if (annotations == null){
            initialiseAnnotations();
        }
        return annotations;
    }

    public boolean hasLoadedFullEntry(){
        return this.hasLoadedFullEntry;
    }

    public void setHasLoadedFullEntry(boolean hasLoadedFullEntry) {
        this.hasLoadedFullEntry = hasLoadedFullEntry;
    }

    protected void initialiseAvailabilities() {
        availabilities = new ArrayList<AbstractAvailability>();
    }

    protected void initialiseAnnotations() {
        annotations = new ArrayList<Annotation>();
    }

    protected void initialiseAvailabilitiesWith(List<AbstractAvailability> availabilities) {
        if (availabilities == null){
            this.availabilities = Collections.EMPTY_LIST;
        }
        else{
           this.availabilities = availabilities;
        }
    }

    protected void initialiseAnnotationsWith(List<Annotation> annotations) {
        if (annotations == null){
            this.annotations = Collections.EMPTY_LIST;
        }
        else{
            this.annotations = annotations;
        }
    }
}
