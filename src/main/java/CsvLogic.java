import org.apache.commons.lang3.math.NumberUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmitriy on 7/12/19.
 */
public class CsvLogic {
    private final static String[] resultColumns = {"Expression Result", "Is Palindrome", "Reversed String"};
    private ArrayList<List<String>> records;

    public CsvLogic(ArrayList<List<String>> records) {
        this.records = records;
    }

    public  ArrayList<List<String>> getRecords(){
        return this.records;
    }

    public void cleanEmptyCells(){
        ArrayList<List<String>> cleanRecords = new ArrayList<List<String>>();

        int i = 0;
        for (List<String> rowData : records) {
            cleanRecords.add(new ArrayList<String>());
            for (String cell : rowData){
                if (cell.length() != 0){
                    cleanRecords.get(i).add(cell);
                }
            }
            i++;
        }

        this.records = cleanRecords;
    }

    public void addColumnTitles(){
        final String titleColumnIndicator = "A, B, C";

        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).toString().contains(titleColumnIndicator)){
                for (String column : CsvLogic.resultColumns){
                    records.get(i).add(column);
                }
                break;
            }
        }
    }

    public void calculateExpressionResult(){
        int aColumn = getColumnNumber( "A");
        int bColumn = getColumnNumber( "B");

        for (List<String> row : records){
            if (row.size() >= 2) {
                String aString = row.get(aColumn);
                String bString = row.get(bColumn);
                if (NumberUtils.isParsable(aString) && NumberUtils.isParsable(bString)) {
                    float a = Float.parseFloat(aString);
                    float b = Float.parseFloat(bString);
                    long result = (long) ((a + b) * (a * b));
                    row.add(String.valueOf(result));
                }
            }
        }
    }

    public void checkPalindrome() {
        int cColumn = getColumnNumber( "C");
        int startRow = getRowNumber(CsvLogic.resultColumns[1]) + 1;
        
        for (int i = startRow; i < records.size(); i++){
            List<String> row = records.get(i);
            if (row.size() >= cColumn) {
                String cCell = row.get(cColumn).toLowerCase();
                String reversed = reverseString(cCell);

                boolean isPolindrome = cCell.equals(reversed) && !cCell.isEmpty();
                row.add(String.valueOf(isPolindrome));
            }
        }
    }

    private static String reverseString(String string) {
        return new StringBuilder(string).reverse().toString().toLowerCase();
    }

    private int getColumnNumber(String columnName){
        int columnNumber = -1;
        for (List<String> row : this.records) {
            for (int j = 0; j < row.size(); j++) {
                String cell = row.get(j);
                if (cell.equals(columnName)) {
                    return j;
                }
            }
        }
        return columnNumber;
    }

    private int getRowNumber(String columnName){
        int columnNumber = -1;
        for (int i = 0; i < this.records.size(); i++) {
            List<String> row = this.records.get(i);
            for (String cell : row) {
                if (cell.equals(columnName)) {
                    return i;
                }
            }
        }
        return columnNumber;
    }

    public void addReversedString() {
        int startRow = getRowNumber(CsvLogic.resultColumns[1]) + 1;
        int cColumn = getColumnNumber( "C");

        this.records.stream()
                .skip(startRow)
                .forEach(row -> {
                    String cell = row.get(cColumn);
                    row.add(reverseString(cell));
                });
    }
}
