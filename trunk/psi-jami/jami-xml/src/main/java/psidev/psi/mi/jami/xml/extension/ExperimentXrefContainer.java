package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Xref container for Experiment
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "experimentXrefContainer")
public class ExperimentXrefContainer extends XrefContainer{

    private Xref imexId;
    private Publication publication;

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
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

    @Override
    protected void initialiseXrefs() {
        super.initialiseXrefsWith(new FullXrefList());
    }

    ///////////////////////////// classes
    //////////////////////////////// private class

    private class FullXrefList extends AbstractListHavingProperties<Xref> {
        public FullXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Xref added) {
            processAddedPotentialImex(added);
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            processRemovedPotentialImex(removed);
        }

        @Override
        protected void clearProperties() {
            clearImexId();
        }
    }
}
