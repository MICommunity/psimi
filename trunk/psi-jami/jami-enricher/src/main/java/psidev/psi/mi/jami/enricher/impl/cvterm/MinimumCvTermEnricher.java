package psidev.psi.mi.jami.enricher.impl.cvterm;


import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.model.CvTerm;

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


    protected void processCvTerm(CvTerm cvTermToEnrich){

        //ShortName not checked - never null

        //FullName
        if(cvTermToEnrich.getFullName() == null
                && cvTermFetched.getFullName() != null){
            cvTermToEnrich.setFullName(cvTermFetched.getFullName());
            if (listener != null) listener.onFullNameUpdate(cvTermToEnrich, null);
        }

        //Identifiers
        /*Collection<Xref> subtractedIdentifiers = CollectionManipulationUtils.comparatorSubtract(
                cvTermFetched.getIdentifiers(),
                cvTermToEnrich.getIdentifiers(),
                new DefaultXrefComparator());

        for(Xref xrefIdentifier: subtractedIdentifiers){
            cvTermToEnrich.getIdentifiers().add(xrefIdentifier);
            if (listener != null) listener.onAddedIdentifier(cvTermToEnrich, xrefIdentifier);
        }  */
    }

}
