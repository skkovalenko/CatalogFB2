package service;

import data.AuthorBookList;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Reader {
    private static final String TAG_FIRST_NAME = "first-name";
    private static final String TAG_LAST_NAME = "last-name";
    private static final String TAG_BOOK_TITLE = "book-title";
    private static File TEMP_FILE;
    private static final String SUFFIX_TEMP = ".xml";
    private static final String MATCHES_REG_FOR_ZIP = ".+\\.fb2\\.zip$";
    public static void parse(File file, AuthorBookList authorBookList) throws IOException, SAXException, ParserConfigurationException {
        //Проверка на zip
        if(file.getName().matches(MATCHES_REG_FOR_ZIP)){
            zip(file);
            file = TEMP_FILE;
        }
        //Создание и парсинг документа .xml формата
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(file);
        String firstName = document.getElementsByTagName(TAG_FIRST_NAME).item(0).getTextContent();
        String lastName = document.getElementsByTagName(TAG_LAST_NAME).item(0).getTextContent();
        String bookTitle = document.getElementsByTagName(TAG_BOOK_TITLE).item(0).getTextContent();
        //Запись данных о кгиге в authorBookList (реформированный TreeMap)
        authorBookList.add(firstName + " " + lastName, bookTitle);
        //Удаление временного файла из распакованного zip архива
        if(TEMP_FILE != null){
            TEMP_FILE.deleteOnExit();
        }
    }
    private static void zip(File file) throws IOException {
        //Распаковка zip файла
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
        ZipEntry entry;
        while ((entry = zipInputStream.getNextEntry()) != null){
            //Создание временного файла для парсинга
            TEMP_FILE = File.createTempFile(entry.getName(), SUFFIX_TEMP);
            FileOutputStream fileOutputStream = new FileOutputStream(TEMP_FILE);
            for (int i = zipInputStream.read(); i != -1; i = zipInputStream.read()){
                fileOutputStream.write(i);
            }
        }
    }
}
