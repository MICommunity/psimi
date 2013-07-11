package psidev.psi.mi.jami.enricher.springbatchattempt;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 11/07/13
 */
public class HelloReader {

    private String hello = "Hello";
    private String world = "world";

    public String getHello(){return hello;}
    public String getWorld(){return world;}
    public String getHelloWorld(){return hello+" "+world;}
}
