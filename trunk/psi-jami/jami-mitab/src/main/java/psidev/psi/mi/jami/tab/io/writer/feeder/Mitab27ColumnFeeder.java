package psidev.psi.mi.jami.tab.io.writer.feeder;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Participant;

import java.io.IOException;

/**
 * A MITAB 2.7 column feeder will write the content of MITAB 2.7 columns
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public interface Mitab27ColumnFeeder<T extends BinaryInteraction, P extends Participant> extends Mitab26ColumnFeeder<T, P>{

    /**
     * Writes participant features
     * @param participant
     */
    public void writeParticipantFeatures(P participant) throws IOException;

    /**
     * Writes participant stoichiometry
     * @param participant
     */
    public void writeParticipantStoichiometry(P participant) throws IOException;

    /**
     * Writes participant identification method(s)
     * @param participant
     */
    public void writeParticipantIdentificationMethod(P participant) throws IOException ;

    /**
     * Writes a feature
     * @param feature
     */
    public void writeFeature(Feature feature) throws IOException;
}
