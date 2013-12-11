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
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.RangeUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.io.InputStream;
import java.util.Collection;

import static psidev.psi.mi.validator.extension.rules.RuleUtils.IDENTITY;
import static psidev.psi.mi.validator.extension.rules.RuleUtils.IDENTITY_MI_REF;

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

    protected Experiment buildExperiment( int taxid ) {
        final Publication bibref = new DefaultPublication(XrefUtils.createXrefWithQualifier("pubmed", "MI:0446", "1234567", "primary-reference", "MI:0358" ));
        Experiment exp = new DefaultExperiment( bibref);
        final Organism organism = new DefaultOrganism(taxid);
        exp.setHostOrganism(organism);
        return exp;
    }

    protected Organism buildOrganism( int taxid ) {

        final Organism organism = new DefaultOrganism(taxid);
        return organism;
    }

    protected InteractionEvidence buildInteraction( CvTerm detection, CvTerm role ) {

        InteractionEvidence interaction = new DefaultInteractionEvidence();
        final Experiment exp = new DefaultExperiment(new DefaultPublication());
        exp.setInteractionDetectionMethod( buildDetectionMethod( "MI:0018", "2hybrid" ) );
        interaction.setExperimentAndAddInteractionEvidence(exp);

        final ParticipantEvidence p1 = new DefaultParticipantEvidence(buildProtein( "P12345" ) );
        p1.setExperimentalRole(role);
        interaction.addParticipant(p1);

        return interaction;
    }

    protected InteractionEvidence buildInteractionDeterministic() {
        return buildInteraction( buildDetectionMethod("MI:0018", "2hybrid"),
                buildExperimentalRole( "MI:0496", "bait" )
        );
    }

    protected ParticipantEvidence buildParticipantDeterministic() {
        final ParticipantEvidence p1 = new DefaultParticipantEvidence(new DefaultProtein("test protein"));
        p1.setInteractor( buildProtein( "P12345" ) );
        p1.setExperimentalRole( buildExperimentalRole( "MI:0496", "bait" ) );
        p1.setBiologicalRole( buildBiologicalRole( "MI:0918", "donor"));

        return p1;
    }

    protected FeatureEvidence buildCertainFeature (long beginPosition, long endPosition){

        FeatureEvidence feature = new DefaultFeatureEvidence();

        CvTerm featureType = CvTermUtils.createMICvTerm("binding site", "MI:0117");

        feature.setType(featureType);

        Range certain = RangeUtils.createCertainRange(3);
        feature.getRanges().add(certain);

        return feature;
    }

    protected FeatureEvidence buildUndeterminedFeature () throws IllegalRangeException {

        FeatureEvidence feature = new DefaultFeatureEvidence();

        CvTerm featureType = CvTermUtils.createMICvTerm("tag", "MI:0507");
        feature.setType(featureType);
        feature.getRanges().add(RangeUtils.createRangeFromString("?-?"));

        return feature;
    }

    protected Protein buildProtein( String uniprotAc ) {
        Protein interactor = new DefaultProtein(uniprotAc);
        interactor.setUniprotkb(uniprotAc);
        interactor.setSequence("TEST"); /// and yes ! TEST is a valid protein sequence ;)
        return interactor;
    }

    protected BioactiveEntity buildSmallMolecule( String chebiAc ) {
        BioactiveEntity interactor = new DefaultBioactiveEntity(chebiAc);
        interactor.setChebi(chebiAc);
        return interactor;
    }

    protected NucleicAcid buildNucleicAcid( String acid ) {
        NucleicAcid interactor = new DefaultNucleicAcid(acid);
        interactor.setDdbjEmblGenbank(acid);
        return interactor;
    }

    protected NucleicAcid buildRibonucleicAcid( String acid ) {
        NucleicAcid interactor = new DefaultNucleicAcid(acid);
        interactor.setDdbjEmblGenbank(acid);
        interactor.setInteractorType(CvTermUtils.createMICvTerm("rna", RuleUtils.RNA_MI_REF));
        return interactor;
    }

    protected CvTerm buildDetectionMethod( String mi, String name ) {
        final CvTerm detectionMethod = CvTermUtils.createMICvTerm(name, mi);
        return detectionMethod;
    }

    protected CvTerm buildExperimentalRole( String mi, String name ) {
        final CvTerm role = CvTermUtils.createMICvTerm(name, mi);
        return role;
    }

    protected CvTerm buildBiologicalRole( String mi, String name ) {
        final CvTerm role = CvTermUtils.createMICvTerm(name, mi);
        return role;
    }

    protected void updateInteractorType( Interactor interactor, String typeMiRef ) {
        interactor.getInteractorType().setMIIdentifier(typeMiRef);
    }

    protected void updateInteractorIdentity( Interactor interactor, String dbRef, String id ) {
        interactor.getIdentifiers().add( XrefUtils.createXrefWithQualifier( "db", dbRef, id, IDENTITY, IDENTITY_MI_REF) );
    }

    protected void setDetectionMethod( InteractionEvidence interaction, String detectionMi, String detectionName ) {
        final Experiment exp = interaction.getExperiment();
        exp.setInteractionDetectionMethod( buildDetectionMethod( detectionMi, detectionName ) );
    }

}
