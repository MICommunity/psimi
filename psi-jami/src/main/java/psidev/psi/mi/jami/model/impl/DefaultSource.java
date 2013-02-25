package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

/**
 * Default implementation for Source
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
        // add new url if not null
        if (url != null){
            SourceAnnotationList sourceAnnotationList = (SourceAnnotationList) getAnnotations();
            CvTerm complexPhysicalProperties = CvTermFactory.createMICvTerm(Annotation.URL, Annotation.URL_MI);
            // first remove old url if not null
            if (this.url != null){
                sourceAnnotationList.removeOnly(this.url);
            }
            this.url = new DefaultAnnotation(complexPhysicalProperties, url);
            sourceAnnotationList.addOnly(this.url);
        }
        // remove all url if the collection is not empty
        else if (!getAnnotations().isEmpty()) {
            AnnotationUtils.removeAllAnnotationsWithTopic(getAnnotations(), Annotation.URL_MI, Annotation.URL);
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

    private class SourceAnnotationList extends AbstractListHavingPoperties<Annotation> {
        public SourceAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Annotation added) {
            if (url == null && AnnotationUtils.doesAnnotationHaveTopic(added, Annotation.URL_MI, Annotation.URL)){
                url = added;
            }
        }

        @Override
        protected void processRemovedObjectEvent(Annotation removed) {
            if (url != null && url.equals(removed)){
                url = null;
            }
        }

        @Override
        protected void clearProperties() {
            url = null;
        }
    }
}
