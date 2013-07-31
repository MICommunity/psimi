package psidev.psi.mi.jami.enricher.impl.publication.listener;

import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.StatisticsWriter;
import psidev.psi.mi.jami.model.Publication;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public class PublicationEnricherStatisticsWriter
        extends StatisticsWriter<Publication>
        implements PublicationEnricherListener{

    private static final String OBJECT = "Publication";

    public PublicationEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName, OBJECT);
    }

    public PublicationEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName, OBJECT);
    }

    public PublicationEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile, OBJECT);
    }

    public void onPublicationEnriched(Publication publication, EnrichmentStatus status, String message){
        onObjectEnriched(publication , status , message);
    }
}
