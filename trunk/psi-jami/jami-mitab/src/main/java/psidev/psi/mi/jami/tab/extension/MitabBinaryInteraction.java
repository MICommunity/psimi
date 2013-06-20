package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.binary.impl.DefaultBinaryInteraction;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Participant;

/**
 * A MitabBinaryInteraction is a binaryInteraction with a MitabSourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class MitabBinaryInteraction extends DefaultBinaryInteraction implements FileSourceContext {

    private FileSourceLocator sourceLocator;

    public MitabBinaryInteraction() {
        super();
    }

    public MitabBinaryInteraction(String shortName) {
        super(shortName);
    }

    public MitabBinaryInteraction(String shortName, CvTerm type) {
        super(shortName, type);
    }

    public MitabBinaryInteraction(Participant participantA, Participant participantB) {
        super(participantA, participantB);
    }

    public MitabBinaryInteraction(String shortName, Participant participantA, Participant participantB) {
        super(shortName, participantA, participantB);
    }

    public MitabBinaryInteraction(String shortName, CvTerm type, Participant participantA, Participant participantB) {
        super(shortName, type, participantA, participantB);
    }

    public MitabBinaryInteraction(CvTerm complexExpansion) {
        super(complexExpansion);
    }

    public MitabBinaryInteraction(String shortName, CvTerm type, CvTerm complexExpansion) {
        super(shortName, type, complexExpansion);
    }

    public MitabBinaryInteraction(Participant participantA, Participant participantB, CvTerm complexExpansion) {
        super(participantA, participantB, complexExpansion);
    }

    public MitabBinaryInteraction(String shortName, Participant participantA, Participant participantB, CvTerm complexExpansion) {
        super(shortName, participantA, participantB, complexExpansion);
    }

    public MitabBinaryInteraction(String shortName, CvTerm type, Participant participantA, Participant participantB, CvTerm complexExpansion) {
        super(shortName, type, participantA, participantB, complexExpansion);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
