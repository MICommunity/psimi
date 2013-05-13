package psidev.psi.mi.enricher.cvtermenricher;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 15:29
 */
public class EnrichmentReport {
    private String identityType = null;
    private String identity = null;

    public EnrichmentReport(){

    }


    public String getIdentity() {return identity;}
    public void setIdentity(String identity) {this.identity = identity;}

    public String getIdentityType() {return identityType;}
    public void setIdentityType(String method) {this.identityType = method;}




}
