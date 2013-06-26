package psidev.psi.mi.jami.enricher.impl.protein;

import psidev.psi.mi.jami.bridges.uniprot.util.UniprotTranslationUtil;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.impl.organism.MaximumOrganismUpdater;
import psidev.psi.mi.jami.enricher.mockfetcher.organism.MockOrganismFetcher;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultXrefComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 20/05/13
 * Time: 09:42
 */
public class MaximumProteinUpdater
        extends MinimumProteinUpdater
        implements ProteinEnricher {



    @Override
    protected void processProtein(Protein proteinToEnrich){
        super.processProtein(proteinToEnrich);

        Set<Xref> fetchedXrefsToAdd = new TreeSet<Xref>(new DefaultXrefComparator());
        fetchedXrefsToAdd.addAll(proteinFetched.getXrefs());

        Set<CvTerm> xrefCvTerms = new TreeSet<CvTerm>(new DefaultCvTermComparator());
        xrefCvTerms.addAll(new UniprotTranslationUtil().getUniprotDatabases().values());
        //for(Xref xref : fetchedXrefsToAdd){
        //    if(! xrefCvTerms.contains(xref.getDatabase())) xrefCvTerms.add(xref.getDatabase());
        //}

        Collection<Xref> toRemoveXrefs = new ArrayList<Xref>();
        for(Xref xref : proteinToEnrich.getXrefs()){
            if(xref.getQualifier() == null
                    && ! fetchedXrefsToAdd.contains(xref)
                    && xrefCvTerms.contains(xref.getDatabase())){
                toRemoveXrefs.add(xref);
            }
            else if( fetchedXrefsToAdd.contains(xref)){
                fetchedXrefsToAdd.remove(xref);
            }
        }
        for(Xref xref: toRemoveXrefs){
            if(listener != null) listener.onRemovedXref(proteinToEnrich , xref);
            proteinToEnrich.getXrefs().remove(xref);
        }

        for(Xref xref: fetchedXrefsToAdd){
            if(listener != null) listener.onAddedXref(proteinToEnrich, xref);
            proteinToEnrich.getXrefs().add(xref);
        }

    }


    @Override
    public OrganismEnricher getOrganismEnricher() {
        if( organismEnricher == null ){
            organismEnricher = new MaximumOrganismUpdater();
            organismEnricher.setFetcher(new MockOrganismFetcher());
        }

        return organismEnricher;
    }
}
