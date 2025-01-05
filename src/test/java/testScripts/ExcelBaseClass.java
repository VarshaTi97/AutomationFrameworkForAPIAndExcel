package testScripts;

import constants.ExcelConstants;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;
import utils.ExcelUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExcelBaseClass {
    protected ExcelUtils excelUtils;
    protected List<Map<String,Object>> dataSheetRecords;
    protected List<Map<String,Object>> updatedDataSheetRecords;
    protected final SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void setup(){
        excelUtils = new ExcelUtils();
        excelUtils.loadExcel(ExcelConstants.TEST_DATA_FILE_PATH);

        //Get data from existing sheet
        dataSheetRecords = excelUtils.readData(ExcelConstants.DATA_SHEET_NAME);

        //add data to the new sheet
        excelUtils.writeSortedDataToNewSheet(ExcelConstants.UPDATED_DATA_SHEET_NAME, dataSheetRecords, 100);
        updatedDataSheetRecords = excelUtils.readData(ExcelConstants.UPDATED_DATA_SHEET_NAME);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws IOException {
        try {
            excelUtils.DeleteExcelSheet(ExcelConstants.UPDATED_DATA_SHEET_NAME);
        } finally {
            excelUtils.closeWorkBook();
        }
        softAssert.assertAll();
    }
}
