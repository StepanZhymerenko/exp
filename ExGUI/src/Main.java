import javax.swing.*;
import java.awt.event.*;
import java.lang.reflect.*;

public class Main {
    private static JTextArea resultArea;
    private static JTextField classNameField;

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

        // Constructors
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        if (constructors.length > 0) {
            description.append("Constructors:\n");
            for (Constructor<?> constructor : constructors) {
                description.append(Modifier.toString(constructor.getModifiers())).append(" ")
                        .append(clazz.getSimpleName()).append("(");
                Class<?>[] paramTypes = constructor.getParameterTypes();
                for (int i = 0; i < paramTypes.length; i++) {
                    description.append(paramTypes[i].getSimpleName());
                    if (i < paramTypes.length - 1) {
                        description.append(", ");
                    }
                }
                description.append(")\n");
            }
        }

        // Methods
        Method[] methods = clazz.getDeclaredMethods();
        if (methods.length > 0) {
            description.append("Methods:\n");
            for (Method method : methods) {
                description.append(Modifier.toString(method.getModifiers())).append(" ")
                        .append(method.getReturnType().getSimpleName()).append(" ")
                        .append(method.getName()).append("(");
                Class<?>[] paramTypes = method.getParameterTypes();
                for (int i = 0; i < paramTypes.length; i++) {
                    description.append(paramTypes[i].getSimpleName());
                    if (i < paramTypes.length - 1) {
                        description.append(", ");
                    }
                }
                description.append(")\n");
            }
        }

        return description.toString();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Class Description");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel classNameLabel = new JLabel("Enter class name:");
        panel.add(classNameLabel);

        classNameField = new JTextField(20);
        panel.add(classNameField);

        JButton getDescriptionButton = new JButton("Get Description");
        panel.add(getDescriptionButton);

        getDescriptionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String className = classNameField.getText();
                try {
                    String description = getClassDescription(className);
                    resultArea.setText(description);
                } catch (ClassNotFoundException ex) {
                    resultArea.setText("Class not found: " + className);
                }
            }
        });

        resultArea = new JTextArea(20, 40);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        panel.add(scrollPane);

        frame.pack();
        frame.setVisible(true);
    }
}
