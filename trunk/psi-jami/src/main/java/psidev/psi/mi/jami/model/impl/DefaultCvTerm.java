package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractIdentifierList;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Default implementation for CvTerm
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class DefaultCvTerm implements CvTerm, Serializable {

    private String shortName;
    private String fullName;
    private String definition;
    private Collection<Xref> xrefs;
    private Collection<Xref> identifiers;
    private Collection<Annotation> annotations;
    private Collection<Alias> synonyms;
    private Collection<CvTerm> parents;
    private Collection<CvTerm> children;

    private Xref miIdentifier;
    private Xref modIdentifier;


    public DefaultCvTerm(String shortName){
        if (shortName == null){
            throw new IllegalArgumentException("The short name is required and cannot be null");
        }
        this.shortName = shortName;

        this.xrefs = new ArrayList<Xref>();
        this.annotations = new ArrayList<Annotation>();
        this.synonyms = new ArrayList<Alias>();
        this.parents = new ArrayList<CvTerm>();
        this.children = new ArrayList<CvTerm>();
        this.identifiers = new CvTermIdentifierList();
    }

    public DefaultCvTerm(String shortName, String miIdentifier){
        this(shortName);
        setMIIdentifier(miIdentifier);
    }

    public DefaultCvTerm(String shortName, String fullName, String miIdentifier){
        this(shortName, miIdentifier);
        this.fullName = fullName;
    }

    public DefaultCvTerm(String shortName, String fullName, String miIdentifier, String def){
        this(shortName, fullName, miIdentifier);
        this.definition = def;
    }

    public DefaultCvTerm(String shortName, Xref ontologyId){
        this(shortName);
        if (ontologyId != null){
            this.identifiers.add(ontologyId);
        }
    }

    public DefaultCvTerm(String shortName, String fullName, Xref ontologyId){
        this(shortName, ontologyId);
        this.fullName = fullName;
    }

    public DefaultCvTerm(String shortName, String fullName, Xref ontologyId, String def){
        this(shortName, fullName, ontologyId);
        this.definition = def;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String name) {
        if (name == null){
           throw new IllegalArgumentException("The short name cannot be null");
        }
        this.shortName = name;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String name) {
        this.fullName = name;
    }

    public String getDefinition() {
        return this.definition;
    }

    public void setDefinition(String def) {
        this.definition = def;
    }

    public Collection<Xref> getIdentifiers() {
        return identifiers;
    }

    public String getMIIdentifier() {
        return this.miIdentifier != null ? this.miIdentifier.getId() : null;
    }

    public String getMODIdentifier() {
        return this.modIdentifier != null ? this.modIdentifier.getId() : null;
    }

    public void setMIIdentifier(String mi) {
        // add new mi if not null
        if (mi != null){
            CvTerm psiMiDatabase = CvTermFactory.createPsiMiDatabase();
            CvTerm identityQualifier = CvTermFactory.createIdentityQualifier();
            // first remove old psi mi if not null
            if (this.miIdentifier != null){
                identifiers.remove(this.miIdentifier);
            }
            this.miIdentifier = new DefaultXref(psiMiDatabase, mi, identityQualifier);
            this.identifiers.add(this.miIdentifier);
        }
        // remove all mi if the collection is not empty
        else if (!this.identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(identifiers, CvTerm.PSI_MI_ID, CvTerm.PSI_MI);
            this.miIdentifier = null;
        }
    }

    public void setMODIdentifier(String mod) {
        // add new mod if not null
        if (mod != null){
            CvTerm psiModDatabase = CvTermFactory.createPsiModDatabase();
            CvTerm identityQualifier = CvTermFactory.createIdentityQualifier();
            // first remove old psi mod if not null
            if (this.modIdentifier != null){
                identifiers.remove(this.modIdentifier);
            }
            this.modIdentifier = new DefaultXref(psiModDatabase, mod, identityQualifier);
            this.identifiers.add(this.modIdentifier);
        }
        // remove all mod if the collection is not empty
        else if (!this.identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(identifiers, CvTerm.PSI_MOD_ID, CvTerm.PSI_MOD);
            this.modIdentifier = null;
        }
    }

    public Collection<Xref> getXrefs() {
        return this.xrefs;
    }

    public Collection<Annotation> getAnnotations() {
        return this.annotations;
    }

    public Collection<Alias> getSynonyms() {
        return this.synonyms;
    }

    public Collection<CvTerm> getParents() {
        return this.parents;
    }

    public Collection<CvTerm> getChildren() {
        return this.children;
    }

    @Override
    public int hashCode() {
        return UnambiguousCvTermComparator.hashCode(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof CvTerm)){
            return false;
        }

        return UnambiguousCvTermComparator.areEquals(this, (CvTerm) o);
    }

    @Override
    public String toString() {
        return (miIdentifier != null ? miIdentifier.getId() : (modIdentifier != null ? modIdentifier.getId() : "-")) + " ("+shortName+")";
    }

    private class CvTermIdentifierList extends AbstractIdentifierList {
        public CvTermIdentifierList(){
            super();
        }

        @Override
        protected void processAddedXrefEvent(Xref added) {

            // the added identifier is psi-mi and it is not the current mi identifier
            if (miIdentifier != added && XrefUtils.isXrefFromDatabase(added, CvTerm.PSI_MI_ID, CvTerm.PSI_MI)){
                // the current psi-mi identifier is not identity, we may want to set miIdentifier
                if (!XrefUtils.doesXrefHaveQualifier(miIdentifier, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    // the miidentifier is not set, we can set the miidentifier
                    if (miIdentifier == null){
                         miIdentifier = added;
                    }
                    else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                        miIdentifier = added;
                    }
                    // the added xref is secondary object and the current mi is not a secondary object, we reset miidentifier
                    else if (!XrefUtils.doesXrefHaveQualifier(miIdentifier, Xref.SECONDARY_MI, Xref.SECONDARY)
                            && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                        miIdentifier = added;
                    }
                }
            }
            // the added identifier is psi-mod and it is not the current mod identifier
            else if (modIdentifier != added && XrefUtils.isXrefFromDatabase(added, CvTerm.PSI_MOD_ID, CvTerm.PSI_MOD)){
                // the current psi-mod identifier is not identity, we may want to set modIdentifier
                if (!XrefUtils.doesXrefHaveQualifier(modIdentifier, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    // the modIdentifier is not set, we can set the modIdentifier
                    if (modIdentifier == null){
                        modIdentifier = added;
                    }
                    else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                        modIdentifier = added;
                    }
                    // the added xref is secondary object and the current mi is not a secondary object, we reset miidentifier
                    else if (!XrefUtils.doesXrefHaveQualifier(modIdentifier, Xref.SECONDARY_MI, Xref.SECONDARY)
                            && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                        modIdentifier = added;
                    }
                }
            }
        }

        @Override
        protected void processRemovedXrefEvent(Xref removed) {
            // the removed identifier is psi-mi
            if (miIdentifier == removed){
                miIdentifier = XrefUtils.collectFirstIdentifierWithDatabase(this, CvTerm.PSI_MI_ID, CvTerm.PSI_MI);
            }
            // the removed identifier is psi-mod
            else if (modIdentifier == removed){
                modIdentifier = XrefUtils.collectFirstIdentifierWithDatabase(this, CvTerm.PSI_MOD_ID, CvTerm.PSI_MOD);
            }
        }

        @Override
        protected void clearProperties() {
            miIdentifier = null;
            modIdentifier = null;
        }
    }
}
