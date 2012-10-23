package psidev.psi.mi.tab.converter.tab2graphml;

import com.google.common.collect.Lists;
import psidev.psi.mi.tab.model.CrossReference;

import java.util.List;

/**
 * A comparator for <code>CrossReference</code> based on a preference list of databases' names.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.2
 */
public class IdentifierByDatabaseComparator implements CrossReferenceComparator {

    private final static List<String> DEFAULT_DATABASES = Lists.newArrayList();
    static {
        DEFAULT_DATABASES.add("uniprotkb");
        DEFAULT_DATABASES.add("uniprot");
        DEFAULT_DATABASES.add("chebi");
        DEFAULT_DATABASES.add("genbank_protein_gi");
        DEFAULT_DATABASES.add("entrez gene/locuslink");
        DEFAULT_DATABASES.add("ensembl");

//        DEFAULT_DATABASES.add("intact");
//        DEFAULT_DATABASES.add("dip");
//        DEFAULT_DATABASES.add("chembl");
//        DEFAULT_DATABASES.add("innatedb");
//        DEFAULT_DATABASES.add("matrixdb");
//        DEFAULT_DATABASES.add("string");
    }

    private boolean matchedAny = false;

    private List<String> databases;

    public IdentifierByDatabaseComparator(List<String> databases) {
        if (databases == null) {
            throw new IllegalArgumentException("You must give a non null list of databases");
        }

        this.databases = databases;
    }

    public IdentifierByDatabaseComparator() {
        this( DEFAULT_DATABASES );
    }

    ////////////////////
    // Comparator

    public int compare(CrossReference cr1, CrossReference cr2) {
        int idx1 = databases.indexOf(cr1.getDatabase());
        int idx2 = databases.indexOf(cr2.getDatabase());

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

    public boolean matches( CrossReference cr ) {
        return databases.indexOf(cr.getDatabase() ) != -1;
    }
}
