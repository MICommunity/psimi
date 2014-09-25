package psidev.psi.mi.xml;

import org.junit.Test;
import org.junit.Assert;
import psidev.psi.mi.xml.model.*;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * PsimiXmlWriter Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.7.6
 */
public class PsimiXmlWriterTest {
    @Test
    public void testWrite() throws Exception {

        final Organism human = PsiFactory.createOrganism( 9606, "human" );

        final InteractorType protein = PsiFactory.createCvType( InteractorType.class, "MI:xxxx", "protein" );
        final InteractionDetectionMethod detect = PsiFactory.createCvType( InteractionDetectionMethod.class, "MI:xxxx", "some method" );
        final ParticipantIdentificationMethod pdetect = PsiFactory.createCvType( ParticipantIdentificationMethod.class, "MI:xxxx", "protein" );
        final InteractionType iType = PsiFactory.createCvType( InteractionType.class, "MI:xxxx", "protein" );
        final ExperimentalRole bait = PsiFactory.createCvType( ExperimentalRole.class, "MI:xxxx", "bait" );
        final ExperimentalRole prey = PsiFactory.createCvType( ExperimentalRole.class, "MI:xxxx", "prey" );
        final BiologicalRole neutral = PsiFactory.createCvType( BiologicalRole.class, "MI:xxxx", "neutral" );

        final Interactor p1a = PsiFactory.createInteractor( "P1", "MI:0001", protein, human );
        final Interactor p1b = PsiFactory.createInteractor( "P1", "MI:0001", protein, human );
        final Interactor p2 = PsiFactory.createInteractor( "P2", "MI:0001", protein, human );
        final Interactor p3 = PsiFactory.createInteractor( "P3", "MI:0001", protein, human );

        final ExperimentDescription exp = PsiFactory.createExperiment( "kerrien-2009-1", "123", detect, pdetect, human );

        Collection<Participant> participants = new ArrayList<Participant>( );
        participants.add( PsiFactory.createParticipant( p1a, neutral, bait ) );
        participants.add( PsiFactory.createParticipant( p2, neutral, prey ) );
        final Interaction i1 = PsiFactory.createInteraction( "name", exp, iType, participants );

        Collection<Participant> participants2 = new ArrayList<Participant>( );
        participants2.add( PsiFactory.createParticipant( p1b, neutral, bait ) );
        participants2.add( PsiFactory.createParticipant( p3, neutral, prey ) );
        final Interaction i2 = PsiFactory.createInteraction( "name", exp, iType, participants2 );

        final Source source = PsiFactory.createSource( "us" );
        final Entry entry = PsiFactory.createEntry( source, Arrays.asList( i1, i2 ) );
        final EntrySet es = PsiFactory.createEntrySet( PsimiXmlVersion.VERSION_253, entry );

        PsimiXmlWriter writer = new PsimiXmlWriter( PsimiXmlVersion.VERSION_253, PsimiXmlForm.FORM_COMPACT );

        String xml = writer.getAsString( es );
        PsimiXmlReader reader= new PsimiXmlReader( PsimiXmlVersion.VERSION_253 );
        final EntrySet es2 = reader.read( xml );
        Assert.assertEquals( 3, es2.getEntries().iterator().next().getInteractors().size() );
        Assert.assertEquals( 2, es2.getEntries().iterator().next().getInteractions().size() );
        Assert.assertEquals( 1, es2.getEntries().iterator().next().getExperiments().size() );
    }
}
