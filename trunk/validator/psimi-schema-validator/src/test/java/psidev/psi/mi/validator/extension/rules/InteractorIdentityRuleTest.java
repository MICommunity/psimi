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

import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.validator.extension.rules.InteractorIdentityRule;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Interactor;
import psidev.psi.mi.xml.model.Xref;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;

import static psidev.psi.mi.validator.extension.rules.RuleUtils.*;


import java.util.Collection;

import junit.framework.Assert;

/**
 * InteractorIdentityRule Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 2.0
 */
public class InteractorIdentityRuleTest extends AbstractRuleTest {

    public InteractorIdentityRuleTest() throws OntologyLoaderException {
        super( InteractorIdentityRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ) );
    }

    @Test
    public void check_smallmolecule_ok() throws Exception {
        final Interactor interactor = buildProtein( "P12345" );
        updateInteractorType( interactor, RuleUtils.SMALL_MOLECULE_MI_REF );
        updateInteractorIdentity( interactor, CHEBI_MI_REF, "CHEBI:00001" );
        InteractorIdentityRule rule = new InteractorIdentityRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interactor );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check_smallmolecule_fail() throws Exception {
        final Interactor interactor = buildProtein( "P12345" );
        updateInteractorType( interactor, RuleUtils.SMALL_MOLECULE_MI_REF );
        updateInteractorIdentity( interactor, UNIPROTKB_MI_REF, "CHEBI:0001" );
        InteractorIdentityRule rule = new InteractorIdentityRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interactor );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void check_protein_ok() throws Exception {
        final Interactor interactor = buildProtein( "P12345" );
        updateInteractorType( interactor, RuleUtils.PROTEIN_MI_REF );
        updateInteractorIdentity( interactor, UNIPROTKB_MI_REF, "P12345" );
        InteractorIdentityRule rule = new InteractorIdentityRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interactor );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void check_protein_fail() throws Exception {
        final Interactor interactor = buildProtein( "P12345" );
        updateInteractorType( interactor, RuleUtils.PROTEIN_MI_REF );
        updateInteractorIdentity( interactor, CHEBI_MI_REF, "CHEBI:0001" );
        InteractorIdentityRule rule = new InteractorIdentityRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interactor );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void check_protein_fail_no_identity() throws Exception {
        final Interactor interactor = buildProtein( "P12345" );
        updateInteractorType( interactor, RuleUtils.PROTEIN_MI_REF );
        updateInteractorIdentity( interactor, UNIPROTKB_MI_REF, "P12345" );

        interactor.getXref().getPrimaryRef().setRefTypeAc( null );
        interactor.getXref().getPrimaryRef().setRefType( null );

        InteractorIdentityRule rule = new InteractorIdentityRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interactor );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }
}