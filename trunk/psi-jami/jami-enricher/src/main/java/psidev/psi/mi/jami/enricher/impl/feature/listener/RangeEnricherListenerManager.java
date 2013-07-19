package psidev.psi.mi.jami.enricher.impl.feature.listener;

import psidev.psi.mi.jami.enricher.listener.EnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.Range;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/07/13
 */
@Deprecated
public class RangeEnricherListenerManager
        extends EnricherListenerManager<RangeEnricherListener>
        implements RangeEnricherListener {


    public void onRangeEnriched(Feature feature, Range range, EnrichmentStatus status, String message) {
        for(RangeEnricherListener listener : listenersList){
            listener.onRangeEnriched(feature, range, status, message);
        }
    }

    public void onRangeShifted(Range range, Position oldStart, Position oldEnd) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
