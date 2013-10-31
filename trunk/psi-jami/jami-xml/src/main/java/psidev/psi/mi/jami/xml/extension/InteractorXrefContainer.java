package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;

import javax.xml.bind.annotation.*;
import java.util.*;

/**
 * Xref container for interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "interactorXrefContainer")
public class InteractorXrefContainer extends XrefContainer {

    private List<Xref> identifiers;

    @Override
    protected void processAddedPrimaryRef(Xref added) {
        if (XrefUtils.isXrefAnIdentifier(added)){
            getIdentifiers().add(added);
        }
        else {
            getXrefs().add(added);
        }
    }

    public Collection<Xref> getIdentifiers() {
        if (identifiers == null){
            initialiseIdentifiers();
        }
        return identifiers;
    }

    public Xref getPreferredIdentifier() {
        return !getIdentifiers().isEmpty() ? identifiers.iterator().next() : null;
    }

    protected void initialiseIdentifiers(){
        this.identifiers = new ArrayList<Xref>();
    }

    @Override
    protected void initialiseSecondaryRefs() {
        super.initialiseSecondaryResWith(new JAXBSecondaryXrefList());
    }


    ///////////////////////////// classes
    //////////////////////////////// private class
    private class JAXBSecondaryXrefList extends XrefContainer.JAXBSecondaryXrefList{

        private JAXBSecondaryXrefList() {
            if (identifiers == null){
                initialiseIdentifiers();
            }
        }

        protected boolean addXref(Integer index, Xref xref) {
            if (XrefUtils.isXrefAnIdentifier(xref)){
                return addIdentifier(index, xref);
            }
            else{
                return processXref(index, xref);
            }
        }
        protected boolean addIdentifier(Integer index, Xref xref) {
            if (index == null){
                return identifiers.add(xref);
            }
            identifiers.add(index, xref);
            return true;
        }

        private boolean processXref(Integer index, Xref xref) {
            if (index == null){
                return getXrefs().add(xref);
            }
            getXrefs().add(index, xref);
            return true;
        }
    }
}
