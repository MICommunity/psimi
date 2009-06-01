/**
 * Copyright 2008 The European Bioinformatics Institute, and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package psidev.psi.mi.tab.mock;

import psidev.psi.mi.tab.model.*;
import psidev.psi.mi.xml.model.Interaction;

import java.util.*;

/**
 * Builds mock objects for the psimitab model.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.5.2
 */
public class PsimiTabMockBuilder{

    private static final String VOWELS = "aeiouy";
    private static final String CONSONANTS = "qwrtpsdfghjklzxcvbnm";
    private static final String INTEGERS = "0123456789";
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static List<CrossReference> sourceDatabases = new ArrayList<CrossReference>();
    private static List<InteractionType> interactionTypes = new ArrayList<InteractionType>();
    private static List<InteractionDetectionMethod> interactionDetectionMethods = new ArrayList<InteractionDetectionMethod>();
    private static List<String> confidenceTypes = new ArrayList<String>();
    private static List<Organism> organisms = new ArrayList<Organism>();

    static {
        // database sources
        sourceDatabases.add( new CrossReferenceImpl( "intact", "MI:0469", "EBI-" ) );
        sourceDatabases.add( new CrossReferenceImpl( "mint", "MI:0471", "MINT-" ) );
        sourceDatabases.add( new CrossReferenceImpl( "dip", "MI:0465", "DIP" ) );

        // Interaction Types
        interactionTypes.add( new InteractionTypeImpl( "psi-mi", "MI:0914", "association" ) );
        interactionTypes.add( new InteractionTypeImpl( "psi-mi", "MI:0915", "physical association" ) );
        interactionTypes.add( new InteractionTypeImpl( "psi-mi", "MI:0403", "colocalization" ) );

        // Interaction Detection Methods
        interactionDetectionMethods.add( new InteractionDetectionMethodImpl( "psi-mi", "MI:0435", "protease assay" ) );
        interactionDetectionMethods.add( new InteractionDetectionMethodImpl( "psi-mi", "MI:0055", "fluorescent resonance energy transfer" ) );
        interactionDetectionMethods.add( new InteractionDetectionMethodImpl( "psi-mi", "MI:0018", "2 hybrid" ) );
        interactionDetectionMethods.add( new InteractionDetectionMethodImpl( "psi-mi", "MI:0115", "yeast display" ) );
        interactionDetectionMethods.add( new InteractionDetectionMethodImpl( "psi-mi", "MI:0027", "cosedimentation" ) );
        interactionDetectionMethods.add( new InteractionDetectionMethodImpl( "psi-mi", "MI:0397", "two hybrid array" ) );

        confidenceTypes.add( "intact confidence" );
        confidenceTypes.add( "psi-score" );

        organisms.add( new OrganismImpl( 9606, "human" ) );
        organisms.add( new OrganismImpl( 4932, "yeast" ) );
        organisms.add( new OrganismImpl( 3702, "arath" ) );
        organisms.add( new OrganismImpl( 6239, "caeel" ) );
        organisms.add( new OrganismImpl( 8355, "xenla" ) );
    }

    public PsimiTabMockBuilder() {
    }

    ////////////////////////
    // Builder methods

    // interactor

    protected Interactor buildInteractor() {
        return new Interactor();
    }

    public Interactor createInteractor( int taxid, String db, String id, String txt ) {
        CrossReference cr = new CrossReferenceImpl( db, id, txt );
        final Interactor interactor = buildInteractor();
        interactor.setIdentifiers( createList( cr ) );
        Organism o = new OrganismImpl( taxid );
        interactor.setOrganism( o );
        return interactor;
    }

    public Interactor createInteractor( int taxid, String db, String id ) {
        return createInteractor( taxid, db, id, null );
    }

    public Interactor createProteinInteractorRandom() {
        final Interactor interactor = buildInteractor();
        interactor.setAliases( createAliasRandom( boundRandom( 1, 4 ), "uniprotkb" ) );
        interactor.setAlternativeIdentifiers( createUniprotCrossReferenceList( boundRandom( 0, 4 ) ) );
        interactor.setIdentifiers( createList( createCrossReference( "uniprotkb", randomUniprotAc() ) ) );
        interactor.setOrganism( ( Organism ) pickRandom( organisms ) );
        return interactor;
    }

    // alias

    public Collection<Alias> createAliasRandom( int aliasCount, String db ) {
        Collection<Alias> aliases = new ArrayList<Alias>( aliasCount );
        for ( int i = 0; i < aliasCount; i++ ) {
            aliases.add( new AliasImpl( db, randomString() ) );
        }
        return aliases;
    }

    // organism

    public Organism createOrganismRandom() {
        return new OrganismImpl( nextInt(), randomString() );
    }

    // interaction

    protected BinaryInteraction buildInteraction(Interactor a, Interactor b) {
        return new BinaryInteractionImpl( a, b );
    }

