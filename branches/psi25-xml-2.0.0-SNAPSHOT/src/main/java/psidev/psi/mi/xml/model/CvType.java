/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


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
    protected void initialiseXrefs() {
        initialiseXrefsWith(new CvTermXrefList());
    }

    @Override
    protected void initialiseIdentifiers() {
        initialiseIdentifiersWith(new CvTermIdentifierList());
    }

    @Override
    protected void initialiseAnnotations(){
        initialiseAnnotationsWith(Collections.EMPTY_LIST);
    }

    @Override
    protected void initialiseSynonyms() {
        initialiseSynonymsWith(new CvTermAliasList());
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
            if (this.names == null){
                this.names = new CvTermNames();
            }
            else {
                getSynonyms().clear();
            }
            super.setShortName(value.getShortLabel() != null ? value.getShortLabel() : UNSPECIFIED);
            super.setFullName(value.getFullName());
            getSynonyms().addAll(value.getAliases());
        }
        else if (this.names != null) {
            getSynonyms().clear();
            super.setShortName(UNSPECIFIED);
            super.setFullName(null);
            this.names = null;
        }
        else {
            this.names = new CvTermNames();
            super.setShortName(UNSPECIFIED);
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
            if (this.xref != null){
                getIdentifiers().clear();
                getXrefs().clear();
            }
            this.xref = new CvTermXref(value.getPrimaryRef(), value.getSecondaryRef());
        }
        else if (this.xref != null){
            getIdentifiers().clear();
            getXrefs().clear();
            this.xref = null;
        }
    }

    //////////////////////////
    // Object override


    @Override
    public void setShortName(String name) {
        if (names != null){
            super.setShortName(name != null ? name : UNSPECIFIED);
        }
        else if (name != null) {
            names = new CvTermNames();
            super.setShortName(name);
        }
        else {
            names = new CvTermNames();
            super.setShortName(UNSPECIFIED);
        }
    }

    @Override
    public void setFullName(String name) {
        if (names != null){
            if (getShortName().equals(UNSPECIFIED)){
                super.setShortName(name != null ? name : UNSPECIFIED);
            }
            super.setFullName(name);
        }
        else if (name != null) {
            names = new CvTermNames();
            super.setShortName(name);
            super.setFullName(name);
        }
        else {
            names = new CvTermNames();
            super.setShortName(UNSPECIFIED);
            super.setFullName(name);
        }
    }

    protected String getCvTermFullName(){
        return super.getFullName();
    }

    protected void setShortNameOnly(String name) {
        super.setShortName(name != null ? name : UNSPECIFIED);
    }

    protected void setFullNameOnly(String name) {
        super.setFullName(name);
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

    private class CvTermXref extends Xref{

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
                extendedSecondaryRefList.addAll(secondaryRef);
            }
        }

        public void setPrimaryRef( DbReference value ) {
            if (getPrimaryRef() == null){
                super.setPrimaryRef(value);
                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((CvTermIdentifierList)getIdentifiers()).addOnly(value);
                        processAddedIdentifierEvent(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((CvTermXrefList)getXrefs()).addOnly(value);
                    }
                }
            }
            else if (isPrimaryAnIdentity){
                ((CvTermIdentifierList)getIdentifiers()).removeOnly(getPrimaryRef());
                processRemovedIdentifierEvent(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((CvTermIdentifierList)getIdentifiers()).addOnly(value);
                        processAddedIdentifierEvent(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((CvTermXrefList)getXrefs()).addOnly(value);
                    }
                }
            }
            else {
                ((CvTermXrefList)getXrefs()).removeOnly(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((CvTermIdentifierList)getIdentifiers()).addOnly(value);
                        processAddedIdentifierEvent(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((CvTermXrefList)getXrefs()).addOnly(value);
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
                    ((CvTermIdentifierList)getIdentifiers()).addOnly(added);
                    processAddedIdentifierEvent(added);
                }
                else {
                    ((CvTermXrefList)getXrefs()).addOnly(added);
                }
            }

            @Override
            protected void processRemovedObjectEvent(DbReference removed) {
                if (XrefUtils.isXrefAnIdentifier(removed)){
                    ((CvTermIdentifierList)getIdentifiers()).removeOnly(removed);
                    processRemovedIdentifierEvent(removed);
                }
                else {
                    ((CvTermXrefList)getXrefs()).removeOnly(removed);
                }
            }

            @Override
            protected void clearProperties() {
                Collection<DbReference> primary = Arrays.asList(getPrimaryRef());
                ((CvTermIdentifierList)getIdentifiers()).retainAllOnly(primary);
                clearPropertiesLinkedToIdentifiers();
                ((CvTermXrefList)getXrefs()).retainAllOnly(primary);
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
                        processAddedIdentifierEvent(added);
                    }
                    else {
                        DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                                added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                        reference.setPrimaryRefOnly(fixedRef);
                        processAddedIdentifierEvent(fixedRef);
                    }
                }
                else {
                    if (added instanceof DbReference){
                        reference.extendedSecondaryRefList.addOnly((DbReference) added);
                        processAddedIdentifierEvent(added);
                    }
                    else {
                        DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                                added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                        reference.extendedSecondaryRefList.addOnly(fixedRef);
                        processAddedIdentifierEvent(fixedRef);
                    }
                }
            }
            else {
                if (added instanceof DbReference){
                    xref = new CvTermXref();
                    ((CvTermXref) xref).setPrimaryRefOnly((DbReference) added);
                    processAddedIdentifierEvent(added);
                }
                else {
                    DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                            added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                    xref = new CvTermXref();
                    ((CvTermXref) xref).setPrimaryRefOnly(fixedRef);
                    processAddedIdentifierEvent(fixedRef);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Xref removed) {

            if (xref != null){
                CvTermXref reference = (CvTermXref) xref;

                if (reference.getPrimaryRef() == removed){
                    reference.setPrimaryRefOnly(null);
                    processRemovedIdentifierEvent(removed);
                }
                else {
                    if (removed instanceof DbReference){
                        reference.extendedSecondaryRefList.removeOnly((DbReference) removed);
                        processRemovedIdentifierEvent(removed);

                    }
                    else {
                        DbReference fixedRef = new DbReference(removed.getDatabase().getShortName(), removed.getDatabase().getMIIdentifier(), removed.getId(),
                                removed.getQualifier() != null ? removed.getQualifier().getShortName() : null, removed.getQualifier() != null ? removed.getQualifier().getMIIdentifier() : null);

                        reference.extendedSecondaryRefList.removeOnly(fixedRef);
                        processRemovedIdentifierEvent(removed);
                    }
                }
            }
        }

        @Override
        protected void clearProperties() {
            clearPropertiesLinkedToIdentifiers();

            if (xref != null){
                CvTermXref reference = (CvTermXref) xref;
                reference.extendedSecondaryRefList.retainAllOnly(getXrefs());

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
                reference.extendedSecondaryRefList.retainAllOnly(getIdentifiers());

                if (!reference.isPrimaryAnIdentity){
                    reference.setPrimaryRefOnly(null);
                }
            }
        }
    }

    private class CvTermNames extends Names{

        protected AliasList extendedAliases = new AliasList();

        public String getShortLabel() {
            return getShortName();
        }

        public boolean hasShortLabel() {
            return getShortName() != null;
        }

        public void setShortLabel( String value ) {
            if (value != null){
                setShortNameOnly(value);
            }
            else {
                setShortNameOnly(UNSPECIFIED);
            }
        }

        public String getFullName() {
            return getCvTermFullName();
        }

        public boolean hasFullName() {
            return getCvTermFullName() != null;
        }

        public void setFullName( String value ) {
            setFullNameOnly(value);
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
            sb.append( "{shortLabel='" ).append( getShortName() ).append( '\'' );
            sb.append( ", fullName='" ).append( getCvTermFullName() ).append( '\'' );
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
            if ( getCvTermFullName() != null ? !getCvTermFullName().equals( names.getFullName() ) : names.getFullName() != null ) return false;
            if ( getShortName() != null ? !getShortName().equals( names.getShortLabel() ) : names.getShortLabel() != null ) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            result = ( getShortName() != null ? getShortName().hashCode() : 0 );
            result = 31 * result + ( getCvTermFullName() != null ? getCvTermFullName().hashCode() : 0 );
            result = 31 * result + ( extendedAliases != null ? extendedAliases.hashCode() : 0 );
            return result;
        }

        protected class AliasList extends AbstractListHavingPoperties<Alias>{

            @Override
            protected void processAddedObjectEvent(Alias added) {
                ((CvTermAliasList) getSynonyms()).addOnly(added);
            }

            @Override
            protected void processRemovedObjectEvent(Alias removed) {
                ((CvTermAliasList)getSynonyms()).removeOnly(removed);
            }

            @Override
            protected void clearProperties() {
                ((CvTermAliasList)getSynonyms()).clearOnly();
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