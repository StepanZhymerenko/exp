import java.lang.reflect.Array;

public class Main {

    public static void main(String[] args) {
        // Приклади використання
        Integer[] arr1 = createArray(Integer.class, 2);
        System.out.println(arrayToString(arr1));

        String[] arr2 = createArray(String.class, 3);
        System.out.println(arrayToString(arr2));

        Double[] arr3 = createArray(Double.class, 5);
        System.out.println(arrayToString(arr3));

        Integer[][] matrix1 = createMatrix(Integer.class, 3, 5);
        setMatrixValues(matrix1);
        System.out.println(matrixToString(matrix1));

        Integer[][] matrix2 = createMatrix(Integer.class, 4, 6);
        setMatrixValues(matrix2);
        System.out.println(matrixToString(matrix2));

        Integer[][] matrix3 = createMatrix(Integer.class, 3, 7);
        setMatrixValues(matrix3);
        System.out.println(matrixToString(matrix3));

        Integer[][] matrix4 = createMatrix(Integer.class, 2, 2);
        setMatrixValues(matrix4);
        System.out.println(matrixToString(matrix4));
    }

    // Створення одновимірного масиву
    @SuppressWarnings("unchecked")
    public static <T> T[] createArray(Class<T> elementType, int length) {
        return (T[]) Array.newInstance(elementType, length);
    }

    // Створення матриці
    @SuppressWarnings("unchecked")
    public static <T> T[][] createMatrix(Class<T> elementType, int rows, int columns) {
        return (T[][]) Array.newInstance(elementType, rows, columns);
    }

    // Зміна розмірів матриці зі збереженням значень
    @SuppressWarnings("unchecked")
    public static <T> T[][] resizeMatrix(T[][] matrix, int newRows, int newColumns) {
        Class<?> elementType = matrix.getClass().getComponentType().getComponentType();
        T[][] newMatrix = (T[][]) Array.newInstance(elementType, newRows, newColumns);
        int copyRows = Math.min(newRows, matrix.length);
        int copyColumns = Math.min(newColumns, matrix[0].length);
        for (int i = 0; i < copyRows; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, copyColumns);
        }
        return newMatrix;
    }

    // Зміна розмірів одновимірного масиву зі збереженням значень
    @SuppressWarnings("unchecked")
    public static <T> T[] resizeArray(T[] array, int newSize) {
        Class<?> elementType = array.getClass().getComponentType();
        T[] newArray = (T[]) Array.newInstance(elementType, newSize);
        int copyLength = Math.min(newSize, array.length);
        System.arraycopy(array, 0, newArray, 0, copyLength);
        return newArray;
    }

    // Перетворення одновимірного масиву в рядок
    public static <T> String arrayToString(T[] array) {
        StringBuilder sb = new StringBuilder();
        sb.append(array.getClass().getComponentType().getSimpleName());
        sb.append("[").append(array.length).append("] = {");
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(array[i]);
        }
        sb.append("}");
        return sb.toString();
    }

    // Перетворення матриці в рядок
    public static <T> String matrixToString(T[][] matrix) {
        StringBuilder sb = new StringBuilder();
        sb.append(matrix.getClass().getComponentType().getComponentType().getSimpleName());
        sb.append("[").append(matrix.length).append("][").append(matrix[0].length).append("] = {");
        for (int i = 0; i < matrix.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(arrayToString(matrix[i]));
        }
        sb.append("}");
        return sb.toString();
    }

    // Заповнення значеннями матриці для тестування
    public static void setMatrixValues(Integer[][] matrix) {
        int value = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = value++;
            }
        }
    }
}
