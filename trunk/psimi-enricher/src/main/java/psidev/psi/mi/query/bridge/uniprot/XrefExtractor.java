package psidev.psi.mi.query.bridge.uniprot;

import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.query.bridge.uniprot.xref.EnsemblGetXref;
import psidev.psi.mi.query.bridge.uniprot.xref.GetXrefInterface;
import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseCrossReference;
import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseType;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 24/04/13
 * Time: 13:51
 */
public class XrefExtractor extends Extractor{

    public ArrayList<Xref> getXrefs(UniProtEntry entry) {
        report = null;//Start a new report
        ArrayList<Xref> xrefs = new ArrayList<Xref>() ;

        Iterator<DatabaseCrossReference> iterator = entry.getDatabaseCrossReferences().iterator();
        while(iterator.hasNext()) {
            DatabaseCrossReference dbxref = iterator.next();
            DatabaseType db = dbxref.getDatabase();

            switch(db) {
                case ENSEMBL:
                    GetXrefInterface x = new EnsemblGetXref();
                    xrefs.addAll(x.getXref(dbxref));
                    break;

                default:
                    report.add("No GetXref for "+db);
                    break; //Gracefully ignore all other databases
            }
        }
        return xrefs;
    }
}
