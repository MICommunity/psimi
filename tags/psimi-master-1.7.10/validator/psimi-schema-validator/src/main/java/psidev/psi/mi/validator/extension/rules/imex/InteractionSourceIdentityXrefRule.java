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

import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import static psidev.psi.mi.validator.extension.rules.RuleUtils.*;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyTermI;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;

import java.util.*;

/**
 * Checks that the given interaction does have an identity xref to a valid interaction database, as defined in the
 * controlled vocabulary (currently: interaction database (MI:0461)).
 * <p/>
 *
 * TODO It seems desirable to update the ontology in order to put IMEx partners under the term imex (MI:0670).
 * TODO Then we could only rely on that parent term.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 2.0
 */
public class InteractionSourceIdentityXrefRule extends Mi25InteractionRule {

    public InteractionSourceIdentityXrefRule( OntologyManager ontologyManager ) {
        super( ontologyManager );

        // describe the rule.
        setName( "Interaction Identity Xref Check" );
        setDescription( "Checks that the interaction defines an identity cross reference to an interaction database." );
        addTip( "Existing interaction databases can be found as child of "+ INTERACTION_DATABASE_MI_REF +" in the " +
                "PSI-MI controlled vocabularies." );
    }

    public Collection<ValidatorMessage> check( Interaction interaction ) throws ValidatorException {
        int interactionId = interaction.getId();

        // list of messages to return
        List<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        // write the rule here ...
        Collection<DbReference> identities = null;
        if( interaction.hasXref() ) {
            identities = searchReferences( interaction.getXref().getAllDbReferences(),
                                           IDENTITY_MI_REF,
                                           null, null );
        }
        
        if( identities == null || identities.isEmpty() ) {
            Mi25Context context = new Mi25Context();
            context.setInteractionId( interactionId );
            messages.add( new ValidatorMessage( "An interaction requires an identity cross reference to an interaction database (child term of "+ INTERACTION_DATABASE_MI_REF +")." ,
                                                MessageLevel.ERROR,
                                                context,
                                                this ) );
        } else {
            // check that we have at least one identity to an interaction database (eg. we could hve IMEx and IntAct)

            final OntologyAccess access = getMiOntology();
            final Set<OntologyTermI> dbTerms = access.getValidTerms( INTERACTION_DATABASE_MI_REF, true, false );
            final Set<String> interactionDbMis = collectAccessions( dbTerms );

            final Collection<DbReference> dbRefs = searchReferences( identities,
                                                                     null,
                                                                     interactionDbMis,
                                                                     null );
            if( dbRefs.isEmpty() ) {
                Mi25Context context = new Mi25Context();
                context.setInteractionId( interactionId );
                String dbList = buildDbList( identities );
                String msg = null;
                if( dbList.length() > 0 ) {
                    msg = "Found identity cross reference ("+ dbList +"), but none to an interaction database (child term of "+ INTERACTION_DATABASE_MI_REF +").";
                } else {
                    msg = "Found identity cross reference, but none to an interaction database (child term of "+ INTERACTION_DATABASE_MI_REF +").";
                }
                messages.add( new ValidatorMessage( msg ,
                                                    MessageLevel.ERROR,
                                                    context,
                                                    this ) );

                // TODO should we allow any other databases than interaction databases here ?
            }
        }

        return messages;
    }

    private String buildDbList( Collection<DbReference> identities ) {
        StringBuilder sb = new StringBuilder( 128 );
        for ( Iterator<DbReference> dbReferenceIterator = identities.iterator(); dbReferenceIterator.hasNext(); ) {
            DbReference ref = dbReferenceIterator.next();
            final String db = ref.getDb();
            final String dbAc = ref.getDbAc();
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
}
