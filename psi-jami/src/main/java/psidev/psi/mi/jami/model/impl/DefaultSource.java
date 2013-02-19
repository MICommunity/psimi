package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.Xref;
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

    protected Annotation url;
    protected String postalAddress;
    protected Xref bibRef;

    public DefaultSource(String shortName) {
        super(shortName);
    }

    public DefaultSource(String shortName, Xref ontologyId) {
        super(shortName, ontologyId);
    }

    public DefaultSource(String shortName, String fullName, Xref ontologyId) {
        super(shortName, fullName, ontologyId);
    }

    public DefaultSource(String shortName, String url, String address, Xref bibRef) {
        super(shortName);
        setUrl(url);
        this.postalAddress = address;
        this.bibRef = bibRef;
    }

    public DefaultSource(String shortName, Xref ontologyId, String url, String address, Xref bibRef) {
        super(shortName, ontologyId);
        setUrl(url);
        this.postalAddress = address;
        this.bibRef = bibRef;
    }

    public DefaultSource(String shortName, String fullName, Xref ontologyId, String url, String address, Xref bibRef) {
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

    public DefaultSource(String shortName, String miId, String url, String address, Xref bibRef) {
        super(shortName, miId);
        setUrl(url);
        this.postalAddress = address;
        this.bibRef = bibRef;
    }

    public DefaultSource(String shortName, String fullName, String miId, String url, String address, Xref bibRef) {
        super(shortName, fullName, miId);
        setUrl(url);
        this.postalAddress = address;
        this.bibRef = bibRef;
    }

    @Override
    protected void initializeAnnotations() {
        this.annotations = new SourceAnnotationList();
    }

    public String getUrl() {
        return this.url != null ? this.url.getValue() : null;
    }

    public void setUrl(String url) {
        // add new url if not null
        if (url != null){
            CvTerm complexPhysicalProperties = CvTermFactory.createMICvTerm(Annotation.URL, Annotation.URL_MI);
            // first remove old url if not null
            if (this.url != null){
                annotations.remove(this.url);
            }
            this.url = new DefaultAnnotation(complexPhysicalProperties, url);
            this.annotations.add(this.url);
        }
        // remove all url if the collection is not empty
        else if (!this.annotations.isEmpty()) {
            AnnotationUtils.removeAllAnnotationsWithTopic(annotations, Annotation.URL_MI, Annotation.URL);
            this.url = null;
        }
    }

    public String getPostalAddress() {
        return this.postalAddress;
    }

    public void setPostalAddress(String address) {
        this.postalAddress = address;
    }

    public Xref getBibRef() {
        return this.bibRef;
    }

    public void setBibRef(Xref ref) {
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
