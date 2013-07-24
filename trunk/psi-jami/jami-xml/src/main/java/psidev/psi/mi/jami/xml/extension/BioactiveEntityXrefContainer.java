package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Xref container for BioactiveEntity
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "")
public class BioactiveEntityXrefContainer extends InteractorXrefContainer{

    private Xref chebi;

    @Override
    /**
     * Return the first chebi identifier if provided, otherwise the first identifier in the list of identifiers
     */
    public Xref getPreferredIdentifier() {
        return chebi != null ? chebi : super.getPreferredIdentifier();
    }

    @XmlTransient
    public String getChebi() {
        return chebi != null ? chebi.getId() : null;
    }

    public void setChebi(String id) {
        FullIdentifierList bioactiveEntityIdentifiers = (FullIdentifierList)getAllIdentifiers();

        // add new chebi if not null
        if (id != null){
            CvTerm chebiDatabase = CvTermUtils.createChebiDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove old chebi if not null
            if (this.chebi != null){
                bioactiveEntityIdentifiers.removeOnly(this.chebi);
            }
            this.chebi = new DefaultXref(chebiDatabase, id, identityQualifier);
            bioactiveEntityIdentifiers.addOnly(this.chebi);
        }
        // remove all chebi if the collection is not empty
        else if (!bioactiveEntityIdentifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(bioactiveEntityIdentifiers, Xref.CHEBI_MI, Xref.CHEBI);
            this.chebi = null;
        }
    }

    protected void processAddedPotentialChebi(Xref added) {
        // the added identifier is chebi and it is not the current chebi identifier
        if (chebi != added && XrefUtils.isXrefFromDatabase(added, Xref.CHEBI_MI, Xref.CHEBI)){
            // the current chebi identifier is not identity, we may want to set chebiIdentifier
            if (!XrefUtils.doesXrefHaveQualifier(chebi, Xref.IDENTITY_MI, Xref.IDENTITY)){
                // the chebi identifier is not set, we can set the chebi identifier
                if (chebi == null){
                    chebi = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    chebi = added;
                }
                // the added xref is secondary object and the current chebi is not a secondary object, we reset chebi identifier
                else if (!XrefUtils.doesXrefHaveQualifier(chebi, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    chebi = added;
                }
            }
        }
    }

    protected void processRemovedPotentialChebi(Xref removed) {
        // the removed identifier is chebi
        if (chebi != null && chebi.equals(removed)){
            chebi = XrefUtils.collectFirstIdentifierWithDatabase(getAllIdentifiers(), Xref.CHEBI_MI, Xref.CHEBI);
        }
    }

    protected void clearChebiIdentifier() {
        chebi = null;
    }

    @Override
    protected void processRemovedPrimaryRef(XmlXref removed) {
        if (!((FullIdentifierList)getAllIdentifiers()).removeOnly(this.primaryRef)){
            // if it is not an identifier
            ((FullXrefList)getAllXrefs()).removeOnly(this.primaryRef);
        }
        else {
            processRemovedPotentialChebi(this.primaryRef);
        }
    }

    @Override
    protected void processAddedPrimaryRef() {
        if (XrefUtils.isXrefAnIdentifier(this.primaryRef)){
            ((FullIdentifierList)getAllIdentifiers()).addOnly(0, this.primaryRef);
            processRemovedPotentialChebi(this.primaryRef);
        }
        else {
            ((FullXrefList)getAllXrefs()).addOnly(0, this.primaryRef);
        }
    }

    @Override
    protected void processAddedIdentifier(Xref added) {
        super.processAddedIdentifier(added);
        processAddedPotentialChebi(added);
    }

    @Override
    protected void processRemovedIdentifier(Xref removed) {
        super.processRemovedIdentifier(removed);
        processRemovedPotentialChebi(removed);
    }

    @Override
    protected void clearFullIdentifiers() {
        super.clearFullIdentifiers();
        clearChebiIdentifier();
    }
}
