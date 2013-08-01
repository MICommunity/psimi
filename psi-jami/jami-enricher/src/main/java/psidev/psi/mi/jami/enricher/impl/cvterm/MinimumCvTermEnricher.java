package psidev.psi.mi.jami.enricher.impl.cvterm;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.util.XrefUpdateMerger;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

/**
 * Provides maximum enrichment of the CvTerm.
 * Will enrich the full name if it is null and the identifiers.
 * As an enricher, no values from the provided CvTerm to enrich will be changed.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/05/13
 */
public class MinimumCvTermEnricher
        extends AbstractCvTermEnricher
        implements CvTermEnricher {

    protected static final Logger log = LoggerFactory.getLogger(MinimumCvTermEnricher.class.getName());

    public MinimumCvTermEnricher(CvTermFetcher cvTermFetcher) {
        super(cvTermFetcher);
    }

    protected void processCvTerm(CvTerm cvTermToEnrich){

        //ShortName not checked - never null

        //FullName
        if(cvTermToEnrich.getFullName() == null
                && cvTermFetched.getFullName() != null){

            cvTermToEnrich.setFullName(cvTermFetched.getFullName());
            if (getCvTermEnricherListener() != null)
                getCvTermEnricherListener().onFullNameUpdate(cvTermToEnrich, null);
        }

        //Identifiers
        if(! cvTermFetched.getIdentifiers().isEmpty()) {

            XrefUpdateMerger merger = new XrefUpdateMerger();
            merger.merge(cvTermFetched.getIdentifiers() , cvTermToEnrich.getIdentifiers(), true);

            for(Xref xref: merger.getToAdd()){
                cvTermToEnrich.getIdentifiers().add(xref);
                if(getCvTermEnricherListener() != null)
                    getCvTermEnricherListener().onAddedIdentifier(cvTermToEnrich, xref);
            }
        }
    }

}
