package utilities;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryListener implements IRetryAnalyzer, IAnnotationTransformer {

    private static final int DEFAULT_RETRY = 1;

    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {

        int maxRetry = DEFAULT_RETRY;

        String[] groups = result.getMethod().getGroups();

        for (String group : groups) {

            if (group.startsWith("retry")) {

                try {

                    maxRetry = Integer.parseInt(
                            group.replace("retry", "").trim()
                    );

                } catch (NumberFormatException ignored) {

                }
            }
        }

        if (retryCount < maxRetry) {

            retryCount++;

            return true;
        }

        return false;
    }

    @Override
    public void transform(
            ITestAnnotation annotation,
            Class testClass,
            Constructor testConstructor,
            Method testMethod
    ) {

        annotation.setRetryAnalyzer(RetryListener.class);
    }
}