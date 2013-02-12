package psidev.psi.mi.tab.model;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.NucleicAcid;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.util.List;

/**
 * Default MITAB interactor implementation for nucleic acids which is a patch for backward compatibility.
 * It only contains molecule information (not any participant information such as experimental role, etc.)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/02/13</pre>
 */

public class MitabNucleicAcid extends MitabInteractor implements NucleicAcid {

    private Xref ddbjEmblGenbank;
    private Xref refseq;
    private String sequence;

    public MitabNucleicAcid() {
        super(CvTermFactory.createMICvTerm(NucleicAcid.NULCEIC_ACID, NucleicAcid.NULCEIC_ACID_MI));
    }

    public MitabNucleicAcid(List<CrossReference> identifiers) {
        super(identifiers, CvTermFactory.createMICvTerm(NucleicAcid.NULCEIC_ACID, NucleicAcid.NULCEIC_ACID_MI));
    }

    public MitabNucleicAcid(CvTerm type) {
        super(type);
    }

    public MitabNucleicAcid(List<CrossReference> identifiers, CvTerm type) {
        super(identifiers, type);
    }

    public String getDdbjEmblGenbank() {
        return this.ddbjEmblGenbank != null ? this.ddbjEmblGenbank.getId() : null;
    }

    public void setDdbjEmblGenbank(String id) {
        // add new ddbj/embl/genbank if not null
        if (id != null){
            // first remove old ddbj/embl/genbank if not null
            if (this.ddbjEmblGenbank != null){
                identifiers.remove(this.ddbjEmblGenbank);
            }
            this.ddbjEmblGenbank = new CrossReferenceImpl(Xref.DDBJ_EMBL_GENBANK, id);
            this.identifiers.add(this.ddbjEmblGenbank);
        }
        // remove all ddbj/embl/genbank if the collection is not empty
        else if (!this.identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(this.identifiers, Xref.DDBJ_EMBL_GENBANK_MI, Xref.DDBJ_EMBL_GENBANK);
            this.ddbjEmblGenbank = null;
        }
    }

    public String getRefseq() {
        return this.refseq != null ? this.refseq.getId() : null;
    }

    public void setRefseq(String id) {
        // add new refseq if not null
        if (id != null){
            // first remove refseq if not null
            if (this.refseq!= null){
                identifiers.remove(this.refseq);
            }
            this.refseq = new CrossReferenceImpl(Xref.REFSEQ, id);
            this.identifiers.add(this.refseq);
        }
        // remove all ensembl genomes if the collection is not empty
        else if (!this.identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(this.identifiers, Xref.REFSEQ_MI, Xref.REFSEQ);
            this.refseq = null;
        }
    }

    public String getSequence() {
        return this.sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    @Override
    protected void processAddedIdentifierEvent(Xref added) {
        // the added identifier is ddbj
        if (ddbjEmblGenbank == null && XrefUtils.isXrefFromDatabase(added, Xref.DDBJ_EMBL_GENBANK_MI, Xref.DDBJ_EMBL_GENBANK)){
            this.ddbjEmblGenbank = added;
        }
        // the added identifier is refseq id and it is not the current refseq id
        else if (refseq != added && XrefUtils.isXrefFromDatabase(added, Xref.REFSEQ_MI, Xref.REFSEQ)){
            // the current refseq id is not identity, we may want to set refseq id
            this.refseq = added;
        }
    }

    @Override
    protected void processRemovedIdentifierEvent(Xref removed) {
        if (ddbjEmblGenbank != null && XrefUtils.isXrefFromDatabase(removed, Xref.DDBJ_EMBL_GENBANK_MI, Xref.DDBJ_EMBL_GENBANK)
                && removed.getId().equals(ddbjEmblGenbank.getId())){
            ddbjEmblGenbank = XrefUtils.collectFirstIdentifierWithDatabase(identifiers, Xref.DDBJ_EMBL_GENBANK_MI, Xref.DDBJ_EMBL_GENBANK);
        }
        else if (refseq != null && XrefUtils.isXrefFromDatabase(removed, Xref.REFSEQ_MI, Xref.REFSEQ)
                && removed.getId().equals(refseq.getId())){
            refseq = XrefUtils.collectFirstIdentifierWithDatabase(identifiers, Xref.REFSEQ_MI, Xref.REFSEQ);
        }
    }

    @Override
    protected void clearPropertiesLinkedToIdentifiers() {
        ddbjEmblGenbank = null;
        refseq = null;
    }
}
