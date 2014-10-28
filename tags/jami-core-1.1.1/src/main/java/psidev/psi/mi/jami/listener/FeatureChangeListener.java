package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.Range;

/**
 * Listener to feature change events
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/07/13
 */
public interface FeatureChangeListener<F extends Feature> extends AnnotationsChangeListener<F>, XrefsChangeListener<F>, IdentifiersChangeListener<F>, AliasesChangeListener<F> {

    /**
     *
     * @param feature : updated feature
     * @param oldShortName : old shortName
     */
    public void onShortNameUpdate(F feature, String oldShortName);

    /**
     *
     * @param feature : updated feature
     * @param oldFullName : old fllName
     */
    public void onFullNameUpdate(F feature, String oldFullName);

    /**
     *
     * @param feature : updated feature
     * @param oldInterpro : old interpro
     */
    public void onInterproUpdate(F feature, String oldInterpro);

    /**
     *
     * @param feature : updated feature
     * @param oldType : old type
     */
    public void onTypeUpdate(F feature , CvTerm oldType);

    /**
     *
     * @param feature : updated feature
     * @param added  : added range
     */
    public void onAddedRange(F feature, Range added);

    /**
     *
     * @param feature : updated feature
     * @param removed : removed range
     */
    public void onRemovedRange(F feature, Range removed);

    /**
     *
     * @param feature : updated feature
     * @param range : updated range
     * @param oldStart : old start
     * @param oldEnd  : old end
     */
    public void onUpdatedRangePositions(F feature, Range range, Position oldStart, Position oldEnd);

    /**
     *
     * @param feature : updated feature
     * @param oldRole : old role
     */
    public void onRoleUpdate(F feature, CvTerm oldRole);

    /**
     *
     * @param feature : updated feature
     * @param added  : added linked feature
     */
    public void onAddedLinkedFeature(F feature, F added);

    /**
     *
     * @param feature : updated feature
     * @param removed : removed linked feature
     */
    public void onRemovedLinkedFeature(F feature, F removed);
}
