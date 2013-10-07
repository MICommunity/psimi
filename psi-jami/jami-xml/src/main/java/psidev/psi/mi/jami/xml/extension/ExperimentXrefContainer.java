package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;

import javax.xml.bind.annotation.*;
import java.util.*;

/**
 * Xref container for Experiment
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "")
public class ExperimentXrefContainer extends XrefContainer{

    private Xref imexId;
    private Publication publication;
    private List<Xref> allIdentifiers;

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    @Override
    protected void processRemovedPrimaryRef(XmlXref removed) {
        if (!((FullIdentifierList)getAllIdentifiers()).removeOnly(this.primaryRef)){
            // if it is not an identifier
            if (((FullXrefList)getAllXrefs()).removeOnly(this.primaryRef)){
                processRemovedPotentialImex(this.primaryRef);
            }
        }
    }

    @Override
    protected void processAddedPrimaryRef() {
        // identity or primary ref
        if (XrefUtils.isXrefAnIdentifier(this.primaryRef) || XrefUtils.doesXrefHaveQualifier(this.primaryRef, Xref.PRIMARY_MI, Xref.PRIMARY)){
            ((FullIdentifierList)getAllIdentifiers()).addOnly(0, this.primaryRef);
        }
        else {
            ((FullXrefList)getAllXrefs()).addOnly(0, this.primaryRef);
            processAddedPotentialImex(this.primaryRef);
        }
    }

    public Collection<Xref> getAllIdentifiers() {
        if (allIdentifiers == null){
            allIdentifiers = new FullIdentifierList();
        }
        return allIdentifiers;
    }

    protected void processAddedPotentialImex(Xref added) {
        if (publication != null){
            // the added identifier is imex and the current imex is not set
            if (publication.getImexId() == null && XrefUtils.isXrefFromDatabase(added, Xref.IMEX_MI, Xref.IMEX)){
                // the added xrefContainer is imex-primary
                if (XrefUtils.doesXrefHaveQualifier(added, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY)){
                    imexId = added;
                    publication.getXrefs().add(added);
                }
            }
        }
    }

    protected void processRemovedPotentialImex(Xref removed) {
        if (publication != null){
            // the removed identifier is pubmed
            if (imexId != null && imexId.equals(removed)){
                imexId = null;
                publication.getXrefs().remove(removed);
            }
        }
    }

    protected void clearImexId() {
        if (imexId != null){
            publication.getXrefs().remove(imexId);
        }
        imexId = null;
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
    protected void processAddedSecondaryXref(XmlXref added) {
        // it is an identifier
        if (XrefUtils.isXrefAnIdentifier(added) || XrefUtils.doesXrefHaveQualifier(this.primaryRef, Xref.PRIMARY_MI, Xref.PRIMARY)){
            ((FullIdentifierList)getAllIdentifiers()).addOnly(added);
        }
        // it is not an identifier
        else {
            ((FullXrefList)getAllXrefs()).addOnly(added);
            processAddedPotentialImex(added);
        }
    }

    @Override
    protected void processRemovedSecondaryXref(XmlXref removed) {
        // if it is an identifier
        if (!((FullIdentifierList)getAllIdentifiers()).removeOnly(removed)){
            // if it is not an identifier
            ((FullXrefList)getAllXrefs()).removeOnly(removed);
            processRemovedPotentialImex(removed);
        }
    }

    @Override
    protected void clearSecondaryXrefProperties() {
        if (primaryRef != null){
            Collection<XmlXref> primary = Collections.singleton(primaryRef);
            List<Xref> identifiersToBeDeleted = new ArrayList<Xref>(getAllXrefs());
            identifiersToBeDeleted.remove(primaryRef);
            for (Xref ref : identifiersToBeDeleted){
                ((FullXrefList)getAllXrefs()).removeOnly(ref);
                processRemovedPotentialImex(ref);
            }

            ((FullIdentifierList)getAllIdentifiers()).retainAllOnly(primary);
        }
        else{
            ((FullIdentifierList)getAllIdentifiers()).clearOnly();
            List<Xref> identifiersToBeDeleted = new ArrayList<Xref>(getAllXrefs());
            for (Xref ref : identifiersToBeDeleted){
                ((FullXrefList)getAllXrefs()).removeOnly(ref);
                processRemovedPotentialImex(ref);
            }
        }
    }

    @Override
    protected void processAddedXref(Xref added) {
        if (added instanceof XmlXref){
            processAddedPrimaryAndSecondaryRefs((XmlXref) added);
        }
        processAddedPotentialImex(added);
    }

    @Override
    protected void processRemovedXref(Xref removed) {
        if (removed instanceof XmlXref){
            processRemovedPrimaryAndSecondaryRefs((XmlXref) removed);
        }
        processRemovedPotentialImex(removed);
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
        clearImexId();
    }

    private class FullIdentifierList extends AbstractListHavingProperties<Xref> {
        public FullIdentifierList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Xref added) {
            if (added != null){
                if (added instanceof XmlXref){
                    processAddedPrimaryAndSecondaryRefs((XmlXref)added);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            if (removed != null){
                if (removed instanceof XmlXref){
                    processRemovedPrimaryAndSecondaryRefs((XmlXref)removed);
                }
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
        }
    }
}
