package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.Range;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/07/13
 */
public interface FeatureChangeListener extends AnnotationsChangeListener<Feature>, XrefsChangeListener<Feature>, IdentifiersChangeListener<Feature> {

    public void onShortNameUpdate(Feature feature, String oldShortName);

    public void onFullNameUpdate(Feature feature, String oldFullName);

    public void onInterproUpdate(Feature feature, String oldInterpro);

    public void onTypeAdded(Feature feature , CvTerm oldType);

    public void onAddedRange(Feature feature, Range added);

    public void onRemovedRange(Feature feature, Range removed);

    public void onUpdatedRangePositions(Feature feature, Range range, Position oldStart, Position oldEnd);
}
