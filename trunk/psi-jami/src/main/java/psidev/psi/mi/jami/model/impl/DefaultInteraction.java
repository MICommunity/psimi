package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

import java.io.Serializable;
import java.util.*;

/**
 * Default implementation for interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultInteraction<P extends Participant> implements Interaction<P>, Serializable {

    protected String shortName;
    protected Set<Xref> xrefs;
    protected Set<Annotation> annotations;
    protected Collection<P> participants;
    protected Source source;
    protected boolean isNegative;
    protected Date updatedDate;
    protected Set<Confidence> confidences;
    protected CvTerm type;

    public DefaultInteraction(){
        this.xrefs = new HashSet<Xref>();
        this.annotations = new HashSet<Annotation>();
        this.participants = new ArrayList<P>();
        this.confidences = new HashSet<Confidence>();
    }

    public DefaultInteraction(String shortName){
        this();
        this.shortName = shortName;
    }

    public DefaultInteraction(String shortName, Source source){
        this(shortName);
        this.source = source;
    }

    public DefaultInteraction(String shortName, CvTerm type){
        this(shortName);
        this.type = type;
    }

    public DefaultInteraction(String shortName, Source source, CvTerm type){
        this(shortName, source);
        this.type = type;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String name) {
        this.shortName = name;
    }

    public Set<Xref> getXrefs() {
        return this.xrefs;
    }

    public Set<Annotation> getAnnotations() {
        return this.annotations;
    }

    public Collection<P> getParticipants() {
        return this.participants;
    }

    public Source getSource() {
        return this.source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public boolean isNegative() {
        return this.isNegative;
    }

    public void setNegative(boolean negative) {
        this.isNegative = negative;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updated) {
        this.updatedDate = updated;
    }

    public Set<Confidence> getConfidences() {
        return this.confidences;
    }

    public CvTerm getType() {
        return this.type;
    }

    public void setType(CvTerm term) {
       this.type = term;
    }

    @Override
    public int hashCode() {
        return super.hashCode();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String toString() {
        return super.toString();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
