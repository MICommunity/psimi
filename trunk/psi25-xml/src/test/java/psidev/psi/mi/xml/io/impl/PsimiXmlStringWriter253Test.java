package psidev.psi.mi.xml.io.impl;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.xml.model.*;

import java.util.Date;

/**
 * PsimiXmlStringWriter Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @since TODO artifact version
 * @version $Id$
 */
public class PsimiXmlStringWriter253Test {

    @Test
    public void source() throws Exception {
        Source source = new Source();
        source.setNames( new Names() );
        source.getNames().setShortLabel( "shortlabel" );
        source.getNames().setShortLabel( "Fullname" );
        source.setReleaseDate( new Date() );
        final Xref xref = new Xref( new DbReference( "MI:xxxx", "IntAct" ) );
        source.setBibref( new Bibref( ) );
        source.getBibref().setXref( xref );

        PsimiXmlStringWriter253 writer = new PsimiXmlStringWriter253();
        final String xml = writer.write( source );
        Assert.assertEquals( 225, xml.length() );
    }
}
