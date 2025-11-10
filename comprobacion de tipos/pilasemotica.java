
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class pilasemotica {
    public static void main(String[] args) {

        String texto = "frend Anna Alexander";
        Pattern patterm = Pattern.compile("A.+a");
        Matcher matcher = patterm.matcher(texto);

        while (matcher.find()) {
            System.out.println(texto.substring(matcher.start(), matcher.end()));
        
        }
        
    }
}
  