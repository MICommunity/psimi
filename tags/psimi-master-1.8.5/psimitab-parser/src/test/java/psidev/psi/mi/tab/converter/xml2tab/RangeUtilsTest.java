package psidev.psi.mi.tab.converter.xml2tab;

import org.junit.Test;
import org.junit.Assert;
import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.model.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 23/07/2012
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */
public class RangeUtilsTest {

    List<String> tabGoodRanges =
            Arrays.asList(
                    "?-?",
                    "20-?",
                    "?-20",
                    "n-?",
                    "?-c",
                    "n-n",
                    "c-c",
                    "n-20",
                    "20-c",
                    "23..23-25..25",
                    "23..25-32..36",
                    ">20->20",
                    "<20-<20",
                    ">20-<40",
                    "<20->40",
                    "17-<20",
                    "<17-20",
                    "17->20",
                    ">17-20",
                    "17-20"
            );

    List<Range> xmlGoodRanges = createGoodRanges();


    private List<Range> createGoodRanges() {

        List<Range> ranges = new ArrayList<Range>();

        RangeStatus undetermined = new RangeStatus();
        Xref undeterminedXref = new Xref();
        Names undeterminedNames = new Names();
        undeterminedXref.setPrimaryRef(new DbReference("psi-mi", "MI:0488", RangeUtils.MI_RANGE_UNDETERMINED, "identity", "MI:0356"));
        undeterminedNames.setShortLabel("undetermined");
        undeterminedNames.setFullName("undetermined sequence position");
        undetermined.setNames(undeterminedNames);
        undetermined.setXref(undeterminedXref);

        RangeStatus greaterThan = new RangeStatus();
        Xref greaterThanXref = new Xref();
        Names greaterThanNames = new Names();
        greaterThanXref.setPrimaryRef(new DbReference("psi-mi", "MI:0488", RangeUtils.MI_RANGE_GREATER, "identity", "MI:0356"));
        greaterThanNames.setShortLabel("greater-than");
        greaterThanNames.setFullName("greater-than");
        greaterThan.setNames(greaterThanNames);
        greaterThan.setXref(greaterThanXref);

        RangeStatus lessThan = new RangeStatus();
        Xref lessThanXref = new Xref();
        Names lessThanNames = new Names();
        lessThanXref.setPrimaryRef(new DbReference("psi-mi", "MI:0488", RangeUtils.MI_RANGE_LESS, "identity", "MI:0356"));
        lessThanNames.setShortLabel("less-than");
        lessThanNames.setFullName("less-than");
        lessThan.setNames(lessThanNames);
        lessThan.setXref(lessThanXref);

        RangeStatus certain = new RangeStatus();
        Xref certainXref = new Xref();
        Names certainNames = new Names();
        certainXref.setPrimaryRef(new DbReference("psi-mi", "MI:0488", RangeUtils.MI_RANGE_CERTAIN, "identity", "MI:0356"));
        certainNames.setShortLabel("certain");
        certainNames.setFullName("certain sequence position");
        certain.setNames(certainNames);
        certain.setXref(certainXref);

        RangeStatus interval = new RangeStatus();
        Xref intervalXref = new Xref();
        Names intervalNames = new Names();
        intervalXref.setPrimaryRef(new DbReference("psi-mi", "MI:0488", RangeUtils.MI_RANGE_INTERVAL, "identity", "MI:0356"));
        intervalNames.setShortLabel("range");
        intervalNames.setFullName("range");
        interval.setNames(intervalNames);
        interval.setXref(intervalXref);

        RangeStatus cTerm = new RangeStatus();
        Xref cTermXref = new Xref();
        Names cTermNames = new Names();
        cTermXref.setPrimaryRef(new DbReference("psi-mi", "MI:0488", RangeUtils.MI_RANGE_C_TERM, "identity", "MI:0356"));
        cTermNames.setShortLabel("c-term range");
        cTermNames.setFullName("c-terminal range");
        cTerm.setNames(cTermNames);
        cTerm.setXref(cTermXref);

        RangeStatus nTerm = new RangeStatus();
        Xref nTermXref = new Xref();
        Names nTermNames = new Names();
        nTermXref.setPrimaryRef(new DbReference("psi-mi", "MI:0488", RangeUtils.MI_RANGE_N_TERM, "identity", "MI:0356"));
        nTermNames.setShortLabel("n-term range");
        nTermNames.setFullName("n-terminal range");
        nTerm.setNames(nTermNames);
        nTerm.setXref(nTermXref);

        ranges.add(new Range(undetermined, undetermined, null, null)); //"? - ?"
        ranges.add(new Range(certain, new Position(20), undetermined, null)); //"20 - ?"
        ranges.add(new Range(undetermined, (Position) null, certain, new Position(20))); //"? - 20"
        ranges.add(new Range(nTerm, undetermined, null, null)); //"n - ?"
        ranges.add(new Range(undetermined, cTerm, null, null)); //"? - c"
        ranges.add(new Range(nTerm, nTerm, null, null)); //"n - n"
        ranges.add(new Range(cTerm, cTerm, null, null)); //"c - c"
        ranges.add(new Range(nTerm, (Position) null, certain, new Position(20))); //"n - 20"
        ranges.add(new Range(certain, new Position(20), cTerm, null)); //"20 - c"
        ranges.add(new Range(new Interval(25, 25), new Interval(23, 23), interval, interval)); //"23..23 - 25..25"
        ranges.add(new Range(new Interval(32, 36), new Interval(23, 25), interval, interval)); //"23..25 - 32..36"
        ranges.add(new Range(greaterThan, new Position(20), greaterThan, new Position(20))); //">20 - >20"
        ranges.add(new Range(lessThan, new Position(20), lessThan, new Position(20))); //"<20 - <20"
        ranges.add(new Range(greaterThan, new Position(20), lessThan, new Position(40))); //">20 - <40"
        ranges.add(new Range(lessThan, new Position(20), greaterThan, new Position(40))); //"<20 - >40"
        ranges.add(new Range(certain, new Position(17), lessThan, new Position(20))); //"17 - <20"
        ranges.add(new Range(lessThan, new Position(17), certain, new Position(20))); //"<17 - 20"
        ranges.add(new Range(certain, new Position(17), greaterThan, new Position(20))); //"17 - >20"
        ranges.add(new Range(greaterThan, new Position(17), certain, new Position(20))); //">17 - 20"
        ranges.add(new Range(certain, new Position(17), certain, new Position(20))); //"17 - 20"


        return ranges;
    }

