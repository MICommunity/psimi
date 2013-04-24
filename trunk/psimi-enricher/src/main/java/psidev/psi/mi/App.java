package psidev.psi.mi;

import psidev.psi.mi.exception.UnrecognizedTermException;
import psidev.psi.mi.query.*;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main(String[] args){
        tester t = new tester();

        t.newQuery("psi-mi","MI:0915",Criteria.TERM);
        t.newQuery("psi-mi","MI:0403",Criteria.TERM);

        t.newQuery("MI","MI:2097",Criteria.TERM);
        t.newQuery("MI","MI:2097",Criteria.SCIENTIFICNAME);


        t.newQuery("MI","MI:2097",Criteria.TERM);
        t.newQuery("MI","2097",Criteria.TERM);
        t.newQuery("MI", "foo", Criteria.TERM);

        t.newQuery("go","GO:0007165",Criteria.TERM);//(signal transduction)

        t.newQuery("Uniprot","Q15942",Criteria.TAXID);

        t.newQuery("Uniprot","Q15942",Criteria.COMMONNAME);
        t.newQuery("Uniprot","Q15942",Criteria.SCIENTIFICNAME);
        t.newQuery("uniprot", "foo", Criteria.TERM);

        t.newQuery("derp", "foo", Criteria.TERM);
    }


}
