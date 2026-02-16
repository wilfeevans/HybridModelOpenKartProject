package Utilities;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import BaseClass.BaseClass;

public class ExcelUtil extends BaseClass
{

	
		@DataProvider(name="dp")
	    public Object[][] sendData(Method m)
	    {
	    	String sheetname= m.getName();
	    	
	    	
	    	int rowcount=  	excel.getRowCount(sheetname);
	    	int columncount= excel.getColumnCount(sheetname);
	    	
	    	Object[][] data= new Object[rowcount-1][columncount];
	    	
	    	for(int i=2; i<=rowcount; i++)
	    	{
	    		for(int j=0; j<columncount; j++)
	    		{
	    			data[i-2][j]=excel.getCellData(sheetname, j, i);
	    		}
	    	}
	    	
	    	return data;
	    	
	    }
		
		
		
		
		
		
		
		

	

}
