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

import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import static psidev.psi.mi.validator.extension.rules.RuleUtils.*;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Participant;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.Xref;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.ValidatorException;
import org.junit.Test;
import junit.framework.Assert;

import java.util.Collection;

/**
 * InteractionSourceIdentityXrefRule Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 2.0
 */
public class InteractionSourceIdentityXrefRuleTest extends AbstractRuleTest {

    public InteractionSourceIdentityXrefRuleTest() throws OntologyLoaderException {
        super( InteractionSourceIdentityXrefRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ));
    }

    @Test
    public void check_ok_1_identity() throws ValidatorException {
        final Interaction interaction = buildInteractionDeterministic();
        // remove Xrefs
        interaction.setXref( new Xref( ) );

        // add a valid one
        interaction.getXref().setPrimaryRef( new DbReference( INTACT, INTACT_MI_REF, "EBI-123456", IDENTITY, IDENTITY_MI_REF ) );

        InteractionSourceIdentityXrefRule rule = new InteractionSourceIdentityXrefRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        printMessages( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check_ok_2_identities() throws ValidatorException {
        final Interaction interaction = buildInteractionDeterministic();

        // add a valid one
        interaction.setXref( new Xref( ) );
        interaction.getXref().setPrimaryRef( new DbReference( INTACT, INTACT_MI_REF, "EBI-123456", IDENTITY, IDENTITY_MI_REF ) );
        interaction.getXref().getSecondaryRef().add( new DbReference( IMEX, IMEX_MI_REF, "IM-123", IDENTITY, IDENTITY_MI_REF ) );

        InteractionSourceIdentityXrefRule rule = new InteractionSourceIdentityXrefRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        printMessages( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check_ok_2_identities_1_correct() throws ValidatorException {
        final Interaction interaction = buildInteractionDeterministic();

        // add a valid one
        interaction.setXref( new Xref( ) );
        interaction.getXref().setPrimaryRef( new DbReference( INTACT, INTACT_MI_REF, "EBI-123456", IDENTITY, IDENTITY_MI_REF ) );
        interaction.getXref().getSecondaryRef().add( new DbReference( RESID, RESID_MI_REF, "xyz", IDENTITY, IDENTITY_MI_REF ) );

        InteractionSourceIdentityXrefRule rule = new InteractionSourceIdentityXrefRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        printMessages( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check_fail_1_incorrect_identity() throws ValidatorException {
        final Interaction interaction = buildInteractionDeterministic();

        // add a valid one
        interaction.setXref( new Xref( ) );
        interaction.getXref().setPrimaryRef( new DbReference( RESID, RESID_MI_REF, "xyz", IDENTITY, IDENTITY_MI_REF ) );

        InteractionSourceIdentityXrefRule rule = new InteractionSourceIdentityXrefRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        printMessages( messages );
        Assert.assertEquals( 1, messages.size() );
    }
}
