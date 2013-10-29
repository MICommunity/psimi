package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Xref container for Proteins
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "proteinXrefContainer")
public class ProteinXrefContainer extends InteractorXrefContainer{

    private Xref uniprotkb;
    private Xref refseq;

    /**
     * The first uniprokb if provided, then the first refseq identifier if provided, otherwise the first identifier in the list
     * @return
     */
    @Override
    public Xref getPreferredIdentifier() {
        return uniprotkb != null ? uniprotkb : (refseq != null ? refseq : super.getPreferredIdentifier());
    }

    public String getUniprotkb() {
        return this.uniprotkb != null ? this.uniprotkb.getId() : null;
    }

    public void setUniprotkb(String ac) {
        FullIdentifierList proteinIdentifiers = (FullIdentifierList)getAllIdentifiers();

        // add new uniprotkb if not null
        if (ac != null){
            CvTerm uniprotkbDatabase = CvTermUtils.createUniprotkbDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove old uniprotkb if not null
            if (this.uniprotkb != null){
                proteinIdentifiers.removeOnly(this.uniprotkb);
                if (this.uniprotkb instanceof XmlXref){
                    processRemovedPrimaryAndSecondaryRefs((XmlXref)this.uniprotkb);
                }
            }
            this.uniprotkb = new XmlXref(uniprotkbDatabase, ac, identityQualifier);
            proteinIdentifiers.addOnly(this.uniprotkb);
            processAddedPrimaryAndSecondaryRefs((XmlXref)this.uniprotkb);
        }
        // remove all uniprotkb if the collection is not empty
        else if (!proteinIdentifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(proteinIdentifiers, Xref.UNIPROTKB_MI, Xref.UNIPROTKB);
            this.uniprotkb = null;
        }
    }

    public String getRefseq() {
        return this.refseq != null ? this.refseq.getId() : null;
    }

    public void setRefseq(String ac) {
        FullIdentifierList proteinIdentifiers = (FullIdentifierList)getAllIdentifiers();

        // add new refseq if not null
        if (ac != null){
            CvTerm refseqDatabase = CvTermUtils.createRefseqDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove old refseq if not null
            if (this.refseq != null){
                proteinIdentifiers.removeOnly(this.refseq);
                if (this.refseq instanceof XmlXref){
                    processRemovedPrimaryAndSecondaryRefs((XmlXref)this.refseq);
                }
            }
            this.refseq = new XmlXref(refseqDatabase, ac, identityQualifier);
            proteinIdentifiers.addOnly(this.refseq);
            processAddedPrimaryAndSecondaryRefs((XmlXref)this.refseq);
        }
        // remove all refseq if the collection is not empty
        else if (!proteinIdentifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(proteinIdentifiers, Xref.REFSEQ_MI, Xref.REFSEQ);
            this.refseq = null;
        }
    }

    protected void processAddedBasicIdentifier(Xref added) {
        // the added identifier is uniprotkb and it is not the current uniprotkb identifier
        if (uniprotkb != added && XrefUtils.isXrefFromDatabase(added, Xref.UNIPROTKB_MI, Xref.UNIPROTKB)){
            // the current uniprotkb identifier is not identity, we may want to set uniprotkb Identifier
            if (!XrefUtils.doesXrefHaveQualifier(uniprotkb, Xref.IDENTITY_MI, Xref.IDENTITY)){
                // the uniprotkb identifier is not set, we can set the uniprotkb identifier
                if (uniprotkb == null){
                    uniprotkb = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    uniprotkb = added;
                }
                // the added xref is secondary object and the current uniprotkb identifier is not a secondary object, we reset uniprotkb identifier
                else if (!XrefUtils.doesXrefHaveQualifier(uniprotkb, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    uniprotkb = added;
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
        if (uniprotkb != null && uniprotkb.equals(removed)){
            uniprotkb = XrefUtils.collectFirstIdentifierWithDatabase(getAllIdentifiers(), Xref.UNIPROTKB_MI, Xref.UNIPROTKB);
        }
        else if (refseq != null && refseq.equals(removed)){
            refseq = XrefUtils.collectFirstIdentifierWithDatabase(getAllIdentifiers(), Xref.REFSEQ_MI, Xref.REFSEQ);
        }
    }

    protected void clearPropertiesLinkedToBasicIdentifiers() {
        uniprotkb = null;
        refseq = null;
    }

    @Override
    protected void processRemovedPrimaryRef(Xref removed) {
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
