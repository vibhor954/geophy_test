package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.NotFoundException;
import org.testng.SkipException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


public class DataReader {


    public static String suiteName;
    private static List<Data> dataObjectRepo = new ArrayList<>();
    public static Map<String, String> pageObjRepoMap=new HashMap<>();
    File baseDirectory = new File(".");
    Workbook workBook=null;
    org.apache.poi.ss.usermodel.Sheet generalConfigSheet=null;

    public void setupDataSheet() throws IOException {

        String testDataPath = baseDirectory.getCanonicalPath()+"//src//main//resources//TestData//testdata";
        setDataObject(testDataPath+ ".xlsx");
    }


    public static List<Data> getDataObjectRepo(){
        return dataObjectRepo;
    }
    /**
     * Presets all test case data from the excel sheet.
     * <br> This should be only be referenced in before scenario methods.
     */
    private void setDataObject(String dataObjectFilePath) throws IOException {
        File file = new File(dataObjectFilePath);
        if (file.exists() && !file.isDirectory()) {
            FileInputStream inputStream = new FileInputStream(file);
            workBook = new XSSFWorkbook(inputStream);
            int totalSheetCount = workBook.getNumberOfSheets();
            try{

                //Initializing GeneralConfig data into Global variables
                //org.apache.poi.ss.usermodel.Sheet generalConfigSheet = workBook.getSheetAt(0);
                generalConfigSheet =workBook.getSheet("GeneralConfig");

                //This function will initialize all general config variables based on the column names
                initializegeneralConfigData();
                //Initializing the test cases
                for (int sheetNumber = 1; sheetNumber < totalSheetCount; sheetNumber++) {
                    //Sheet sheet = (Sheet) workBook.getSheetAt(sheetNumber);
                    XSSFSheet sheet= (XSSFSheet) workBook.getSheetAt(sheetNumber);
                    Map<String, DataElements> dataElementsMap = getDataElements((org.apache.poi.ss.usermodel.Sheet) sheet);

                    dataObjectRepo.add(new Data(((XSSFSheet) sheet).getSheetName(), dataElementsMap));
                }


            }catch(Exception e){
                e.printStackTrace();
            }

        } else {
            //logger.error("Data object file not found at: " + file.getAbsolutePath());
            throw new SkipException("Data object file not found at: " + file.getAbsolutePath());
        }
    }



    /**
     * @param cell
     * @return
     * @description: This function takes a cell as an argument and returns the cell value based on the type of cell value type
     */
    @SuppressWarnings("deprecation")
    public String getCellData(Cell cell){
        String cellData="";
        try{

            switch(cell.getCellType()){

                case Cell.CELL_TYPE_STRING:
                    cellData=cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    cellData=(int)cell.getNumericCellValue()+"";
                    break;
                case Cell.CELL_TYPE_BLANK:
                    cellData="";
                    break;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return cellData.trim();
    }

    /**
     * @param
     * @return
     * @description: This function takes a field name as an argument and returns the cell value
     */
    public String initializegeneralConfigData(){
        String cellData="";
        try{
            Iterator rowIterator=generalConfigSheet.iterator();
            while (rowIterator.hasNext()){
                Row row= (Row) rowIterator.next();
                cellData=getCellData(row.getCell(0));
                initializeGeneralConfigVariables(cellData);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return cellData.trim();
    }
    /**
     * @param fieldName
     * @return
     * @description: This function returns the value of each field
     */
    public String getFieldValue(String fieldName){
        String cellValue="";
        try{
            Iterator rowIterator=generalConfigSheet.iterator();
            while (rowIterator.hasNext()){
                Row row= (Row) rowIterator.next();
                if(getCellData(row.getCell(0)).equalsIgnoreCase(fieldName))
                    cellValue=getCellData(row.getCell(1));

            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        //System.out.println("The value of "+fieldName+" : is: "+cellValue);
        return cellValue.trim();

    }

    /**
     * @param fieldName
     * @return
     * @description: This function initializes the general config variables
     */
    public void initializeGeneralConfigVariables(String fieldName){
        try{

            switch (fieldName){
                case Constants.BROWSER:
                    GlobalVars.browser=getFieldValue(fieldName);
                    break;
                case Constants.GECKODRIVER:
                    GlobalVars.geckodriver=getFieldValue(fieldName);
                    break;
                case Constants.CHROMEDRIVER:
                    GlobalVars.chromedriver=getFieldValue(fieldName);
                    break;
                case Constants.URL:
                    GlobalVars.url=getFieldValue(fieldName);
                    break;
                case Constants.MAIL_RECIPIENT:
                    GlobalVars.mailRecipientList=Arrays.asList(getFieldValue(fieldName).split(","));
                    break;
                case Constants.JIRA_FLAG:
                    if(getFieldValue(fieldName).toLowerCase().equalsIgnoreCase("yes"))
                        GlobalVars.jiraFlag=true;
                    break;
                case Constants.MAIL_FLAG:
                    if(getFieldValue(fieldName).toLowerCase().equalsIgnoreCase("yes"))
                        GlobalVars.mailFlag=true;
                    break;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * support method for getPageObjects
     * <br>Fetches all page elements in a page
     *
     * @param sheet takes the Sheet object returned from the workbook
     * @return return the map of page elements with element name and object of pageElement for the same.
     *
     */
    private Map<String, DataElements> getDataElements(org.apache.poi.ss.usermodel.Sheet sheet) {
        Map<String, DataElements> dataElementsMap = new HashMap<>();
        String testCaseName="";
        String runStatus="";
        String params="";
        //Ignoring title row [0] starting from row [1]
        for (int row = 1; row <= sheet.getLastRowNum(); row++) {
            Row dataRow = sheet.getRow(row);

            try{

                testCaseName=getCellData(dataRow.getCell(0));
                runStatus=getCellData(dataRow.getCell(1));
                params=getCellData(dataRow.getCell(2));

            }catch(Exception e){
                e.printStackTrace();

            }

            dataElementsMap.put(testCaseName, new DataElements(testCaseName, runStatus,	params));
        }
        return dataElementsMap;
    }

    /**
     * Fetches page locators from in-memory pageObjectRepo
     * Improved for performance considerations.
     *
     * @param testSuiteName name of the page where the element will be queried
     * @return org.openqa.selenium.By pageElement
     *
     */
    public Map<String, DataElements> getClassData(String testSuiteName) {
        Map<String, DataElements> dataElementMap = new HashMap<>();
        for (Data data : dataObjectRepo) {
            if (data.getSuiteName().equalsIgnoreCase(testSuiteName)) {
                dataElementMap = data.getElementList();
            }
        }
        return dataElementMap;
    }

    public void setSuiteName(String suite_name)
    {
        suiteName = suite_name;
    }
    public static String readProperty(String property) {
        Properties prop;
        String value = null;
        try {
            prop = new Properties();
            prop.load(new FileInputStream(new File("config.properties")));

            value = prop.getProperty(property);

            if (value == null || value.isEmpty()) {
                throw new NotFoundException("Value not set or empty");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }
}
