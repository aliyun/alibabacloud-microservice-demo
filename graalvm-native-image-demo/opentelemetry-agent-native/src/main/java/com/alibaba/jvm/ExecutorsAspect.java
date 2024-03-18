package com.alibaba.jvm;

import com.oracle.svm.core.annotate.Advice;
import com.oracle.svm.core.annotate.Aspect;
import io.opentelemetry.javaagent.shaded.instrumentation.api.util.VirtualField;
import io.opentelemetry.javaagent.bootstrap.Java8BytecodeBridge;
import io.opentelemetry.javaagent.bootstrap.executors.ContextPropagatingRunnable;
import io.opentelemetry.javaagent.bootstrap.executors.ExecutorAdviceHelper;
import io.opentelemetry.javaagent.bootstrap.executors.PropagatedContext;
import io.opentelemetry.javaagent.shaded.io.opentelemetry.context.Context;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.function.Predicate;

/**
 * This is the native image version of
 * {@link io.opentelemetry.javaagent.instrumentation.executors.JavaExecutorInstrumentation}
 * and
 * {@link io.opentelemetry.javaagent.instrumentation.executors.ExecutorMatchers}.
 */
@SuppressWarnings("OtelPrivateConstructorForUtilityClass")
@Aspect(matchers = {
        "java.util.concurrent.CompletableFuture$ThreadPerTaskExecutor",
        "java.util.concurrent.Executors$DelegatedExecutorService",
        "java.util.concurrent.Executors$FinalizableDelegatedExecutorService",
        "java.util.concurrent.ForkJoinPool",
        "java.util.concurrent.ScheduledThreadPoolExecutor",
        "java.util.concurrent.ThreadPoolExecutor",
        "java.util.concurrent.ThreadPerTaskExecutor" })
public class ExecutorsAspect {

    @Advice.ResultWrapper
    public static class ResultWrapper {
        @Advice.BeforeResult
        public PropagatedContext ret;
        public Optional<Runnable> newTask;
    }

    @Advice.Before(value = { "execute", "addTask" }, notFoundAction = Advice.NotFoundAction.ignore)
    public static ResultWrapper beforeExecute(@Advice.Rewrite(field = "newTask") Runnable task) {
        try {
            ResultWrapper resultWrapper = new ResultWrapper();
            Context context = Java8BytecodeBridge.currentContext();
            if (!ExecutorAdviceHelper.shouldPropagateContext(context, task)) {
                return resultWrapper;
            }
            if (ContextPropagatingRunnable.shouldDecorateRunnable(task)) {
                resultWrapper.newTask = Optional
                        .of(ContextPropagatingRunnable.propagateContext(task, context));
                return resultWrapper;
            }
            VirtualField<Runnable, PropagatedContext> virtualField = VirtualField.find(Runnable.class,
                    PropagatedContext.class);
            resultWrapper.ret = ExecutorAdviceHelper.attachContextToTask(context, virtualField, task);
            return resultWrapper;
        } catch (Throwable t) {
            ResultWrapper resultWrapper = new ResultWrapper();
            resultWrapper.newTask = null;
            resultWrapper.ret = null;
            return resultWrapper;
        }
    }

    @Advice.After(value = { "execute",
            "addTask" }, onThrowable = Throwable.class, notFoundAction = Advice.NotFoundAction.ignore)
    public static void afterExecute(
            @Advice.BeforeResult ResultWrapper resultWrapper,
            @Advice.Thrown Throwable throwable) {
        try {
            ExecutorAdviceHelper.cleanUpAfterSubmit(resultWrapper.ret, throwable);
        } catch (Throwable t) {
        }

    }

    @Advice.Before(value = { "execute", "submit",
            "invoke" }, notFoundAction = Advice.NotFoundAction.ignore)
    public static PropagatedContext enterJobSubmit(ForkJoinTask<?> task) {
        try {
            Context context = Java8BytecodeBridge.currentContext();
            if (ExecutorAdviceHelper.shouldPropagateContext(context, task)) {
                VirtualField<ForkJoinTask<?>, PropagatedContext> virtualField = VirtualField.find(ForkJoinTask.class,
                        PropagatedContext.class);
                return ExecutorAdviceHelper.attachContextToTask(context, virtualField, task);
            }
            return null;
        } catch (Throwable t) {
            return null;
        }
    }

    @Advice.After(value = { "execute", "submit",
            "invoke" }, onThrowable = Throwable.class, notFoundAction = Advice.NotFoundAction.ignore)
    public static void exitJobSubmit(
            @Advice.BeforeResult PropagatedContext propagatedContext,
            @Advice.Thrown Throwable throwable) {
        try {
            ExecutorAdviceHelper.cleanUpAfterSubmit(propagatedContext, throwable);
        } catch (Throwable t) {

        }
    }

