import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dmitriy on 7/12/19.
 */
public class CsvHelper {
    private FileReader fileReader = null;
    private String inputFilePath;
    private String outputFilePath;

    public CsvHelper(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }

    public ArrayList<List<String>> readCsvFile(){
        ArrayList<List<String>> records = new ArrayList<List<String>>();

        try {
            fileReader = new FileReader(inputFilePath);
            CSVReader csvReader = new CSVReader(fileReader);
            String[] values;

            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }

        } catch (IOException e){
            System.out.println("Wasn't able to reach csv file by path " + inputFilePath);
        }

        return records;
    }

    public void writeCsvFile(ArrayList<List<String>> records) {
        FileWriter fileWriter;

        try {
            fileWriter = new FileWriter(outputFilePath);
            CSVWriter csvWriter = new CSVWriter(fileWriter);

            for (List<String> rowData : records) {
                csvWriter.writeNext(rowData.toArray(new String[rowData.size()]));
            }

            csvWriter.flush();
            csvWriter.close();

        } catch (IOException e) {
            System.out.println("Wasn't able to write csv file with path " + outputFilePath);
        }
    }
}
