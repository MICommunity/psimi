package psidev.psi.mi.jami.crosslink.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.ResultingSequence;
import psidev.psi.mi.jami.model.impl.DefaultRange;

/**
 * Crosslink CSV extension of Range
 * It contains a FileSourceLocator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class CsvRange extends DefaultRange implements FileSourceContext{

    private FileSourceLocator sourceLocator;

    public CsvRange(Position start, Position end) {
        super(start, end);
    }

    public CsvRange(Position start, Position end, boolean isLink) {
        super(start, end, isLink);
    }

    public CsvRange(Position start, Position end, ResultingSequence resultingSequence) {
        super(start, end, resultingSequence);
    }

    public CsvRange(Position start, Position end, boolean isLink, ResultingSequence resultingSequence) {
        super(start, end, isLink, resultingSequence);
    }

    public CsvRange(Position start, Position end, Participant participant) {
        super(start, end, participant);
    }

    public CsvRange(Position start, Position end, boolean isLink, Participant participant) {
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
