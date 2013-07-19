package psidev.psi.mi.jami.enricher;


import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.feature.listener.FeatureEnricherListener;
import psidev.psi.mi.jami.enricher.impl.protein.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.model.Feature;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 */
public interface FeatureEnricher <F extends Feature>{

    public void enrichFeature(F featureToEnrich) throws EnricherException;
    public void enrichFeatures(Collection<F> featuresToEnrich) throws EnricherException;



    public void setFeatureEnricherListener(FeatureEnricherListener listener);
    public FeatureEnricherListener getFeatureEnricherListener();

    public void setCvTermEnricher(CvTermEnricher cvTermEnricher);
    public CvTermEnricher getCvTermEnricher();
}
