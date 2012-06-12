package psidev.psi.mi.tab.converter.tab2graphml;

import psidev.psi.mi.tab.model.CrossReference;

import java.util.Comparator;

/**
 * Comparator that indicated if while sorting if matched any of the specified criteria.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.2
 */
public interface CrossReferenceComparator extends Comparator<CrossReference> {

    int compare(CrossReference cr1, CrossReference cr2);

    boolean hasMatchedAny();

    boolean matches(CrossReference cr);

}
