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

import org.junit.AfterClass;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.io.InputStream;
import java.util.Collection;

import static psidev.psi.mi.validator.extension.rules.RuleUtils.*;

/**
 * Abstract rule test.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 2.0
 */
public abstract class AbstractRuleTest {

    protected static OntologyManager ontologyMaganer;

    public AbstractRuleTest() {
        ontologyMaganer = new OntologyManager();
    }

    public AbstractRuleTest( InputStream ontologyCfgStream ) throws OntologyLoaderException {
        ontologyMaganer = new OntologyManager( ontologyCfgStream );
    }

    @AfterClass
    public static void cleanup() {
        ontologyMaganer = null;
    }

    protected void printMessages( Collection<ValidatorMessage> messages ) {
        for ( ValidatorMessage message : messages ) {
            System.out.println( message );
        }
    }

    protected ExperimentDescription buildExperiment( int taxid ) {
        final Bibref bibref = new Bibref();
        final Xref xref = new Xref();
        xref.setPrimaryRef( new DbReference( "pubmed", "MI:0446", "1234567", "primary-reference", "MI:0358" ) );
        bibref.setXref( xref );
        ExperimentDescription exp = new ExperimentDescription( bibref, new InteractionDetectionMethod() );
        exp.setId( 7 );
        final Organism organism = new Organism();
        organism.setNcbiTaxId( taxid );
        exp.getHostOrganisms().add( organism );
        return exp;
    }

    protected Interaction buildInteraction( InteractionDetectionMethod detection, ExperimentalRole... roles ) {

        Interaction interaction = new Interaction();
        interaction.setId( 1 );
        final ExperimentDescription exp = new ExperimentDescription();
        exp.setId( 2 );
        exp.setNames( new Names() );
        exp.getNames().setShortLabel( "gavin-2006" );

        exp.setInteractionDetectionMethod( buildDetectionMethod( "MI:0018", "2hybrid" ) );
        interaction.getExperiments().add( exp );

        int id = 100;

        for ( ExperimentalRole role : roles ) {

            final Participant p1 = new Participant();
            p1.setId( ( ++id ) );
            p1.setInteractor( buildProtein( "P12345" ) );
            p1.getExperimentalRoles().add( role );
            interaction.getParticipants().add( p1 );
        }

        return interaction;
    }

    protected Interaction buildInteractionDeterministic() {
        return buildInteraction( buildDetectionMethod( "MI:0018", "2hybrid" ),
                                 buildExperimentalRole( "MI:0496", "bait" ),
                                 buildExperimentalRole( "MI:0498", "prey" )
        );
    }

    protected Feature buildCertainFeature (long beginPosition, long endPosition){

        Feature feature = new Feature();

        Xref ref = new Xref(new DbReference("psi-mi", "MI:0488", "MI:0117", "identity", "MI:0356"));
        Names names = new Names();
        names.setShortLabel("binding site");

        FeatureType featureType = new FeatureType();
        featureType.setXref(ref);
        featureType.setNames(names);

        feature.setFeatureType(featureType);

        Xref refStart = new Xref(new DbReference("psi-mi", "MI:0488", "MI:0335", "identity", "MI:0356"));
        Names namesStart = new Names();
        names.setShortLabel("certain");
        RangeStatus start = new RangeStatus();
        start.setNames(namesStart);
        start.setXref(refStart);

        Xref refEnd = new Xref(new DbReference("psi-mi", "MI:0488", "MI:0335", "identity", "MI:0356"));
        Names namesEnd = new Names();
        names.setShortLabel("certain");
        RangeStatus end = new RangeStatus();
        end.setNames(namesEnd);
        end.setXref(refEnd);

        Range certain = new Range (start, new Position(beginPosition), end, new Position(endPosition));
        feature.getRanges().add(certain);

        return feature;
    }

