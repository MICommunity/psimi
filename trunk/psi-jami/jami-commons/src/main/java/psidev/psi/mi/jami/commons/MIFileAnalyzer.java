package psidev.psi.mi.jami.commons;

import java.io.*;

/**
 * The file source analyzer will recognise what kind of molecular interaction source a given file is from.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class MIFileAnalyzer {

    public static String XML_EXTENSION=".xml";
    public static String TXT_EXTENSION=".txt";
    public static String CSV_EXTENSION=".csv";
    public static String TSV_EXTENSION=".tsv";

    public static final String MITAB_25_TITLE="#ID(s) interactor A\tID(s) interactor B\tAlt. ID(s) interactor A\tAlt. ID(s) interactor B\tAlias(es) interactor A\tAlias(es) interactor B\tInteraction detection method(s)\tPublication 1st author(s)\tPublication Identifier(s)\tTaxid interactor A\tTaxid interactor B\tInteraction type(s)\tSource database(s)\tInteraction identifier(s)\tConfidence value(s)";

    /**
     * Recognize the MIFileDataSource from the file signature and first line.
     * It will recognize psi25-xml and mitab files. If it is neither of them, it will return  MIFileType.other
     * @param file : the file to analyze
     * @return the MIFileType that matches the file
     * @throws java.io.IOException
     */
    public MIFileType identifyMIFileTypeFor(File file) throws IOException {

        if (file == null){
            throw new FileNotFoundException("The file cannot be null.");
        }
        else if (isFileSignature(file, XML_EXTENSION)){
            // check xml files
            BufferedReader reader = new BufferedReader(new FileReader(file));

            try{
                String line = reader.readLine();

                if (line == null){
                    return MIFileType.other;
                }

                // skip empty lines or break lines or xml encode line
                while (line != null && (line.trim().length() == 0 || line.trim().equals(System.getProperty("line.separator")) || line.trim().startsWith("<?xml"))){
                    line = reader.readLine();
                }

                if (line == null){
                    return MIFileType.other;
                }
                else if (line.startsWith("<entrySet")){
                    return MIFileType.psi25_xml;
                }
                else{
                    return MIFileType.other;
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
                    return MIFileType.other;
                }

                // skip empty lines or break lines
                while (line != null && (line.trim().length() == 0 || line.trim().equals(System.getProperty("line.separator")))){
                    line = reader.readLine();
                }

                if (line == null){
                    return MIFileType.other;
                }
                else if (line.toLowerCase().trim().startsWith(MITAB_25_TITLE.toLowerCase())){
                    return MIFileType.mitab;
                }
                else if (line.contains("\t")){
                    return MIFileType.mitab;
                }
                else{
                    return MIFileType.other;
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
                    return MIFileType.other;
                }

                // skip empty lines or break lines or xml encode line
                while (line != null && (line.trim().length() == 0 || line.trim().equals(System.getProperty("line.separator")))){
                    line = reader.readLine();
                }

                if (line == null){
                    return MIFileType.other;
                }
                // we have xml
                else if (line.trim().startsWith("<?xml")){
                    line = reader.readLine();
                    if (line != null && line.startsWith("<entrySet")){
                        return MIFileType.psi25_xml;
                    }
                    else {
                        return MIFileType.other;
                    }
                }
                else if (line.startsWith("<entrySet")){
                    return MIFileType.psi25_xml;
                }
                else if (line.toLowerCase().trim().startsWith(MITAB_25_TITLE.toLowerCase())){
                    return MIFileType.mitab;
                }
                else if (line.contains("\t")){
                    return MIFileType.mitab;
                }
                else{
                    return MIFileType.other;
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

    /**
     * Recognize the MIFileDataSource from the inputStream.
     * Because it needs to open the inputStream to analyze its content, it will return an OpenedInputStream which contains the MIFileType and
     * a copy of the inputStream it opened which should be used instead of the given 'stream' which have been opened and closed. As for a normal InputStream, it needs to be closed after being used.
     * It will recognize psi25-xml and mitab files. If it is neither of them, it will return an OpenedInputStream MIFileType.other
     *
     * @param stream : the stream to recognize
     * @return
     * @throws java.io.IOException
     */
    public OpenedInputStream extractMIFileTypeAndCopiedInputStream(InputStream stream) throws IOException {

        if (stream == null){
            throw new IOException("The input stream cannot be null.");
        }
        else {

            File temp = MIFileUtils.storeAsTemporaryFile(stream, "molecular_interaction_stream" + System.currentTimeMillis(), ".txt");
            return readTemporaryFile(temp);


        }
    }

    /**
     * Recognize the MIFileDataSource from the reader.
     * Because it needs to open the reader to analyze its content, it will return an OpenedInputStream which contains the MIFileType and
     * a copy of the inputStream it opened which should be used instead of the given 'stream' which have been opened and closed. As for a normal InputStream, it needs to be closed after being used.
     * It will recognize psi25-xml and mitab files. If it is neither of them, it will return an OpenedInputStream MIFileType.other
     *
     * @param reader : the reader for the source to recognize
     * @return
     * @throws java.io.IOException
     */
    public OpenedInputStream extractMIFileTypeAndCopiedInputStream(Reader reader) throws IOException {

        if (reader == null){
            throw new IOException("The input reader cannot be null.");
        }
        else {

            File temp = MIFileUtils.storeAsTemporaryFile(reader, "molecular_interaction_stream" + System.currentTimeMillis(), ".txt");

            // check first line
            return readTemporaryFile(temp);
        }
    }

    /**
     * Recognize the MIFileDataSource from the inputStream.
     * Because it needs to open the inputStream to analyze its content, it will consume the provided stream.
     * It will recognize psi25-xml and mitab files. If it is neither of them, it will return a MIFileType.other
     *
     * @param stream : the stream to recognize
     * @return
     * @throws java.io.IOException
     */
    public MIFileType identifyMIFileTypeFor(InputStream stream) throws IOException {

        if (stream == null){
            throw new IOException("The input stream cannot be null.");
        }
        else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            MIFileType type = MIFileType.other;
            try{
                type = identifyMIFileTypeAndConsume(reader);
            }
            finally{
                reader.close();
            }

            return type;
        }
    }

    /**
     * Recognize the MIFileDataSource from the reader.
     * Because it needs to read the first line to analyze its content, it will consume the provided reader.
     * It does not need a BufferedReader as it creates a new BufferedReader from this reader
     * It will recognize psi25-xml and mitab files. If it is neither of them, it will return a MIFileType.other
     * @param reader : the reader for the source to recognize
     * @return
     * @throws java.io.IOException
     */
    public MIFileType identifyMIFileTypeFor(Reader reader) throws IOException {

        if (reader == null){
            throw new IOException("The input reader cannot be null.");
        }
        else {
            BufferedReader reader2 = new BufferedReader(reader);
            MIFileType type = MIFileType.other;
            try{
                type = identifyMIFileTypeAndConsume(reader2);
            }
            finally{
                reader2.close();
            }

            return type;
        }
    }

    private MIFileType identifyMIFileTypeAndConsume(BufferedReader reader) throws IOException {
        // check first line
        String line = reader.readLine();

        if (line == null){
            return MIFileType.other;
        }

        // skip empty lines or break lines or xml encode line
        while (line != null && (line.trim().length() == 0 || line.trim().equals(System.getProperty("line.separator")))){
            line = reader.readLine();
        }

        if (line == null){
            return MIFileType.other;
        }
        // we have xml
        else if (line.trim().startsWith("<?xml")){
            line = reader.readLine();
            if (line.startsWith("<entrySet")){
                return MIFileType.psi25_xml;
            }
            else {
                return MIFileType.other;
            }
        }
        else if (line.startsWith("<entrySet")){
            return MIFileType.psi25_xml;
        }
        else if (line.toLowerCase().trim().startsWith(MITAB_25_TITLE.toLowerCase())){
            return MIFileType.mitab;
        }
        else if (line.contains("\t")){
            return MIFileType.mitab;
        }
        else{
            return MIFileType.other;
        }
    }

    private OpenedInputStream readTemporaryFile(File temp) throws IOException {
        // check first line
        BufferedReader reader = new BufferedReader(new FileReader(temp));

        try {
            MIFileType type = identifyMIFileTypeAndConsume(reader);
            return new OpenedInputStream(temp, type);
        }
        finally {
            reader.close();
        }
    }
}
