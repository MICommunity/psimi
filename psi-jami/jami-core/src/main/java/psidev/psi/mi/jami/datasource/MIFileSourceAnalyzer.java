package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.utils.MIFileDataSourceUtils;

import java.io.*;

/**
 * The file source analyzer will recognise what kind of molecular interaction source a given file is from.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class MIFileSourceAnalyzer {

    public static String XML_EXTENSION=".xml";
    public static String TXT_EXTENSION=".txt";
    public static String CSV_EXTENSION=".csv";
    public static String TSV_EXTENSION=".tsv";

    public static final String MITAB_25_TITLE="#ID(s) interactor A\tID(s) interactor B\tAlt. ID(s) interactor A\tAlt. ID(s) interactor B\tAlias(es) interactor A\tAlias(es) interactor B\tInteraction detection method(s)\tPublication 1st author(s)\tPublication Identifier(s)\tTaxid interactor A\tTaxid interactor B\tInteraction type(s)\tSource database(s)\tInteraction identifier(s)\tConfidence value(s)";

    /**
     * Recognize the MIFileDataSource from the file signature and first line.
     * It will recognize psi25-xml and mitab files. If it is neither of them, it will return  MIFileSourceType.other
     * @param file : the file to analyze
     * @return the MIFileSourceType that matches the file
     * @throws IOException
     */
    public MIFileSourceType getMolecularInteractionSourceFor(File file) throws IOException {

        if (file == null){
            throw new FileNotFoundException("The file cannot be null.");
        }
        else if (isFileSignature(file, XML_EXTENSION)){
            // check xml files
            BufferedReader reader = new BufferedReader(new FileReader(file));

            try{
                String line = reader.readLine();

                if (line == null){
                    return MIFileSourceType.other;
                }

                // skip empty lines or break lines or xml encode line
                while (line != null && (line.trim().length() == 0 || line.trim().equals(System.getProperty("line.separator")) || line.trim().startsWith("<?xml"))){
                    line = reader.readLine();
                }

                if (line == null){
                    return MIFileSourceType.other;
                }
                else if (line.startsWith("<entrySet")){
                    return MIFileSourceType.psi25_xml;
                }
                else{
                    return MIFileSourceType.other;
                }
            }
            finally {
                reader.close();
            }
        }
        else if (isFileSignature(file, TXT_EXTENSION) || isFileSignature(file, CSV_EXTENSION) || isFileSignature(file, TSV_EXTENSION)){
            // check text formats
            BufferedReader reader = new BufferedReader(new FileReader(file));

            try{
                String line = reader.readLine();

                if (line == null){
                    return MIFileSourceType.other;
                }

                // skip empty lines or break lines
                while (line != null && (line.trim().length() == 0 || line.trim().equals(System.getProperty("line.separator")))){
                    line = reader.readLine();
                }

                if (line == null){
                    return MIFileSourceType.other;
                }
                else if (line.toLowerCase().trim().startsWith(MITAB_25_TITLE.toLowerCase())){
                    return MIFileSourceType.mitab;
                }
                else if (line.contains("\t")){
                    return MIFileSourceType.mitab;
                }
                else{
                    return MIFileSourceType.other;
                }
            }
            finally {
                reader.close();
            }
        }
        else {
            // check xml files
            BufferedReader reader = new BufferedReader(new FileReader(file));

            try{
                String line = reader.readLine();

                if (line == null){
                    return MIFileSourceType.other;
                }

                // skip empty lines or break lines or xml encode line
                while (line != null && (line.trim().length() == 0 || line.trim().equals(System.getProperty("line.separator")))){
                    line = reader.readLine();
                }

                if (line == null){
                    return MIFileSourceType.other;
                }
                // we have xml
                else if (line.trim().startsWith("<?xml")){
                    line = reader.readLine();
                    if (line.startsWith("<entrySet")){
                        return MIFileSourceType.psi25_xml;
                    }
                    else {
                        return MIFileSourceType.other;
                    }
                }
                else if (line.startsWith("<entrySet")){
                    return MIFileSourceType.psi25_xml;
                }
                else if (line.toLowerCase().trim().startsWith(MITAB_25_TITLE.toLowerCase())){
                    return MIFileSourceType.mitab;
                }
                else if (line.contains("\t")){
                    return MIFileSourceType.mitab;
                }
                else{
                    return MIFileSourceType.other;
                }
            }
            finally {
                reader.close();
            }
        }
    }

    /**
     * Recognize the MIFileDataSource from the inputStream.
     * Because it needs to open the inputStream to analyze its content, it will return an OpenedInputStream which contains the MIFileSourceType and
     * a copy of the inputStream it opened which should be used instead of the given 'stream' which have been opened and closed. As for a normal InputStream, it needs to be closed after being used.
     * It will recognize psi25-xml and mitab files. If it is neither of them, it will return an OpenedInputStream MIFileSourceType.other
     *
     * @param stream : the stream to recognize
     * @return
     * @throws IOException
     */
    public OpenedInputStream getMolecularInteractionSourceFor(InputStream stream) throws IOException {

        if (stream == null){
            throw new IOException("The input stream cannot be null.");
        }
        else {

            File temp = MIFileDataSourceUtils.storeAsTemporaryFile(stream, "molecular_interaction_stream" + System.currentTimeMillis(), ".txt");

            // check first line
            BufferedReader reader = new BufferedReader(new FileReader(temp));

            try {
                String line = reader.readLine();

                if (line == null){
                    return new OpenedInputStream(temp, MIFileSourceType.other);
                }

                // skip empty lines or break lines or xml encode line
                while (line != null && (line.trim().length() == 0 || line.trim().equals(System.getProperty("line.separator")))){
                    line = reader.readLine();
                }

                if (line == null){
                    return new OpenedInputStream(temp, MIFileSourceType.other);
                }
                // we have xml
                else if (line.trim().startsWith("<?xml")){
                    line = reader.readLine();
                    if (line.startsWith("<entrySet")){
                        return new OpenedInputStream(temp, MIFileSourceType.psi25_xml);
                    }
                    else {
                        return new OpenedInputStream(temp, MIFileSourceType.other);
                    }
                }
                else if (line.startsWith("<entrySet")){
                    return new OpenedInputStream(temp,  MIFileSourceType.psi25_xml);
                }
                else if (line.toLowerCase().trim().startsWith(MITAB_25_TITLE.toLowerCase())){
                    return new OpenedInputStream(temp,  MIFileSourceType.mitab);
                }
                else if (line.contains("\t")){
                    return new OpenedInputStream(temp,  MIFileSourceType.mitab);
                }
                else{
                    return new OpenedInputStream(temp, MIFileSourceType.other);
                }
            }
            finally {
                reader.close();
            }
        }
    }

    public boolean isFileSignature(File file, String signature){
        if (file == null || signature == null){
            return false;
        }
        else if (file.getName().endsWith(signature)){
            return true;
        }
        else {
            return false;
        }
    }
}
