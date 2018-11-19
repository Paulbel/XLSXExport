import org.apache.poi.ss.formula.functions.FreeRefFunction;
import org.apache.poi.ss.formula.udf.DefaultUDFFinder;
import org.apache.poi.ss.formula.udf.UDFFinder;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class Creator {
    void create() throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook(); //or new HSSFWorkbook();
//
        String[] functionNames = {"calculatePayment"};
        FreeRefFunction[] functionImpls = {new CalculateBidNumber()};

        UDFFinder udfToolpack = new DefaultUDFFinder(functionNames, functionImpls);

        // register the user-defined function in the workbook
        wb.addToolPack(udfToolpack);

        FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
        //
        XSSFSheet sheet = wb.createSheet();
        XSSFRow row = sheet.createRow(0);


        row.createCell(0).setCellValue(5);
        row.createCell(1).setCellValue(3);
        row.createCell(2).setCellValue(4);

        XSSFCell cell = row.createCell(8);
        cell.setCellType(CellType.FORMULA);
        row.getCell(8).setCellFormula("calculatePayment(A1,B1,C1)");


        //CellValue value = evaluator.evaluate(cell);

        //System.out.println("returns value: " + value.getNumberValue());
        XSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
        // Write the output to a file
        try (OutputStream fileOut = new FileOutputStream("xssf-align.xlsx")) {
            wb.write(fileOut);
        }

        wb.close();
    }
}
