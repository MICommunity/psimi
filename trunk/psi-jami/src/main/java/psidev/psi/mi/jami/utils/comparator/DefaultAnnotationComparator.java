package psidev.psi.mi.jami.utils.comparator;

/**
 * Default annotation comparator
 *
 * - Two annotations which are null are equals
 * - The annotation which is not null is before null.
 * - use DefaultCvTermComparator to compare the topics. If they are equals, compares the values (case insensitive)
 * - If both annotations have same topic, the one with a null value is always after the one with a non null value.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class DefaultAnnotationComparator extends AbstractAnnotationComparator<DefaultCvTermComparator> {
    @Override
    protected void instantiateAnnotationComparator() {
        this.topicComparator = new DefaultCvTermComparator();
    }
}
