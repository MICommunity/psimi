package psidev.psi.mi.tab.converter.tab2graphml;

import com.google.common.collect.Lists;
import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.tab.model.Alias;
import psidev.psi.mi.tab.model.AliasImpl;

import java.util.Collections;
import java.util.List;

/**
 * AliasByTypeComparator tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.2
 */
public class AliasByTypeComparatorTest {
    @Test
    public void sort() throws Exception {
         List<Alias> aliases = Lists.newArrayList();
        aliases.add( new AliasImpl("uniprotkb", "G7B", "gene name synonym") );
        aliases.add( new AliasImpl("uniprotkb", "lolo", "locus name") );
        aliases.add( new AliasImpl("uniprotkb", "lala", "orf name") );
        aliases.add( new AliasImpl("uniprotkb", "Small nuclear ribonuclear protein D homolog", "gene name synonym") );
        aliases.add( new AliasImpl("uniprotkb", "C6orf28", "gene name synonym") );
		aliases.add( new AliasImpl("uniprotkb", "C6orf28", "display_short") );
		aliases.add( new AliasImpl("uniprotkb", "C6orf28", "display_long") );
		aliases.add( new AliasImpl("uniprotkb", "LSM2", "gene name") );

        final AliasComparator comparator = new AliasByTypeComparator();
        Collections.sort(aliases, comparator);

        Assert.assertTrue(comparator.hasMatchedAny());

		Assert.assertEquals( "display_short", aliases.get( 0 ).getAliasType() );
		Assert.assertEquals( "display_long", aliases.get( 1 ).getAliasType() );
		Assert.assertEquals( "gene name", aliases.get( 2 ).getAliasType() );
        Assert.assertEquals( "orf name", aliases.get( 3 ).getAliasType() );
        Assert.assertEquals( "locus name", aliases.get( 4 ).getAliasType() );
        Assert.assertEquals( "gene name synonym", aliases.get( 5 ).getAliasType() );
    }
}
