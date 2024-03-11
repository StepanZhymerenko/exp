import java.lang.reflect.*;

public class Main {
    public static Object invokeMethod(Object obj, String methodName, Object... args) throws FunctionNotFoundException {
        try {
            // Отримуємо клас об'єкта
            Class<?> clazz = obj.getClass();

            // Отримуємо масив типів параметрів
            Class<?>[] parameterTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = args[i].getClass();
            }

            // Отримуємо метод за його ім'ям і типами параметрів
            Method method = clazz.getMethod(methodName, parameterTypes);

            // Викликаємо метод на заданому об'єкті з вказаними аргументами
            return method.invoke(obj, args);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new FunctionNotFoundException("Method '" + methodName + "' not found or cannot be invoked", e);
        }
    }

    public static void main(String[] args) {
        try {
            // Приклад використання методу
            String str = "Hello";
            int length = (int) invokeMethod(str, "length");
            System.out.println("Length of the string: " + length);
        } catch (FunctionNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class FunctionNotFoundException extends Exception {
    public FunctionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
