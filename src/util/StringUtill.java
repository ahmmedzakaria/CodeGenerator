package util;

import java.util.Arrays;
import java.util.List;

import com.sun.xml.internal.ws.util.StringUtils;

public class StringUtill {
	
	//Pascal case means that the first letter of each word is capitalized
    public static String getPascalCase(String str, String wordSeperator){ // wordSeperator: _, -,
        StringBuffer sb = new StringBuffer();
        List<String> words = Arrays.asList(str.split(wordSeperator));
        if(words.size()>0){
            words.forEach(word->{
            sb.append(StringUtils.capitalize(word));
        });
        }
        return sb.toString();
    }
    
    public static String getCamelCase(String str, String wordSeperator){ // wordSeperator: _, -,
        StringBuffer sb = new StringBuffer();
        List<String> words = Arrays.asList(str.split(wordSeperator));
        if(words.size()>0)sb.append(words.get(0).toLowerCase());
        if(words.size()>1){
            words.stream().skip(1)
            .forEach(word->sb.append(StringUtils.capitalize(word)));
        }
        return sb.toString();
    }
}