    protected Feature buildUndeterminedFeature (){

        Feature feature = new Feature();

        Xref ref = new Xref(new DbReference("psi-mi", "MI:0488", "MI:0507", "identity", "MI:0356"));
        Names names = new Names();
        names.setShortLabel("tag");

        FeatureType featureType = new FeatureType();
        featureType.setXref(ref);
        featureType.setNames(names);

        feature.setFeatureType(featureType);

        Xref refStart = new Xref(new DbReference("psi-mi", "MI:0488", "MI:0339", "identity", "MI:0356"));
        Names namesStart = new Names();
        names.setShortLabel("undetermined");
        RangeStatus start = new RangeStatus();
        start.setNames(namesStart);
        start.setXref(refStart);

        Xref refEnd = new Xref(new DbReference("psi-mi", "MI:0488", "MI:0339", "identity", "MI:0356"));
        Names namesEnd = new Names();
        names.setShortLabel("undetermined");
        RangeStatus end = new RangeStatus();
        end.setNames(namesEnd);
        end.setXref(refEnd);

        Range undetermined = new Range (start, new Position(0), end, new Position(0));
        feature.getRanges().add(undetermined);

        return feature;
    }

    protected Interactor buildProtein( String uniprotAc ) {
        Interactor interactor = new Interactor();
        interactor.setXref( new Xref() );
        interactor.getXref().setPrimaryRef( new DbReference( UNIPROTKB, UNIPROTKB_MI_REF, uniprotAc, IDENTITY, IDENTITY_MI_REF ) );
        interactor.setNames( new Names() );
        interactor.getNames().setShortLabel( uniprotAc );
        interactor.setInteractorType( new InteractorType() );
        interactor.getInteractorType().setXref( new Xref( ) );
        interactor.getInteractorType().getXref().setPrimaryRef( new DbReference( PSI_MI, PSI_MI_REF, RuleUtils.PROTEIN_MI_REF, IDENTITY, IDENTITY_MI_REF ) );
        interactor.setSequence( "TEST" ); /// and yes ! TEST is a valid protein sequence ;) 
        return interactor;
    }

    protected InteractionDetectionMethod buildDetectionMethod( String mi, String name ) {
        final InteractionDetectionMethod detectionMethod = new InteractionDetectionMethod();
        detectionMethod.setXref( new Xref() );
        detectionMethod.getXref().setPrimaryRef( new DbReference( PSI_MI, PSI_MI_REF, mi, IDENTITY, IDENTITY_MI_REF ) );
        detectionMethod.setNames( new Names() );
        detectionMethod.getNames().setShortLabel( name );
        return detectionMethod;
    }

    protected ExperimentalRole buildExperimentalRole( String mi, String name ) {
        final ExperimentalRole role = new ExperimentalRole();
        role.setXref( new Xref() );
        role.getXref().setPrimaryRef( new DbReference( PSI_MI, PSI_MI_REF, mi, IDENTITY, IDENTITY_MI_REF ) );
        role.setNames( new Names() );
        role.getNames().setShortLabel( name );
        return role;
    }

    protected BiologicalRole buildBiologicalRole( String mi, String name ) {
        final BiologicalRole role = new BiologicalRole();
        role.setXref( new Xref() );
        role.getXref().setPrimaryRef( new DbReference( PSI_MI, PSI_MI_REF, mi, IDENTITY, IDENTITY_MI_REF ) );
        role.setNames( new Names() );
        role.getNames().setShortLabel( name );
        return role;
    }

    protected void updateInteractorType( Interactor interactor, String typeMiRef ) {
        interactor.getInteractorType().getXref().getPrimaryRef().setId( typeMiRef );
    }

    protected void updateInteractorIdentity( Interactor interactor, String dbRef, String id ) {
        if( ! interactor.hasXref() ) {
            interactor.setXref( new Xref( ) );
        }

        interactor.getXref().setPrimaryRef( new DbReference( "db", dbRef, id, IDENTITY, IDENTITY_MI_REF) );
    }

    protected void setDetectionMethod( Interaction interaction, String detectionMi, String detectionName ) {
        final ExperimentDescription exp = interaction.getExperiments().iterator().next();

        exp.setInteractionDetectionMethod( buildDetectionMethod( detectionMi, detectionName ) );
    }

}
