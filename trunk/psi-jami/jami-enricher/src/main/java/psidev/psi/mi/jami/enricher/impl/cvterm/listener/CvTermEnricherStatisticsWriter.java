package psidev.psi.mi.jami.enricher.impl.cvterm.listener;


import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.StatisticsWriter;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

import java.io.*;

/**
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 18/07/13
 */
public class CvTermEnricherStatisticsWriter
        extends StatisticsWriter<CvTerm>
        implements CvTermEnricherListener {

    private static final String OBJECT = "CvTerm";

    public CvTermEnricherStatisticsWriter(String fileName) throws IOException {
        super(fileName, OBJECT);
    }

    public CvTermEnricherStatisticsWriter(String successFileName, String failureFileName) throws IOException {
        super(successFileName, failureFileName, OBJECT);
    }

    public CvTermEnricherStatisticsWriter(File successFile, File failureFile) throws IOException {
        super(successFile, failureFile, OBJECT);
    }

    public void onCvTermEnriched(CvTerm cvTerm, EnrichmentStatus status, String message){
        onObjectEnriched(cvTerm , status , message);
    }


    public void onShortNameUpdate(CvTerm cv, String oldShortName) {
        checkObject(cv);
        updateCount ++;
    }

    public void onFullNameUpdate(CvTerm cv, String oldFullName) {
        checkObject(cv);
        updateCount ++;
    }

    public void onMIIdentifierUpdate(CvTerm cv, String oldMI) {
        checkObject(cv);
        updateCount ++;
    }

    public void onMODIdentifierUpdate(CvTerm cv, String oldMOD) {
        checkObject(cv);
        updateCount ++;
    }

    public void onPARIdentifierUpdate(CvTerm cv, String oldPAR) {
        checkObject(cv);
        updateCount ++;
    }

    public void onAddedIdentifier(CvTerm cv, Xref added) {
        checkObject(cv);
        additionCount ++;
    }

    public void onRemovedIdentifier(CvTerm cv, Xref removed) {
        checkObject(cv);
        removedCount ++;
    }

    public void onAddedXref(CvTerm cv, Xref added) {
        checkObject(cv);
        additionCount ++;
    }

    public void onRemovedXref(CvTerm cv, Xref removed) {
        checkObject(cv);
        removedCount ++;
    }

    public void onAddedSynonym(CvTerm cv, Alias added) {
        checkObject(cv);
        additionCount ++;
    }

    public void onRemovedSynonym(CvTerm cv, Alias removed) {
        checkObject(cv);
        removedCount ++;
    }
}
