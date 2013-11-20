package psidev.psi.mi.jami.xml.io.writer.elements;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Interaction;

import java.util.List;

/**
 * Interface for interaction writers writing extensions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/11/13</pre>
 */

public interface PsiXml25ExtendedInteractionWriter<T extends Interaction> extends PsiXml25InteractionWriter<T> {

    /**
     *
     * @param interaction
     * @return the default experiments for this interaction
     */
    public List<Experiment> extractDefaultExperimentsFrom(T interaction);


}
