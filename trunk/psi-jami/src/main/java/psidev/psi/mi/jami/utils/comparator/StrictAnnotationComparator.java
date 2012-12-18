package psidev.psi.mi.jami.utils.comparator;

/**
 * Strict annotation comparator
 *
 * - Two annotations which are null are equals
 * - The annotation which is not null is before null.
 * - use StrictCvTermComparator to compare the topics. If they are equals, compares the values (case insensitive)
 * - If both annotations have same topic, the one with a null value is always after the one with a non null value.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class StrictAnnotationComparator extends AnnotationComparator<StrictCvTermComparator> {
    @Override
    protected void instantiateAnnotationComparator() {
        this.topicComparator = new StrictCvTermComparator();
    }
}
