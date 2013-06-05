package psidev.psi.mi.jami.bridges.uniprotremapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.bridges.uniprotremapping.exception.ResultConflictException;
import psidev.psi.mi.jami.bridges.uniprotremapping.listener.RemapListener;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.model.impl.DefaultXref;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 03/06/13
 * Time: 15:29
 */
public  class GeneralTest {

    public static final Log log = LogFactory.getLog(GeneralTest.class);
    static DefaultProteinRemap remap;
    static ArrayList<Protein> tests;
    static HashMap<String, Xref> xrefs = new HashMap<String, Xref>();
    static String[][] xrefsraw;


    public GeneralTest(){
        xrefsraw = new String[][] {
                {"ensembl", "ENSP00000351524"}, //0 P42694
                {"ensembl", "ENSG00000198265"}, //1 P42694
                {"pfam","PF00642"},             //2 P42694
                {"ensembl"," ENSG00000197561"}, //3 P08246
        };
        for(int i = 0; i< xrefsraw.length ;i++){
            String database = xrefsraw[i][0];
            String id = xrefsraw[i][1];
            xrefs.put(""+i, new DefaultXref(new DefaultCvTerm(database), id));
        }

        tests = new ArrayList<Protein>();

        Protein pa = makeP();
        pa.getXrefs().add(xrefs.get("0"));
        tests.add(pa);

        Protein pb = makeP();
        pb.getXrefs().add(xrefs.get("0"));
        pb.getXrefs().add(xrefs.get("1"));
        tests.add(pb);

        Protein pc = makeP();
        pc.getXrefs().add(xrefs.get("2"));
        pc.getXrefs().add(xrefs.get("0"));
        tests.add(pc);

        Protein pd = makeP();
        pd.getXrefs().add(xrefs.get("3"));
        pd.getXrefs().add(xrefs.get("0"));
        tests.add(pd);

        Protein pans = makeP();
        pans.getXrefs().add(xrefs.get("0"));
        pans.setSequence("") ;
        tests.add(pans);

        Protein pbns = makeP();
        pbns.getXrefs().add(xrefs.get("0"));
        pbns.getXrefs().add(xrefs.get("1"));
        pbns.setSequence("") ;
        tests.add(pbns);

        Protein pcns = makeP();
        pcns.getXrefs().add(xrefs.get("2"));
        pcns.getXrefs().add(xrefs.get("0"));
        pcns.setSequence("") ;
        tests.add(pcns);

        Protein pdns = makeP();
        pdns.getXrefs().add(xrefs.get("3"));
        pdns.getXrefs().add(xrefs.get("0"));
        pdns.setSequence("") ;
        tests.add(pdns);

        remap = new DefaultProteinRemap();
        remap.addRemapListener(new RemapListener() {
            public void fireRemapReport(RemapReport report) {
                onRemapReport(report);
            }
        });
    }

    private void onRemapReport(RemapReport report){


    }

    public Protein makeP(){
        Protein p = new DefaultProtein("test");
        p.setSequence(sequence_a);
        p.setOrganism(new DefaultOrganism(Integer.parseInt("9606")));

        return p;
    }

    public static void runTest(){
        int i = 1;
        for(Protein p: tests){
            log.info("---- Doing a test "+i+"---");
            i++;
            remap.setProtein(p);
            remap.setCheckingEnabled(true);

            while(true){
                remap.setUseIdentifiers(true);
                remap.setUseSequence(true);
                remap.remapProtein();

                remap.setUseIdentifiers(true);
                remap.setUseSequence(false);
                remap.remapProtein();

                remap.setUseIdentifiers(false);
                remap.setUseSequence(true);
                remap.remapProtein();

                remap.setUseIdentifiers(false);
                remap.setUseSequence(false);
                remap.remapProtein();

                if(remap.isCheckingEnabled()) remap.setCheckingEnabled(false);
                else break;
            }
        }
        log.info("finished");
    }


    public static void main(String[] args){
        GeneralTest test = new GeneralTest();
        runTest();
    }



















