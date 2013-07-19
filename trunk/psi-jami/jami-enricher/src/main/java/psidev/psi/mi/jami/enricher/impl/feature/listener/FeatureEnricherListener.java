package psidev.psi.mi.jami.enricher.impl.feature.listener;

import psidev.psi.mi.jami.enricher.listener.EnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.listener.FeatureChangeListener;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.Range;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 */
public interface FeatureEnricherListener
        extends FeatureChangeListener, EnricherListener {

    public void onFeatureEnriched(Feature feature , EnrichmentStatus status , String message);

    public void onUpdatedRange(Feature feature, Range updated , String message);
    public void onInvalidRange(Feature feature, Range invalid, String message);


}
