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


        t.log("MI","MI:2097") ;
        t.log("MI","foo");

        t.log("UP","Q15942");
        t.log("UP","foo");

        t.log("derp","foo");

    }


}
