package psidev.psi.mi.jami.enricher;

/**
 * An enricher for curated publications which can enrich either a single publication or a collection.
 * It must be initiated with a fetcher.
 * Sub enrichers:
 * - Source enricher
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  31/07/13
 */
public interface CuratedPublicationEnricher extends PublicationEnricher{

    public SourceEnricher getSourceEnricher();

    public void setSourceEnricher(SourceEnricher enricher);
}
