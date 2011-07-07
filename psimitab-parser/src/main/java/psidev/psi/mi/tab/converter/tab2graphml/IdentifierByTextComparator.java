package psidev.psi.mi.tab.converter.tab2graphml;

import com.google.common.collect.Lists;
import psidev.psi.mi.tab.model.CrossReference;

import java.util.List;

/**
 * A comparator for <code>CrossReference</code> based on an ordered list of CrossReference's text.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.2
 */
public class IdentifierByTextComparator implements CrossReferenceComparator {

    private static List<String> DEFAULT_TEXT = Lists.newArrayList();
    static {
        DEFAULT_TEXT.add("display name");
        DEFAULT_TEXT.add("gene name");
        DEFAULT_TEXT.add("short label");
    }

    private boolean matchedAny = false;

    private List<String> texts = Lists.newArrayList();

    public IdentifierByTextComparator(List<String> databases) {
        this.texts = databases;
    }

    public IdentifierByTextComparator() {
        this(DEFAULT_TEXT);
    }

    ////////////////////
    // Comparator

    public int compare(CrossReference cr1, CrossReference cr2) {
        int idx1 = texts.indexOf(cr1.getText());
        int idx2 = texts.indexOf(cr2.getText());

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
            compare = idx1 - idx2; // relative to their position in the ordered list of text
        }

        return compare;
    }

    public boolean hasMatchedAny() {
        return matchedAny;
    }

    public boolean matches(CrossReference cr) {
        return texts.indexOf( cr.getText() ) != -1;
    }
}
