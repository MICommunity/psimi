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


        t.newQuery("MI","MI:2097") ;
        t.newQuery("MI","foo");

        t.newQuery("Uniprot","Q15942");
        t.newQuery("uniprot","foo");

        t.newQuery("derp","foo");

    }


}
