package psidev.psi.mi.jami.bridges.uniprotremapping;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 03/06/13
 * Time: 15:29
 */
public  class GeneralTest {

    public GeneralTest(){
        RemapProteinImpl remap = new RemapProteinImpl();
        remap.setIdentifier("ensembl", "ENSP00000351524");
        RemapReport report = remap.runRemap();
        System.out.println("report "+report.getFinalUniprotId());
    }

    public static void main(String[] args){
        GeneralTest test = new GeneralTest();
    }

}
