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

    List<CrossReference> identifiers = new OrganismIdentifierList();

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

        for (CrossReference ref : identifiers){
            if (ref.getText() != null){
                if (shortestName == null || ref.getText().length() < shortestName.length()){
                    shortestName = ref.getText();
                }
                else if (longestName == null || ref.getText().length() > longestName.length()){
                    longestName = ref.getText();
                }
                else if (ref.getText() != null){
                    aliases.add(new DefaultAlias(CvTermFactory.createMICvTerm(Alias.SYNONYM, Alias.SYNONYM_MI), ref.getText()));
                }
            }
        }
        this.commonName = shortestName;
        this.scientificName = longestName;
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
    protected void initializeAliases() {
        aliases = new OrganismAliasList();
    }

    ////////////////
    //

    public void addIdentifier( CrossReference ref ) {
        identifiers.add( ref );
    }

    ////////////////////////
    // Getters & Setters


    public List<CrossReference> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers( List<CrossReference> identifiers ) {
        if ( identifiers == null ) {
            throw new IllegalArgumentException( "Identifiers cannot be null." );
        }
        this.identifiers.addAll(identifiers);
    }

    ///////////////////
    // Utility

    public String getTaxid() {
        return Integer.toString(taxId);
    }

    /////////////////////////
    // Object's override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Organism" );
        sb.append( "{identifiers=" ).append( identifiers );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        OrganismImpl organism = ( OrganismImpl ) o;

        if ( !CollectionUtils.isEqualCollection(identifiers, organism.getIdentifiers()) ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return identifiers.hashCode();
    }

    private void resetCommonNameFromIdentifiers(){
        if (identifiers.isEmpty()){
            this.commonName = null;
            this.taxId = -3;
        }
        else {
            String newCommonName=null;
            for (CrossReference ref : identifiers){
                if (ref.getText() != null){
                    if (newCommonName == null){
                        newCommonName = ref.getText();
                        this.taxId = Integer.parseInt(ref.getId());
                    }
                    else if (newCommonName.length() > ref.getText().length()){
                        newCommonName = ref.getText();
                        this.taxId = Integer.parseInt(ref.getId());
                    }
                }
            }
            this.commonName = newCommonName;
        }
    }

    private void resetScientificNameFromIdentifiers(){
        String newScientificName=null;
        for (CrossReference ref : identifiers){
            if (ref.getText() != null){
                if (newScientificName == null){
                    newScientificName = ref.getText();
                }
                else if (newScientificName.length() < ref.getText().length()){
                    newScientificName = ref.getText();
                }
            }
        }
        this.scientificName = newScientificName;
    }

    @Override
    public void setCommonName(String name) {
        super.setCommonName(name);

        CrossReference newIdentifier = new CrossReferenceImpl( DEFAULT_DATABASE, String.valueOf( taxId ), name );
        if (!identifiers.contains(newIdentifier)){
            identifiers.add(newIdentifier);
        }
    }

    @Override
    public void setScientificName(String name) {
        super.setScientificName(name);

        CrossReference newIdentifier = new CrossReferenceImpl( DEFAULT_DATABASE, String.valueOf( taxId ), name );
        if (!identifiers.contains(newIdentifier)){
            identifiers.add(newIdentifier);
        }
    }

    private class OrganismAliasList extends AbstractListHavingPoperties<Alias> {
        public OrganismAliasList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Alias added) {
            CrossReference matchingId = new CrossReferenceImpl(DEFAULT_DATABASE, String.valueOf(taxId), added.getName());

            if (!identifiers.contains(matchingId)){
                // add the matching identifier xref
                ((OrganismIdentifierList) identifiers).addOnly(matchingId);
            }
        }

        @Override
        protected void processRemovedObjectEvent(Alias removed) {

            // remove the matching identifier xref
            ((OrganismIdentifierList) identifiers).removeOnly(new CrossReferenceImpl(DEFAULT_DATABASE, String.valueOf(taxId), removed.getName()));
        }

        @Override
        protected void clearProperties() {
            List<CrossReference> identifiersCopy = new ArrayList<CrossReference>(identifiers);
            for (CrossReference ref : identifiersCopy){
                if (ref.getText() != null){
                    // ignore shortname and fullname
                    if (commonName == null && scientificName == null){
                        // remove the matching identifier xref
                        ((OrganismIdentifierList) identifiers).removeOnly(ref);
                    }
                    else if (commonName == null){
                        if (!scientificName.equals(ref.getText())){
                            // remove the matching identifier xref
                            ((OrganismIdentifierList) identifiers).removeOnly(ref);
                        }
                    }
                    else if (scientificName == null){
                        if (!commonName.equals(ref.getText())){
                            // remove the matching identifier xref
                            ((OrganismIdentifierList) identifiers).removeOnly(ref);
                        }
                    }
                    else {
                        if (!commonName.equals(ref.getText()) && !scientificName.equals(ref.getText())){
                            // remove the matching identifier xref
                            ((OrganismIdentifierList) identifiers).removeOnly(ref);
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

            if (added.getText() != null && commonName != null && scientificName != null && !added.getText().equals(commonName) && !added.getText().equals(scientificName)){
                Alias matchingAlias = new DefaultAlias(CvTermFactory.createMICvTerm(Alias.SYNONYM, Alias.SYNONYM_MI), added.getText());

                if (!aliases.contains(matchingAlias)){
                    ((OrganismAliasList) aliases).addOnly(matchingAlias);
                }
            }
            else if (added.getText() != null && commonName == null){
                commonName = added.getText();
            }
            else if (added.getText() != null && scientificName == null){
                scientificName = added.getText();
            }
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {
            if (removed.getText() != null){
                if (isEmpty()){
                   clearProperties();
                }
                else {
                    if (commonName != null && commonName.equals(removed.getText())){
                        resetCommonNameFromIdentifiers();
                    }
                    else if (scientificName != null && scientificName.equals(removed.getText())){
                        resetScientificNameFromIdentifiers();
                    }
                    else {
                        ((OrganismAliasList) aliases).removeOnly(new DefaultAlias(CvTermFactory.createMICvTerm(Alias.SYNONYM, Alias.SYNONYM_MI), removed.getText()));
                    }
                }
            }
        }

        @Override
        protected void clearProperties() {
            ((OrganismAliasList) aliases).clearOnly();
            commonName = null;
            scientificName = null;
            taxId = -3;
        }
    }
}