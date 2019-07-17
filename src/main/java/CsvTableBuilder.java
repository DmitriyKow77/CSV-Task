import org.apache.commons.lang3.math.NumberUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dmitriy on 7/12/19.
 */
class CsvTableBuilder {
    private final String[] resultColumns = {"Expression Result", "Is Palindrome", "Reversed String"};
    private ArrayList<List<String>> records;

    CsvTableBuilder(ArrayList<List<String>> records) {
        this.records = records;
    }

    CsvTableBuilder cleanEmptyCells(){
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
        return this;
    }

    CsvTableBuilder addColumnTitles(){
        final String titleColumnIndicator = "A, B, C";

        for (List<String> record : records) {
            if (record.toString().contains(titleColumnIndicator)) {
                Collections.addAll(record, resultColumns);
                break;
            }
        }
        return this;
    }

    CsvTableBuilder calculateExpressionResult(){
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
        return this;
    }

    CsvTableBuilder checkPalindrome() {
        int cColumn = getColumnNumber( "C");
        int startRow = getRowNumber(resultColumns[1]) + 1;
        
        for (int i = startRow; i < records.size(); i++){
            List<String> row = records.get(i);
            if (row.size() >= cColumn) {
                String cCell = row.get(cColumn).toLowerCase();
                String reversed = reverseString(cCell);

                boolean isPolindrome = cCell.equals(reversed) && !cCell.isEmpty();
                row.add(String.valueOf(isPolindrome));
            }
        }
        return this;
    }

    CsvTableBuilder addReversedString() {
        int startRow = getRowNumber(resultColumns[1]) + 1;
        int cColumn = getColumnNumber( "C");

        this.records.stream()
                .skip(startRow)
                .forEach(row -> {
                    String cell = row.get(cColumn);
                    row.add(reverseString(cell));
                });
        return this;
    }

    ArrayList<List<String>> build(){
        return this.records;
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
}
