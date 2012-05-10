package psidev.psi.mi.validator.extension.rules.dependencies;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

import static psidev.psi.mi.validator.extension.rules.RuleUtils.*;

/**
 * InteractionDetectionMethod2InteractionTypeDependencyRule Tester.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: InteractionDetectionMethod2InteractionTypeDependenciesRuleTest.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class InteractionDetectionMethod2InteractionTypeDependenciesRuleTest extends AbstractRuleTest {

    /**
     *
     * @throws OntologyLoaderException
     */
    public InteractionDetectionMethod2InteractionTypeDependenciesRuleTest() throws OntologyLoaderException {
        super( InteractionDetectionMethod2InteractionTypeDependenciesRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ) );
    }

    /**
     * Checks that a cross linking study is associated with an appropriate interaction type (for instance physical association when host = in vivo)
     * @throws Exception
     */
    @Test
    public void check_Cross_Linking_ok() throws Exception {
        Interaction interaction = new Interaction();
        interaction.setId( 1 );
        InteractionType type = new InteractionType();
        Xref ref = new Xref();

        ref.setPrimaryRef( new DbReference( PSI_MI, PSI_MI_REF, "MI:0915", IDENTITY, IDENTITY_MI_REF ) );

        Names name = new Names();

        name.setFullName("physical association");
        type.setNames(name);
        type.setXref(ref);
        interaction.getInteractionTypes().add(type);
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );
        Organism host = new Organism();

        // set the host organism
        host.setNcbiTaxId(-4);
        exp.getHostOrganisms().add(host);

        exp.setInteractionDetectionMethod( buildDetectionMethod( CROSS_LINKING_MI_REF, "cross-linking study" ) );
        interaction.getExperiments().add( exp );

        // Set the interaction detection method
        setDetectionMethod( interaction, CROSS_LINKING_MI_REF, "cross-linking study" );

        // set the role of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, BAIT_MI_REF, "bait" );
        addParticipant( interaction, PREY_MI_REF, "prey" );

        InteractionDetectionMethod2InteractionTypeDependencyRule rule =
                new InteractionDetectionMethod2InteractionTypeDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    /**
     * Checks that a warning message appears when a cross linking study is associated with an appropriate interaction type (for instance physical association) but the host organism
     * requirements are not respected (host != in vivo)
     * @throws Exception
     */
    @Test
    public void check_Cross_Linking_Wrong_HostOrganism() throws Exception {
        Interaction interaction = new Interaction();
        interaction.setId( 1 );
        InteractionType type = new InteractionType();
        Xref ref = new Xref();
        ref.setPrimaryRef( new DbReference( PSI_MI, PSI_MI_REF, "MI:0915", IDENTITY, IDENTITY_MI_REF ) );
        Names name = new Names();
        name.setFullName("physical association");
        type.setNames(name);
        type.setXref(ref);
        interaction.getInteractionTypes().add(type);
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );
        Organism host = new Organism();

        // set the host organism
        host.setNcbiTaxId(-2);
        exp.getHostOrganisms().add(host);

        exp.setInteractionDetectionMethod( buildDetectionMethod( CROSS_LINKING_MI_REF, "cross-linking study" ) );
        interaction.getExperiments().add( exp );

        // Set the interaction detection method
        setDetectionMethod( interaction, CROSS_LINKING_MI_REF, "cross-linking study" );

        // set the role of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, BAIT_MI_REF, "bait" );
        addParticipant( interaction, PREY_MI_REF, "prey" );

        InteractionDetectionMethod2InteractionTypeDependencyRule rule =
                new InteractionDetectionMethod2InteractionTypeDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        Assert.assertEquals( 1, messages.size() );
    }

    /**
     * Checks that a cross linking study is associated with an appropriate interaction type (for instance direct interaction with a number of participants of 2)
     * @throws Exception
     */
    @Test
    public void check_Cross_Linking_Direct_Association_MatchingHostOrganism() throws Exception {
        Interaction interaction = new Interaction();
        interaction.setId( 1 );
        InteractionType type = new InteractionType();
        Xref ref = new Xref();
        ref.setPrimaryRef( new DbReference( PSI_MI, PSI_MI_REF, "MI:0407", IDENTITY, IDENTITY_MI_REF ) );
        Names name = new Names();
        name.setFullName("direct interaction");
        type.setNames(name);
        type.setXref(ref);
        interaction.getInteractionTypes().add(type);
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );
        Organism host = new Organism();

        // set the host organism
        host.setNcbiTaxId(-1);
        exp.getHostOrganisms().add(host);

        exp.setInteractionDetectionMethod( buildDetectionMethod( CROSS_LINKING_MI_REF, "cross-linking study" ) );
        interaction.getExperiments().add( exp );

        // Set the interaction detection method
        setDetectionMethod( interaction, CROSS_LINKING_MI_REF, "cross-linking study" );

        // set the role of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, BAIT_MI_REF, "bait" );
        addParticipant( interaction, PREY_MI_REF, "prey" );

        InteractionDetectionMethod2InteractionTypeDependencyRule rule =
                new InteractionDetectionMethod2InteractionTypeDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    /**
     * Checks that a warning message appears when a cross linking study is associated with an appropriate interaction type (for instance direct interaction) but the number of participants
     * requirements are not respected (num != 2)
     * @throws Exception
     */
    @Test
    public void check_Cross_Linking_Direct_Association_NonMatchingNumberOfParticipants() throws Exception {
        Interaction interaction = new Interaction();
        interaction.setId( 1 );
        InteractionType type = new InteractionType();
        Xref ref = new Xref();
        ref.setPrimaryRef( new DbReference( PSI_MI, PSI_MI_REF, "MI:0407", IDENTITY, IDENTITY_MI_REF ) );
        Names name = new Names();
        name.setFullName("direct interaction");
        type.setNames(name);
        type.setXref(ref);
        interaction.getInteractionTypes().add(type);
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );
        Organism host = new Organism();

        // set the host organism
        host.setNcbiTaxId(-1);
        exp.getHostOrganisms().add(host);

        exp.setInteractionDetectionMethod( buildDetectionMethod( CROSS_LINKING_MI_REF, "cross-linking study" ) );
        interaction.getExperiments().add( exp );

        // Set the interaction detection method
        setDetectionMethod( interaction, CROSS_LINKING_MI_REF, "cross-linking study" );

        // set the role of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, BAIT_MI_REF, "bait" );

        InteractionDetectionMethod2InteractionTypeDependencyRule rule =
                new InteractionDetectionMethod2InteractionTypeDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    /**
     * Checks that the ribonuclease assay is associated with a valid interaction type (for instance rna cleavage)
     * @throws Exception
     */
    @Test
    public void check_ribonuclease_assay_rna_cleavage_ok() throws Exception {
        Interaction interaction = new Interaction();
        interaction.setId( 1 );
        InteractionType type = new InteractionType();
        Xref ref = new Xref();
        ref.setPrimaryRef( new DbReference( PSI_MI, PSI_MI_REF, "MI:0902", IDENTITY, IDENTITY_MI_REF ) );
        Names name = new Names();
        name.setFullName("rna cleavage");
        type.setNames(name);
        type.setXref(ref);
        interaction.getInteractionTypes().add(type);
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );
        Organism host = new Organism();

        // set the host organism
        host.setNcbiTaxId(-1);
        exp.getHostOrganisms().add(host);

        exp.setInteractionDetectionMethod( buildDetectionMethod( "MI:0920", "ribonuclease assay" ) );
        interaction.getExperiments().add( exp );

        // Set the interaction detection method
        setDetectionMethod( interaction, "MI:0920", "ribonuclease assay" );

        // set the role of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, BAIT_MI_REF, "bait" );

        InteractionDetectionMethod2InteractionTypeDependencyRule rule =
                new InteractionDetectionMethod2InteractionTypeDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    /**
     * Checks that no message appears when a children of rna cleavage is associated with an appropriate interaction detection method (for instance cross linking study)
     * @throws Exception
     */
    @Test
    public void check_ribonuclease_assay_rna_cleavage_children_warning() throws Exception {
        Interaction interaction = new Interaction();
        interaction.setId( 1 );
        InteractionType type = new InteractionType();
        Xref ref = new Xref();
        ref.setPrimaryRef( new DbReference( PSI_MI, PSI_MI_REF, "MI:0571", IDENTITY, IDENTITY_MI_REF ) );
        Names name = new Names();
        name.setFullName("mrna cleavage");
        type.setNames(name);
        type.setXref(ref);
        interaction.getInteractionTypes().add(type);
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );
        exp.setNames( new Names() );
        exp.getNames().setShortLabel( "gavin-2006" );
        Organism host = new Organism();

        // set the host organism
        host.setNcbiTaxId(-1);
        exp.getHostOrganisms().add(host);

        exp.setInteractionDetectionMethod( buildDetectionMethod( "MI:0920", "ribonuclease assay" ) );
        interaction.getExperiments().add( exp );

        // Set the interaction detection method
        setDetectionMethod( interaction, "MI:0920", "ribonuclease assay" );

        // set the role of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, BAIT_MI_REF, "bait" );

        InteractionDetectionMethod2InteractionTypeDependencyRule rule =
                new InteractionDetectionMethod2InteractionTypeDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    /**
     * Checks that a warning message appears when physical association is associated with an appropriate interaction detection method (for instance affinity chromatography)
     * but with an unexpected number of preys (>1)
     * @throws Exception
     */
    @Test
    public void check_chromatography_numberPrey_warning() throws Exception {
        Interaction interaction = new Interaction();
        interaction.setId( 1 );
        InteractionType type = new InteractionType();
        Xref ref = new Xref();
        ref.setPrimaryRef( new DbReference( PSI_MI, PSI_MI_REF, "MI:0915", IDENTITY, IDENTITY_MI_REF ) );
        Names name = new Names();
        name.setFullName("physical association");
        type.setNames(name);
        type.setXref(ref);
        interaction.getInteractionTypes().add(type);
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );
        Organism host = new Organism();

        // set the host organism
        host.setNcbiTaxId(-1);
        exp.getHostOrganisms().add(host);

        exp.setInteractionDetectionMethod( buildDetectionMethod( "MI:0004", "affinity chromatography" ) );
        interaction.getExperiments().add( exp );

        // Set the interaction detection method
        setDetectionMethod( interaction, "MI:0004", "affinity chromatography" );

        // set the role of the participants
        interaction.getParticipants().clear();
        addParticipant( interaction, BAIT_MI_REF, "bait" );
        addParticipant( interaction, PREY_MI_REF, "prey" );
        addParticipant( interaction, PREY_MI_REF, "prey" );

        InteractionDetectionMethod2InteractionTypeDependencyRule rule =
                new InteractionDetectionMethod2InteractionTypeDependencyRule( ontologyMaganer );
        final Collection<ValidatorMessage> messages = rule.check( interaction );
        Assert.assertNotNull( messages );
        System.out.println(messages);
        Assert.assertEquals( 1, messages.size() );
    }


    private void addParticipant( Interaction interaction,
                                 String expRoleMi, String expRoleName ) {

        final Participant participant = new Participant();
        participant.setInteractor( new Interactor());
        participant.getExperimentalRoles().clear();
        participant.getExperimentalRoles().add( buildExperimentalRole( expRoleMi, expRoleName ));
        interaction.getParticipants().add( participant );
    }
}
