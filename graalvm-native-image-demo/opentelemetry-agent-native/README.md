# About
This project adds GraalVM native image support for [OpenTelemetry-java-instrumentation](https://github.com/open-telemetry/opentelemetry-java-instrumentation) 1.32.0. It serves as an input for native image building of OT agent-enabled applications.

There are two things that the GraalVM [agent support PR](https://github.com/oracle/graal/pull/8077) can't automatically handle when supporting the agent instrumentation in native image:
1. The actions in agent `premain`. Java agent usually initializes the context and registers the class transformations in the premain phase. Native image needs the `premain` to do part of such works, e.g. initializations to activate the agent at runtime is necessary, but the actual bytecode transformations should be excluded. In this project, we provide a native version of OT agent premain via GraalVM's substitution mechanism. See [Target_io_opentelemetry_javaagent_OpenTelemetryAgent](src/main/java/com/alibaba/jvm/Target_io_opentelemetry_javaagent_OpenTelemetryAgent.java) for details.
2. The JDK class modifications. GraalVM can automatically compile transformed application classes into native image, but can't do the same work for transformed JDK classes. Because GraalVM itself is a Java application. Modified JDK classes shall influence GraalVM's building behaviors as well. And GraalVM has modified some JDK classes to fit the native image runtime, agent modifications could conflict with GraalVM modifications. Therefore, agent developers should provide the native image runtime version of JDK transformations.

### Native Image Runtime `premain` Support
There are two approaches to define native image runtime `premain` actions.
1. Dectect the actual runtime environment and provide different actions accordingly. For example, the `java.vm.name` system property is "Substrate VM" for native image:
   ```java
        String vm = System.getProperty("java.vm.name");
        if ("Substrate VM".equals(vm)) {
            // In native image
            ...
        } else {
            // In JVM
            ...
        }
   ```  
2. Define native image runtime substitutions for `premain` with GraalVM APIs. The substitution classes, methods and fields will replace the originals at native image build time and take effects at native image runtime. See [here](https://www.graalvm.org/sdk/javadoc/com/oracle/svm/core/annotate/TargetClass.html) for the complete introduction of GraalVM's substitution annotation system. In this project, [Target_io_opentelemetry_javaagent_OpenTelemetryAgent](src/main/java/com/alibaba/jvm/Target_io_opentelemetry_javaagent_OpenTelemetryAgent.java) is the substitution entry point for `premain` actions.
    
## Enhance Classes with Advice Annotations
GraalVM's substitution system can handle the basic class enhancement requirements. But in practice, agents may instrument classes in more complicated means. Take OpenTelemetry java agent for example, it uses [Byte  Buddy](https://github.com/raphw/byte-buddy) for complicated instrumentations such as exception interception, instrumenting certain methods for all subclasses of one class in batch, etc.

To ease the native image adaption, Byte-Buddy-styled annotations are also provided in the agent support PR. We refer to them as advice annotations.
The basic rules are:

- Class annotation `@Aspect` defines which classes will be enhanced. See the [source](https://github.com/oracle/graal/pull/8077/files#diff-2f2c248dd98c839fd85314a71d90f0d435773c3f40fc8a388ed4a9bb50440a6e) for details.
- Method annotation `@Advice.Before` and `@Advice.After` define the actual enhancement before and after the specified method. We refer these methods as advice methods. The advice methods must be **public static**. See the [source](https://github.com/oracle/graal/pull/8077/files#diff-50c767f452a495e5b7db040c23c74c2bb03b472cadd52674d5647280ada2fdcb) for details.

As the the agent support PR is not merged into GraalVM master, these annotations are still not publicly available. The preview version can be obtained by compiling the PR. In this project, [graal-sdk.jar](libs/graal-sdk.jar) for JDK 17, and [nativeimage.jar](libs/nativeimage.jar) for JDK 21 are provided.

### Exemptions of JDK Methods
Some JDK methods can be exempted from providing native image version, because they are entirely not supported in native image and won't get called anyway.
For example, in OpenTelemetry java agent, `java.lang.ClassLoader#defineClass` is instrumented to make sure its super class is loaded before the class is defined, See [io.opentelemetry.javaagent.instrumentation.internal.classloader.DefineClassInstrumentation](https://github.com/open-telemetry/opentelemetry-java-instrumentation/blob/main/instrumentation/internal/internal-class-loader/javaagent/src/main/java/io/opentelemetry/javaagent/instrumentation/internal/classloader/DefineClassInstrumentation.java) for details. But in native image, no class can be actually defined at build time, so there is no need to make such enhancement.

Another example is that `java.lang.ClassLoader#loadClass` is instrumented to inject helper classes when loading certain classes. This is a specified transformation action, and no need to take in native image runtime, because the transformed results have been already captured by native-image-agent at [the interception stage](https://github.com/oracle/graal/pull/8077#:~:text=in%20three%20stages%3A-,Interception%20stage%3A,-native%2Dimage%2Dagent).  

Here are examples of how to use the advice annotations. 
### Declare classes to be instrumented
@Aspect annotation has 3 methods to declare different kinds of class-matching strategies:
1. `subClassOf`: match all subclasses of the specified class. See [LoggerAspect.java](src/main/java/com/alibaba/jvm/LoggerAspect.java) for details.
2. `implementInterface`: match all implementation classes of the specified class. See [CallableAspect.java](src/main/java/com/alibaba/jvm/CallableAspect.java) for details.
3. `matchers`: Match the specified classes. It could be one or more classes. See [ExecutorsAspect.java](src/main/java/com/alibaba/jvm/ExecutorsAspect.java) for details.

### Declare Advice Methods
There are two kinds of advice methods, before and after, executing before and after the target method is invoked.
By default, the target method is matched by the annotated method's name and parameters. 

The advice methods must **public static**.

Here are examples of how to use advice method annotations:

- Match multiple methods with the same parameters: all methods in [ExecutorsAspect](src/main/java/com/alibaba/jvm/ExecutorsAspect.java)
- Rewrite input parameter: `beforeExecute` in [ExecutorsAspect](src/main/java/com/alibaba/jvm/ExecutorsAspect.java)
- Restrict returning type: `exitSubmitRunnable` in [ExecutorsAspect](src/main/java/com/alibaba/jvm/ExecutorsAspect.java)
- Exception interception: `exitSubmitRunnable` in [ExecutorsAspect](src/main/java/com/alibaba/jvm/ExecutorsAspect.java)
- Pass returning value from before method to after method: `enterSubmitCallable` and `exitSubmitCallable` in [ExecutorsAspect](src/main/java/com/alibaba/jvm/ExecutorsAspect.java)
- Refer to `this` in advice methods: `beforeRun` in [RunnableAspect](src/main/java/com/alibaba/jvm/RunnableAspect.java)
- Refer to the field of target method's class: Not supported by annotations, need to use reflection.

### Extra Reflection Registration
[The interception stage](https://github.com/oracle/graal/pull/8077#:~:text=in%20three%20stages%3A-,Interception%20stage%3A,-native%2Dimage%2Dagent) is supposed to capture reflections. But it misses some reflections in OpenTelemetry case. For example, the following code should use reflection at runtime.
``` java
VirtualField<Callable<?>, PropagatedContext> virtualField = VirtualField.find(Callable.class, PropagatedContext.class);
```
But the build system of OT can generate the reflection result to replace the `find` invocation at build time by Gradle plugin. So GraalVM's native-image-agent can't see the reflection execution, and no corresponding configuration is recorded, resulting native image run time errors.

[OTFeature](src/main/java/com/alibaba/jvm/OTFeature.java) is added for this issue. It cooperates with GraalVM's feature mechanism to register necessary reflection data at native image build time.

### Shaded Class Dependency
OT shaded some classes to avoid runtime conflict. So transformed classes dumped at [the interception stage](https://github.com/oracle/graal/pull/8077#:~:text=in%20three%20stages%3A-,Interception%20stage%3A,-native%2Dimage%2Dagent) have dependencies on shaded classes, not original classes. To keep dependency consistent, the **shaded** classes should be used when adapting JDK class enhancements to native image version.