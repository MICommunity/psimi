package psidev.psi.mi.jami.enricher.listener.impl;

import psidev.psi.mi.jami.enricher.listener.InteractorEnricherListener;
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

public class InteractorEnricherStatisticsWriter extends EnricherStatisticsWriter<Interactor>
implements InteractorEnricherListener<Interactor> {

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

public void onShortNameUpdate(Interactor interactor, String oldShortName) {
        checkObject(interactor);
incrementUpdateCount();
}

public void onFullNameUpdate(Interactor interactor, String oldFullName) {
        checkObject(interactor);
incrementUpdateCount();
}

public void onAddedOrganism(Interactor interactor) {
        checkObject(interactor);
incrementAdditionCount();
}

public void onAddedInteractorType(Interactor interactor) {
        checkObject(interactor);
incrementAdditionCount();
}

public void onAddedIdentifier(Interactor interactor, Xref added) {
        checkObject(interactor);
incrementAdditionCount();
}

public void onRemovedIdentifier(Interactor interactor, Xref removed) {
        checkObject(interactor);
incrementRemovedCount();
}

public void onAddedXref(Interactor interactor, Xref added) {
        checkObject(interactor);
incrementAdditionCount();
}

public void onRemovedXref(Interactor interactor, Xref removed) {
        checkObject(interactor);
incrementRemovedCount();
}

public void onAddedAlias(Interactor interactor, Alias added) {
        checkObject(interactor);
incrementAdditionCount();
}

public void onRemovedAlias(Interactor interactor, Alias removed) {
        checkObject(interactor);
incrementRemovedCount();
}

public void onAddedChecksum(Interactor interactor, Checksum added) {
        checkObject(interactor);
incrementAdditionCount();
}

public void onRemovedChecksum(Interactor interactor, Checksum removed) {
        checkObject(interactor);
incrementRemovedCount();
}

public void onAddedAnnotation(Interactor o, Annotation added) {
        checkObject(o);
incrementAdditionCount();
}

public void onRemovedAnnotation(Interactor o, Annotation removed) {
        checkObject(o);
incrementRemovedCount();
}
        }