    @Advice.Before(value = { "submit",
            "schedule" }, notFoundAction = Advice.NotFoundAction.ignore, onlyWithReturnType = SubClassOfFuture.class)
    public static PropagatedContext enterSubmitRunnable(Runnable task) {
        try {
            Context context = Java8BytecodeBridge.currentContext();
            if (ExecutorAdviceHelper.shouldPropagateContext(context, task)) {
                VirtualField<Runnable, PropagatedContext> virtualField = VirtualField.find(Runnable.class,
                        PropagatedContext.class);
                return ExecutorAdviceHelper.attachContextToTask(context, virtualField, task);
            }
            return null;
        } catch (Throwable t) {
            return null;
        }
    }

    @Advice.After(value = { "submit",
            "schedule" }, onThrowable = Throwable.class, notFoundAction = Advice.NotFoundAction.ignore, onlyWithReturnType = SubClassOfFuture.class)
    public static void exitSubmitRunnable(Runnable task,
            @Advice.BeforeResult PropagatedContext propagatedContext,
            @Advice.Thrown Throwable throwable,
            @Advice.Return Future<?> future) {
        try {
            if (propagatedContext != null && future != null) {
                VirtualField<Future<?>, PropagatedContext> virtualField = VirtualField.find(Future.class,
                        PropagatedContext.class);
                virtualField.set(future, propagatedContext);
            }
            ExecutorAdviceHelper.cleanUpAfterSubmit(propagatedContext, throwable);
        } catch (Throwable t) {

        }
    }

    @Advice.Before(value = { "submit",
            "schedule" }, notFoundAction = Advice.NotFoundAction.ignore, onlyWithReturnType = SubClassOfFuture.class)
    public static PropagatedContext enterSubmitCallable(Callable<?> task) {
        try {
            Context context = Java8BytecodeBridge.currentContext();
            if (ExecutorAdviceHelper.shouldPropagateContext(context, task)) {
                VirtualField<Callable<?>, PropagatedContext> virtualField = VirtualField.find(Callable.class,
                        PropagatedContext.class);
                return ExecutorAdviceHelper.attachContextToTask(context, virtualField, task);
            }
            return null;
        } catch (Throwable t) {
            return null;
        }
    }

    @Advice.After(value = { "submit",
            "schedule" }, onThrowable = Throwable.class, notFoundAction = Advice.NotFoundAction.ignore, onlyWithReturnType = SubClassOfFuture.class)
    public static void exitSubmitCallable(
            Callable<?> task,
            @Advice.BeforeResult PropagatedContext propagatedContext,
            @Advice.Thrown Throwable throwable,
            @Advice.Return Future<?> future) {
        try {
            if (propagatedContext != null && future != null) {
                VirtualField<Future<?>, PropagatedContext> virtualField = VirtualField.find(Future.class,
                        PropagatedContext.class);
                virtualField.set(future, propagatedContext);
            }
            ExecutorAdviceHelper.cleanUpAfterSubmit(propagatedContext, throwable);
        } catch (Throwable t) {

        }
    }

    @Advice.Before(value = { "invokeAny", "invokeAll" }, notFoundAction = Advice.NotFoundAction.ignore)
    public static Collection<?> submitEnter(
            Collection<? extends Callable<?>> tasks) {
        try {
            if (tasks == null) {
                return Collections.emptyList();
            }

            Context context = Java8BytecodeBridge.currentContext();
            for (Callable<?> task : tasks) {
                if (ExecutorAdviceHelper.shouldPropagateContext(context, task)) {
                    VirtualField<Callable<?>, PropagatedContext> virtualField = VirtualField.find(Callable.class,
                            PropagatedContext.class);
                    ExecutorAdviceHelper.attachContextToTask(context, virtualField, task);
                }
            }

            // returning tasks and not propagatedContexts to avoid allocating another list
            // just for an edge case (exception)
            return tasks;
        } catch (Throwable t) {
            return null;
        }
    }

    @Advice.After(value = { "invokeAny",
            "invokeAll" }, onThrowable = Throwable.class, notFoundAction = Advice.NotFoundAction.ignore)
    public static void submitExit(Collection<? extends Callable<?>> originalTasks,
            @Advice.BeforeResult Collection<? extends Callable<?>> tasks,
            @Advice.Thrown Throwable throwable) {
        try {
            /*
             * Note1: invokeAny doesn't return any futures so all we need to do for it
             * is to make sure we close all scopes in case of an exception.
             * Note2: invokeAll does return futures - but according to its documentation
             * it actually only returns after all futures have been completed - i.e. it
             * blocks.
             * This means we do not need to setup any hooks on these futures, we just need
             * to clear
             * any parent spans in case of an error.
             * (according to ExecutorService docs and AbstractExecutorService code)
             */
            if (throwable != null) {
                for (Callable<?> task : tasks) {
                    if (task != null) {
                        VirtualField<Callable<?>, PropagatedContext> virtualField = VirtualField.find(Callable.class,
                                PropagatedContext.class);
                        PropagatedContext propagatedContext = virtualField.get(task);
                        ExecutorAdviceHelper.cleanUpAfterSubmit(propagatedContext, throwable);
                    }
                }
            }
        } catch (Throwable t) {

        }
    }

    static class SubClassOfFuture implements Predicate<Class<?>> {
        @Override
        public boolean test(Class<?> clazz) {
            return Advice.isInterfaceOf(Future.class, clazz);
        }
    }
}
