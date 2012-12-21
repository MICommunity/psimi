package psidev.psi.mi.jami.utils.comparator.annotation;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;
import psidev.psi.mi.jami.utils.comparator.annotation.AnnotationComparator;

/**
 * Comparator for collection of Annotations
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/12/12</pre>
 */

public class AnnotationsCollectionComparator extends CollectionComparator<Annotation> {
    /**
     * Creates a new annotation CollectionComparator. It requires a Comparator for the annotations in the Collection
     *
     * @param annotationComparator
     */
    public AnnotationsCollectionComparator(AnnotationComparator annotationComparator) {
        super(annotationComparator);
    }

    @Override
    public AnnotationComparator getObjectComparator() {
        return (AnnotationComparator) objectComparator;
    }
}
