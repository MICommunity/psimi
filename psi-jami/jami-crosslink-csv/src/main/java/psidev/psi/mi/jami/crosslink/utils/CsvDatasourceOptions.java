package psidev.psi.mi.jami.crosslink.utils;

import psidev.psi.mi.jami.factory.options.MIFileDataSourceOptions;

/**
 * Options for CSV datasource which extends MIFileDataSource option
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/14</pre>
 */

public class CsvDatasourceOptions extends MIFileDataSourceOptions{

    /**
     * The type of crosslink CSV file to read.
     * The option value should be an enum of type CsvType.
     * If this option is not provided, the value by default is CsvType.mix
     */
    public static final String CSV_TYPE_OPTION_KEY = "crosslink_csv_type_key";
}
