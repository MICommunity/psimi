package psidev.psi.mi.jami.enricher.listener;

/**
 * An enricher listener has enricher specific methods,
 * fired after the object has been changed and upon completion of the enrichment.
 *
 * @param <T>   The type of JAMI object being enriched.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  09/07/13
 */
public interface EnricherListener<T> {

    public void onEnrichmentComplete(T object , EnrichmentStatus status , String message);

}
