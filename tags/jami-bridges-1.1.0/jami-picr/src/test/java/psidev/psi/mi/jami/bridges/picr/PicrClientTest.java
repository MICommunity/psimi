package psidev.psi.mi.jami.bridges.picr;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * PicrClient Tester
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16-Mar-2010</pre>
 */

public class PicrClientTest {

    private PicrClient client;

    @Before
    public void before() {
        client = new PicrClient();
    }

    @After
    public void after() {
        client = null;
    }

    @Test
    public void getSWISSPROTAccession() {
        Collection<String> upis = null;
        try {
            upis = client.getSwissprotIdsForAccession("NP_417804", null);
            for (String upi : upis){
                System.out.println(upi);
            }
            Assert.assertEquals(4, upis.size());
        } catch (BridgeFailedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Test
    public void getTREMBLAccession() {
        Collection<String> upis = null;
        try {
            upis = client.getTremblIdsForAccession("NP_417804", null);
            for (String upi : upis){
                System.out.println(upi);
            }
            Assert.assertEquals(248, upis.size());
        } catch (BridgeFailedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Test
    public void getSWISSPROTAccessionFromSequence() {
        ArrayList<String> upis = null;
        try {
            upis = client.getSwissprotIdsForSequence("MRFAIVVTGPAYGTQQASSAFQFAQALIADGHELSSVFFYREGVYNANQLTSPASDEFDLVRAWQQLNAQHGVALNICVAAALRRGVVDETEAGRLGLASSNLQQGFTLSGLGALAEASLTCDRVVQF", null);
            for (String upi : upis){
                System.out.println(upi);
            }
            Assert.assertEquals(4, upis.size());
        } catch (BridgeFailedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Test
    public void getTREMBLAccessionFromSequence() {
        ArrayList<String> upis = null;
        try {
            upis = client.getTremblIdsForSequence("MRFAIVVTGPAYGTQQASSAFQFAQALIADGHELSSVFFYREGVYNANQLTSPASDEFDLVRAWQQLNAQHGVALNICVAAALRRGVVDETEAGRLGLASSNLQQGFTLSGLGALAEASLTCDRVVQF", null);
            for (String upi : upis){
                System.out.println(upi);
            }
            Assert.assertEquals(248, upis.size());
        } catch (BridgeFailedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Test
    public void getUniprotBestGuess(){
        try {
            String [] result = client.getUniprotBestGuessFor("P45532", "83333");

            System.out.println("database : " + result[0]);
            System.out.println("accession : " + result[1]);

            Assert.assertEquals("SWISSPROT", result[0]);
            Assert.assertEquals("P45532", result[1]);
        } catch (BridgeFailedException e) {
            e.printStackTrace();
        }
    }
}
