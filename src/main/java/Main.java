import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args)  {
        String csvInFilePath = "C:\\Automation\\CsvFiles\\CsvInput.csv";
        String csvOutFilePath;

        if (args.length == 0){
            System.out.println("App has no csv file path, using default path as C:\\Automation\\CsvFiles\\CsvInput.csv\n" +
                    "Output file will be saved in same folder as input. " +
                    "If you want use custom file path add params -path/to/csv/file");
        } else {
            csvInFilePath = args[0];
            System.out.println("Using custom csv path " + args[0]);
        }
        csvOutFilePath = csvInFilePath.replace("CsvInput", "CsvOutput");

        ArrayList<List<String>> records;
        CsvHelper csvHelper = new CsvHelper(csvInFilePath, csvOutFilePath);
        records = csvHelper.readCsvFile();

        CsvLogic csvLogic = new CsvLogic(records);

        csvLogic.cleanEmptyCells();
        csvLogic.addColumnTitles();
        csvLogic.calculateExpressionResult();
        csvLogic.checkPalindrome();
        csvLogic.addReversedString();

        csvHelper.writeCsvFile(csvLogic.getRecords());
    }
}
