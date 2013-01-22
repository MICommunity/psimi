package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.ExternalIdentifier;
import psidev.psi.mi.jami.model.Source;

/**
 * Default implementation for Source
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/01/13</pre>
 */

public class DefaultSource extends DefaultCvTerm implements Source {

    private String url;
    private String postalAddress;
    private ExternalIdentifier bibRef;

    public DefaultSource(String shortName) {
        super(shortName);
    }

    public DefaultSource(String shortName, ExternalIdentifier ontologyId) {
        super(shortName, ontologyId);
    }

    public DefaultSource(String shortName, String fullName, ExternalIdentifier ontologyId) {
        super(shortName, fullName, ontologyId);
    }

    public DefaultSource(String shortName, String fullName, ExternalIdentifier ontologyId, String def) {
        super(shortName, fullName, ontologyId, def);
    }

    public DefaultSource(String shortName, String url, String address, ExternalIdentifier bibRef) {
        super(shortName);
        this.url = url;
        this.postalAddress = address;
        this.bibRef = bibRef;
    }

    public DefaultSource(String shortName, ExternalIdentifier ontologyId, String url, String address, ExternalIdentifier bibRef) {
        super(shortName, ontologyId);
        this.url = url;
        this.postalAddress = address;
        this.bibRef = bibRef;
    }

    public DefaultSource(String shortName, String fullName, ExternalIdentifier ontologyId, String url, String address, ExternalIdentifier bibRef) {
        super(shortName, fullName, ontologyId);
        this.url = url;
        this.postalAddress = address;
        this.bibRef = bibRef;
    }

    public DefaultSource(String shortName, String fullName, ExternalIdentifier ontologyId, String def, String url, String address, ExternalIdentifier bibRef) {
        super(shortName, fullName, ontologyId, def);
        this.url = url;
        this.postalAddress = address;
        this.bibRef = bibRef;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPostalAddress() {
        return this.postalAddress;
    }

    public void setPostalAddress(String address) {
        this.postalAddress = address;
    }

    public ExternalIdentifier getBibRef() {
        return this.bibRef;
    }

    public void setBibRef(ExternalIdentifier ref) {
        this.bibRef = ref;
    }
}
