package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Utils
 * @author Loann Neveu
 */
public class Utils {

    /**
     * Iterable to list
     * @param iterable
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(Iterable<T> iterable) {
        if(iterable instanceof List) {
            return (List<T>) iterable;
        }
        ArrayList<T> list = new ArrayList<T>();
        if(iterable != null) {
            for(T e: iterable) {
                list.add(e);
            }
        }
        return list;
    }

}
