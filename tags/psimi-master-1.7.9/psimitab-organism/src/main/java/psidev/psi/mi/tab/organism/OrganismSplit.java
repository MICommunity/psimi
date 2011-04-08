/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.organism;

import psidev.psi.mi.tab.PsimiTabReader;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.CrossReference;
import psidev.psi.mi.tab.model.Organism;
import psidev.psi.mi.xml.converter.ConverterException;
import uk.ac.ebi.intact.util.newt.NewtBridge;
import uk.ac.ebi.intact.util.newt.NewtTerm;
import uk.ac.ebi.intact.util.newt.NewtUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * TODO comment this
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30-Jan-2007</pre>
 */
public class OrganismSplit {

    public static void main( String[] args ) throws ConverterException, IOException, ClassNotFoundException {

        System.out.println( "Starting at: " + new SimpleDateFormat( "hh:mm:ss a" ).format( new Date() ) );

        PsimiTabReader tabReader = new PsimiTabReader( true );
        Iterator<BinaryInteraction> iterator = tabReader.iterate( new File( "C:\\intact.txt" ) );

        // by storing in a Map and keeping the count of interactions we can optimize the closure of organism specific writer.
        Set<Integer> organismIdentifierSet = new HashSet<Integer>( 512 );

        // 1. Collect a distinct list of taxid

        while ( iterator.hasNext() ) {
            BinaryInteraction interaction = iterator.next();

            // interactor A
            Organism oa = interaction.getInteractorA().getOrganism();
            for ( CrossReference cr : oa.getIdentifiers() ) {
                if ( "taxid".equals( cr.getDatabase() ) ) {
                    organismIdentifierSet.add( Integer.parseInt( cr.getIdentifier() ) );
                }
            }

            // interactor B
            Organism ob = interaction.getInteractorB().getOrganism();
            for ( CrossReference cr : ob.getIdentifiers() ) {
                if ( "taxid".equals( cr.getDatabase() ) ) {
                    organismIdentifierSet.add( Integer.parseInt( cr.getIdentifier() ) );
                }
            }
        }

        System.out.println( "Found " + organismIdentifierSet.size() + " distinct organism." );

        // 2. Create a hierarchy of Organism using Newt
        NewtBridge newt = new NewtBridge( true );

        Collection<NewtTerm> organisms = new ArrayList<NewtTerm>( organismIdentifierSet.size() );
        int count = 0;
        for ( Integer taxid : organismIdentifierSet ) {
            count++;
            System.out.println( "Newt: Retreiving term #" + count + ": " + taxid );
            NewtTerm term = newt.getNewtTerm( taxid );
            System.out.println( "Newt: populating parent hierarchy " + term );
            newt.retreiveParents( term, true );

            organisms.add( term );
        }
        System.out.println( "Hierarchy built completed at: " + new SimpleDateFormat( "hh:mm:ss a" ).format( new Date() ) );

        // 3. get the root term
        NewtTerm arbitraryTerm = organisms.iterator().next();
        NewtTerm root = arbitraryTerm;
        while ( !root.getParents().isEmpty() ) {
            root = root.getParents().iterator().next();
        }

        // 4. Print the IntAct organism hierarchy
        NewtUtils.printNewtHierarchy( root );

        // 5. Serialize that root to a file so we can reuse it later
        File newtFile = new File( "newt.ser" );
        serialize( root, newtFile );

        NewtTerm newRoot = deserialize( newtFile );
        System.out.println( "newRoot = " + newRoot );
    }

    private static NewtTerm deserialize( File intput ) throws ClassNotFoundException, IOException {
        // Deserialize from a file
        ObjectInputStream in = new ObjectInputStream( new FileInputStream( intput ) );
        // Deserialize the object
        NewtTerm term = ( NewtTerm ) in.readObject();
        return term;
    }

    private static void serialize( NewtTerm root, File output ) throws ClassNotFoundException, IOException {
        // Serialize to a file
        System.out.println( "Serializing the Newt hierarchy to a file: " + output.getAbsolutePath() );
        ObjectOutput out = new ObjectOutputStream( new FileOutputStream( output ) );
        out.writeObject( root );
        out.close();
    }
}