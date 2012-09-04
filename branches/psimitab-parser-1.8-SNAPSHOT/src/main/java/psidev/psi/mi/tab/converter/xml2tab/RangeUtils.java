package psidev.psi.mi.tab.converter.xml2tab;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 04/07/2012
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
public class RangeUtils {

//    For help only, fields in the Range

//    private RangeStatus startStatus;
//    private Position begin;
//    private Interval beginInterval;
//    private RangeStatus endStatus;
//    private Position end;
//    private Interval endInterval;
//    private Boolean isLink;

	protected static final String RANGE_SEPARATOR = "-";
	protected static final String RANGE_UNDETERMINED = "?";
	protected static final String RANGE_INTERVAL = "..";
	protected static final String RANGE_GREATER = ">";
	protected static final String RANGE_LESS = "<";
	protected static final String RANGE_N_TERM = "n";
	protected static final String RANGE_C_TERM = "c";

	protected static final String MI_RANGE_CERTAIN = "MI:0335";
	protected static final String MI_RANGE_UNDETERMINED = "MI:0339";
	protected static final String MI_RANGE_INTERVAL = "MI:0338";
	protected static final String MI_RANGE_GREATER = "MI:0336";
	protected static final String MI_RANGE_LESS = "MI:0337";
	protected static final String MI_RANGE_N_TERM = "MI:1040";
	protected static final String MI_RANGE_C_TERM = "MI:1039";

	private static final int START = 0;
	private static final int END = 1;

	public static final Log log = LogFactory.getLog(RangeUtils.class);


	//TODO Review with Marine

	/**
	 * It converts a list of ranges from MITAB to PSI XMl
	 *
	 * @param ranges the list of Strings
	 * @return a collection of xml Range objects
	 * @throws ConverterException
	 */
	public static Collection<Range> fromMitab(List<String> ranges) throws ConverterException {

		if (ranges == null || ranges.isEmpty()) {
			throw new ConverterException("The ranges list can not be empty or null");
		}

		Collection<Range> xmlRanges = new ArrayList<Range>();

		for (String range : ranges) {
			if (range.contains(RANGE_SEPARATOR)) {
				// The range has a begin and a end
				String[] positions = range.split(RANGE_SEPARATOR);
				if (positions.length != 2) {
					throw new ConverterException("The range is bad formatted." + ArrayUtils.toString(positions));
				} else {
					//The length is two
					Range oneXmlRange = parseRange(positions);
					if (oneXmlRange != null) {
						xmlRanges.add(oneXmlRange);
					}
				}

			} else {
				//TODO we can have a range without the - minus symbol?
			}

		}

		return xmlRanges;
	}

	/**
	 * It converts a list of ranges from PSI XML to MITAB
	 *
	 * @param ranges the collection of xml Range objects
	 * @return a list of Strings
	 * @throws TabConversionException
	 */
	public static List<String> toMitab(Collection<Range> ranges) throws TabConversionException {

		if (ranges == null || ranges.isEmpty()) {
			throw new TabConversionException("The ranges list can not be empty or null");
		}

		List<String> tabRanges = new ArrayList<String>();

		for (Range range : ranges) {
			if (!range.isLink()) {
				StringBuilder stringBuilder = new StringBuilder();

				stringBuilder.append(buildBeginOrEnd(range, START));
				stringBuilder.append(RANGE_SEPARATOR);
				stringBuilder.append(buildBeginOrEnd(range, END));

				tabRanges.add(stringBuilder.toString());

			} else {
				log.warn("Linked features/ranges can not be converted to mitab.");
			}
		}

		return tabRanges;
	}

	private static Range parseRange(String[] positions) throws ConverterException {

		if (positions.length != 2) {
			throw new ConverterException("The range is bad formatted." + ArrayUtils.toString(positions));
		}


		Range range = new Range();


		for (int i = 0; i < positions.length; i++) {

			Names names = new Names();

			DbReference rangeRef = new DbReference();
			rangeRef.setDb("psi-mi");
			rangeRef.setDbAc("MI:0488");
			rangeRef.setRefType("identity");
			rangeRef.setRefTypeAc("MI:0356");

			Xref xref = new Xref();
			xref.setPrimaryRef(rangeRef);

			String position = positions[i];

			RangeStatus status = new RangeStatus();
			status.setNames(names);
			status.setXref(xref);

			if (position.contains(RANGE_INTERVAL)) {
				//We have a range
				names.setShortLabel("range");
				names.setFullName("range");

				rangeRef.setId(MI_RANGE_INTERVAL);

				String[] interval = position.split("\\.\\.");

				if (interval.length != 2) {
					throw new ConverterException("The range is bad formatted. Interval: " + ArrayUtils.toString(interval));
				} else {
					try {
						//We are assumed that the range only can have longs

						Long start = Long.valueOf(interval[START]);
						Long end = Long.valueOf(interval[END]);

						if (i == START) {
							range.setBeginInterval(new Interval(start, end));
						} else if (i == END) {
							range.setEndInterval(new Interval(start, end));
						}
					} catch (Exception e) {
						throw new ConverterException("The range is bad formatted. Start:" + interval[START] + "End:" + interval[END]);
					}

				}

			} else {
				Position value = null;

				if (position.startsWith(RANGE_GREATER)) {

					names.setShortLabel("greater-than");
					names.setFullName("greater-than");

					rangeRef.setId(MI_RANGE_GREATER);

					value = removeFirstCharAndConvert(position);

				} else if (position.startsWith(RANGE_LESS)) {

					names.setShortLabel("less-than");
					names.setFullName("less-than");

					rangeRef.setId(MI_RANGE_LESS);

					value = removeFirstCharAndConvert(position);

				} else if (position.equals(RANGE_N_TERM)) {

					names.setShortLabel("n-term range");
					names.setFullName("n-terminal range");


					rangeRef.setId(MI_RANGE_N_TERM);

				} else if (position.equals(RANGE_C_TERM)) {

					names.setShortLabel("c-term range");
					names.setFullName("c-terminal range");


					rangeRef.setId(MI_RANGE_C_TERM);


				} else if (position.equals(RANGE_UNDETERMINED)) {

					names.setShortLabel("undetermined");
					names.setFullName("undetermined sequence position");

					rangeRef.setId(MI_RANGE_UNDETERMINED);


				} else {
					// It is only a number

					names.setShortLabel("certain");
					names.setFullName("certain sequence position");

					rangeRef.setId(MI_RANGE_CERTAIN);

					//It is a certain position (o the expression is wrong)
					try {
						value = new Position(Long.valueOf(position));
					} catch (Exception e) {
						throw new ConverterException("The range is a certain sequence position but is bad formatted. Value:" + value);
					}
				}

				if (i == START) {
					range.setBegin(value);
				} else if (i == END) {
					range.setEnd(value);
				}
			}

			if (i == START) {
				range.setStartStatus(status);
			} else if (i == END) {
				range.setEndStatus(status);
			}
		}


		//Now we don't support linked features
		//range.setIsLink(false);

		return range;
	}


