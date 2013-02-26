/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.model;

import org.apache.commons.collections.CollectionUtils;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a simple organism.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12-Jan-2007</pre>
 */
public class OrganismImpl extends DefaultOrganism implements Organism {

    /**
     * Generated with IntelliJ plugin generateSerialVersionUID.
     * To keep things consistent, please use the same thing.
     */
    private static final long serialVersionUID = 5647365864375422507L;

    List<CrossReference> identifiers;

    ///////////////////////
    // Cosntructor

    public OrganismImpl() {
        // create uncknown organism
        super(-3);
    }

    public OrganismImpl( CrossReference ref ) {
        super(ref != null ? Integer.parseInt(ref.getId()) : -3);
        addIdentifier( ref );
    }

    public OrganismImpl( List<CrossReference> identifiers ) {
        super((identifiers != null && !identifiers.isEmpty()) ? Integer.parseInt(identifiers.get(0).getId()) : -3);

        String shortestName=null;
        String longestName=null;

        OrganismAliasList organismAliases = (OrganismAliasList) getAliases();

        for (CrossReference ref : identifiers){
            if (ref.getText() != null){
                if (shortestName == null || ref.getText().length() < shortestName.length()){
                    shortestName = ref.getText();
                }
                else if (longestName == null || ref.getText().length() > longestName.length()){
                    longestName = ref.getText();
                }
                else if (ref.getText() != null){
                    organismAliases.addOnly(new DefaultAlias(CvTermFactory.createMICvTerm(Alias.SYNONYM, Alias.SYNONYM_MI), ref.getText()));
                }
            }
        }
        super.setCommonName(shortestName);
        super.setScientificName(longestName);
        setIdentifiers( identifiers );
    }

    public OrganismImpl( int taxid ) {
        super(taxid);
        addIdentifier( new CrossReferenceImpl( DEFAULT_DATABASE, String.valueOf( taxid ) ) );
    }

    public OrganismImpl( int taxid, String name ) {
        super(taxid, name);
        addIdentifier( new CrossReferenceImpl( DEFAULT_DATABASE, String.valueOf( taxid ), name ) );
    }

    @Override
    protected void initialiseAliases() {
        initialiseAliasesWith(new OrganismAliasList());
    }

    ////////////////
    //

    public void addIdentifier( CrossReference ref ) {
        getIdentifiers().add( ref );
    }

    ////////////////////////
    // Getters & Setters


    public List<CrossReference> getIdentifiers() {
        if (identifiers == null){
           identifiers = new OrganismIdentifierList();
        }
        return identifiers;
    }

    public void setIdentifiers( List<CrossReference> identifiers ) {
        if ( identifiers == null ) {
            throw new IllegalArgumentException( "Identifiers cannot be null." );
        }
        getIdentifiers().addAll(identifiers);
    }

    ///////////////////
    // Utility

    public String getTaxid() {
        return Integer.toString(getTaxId());
    }

    /////////////////////////
    // Object's override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Organism" );
        sb.append( "{identifiers=" ).append( getIdentifiers() );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        OrganismImpl organism = ( OrganismImpl ) o;

        if ( !CollectionUtils.isEqualCollection(getIdentifiers(), organism.getIdentifiers()) ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return getIdentifiers().hashCode();
    }

    private void resetCommonNameFromIdentifiers(){
        if (getIdentifiers().isEmpty()){
            super.setCommonName(null);
            super.setTaxId(-3);
        }
        else {
            String newCommonName=null;
            for (CrossReference ref : getIdentifiers()){
                if (ref.getText() != null){
                    if (newCommonName == null){
                        newCommonName = ref.getText();
                        super.setTaxId(Integer.parseInt(ref.getId()));
                    }
                    else if (newCommonName.length() > ref.getText().length()){
                        newCommonName = ref.getText();
                        super.setTaxId(Integer.parseInt(ref.getId()));
                    }
                }
            }
            super.setCommonName(newCommonName);
        }
    }

    private void resetScientificNameFromIdentifiers(){
        String newScientificName=null;
        for (CrossReference ref : getIdentifiers()){
            if (ref.getText() != null){
                if (newScientificName == null){
                    newScientificName = ref.getText();
                }
                else if (newScientificName.length() < ref.getText().length()){
                    newScientificName = ref.getText();
                }
            }
        }
        super.setScientificName(newScientificName);
    }

    @Override
    public void setCommonName(String name) {
        super.setCommonName(name);

        CrossReference newIdentifier = new CrossReferenceImpl( DEFAULT_DATABASE, String.valueOf( getTaxId() ), name );
        if (!getIdentifiers().contains(newIdentifier)){
            getIdentifiers().add(newIdentifier);
        }
    }

