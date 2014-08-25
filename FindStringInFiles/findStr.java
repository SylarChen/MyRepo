import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class findStr {

	public static void main(String[] args) {
		 //    åœ¨æ­¤ç›®å½•ä¸­æ‰¾æ–‡ä»¶   
        String baseDIR = "C:/Users/Administrator/desktop/AssetBash/btoe-dev-program/Modules/Foundation/";
        //    æ‰¾æ‰©å±•å��ä¸ºtxtçš„æ–‡ä»¶   
        String fileName = "pom.xml";    
        List<File> resultList = new ArrayList<File>();
        findFiles(baseDIR, fileName,resultList);    
        if (resultList.size() == 0) {   
            System.out.println("No File Fount.");   
        } else {   
            for (int i = 0; i < resultList.size(); i++) {   
            	try {
					FileReader fr = new FileReader(resultList.get(i));
					char[] fileChars = new char[(int) resultList.get(i).length()];
					fr.read(fileChars);
					String fileStr = new String(fileChars);
					if(wildcardMatch("*<dependency>*dependencyM*</dependency>*",fileStr))
						System.out.println(resultList.get(i).getAbsoluteFile());//æ˜¾ç¤ºæŸ¥æ‰¾ç»“æžœã€‚
					fr.close();					
				} catch (Exception e) {
					e.printStackTrace();
				}
            	
                //System.out.println(resultList.get(i).getAbsoluteFile());//æ˜¾ç¤ºæŸ¥æ‰¾ç»“æžœã€‚    
            }   
        }   

	}

	/**  
     * é€’å½’æŸ¥æ‰¾æ–‡ä»¶  
     * @param baseDirName  æŸ¥æ‰¾çš„æ–‡ä»¶å¤¹è·¯å¾„  
     * @param targetFileName  éœ€è¦�æŸ¥æ‰¾çš„æ–‡ä»¶å��  
     * @param fileList  æŸ¥æ‰¾åˆ°çš„æ–‡ä»¶é›†å�ˆ  
     */  
    public static void findFiles(String baseDirName, String targetFileName, List<File> fileList) {   
      
    	File baseDir = new File(baseDirName);		// åˆ›å»ºä¸€ä¸ªFileå¯¹è±¡
		if (!baseDir.exists() || !baseDir.isDirectory()) {	// åˆ¤æ–­ç›®å½•æ˜¯å�¦å­˜åœ¨
			System.out.println("æ–‡ä»¶æŸ¥æ‰¾å¤±è´¥ï¼š" + baseDirName + "ä¸�æ˜¯ä¸€ä¸ªç›®å½•ï¼�");
		}
        String tempName = null;   
        //åˆ¤æ–­ç›®å½•æ˜¯å�¦å­˜åœ¨   
        File tempFile;
    	File[] files = baseDir.listFiles();
    	for (int i = 0; i < files.length; i++) {
			tempFile = files[i];
			if(tempFile.isDirectory()){
				findFiles(tempFile.getAbsolutePath(), targetFileName, fileList);
			}else if(tempFile.isFile()){
				tempName = tempFile.getName();
				if(wildcardMatch(targetFileName, tempName)){
					// åŒ¹é…�æˆ�åŠŸï¼Œå°†æ–‡ä»¶å��æ·»åŠ åˆ°ç»“æžœé›†
					fileList.add(tempFile);
				}
			}
		}
    }   
       
    /**  
     * é€šé…�ç¬¦åŒ¹é…�  
     * @param pattern    é€šé…�ç¬¦æ¨¡å¼�  
     * @param str    å¾…åŒ¹é…�çš„å­—ç¬¦ä¸²  
     * @return    åŒ¹é…�æˆ�åŠŸåˆ™è¿”å›žtrueï¼Œå�¦åˆ™è¿”å›žfalse  
     */  
    private static boolean wildcardMatch(String pattern, String str) {   
        int patternLength = pattern.length();   
        int strLength = str.length();   
        int strIndex = 0;   
        char ch;   
        for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {   
            ch = pattern.charAt(patternIndex);   
            if (ch == '*') {   
                //é€šé…�ç¬¦æ˜Ÿå�·*è¡¨ç¤ºå�¯ä»¥åŒ¹é…�ä»»æ„�å¤šä¸ªå­—ç¬¦   
                while (strIndex < strLength) {   
                    if (wildcardMatch(pattern.substring(patternIndex + 1),   
                            str.substring(strIndex))) {   
                        return true;   
                    }   
                    strIndex++;   
                }   
            } else if (ch == '?') {   
                //é€šé…�ç¬¦é—®å�·?è¡¨ç¤ºåŒ¹é…�ä»»æ„�ä¸€ä¸ªå­—ç¬¦   
                strIndex++;   
                if (strIndex > strLength) {   
                    //è¡¨ç¤ºsträ¸­å·²ç»�æ²¡æœ‰å­—ç¬¦åŒ¹é…�?äº†ã€‚   
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
}
