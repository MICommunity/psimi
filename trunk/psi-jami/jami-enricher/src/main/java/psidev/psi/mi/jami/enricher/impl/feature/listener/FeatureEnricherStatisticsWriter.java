package psidev.psi.mi.jami.enricher.impl.feature.listener;

import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.StatisticsWriter;
import psidev.psi.mi.jami.model.*;

import java.io.*;

/**
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 18/07/13
 */
public class FeatureEnricherStatisticsWriter
        extends StatisticsWriter<Feature>
        implements FeatureEnricherListener {


    public FeatureEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile, "Feature");
    }

    public void onFeatureEnriched(Feature feature, EnrichmentStatus status, String message) {
        onObjectEnriched(feature, status, message);
    }

    public void onShortNameUpdate(Feature feature, String oldShortName) {
        checkObject(feature);
        updateCount++;
    }

    public void onFullNameUpdate(Feature feature, String oldFullName) {
        checkObject(feature);
        updateCount++;
    }

    public void onInterproUpdate(Feature feature, String oldInterpro) {
        checkObject(feature);
        updateCount++;
    }

    public void onTypeAdded(Feature feature, CvTerm oldType) {
        checkObject(feature);
        additionCount++;
    }

    public void onAddedIdentifier(Feature feature, Xref added) {
        checkObject(feature);
        additionCount++;
    }

    public void onRemovedIdentifier(Feature feature, Xref removed) {
        checkObject(feature);
        removedCount++;
    }

    public void onAddedXref(Feature feature, Xref added) {
        checkObject(feature);
        additionCount++;
    }

    public void onRemovedXref(Feature feature, Xref removed) {
        checkObject(feature);
        removedCount++;
    }

    public void onAddedAnnotation(Feature feature, Annotation added) {
        checkObject(feature);
        additionCount++;
    }

    public void onRemovedAnnotation(Feature feature, Annotation removed) {
        checkObject(feature);
        removedCount++;
    }

    public void onAddedRange(Feature feature, Range added) {
        checkObject(feature);
        additionCount++;
    }

    public void onRemovedRange(Feature feature, Range removed) {
        checkObject(feature);
        removedCount++;
    }


}