    protected void setCommonNameOnly(String name) {
        super.setCommonName(name);
    }

    @Override
    public void setScientificName(String name) {
        super.setScientificName(name);

        CrossReference newIdentifier = new CrossReferenceImpl( DEFAULT_DATABASE, String.valueOf( getTaxId() ), name );
        if (!getIdentifiers().contains(newIdentifier)){
            getIdentifiers().add(newIdentifier);
        }
    }

    protected void setScientificNameOnly(String name) {
        super.setScientificName(name);
    }

    @Override
    public void setTaxId(int id) {
        super.setTaxId(id);

        getIdentifiers().clear();
        CrossReference ref = new CrossReferenceImpl("taxid", Integer.toString(id));
        ((OrganismIdentifierList)getIdentifiers()).addOnly(ref);
    }

    public void setTaxIdOnly(int id) {
        super.setTaxId(id);
    }

    private class OrganismAliasList extends AbstractListHavingPoperties<Alias> {
        public OrganismAliasList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Alias added) {
            CrossReference matchingId = new CrossReferenceImpl(DEFAULT_DATABASE, String.valueOf(getTaxId()), added.getName());

            if (!getIdentifiers().contains(matchingId)){
                // add the matching identifier xref
                ((OrganismIdentifierList) getIdentifiers()).addOnly(matchingId);
            }
        }

        @Override
        protected void processRemovedObjectEvent(Alias removed) {

            // remove the matching identifier xref
            ((OrganismIdentifierList) getIdentifiers()).removeOnly(new CrossReferenceImpl(DEFAULT_DATABASE, String.valueOf(getTaxId()), removed.getName()));
        }

        @Override
        protected void clearProperties() {
            List<CrossReference> identifiersCopy = new ArrayList<CrossReference>(getIdentifiers());
            for (CrossReference ref : identifiersCopy){
                if (ref.getText() != null){
                    // ignore shortname and fullname
                    if (getCommonName() == null && getScientificName() == null){
                        // remove the matching identifier xref
                        ((OrganismIdentifierList) getIdentifiers()).removeOnly(ref);
                    }
                    else if (getCommonName() == null){
                        if (!getScientificName().equals(ref.getText())){
                            // remove the matching identifier xref
                            ((OrganismIdentifierList) getIdentifiers()).removeOnly(ref);
                        }
                    }
                    else if (getScientificName() == null){
                        if (!getCommonName().equals(ref.getText())){
                            // remove the matching identifier xref
                            ((OrganismIdentifierList) getIdentifiers()).removeOnly(ref);
                        }
                    }
                    else {
                        if (!getCommonName().equals(ref.getText()) && !getScientificName().equals(ref.getText())){
                            // remove the matching identifier xref
                            ((OrganismIdentifierList) getIdentifiers()).removeOnly(ref);
                        }
                    }
                }
            }
        }
    }

    private class OrganismIdentifierList extends AbstractListHavingPoperties<CrossReference> {
        public OrganismIdentifierList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {

            if (added.getText() != null && getCommonName() != null && getScientificName() != null && !added.getText().equals(getCommonName()) && !added.getText().equals(getScientificName())){
                Alias matchingAlias = new DefaultAlias(CvTermFactory.createMICvTerm(Alias.SYNONYM, Alias.SYNONYM_MI), added.getText());

                if (!getAliases().contains(matchingAlias)){
                    ((OrganismAliasList) getAliases()).addOnly(matchingAlias);
                }
            }
            else if (added.getText() != null && getCommonName() == null){
                setCommonNameOnly(added.getText());
            }
            else if (added.getText() != null && getScientificName() == null){
                setScientificNameOnly(added.getText());
            }
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {
            if (removed.getText() != null){
                if (isEmpty()){
                   clearProperties();
                }
                else {
                    if (getCommonName() != null && getCommonName().equals(removed.getText())){
                        resetCommonNameFromIdentifiers();
                    }
                    else if (getScientificName() != null && getScientificName().equals(removed.getText())){
                        resetScientificNameFromIdentifiers();
                    }
                    else {
                        ((OrganismAliasList) getAliases()).removeOnly(new DefaultAlias(CvTermFactory.createMICvTerm(Alias.SYNONYM, Alias.SYNONYM_MI), removed.getText()));
                    }
                }
            }
        }

        @Override
        protected void clearProperties() {
            ((OrganismAliasList) getAliases()).clearOnly();
            setCommonNameOnly(null);
            setScientificNameOnly(null);
            setTaxIdOnly(-3);
        }
    }
}