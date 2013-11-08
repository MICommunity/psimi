package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.xml.extension.Availability;
import psidev.psi.mi.jami.xml.extension.XmlSource;

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
public class XmlEntry {

    private XmlSource source;
    private List<Annotation> annotations;
    private List<Availability> availabilities;
    private boolean hasLoadedFullEntry = false;

    public XmlEntry(){
    }

    public XmlSource getSource() {
        return source;
    }

    public void setSource(XmlSource source) {
        this.source = source;
    }

    public List<Availability> getAvailabilities() {
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
        availabilities = new ArrayList<Availability>();
    }

    protected void initialiseAnnotations() {
        annotations = new ArrayList<Annotation>();
    }

    protected void initialiseAvailabilitiesWith(List<Availability> availabilities) {
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
