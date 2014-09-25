package psidev.psi.mi.tab.converter.tab2graphml;

import com.google.common.collect.Lists;
import psidev.psi.mi.tab.model.Alias;

import java.util.List;

/**
 * A comparator for <code>Alias</code> based on a preference list of alias types.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.2
 */
public class AliasByTypeComparator implements AliasComparator {

    private static List<String> DEFAULT_ALIAS_TYPE = Lists.newArrayList();
    static {
        DEFAULT_ALIAS_TYPE.add("display_short");
		DEFAULT_ALIAS_TYPE.add("display_long");
		DEFAULT_ALIAS_TYPE.add("gene name");
        DEFAULT_ALIAS_TYPE.add("orf name");
        DEFAULT_ALIAS_TYPE.add("locus name");
        DEFAULT_ALIAS_TYPE.add("gene name synonym");
		DEFAULT_ALIAS_TYPE.add("chebi name");
		DEFAULT_ALIAS_TYPE.add("shortlabel");
		DEFAULT_ALIAS_TYPE.add("shortLabel");
		DEFAULT_ALIAS_TYPE.add("short label");


	}

    private List<String> aliasTypes = Lists.newArrayList();

    private boolean matchedAny = false;

    public AliasByTypeComparator(List<String> databases) {
        this.aliasTypes = databases;
    }

    public AliasByTypeComparator() {
        this(DEFAULT_ALIAS_TYPE);
    }

    ////////////////////
    // Comparator

    public int compare(Alias a1, Alias a2) {
        int idx1 = aliasTypes.indexOf(a1.getAliasType());
        int idx2 = aliasTypes.indexOf(a2.getAliasType());

        if( idx1 != -1 || idx2 != -1 ) {
            matchedAny = true;
        }

        int compare = 0;
        if (idx1 == -1 && idx2 == -1) {
            compare = 0;
        } else if (idx1 == -1) {
            compare = 1; // first is greater
        } else if (idx2 == -1) {
            compare = -1; // first is lower
        } else {
            compare = idx1 - idx2; // relative to their position in the ordered list of DBs
        }

        return compare;
    }

    public boolean hasMatchedAny() {
        return matchedAny;
    }

    public boolean matches(Alias a) {
        return aliasTypes.indexOf( a.getAliasType() ) != -1;
    }
}
