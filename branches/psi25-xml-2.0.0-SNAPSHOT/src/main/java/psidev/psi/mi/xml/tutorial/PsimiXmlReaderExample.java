/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.tutorial;

import psidev.psi.mi.xml.PsimiXmlReader;
import psidev.psi.mi.xml.PsimiXmlWriter;
import psidev.psi.mi.xml.model.*;

import java.io.File;

/**
 * This code is used in the web site's tutorial. Should something change in the API we will find out early is the code breaks.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29-Sep-2006</pre>
 */
public class PsimiXmlReaderExample {
    public static void main( String[] args ) throws Exception {

        PsimiXmlReader pr = new PsimiXmlReader();
        EntrySet entrySet = pr.read( new File( "path/to/data.xml" ) );

        for ( Entry entry : entrySet.getEntries() ) {

            // browse all interaction
            for ( Interaction interaction : entry.getInteractions() ) {
                System.out.println( interaction.getNames().getShortLabel() );

                // browse all participants
                for ( Participant participant : interaction.getInteractionParticipants() ) {

                    Interactor interactor = participant.getInteractor();
                    String name = interactor.getNames().getShortLabel();
                    String type = interactor.getInteractorType().getNames().getShortLabel();

                    String role = "none";
                    if ( participant.hasBiologicalRole() ) {
                        role = participant.getBiologicalRole().getNames().getShortLabel();
                    }

                    System.out.println( type + ": " + name + " (" + role + ")" );
                }
            }
        }

        PsimiXmlWriter pw = new PsimiXmlWriter();
        pw.write( entrySet, new File( "path/to/output.xml" ) );
    }
}