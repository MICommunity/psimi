package psidev.psi.mi.jami.enricher.listener.impl.writer;

import psidev.psi.mi.jami.enricher.listener.InteractorEnricherListener;
import psidev.psi.mi.jami.enricher.listener.impl.writer.EnricherStatisticsWriter;
import psidev.psi.mi.jami.model.*;

import java.io.File;
import java.io.IOException;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class InteractorEnricherStatisticsWriter<T extends Interactor> extends EnricherStatisticsWriter<T>
implements InteractorEnricherListener<T> {

public static final String FILE_NAME = "interactor";

/**
 * Uses the known name of the JamiObject type as the seed to generate names for the success an failure log files.
 * @throws java.io.IOException      Thrown if a problem is encountered with file location.
 */
public InteractorEnricherStatisticsWriter() throws IOException {
        super(FILE_NAME);
}

/**
 * Creates the files from the provided seed file name with 'success' and 'failure' appended.
 * @param fileName          The seed to base the names of the files on.
 * @throws java.io.IOException      Thrown if a problem is encountered with file location.
 */
public InteractorEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName);
}

/**
 * Uses the provided names to create the files for successful and failed enrichment logging.
 * @param successFileName   The exact name for the file to log successful enrichments in
 * @param failureFileName   The exact name for the file to log failed enrichments in
 * @throws java.io.IOException      Thrown if a problem is encountered with file location.
 */
public InteractorEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName);
}

/**
 * Uses the exact files provided to log successful and failed enrichments.
 * @param successFile       The file to log successful enrichments in
 * @param failureFile       The file to log failed enrichments in.
 * @throws java.io.IOException      Thrown if a problem is encountered with file location.
 */
public InteractorEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile);
}


// ================================================================

public void onShortNameUpdate(T interactor, String oldShortName) {
        checkObject(interactor);
incrementUpdateCount();
}

public void onFullNameUpdate(T interactor, String oldFullName) {
        checkObject(interactor);
incrementUpdateCount();
}

public void onOrganismUpdate(T interactor, Organism organism) {
        checkObject(interactor);
incrementUpdateCount();
}

public void onInteractorTypeUpdate(T interactor, CvTerm old) {
        checkObject(interactor);
incrementUpdateCount();
}

public void onAddedIdentifier(T interactor, Xref added) {
        checkObject(interactor);
incrementAdditionCount();
}

public void onRemovedIdentifier(T interactor, Xref removed) {
        checkObject(interactor);
incrementRemovedCount();
}

public void onAddedXref(T interactor, Xref added) {
        checkObject(interactor);
incrementAdditionCount();
}

public void onRemovedXref(T interactor, Xref removed) {
        checkObject(interactor);
incrementRemovedCount();
}

public void onAddedAlias(T interactor, Alias added) {
        checkObject(interactor);
incrementAdditionCount();
}

public void onRemovedAlias(T interactor, Alias removed) {
        checkObject(interactor);
incrementRemovedCount();
}

public void onAddedChecksum(T interactor, Checksum added) {
        checkObject(interactor);
incrementAdditionCount();
}

public void onRemovedChecksum(T interactor, Checksum removed) {
        checkObject(interactor);
incrementRemovedCount();
}

public void onAddedAnnotation(T o, Annotation added) {
        checkObject(o);
incrementAdditionCount();
}

public void onRemovedAnnotation(T o, Annotation removed) {
        checkObject(o);
incrementRemovedCount();
}
        }