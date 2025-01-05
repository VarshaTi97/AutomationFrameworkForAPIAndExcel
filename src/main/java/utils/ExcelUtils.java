package utils;

import constants.ExcelConstants;
import exceptions.ExcelExceptions;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ExcelUtils {
    private Workbook workbook;
    private Sheet sheet;
    private FileOutputStream fos;
    private FileInputStream fis;

    /*
    * Loads the Excel file
    * @param path of the Excel file
    */
    public void loadExcel(String filePath) {
        try {
            fis = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fis);
        }catch (IOException e){
            throw new ExcelExceptions.FileLoadingException("Failed to load excel file: "+ filePath);
        }
    }

    /*
     * Load the specific sheet
     * @param name of the sheet in Excel file
     */
    public void loadSheet(String sheetName){
        sheet = workbook.getSheet(sheetName);
        if(sheet == null){
            throw new ExcelExceptions.SheetNotFoundException("Sheet "+ sheetName + "does not exists in the excel file.");
        }
    }

    /*
     * Reads data from the Excel sheet
     * @return List of user data in the form of maps
     */
    public List<Map<String, Object>> readData(String sheetName){
        loadSheet(sheetName);
        List<Map<String, Object>> data = new ArrayList<>();
        Row headerRow = sheet.getRow(0);
        int totalColumns = headerRow.getLastCellNum();
        for(int i=1; i<= sheet.getLastRowNum(); i++){
            Row row =  sheet.getRow(i);
            Map<String, Object> rowData = new HashMap<>();
            for(int j=0;j<totalColumns;j++){
                String columnHeader = headerRow.getCell(j).getStringCellValue();
                Cell cell  = row.getCell(j);
                Object value = (cell == null) ? "": getCellValue(cell);
                rowData.put(columnHeader, value);
            }
            data.add(rowData);
        }
        return data;
    }

    /*
     * Writes updated amount data,id and name from data sheet to new Excel sheet
     * @param name of new sheet
     * @param List of user data in the form of maps
     * @param increment value for amount
     */
    public void writeSortedDataToNewSheet(String newSheetName, List<Map<String,Object>> initialData, int incrementValue) {
        Sheet newSheet = workbook.createSheet(newSheetName);
        Row headerRow = sheet.getRow(0);
        List<Map<String,Object>> data =  sortData(initialData, "Name");

        //create header row in the new sheet
        Row newHeaderRow = newSheet.createRow(0);
        if(newHeaderRow == null)
            throw new ExcelExceptions.DataValidationException("Header row is missing in the sheet: "+ ExcelConstants.DATA_SHEET_NAME);
        for(int i=0; i<headerRow.getLastCellNum();i++){
            newHeaderRow.createCell(i).setCellValue(headerRow.getCell(i).getStringCellValue());
        }


        //insert incremented cell values in new sheet
        for(int i =0;i< data.size();i++){
            Row newRow = newSheet.createRow(i+1);
            Map<String, Object> rowData = data.get(i);
            for(int j=0;j< headerRow.getLastCellNum();j++){
                String columnHeader = headerRow.getCell(j).getStringCellValue();
                Object value = rowData.get(columnHeader);
                if(columnHeader.equalsIgnoreCase("amount") && value instanceof Integer){
                     value = (int)value + incrementValue;

                }
                Cell newCell =  newRow.createCell(j);
                setCellValue(newCell, value);

            }
        }
        saveExcel(ExcelConstants.TEST_DATA_FILE_PATH);
    }

    /*
    * Checks if the amount for specific username matches the expected amount.
    * @param List of user data in the form of maps
    * @param name of the user for which amount to be checked
    * @param expected amount for the user
    * @return
     */
    public int getAmountForUser(List<Map<String, Object>> data, String username) {
        int amount;
        for(Map<String, Object> entry: data){
            if(entry.get("Name").equals(username)){
                amount = (int) entry.get("Amount");
                return amount;
            }
        }
        throw new ExcelExceptions.UserNotFoundException(username + "not present in the excel sheet");
    }

    /*
     * calculates sum of column values
     * @param List of user data in the form of maps
     * @param name of the column whose values to be added
     * @param expected amount for the user
     */
    public int calculateTotal(List<Map<String, Object>> data, String columnName) {
        int total = 0;
        for(Map<String, Object> entry: data){
            total += (int)entry.get(columnName);
        }
        return total;
    }

    /*
    * Generic method to get a list of values from a specific column
    * @param name of the sheet
    * @param name of the column
    * @returns list of column data
     */
    public List<Object> getColumnValuesFromSheet(String sheetName, String columnName){
        List<Object> columnValues = new ArrayList<>();
        loadSheet(sheetName);
        if(sheet == null){
            throw new ExcelExceptions.SheetNotFoundException("Sheet with name "+sheetName + "doesn't exists");
        }
        //Get header row and check if table has content
        Row headerRow = sheet.getRow(0);
        if(headerRow == null){
            try {
                workbook.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            throw new IllegalArgumentException("No rows available in the table");
        }

        //Get the column index for the specified column name
        int columnIndex = -1;
        for (Cell cell : headerRow) {
            if (cell.getStringCellValue().equalsIgnoreCase(columnName)) {
                columnIndex = cell.getColumnIndex();
                break;
            }
        }

        //Read the values from the column
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();
        while(rowIterator.hasNext()){
            Row row = rowIterator.next();
            Cell cell = row.getCell(columnIndex);
            columnValues.add(getCellValue(cell));
        }

        // Close the workbook and input stream
        return columnValues;
    }

    /*
    * Check if list of strings is alphabetically sorted.
    * @param list of names to check.
    * @return true if names are alphabetically sorted else false
     */
    public boolean isAlphabeticallySorted(List<Object> columnValues){
        for(int i=1;i< columnValues.size();i++){
            if(columnValues.get(i-1).toString().compareTo(columnValues.get(i).toString()) > 0){
                return false;
            }
        }
        return true;
    }

    /*
    * Save the Excel worksheet
    * @param: path of Excel file
    */
    public void saveExcel(String filePath){
        try {
            fos = new FileOutputStream(filePath);
            workbook.write(fos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    * Close excel workbook
    */
    public void closeWorkBook() throws IOException{
        if (workbook != null) {
            workbook.close();
        }
        if (fos != null) {
            fos.close();
        }
        if (fis != null) {
            fis.close();
        }
    }

    /*
    * get value from a cell based on the cell data type
    * @param : cell whose data to be read
     */
    public Object getCellValue(Cell cell){
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                double numericValue = cell.getNumericCellValue();
                // Check if the numeric value is an integer (i.e., no decimal part)
                if (numericValue == (int) numericValue) {
                    return (int) numericValue;  // Convert to integer if it has no decimal
                } else {
                    return numericValue;  // Return as double if it has decimals
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            default:
                return null;
        }
    }

    /*
    * Set a specific value in the cell
    * @param : cell in which value to be added
    * @param: value to be added in the cell
     */
    public void setCellValue(Cell cell, Object value){
        switch (value.getClass().getSimpleName()) {
            case "Integer":
                cell.setCellValue((int) value);
                break;
            case "Double":
                cell.setCellValue((double) value);
                break;
            case "String":
                cell.setCellValue(value.toString());
                break;
            case "Boolean":
                cell.setCellValue((boolean)value);
                break;
            default:
                cell.setCellValue("");
                break;
        }
    }

    /*
    * Delete excel sheet
    * @param: name of the sheet
     */

    public void DeleteExcelSheet(String sheetName) {
        int sheetIndex = workbook.getSheetIndex(sheetName);
        if(sheetIndex != -1) {
            workbook.removeSheetAt(sheetIndex);
        }
        saveExcel(ExcelConstants.TEST_DATA_FILE_PATH);
    }

    /*
    * Sort data by a specific column in alphabetical order
    * @param: data is the list of map of Excel data
    * @param: column name which is to be used for sorting
     */
    public List<Map<String, Object>> sortData(List<Map<String, Object>> data, String sortByColumn) {
        int n = data.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Map<String, Object> current = data.get(j);
                Map<String, Object> next = data.get(j + 1);

                // Compare the values in the specified column
                String currentValue = current.get(sortByColumn).toString();
                String nextValue = next.get(sortByColumn).toString();

                if (currentValue.compareTo(nextValue) > 0) {
                    data.set(j, next);
                    data.set(j + 1, current);
                }
            }
        }
        return data;
    }

    /*
    * Return id of the user based on the name of the user
    * @param: excel data
    * @param: name of the user for which id has to be retrieved
     */
    public int getUserIdByName(List<Map<String, Object>> data, String name) throws Exception {
        for(Map<String, Object> user: data){
            String userName = (String)user.get(ExcelConstants.DATA_SHEET_HEADER_NAME);
            if(userName !=null && userName.equalsIgnoreCase(name)) {
                return (Integer)user.get(ExcelConstants.DATA_SHEET_HEADER_ID);
            }
        }
        throw new Exception("Username not present in the excel sheet");
    }
}