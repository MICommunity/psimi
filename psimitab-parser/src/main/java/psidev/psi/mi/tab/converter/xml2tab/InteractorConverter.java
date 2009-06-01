/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.converter.xml2tab;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.converter.IdentifierGenerator;
import psidev.psi.mi.tab.converter.tab2xml.InteractorNameBuilder;
import psidev.psi.mi.tab.converter.tab2xml.InteractorUniprotIdBuilder;
import psidev.psi.mi.tab.converter.tab2xml.XmlConversionException;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.CrossReference;
import psidev.psi.mi.tab.model.CrossReferenceFactory;
import psidev.psi.mi.tab.model.OrganismFactory;
import psidev.psi.mi.xml.model.*;

import java.util.*;

/**
 * Converts an psi25 interactor into a simple interactor for MITAB25;
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Oct-2006</pre>
 */
public abstract class InteractorConverter<T extends psidev.psi.mi.tab.model.Interactor> {

    public static final String IDENTITY = "identity";
    public static final String IDENTITY_REF = "MI:0356";
    public static final String SOURCE_REFERENCE = "source reference";
    public static final String SOURCE_REFERENCE_REF = "MI:0685";

    public static final String UNIPROT = "uniprotkb";
    private static final String UNIPROT_MI = "MI:0486";

    public static final String INTACT = "intact";
    private static final String INTACT_MI = "MI:0469";

    public static final String CHEBI = "chebi";
    private static final String CHEBI_MI = "MI:0474";

    private static final String PSIMI = "psi-mi";
    private static final String PSIMI_MI = "MI:0488";

    private static final List<String> uniprotKeys = new ArrayList<String>( Arrays.asList( new String[]
            {"gene name", "gene name synonym", "isoform synonym", "ordered locus name", "open reading frame name"} ));

    public static final String SHORT_LABEL = "shortLabel";
    /**
     * Override the alias source database (column 3/4 & 5/6).
     */
    @Deprecated
    private CrossReference overrideAliasSourceDatabase;

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( InteractorConverter.class );


    ///////////////////////////
    // Setter

    /**
     * Setter for property 'overrideAliasSourceDatabase'.
     *
     * @param overrideAliasSourceDatabase Value to set for property 'overrideAliasSourceDatabase'.
     */
    @Deprecated
    public void setOverrideAliasSourceDatabase( CrossReference overrideAliasSourceDatabase ) {
        this.overrideAliasSourceDatabase = overrideAliasSourceDatabase;

    }

    private CrossReference buildCrossReference( DbReference ref ) {
        return CrossReferenceFactory.getInstance().build( ref.getDb(), ref.getId() );
    }

    ///////////////////////
    // Conversion method

    public psidev.psi.mi.tab.model.Interactor toMitab( psidev.psi.mi.xml.model.Interactor interactor ) throws TabConversionException {

        if ( interactor == null ) {
            throw new IllegalArgumentException( "Interactor must not be null" );
        }

        // collect primary accession number
        List<CrossReference> identifiers = new ArrayList<CrossReference>( 2 );
        List<CrossReference> altIdentifiers = new ArrayList<CrossReference>( 2 );

        Collection<DbReference> identityRefs = null;
        if ( interactor.getXref() != null ) {

            identityRefs = XrefUtils.searchByType( interactor.getXref(), IDENTITY, IDENTITY_REF );
            identityRefs.addAll(XrefUtils.searchByType( interactor.getXref(), SOURCE_REFERENCE, SOURCE_REFERENCE_REF ));
            for ( DbReference ref : identityRefs ) {
                CrossReference cr = buildCrossReference( ref );
                if ( !identifiers.contains( cr ) ) {
                    identifiers.add( cr );
                }
            }

            List<DbReference> allRefs = XrefUtils.getAllDbReferences( interactor.getXref() );

            if ( identifiers.isEmpty() && allRefs.size() > 0 ) {

                // sort identifiers
                allRefs = XrefUtils.sortByIdentifier( allRefs );

                // pick the first one in the list
                identifiers.add( buildCrossReference( allRefs.iterator().next() ) );
            }

        } // xrefs

        if ( identifiers.isEmpty() ) {
            throw new TabConversionException( "Could not find any identifiers for interactor " + interactor.getId() );
        }

        T tabInteractor = newInteractor(identifiers);

        // alternative identifiers
        //   -> use the gene name
        Collection<Alias> aliases = AliasUtils.getAllAliases( interactor.getNames() );
        Alias gn = AliasUtils.getGeneName( aliases );
        if ( gn != null ) {
            aliases.remove( gn ); // (!) the rest will be stored in the alias builder
            String geneNameValue = gn.getValue();

            if (geneNameValue != null && geneNameValue.trim().length() > 0) {
                CrossReference cr = CrossReferenceFactory.getInstance().build( UNIPROT, geneNameValue);
                cr.setText( gn.getType() );
                altIdentifiers.add( cr );
                tabInteractor.setAlternativeIdentifiers( altIdentifiers );
            } else {
                log.warn("Found alias (gene name) without value. Ignoring");
            }
        }

        // aliases
        Collection<psidev.psi.mi.tab.model.Alias> tabAliases = new ArrayList<psidev.psi.mi.tab.model.Alias>();
        for ( Alias alias : aliases ) {

            String aliasValue = alias.getValue();

            if (aliasValue != null && aliasValue.trim().length() > 0) {
                String db;
                if ( uniprotKeys.contains( alias.getType()) ){
                    db = UNIPROT;
                } else {
                    db = INTACT;
                }
                psidev.psi.mi.tab.model.Alias a = new psidev.psi.mi.tab.model.AliasImpl( db, aliasValue );
                a.setAliasType( alias.getType() );
                tabAliases.add( a );
            } else {
                log.warn("Found alias without value. Ignoring alias");
            }
        }
        //include shortlabel also
        if ( interactor.getNames() != null && interactor.getNames().getShortLabel() != null ) {
            String shortLabel = interactor.getNames().getShortLabel();
            String db = INTACT;
            psidev.psi.mi.tab.model.Alias a = new psidev.psi.mi.tab.model.AliasImpl( db, shortLabel );
            a.setAliasType( SHORT_LABEL );
            tabAliases.add( a );
        }

        tabInteractor.setAliases( tabAliases );

        // taxonomy
        if ( interactor.hasOrganism() ) {
            psidev.psi.mi.xml.model.Organism o = interactor.getOrganism();
            psidev.psi.mi.tab.model.Organism organism = null;
            String name = null;
            if ( o.hasNames() ) {
                if ( o.getNames().hasShortLabel() ) {
                    name = o.getNames().getShortLabel();
                }
            }

            if( name == null ) {
                organism = OrganismFactory.getInstance().build( o.getNcbiTaxId() );
            } else {
                organism = OrganismFactory.getInstance().build( o.getNcbiTaxId(), name );
            }

            tabInteractor.setOrganism( organism );
        }

        return tabInteractor;
    }