    @Test
    public void testFromMitab() throws Exception {


        Collection<Range> xmlRanges = RangeUtils.fromMitab(tabGoodRanges);
        Assert.assertEquals(xmlGoodRanges.size(), xmlRanges.size());

        Iterator<Range> iterator = xmlGoodRanges.iterator();
        for (Range xmlRange : xmlRanges) {
            Assert.assertEquals(iterator.next(), xmlRange);
        }

    }


    @Test(expected = ConverterException.class)
    public void testFromMitabException() throws Exception {
        RangeUtils.fromMitab(null);
    }

    @Test(expected = ConverterException.class)
    public void testFromMitabException1() throws Exception {
        List<String> tabBadRanges = Arrays.asList("x-n");
        RangeUtils.fromMitab(tabBadRanges);
    }

    @Test(expected = ConverterException.class)
    public void testFromMitabException2() throws Exception {
        List<String> tabBadRanges = Arrays.asList("c-x");
        RangeUtils.fromMitab(tabBadRanges);
    }

    @Test(expected = ConverterException.class)
    public void testFromMitabException3() throws Exception {
        List<String> tabBadRanges = Arrays.asList("b-n");
        RangeUtils.fromMitab(tabBadRanges);
    }

    @Test(expected = ConverterException.class)
    public void testFromMitabException4() throws Exception {
        List<String> tabBadRanges = Arrays.asList("2.0-n");
        RangeUtils.fromMitab(tabBadRanges);
    }

    @Test(expected = ConverterException.class)
    public void testFromMitabException5() throws Exception {
        List<String> tabBadRanges = Arrays.asList("c-2.0");
        RangeUtils.fromMitab(tabBadRanges);
    }

    @Test(expected = ConverterException.class)
    public void testFromMitabException6() throws Exception {
        List<String> tabBadRanges = Arrays.asList("17>-17>");
        RangeUtils.fromMitab(tabBadRanges);
    }


    @Test
    public void testToMitab() throws Exception {

        List<String> tabRanges = RangeUtils.toMitab(xmlGoodRanges);
        Assert.assertEquals(tabGoodRanges.size(), tabRanges.size());

        Iterator<String> iterator = tabGoodRanges.iterator();
        for (String tabRange : tabRanges) {
            Assert.assertEquals(iterator.next(), tabRange);
        }

    }

