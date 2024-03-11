import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

// Інтерфейс, який будемо профілювати та трасувати
interface Service {
    void doSomething();
}

// Реалізація інтерфейсу Service
class ServiceImpl implements Service {
    @Override
    public void doSomething() {
        System.out.println("Doing something...");
    }
}

// Обробник викликів методів для профілювання
class ProfilingHandler implements InvocationHandler {
    private final Object target;

    public ProfilingHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.nanoTime();
        Object result = method.invoke(target, args);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Перетворення на мілісекунди
        System.out.println("Method '" + method.getName() + "' execution time: " + duration + " ms");
        return result;
    }
}

// Обробник викликів методів для трасування
class TracingHandler implements InvocationHandler {
    private final Object target;

    public TracingHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Method '" + method.getName() + "' called with arguments: " + Arrays.toString(args));
        Object result = method.invoke(target, args);
        System.out.println("Method '" + method.getName() + "' returned: " + result);
        return result;
    }
}

public class Main {
    public static void main(String[] args) {
        // Створення реального об'єкта
        Service realService = new ServiceImpl();

        // Створення проксі-об'єкта для профілювання
        Service profilingProxy = (Service) Proxy.newProxyInstance(
                Service.class.getClassLoader(),
                new Class[]{Service.class},
                new ProfilingHandler(realService)
        );

        // Виклик методу через проксі для профілювання
        profilingProxy.doSomething();

        // Створення проксі-об'єкта для трасування
        Service tracingProxy = (Service) Proxy.newProxyInstance(
                Service.class.getClassLoader(),
                new Class[]{Service.class},
                new TracingHandler(realService)
        );

        // Виклик методу через проксі для трасування
        tracingProxy.doSomething();
    }
}
