import java.lang.reflect.*;
import java.util.Scanner;

public class Main {

    public static String getClassDescription(String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        return getClassDescription(clazz);
    }

    public static String getClassDescription(Class<?> clazz) {
        StringBuilder description = new StringBuilder();

        // Package name
        Package pkg = clazz.getPackage();
        if (pkg != null) {
            description.append("Package: ").append(pkg.getName()).append("\n");
        }

        // Class modifiers and name
        int modifiers = clazz.getModifiers();
        description.append("Modifiers: ").append(Modifier.toString(modifiers)).append("\n");
        description.append("Class Name: ").append(clazz.getSimpleName()).append("\n");

        // Superclass
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null) {
            description.append("Superclass: ").append(superclass.getSimpleName()).append("\n");
        }

        // Implemented interfaces
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length > 0) {
            description.append("Implemented Interfaces: ");
            for (Class<?> intf : interfaces) {
                description.append(intf.getSimpleName()).append(", ");
            }
            description.delete(description.length() - 2, description.length()); // Remove trailing comma
            description.append("\n");
        }

        // Fields
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length > 0) {
            description.append("Fields:\n");
            for (Field field : fields) {
                description.append(Modifier.toString(field.getModifiers())).append(" ")
                        .append(field.getType().getSimpleName()).append(" ")
                        .append(field.getName()).append("\n");
            }
        }

        // Methods
        Method[] methods = clazz.getDeclaredMethods();
        if (methods.length > 0) {
            description.append("Methods:\n");
            int count = 1;
            for (Method method : methods) {
                if (Modifier.isPublic(method.getModifiers()) && method.getParameterCount() == 0) {
                    description.append(count).append(") ").append(method.toString()).append("\n");
                    count++;
                }
            }
        }

        return description.toString();
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the object to inspect:");
        String input = scanner.nextLine();

        // Create an object of the specified class
        Object obj = Class.forName(input).newInstance();

        // Print class description
        System.out.println(getClassDescription(obj.getClass()));

        // Select a method to invoke
        System.out.println("Enter the method number to invoke (or 'exit' to quit):");
        while (true) {
            String methodNumber = scanner.nextLine();
            if (methodNumber.equals("exit")) {
                break;
            }

            int index = Integer.parseInt(methodNumber) - 1;
            Method[] methods = obj.getClass().getDeclaredMethods();
            int count = 0;
            for (Method method : methods) {
                if (Modifier.isPublic(method.getModifiers()) && method.getParameterCount() == 0) {
                    if (count == index) {
                        Object result = method.invoke(obj);
                        System.out.println("Result of method " + method.getName() + ": " + result);
                        break;
                    }
                    count++;
                }
            }
        }

        scanner.close();
    }
}
