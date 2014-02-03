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
public interface FeatureChangeListener<F extends Feature> extends AnnotationsChangeListener<F>, XrefsChangeListener<F>, IdentifiersChangeListener<F>, AliasesChangeListener<F> {

    public void onShortNameUpdate(F feature, String oldShortName);

    public void onFullNameUpdate(F feature, String oldFullName);

    public void onInterproUpdate(F feature, String oldInterpro);

    public void onTypeUpdate(F feature , CvTerm oldType);

    public void onAddedRange(F feature, Range added);

    public void onRemovedRange(F feature, Range removed);

    public void onUpdatedRangePositions(F feature, Range range, Position oldStart, Position oldEnd);

    public void onInteractionDependencyUpdate(F feature , CvTerm oldDependency);

    public void onInteractionEffectUpdate(F feature , CvTerm oldEffect);
}
