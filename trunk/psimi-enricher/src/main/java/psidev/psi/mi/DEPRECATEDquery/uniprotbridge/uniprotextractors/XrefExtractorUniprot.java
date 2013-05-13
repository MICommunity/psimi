package psidev.psi.mi.DEPRECATEDquery.uniprotbridge.uniprotextractors;

import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.DEPRECATEDquery.uniprotbridge.uniprotextractors.xrefuniprot.EnsemblGetXref;
import psidev.psi.mi.DEPRECATEDquery.uniprotbridge.uniprotextractors.xrefuniprot.GetXrefInterface;
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
public class XrefExtractorUniprot {

    public ArrayList<Xref> getXrefs(UniProtEntry entry) {

        if(entry == null){
            //report.add("No entry to extract xrefs from");
            return null;
        }

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
                    //report.add("No GetXref for "+db);
                    break; //Gracefully ignore all other databases
            }
        }
        return xrefs;
    }
}
