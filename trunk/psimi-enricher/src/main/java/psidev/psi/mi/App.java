package psidev.psi.mi;

import psidev.psi.mi.exception.UnrecognizedTermException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;
import psidev.psi.mi.query.*;
import psidev.psi.mi.query.bridge.QueryOLS;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main(String[] args){

        tester t = new tester();
        //t.testMIQuery("allosteric change in dynamics","MI:1166");

        t.testCVTerm(CvTermFactory.createMODCvTerm("dehydromethionine","MOD:01906"));
        t.testCVTerm(CvTermFactory.createMODCvTerm("dehydromethionine",null));
        t.testCVTerm(CvTermFactory.createMODCvTerm("dehydromethion",null));


        t.testCVTerm(CvTermFactory.createMICvTerm("allosteric change in dynamics","MOD:01906"));
        t.testCVTerm(CvTermFactory.createMODCvTerm("allosteric change in dynamics","MI:1166"));

        t.testCVTerm(CvTermFactory.createMICvTerm("allosteric change in dynamics","MI:1166"));
        t.testCVTerm(CvTermFactory.createMICvTerm("allosteric change in dynamics",null));
        t.testCVTerm(CvTermFactory.createMICvTerm("allosteric change in dynam",null));
        t.testCVTerm(CvTermFactory.createMICvTerm("osteric change in dynam",null));
        t.testCVTerm(CvTermFactory.createMICvTerm("allosteric",null));
        t.testCVTerm(CvTermFactory.createMICvTerm("0915", "MI:0915"));
        t.testCVTerm(CvTermFactory.createMICvTerm("0915", null));



    }


}