	private static String buildBeginOrEnd(Range range, int startOrEnd) throws TabConversionException {

		StringBuilder stringBuilder = new StringBuilder();

		CvType status;

		if (startOrEnd == START) {
			status = range.getStartStatus();
		} else {//if (startOrEnd == END)
			status = range.getEndStatus();
		}

		if (status != null && status.getXref() != null) {
			DbReference dbReference = status.getXref().getPrimaryRef();
			if (dbReference != null) {
				String id = dbReference.getId();

				if (id.equalsIgnoreCase(MI_RANGE_N_TERM)) {
					stringBuilder.append(RANGE_N_TERM);

				} else if (id.equalsIgnoreCase(MI_RANGE_C_TERM)) {
					stringBuilder.append(RANGE_C_TERM);

				} else if (id.equalsIgnoreCase(MI_RANGE_GREATER)) {
					stringBuilder.append(RANGE_GREATER);
					stringBuilder.append(buildPosition(range, startOrEnd));

				} else if (id.equalsIgnoreCase(MI_RANGE_LESS)) {
					stringBuilder.append(RANGE_LESS);
					stringBuilder.append(buildPosition(range, startOrEnd));

				} else if (id.equalsIgnoreCase(MI_RANGE_UNDETERMINED)) {
					stringBuilder.append(RANGE_UNDETERMINED);

				} else if (id.equalsIgnoreCase(MI_RANGE_CERTAIN)) {
					stringBuilder.append(buildPosition(range, startOrEnd));

				} else if (id.equalsIgnoreCase(MI_RANGE_INTERVAL)) {

					if (startOrEnd == START) {
						if (range.hasBeginInterval()) {
							Interval startInterval = range.getBeginInterval();
							stringBuilder.append(startInterval.getBegin());
							stringBuilder.append("..");
							stringBuilder.append(startInterval.getEnd());
						} else {
							throw new TabConversionException("The range must have a valid start interval.");
						}
					} else {//if (startOrEnd == END)
						if (range.hasEndInterval()) {
							Interval endInterval = range.getEndInterval();
							stringBuilder.append(endInterval.getBegin());
							stringBuilder.append("..");
							stringBuilder.append(endInterval.getEnd());
						} else {
							throw new TabConversionException("The range must have a valid end interval.");
						}
					}
				}

			} else {
				throw new TabConversionException("The range can not be converter because has not a valid reference.");
			}
		} else {
			throw new TabConversionException("The range can not be converter because has not a valid status.");
		}

		return stringBuilder.toString();
	}

	private static String buildPosition(Range range, int startOrEnd) throws TabConversionException {

		String res;

		if (startOrEnd == START) {
			if (range.hasBegin()) {
				res = String.valueOf((range.getBegin().getPosition()));
			} else {
				throw new TabConversionException("The range must have a valid start position.");
			}
		} else {//if (startOrEnd == END)
			if (range.hasEnd()) {
				res = String.valueOf((range.getEnd().getPosition()));
			} else {
				throw new TabConversionException("The range must have a valid end position.");
			}
		}
		return res;
	}

	/**
	 * It allows deleting the > or < char and create the Position
	 *
	 * @param stringPosition string to process
	 * @return The new Position
	 */
	private static Position removeFirstCharAndConvert(String stringPosition) throws ConverterException {

		Position rangePosition;

		if (stringPosition.length() > 1) {
			String position = stringPosition.substring(1);
			if (!position.equals("")) {
				//We have a number no a empty String
				try {
					rangePosition = new Position(Long.valueOf(position));
				} catch (Exception e) {
					throw new ConverterException("The range is bad formatted. Value:" + position);
				}
			} else
				throw new ConverterException("The range is bad formatted. Value:" + position);
		} else
			throw new ConverterException("The range is bad formatted. Value:" + stringPosition);

		return rangePosition;
	}
}
