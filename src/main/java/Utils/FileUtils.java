package Utils;

import java.io.File;
import java.io.IOException;
import org.testng.Assert;

public class FileUtils {
	
	
	public static void deleteFile(String sFilePath){
		File file = new File(sFilePath);
		if(file.delete()) { 
			System.out.println(file.getName() + " is deleted!");
		} else {
			System.out.println(file.getName() + " is not deleted!");
		}
	}

	public static void createFile(String sFilePath){
		try{
			File file = new File(sFilePath);
			if(file.createNewFile()) { 
				System.out.println(file.getName() + " is created!");
			} else {
				System.out.println(file.getName() + " is not created!");
			}
		}catch(IOException iox){
			System.err.println("Exception occurred while creating a file : " + iox.getMessage());
			iox.printStackTrace();
			Assert.fail("Exception occurred while creating a file : " + iox.getMessage());
		}
	}
	
	public static String findFile(String name, File dir)throws IOException
	{      
		String absolutPath = "";
		File[] list = dir.listFiles();       
		if(list != null)  
		{                          
			for(File file2 : list)
			{            
				if (file2.isDirectory())
				{
					absolutPath = findFile(name,file2); 
					if (absolutPath != "") {
						break;
					}
				}
				else if (name.equalsIgnoreCase(file2.getName()))
				{                                                              
					File newFileFound = new File(file2.getAbsolutePath());
					if (newFileFound.exists()) {
						absolutPath = file2.getAbsolutePath();
						break;
					}

				}                      
			}        
		}
		return absolutPath;
	}
}