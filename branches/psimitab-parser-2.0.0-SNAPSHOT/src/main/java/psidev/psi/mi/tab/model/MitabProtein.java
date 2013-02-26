package psidev.psi.mi.tab.model;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.model.impl.DefaultChecksum;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.util.Collection;
import java.util.List;

/**
 * Default MITAB interactor implementation for proteins which is a patch for backward compatibility.
 * It only contains molecule information (not any participant information such as experimental role, etc.)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/02/13</pre>
 */

public class MitabProtein extends MitabInteractor implements Protein {
    private Xref uniprotkb;
    private Xref refseq;
    private psidev.psi.mi.jami.model.Alias geneName;
    private Checksum rogid;
    private String sequence;

    public MitabProtein() {
        super(CvTermFactory.createMICvTerm(Protein.PROTEIN, Protein.PROTEIN_MI));
    }

    public MitabProtein(List<CrossReference> identifiers) {
        super(identifiers, CvTermFactory.createMICvTerm(Protein.PROTEIN, Protein.PROTEIN_MI));
    }

    public MitabProtein(CvTerm type) {
        super(type);
    }

    public MitabProtein(List<CrossReference> identifiers, CvTerm type) {
        super(identifiers, type);
    }

    public String getUniprotkb() {
        return this.uniprotkb != null ? this.uniprotkb.getId() : null;
    }

    public void setUniprotkb(String ac) {
        Collection<Xref> proteinIdentifiers = getIdentifiers();

        // add new uniprotkb if not null
        if (ac != null){
            CvTerm uniprotkbDatabase = CvTermFactory.createUniprotkbDatabase();
            CvTerm identityQualifier = CvTermFactory.createIdentityQualifier();
            // first remove old uniprotkb if not null
            if (this.uniprotkb != null){
                proteinIdentifiers.remove(this.uniprotkb);
            }
            this.uniprotkb = new DefaultXref(uniprotkbDatabase, ac, identityQualifier);
            proteinIdentifiers.add(this.uniprotkb);
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
        Collection<Xref> proteinIdentifiers = getIdentifiers();

        // add new refseq if not null
        if (ac != null){
            CvTerm refseqDatabase = CvTermFactory.createRefseqDatabase();
            CvTerm identityQualifier = CvTermFactory.createIdentityQualifier();
            // first remove old refseq if not null
            if (this.refseq != null){
                proteinIdentifiers.remove(this.refseq);
            }
            this.refseq = new DefaultXref(refseqDatabase, ac, identityQualifier);
            proteinIdentifiers.add(this.refseq);
        }
        // remove all refseq if the collection is not empty
        else if (!proteinIdentifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(proteinIdentifiers, Xref.REFSEQ_MI, Xref.REFSEQ);
            this.refseq = null;
        }
    }

    public String getGeneName() {
        return this.geneName != null ? this.geneName.getName() : null;
    }

    public void setGeneName(String name) {
        Collection<Alias> proteinAliases = getAliases();

        // add new gene name if not null
        if (name != null){
            CvTerm geneNameType = CvTermFactory.createGeneNameAliasType();
            // first remove old gene name if not null
            if (this.geneName != null){
                proteinAliases.remove(this.geneName);
            }
            this.geneName = new DefaultAlias(geneNameType, name);
            proteinAliases.add(this.geneName);
        }
        // remove all gene names if the collection is not empty
        else if (!proteinAliases.isEmpty()) {
            AliasUtils.removeAllAliasesWithType(proteinAliases, psidev.psi.mi.jami.model.Alias.GENE_NAME_MI, Alias.GENE_NAME);
            this.geneName = null;
        }
    }

    public String getRogid() {
        return this.rogid != null ? this.rogid.getValue() : null;
    }

    public void setRogid(String rogid) {
        Collection<Checksum> checksums = getChecksums();

        if (rogid != null){
            CvTerm rogidMethod = CvTermFactory.createRogid();
            // first remove old rogid
            if (this.rogid != null){
                checksums.remove(this.rogid);
            }
            this.rogid = new DefaultChecksum(rogidMethod, rogid);
            checksums.add(this.rogid);
        }
        // remove all smiles if the collection is not empty
        else if (!checksums.isEmpty()) {
            ChecksumUtils.removeAllChecksumWithMethod(checksums, Checksum.ROGID_MI, Checksum.ROGID);
            this.rogid = null;
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
        if (uniprotkb == null && XrefUtils.isXrefFromDatabase(added, Xref.UNIPROTKB_MI, Xref.UNIPROTKB)){
            uniprotkb = added;
        }
        else if (refseq == null && XrefUtils.isXrefFromDatabase(added, Xref.REFSEQ_MI, Xref.REFSEQ)){
            refseq = added;
        }
    }
    @Override
    protected void processRemovedIdentifierEvent(Xref removed) {
        if (uniprotkb != null && XrefUtils.isXrefFromDatabase(removed, Xref.UNIPROTKB_MI, Xref.UNIPROTKB) && removed.getId().equals(uniprotkb.getId())){
            uniprotkb = XrefUtils.collectFirstIdentifierWithDatabase(getIdentifiers(), Xref.UNIPROTKB_MI, Xref.UNIPROTKB);
        }
        else if (refseq != null && XrefUtils.isXrefFromDatabase(removed, Xref.REFSEQ_MI, Xref.REFSEQ) && removed.getId().equals(refseq.getId())){
            refseq = XrefUtils.collectFirstIdentifierWithDatabase(getIdentifiers(), Xref.REFSEQ_MI, Xref.REFSEQ);
        }
    }

    @Override
    protected void clearPropertiesLinkedToIdentifiers() {
        uniprotkb = null;
        refseq = null;
    }

    @Override
    protected void processAddedChecksumEvent(psidev.psi.mi.jami.model.Checksum added) {
        if (rogid == null && ChecksumUtils.doesChecksumHaveMethod(added, Checksum.ROGID_MI, Checksum.ROGID)){
            rogid = added;
        }
    }

    @Override
    protected void processRemovedChecksumEvent(psidev.psi.mi.jami.model.Checksum removed) {
        if (rogid != null && rogid.getValue().equals(removed.getValue())
                && ChecksumUtils.doesChecksumHaveMethod(removed, Checksum.ROGID_MI, Checksum.ROGID)){
            rogid = ChecksumUtils.collectFirstChecksumWithMethod(getChecksums(), Checksum.ROGID_MI, Checksum.ROGID);
        }
    }

    @Override
    protected void clearPropertiesLinkedToChecksum() {
        rogid = null;
    }

    @Override
    protected void processAddedAliasEvent(Alias added) {
        if (geneName == null && AliasUtils.doesAliasHaveType(added, Alias.GENE_NAME_MI, Alias.GENE_NAME)){
            geneName = added;
        }
    }

    @Override
    protected void processRemovedAliasEvent(psidev.psi.mi.jami.model.Alias removed) {
        if (geneName != null && geneName.getName().equals(removed.getName())
                && AliasUtils.doesAliasHaveType(removed, Alias.GENE_NAME_MI, Alias.GENE_NAME)){
            geneName = AliasUtils.collectFirstAliasWithType(getAliases(), Alias.GENE_NAME_MI, Alias.GENE_NAME);
        }
    }

    @Override
    protected void clearPropertiesLinkedToAliases() {
        geneName = null;
    }
}
