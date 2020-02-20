package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
//Класс схож с TreeMap с изменением добавления ключей и значений, а также сортировка значения
public class AuthorBookList {
    private TreeMap<String, ArrayList<String>> listAuthorBooks = new TreeMap<>();
    public void add(String author, String book){
        if(!listAuthorBooks.containsKey(author)){
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(book);
            listAuthorBooks.put(author, arrayList);
        }else {
            listAuthorBooks.get(author).add(book);
            Collections.sort(listAuthorBooks.get(author));
        }
    }
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Map.Entry<String, ArrayList<String>> entry : listAuthorBooks.entrySet()){
            for (String book : entry.getValue()){
                 string.append(entry.getKey()).append(" - ").append(book).append("\n");
            }
        }
        return string.toString();
    }
}
