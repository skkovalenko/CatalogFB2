import data.AuthorBookList;
import org.xml.sax.SAXException;
import service.Reader;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
public class Main {
    private static final String START_MESSAGE = "введите путь каталога:";
    private static final String WAIT_MASSAGE = "ждите...";
    private static final String MESSAGE_IF_NOT_EXISTS = "некорректный путь\nпоробуйте снова:";
    private static final String MESSAGE_IF_NOT_FB2_FILES = "в текущем каталоге отсутсвуют книги";
    private static final String MATCHES_REG_FB2 = "(.+\\.fb2$)|(.+\\.fb2\\.zip$)";
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        System.out.println(START_MESSAGE);
        //Получение пути через сканер и проверка пути на корректность
        String path = new Scanner(System.in).nextLine().trim();
        while (!Files.exists(Paths.get(path))) {
            System.out.println(MESSAGE_IF_NOT_EXISTS);
            path = new Scanner(System.in).nextLine().trim();
        }
        System.out.println(WAIT_MASSAGE);
        //Получение Массива всех файлов в каталоге
        File[] files  = new File(path).listFiles();
        assert files != null;
        AuthorBookList authorBookList = new AuthorBookList();
        int countFb2File = 0;
        //Обход массива файлов
        for(File file : files){
            if(file.getName().matches(MATCHES_REG_FB2)){
                countFb2File++;
                //парсинг .fb2 и .fb2.zip и запись в объект authorBookList
                Reader.parse(file, authorBookList);
            }
        }
        if(countFb2File == 0) System.out.println(MESSAGE_IF_NOT_FB2_FILES);
        else System.out.println(authorBookList);
    }
}
