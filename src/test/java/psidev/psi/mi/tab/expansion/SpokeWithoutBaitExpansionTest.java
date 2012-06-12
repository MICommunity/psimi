package psidev.psi.mi.tab.expansion;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import psidev.psi.mi.xml.model.Interactor;
import psidev.psi.mi.xml.model.Names;
import psidev.psi.mi.xml.model.Participant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * SpokeWithoutBaitExpansion Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version 1.0
 * @since <pre>01/09/2007</pre>
 */
public class SpokeWithoutBaitExpansionTest {

    private Participant buildParticipant( String shorlabel ) {
        Participant p = new Participant();
        Interactor i = new Interactor();
        p.setInteractor( i );
        i.setNames( new Names() );
        i.getNames().setShortLabel( shorlabel );
        return p;
    }

    @Test
    public void expand() {

        SpokeWithoutBaitExpansion expander = new SpokeWithoutBaitExpansion();
        Collection<Participant> participants = new ArrayList<Participant>();

        Participant p1 = buildParticipant( "A1111" );
        Participant p2 = buildParticipant( "B2222" );
        Participant p3 = buildParticipant( "C3333" );
        Participant p4 = buildParticipant( "D4444" );

        participants.add( p2 );
        participants.add( p1 );
        participants.add( p4 );
        participants.add( p3 );

        List<Participant> list = expander.sortParticipants( participants );

        assertEquals( p1, list.get( 0 ) );
        assertEquals( p2, list.get( 1 ) );
        assertEquals( p3, list.get( 2 ) );
        assertEquals( p4, list.get( 3 ) );
    }
}
