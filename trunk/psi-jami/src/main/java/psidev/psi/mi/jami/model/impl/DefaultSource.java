package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.Xref;

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
    private Xref bibRef;

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
        this.url = url;
        this.postalAddress = address;
        this.bibRef = bibRef;
    }

    public DefaultSource(String shortName, Xref ontologyId, String url, String address, Xref bibRef) {
        super(shortName, ontologyId);
        this.url = url;
        this.postalAddress = address;
        this.bibRef = bibRef;
    }

    public DefaultSource(String shortName, String fullName, Xref ontologyId, String url, String address, Xref bibRef) {
        super(shortName, fullName, ontologyId);
        this.url = url;
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
        this.url = url;
        this.postalAddress = address;
        this.bibRef = bibRef;
    }

    public DefaultSource(String shortName, String fullName, String miId, String url, String address, Xref bibRef) {
        super(shortName, fullName, miId);
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

    public Xref getBibRef() {
        return this.bibRef;
    }

    public void setBibRef(Xref ref) {
        this.bibRef = ref;
    }
}
