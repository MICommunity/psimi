package psidev.psi.mi.DEPRECATEDquery.uniprotbridge.uniprotextractors.xrefuniprot;

import psidev.psi.mi.jami.model.Xref;
import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseCrossReference;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 24/04/13
 * Time: 13:42
 */
public interface GetXrefInterface {
    public ArrayList<Xref> getXref(DatabaseCrossReference dbxref);
}
