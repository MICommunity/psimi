package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.*;

/**
 * Container for both xrefs and identifiers  in a CvTerm
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "cvTermXrefContainer")
public class CvTermXrefContainer extends XrefContainer{

    private Xref miIdentifier;
    private Xref modIdentifier;
    private Xref parIdentifier;
    private List<Xref> allIdentifiers;

    @Override
    protected void processRemovedPrimaryRef(Xref removed) {
        if (!((FullIdentifierList)getAllIdentifiers()).removeOnly(this.primaryRef)){
            // if it is not an identifier
            ((FullXrefList)getAllXrefs()).removeOnly(this.primaryRef);
        }
        else {
            processRemovedIdentifierEvent(this.primaryRef);
        }
    }

    @Override
    protected void processAddedPrimaryRef() {
        if (XrefUtils.isXrefAnIdentifier(this.primaryRef)){
            ((FullIdentifierList)getAllIdentifiers()).addOnly(0, this.primaryRef);
            processAddedIdentifierEvent(this.primaryRef);
        }
        else {
            ((FullXrefList)getAllXrefs()).addOnly(0, this.primaryRef);
        }
    }

    public Collection<Xref> getAllIdentifiers() {
        if (allIdentifiers == null){
            allIdentifiers = new FullIdentifierList();
        }
        return allIdentifiers;
    }

    public String getMIIdentifier() {
        return this.miIdentifier != null ? this.miIdentifier.getId() : null;
    }

    public String getMODIdentifier() {
        return this.modIdentifier != null ? this.modIdentifier.getId() : null;
    }

    public String getPARIdentifier() {
        return this.parIdentifier != null ? this.parIdentifier.getId() : null;
    }

    public void setMIIdentifier(String mi) {
        FullIdentifierList cvTermIdentifiers = (FullIdentifierList)getAllIdentifiers();

        // add new mi if not null
        if (mi != null){
            CvTerm psiMiDatabase = CvTermUtils.createPsiMiDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove old psi mi if not null
            if (this.miIdentifier != null){
                cvTermIdentifiers.removeOnly(this.miIdentifier);
                if (this.miIdentifier instanceof XmlXref){
                    processRemovedPrimaryAndSecondaryRefs((XmlXref)this.miIdentifier);
                }
            }
            this.miIdentifier = new XmlXref(psiMiDatabase, mi, identityQualifier);
            cvTermIdentifiers.addOnly(this.miIdentifier);
            processAddedPrimaryAndSecondaryRefs((XmlXref)this.miIdentifier);
        }
        // remove all mi if the collection is not empty
        else if (!getAllIdentifiers().isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(getAllIdentifiers(), CvTerm.PSI_MI_MI, CvTerm.PSI_MI);
            this.miIdentifier = null;
        }
    }

    public void setMODIdentifier(String mod) {
        FullIdentifierList cvTermIdentifiers = (FullIdentifierList)getAllIdentifiers();

        // add new mod if not null
        if (mod != null){

            CvTerm psiModDatabase = CvTermUtils.createPsiModDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove old psi mod if not null
            if (this.modIdentifier != null){
                cvTermIdentifiers.removeOnly(this.modIdentifier);
                if (this.modIdentifier instanceof XmlXref){
                    processRemovedPrimaryAndSecondaryRefs((XmlXref)this.modIdentifier);
                }
            }
            this.modIdentifier = new XmlXref(psiModDatabase, mod, identityQualifier);
            cvTermIdentifiers.addOnly(this.modIdentifier);
            processAddedPrimaryAndSecondaryRefs((XmlXref)this.modIdentifier);
        }
        // remove all mod if the collection is not empty
        else if (!getAllIdentifiers().isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(getAllIdentifiers(), CvTerm.PSI_MOD_MI, CvTerm.PSI_MOD);
            this.modIdentifier = null;
        }
    }

    public void setPARIdentifier(String par) {
        FullIdentifierList cvTermIdentifiers = (FullIdentifierList)getAllIdentifiers();

        // add new mod if not null
        if (par != null){

            CvTerm psiModDatabase = CvTermUtils.createPsiParDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove old psi mod if not null
            if (this.parIdentifier != null){
                cvTermIdentifiers.removeOnly(this.parIdentifier);
                if (this.parIdentifier instanceof XmlXref){
                    processRemovedPrimaryAndSecondaryRefs((XmlXref)this.parIdentifier);
                }
            }
            this.parIdentifier = new XmlXref(psiModDatabase, par, identityQualifier);
            cvTermIdentifiers.addOnly(this.parIdentifier);
            processAddedPrimaryAndSecondaryRefs((XmlXref)this.parIdentifier);
        }
        // remove all mod if the collection is not empty
        else if (!getAllIdentifiers().isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(getAllIdentifiers(), null, CvTerm.PSI_PAR);
            this.parIdentifier = null;
        }
    }

    protected void processAddedIdentifierEvent(Xref added) {

        // the added identifier is psi-mi and it is not the current mi identifier
        if (miIdentifier != added && XrefUtils.isXrefFromDatabase(added, CvTerm.PSI_MI_MI, CvTerm.PSI_MI)){
            // the current psi-mi identifier is not identity, we may want to set miIdentifier
            if (!XrefUtils.doesXrefHaveQualifier(miIdentifier, Xref.IDENTITY_MI, Xref.IDENTITY)){
                // the miidentifier is not set, we can set the miidentifier
                if (miIdentifier == null){
                    miIdentifier = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    miIdentifier = added;
                }
                // the added xrefContainer is secondary object and the current mi is not a secondary object, we reset miidentifier
                else if (!XrefUtils.doesXrefHaveQualifier(miIdentifier, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    miIdentifier = added;
                }
            }
        }
        // the added identifier is psi-mod and it is not the current mod identifier
        else if (modIdentifier != added && XrefUtils.isXrefFromDatabase(added, CvTerm.PSI_MOD_MI, CvTerm.PSI_MOD)){
            // the current psi-mod identifier is not identity, we may want to set modIdentifier
            if (!XrefUtils.doesXrefHaveQualifier(modIdentifier, Xref.IDENTITY_MI, Xref.IDENTITY)){
                // the modIdentifier is not set, we can set the modIdentifier
                if (modIdentifier == null){
                    modIdentifier = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    modIdentifier = added;
                }
                // the added xrefContainer is secondary object and the current mi is not a secondary object, we reset miidentifier
                else if (!XrefUtils.doesXrefHaveQualifier(modIdentifier, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    modIdentifier = added;
                }
            }
        }
        // the added identifier is psi-par and it is not the current par identifier
        else if (parIdentifier != added && XrefUtils.isXrefFromDatabase(added, null, CvTerm.PSI_PAR)){
            // the current psi-par identifier is not identity, we may want to set parIdentifier
            if (!XrefUtils.doesXrefHaveQualifier(parIdentifier, Xref.IDENTITY_MI, Xref.IDENTITY)){
                // the parIdentifier is not set, we can set the parIdentifier
                if (parIdentifier == null){
                    parIdentifier = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    parIdentifier = added;
                }
                // the added xrefContainer is secondary object and the current par is not a secondary object, we reset paridentifier
                else if (!XrefUtils.doesXrefHaveQualifier(parIdentifier, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    parIdentifier = added;
                }
            }
        }
    }

    protected void processRemovedIdentifierEvent(Xref removed) {
        // the removed identifier is psi-mi
        if (miIdentifier != null && miIdentifier.equals(removed)){
            miIdentifier = (XmlXref)XrefUtils.collectFirstIdentifierWithDatabase(getAllIdentifiers(), CvTerm.PSI_MI_MI, CvTerm.PSI_MI);
        }
        // the removed identifier is psi-mod
        else if (modIdentifier != null && modIdentifier.equals(removed)){
            modIdentifier = (XmlXref)XrefUtils.collectFirstIdentifierWithDatabase(getAllIdentifiers(), CvTerm.PSI_MOD_MI, CvTerm.PSI_MOD);
        }
        // the removed identifier is psi-par
        else if (parIdentifier != null && parIdentifier.equals(removed)){
            parIdentifier = (XmlXref)XrefUtils.collectFirstIdentifierWithDatabase(getAllIdentifiers(), null, CvTerm.PSI_PAR);
        }
    }

    protected void clearPropertiesLinkedToIdentifiers() {
        miIdentifier = null;
        modIdentifier = null;
        parIdentifier = null;
    }

    private void processAddedPrimaryAndSecondaryRefs(XmlXref added) {
        if (primaryRef == null){
            primaryRef = added;
        }
        else{
            ((SecondaryXrefList)getJAXBSecondaryRefs()).addOnly(added);
        }
    }

    private void processRemovedPrimaryAndSecondaryRefs(XmlXref removed) {
        if (primaryRef != null && removed.equals(primaryRef)){
            if (!getJAXBSecondaryRefs().isEmpty()){
                primaryRef = secondaryRefs.iterator().next();
                ((SecondaryXrefList)secondaryRefs).removeOnly(primaryRef);
            }
            else{
                primaryRef = null;
            }
        }
    }

    @Override
    protected void processAddedSecondaryXref(Xref added) {
        // it is an identifier
        if (XrefUtils.isXrefAnIdentifier(added)){
            ((FullIdentifierList)getAllIdentifiers()).addOnly(added);
            processAddedIdentifierEvent(added);
        }
        // it is not an identifier
        else {
            ((FullXrefList)getAllXrefs()).addOnly(added);
        }
    }

    @Override
    protected void processRemovedSecondaryXref(Xref removed) {
        // if it is an identifier
        if (!((FullIdentifierList)getAllIdentifiers()).removeOnly(removed)){
            // if it is not an identifier
            ((FullXrefList)getAllXrefs()).removeOnly(removed);
        }
        else {
            processRemovedIdentifierEvent(removed);
        }
    }

    @Override
    protected void clearSecondaryXrefProperties() {
        if (primaryRef != null){
            Collection<Xref> primary = Collections.singleton(primaryRef);

            List<Xref> identifiersToBeDeleted = new ArrayList<Xref>(getAllIdentifiers());
            identifiersToBeDeleted.remove(primaryRef);

            for (Xref ref : identifiersToBeDeleted){
                ((FullIdentifierList)getAllIdentifiers()).removeOnly(ref);
                processRemovedIdentifierEvent(ref);
            }

            // if it is not an identifier
            ((FullXrefList)getAllXrefs()).retainAllOnly(primary);
        }
        else{
            ((FullXrefList)getAllXrefs()).clearOnly();

            List<Xref> identifiersToBeDeleted = new ArrayList<Xref>(getAllIdentifiers());
            for (Xref ref : identifiersToBeDeleted){
                ((FullIdentifierList)getAllIdentifiers()).removeOnly(ref);
                processRemovedIdentifierEvent(ref);
            }
        }
    }

    @Override
    protected void processAddedXref(Xref added) {
        if (added instanceof XmlXref){
            processAddedPrimaryAndSecondaryRefs((XmlXref)added);
        }
    }

    @Override
    protected void processRemovedXref(Xref removed) {
        if (removed instanceof XmlXref){
            processRemovedPrimaryAndSecondaryRefs((XmlXref)removed);
        }
    }

    @Override
    protected void clearFullXrefProperties() {
        // the primary ref is in identifiers
        if (getAllIdentifiers().contains(primaryRef)){
            // do nothing
        }
        // the primary ref is in xrefs, we reset it to the first identifier
        else if (!getAllIdentifiers().isEmpty()){
            Iterator<Xref> refsIterator = allIdentifiers.iterator();
            Xref ref = refsIterator.next();

            while(refsIterator.hasNext() && !(ref instanceof XmlXref)){
                ref = refsIterator.next();
            }
            if (ref instanceof XmlXref){
                primaryRef = (XmlXref) ref;
                ((FullIdentifierList)allIdentifiers).removeOnly(ref);
            }
        }
        else{
            primaryRef = null;
        }

        ((SecondaryXrefList)getJAXBSecondaryRefs()).retainAllOnly(getAllIdentifiers());
    }

    private class FullIdentifierList extends AbstractListHavingProperties<Xref> {
        public FullIdentifierList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Xref added) {
            if (added != null){
                if (added instanceof XmlXref){
                   processAddedPrimaryAndSecondaryRefs((XmlXref) added);
                }
                processAddedIdentifierEvent(added);
            }
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            if (removed != null){
                if (removed instanceof XmlXref){
                    processRemovedPrimaryAndSecondaryRefs((XmlXref)removed);
                }
                processRemovedIdentifierEvent(removed);
            }
        }

        @Override
        protected void clearProperties() {
            // the primary ref is in xrefs
            if (getAllXrefs().contains(primaryRef)){
                // do nothing
            }
            // the primary ref is in identifiers, we reset it to the first xrefContainer
            else if (!getAllXrefs().isEmpty()){
                Iterator<Xref> refsIterator = allXrefs.iterator();
                Xref ref = refsIterator.next();

                while(refsIterator.hasNext() && !(ref instanceof XmlXref)){
                    ref = refsIterator.next();
                }
                if (ref instanceof XmlXref){
                    primaryRef = (XmlXref) ref;
                    ((FullXrefList)allXrefs).removeOnly(ref);
                }
            }
            else{
                primaryRef = null;
            }
            ((SecondaryXrefList)getJAXBSecondaryRefs()).retainAllOnly(getAllXrefs());
            clearPropertiesLinkedToIdentifiers();
        }
    }
}
