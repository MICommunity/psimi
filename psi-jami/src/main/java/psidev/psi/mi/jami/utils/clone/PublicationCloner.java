package psidev.psi.mi.jami.utils.clone;

import psidev.psi.mi.jami.model.Publication;

/**
 * Utility class for cloning publications
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class PublicationCloner {

    /***
     * This method will copy properties of publication source in publication target and will override all the other properties of Target publication.
     * This method will ignore experiments
     * @param source
     * @param target
     */
    public static void copyAndOverridePublicationProperties(Publication source, Publication target){
        if (source != null && target != null){
            target.setCurationDepth(source.getCurationDepth());
            target.setJournal(source.getJournal());
            target.setReleasedDate(source.getReleasedDate());
            target.setTitle(source.getTitle());
            target.setPublicationDate(source.getPublicationDate());

            // copy collections
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getIdentifiers().clear();
            target.getIdentifiers().addAll(source.getIdentifiers());
            target.getAuthors().clear();
            target.getAuthors().addAll(source.getAuthors());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
        }
    }
}
