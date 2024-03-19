package com.alibaba.jvm;

import com.oracle.svm.core.annotate.Advice;
import com.oracle.svm.core.annotate.Aspect;
import io.opentelemetry.javaagent.bootstrap.executors.PropagatedContext;
import io.opentelemetry.javaagent.bootstrap.executors.TaskAdviceHelper;
import io.opentelemetry.javaagent.shaded.instrumentation.api.util.VirtualField;
import io.opentelemetry.javaagent.shaded.io.opentelemetry.context.Scope;

/**
 * This is the native image version of
 * {@link io.opentelemetry.javaagent.instrumentation.executors.RunnableInstrumentation}.
 */
@Aspect(implementInterface = "java.lang.Runnable", onlyWith = Aspect.JDKClassOnly.class)
public class RunnableAspect {

    @Advice.Before("run")
    public static Scope beforeRun(@Advice.This Runnable thiz) {
        try {
            VirtualField<Runnable, PropagatedContext> virtualField = VirtualField.find(Runnable.class,
                    PropagatedContext.class);
            return TaskAdviceHelper.makePropagatedContextCurrent(virtualField, thiz);
        } catch (Throwable t) {
            return null;
        }

    }

    @Advice.After(value = "run", onThrowable = Throwable.class)
    public static void afterRun(@Advice.BeforeResult Scope scope) {
        try {
            if (scope != null) {
                scope.close();
            }
        } catch (Throwable t) {

        }

    }
}
