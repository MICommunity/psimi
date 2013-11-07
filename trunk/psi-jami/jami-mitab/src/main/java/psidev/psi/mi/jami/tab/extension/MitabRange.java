package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.ResultingSequence;
import psidev.psi.mi.jami.model.impl.DefaultRange;

/**
 * Mitab extension of Range
 * It contains a FileSourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class MitabRange extends DefaultRange implements FileSourceContext{

    private FileSourceLocator sourceLocator;

    public MitabRange(Position start, Position end) {
        super(start, end);
    }

    public MitabRange(Position start, Position end, boolean isLink) {
        super(start, end, isLink);
    }

    public MitabRange(Position start, Position end, ResultingSequence resultingSequence) {
        super(start, end, resultingSequence);
    }

    public MitabRange(Position start, Position end, boolean isLink, ResultingSequence resultingSequence) {
        super(start, end, isLink, resultingSequence);
    }

    public MitabRange(Position start, Position end, Participant participant) {
        super(start, end, participant);
    }

    public MitabRange(Position start, Position end, boolean isLink, Participant participant) {
        super(start, end, isLink, participant);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public String toString() {
        return "Feature Range: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }
}
