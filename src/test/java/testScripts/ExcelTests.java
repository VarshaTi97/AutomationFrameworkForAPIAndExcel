package testScripts;

import constants.ExcelConstants;
import org.testng.annotations.Test;
import java.util.List;


public class ExcelTests extends ExcelBaseClass{


    @Test
    //Testcase 1: Amount of Eve should be 500, and updated amount should be 600
    public void checkIfAmountForEveIsUpdated() {
        //check if initial value in table is correct
        int initialAmount = excelUtils.getAmountForUser(dataSheetRecords, "Eve");
        softAssert.assertEquals(initialAmount, 500, "Amount in the Data sheet for Eve is: " + initialAmount);

        //check if value after update is correct
        int updatedAmount = excelUtils.getAmountForUser(updatedDataSheetRecords, "Eve");
        softAssert.assertEquals(updatedAmount, 600, "Amount in the Updated Data sheet for Eve is: " + updatedAmount);
    }

    @Test
    //Testcase 2: Yasmin should have ID “3”
    public void checkValueOfIdForYasmin() throws Exception {
        //check if value of ID is correct for Yasmin
        int updatedAmount = excelUtils.getUserIdByName(updatedDataSheetRecords, "Yasmin");
        softAssert.assertEquals(updatedAmount, 3, "ID for Yasmin is not correct");
    }

    @Test
    //Testcase 3: Total amount should be “1500”, updated amount should be “2000”
    public void checkIfTotalAmountIsCorrect() {
        //Validate sum of initial amount
        int dataSheetAmountTotal = excelUtils.calculateTotal(dataSheetRecords, ExcelConstants.DATA_SHEET_HEADER_AMOUNT);
        softAssert.assertEquals(dataSheetAmountTotal, 1500, "Total Amount in the Data sheet should be: " + dataSheetAmountTotal);

        //Validate sum of updated amount
        int updatedDataSheetAmountTotal = excelUtils.calculateTotal(updatedDataSheetRecords, ExcelConstants.DATA_SHEET_HEADER_AMOUNT);
        softAssert.assertEquals(updatedDataSheetAmountTotal, 2000, "Total Amount in the Updated Data sheet should be: " + updatedDataSheetAmountTotal);
    }

    @Test
    //Testcase 4: List should be in alphabetical order
    public void checkIfDataIsAlphabeticallySorted() {
        List<Object> updatedDataSheetNameRecords = excelUtils.getColumnValuesFromSheet(ExcelConstants.UPDATED_DATA_SHEET_NAME, ExcelConstants.DATA_SHEET_HEADER_NAME);
        softAssert.assertTrue(excelUtils.isAlphabeticallySorted(updatedDataSheetNameRecords), "Records in updated data sheet are not alphabetically sorted");
    }
}
