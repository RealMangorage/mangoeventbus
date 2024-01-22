package org.mangorage.eventbus.misc;

import java.lang.reflect.Array;

public class Utils {
    @SuppressWarnings("unchecked")
    public static <T> T[] removeNullElements(T[] array) {
        if (array == null) {
            return null;
        }

        int newSize = 0;
        for (T element : array) {
            if (element != null) {
                newSize++;
            }
        }

        T[] result = (T[]) Array.newInstance(array.getClass().getComponentType(), newSize);
        int index = 0;

        for (T element : array) {
            if (element != null) {
                result[index++] = element;
            }
        }

        return result;
    }

}
