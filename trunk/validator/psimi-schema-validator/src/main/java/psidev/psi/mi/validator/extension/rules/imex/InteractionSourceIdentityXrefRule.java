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
package psidev.psi.mi.validator.extension.rules.imex;

import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.rules.AbstractMIRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.ontology_manager.interfaces.OntologyTermI;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.*;

import static psidev.psi.mi.validator.extension.rules.RuleUtils.*;

/**
 * Checks that the given interaction does have an identity xref to a valid interaction database, as defined in the
 * controlled vocabulary (currently: interaction database (MI:0461)).
 * <p/>
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 2.0
 */
public class InteractionSourceIdentityXrefRule extends AbstractMIRule<InteractionEvidence> {

    public InteractionSourceIdentityXrefRule( OntologyManager ontologyManager ) {
        super( ontologyManager,InteractionEvidence.class );

        // describe the rule.
        setName( "Interaction Identity Xref Check" );
        setDescription( "Checks that the interaction defines an identity cross reference to an interaction database." );
        addTip( "Existing interaction databases can be found as child of "+ INTERACTION_DATABASE_MI_REF +" in the " +
                "PSI-MI controlled vocabularies." );
    }

    public Collection<ValidatorMessage> check( InteractionEvidence interaction ) throws ValidatorException {

        // list of messages to return
        List<ValidatorMessage> messages = Collections.EMPTY_LIST;

        // write the rule here ...

        if( interaction.getIdentifiers().isEmpty() ) {
            Mi25Context context = RuleUtils.buildContext(interaction, "interaction");
            messages=Collections.singletonList( new ValidatorMessage( "An interaction requires an identity cross reference to an interaction database (child term of "+ INTERACTION_DATABASE_MI_REF +")." ,
                    MessageLevel.ERROR,
                    context,
                    this ) );
        } else {
            // check that we have at least one identity to an interaction database (eg. we could hve IMEx and IntAct)

            final OntologyAccess access = ontologyManager.getOntologyAccess( "MI" );
            final Set<OntologyTermI> dbTerms = access.getValidTerms(INTERACTION_DATABASE_MI_REF, true, false);
            final Set<String> interactionDbMis = collectAccessions(dbTerms);
            final Set<String> interactionDbs = collectNames(dbTerms);

            final Collection<Xref> dbRefs = XrefUtils.searchAllXrefsHavingDatabases(interaction.getIdentifiers(), interactionDbMis, interactionDbs);
            if( dbRefs.isEmpty() ) {
                Mi25Context context = RuleUtils.buildContext(interaction, "interaction");
                String dbList = buildDbList( interaction.getIdentifiers() );
                String msg = null;
                if( dbList.length() > 0 ) {
                    msg = "Found identity cross reference ("+ dbList +"), but none to an interaction database (child term of "+ INTERACTION_DATABASE_MI_REF +").";
                } else {
                    msg = "Found identity cross reference, but none to an interaction database (child term of "+ INTERACTION_DATABASE_MI_REF +").";
                }
                messages=Collections.singletonList( new ValidatorMessage( msg ,
                        MessageLevel.ERROR,
                        context,
                        this ) );
            }
        }

        return messages;
    }

    private String buildDbList( Collection<Xref> identities ) {
        StringBuilder sb = new StringBuilder( 128 );
        for ( Iterator<Xref> dbReferenceIterator = identities.iterator(); dbReferenceIterator.hasNext(); ) {
            Xref ref = dbReferenceIterator.next();
            final String db = ref.getDatabase().getShortName();
            final String dbAc = ref.getDatabase().getMIIdentifier();
            if( db != null && db.length() > 0 ) {
                sb.append( db ).append(" ,");
            } else if( dbAc != null && dbAc.length() > 0 ) {
                sb.append( dbAc ).append(" ,");
            }
        }
        int length = sb.length();
        if( length > 0 ) {
            length = length - 2; // gets rid of the last comma
        }
        return sb.substring( 0, length );
    }

    public String getId() {
        return "R41";
    }
}
