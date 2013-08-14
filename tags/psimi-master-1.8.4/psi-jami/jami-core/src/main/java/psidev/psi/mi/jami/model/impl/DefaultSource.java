package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;

import java.util.Collection;

/**
 * Default implementation for Source
 *
 * Notes: The equals and hashcode methods have been overridden to be consistent with UnambiguousCvTermComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/01/13</pre>
 */

public class DefaultSource extends DefaultCvTerm implements Source {

    private Annotation url;
    private String postalAddress;
    private Publication bibRef;

    public DefaultSource(String shortName) {
        super(shortName);
    }

    public DefaultSource(String shortName, Xref ontologyId) {
        super(shortName, ontologyId);
    }

    public DefaultSource(String shortName, String fullName, Xref ontologyId) {
        super(shortName, fullName, ontologyId);
    }

    public DefaultSource(String shortName, String url, String address, Publication bibRef) {
        super(shortName);
        setUrl(url);
        this.postalAddress = address;
        this.bibRef = bibRef;
    }

    public DefaultSource(String shortName, Xref ontologyId, String url, String address, Publication bibRef) {
        super(shortName, ontologyId);
        setUrl(url);
        this.postalAddress = address;
        this.bibRef = bibRef;
    }

    public DefaultSource(String shortName, String fullName, Xref ontologyId, String url, String address, Publication bibRef) {
        super(shortName, fullName, ontologyId);
        setUrl(url);
        this.postalAddress = address;
        this.bibRef = bibRef;
    }

    public DefaultSource(String shortName, String miId) {
        super(shortName, miId);
    }

    public DefaultSource(String shortName, String fullName, String miId) {
        super(shortName, fullName, miId);
    }

    public DefaultSource(String shortName, String miId, String url, String address, Publication bibRef) {
        super(shortName, miId);
        setUrl(url);
        this.postalAddress = address;
        this.bibRef = bibRef;
    }

    public DefaultSource(String shortName, String fullName, String miId, String url, String address, Publication bibRef) {
        super(shortName, fullName, miId);
        setUrl(url);
        this.postalAddress = address;
        this.bibRef = bibRef;
    }

    @Override
    protected void initialiseAnnotations() {
        initialiseAnnotationsWith(new SourceAnnotationList());
    }

    public String getUrl() {
        return this.url != null ? this.url.getValue() : null;
    }

    public void setUrl(String url) {
        Collection<Annotation> sourceAnnotationList = getAnnotations();

        // add new url if not null
        if (url != null){
            CvTerm complexPhysicalProperties = CvTermUtils.createMICvTerm(Annotation.URL, Annotation.URL_MI);
            // first remove old url if not null
            if (this.url != null){
                sourceAnnotationList.remove(this.url);
            }
            this.url = new DefaultAnnotation(complexPhysicalProperties, url);
            sourceAnnotationList.add(this.url);
        }
        // remove all url if the collection is not empty
        else if (!sourceAnnotationList.isEmpty()) {
            AnnotationUtils.removeAllAnnotationsWithTopic(sourceAnnotationList, Annotation.URL_MI, Annotation.URL);
            this.url = null;
        }
    }

    public String getPostalAddress() {
        return this.postalAddress;
    }

    public void setPostalAddress(String address) {
        this.postalAddress = address;
    }

    public Publication getPublication() {
        return this.bibRef;
    }

    public void setPublication(Publication ref) {
        this.bibRef = ref;
    }

    protected void processAddedAnnotationEvent(Annotation added) {
        if (url == null && AnnotationUtils.doesAnnotationHaveTopic(added, Annotation.URL_MI, Annotation.URL)){
            url = added;
        }
    }

    protected void processRemovedAnnotationEvent(Annotation removed) {
        if (url != null && url.equals(removed)){
            url = null;
        }
    }

    protected void clearPropertiesLinkedToAnnotations() {
        url = null;
    }

    private class SourceAnnotationList extends AbstractListHavingProperties<Annotation> {
        public SourceAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Annotation added) {
            processAddedAnnotationEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(Annotation removed) {
            processRemovedAnnotationEvent(removed);
        }

        @Override
        protected void clearProperties() {
            clearPropertiesLinkedToAnnotations();
        }
    }
}
