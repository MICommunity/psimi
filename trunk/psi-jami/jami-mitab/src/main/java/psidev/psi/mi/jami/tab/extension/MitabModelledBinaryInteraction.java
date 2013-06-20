package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.binary.impl.DefaultModelledBinaryInteraction;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledParticipant;

/**
 * A MitabBinaryInteraction is a modelled binaryInteraction with a MitabSourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class MitabModelledBinaryInteraction extends DefaultModelledBinaryInteraction implements FileSourceContext {

    private FileSourceLocator sourceLocator;

    public MitabModelledBinaryInteraction() {
        super();
    }

    public MitabModelledBinaryInteraction(String shortName) {
        super(shortName);
    }

    public MitabModelledBinaryInteraction(String shortName, CvTerm type) {
        super(shortName, type);
    }

    public MitabModelledBinaryInteraction(ModelledParticipant participantA, ModelledParticipant participantB) {
        super(participantA, participantB);
    }

    public MitabModelledBinaryInteraction(String shortName, ModelledParticipant participantA, ModelledParticipant participantB) {
        super(shortName, participantA, participantB);
    }

    public MitabModelledBinaryInteraction(String shortName, CvTerm type, ModelledParticipant participantA, ModelledParticipant participantB) {
        super(shortName, type, participantA, participantB);
    }

    public MitabModelledBinaryInteraction(CvTerm complexExpansion) {
        super(complexExpansion);
    }

    public MitabModelledBinaryInteraction(String shortName, CvTerm type, CvTerm complexExpansion) {
        super(shortName, type, complexExpansion);
    }

    public MitabModelledBinaryInteraction(ModelledParticipant participantA, ModelledParticipant participantB, CvTerm complexExpansion) {
        super(participantA, participantB, complexExpansion);
    }

    public MitabModelledBinaryInteraction(String shortName, ModelledParticipant participantA, ModelledParticipant participantB, CvTerm complexExpansion) {
        super(shortName, participantA, participantB, complexExpansion);
    }

    public MitabModelledBinaryInteraction(String shortName, CvTerm type, ModelledParticipant participantA, ModelledParticipant participantB, CvTerm complexExpansion) {
        super(shortName, type, participantA, participantB, complexExpansion);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
