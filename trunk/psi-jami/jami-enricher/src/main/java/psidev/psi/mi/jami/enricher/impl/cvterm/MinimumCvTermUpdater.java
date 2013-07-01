package psidev.psi.mi.jami.enricher.impl.cvterm;


import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.model.CvTerm;


/**
 * Provides minimum updating of the CvTerm.
 * Will update the short name and full name of CvTerm to enrich.
 * As an updater, values from the provided CvTerm to enrich may be overwritten.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 */
public class MinimumCvTermUpdater
        extends AbstractCvTermEnricher
        implements CvTermEnricher{

    @Override
    protected void processCvTerm(CvTerm cvTermToEnrich){

        if(cvTermFetched.getShortName() != null
                && ! cvTermFetched.getShortName().equalsIgnoreCase(cvTermToEnrich.getShortName())){
            if (listener != null) listener.onShortNameUpdate(cvTermToEnrich, cvTermToEnrich.getShortName());
            cvTermToEnrich.setShortName(cvTermFetched.getShortName());

        }

        //FullName
        if(cvTermFetched.getFullName() != null
                && ! cvTermFetched.getFullName().equalsIgnoreCase(cvTermToEnrich.getFullName())){
            if (listener != null) listener.onFullNameUpdate(cvTermToEnrich, cvTermToEnrich.getFullName());
            cvTermToEnrich.setFullName(cvTermFetched.getFullName());
        }

        //TOdo - allow comparison of idneitifers
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