    public BinaryInteraction createInteraction( Interactor a, Interactor b ) {
        BinaryInteraction bi = buildInteraction( a, b );
        bi.getDetectionMethods().add( new InteractionDetectionMethodImpl( "psi-mi", "MI:0071", "molecular sieving" ) );
        return bi;
    }

    public BinaryInteraction createInteractionRandom() {
        BinaryInteraction bi = buildInteraction( createProteinInteractorRandom(),
                                                 createProteinInteractorRandom() );

        CrossReference source = pickRandom( sourceDatabases );

        bi.setAuthors( createList( createAuthorRandom() ) );
        bi.setConfidenceValues( createList( createConfidenceRandom() ) );
        bi.setDetectionMethods( createList( createInteractionDetectionMethodRandom() ) );
        bi.setInteractionAcs( createList( createCrossReference( source.getDatabase(), source.getText() + nextId() ) ) );
        bi.setInteractionTypes( createList( createInteractionTypeRandom() ) );
        bi.setPublications( createList( createCrossReference( "pubmed", String.valueOf( boundRandom( 100000, 999999 ) ) ) ) );
        bi.setSourceDatabases( createList( source ) );

        return bi;
    }

    // interaction type

    public InteractionType createInteractionTypeRandom() {
        return pickRandom( interactionTypes );
    }

    // interaction detection method

    public InteractionDetectionMethod createInteractionDetectionMethodRandom() {
        return pickRandom( interactionDetectionMethods );
    }

    // Condifence

    public Confidence createConfidenceRandom() {
        String confidenceType = pickRandom( confidenceTypes );
        return new ConfidenceImpl( confidenceType, String.valueOf( boundRandomDouble( 0d, 1d ) ) );
    }

    // Author

    public Author createAuthorRandom() {
        int year = 2000 + new Random().nextInt( 8 );
        return new AuthorImpl( randomString( boundRandom( 5, 10 ) ) + " et al." + " (" + year + ")" );
    }

    // Xrefs

    public Collection<CrossReference> createUniprotCrossReferenceList( int crossRefCount ) {
        Collection<CrossReference> references = new ArrayList<CrossReference>( crossRefCount );
        for ( int i = 0; i < crossRefCount; i++ ) {
            references.add( createCrossReference( "uniprotkb", randomUniprotAc() ) );
        }
        return null;
    }

    public CrossReference createCrossReference( String db, String id, String text ) {
        return CrossReferenceFactory.getInstance().build( db, id, text );
    }

    public CrossReference createCrossReference( String db, String id ) {
        return CrossReferenceFactory.getInstance().build( db, id );
    }

    public CrossReference createUniprotCrossReference( String id ) {
        return CrossReferenceFactory.getInstance().build( "uniprotkb", id );
    }

    ///////////////////
    // Utilities

    private int sequence = 0;

    protected String nextString() {
        return randomString();
    }

    protected String nextString( String prefix ) {
        return prefix + "_" + randomString();
    }

    protected int nextInt() {
        return new Random().nextInt( 10000 );
    }

    protected int nextId() {
        return ++sequence;
    }

    protected int boundRandom( int min, int max ) {
        if ( min == max ) return max;

        return new Random().nextInt( max - min ) + min;
    }

    protected double boundRandomDouble( double min, double max ) {
        if ( min == max ) return max;
        final double random = new Random().nextDouble();
        double x = random * max;
        if ( x < min ) {
            x += ( max - min );
        }
        return x;
    }

    public String randomString() {
        return randomString( boundRandom( 4, 10 ) );
    }

    public char randomChar( String alphabet ) {
        return alphabet.charAt( ( int ) ( Math.random() * alphabet.length() ) );
    }

    public String randomString( int returnLength ) {

        StringBuilder random = new StringBuilder( returnLength );
        final Random rdm = new Random( System.currentTimeMillis() );
        for ( int j = 0; j < returnLength; j++ ) {
            boolean nextIsVowel = rdm.nextBoolean();

            if ( nextIsVowel ) {
                random.append( randomChar( VOWELS ) );
            } else {
                random.append( randomChar( CONSONANTS ) );
            }
        }
        return random.toString();
    }

    protected String randomExperimentLabel() {
        int year = 2000 + new Random().nextInt( 8 );
        return randomString() + "-" + year + "-" + ( new Random().nextInt( 7 ) + 1 );
    }

    public String randomUniprotAc() {
        StringBuilder sb = new StringBuilder( 5 );
        sb.append( randomChar( "OPQ" ) );
        sb.append( randomChar( INTEGERS ) );
        sb.append( randomChar( ALPHABET ) );
        sb.append( randomChar( INTEGERS ) );
        sb.append( randomChar( INTEGERS ) );
        return sb.toString();
    }

    protected <T> T pickRandom( List<T> list ) {
        return list.get( boundRandom( 0, list.size() - 1 ) );
    }

    protected <T> List<T> createList( T... objects ) {
        List<T> result = new ArrayList<T>( objects.length );
        for ( T object : objects ) {
            result.add( object );
        }
        return result;
    }
}
