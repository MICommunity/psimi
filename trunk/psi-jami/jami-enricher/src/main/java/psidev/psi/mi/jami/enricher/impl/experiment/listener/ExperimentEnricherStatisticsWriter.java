package psidev.psi.mi.jami.enricher.impl.experiment.listener;

import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.StatisticsWriter;
import psidev.psi.mi.jami.model.Experiment;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/08/13
 */
public class ExperimentEnricherStatisticsWriter
        extends StatisticsWriter<Experiment>
        implements ExperimentEnricherListener{

    private static final String jamiObject = "Experiment";

    public ExperimentEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName, jamiObject);
    }

    public ExperimentEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName, jamiObject);
    }

    public ExperimentEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile, jamiObject);
    }

    public void onExperimentEnriched(Experiment experiment, EnrichmentStatus status, String message) {
        onObjectEnriched(experiment , status , message);
    }
}
