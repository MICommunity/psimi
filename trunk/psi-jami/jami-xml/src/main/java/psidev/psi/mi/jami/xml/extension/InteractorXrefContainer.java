package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.*;

/**
 * Xref container for interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "")
public class InteractorXrefContainer extends XrefContainer {

    private List<Xref> allIdentifiers;


    @Override
    protected void processRemovedPrimaryRef(XmlXref removed) {
        if (!((FullIdentifierList)getAllIdentifiers()).removeOnly(this.primaryRef)){
            // if it is not an identifier
            ((FullXrefList)getAllXrefs()).removeOnly(this.primaryRef);
        }
    }

    @Override
    protected void processAddedPrimaryRef() {
        if (XrefUtils.isXrefAnIdentifier(this.primaryRef)){
            ((FullIdentifierList)getAllIdentifiers()).addOnly(0, this.primaryRef);
        }
        else {
            ((FullXrefList)getAllXrefs()).addOnly(0, this.primaryRef);
        }
    }

    @XmlTransient
    public Collection<Xref> getAllIdentifiers() {
        if (allIdentifiers == null){
            initialiseIdentifiers();
        }
        return allIdentifiers;
    }

    @XmlTransient
    public Xref getPreferredIdentifier() {
        return !getAllIdentifiers().isEmpty() ? allIdentifiers.iterator().next() : null;
    }

    protected void initialiseXrefs(){
        this.allXrefs = new XrefContainer.FullXrefList();
    }

    protected void initialiseIdentifiers(){
        this.allIdentifiers = new FullIdentifierList();
    }

    private void processAddedPrimaryAndSecondaryRefs(XmlXref added) {
        if (primaryRef == null){
            primaryRef = added;
        }
        else{
            ((SecondaryXrefList)getSecondaryRefs()).addOnly(added);
        }
    }

    private void processRemovedPrimaryAndSecondaryRefs(XmlXref removed) {
        if (primaryRef != null && removed.equals(primaryRef)){
            if (!getSecondaryRefs().isEmpty()){
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
        if (XrefUtils.isXrefAnIdentifier(added)){
            ((FullIdentifierList)getAllIdentifiers()).addOnly(added);
        }
        // it is not an identifier
        else {
            ((FullXrefList)getAllXrefs()).addOnly(added);
        }
    }

    @Override
    protected void processRemovedSecondaryXref(XmlXref removed) {
        // if it is an identifier
        if (!((FullIdentifierList)getAllIdentifiers()).removeOnly(removed)){
            // if it is not an identifier
            ((FullXrefList)getAllXrefs()).removeOnly(removed);
        }
    }

    @Override
    protected void clearSecondaryXrefProperties() {
        if (primaryRef != null){
            Collection<XmlXref> primary = Collections.singleton(primaryRef);
            List<Xref> identifiersToBeDeleted = new ArrayList<Xref>(getAllIdentifiers());
            identifiersToBeDeleted.remove(primaryRef);
            for (Xref ref : identifiersToBeDeleted){
                ((FullIdentifierList)getAllIdentifiers()).removeOnly(ref);
            }
            if (!((FullIdentifierList)getAllIdentifiers()).retainAllOnly(primary)){
                // if it is not an identifier
                ((FullXrefList)getAllXrefs()).retainAllOnly(primary);
            }
            else {
                ((FullXrefList)getAllXrefs()).clearOnly();
            }
        }
        else{
            ((FullXrefList)getAllXrefs()).clearOnly();
            List<Xref> identifiersToBeDeleted = new ArrayList<Xref>(getAllIdentifiers());
            for (Xref ref : identifiersToBeDeleted){
                ((FullIdentifierList)getAllIdentifiers()).removeOnly(ref);
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

        ((SecondaryXrefList)getSecondaryRefs()).retainAllOnly(getAllIdentifiers());
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
            ((SecondaryXrefList)getSecondaryRefs()).retainAllOnly(getAllXrefs());
        }
    }
}
