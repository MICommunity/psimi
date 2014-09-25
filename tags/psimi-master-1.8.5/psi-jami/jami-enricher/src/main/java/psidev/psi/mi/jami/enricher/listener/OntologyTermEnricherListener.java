package psidev.psi.mi.jami.enricher.listener;

import psidev.psi.mi.jami.listener.OntologyTermChangeListener;
import psidev.psi.mi.jami.model.OntologyTerm;

/**
 * An extension of the OntologyTermChangeListener
 * with specific methods related to the process of enriching.
 * Each method will be fired after the change has been made to the ontology term
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public interface OntologyTermEnricherListener
        extends CvTermEnricherListener<OntologyTerm>, OntologyTermChangeListener{

}
