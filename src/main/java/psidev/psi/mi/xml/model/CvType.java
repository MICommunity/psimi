/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Reference to an external controlled vocabulary.
 * <p/>
 * <p>Java class for cvType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="cvType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="names" type="{net:sf:psidev:mi}namesType"/>
 *         &lt;element name="xref" type="{net:sf:psidev:mi}xrefType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public abstract class CvType extends DefaultCvTerm implements NamesContainer, XrefContainer {

    private Names names = new CvTermNames();

    private Xref xref;

    private final static String UNSPECIFIED = "unspecified";

    ///////////////////////////
    // Getters and Setters

    protected CvType() {
        super(UNSPECIFIED);
    }

    protected CvType( Names names, Xref xref ) {
        super(names != null ? names.getShortLabel() : UNSPECIFIED);
        setNames( names );

        // set xrefs
        setXref(xref);
    }

    @Override
    protected void initializeXrefs() {
        this.xrefs = new CvTermXrefList();
    }

    @Override
    protected void initializeIdentifiers() {
        this.identifiers = new CvTermIdentifierList();
    }

    @Override
    protected void initializeAnnotations(){
        this.annotations = Collections.EMPTY_LIST;
    }

    @Override
    protected void initializeSynonyms() {
        this.synonyms = new CvTermAliasList();
    }

    /**
     * Check if the optional names is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasNames() {
        return names != null;
    }


    /**
     * Gets the value of the names property.
     *
     * @return possible object is {@link Names }
     */
    public Names getNames() {
        return names;
    }

    /**
     * Sets the value of the names property.
     *
     * @param value allowed object is {@link Names }
     */
    public void setNames( Names value ) {
        if (value != null){
            this.names.setShortLabel(value.getShortLabel());
            this.names.setFullName(value.getFullName());
            this.names.getAliases().addAll(value.getAliases());
        }
        else {
            synonyms.clear();
            this.shortName = UNSPECIFIED;
            this.fullName = null;
            this.names = null;
        }
    }

    /**
     * Gets the value of the xref property.
     *
     * @return possible object is {@link Xref }
     */
    public Xref getXref() {
        return xref;
    }

    /**
     * Sets the value of the xref property.
     *
     * @param value allowed object is {@link Xref }
     */
    public void setXref( Xref value ) {
        if (value != null){
            this.xref = new CvTermXref(value.getPrimaryRef(), value.getSecondaryRef());
        }
        else {
            identifiers.clear();
            xrefs.clear();
            this.xref = null;
        }
    }

    protected void processAddedIdentifier(psidev.psi.mi.jami.model.Xref added){
        // the added identifier is psi-mi and it is not the current mi identifier
        if (miIdentifier != added && XrefUtils.isXrefFromDatabase(added, CvTerm.PSI_MI_MI, CvTerm.PSI_MI)){
            // the current psi-mi identifier is not identity, we may want to set miIdentifier
            if (!XrefUtils.doesXrefHaveQualifier(miIdentifier, psidev.psi.mi.jami.model.Xref.IDENTITY_MI, psidev.psi.mi.jami.model.Xref.IDENTITY)){
                // the miidentifier is not set, we can set the miidentifier
                if (miIdentifier == null){
                    miIdentifier = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, psidev.psi.mi.jami.model.Xref.IDENTITY_MI, psidev.psi.mi.jami.model.Xref.IDENTITY)){
                    miIdentifier = added;
                }
                // the added xref is secondary object and the current mi is not a secondary object, we reset miidentifier
                else if (!XrefUtils.doesXrefHaveQualifier(miIdentifier, psidev.psi.mi.jami.model.Xref.SECONDARY_MI, psidev.psi.mi.jami.model.Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, psidev.psi.mi.jami.model.Xref.SECONDARY_MI, psidev.psi.mi.jami.model.Xref.SECONDARY)){
                    miIdentifier = added;
                }
            }
        }
        // the added identifier is psi-mod and it is not the current mod identifier
        else if (modIdentifier != added && XrefUtils.isXrefFromDatabase(added, CvTerm.PSI_MOD_MI, CvTerm.PSI_MOD)){
            // the current psi-mod identifier is not identity, we may want to set modIdentifier
            if (!XrefUtils.doesXrefHaveQualifier(modIdentifier, psidev.psi.mi.jami.model.Xref.IDENTITY_MI, psidev.psi.mi.jami.model.Xref.IDENTITY)){
                // the modIdentifier is not set, we can set the modIdentifier
                if (modIdentifier == null){
                    modIdentifier = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, psidev.psi.mi.jami.model.Xref.IDENTITY_MI, psidev.psi.mi.jami.model.Xref.IDENTITY)){
                    modIdentifier = added;
                }
                // the added xref is secondary object and the current mi is not a secondary object, we reset miidentifier
                else if (!XrefUtils.doesXrefHaveQualifier(modIdentifier, psidev.psi.mi.jami.model.Xref.SECONDARY_MI, psidev.psi.mi.jami.model.Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, psidev.psi.mi.jami.model.Xref.SECONDARY_MI, psidev.psi.mi.jami.model.Xref.SECONDARY)){
                    modIdentifier = added;
                }
            }
        }
    }

    protected void processRemovedIdentifier(psidev.psi.mi.jami.model.Xref removed){
        // the removed identifier is psi-mi
        if (miIdentifier != null && miIdentifier.equals(removed)){
            miIdentifier = XrefUtils.collectFirstIdentifierWithDatabase(identifiers, CvTerm.PSI_MI_MI, CvTerm.PSI_MI);
        }
        // the removed identifier is psi-mod
        else if (modIdentifier != null && modIdentifier.equals(removed)){
            modIdentifier = XrefUtils.collectFirstIdentifierWithDatabase(identifiers, CvTerm.PSI_MOD_MI, CvTerm.PSI_MOD);
        }
    }

    protected void clearPropertiesLinkedToIdentifiers(){
        miIdentifier = null;
        modIdentifier = null;
    }

    //////////////////////////
    // Object override


    @Override
    public void setShortName(String name) {
        if (names != null){
            names.setShortLabel(name);
        }
        else if (name != null) {
            names = new CvTermNames();
            names.setShortLabel(name);
        }
        else {
            names = new CvTermNames();
            names.setShortLabel(UNSPECIFIED);
        }
    }

    @Override
    public void setFullName(String name) {
        if (names != null){
            if (names.getShortLabel().equals(UNSPECIFIED)){
                names.setShortLabel(name);
            }
            names.setFullName(name);
        }
        else if (name != null) {
            names = new CvTermNames();
            names.setShortLabel(name);
            names.setFullName(name);
        }
        else {
            names = new CvTermNames();
            names.setShortLabel(UNSPECIFIED);
            names.setFullName(name);
        }
    }

    @Override
    public void setMIIdentifier(String mi) {
        // add new mi if not null
        if (mi != null){
            // first remove old psi mi if not null
            if (this.miIdentifier != null){
                identifiers.remove(this.miIdentifier);
            }
            this.miIdentifier = new DbReference(CvTerm.PSI_MI, CvTerm.PSI_MI_MI, mi, psidev.psi.mi.jami.model.Xref.IDENTITY, psidev.psi.mi.jami.model.Xref.IDENTITY_MI);
            this.identifiers.add(this.miIdentifier);
        }
        // remove all mi if the collection is not empty
        else if (!this.identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(identifiers, CvTerm.PSI_MI_MI, CvTerm.PSI_MI);
            this.miIdentifier = null;
        }
    }

    @Override
    public void setMODIdentifier(String mod) {
        // add new mod if not null
        if (mod != null){
            // first remove old psi mod if not null
            if (this.modIdentifier != null){
                identifiers.remove(this.modIdentifier);
            }
            this.modIdentifier = new DbReference(CvTerm.PSI_MOD, CvTerm.PSI_MOD_MI, mod, psidev.psi.mi.jami.model.Xref.IDENTITY, psidev.psi.mi.jami.model.Xref.IDENTITY_MI);
            this.identifiers.add(this.modIdentifier);
        }
        // remove all mod if the collection is not empty
        else if (!this.identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(identifiers, CvTerm.PSI_MOD_MI, CvTerm.PSI_MOD);
            this.modIdentifier = null;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "CvType" );
        sb.append( "{names=" ).append( names );
        sb.append( ", xref=" ).append( xref );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        final CvType cvType = ( CvType ) o;

        if ( names != null ? !names.equals( cvType.names ) : cvType.names != null ) {
            return false;
        }
        if ( xref != null ? !xref.equals( cvType.xref ) : cvType.xref != null ) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = ( names != null ? names.hashCode() : 0 );
        result = 29 * result + ( xref != null ? xref.hashCode() : 0 );
        return result;
    }

    protected class CvTermXref extends Xref{

        protected boolean isPrimaryAnIdentity = false;
        protected SecondaryRefList extendedSecondaryRefList = new SecondaryRefList();

        public CvTermXref() {
            super();
        }

        public CvTermXref( DbReference primaryRef ) {
            super(primaryRef);
        }

        public CvTermXref( DbReference primaryRef, Collection<DbReference> secondaryRef ) {
            super( primaryRef );

            if (secondaryRef != null && !secondaryRef.isEmpty()){
                secondaryRef.addAll(secondaryRef);
            }
        }

        public void setPrimaryRef( DbReference value ) {
            if (getPrimaryRef() == null){
                super.setPrimaryRef(value);
                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((CvTermIdentifierList)identifiers).addOnly(value);
                        processAddedIdentifier(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((CvTermXrefList)xrefs).addOnly(value);
                    }
                }
            }
            else if (isPrimaryAnIdentity){
                ((CvTermIdentifierList)identifiers).removeOnly(getPrimaryRef());
                processRemovedIdentifier(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((CvTermIdentifierList)identifiers).addOnly(value);
                        processAddedIdentifier(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((CvTermXrefList)xrefs).addOnly(value);
                    }
                }
            }
            else {
                ((CvTermXrefList)xrefs).removeOnly(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((CvTermIdentifierList)identifiers).addOnly(value);
                        processAddedIdentifier(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((CvTermXrefList)xrefs).addOnly(value);
                    }
                }
            }
        }

        public void setPrimaryRefOnly( DbReference value ) {
            if (value == null && !extendedSecondaryRefList.isEmpty()){
                super.setPrimaryRef(extendedSecondaryRefList.get(0));
                extendedSecondaryRefList.removeOnly(0);
            }
            else {
                super.setPrimaryRef(value);
                if (XrefUtils.isXrefAnIdentifier(value)){
                    isPrimaryAnIdentity = true;
                }
            }
        }

        public boolean hasSecondaryRef() {
            return ( extendedSecondaryRefList != null ) && ( !extendedSecondaryRefList.isEmpty() );
        }

        public Collection<DbReference> getSecondaryRef() {
            return this.extendedSecondaryRefList;
        }

        public Collection<DbReference> getAllDbReferences() {
            Collection<DbReference> refs = new ArrayList<DbReference>();
            if ( getPrimaryRef() != null ) {
                refs.add( getPrimaryRef() );
            }
            if ( extendedSecondaryRefList != null ) {
                refs.addAll( extendedSecondaryRefList );
            }
            return refs;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append( "Xref" );
            sb.append( "{primaryRef=" ).append( getPrimaryRef() );
            sb.append( ", secondaryRef=" ).append( extendedSecondaryRefList );
            sb.append( '}' );
            return sb.toString();
        }

        protected class SecondaryRefList extends AbstractListHavingPoperties<DbReference>{

            @Override
            protected void processAddedObjectEvent(DbReference added) {
                if (XrefUtils.isXrefAnIdentifier(added)){
                    ((CvTermIdentifierList)identifiers).addOnly(added);
                    processAddedIdentifier(added);
                }
                else {
                    ((CvTermXrefList)xrefs).addOnly(added);
                }
            }

            @Override
            protected void processRemovedObjectEvent(DbReference removed) {
                if (XrefUtils.isXrefAnIdentifier(removed)){
                    ((CvTermIdentifierList)identifiers).removeOnly(removed);
                    processRemovedIdentifier(removed);
                }
                else {
                    ((CvTermXrefList)xrefs).removeOnly(removed);
                }
            }

            @Override
            protected void clearProperties() {
                Collection<DbReference> primary = Arrays.asList(getPrimaryRef());
                ((CvTermIdentifierList)identifiers).retainAllOnly(primary);
                clearPropertiesLinkedToIdentifiers();
                ((CvTermXrefList)xrefs).retainAllOnly(primary);
            }
        }
    }

    private class CvTermIdentifierList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Xref> {
        public CvTermIdentifierList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Xref added) {

            if (xref != null){
                CvTermXref reference = (CvTermXref) xref;

                if (xref.getPrimaryRef() == null){
                    if (added instanceof DbReference){
                        reference.setPrimaryRefOnly((DbReference) added);
                        processAddedIdentifier(added);
                    }
                    else {
                        DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                                added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                        reference.setPrimaryRefOnly(fixedRef);
                        processAddedIdentifier(fixedRef);
                    }
                }
                else {
                    if (added instanceof DbReference){
                        reference.extendedSecondaryRefList.addOnly((DbReference) added);
                        processAddedIdentifier(added);
                    }
                    else {
                        DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                                added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                        reference.extendedSecondaryRefList.addOnly(fixedRef);
                        processAddedIdentifier(fixedRef);
                    }
                }
            }
            else {
                if (added instanceof DbReference){
                    xref = new CvTermXref();
                    ((CvTermXref) xref).setPrimaryRefOnly((DbReference) added);
                    processAddedIdentifier(added);
                }
                else {
                    DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                            added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                    xref = new CvTermXref();
                    ((CvTermXref) xref).setPrimaryRefOnly(fixedRef);
                    processAddedIdentifier(fixedRef);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Xref removed) {

            if (xref != null){
                CvTermXref reference = (CvTermXref) xref;

                if (reference.getPrimaryRef() == removed){
                    reference.setPrimaryRefOnly(null);
                    processRemovedIdentifier(removed);
                }
                else {
                    if (removed instanceof DbReference){
                        reference.extendedSecondaryRefList.removeOnly((DbReference) removed);
                        processRemovedIdentifier(removed);

                    }
                    else {
                        DbReference fixedRef = new DbReference(removed.getDatabase().getShortName(), removed.getDatabase().getMIIdentifier(), removed.getId(),
                                removed.getQualifier() != null ? removed.getQualifier().getShortName() : null, removed.getQualifier() != null ? removed.getQualifier().getMIIdentifier() : null);

                        reference.extendedSecondaryRefList.removeOnly(fixedRef);
                        processRemovedIdentifier(removed);
                    }
                }
            }
        }

        @Override
        protected void clearProperties() {
            clearPropertiesLinkedToIdentifiers();

            if (xref != null){
                CvTermXref reference = (CvTermXref) xref;
                reference.extendedSecondaryRefList.retainAllOnly(xrefs);

                if (reference.isPrimaryAnIdentity){
                    reference.setPrimaryRefOnly(null);
                }
            }
        }
    }

    private class CvTermXrefList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Xref> {
        public CvTermXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Xref added) {

            if (xref != null){
                CvTermXref reference = (CvTermXref) xref;

                if (xref.getPrimaryRef() == null){
                    if (added instanceof DbReference){
                        reference.setPrimaryRefOnly((DbReference) added);
                    }
                    else {
                        DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                                added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                        reference.setPrimaryRefOnly(fixedRef);
                    }
                }
                else {
                    if (added instanceof DbReference){
                        reference.extendedSecondaryRefList.addOnly((DbReference) added);
                    }
                    else {
                        DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                                added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                        reference.extendedSecondaryRefList.addOnly(fixedRef);
                    }
                }
            }
            else {
                if (added instanceof DbReference){
                    xref = new CvTermXref();
                    ((CvTermXref) xref).setPrimaryRefOnly((DbReference) added);
                }
                else {
                    DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                            added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                    xref = new CvTermXref(fixedRef);
                    ((CvTermXref) xref).setPrimaryRefOnly(fixedRef);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Xref removed) {
            if (xref != null){
                CvTermXref reference = (CvTermXref) xref;

                if (reference.getPrimaryRef() == removed){
                    reference.setPrimaryRefOnly(null);
                }
                else {
                    if (removed instanceof DbReference){
                        reference.extendedSecondaryRefList.removeOnly((DbReference) removed);

                    }
                    else {
                        DbReference fixedRef = new DbReference(removed.getDatabase().getShortName(), removed.getDatabase().getMIIdentifier(), removed.getId(),
                                removed.getQualifier() != null ? removed.getQualifier().getShortName() : null, removed.getQualifier() != null ? removed.getQualifier().getMIIdentifier() : null);

                        reference.extendedSecondaryRefList.removeOnly(fixedRef);
                    }
                }
            }
        }

        @Override
        protected void clearProperties() {

            if (xref != null){
                CvTermXref reference = (CvTermXref) xref;
                reference.extendedSecondaryRefList.retainAllOnly(identifiers);

                if (!reference.isPrimaryAnIdentity){
                    reference.setPrimaryRefOnly(null);
                }
            }
        }
    }

    protected class CvTermNames extends Names{

        protected AliasList extendedAliases = new AliasList();

        public String getShortLabel() {
            return shortName;
        }

        public boolean hasShortLabel() {
            return shortName != null;
        }

        public void setShortLabel( String value ) {
            if (value != null){
                shortName = value;
            }
            else {
                shortName = UNSPECIFIED;
            }
        }

        public String getFullName() {
            return fullName;
        }

        public boolean hasFullName() {
            return fullName != null;
        }

        public void setFullName( String value ) {
            fullName = value;
        }

        public Collection<Alias> getAliases() {
            return this.extendedAliases;
        }

        public boolean hasAliases() {
            return ( extendedAliases != null ) && ( !extendedAliases.isEmpty() );
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append( "Names" );
            sb.append( "{shortLabel='" ).append( shortName ).append( '\'' );
            sb.append( ", fullName='" ).append( fullName ).append( '\'' );
            sb.append( ", aliases=" ).append( extendedAliases );
            sb.append( '}' );
            return sb.toString();
        }

        @Override
        public boolean equals( Object o ) {
            if ( this == o ) return true;
            if ( o == null || getClass() != o.getClass() ) return false;

            Names names = ( Names ) o;

            if ( extendedAliases != null ? !extendedAliases.equals( names.getAliases() ) : names.getAliases() != null ) return false;
            if ( fullName != null ? !fullName.equals( names.getFullName() ) : names.getFullName() != null ) return false;
            if ( shortName != null ? !shortName.equals( names.getShortLabel() ) : names.getShortLabel() != null ) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            result = ( shortName != null ? shortName.hashCode() : 0 );
            result = 31 * result + ( fullName != null ? fullName.hashCode() : 0 );
            result = 31 * result + ( extendedAliases != null ? extendedAliases.hashCode() : 0 );
            return result;
        }

        protected class AliasList extends AbstractListHavingPoperties<Alias>{

            @Override
            protected void processAddedObjectEvent(Alias added) {
                ((CvTermAliasList) synonyms).addOnly(added);
            }

            @Override
            protected void processRemovedObjectEvent(Alias removed) {
                ((CvTermAliasList)synonyms).removeOnly(removed);
            }

            @Override
            protected void clearProperties() {
                ((CvTermAliasList)synonyms).clearOnly();
            }
        }
    }

    private class CvTermAliasList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Alias> {
        public CvTermAliasList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Alias added) {

            if (names != null){
                CvTermNames name = (CvTermNames) names;

                if (added instanceof Alias){
                    ((CvTermNames.AliasList) name.getAliases()).addOnly((Alias) added);
                }
                else {
                    Alias fixedAlias = new Alias(added.getName(), added.getType() != null ? added.getType().getShortName() : null, added.getType() != null ? added.getType().getMIIdentifier() : null);

                    ((CvTermNames.AliasList) name.getAliases()).addOnly(fixedAlias);
                }
            }
            else {
                if (added instanceof Alias){
                    names = new CvTermNames();
                    names.setShortLabel(UNSPECIFIED);
                    ((CvTermNames.AliasList) names.getAliases()).addOnly((Alias)added);
                }
                else {
                    Alias fixedAlias = new Alias(added.getName(), added.getType() != null ? added.getType().getShortName() : null, added.getType() != null ? added.getType().getMIIdentifier() : null);

                    names = new CvTermNames();
                    names.setShortLabel(UNSPECIFIED);
                    ((CvTermNames.AliasList) names.getAliases()).addOnly((Alias)fixedAlias);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Alias removed) {

            if (names != null){
                CvTermNames name = (CvTermNames) names;

                if (removed instanceof Alias){
                    name.extendedAliases.removeOnly((Alias) removed);

                }
                else {
                    Alias fixedAlias = new Alias(removed.getName(), removed.getType() != null ? removed.getType().getShortName() : null, removed.getType() != null ? removed.getType().getMIIdentifier() : null);

                    name.extendedAliases.removeOnly(fixedAlias);
                }
            }
        }

        @Override
        protected void clearProperties() {

            if (names != null){
                CvTermNames name = (CvTermNames) names;
                name.extendedAliases.clearOnly();
            }
        }
    }
}