public record CallingInfo(String className, String methodName) {}

public class CallerInfoUtil {
    public static CallingInfo callingInfo() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();

        if (stackTrace.length < 2) {
            throw new IllegalStateException("Stack trace is too short.");
        }

        StackTraceElement caller = stackTrace[1];
        String className = caller.getClassName();
        String methodName = caller.getMethodName();

        return new CallingInfo(className, methodName);
    }
}