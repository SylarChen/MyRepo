
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Alternative {

	public static void main(String[] args) {
		 //    在此目录中找文件   
        String baseDIR = "C:/Users/Administrator/Desktop/Asset3/btoe-dev-program/parent";
        //String baseDIR = "C:/Users/Administrator/desktop/Asset3/btoe-dev-program/Modules/Foundation/";
        //    找扩展名为txt的文件   
        String fileName = "pom.xml";    
        List<File> resultList = new ArrayList<File>();
        findFiles(baseDIR, fileName,resultList);    
        if (resultList.size() == 0) {   
            System.out.println("No File Fount.");   
        } else {   
            for (int i = 0; i < resultList.size(); i++) {   
        	//for (int i = 0; i < 1; i++) {   
                //System.out.println(resultList.get(i).getAbsoluteFile());//显示查找结果。    
                AmendFile(resultList.get(i),"9.50.00-SNAPSHOT","10.00.00-SNAPSHOT",1);
            }   
        }   
        System.out.println("success!");

	}

	/**  
     * 递归查找文件  
     * @param baseDirName  查找的文件夹路径  
     * @param targetFileName  需要查找的文件名  
     * @param fileList  查找到的文件集合  
     */  
    public static void findFiles(String baseDirName, String targetFileName, List<File> fileList) {   
      
    	File baseDir = new File(baseDirName);		// 创建一个File对象
		if (!baseDir.exists() || !baseDir.isDirectory()) {	// 判断目录是否存在
			System.out.println("文件查找失败：" + baseDirName + "不是一个目录！");
		}
        String tempName = null;   
        //判断目录是否存在   
        File tempFile;
    	File[] files = baseDir.listFiles();
    	for (int i = 0; i < files.length; i++) {
			tempFile = files[i];
			if(tempFile.isDirectory()){
				findFiles(tempFile.getAbsolutePath(), targetFileName, fileList);
			}else if(tempFile.isFile()){
				tempName = tempFile.getName();
				if(wildcardMatch(targetFileName, tempName)){
					// 匹配成功，将文件名添加到结果集
					fileList.add(tempFile);
				}
			}
		}
    }   
       
    /**  
     * 通配符匹配  
     * @param pattern    通配符模式  
     * @param str    待匹配的字符串  
     * @return    匹配成功则返回true，否则返回false  
     */  
    private static boolean wildcardMatch(String pattern, String str) {   
        int patternLength = pattern.length();   
        int strLength = str.length();   
        int strIndex = 0;   
        char ch;   
        for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {   
            ch = pattern.charAt(patternIndex);   
            if (ch == '*') {   
                //通配符星号*表示可以匹配任意多个字符   
                while (strIndex < strLength) {   
                    if (wildcardMatch(pattern.substring(patternIndex + 1),   
                            str.substring(strIndex))) {   
                        return true;   
                    }   
                    strIndex++;   
                }   
            } else if (ch == '?') {   
                //通配符问号?表示匹配任意一个字符   
                strIndex++;   
                if (strIndex > strLength) {   
                    //表示str中已经没有字符匹配?了。   
                    return false;   
                }   
            } else {   
                if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {   
                    return false;   
                }   
                strIndex++;   
            }   
        }   
        return (strIndex == strLength);   
    } 
    
    /**  
     * 读取一个文件中的内容，并替换某部分内容   
     * @param file          
     * @param oriStr    原字符串  
     * @param altStr    替换成的字符串  
     * @return    替换成功并写回则返回true，否则返回false  
     */  
    private static boolean AmendFile(File file, String oriStr, String altStr, int count) { 
    	char[] FileChars = new char [(int)file.length()];
    	try {
			FileReader fr = new FileReader(file);		        
	        fr.read(FileChars);	   
	        fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	String FileStr =  new String(FileChars);
    	int startIndex = FileStr.indexOf(oriStr);
    	int endIndex = startIndex + oriStr.length();  
    	if(startIndex != -1)
    	{
    		if(count == 3)
    			System.out.println(file.getAbsolutePath());
	    	String out = FileStr.substring(0 , startIndex) + altStr + FileStr.substring(endIndex , FileStr.length());
	    	try {	    		
	    		FileWriter fw = new FileWriter(file);
	    		fw.write(out);
	    		fw.close();
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    	//find if there is another origin string
	    	AmendFile( file, oriStr, altStr, count + 1);
    	}
    	return true;
    }
}
