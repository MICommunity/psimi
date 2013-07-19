package psidev.psi.mi.jami.enricher.impl.feature.listener;

import psidev.psi.mi.jami.enricher.listener.EnricherListener;
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
public interface RangeEnricherListener
        extends EnricherListener {

    public void onRangeEnriched(Feature feature , Range range ,
                                EnrichmentStatus status, String message);

    public void onRangeShifted(Range range , Position oldStart , Position oldEnd);

}
