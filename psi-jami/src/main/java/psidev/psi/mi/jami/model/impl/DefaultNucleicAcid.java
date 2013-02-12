package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.NucleicAcid;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactNucleicAcidComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

/**
 * Default implementation for NucleicAcid.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/02/13</pre>
 */

public class DefaultNucleicAcid extends DefaultInteractor implements NucleicAcid{

    private Xref ddbjEmblGenbank;
    private Xref refseq;
    private String sequence;

    public DefaultNucleicAcid(String name, CvTerm type) {
        super(name, type);
    }

    public DefaultNucleicAcid(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
    }

    public DefaultNucleicAcid(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
    }

    public DefaultNucleicAcid(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
    }

    public DefaultNucleicAcid(String name, CvTerm type, Xref uniqueId) {
        super(name, type, uniqueId);
    }

    public DefaultNucleicAcid(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public DefaultNucleicAcid(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public DefaultNucleicAcid(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type, organism, uniqueId);
    }

    public DefaultNucleicAcid(String name) {
        super(name, CvTermFactory.createMICvTerm(NULCEIC_ACID, NULCEIC_ACID_MI));
    }

    public DefaultNucleicAcid(String name, String fullName) {
        super(name, fullName, CvTermFactory.createMICvTerm(NULCEIC_ACID, NULCEIC_ACID_MI));
    }

    public DefaultNucleicAcid(String name, Organism organism) {
        super(name, CvTermFactory.createMICvTerm(NULCEIC_ACID, NULCEIC_ACID_MI), organism);
    }

    public DefaultNucleicAcid(String name, String fullName, Organism organism) {
        super(name, fullName, CvTermFactory.createMICvTerm(NULCEIC_ACID, NULCEIC_ACID_MI), organism);
    }

    public DefaultNucleicAcid(String name, Xref uniqueId) {
        super(name, CvTermFactory.createMICvTerm(NULCEIC_ACID, NULCEIC_ACID_MI), uniqueId);
    }

    public DefaultNucleicAcid(String name, String fullName, Xref uniqueId) {
        super(name, fullName, CvTermFactory.createMICvTerm(NULCEIC_ACID, NULCEIC_ACID_MI), uniqueId);
    }

    public DefaultNucleicAcid(String name, Organism organism, Xref uniqueId) {
        super(name, CvTermFactory.createMICvTerm(NULCEIC_ACID, NULCEIC_ACID_MI), organism, uniqueId);
    }

    public DefaultNucleicAcid(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, CvTermFactory.createMICvTerm(NULCEIC_ACID, NULCEIC_ACID_MI), organism, uniqueId);
    }

    public String getDdbjEmblGenbank() {
        return this.ddbjEmblGenbank != null ? this.ddbjEmblGenbank.getId() : null;
    }

    @Override
    protected void initializeIdentifiers() {
        this.identifiers = new NucleicAcidIdentifierList();
    }

    public void setDdbjEmblGenbank(String id) {
        // add new ddbj/embl/genbank if not null
        if (id != null){
            CvTerm ddbjEmblGenbankDatabase = CvTermFactory.createDdbjEmblGenbankDatabase();
            CvTerm identityQualifier = CvTermFactory.createIdentityQualifier();
            // first remove old ddbj/embl/genbank if not null
            if (this.ddbjEmblGenbank != null){
                identifiers.remove(this.ddbjEmblGenbank);
            }
            this.ddbjEmblGenbank = new DefaultXref(ddbjEmblGenbankDatabase, id, identityQualifier);
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
            CvTerm refseqDatabase = CvTermFactory.createRefseqDatabase();
            CvTerm identityQualifier = CvTermFactory.createIdentityQualifier();
            // first remove refseq if not null
            if (this.refseq!= null){
                identifiers.remove(this.refseq);
            }
            this.refseq = new DefaultXref(refseqDatabase, id, identityQualifier);
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
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof NucleicAcid)){
            return false;
        }

        // use UnambiguousExactNucleicAcid comparator for equals
        return UnambiguousExactNucleicAcidComparator.areEquals(this, (NucleicAcid) o);
    }

    @Override
    public String toString() {
        return ddbjEmblGenbank != null ? ddbjEmblGenbank.getId() : (refseq != null ? refseq.getId() : super.toString());
    }

    private class NucleicAcidIdentifierList extends AbstractListHavingPoperties<Xref> {
        public NucleicAcidIdentifierList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Xref added) {
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

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            if (ddbjEmblGenbank == removed){
                ddbjEmblGenbank = XrefUtils.collectFirstIdentifierWithDatabase(this, Xref.DDBJ_EMBL_GENBANK_MI, Xref.DDBJ_EMBL_GENBANK);
            }
            else if (refseq == removed){
                refseq = XrefUtils.collectFirstIdentifierWithDatabase(this, Xref.REFSEQ_MI, Xref.REFSEQ);
            }
        }

        @Override
        protected void clearProperties() {
            ddbjEmblGenbank = null;
            refseq = null;
        }
    }
}
