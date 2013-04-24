package psidev.psi.mi.query.bridge.uniprot.xref;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;
import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseCrossReference;
import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseType;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.interfaces.uniprot.dbx.ensembl.Ensembl;

import java.util.ArrayList;

/**
 * A class to handle the translation of a UniProt DatabaseCrossReference object.
 * A separate class exists for each database as UniProt treats each as individual classes.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 24/04/13
 * Time: 13:16
 */
public class EnsemblGetXref implements GetXrefInterface {
    private boolean useGene = true;
    private boolean useProtein = true;
    private boolean useTranscript = true;

    /**
     * By default all 3 of the possible x-references with Ensembl are generated.
     * (Gene, Transcript and Protein) unless the useX booleans have been modified.
     *
     *
     * @param dbxref    The data provided by uniprot to be translated to jami
     * @return          The data in jami form. Null if the dbxref is incompatible
     */
    public ArrayList<Xref> getXref(DatabaseCrossReference dbxref) {
        if(dbxref == null){
            return null;
        }
        if(dbxref.getDatabase() != DatabaseType.ENSEMBL){
            return null;
        }

        ArrayList<Xref> xrefs = new ArrayList<Xref>();

        CvTerm cvTerm = CvTermFactory.createEnsemblDatabase();
        Ensembl e = (Ensembl)dbxref;
        //
        if(useGene && e.hasEnsemblGeneIdentifier()){
            xrefs.add(new DefaultXref(cvTerm, e.getEnsemblGeneIdentifier().getValue()));
        }
        if(useProtein && e.hasEnsemblProteinIdentifier()){
            xrefs.add(new DefaultXref(cvTerm, e.getEnsemblProteinIdentifier().getValue()));
        }
        if(useTranscript && e.hasEnsemblTranscriptIdentifier()){
            xrefs.add(new DefaultXref(cvTerm, e.getEnsemblTranscriptIdentifier().getValue()));
        }

        return xrefs;
    }

    public boolean isUseGene() {
        return useGene;
    }

    /**
     *
     * @param useGene
     */
    public void setUseGene(boolean useGene) {
        this.useGene = useGene;
    }

    public boolean isUseProtein() {
        return useProtein;
    }

    public void setUseProtein(boolean useProtein) {
        this.useProtein = useProtein;
    }

    public boolean isUseTranscript() {
        return useTranscript;
    }

    public void setUseTranscript(boolean useTranscript) {
        this.useTranscript = useTranscript;
    }
}