    protected T newInteractor(List<CrossReference> identifiers) {
         return (T) new psidev.psi.mi.tab.model.Interactor( identifiers );
    }

	/**
	 * Strategy defining which interactor name used.
	 */
	private InteractorNameBuilder interactorNameBuilder;


    /////////////////////////////////////
    // Getters & Setters

	/**
	 * Getter for property 'interactorNameBuilder'
	 */
	public InteractorNameBuilder getInteractorNameBuilder(){
		return interactorNameBuilder;
	}

	/**
	 * Setter for property 'interactorNameBuilder'
	 */
	public void setInteractorNameBuilder(InteractorNameBuilder interactorNameBuilder){
		this.interactorNameBuilder = interactorNameBuilder;
	}

    public psidev.psi.mi.xml.model.Interactor fromMitab( psidev.psi.mi.tab.model.Interactor tabInteractor ) throws XmlConversionException {

        if ( tabInteractor == null ) {
            throw new IllegalArgumentException( "Interactor must not be null" );
        }

        psidev.psi.mi.xml.model.Interactor xmlInteractor = new psidev.psi.mi.xml.model.Interactor();
        xmlInteractor.setId( IdentifierGenerator.getInstance().nextId() );

        // taxonomy
        if ( tabInteractor.hasOrganism() ) {
            psidev.psi.mi.tab.model.Organism o = tabInteractor.getOrganism();

            // get shortLabel
            Iterator<CrossReference> idIterator = o.getIdentifiers().iterator();
            String shortlabel = null;
            if ( idIterator.hasNext() ) {
                CrossReference first = idIterator.next();
                if ( first.hasText() ) {
                    shortlabel = first.getText();
                }
            }

            // get taxid
            int ncbiTaxId = 0;
            if( o.getTaxid() != null ) {
                try {
                    ncbiTaxId = Integer.parseInt( o.getTaxid() );
                } catch ( NumberFormatException e ) {
                    final String msg =  "Could not parse taxid " + o.getTaxid() + ", it doesn't seem to be a valid integer value.";
                    throw new XmlConversionException( msg );
                }

                psidev.psi.mi.xml.model.Organism organism = new psidev.psi.mi.xml.model.Organism();

                Names names = new Names();
                organism.setNames( names );
                if( shortlabel != null ) {
                    names.setShortLabel( shortlabel );
                } else {
                    names.setShortLabel( String.valueOf( ncbiTaxId ) );
                }

                organism.setNcbiTaxId( ncbiTaxId );
                xmlInteractor.setOrganism( organism );
            } 
        }

        // set primary & secondary references
        DbReference primaryReferece = null;
        Collection<DbReference> secondaryRefs = new ArrayList<DbReference>();

        Collection<CrossReference> tabIdentifiers = tabInteractor.getIdentifiers();
        String primaryDatabase = null;
        String primaryId = null;
        if (!tabIdentifiers.isEmpty()){

        	Iterator<CrossReference> identifierIterator = tabIdentifiers.iterator();
        	CrossReference primaryIdentifier = identifierIterator.next();
        	overrideAliasSourceDatabase = primaryIdentifier;

        	primaryDatabase = primaryIdentifier.getDatabase();
        	primaryId = primaryIdentifier.getIdentifier();

        	primaryReferece = new DbReference(primaryId, primaryDatabase);
        	if (primaryDatabase.equals(UNIPROT)) {
        		primaryReferece.setDbAc( UNIPROT_MI );
            	primaryReferece.setRefType(IDENTITY);
            	primaryReferece.setRefTypeAc(IDENTITY_REF);
        	}
        	else if (primaryDatabase.equals(CHEBI)){
        		primaryReferece.setDbAc( CHEBI_MI );
            	primaryReferece.setRefType(IDENTITY);
            	primaryReferece.setRefTypeAc(IDENTITY_REF);
        	}
        	else if (primaryDatabase.equals(INTACT)){
        		primaryReferece.setDbAc( INTACT_MI );
            	primaryReferece.setRefType(IDENTITY);
            	primaryReferece.setRefTypeAc(IDENTITY_REF);
        	}

        	while (identifierIterator.hasNext()){
        		CrossReference secondaryIdentifier = identifierIterator.next();

               	String database = secondaryIdentifier.getDatabase();
            	String id = secondaryIdentifier.getIdentifier();

        		DbReference secondaryRef = new DbReference(id,database);
        		secondaryRefs.add(secondaryRef);
        	}
        }
        if (primaryReferece != null){
        	if (!secondaryRefs.isEmpty()){
        		Xref interactorXref = new Xref(primaryReferece,secondaryRefs);
        		xmlInteractor.setXref(interactorXref);
        	} else {
        		Xref interactorXref = new Xref(primaryReferece);
        		xmlInteractor.setXref(interactorXref);
        	}
        } else {
        	throw new XmlConversionException("No Xref found");
        }

        // set interactor name
        Names interactorName = null;
        if (interactorNameBuilder != null){
        	interactorName = interactorNameBuilder.select(tabInteractor);
        } else {
        	log.debug("No InteractorNameBuilder choosen. default = "+ InteractorUniprotIdBuilder.class);
        	InteractorNameBuilder builder = new InteractorUniprotIdBuilder();
        	interactorName = builder.select(tabInteractor);
        }

    	if (interactorName != null){
    		xmlInteractor.setNames(interactorName);
    	}


    	InteractorType interactorType = new InteractorType();

    	Xref interactorTypeXref = null;
    	Names interactorTypeName = new Names();

    	if (xmlInteractor.hasXref()){
    		String xrefPrimaryDB = xmlInteractor.getXref().getPrimaryRef().getDb();
    		if (xrefPrimaryDB.equals(UNIPROT)){
    			// in this case the interactorType is a protein
    	    	interactorTypeName.setShortLabel("protein");
    	    	interactorTypeName.setFullName("protein");

    	    	DbReference dbRef = new DbReference("MI:0326", PSIMI );
    	    	dbRef.setDbAc( PSIMI_MI );
    	    	dbRef.setRefType(IDENTITY);
    	    	dbRef.setRefTypeAc(IDENTITY_REF);
    			interactorTypeXref = new Xref(dbRef);
    		}
    		else if (xrefPrimaryDB.equals(CHEBI)){
    			// in this case the interactorType is a small molecule
    	    	interactorTypeName.setShortLabel("small molecule");
    	    	interactorTypeName.setFullName("small molecule");

    	    	DbReference dbRef = new DbReference("MI:0328", PSIMI );
    	    	dbRef.setDbAc( PSIMI_MI );
    	    	dbRef.setRefType(IDENTITY);
    	    	dbRef.setRefTypeAc(IDENTITY_REF);
    			interactorTypeXref = new Xref(dbRef);
    		}
    		else {
    			// in other cases its not possible to know which type -> so we create a unknown
       	    	interactorTypeName.setShortLabel("unknown participant");
    	    	interactorTypeName.setFullName("unknown participant");

    	    	DbReference dbRef = new DbReference("MI:0329", PSIMI );
    	    	dbRef.setDbAc( PSIMI_MI );
    	    	dbRef.setRefType(IDENTITY);
    	    	dbRef.setRefTypeAc(IDENTITY_REF);
    			interactorTypeXref = new Xref(dbRef);
    			log.debug("Interactor type is unknown");
    		}

    	}
    	interactorType.setNames(interactorTypeName);
    	interactorType.setXref(interactorTypeXref);
    	xmlInteractor.setInteractorType(interactorType);

        // note: interactionType are not stored in MITAB25, but XMLWriter need one -> default (=empty) InteractorType were set.
    	return xmlInteractor;
    }

    public abstract Participant buildParticipantA(Interactor xmlInteractor, BinaryInteraction binaryInteraction, int index)
            throws XmlConversionException;

    public abstract Participant buildParticipantB(Interactor xmlInteractor, BinaryInteraction binaryInteraction, int index)
            throws XmlConversionException;
}