    static String sequence_a = "MEDRRAEKSCEQACESLKRQDYEMALKHCTEALLSLGQYSMADFTGPCPLEIERIKIESL" +
            "LYRIASFLQLKNYVQADEDCRHVLGEGLAKGEDAFRAVLCCMQLKGKLQPVSTILAKSLT" +
            "GESLNGMVTKDLTRLKTLLSETETATSNALSGYHVEDLDEGSCNGWHFRPPPRGITSSEE" +
            "YTLCKRFLEQGICRYGAQCTSAHSQEELAEWQKRYASRLIKLKQQNENKQLSGSYMETLI" +
            "EKWMNSLSPEKVLSECIEGVKVEHNPDLSVTVSTKKSHQTWTFALTCKPARMLYRVALLY" +
            "DAHRPHFSIIAISAGDSTTQVSQEVPENCQEWIGGKMAQNGLDHYVYKVGIAFNTEIFGT" +
            "FRQTIVFDFGLEPVLMQRVMIDAASTEDLEYLMHAKQQLVTTAKRWDSSSKTIIDFEPNE" +
            "TTDLEKSLLIRYQIPLSADQLFTQSVLDKSLTKSNYQSRLHDLLYIEEIAQYKEISKFNL" +
            "KVQLQILASFMLTGVSGGAKYAQNGQLFGRFKLTETLSEDTLAGRLVMTKVNAVYLLPVP" +
            "KQKLVQTQGTKEKVYEATIEEKTKEYIFLRLSRECCEELNLRPDCDTQVELQFQLNRLPL" +
            "CEMHYALDRIKDNGVLFPDISMTPTIPWSPNRQWDEQLDPRLNAKQKEAVLAITTPLAIQ" +
            "LPPVLIIGPYGTGKTFTLAQAVKHILQQQETRILICTHSNSAADLYIKDYLHPYVEAGNP" +
            "QARPLRVYFRNRWVKTVHPVVHQYCLISSAHSTFQMPQKEDILKHRVVVVTLNTSQYLCQ" +
            "LDLEPGFFTHILLDEAAQAMECETIMPLALATQNTRIVLAGDHMQLSPFVYSEFARERNL" +
            "HVSLLDRLYEHYPAEFPCRILLCENYRSHEAIINYTSELFYEGKLMASGKQPAHKDFYPL" +
            "TFFTARGEDVQEKNSTAFYNNAEVFEVVERVEELRRKWPVAWGKLDDGSIGVVTPYADQV" +
            "FRIRAELRKKRLSDVNVERVLNVQGKQFRVLFLSTVRTRHTCKHKQTPIKKKEQLLEDST" +
            "EDLDYGFLSNYKLLNTAITRAQSLVAVVGDPIALCSIGRCRKFWERFIALCHENSSLHGI" +
            "TFEQIKAQLEALELKKTYVLNPLAPEFIPRALRLQHSGSTNKQQQSPPKGKSLHHTQNDH" +
            "FQNDGIVQPNPSVLIGNPIRAYTPPPPLGPHPNLGKSPSPVQRIDPHTGTSILYVPAVYG" +
            "GNVVMSVPLPVPWTGYQGRFAVDPRIITHQAAMAYNMNLLQTHGRGSPIPYGLGHHPPVT" +
            "IGQPQNQHQEKDQHEQNRNGKSDTNNSGPEINKIRTPEKKPTEPKQVDLESNPQNRSPES" +
            "RPSVVYPSTKFPRKDNLNPRHINLPLPAPHAQYAIPNRHFHPLPQLPRPPFPIPQQHTLL" +
            "NQQQNNLPEQPNQIPPQPNQVVQQQSQLNQQPQQPPPQLSPAYQAGPNNAFFNSAVAHRP" +
            "QSPPAEAVIPEQQPPPMLQEGHSPLRAIAQPGPILPSHLNSFIDENPSGLPIGEALDRIH" +
            "GSVALETLRQQQARFQQWSEHHAFLSQGSAPYPHHHHPHLQHLPQPPLGLHQPPVRADWK" +
            "LTSSAEDEVETTYSRFQDLIRELSHRDQSETRELAEMPPPQSRLLQYRQVQSRSPPAVPS" +
            "PPSSTDHSSHFSNFNDNSRDIEVASNPAFPQRLPPQIFNSPFSLPSEHLAPPPLKYLAPD" +
            "GAWTFANLQQNHLMGPGFPYGLPPLPHRPPQNPFVQIQNHQHAIGQEPFHPLSSRTVSSS" +
            "SLPSLEEYEPRGPGRPLYQRRISSSSVQPCSEEVSTPQDSLAQCKELQDHSNQSSFNFSS" +
            "PESWVNTTSSTPYQNIPCNGSSRTAQPRELIAPPKTVKPPEDQLKSENLEVSSSFNYSVL" +
            "QHLGQFPPLMPNKQIAESANSSSPQSSAGGKPAMSYASALRAPPKPRPPPEQAKKSSDPL" +
            "SLFQELSLGSSSGSNGFYSYFK";

}
