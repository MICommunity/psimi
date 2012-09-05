package psidev.psi.mi.calimocho.solr.converter;

import java.text.SimpleDateFormat;

/**
 * Hello world!
 *
 */
public class AppTest
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );


        String year = "2012", month = "5", day = "1";

        try {
            SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy/MM/dd");
            String formattedDate = simpleFormat.format(simpleFormat.parse(year+"/"+month+"/"+day));
            System.out.println("year = "+year+" month = "+month+" day + "+day);
            System.out.println("formattedDate: "+formattedDate);
            System.out.println("intDate: "+Integer.parseInt(formattedDate.replace("/", "")));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace(System.err);
        }

    }
}
