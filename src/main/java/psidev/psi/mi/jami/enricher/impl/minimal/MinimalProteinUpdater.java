package psidev.psi.mi.jami.enricher.impl.minimal;


import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.bridges.mapper.ProteinMapper;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.AbstractInteractorUpdater;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.util.Collection;

/**
 * Updates a protein to the minimum level. As an updater, some data may be overwritten in the protein being enriched.
 * See description of minimal update in AbstractInteractorUpdater
 * If the protein remapper is not null and the enricher cannot find a uniprot identifier, it will remap to uniprot using the proteinMapper.
 * In case of dead uniprot entries, it will move the dead uniprot identifier to the xrefs and add a qualifier 'uniprot-removed-ac' and add a caution in the annotations.
 * If the remapping is successfull, it will remove any cautions left because of dead entry and set the uniprot identifier of the protein
 *
 * The protein fetcher is require to enrich proteins
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  13/06/13
 */
public class MinimalProteinUpdater extends AbstractInteractorUpdater<Protein> implements ProteinEnricher{
    public static final String UNIPROT_REMOVED_QUALIFIER = "uniprot-removed-ac";

    /**
     * The only constructor which forces the setting of the fetcher
     * If the cvTerm fetcher is null, an illegal state exception will be thrown at the next enrichment.
     * @param proteinFetcher    The protein fetcher to use.
     */
    public MinimalProteinUpdater(ProteinFetcher proteinFetcher) {
        super(new MinimalProteinEnricher(proteinFetcher));
    }

    protected void processDeadUniprotIdentity(Protein proteinToEnrich, String oldUniprot) {
        Collection<Xref> uniprotIdentities = XrefUtils.collectAllXrefsHavingDatabaseAndId(proteinToEnrich.getXrefs(), Xref.UNIPROTKB_MI, Xref.UNIPROTKB, oldUniprot);
        for (Xref uniprotIdentity : uniprotIdentities){
            proteinToEnrich.getXrefs().remove(uniprotIdentity);
            if(getListener() != null){
                getListener().onRemovedXref(proteinToEnrich, uniprotIdentity);
            }
            Xref removedIdentity = new DefaultXref(uniprotIdentity.getDatabase(), uniprotIdentity.getId(), UNIPROT_REMOVED_QUALIFIER);
            proteinToEnrich.getXrefs().add(removedIdentity);
            if(getListener() != null){
                getListener().onAddedXref(proteinToEnrich, removedIdentity);
            }
        }
    }

    @Override
    public Protein find(Protein objectToEnrich) throws EnricherException {
        String oldUniprot = objectToEnrich.getUniprotkb();
        Protein prot = getInteractorEnricher().find(objectToEnrich);
        if (prot == null && oldUniprot != null){
            processDeadUniprotIdentity(objectToEnrich, oldUniprot);
        }
        return prot;
    }

    public ProteinMapper getProteinMapper() {
        return ((ProteinEnricher)getInteractorEnricher()).getProteinMapper();
    }

    public void setProteinMapper(ProteinMapper mapper){
        ((MinimalProteinEnricher)getInteractorEnricher()).setProteinMapper(mapper);
    }
}
