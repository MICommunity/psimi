package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractXrefTreeSet;
import psidev.psi.mi.jami.utils.comparator.ComparatorUtils;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.xref.AbstractIdentifierComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.io.Serializable;
import java.util.*;

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
            ((CvTermIdentifierList) identifiers).removeAllMiIdentifiers();
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
            ((CvTermIdentifierList) identifiers).removeAllModIdentifiers();
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

    /**
     * Comparator which sorts external identifiers so chebi identifiers are always first
     */
    private class CvTermIdentifierComparator extends AbstractIdentifierComparator {

        @Override
        protected int compareIdentifiers(Xref xref1, Xref xref2, boolean isIdenticalObject1, boolean isIdenticalObject2) {
            int EQUAL = 0;
            int BEFORE = -1;
            int AFTER = 1;

            boolean isFromPsiMi1 = XrefUtils.isXrefFromDatabase(xref1, CvTerm.PSI_MI_ID, CvTerm.PSI_MI);
            boolean isFromPsiMi2 = XrefUtils.isXrefFromDatabase(xref2, CvTerm.PSI_MI_ID, CvTerm.PSI_MI);

            // psi-mi is first
            if (isFromPsiMi1
                    && isFromPsiMi2){
                // identity is first, then secondary
                if (isIdenticalObject1 && isIdenticalObject2){
                    return ComparatorUtils.compareIdentifiersWithDefaultIdentifier(xref1.getId(), xref2.getId(), miIdentifier != null ? miIdentifier.getId() : null);
                }
                else if (isIdenticalObject1){
                    return BEFORE;
                }
                else if (isIdenticalObject2){
                    return AFTER;
                }
                // both identifiers are secondary
                else {
                    return ComparatorUtils.compareIdentifiersWithDefaultIdentifier(xref1.getId(), xref2.getId(), miIdentifier != null ? miIdentifier.getId() : null);
                }
            }
            else if (isFromPsiMi1){
                return BEFORE;
            }
            else if (isFromPsiMi2){
                return AFTER;
            }
            // it is not a psi-mi id, checks for psi-mod
            else {
                boolean isFromPsiMod1 = XrefUtils.isXrefFromDatabase(xref1, CvTerm.PSI_MOD_ID, CvTerm.PSI_MOD);
                boolean isFromPsiMod2 = XrefUtils.isXrefFromDatabase(xref2, CvTerm.PSI_MOD_ID, CvTerm.PSI_MOD);
                // psi-mod is first
                if (isFromPsiMod1
                        && isFromPsiMod2){
                    // identity is first, then secondary
                    if (isIdenticalObject1 && isIdenticalObject2){
                        return ComparatorUtils.compareIdentifiersWithDefaultIdentifier(xref1.getId(), xref2.getId(), modIdentifier != null ? modIdentifier.getId() : null);
                    }
                    else if (isIdenticalObject1){
                        return BEFORE;
                    }
                    else if (isIdenticalObject2){
                        return AFTER;
                    }
                    // both identifiers are secondary
                    else {
                        return ComparatorUtils.compareIdentifiersWithDefaultIdentifier(xref1.getId(), xref2.getId(), modIdentifier != null ? modIdentifier.getId() : null);
                    }
                }
                else if (isFromPsiMod1){
                    return BEFORE;
                }
                else if (isFromPsiMod2){
                    return AFTER;
                }
                // it is not a standard id, compare databases and then id and then qualifier
                else {
                    // compares databases first (cannot use CvTermComparator because have to break the loop)
                    return compareDefaultXref(xref1, xref2);
                }
            }
        }
    }

    private class CvTermIdentifierList extends AbstractXrefTreeSet {
        public CvTermIdentifierList(){
            super(new CvTermIdentifierComparator());
        }

        @Override
        protected void processAddedXrefEvent() {
            // set psi-mi if not done
            if (miIdentifier == null){
                Xref firstMi = first();

                if (XrefUtils.isXrefAnIdentifier(firstMi)
                        && XrefUtils.isXrefFromDatabase(firstMi, CvTerm.PSI_MI_ID, CvTerm.PSI_MI)){
                    miIdentifier = firstMi;
                }
            }
            else if (modIdentifier == null){
                Iterator<Xref> refIterator = iterator();
                Xref firstXref = iterator().next();
                // go through all psi-mi before finding psi-mod
                while (refIterator.hasNext() && XrefUtils.isXrefAnIdentifier(firstXref) &&
                        XrefUtils.isXrefFromDatabase(firstXref, CvTerm.PSI_MI_ID, CvTerm.PSI_MI)){
                    firstXref = refIterator.next();
                }

                if (XrefUtils.isXrefAnIdentifier(firstXref)
                        && XrefUtils.isXrefFromDatabase(firstXref, CvTerm.PSI_MOD_ID, CvTerm.PSI_MOD)){
                    modIdentifier = firstXref;
                }
            }
        }

        @Override
        protected void processRemovedXrefEvent() {
            Iterator<Xref> identifierIterator = iterator();
            Xref firstIdentifier = identifierIterator.next();

            // first identifier is psi-mi
            if (XrefUtils.isXrefAnIdentifier(firstIdentifier)
                    && XrefUtils.isXrefFromDatabase(firstIdentifier, CvTerm.PSI_MI_ID, CvTerm.PSI_MI)){
                miIdentifier = firstIdentifier;
            }
            // process psi-mod
            // go through all psi-mi
            while (identifierIterator.hasNext() && XrefUtils.isXrefAnIdentifier(firstIdentifier)
                    && XrefUtils.isXrefFromDatabase(firstIdentifier, CvTerm.PSI_MI_ID, CvTerm.PSI_MI)){
                firstIdentifier = identifierIterator.next();
            }

            if (XrefUtils.isXrefAnIdentifier(firstIdentifier)
                    && XrefUtils.isXrefFromDatabase(firstIdentifier, CvTerm.PSI_MOD_ID, CvTerm.PSI_MOD)){
                modIdentifier = firstIdentifier;
            }
        }

        @Override
        protected void clearProperties() {
            miIdentifier = null;
            modIdentifier = null;
        }

        public void removeAllMiIdentifiers(){

            Xref first = first();
            while (XrefUtils.isXrefAnIdentifier(first)
                    && XrefUtils.isXrefFromDatabase(first, CvTerm.PSI_MI_ID, CvTerm.PSI_MI)){
                remove(first);
                first = first();
            }
        }

        public void removeAllModIdentifiers(){

            if (!isEmpty()){
                Iterator<Xref> identifierIterator = iterator();
                Xref first = identifierIterator.next();
                // skip the psi-mi
                while (identifierIterator.hasNext()
                        && XrefUtils.isXrefAnIdentifier(first)
                        && XrefUtils.isXrefFromDatabase(first, CvTerm.PSI_MI_ID, CvTerm.PSI_MI)){
                    first = identifierIterator.next();
                }

                while (identifierIterator.hasNext()
                        && XrefUtils.isXrefAnIdentifier(first)
                        && XrefUtils.isXrefFromDatabase(first, CvTerm.PSI_MOD_ID, CvTerm.PSI_MOD)){
                    identifierIterator.remove();
                    first = identifierIterator.next();
                }
            }
        }
    }
}
