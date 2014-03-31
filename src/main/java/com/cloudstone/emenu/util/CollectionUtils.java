/**
 * @(#)CollectionUtils.java, Jun 15, 2013. 
 *
 */
package com.cloudstone.emenu.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.cloudstone.emenu.constant.EmptyConst;

/**
 * @author xuhongfeng
 */
public class CollectionUtils {
    public static boolean isEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(Collection<?> c) {
        if (c == null || c.size() == 0) {
            return true;
        }
        return false;
    }

    public static <T> List<T> arrayToList(T[] array) {
        List<T> list = new ArrayList<T>();
        for (T item : array) {
            list.add(item);
        }
        return list;
    }

    public static Set<Integer> arrayToSet(int[] array) {
        Set<Integer> set = new HashSet<Integer>();
        for (int i : array) {
            set.add(i);
        }
        return set;
    }

    public static <T> void slice(List<T> oldList, List<T> newList, int start, int len) {
        newList.clear();
        int end = start + len;
        for (int i = start; i < end; i++) {
            newList.add(oldList.get(i));
        }
    }

    public static byte[] slice(byte[] array, int start, int len) {
        byte[] r = new byte[len];
        System.arraycopy(array, start, r, 0, len);
        return r;
    }

    public static int[] slice(int[] array, int start, int len) {
        int[] r = new int[len];
        System.arraycopy(array, start, r, 0, len);
        return r;
    }

    public static String[] slice(String[] array, int start, int len) {
        String[] r = new String[len];
        for (int i = 0; i < len; i++) {
            r[i] = array[i + start];
        }
        return r;
    }

    public static int[] splitToInt(String s, String split) {
        if (StringUtils.isBlank(s)) {
            return EmptyConst.EMPTY_INT_ARRAY;
        }
        String[] ss = s.split(split);
        return toIntArray(ss);
    }

    public static int[] toIntArray(String[] ss) {
        int[] r = new int[ss.length];
        for (int i = 0; i < r.length; i++) {
            r[i] = Integer.valueOf(ss[i]);
        }
        return r;
    }

    public static int[] toIntArray(Collection<Integer> list) {
        return toPrimitive(list.toArray(new Integer[0]));
    }

    public static int[] toPrimitive(Integer[] array) {
        int[] r = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            r[i] = array[i];
        }
        return r;
    }

    public static Integer[] toBoxed(int[] array) {
        Integer[] boxed = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            boxed[i] = Integer.valueOf(array[i]);
        }
        return boxed;
    }

    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    public static int[] remove(int[] array, int start, int len) {
        int[] r = new int[array.length - len];
        System.arraycopy(array, 0, r, 0, start);
        System.arraycopy(array, start + len, r, start, array.length - start - len);
        return r;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] remove(T[] array, int start, int len, Class<T> clazz) {
        T[] r = (T[]) Array.newInstance(clazz, array.length - len);
        System.arraycopy(array, 0, r, 0, start);
        System.arraycopy(array, start + len, r, start, array.length - start - len);
        return r;
    }

    public static <T> void filter(List<T> list, Tester<T> tester) {
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (!tester.test(it.next())) {
                it.remove();
            }
        }
    }

    public static <T> boolean every(List<T> list, Tester<T> callback) {
        for (T data : list) {
            if (!callback.test(data)) {
                return false;
            }
        }
        return true;
    }

    public static interface Tester<T> {
        public boolean test(T data);
    }

    public static <T, R> R reduce(T[] array, Reducer<T, R> callback, R initialValue) {
        if (isEmpty(array)) {
            return initialValue;
        }
        R result = initialValue;
        for (int i = 0; i < array.length; i++) {
            result = callback.reduce(result, array[i], i, array);
        }
        return result;
    }

    public static interface Reducer<T, R> {
        public R reduce(R previousValue, T currentValue, int index, T[] array);
    }

    public static <T, R> R[] map(T[] values, R[] emptyResults, Mapper<T, R> mapper) {
        for (int i = 0; i < emptyResults.length; i++) {
            emptyResults[i] = mapper.map(values[i]);
        }
        return emptyResults;
    }

    public static interface Mapper<T, R> {
        public R map(T value);
    }

    private static final Reducer<Integer, String> INT_ARRAY_TO_STRING = new Reducer<Integer, String>() {
        @Override
        public String reduce(String previousValue, Integer currentValue,
                             int index, Integer[] array) {
            if (StringUtils.isBlank(previousValue)) {
                if (index == array.length - 1) {
                    return "[" + currentValue + "]";
                } else {
                    return "[" + currentValue;
                }
            } else {
                if (index == array.length - 1) {
                    return previousValue + ", " + currentValue + "]";
                } else {
                    return previousValue + ", " + currentValue;
                }
            }
        }
    };

    public static String toString(int[] array) {
        return reduce(toBoxed(array), INT_ARRAY_TO_STRING, "");
    }

    private static class Joiner<T> implements Reducer<T, String> {
        private final String separator;

        public Joiner(String separator) {
            super();
            this.separator = separator;
        }

        @Override
        public String reduce(String previousValue, T currentValue, int index,
                             T[] array) {
            if (StringUtils.isBlank(previousValue)) {
                return currentValue.toString();
            } else {
                return previousValue + separator + currentValue;
            }
        }
    }

    public static String join(int[] array, String separator) {
        Integer[] boxed = toBoxed(array);
        Joiner<Integer> joiner = new Joiner<Integer>(separator);
        return reduce(boxed, joiner, "");
    }

    public static String join(List<String> array, String separator) {
        return join(array.toArray(new String[0]), separator);
    }

    public static String join(String[] array, String separator) {
        Joiner<String> joiner = new Joiner<String>(separator);
        return reduce(array, joiner, "");
    }

    public static int[] insert(int[] array, int pos, int value) {
        int[] newArray = new int[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, pos);
        newArray[pos] = value;
        System.arraycopy(array, pos, newArray, pos + 1, array.length - pos);
        return newArray;
    }
}