    @Test(expected = TabConversionException.class)
    public void testToMitabException1() throws Exception {

        List<Range> ranges = new ArrayList<Range>();

        RangeStatus certain = new RangeStatus();
        Xref certainXref = new Xref();
        Names certainNames = new Names();
        certainXref.setPrimaryRef(new DbReference("psi-mi", "MI:0488", RangeUtils.MI_RANGE_CERTAIN, "identity", "MI:0356"));
        certainNames.setShortLabel("certain");
        certainNames.setFullName("certain sequence position");
        certain.setNames(certainNames);
        certain.setXref(certainXref);

        RangeStatus undetermined = new RangeStatus();
        Xref undeterminedXref = new Xref();
        Names undeterminedNames = new Names();
        undeterminedXref.setPrimaryRef(new DbReference("psi-mi", "MI:0488", RangeUtils.MI_RANGE_UNDETERMINED, "identity", "MI:0356"));
        undeterminedNames.setShortLabel("undetermined");
        undeterminedNames.setFullName("undetermined sequence position");
        undetermined.setNames(undeterminedNames);
        undetermined.setXref(undeterminedXref);


        //End Status is null
        ranges.add(new Range(undetermined, (Position) null, null, new Position(20)));
        RangeUtils.toMitab(ranges);

    }

    @Test(expected = TabConversionException.class)
    public void testToMitabException2() throws Exception {

        List<Range> ranges = new ArrayList<Range>();

        RangeStatus certain = new RangeStatus();
        Xref certainXref = new Xref();
        Names certainNames = new Names();
        certainXref.setPrimaryRef(new DbReference("psi-mi", "MI:0488", RangeUtils.MI_RANGE_CERTAIN, "identity", "MI:0356"));
        certainNames.setShortLabel("certain");
        certainNames.setFullName("certain sequence position");
        certain.setNames(certainNames);
        certain.setXref(certainXref);

        RangeStatus undetermined = new RangeStatus();
        Xref undeterminedXref = new Xref();
        Names undeterminedNames = new Names();
        undeterminedXref.setPrimaryRef(new DbReference("psi-mi", "MI:0488", RangeUtils.MI_RANGE_UNDETERMINED, "identity", "MI:0356"));
        undeterminedNames.setShortLabel("undetermined");
        undeterminedNames.setFullName("undetermined sequence position");
        undetermined.setNames(undeterminedNames);
        undetermined.setXref(undeterminedXref);


        //End Position is null
        ranges.add(new Range(undetermined, (Position) null, certain, null));
        RangeUtils.toMitab(ranges);

    }

    @Test(expected = TabConversionException.class)
    public void testToMitabException3() throws Exception {

        List<Range> ranges = new ArrayList<Range>();

        RangeStatus certain = new RangeStatus();
        Xref certainXref = new Xref();
        Names certainNames = new Names();
        certainXref.setPrimaryRef(new DbReference("psi-mi", "MI:0488", RangeUtils.MI_RANGE_CERTAIN, "identity", "MI:0356"));
        certainNames.setShortLabel("certain");
        certainNames.setFullName("certain sequence position");
        certain.setNames(certainNames);
        certain.setXref(certainXref);


        //Start Status is null
        ranges.add(new Range( null, new Position(20), certain, new Position(20)));
        RangeUtils.toMitab(ranges);

    }

    @Test(expected = TabConversionException.class)
    public void testToMitabException5() throws Exception {

        List<Range> ranges = new ArrayList<Range>();

        RangeStatus certain = new RangeStatus();
        Xref certainXref = new Xref();
        Names certainNames = new Names();
        certainXref.setPrimaryRef(new DbReference("psi-mi", "MI:0488", RangeUtils.MI_RANGE_CERTAIN, "identity", "MI:0356"));
        certainNames.setShortLabel("certain");
        certainNames.setFullName("certain sequence position");
        certain.setNames(certainNames);
        certain.setXref(certainXref);

        RangeStatus undetermined = new RangeStatus();
        Xref undeterminedXref = new Xref();
        Names undeterminedNames = new Names();
        undeterminedXref.setPrimaryRef(new DbReference("psi-mi", "MI:0488", RangeUtils.MI_RANGE_UNDETERMINED, "identity", "MI:0356"));
        undeterminedNames.setShortLabel("undetermined");
        undeterminedNames.setFullName("undetermined sequence position");
        undetermined.setNames(undeterminedNames);
        undetermined.setXref(undeterminedXref);


        //End Status is null
        ranges.add(new Range(certain, (Position) null, undetermined, null));
        RangeUtils.toMitab(ranges);

    }
}
