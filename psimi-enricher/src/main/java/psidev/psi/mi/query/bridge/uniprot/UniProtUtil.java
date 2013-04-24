package psidev.psi.mi.query.bridge.uniprot;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;
import psidev.psi.mi.query.bridge.uniprot.xref.EnsemblGetXref;
import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseType;
import uk.ac.ebi.kraken.interfaces.uniprot.DatabaseCrossReference;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.interfaces.uniprot.dbx.ensembl.Ensembl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 22/04/13
 * Time: 16:38
 */
public class UniProtUtil {

    public void uniProtToJami (UniProtEntry entry){

        Organism organism = new OrganismExtractor().getOrganism(entry);
        ArrayList<Xref> xref = new XrefExtractor().getXrefs(entry);


        //return null;
    }



}
