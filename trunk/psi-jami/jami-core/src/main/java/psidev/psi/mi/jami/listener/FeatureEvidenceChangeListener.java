package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/07/13
 */
public interface FeatureEvidenceChangeListener extends FeatureChangeListener<FeatureEvidence> {

    public void onAddedDetectionMethod(FeatureEvidence feature, CvTerm added);

    public void onRemovedDetectionMethod(FeatureEvidence feature, CvTerm removed);
}
