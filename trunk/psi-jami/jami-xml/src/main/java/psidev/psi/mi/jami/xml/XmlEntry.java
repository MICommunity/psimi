package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.xml.extension.Availability;

import java.util.ArrayList;
import java.util.Collection;

/**
 * An XML entry is associated with a set of interactions, experiments, source and attributes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/10/13</pre>
 */

public class XmlEntry {

    private Source source;
    private Collection<Annotation> annotations;
    private Collection<Availability> availabilities;
    private boolean hasLoadedFullEntry = false;

    public XmlEntry(){
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Collection<Availability> getAvailabilities() {
        if (availabilities == null){
            availabilities = new ArrayList<Availability>();
        }
        return availabilities;
    }

    public Collection<Annotation> getAnnotations() {
        if (annotations == null){
            annotations = new ArrayList<Annotation>();
        }
        return annotations;
    }

    public boolean hasLoadedFullEntry(){
        return this.hasLoadedFullEntry;
    }

    public void setHasLoadedFullEntry(boolean hasLoadedFullEntry) {
        this.hasLoadedFullEntry = hasLoadedFullEntry;
    }
}
