package com.app.trys.utils;

/**
 * @author linjf
 * @since 2025-04-24
 */
public class ArrayUtil {

    /**
     * 数组中元素未找到的下标，值为-1
     */
    public static final int INDEX_NOT_FOUND = -1;

    public static boolean isEmpty(Object[] array){
        return array == null || array.length == 0;
    }

    public static boolean isNotEmpty(Object[] array){
        return !isEmpty(array);
    }

    /**
     * 返回数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     * @since 3.0.7
     */
    public static int indexOf(int[] array, int value) {
        if (null != array) {
            for (int i = 0; i < array.length; i++) {
                if (value == array[i]) {
                    return i;
                }
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 数组中是否包含元素
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 是否包含
     * @since 3.0.7
     */
    public static boolean contains(int[] array, int value) {
        return indexOf(array, value) > INDEX_NOT_FOUND;
    }

    public static boolean isAllNull(Object... objects){
        if(isEmpty(objects)){
            return true;
        }
        for (Object o : objects) {
            if(o == null)
                return true;
        }
        return false;
    }
}
