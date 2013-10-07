package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * XrefContainer for Nucleic Acid
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "")
public class NucleicAcidXrefContainer extends InteractorXrefContainer{

    private Xref ddbjEmblGenbank;
    private Xref refseq;

    /**
     * The first ddbjEmblGenbank if provided, then the first refseq identifier if provided, otherwise the first identifier in the list
     * @return
     */
    @Override
    public Xref getPreferredIdentifier() {
        return ddbjEmblGenbank != null ? ddbjEmblGenbank : (refseq != null ? refseq : super.getPreferredIdentifier());
    }

    public String getDdbjEmblGenbank() {
        return this.ddbjEmblGenbank != null ? this.ddbjEmblGenbank.getId() : null;
    }

    public void setDdbjEmblGenbank(String id) {
        FullIdentifierList nucleicAcidIdentifiers = (FullIdentifierList)getAllIdentifiers();

        // add new ddbj/embl/genbank if not null
        if (id != null){
            CvTerm ddbjEmblGenbankDatabase = CvTermUtils.createDdbjEmblGenbankDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove old ddbj/embl/genbank if not null
            if (this.ddbjEmblGenbank != null){
                nucleicAcidIdentifiers.removeOnly(this.ddbjEmblGenbank);
                if (this.ddbjEmblGenbank instanceof XmlXref){
                    processRemovedPrimaryAndSecondaryRefs((XmlXref)this.ddbjEmblGenbank);
                }
            }
            this.ddbjEmblGenbank = new XmlXref(ddbjEmblGenbankDatabase, id, identityQualifier);
            nucleicAcidIdentifiers.addOnly(this.ddbjEmblGenbank);
            processAddedPrimaryAndSecondaryRefs((XmlXref)this.ddbjEmblGenbank);
        }
        // remove all ddbj/embl/genbank if the collection is not empty
        else if (!nucleicAcidIdentifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(nucleicAcidIdentifiers, Xref.DDBJ_EMBL_GENBANK_MI, Xref.DDBJ_EMBL_GENBANK);
            this.ddbjEmblGenbank = null;
        }
    }

    public String getRefseq() {
        return this.refseq != null ? this.refseq.getId() : null;
    }

    public void setRefseq(String id) {
        FullIdentifierList nucleicAcidIdentifiers = (FullIdentifierList)getAllIdentifiers();
        // add new refseq if not null
        if (id != null){
            CvTerm refseqDatabase = CvTermUtils.createRefseqDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove refseq if not null
            if (this.refseq!= null){
                nucleicAcidIdentifiers.removeOnly(this.refseq);
                if (this.refseq instanceof XmlXref){
                    processRemovedPrimaryAndSecondaryRefs((XmlXref)this.refseq);
                }
            }
            this.refseq = new XmlXref(refseqDatabase, id, identityQualifier);
            nucleicAcidIdentifiers.addOnly(this.refseq);
            processAddedPrimaryAndSecondaryRefs((XmlXref)this.ddbjEmblGenbank);
        }
        // remove all ensembl genomes if the collection is not empty
        else if (!nucleicAcidIdentifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(nucleicAcidIdentifiers, Xref.REFSEQ_MI, Xref.REFSEQ);
            this.refseq = null;
        }
    }

    protected void processAddedBasicIdentifier(Xref added) {
        // the added identifier is ddbj/embl/genbank and it is not the current ddbj/embl/genbank identifier
        if (ddbjEmblGenbank != added && XrefUtils.isXrefFromDatabase(added, Xref.DDBJ_EMBL_GENBANK_MI, Xref.DDBJ_EMBL_GENBANK)){
            // the current ddbj/embl/genbank identifier is not identity, we may want to set ddbj/embl/genbank Identifier
            if (!XrefUtils.doesXrefHaveQualifier(ddbjEmblGenbank, Xref.IDENTITY_MI, Xref.IDENTITY)){
                // the ddbj/embl/genbank identifier is not set, we can set the ddbj/embl/genbank identifier
                if (ddbjEmblGenbank == null){
                    ddbjEmblGenbank = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    ddbjEmblGenbank = added;
                }
                // the added xref is secondary object and the current ddbj/embl/genbank identifier is not a secondary object, we reset ddbj/embl/genbank identifier
                else if (!XrefUtils.doesXrefHaveQualifier(ddbjEmblGenbank, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    ddbjEmblGenbank = added;
                }
            }
        }
        // the added identifier is refseq id and it is not the current refseq id
        else if (refseq != added && XrefUtils.isXrefFromDatabase(added, Xref.REFSEQ_MI, Xref.REFSEQ)){
            // the current refseq id is not identity, we may want to set refseq id
            if (!XrefUtils.doesXrefHaveQualifier(refseq, Xref.IDENTITY_MI, Xref.IDENTITY)){
                // the refseq id is not set, we can set the refseq id
                if (refseq == null){
                    refseq = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    refseq = added;
                }
                // the added xref is secondary object and the current refseq id is not a secondary object, we reset refseq id
                else if (!XrefUtils.doesXrefHaveQualifier(refseq, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    refseq = added;
                }
            }
        }
    }

    protected void processRemovedBasicIdentifier(Xref removed) {
        if (ddbjEmblGenbank != null && ddbjEmblGenbank.equals(removed)){
            ddbjEmblGenbank = XrefUtils.collectFirstIdentifierWithDatabase(getAllIdentifiers(), Xref.DDBJ_EMBL_GENBANK_MI, Xref.DDBJ_EMBL_GENBANK);
        }
        else if (refseq != null && refseq.equals(removed)){
            refseq = XrefUtils.collectFirstIdentifierWithDatabase(getAllIdentifiers(), Xref.REFSEQ_MI, Xref.REFSEQ);
        }
    }

    protected void clearPropertiesLinkedToBasicIdentifiers() {
        ddbjEmblGenbank = null;
        refseq = null;
    }

    @Override
    protected void processRemovedPrimaryRef(XmlXref removed) {
        if (!((FullIdentifierList)getAllIdentifiers()).removeOnly(this.primaryRef)){
            // if it is not an identifier
            ((FullXrefList)getAllXrefs()).removeOnly(this.primaryRef);
        }
        else {
            processRemovedBasicIdentifier(this.primaryRef);
        }
    }

    @Override
    protected void processAddedPrimaryRef() {
        if (XrefUtils.isXrefAnIdentifier(this.primaryRef)){
            ((FullIdentifierList)getAllIdentifiers()).addOnly(0, this.primaryRef);
            processAddedBasicIdentifier(this.primaryRef);
        }
        else {
            ((FullXrefList)getAllXrefs()).addOnly(0, this.primaryRef);
        }
    }

    @Override
    protected void processAddedIdentifier(Xref added) {
        super.processAddedIdentifier(added);
        processAddedBasicIdentifier(added);
    }

    @Override
    protected void processRemovedIdentifier(Xref removed) {
        super.processRemovedIdentifier(removed);
        processRemovedBasicIdentifier(removed);
    }

    @Override
    protected void clearFullIdentifiers() {
        super.clearFullIdentifiers();
        clearPropertiesLinkedToBasicIdentifiers();
    }
}
