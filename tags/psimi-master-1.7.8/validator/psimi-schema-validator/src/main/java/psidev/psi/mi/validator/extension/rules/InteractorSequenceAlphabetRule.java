/**
 * Copyright 2009 The European Bioinformatics Institute, and others.
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
package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Interactor;
import psidev.psi.mi.xml.model.Participant;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Check that the alphabet used in a protein sequence is correct.
 * <p/>
 * source: http://en.wikipedia.org/wiki/Amino_acid
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 2.0
 */
public class InteractorSequenceAlphabetRule extends Mi25InteractionRule {

    public static final String AMINO_ACID_1_LETTER_CODES = "ARNDCEQGHILKMFPSTWYV";
    // source: http://searchlauncher.bcm.tmc.edu/multi-align/Help/pima.html
    public static final String AMINO_ACID_WILDCARDS_1_LETTER_CODES = "abdlkonihcempjfrX";
    public static final Pattern AMINO_ACID_SEQUENCE_PATTERN = Pattern.compile( "[" +
            AMINO_ACID_1_LETTER_CODES +
            AMINO_ACID_WILDCARDS_1_LETTER_CODES +
            "]+" );

    // source: http://embnet.ccg.unam.mx/docs/perl-doc/Bio/Tools/IUPAC.html
    public static final String NUCLEOTIDE_WILDCARDS = "MRWSYKVHDBXN";

    public static final String DNA_1_LETTER_CODES = "ATCG";
    public static final Pattern DNA_SEQUENCE_PATTERN = Pattern.compile( "[" +
            DNA_1_LETTER_CODES +
            NUCLEOTIDE_WILDCARDS +
            "]+" );

    public static final String RNA_1_LETTER_CODES = "AUCG";
    public static final Pattern RNA_SEQUENCE_PATTERN = Pattern.compile( "[" +
            RNA_1_LETTER_CODES +
            NUCLEOTIDE_WILDCARDS +
            "]+" );

    public InteractorSequenceAlphabetRule( OntologyManager ontologyManager ) {
        super( ontologyManager );
        setName( "Interactor sequence check" );
        setDescription( "Check that interactors of type biopolymer have a valid sequence." );
        addTip( "Proteins and Peptides take amino acids from: " + AMINO_ACID_1_LETTER_CODES );
        addTip( "DNA interactors take nucleic acids from: " + DNA_1_LETTER_CODES );
        addTip( "RNA interactors take nucleic acids from: " + RNA_1_LETTER_CODES );
    }

    public Collection<ValidatorMessage> check( Interaction interaction ) throws ValidatorException {

        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        for ( Participant participant : interaction.getParticipants() ) {

            final Interactor interactor = participant.getInteractor();

            if (interactor != null){
                if ( interactor.hasSequence() ) {

                    final String seq = interactor.getSequence();

                    if ( RuleUtils.isProtein( ontologyManager, interactor ) || RuleUtils.isPeptide( ontologyManager, interactor )) {
                        final Matcher matcher = AMINO_ACID_SEQUENCE_PATTERN.matcher( seq );
                        if ( !matcher.matches() ) {
                            // error
                            Mi25Context context = buildContext( interaction, participant, interactor );
                            messages.add( new ValidatorMessage( "Protein interactor with incorrect sequence: " + seq,
                                    MessageLevel.WARN,
                                    context,
                                    this ) );
                        }
                    } else if ( RuleUtils.isDNA( ontologyManager, interactor ) ) {
                        final Matcher matcher = DNA_SEQUENCE_PATTERN.matcher( seq );
                        if ( !matcher.matches() ) {
                            // error
                            Mi25Context context = buildContext( interaction, participant, interactor );
                            messages.add( new ValidatorMessage( "DNA interactor with incorrect sequence: " + seq,
                                    MessageLevel.WARN,
                                    context,
                                    this ) );
                        }
                    } else if ( RuleUtils.isRNA( ontologyManager, interactor ) ) {
                        final Matcher matcher = RNA_SEQUENCE_PATTERN.matcher( seq );
                        if ( !matcher.matches() ) {
                            // error
                            Mi25Context context = buildContext( interaction, participant, interactor );
                            messages.add( new ValidatorMessage( "RNA interactor with incorrect sequence: " + seq,
                                    MessageLevel.WARN,
                                    context,
                                    this ) );
                        }
                    } else {
                        // unknown interactor type, keep quiet.
                    }
                } else {
                    if( RuleUtils.isBiopolymer( ontologyManager, interactor ) ) {
                        Mi25Context context = buildContext( interaction, participant, interactor );
                        messages.add( new ValidatorMessage( "Biopolymer without a sequence",
                                MessageLevel.INFO,
                                context,
                                this ) );
                    }
                }
            }
        } // participants

        return messages;
    }

    private Mi25Context buildContext( Interaction interaction, Participant participant, Interactor interactor ) {
        Mi25Context context;
        context = new Mi25Context();
        context.setInteractionId( interaction.getId() );
        context.setParticipantId( participant.getId() );
        context.setInteractorId( interactor.getId() );
        return context;
    }


}