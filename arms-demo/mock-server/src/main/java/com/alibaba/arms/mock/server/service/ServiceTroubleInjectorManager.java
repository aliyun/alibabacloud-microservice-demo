package com.alibaba.arms.mock.server.service;

import com.alibaba.arms.mock.server.service.trouble.DefaultErrorTroubleInjector;
import com.alibaba.arms.mock.server.service.trouble.DefaultSlowTroubleInjector;
import com.alibaba.arms.mock.server.service.trouble.IServiceTroubleInjector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author aliyun
 * @date 2022/02/08
 */
@Service
@Slf4j
public class ServiceTroubleInjectorManager {

    @Autowired
    private List<IServiceTroubleInjector> troubleInjectors;

    @Autowired
    private List<IComponent> components;

    private Map<String, IServiceTroubleInjector> troubleInjectorMap = new HashMap<>();
    private Map<String, IComponent> componentMap = new HashMap<>();

    @Autowired
    private DefaultErrorTroubleInjector defaultErrorTroubleInjector;

    @Autowired
    private DefaultSlowTroubleInjector defaultSlowTroubleInjector;

    @PostConstruct
    public void init() {
        if (null == troubleInjectors) {
            return;
        }

        for (IComponent component : components) {
            componentMap.put(component.getComponentName().toLowerCase(), component);
        }

        troubleInjectors.forEach(e -> {
            if (null != troubleInjectorMap.put(e.getComponentName(), e)) {
                throw new RuntimeException("duplicated component name " + e.getComponentName());
            }
        });
    }

    public void cancelInjectError(String componentName) {
        IServiceTroubleInjector troubleInjector = troubleInjectorMap.get(componentName);
        if (null == troubleInjector) {
            log.warn("cannot find trouble injector by {}, use default trouble injector", componentName);
            IComponent component = componentMap.get(componentName);
            if (null == component) {
                log.warn("cannot find component by name {}", componentName);
            } else {
                String fullClassName = component.getImplClass().getCanonicalName();
                String methodName = "execute";
                Map<String, String> params = new HashMap<>();
                params.put("fullMethodName", fullClassName + "#" + methodName);
                defaultErrorTroubleInjector.cancel(params);
            }
        } else {
            troubleInjector.cancelInjectError();
        }
    }


    public void cancelInjectSlow(String componentName) {
        IServiceTroubleInjector troubleInjector = troubleInjectorMap.get(componentName);
        if (null == troubleInjector) {
            log.warn("cannot find trouble injector by {}, use default trouble injector", componentName);
            IComponent component = componentMap.get(componentName);
            if (null == component) {
                log.warn("cannot find component by name {}", componentName);
            } else {
                String fullClassName = component.getImplClass().getCanonicalName();
                String methodName = "execute";
                Map<String, String> params = new HashMap<>();
                params.put("fullMethodName", fullClassName + "#" + methodName);
                defaultSlowTroubleInjector.cancel(params);
            }
        } else {
            troubleInjector.cancelInjectSlow();
        }
    }

    public void injectError(String componentName, Map<String, String> params) {

        IServiceTroubleInjector troubleInjector = troubleInjectorMap.get(componentName);
        if (null == troubleInjector) {
            log.warn("cannot find trouble injector by {}, use default trouble injector", componentName);
            IComponent component = componentMap.get(componentName);
            if (null == component) {
                log.warn("cannot find component by name {}", componentName);
            } else {
                String fullClassName = component.getImplClass().getCanonicalName();
                String methodName = "execute";
                params.put("fullMethodName", fullClassName + "#" + methodName);
                defaultErrorTroubleInjector.make(params);
            }
        } else {
            troubleInjector.injectError(params);
        }
    }

    public void injectSlow(String componentName, Map<String, String> params, String seconds) {
        IServiceTroubleInjector troubleInjector = troubleInjectorMap.get(componentName);
        if (null == troubleInjector) {
            log.warn("cannot find trouble injector by {}, use default trouble injector", componentName);
            IComponent component = componentMap.get(componentName);
            if (null == component) {
                log.warn("cannot find component by name {}", componentName);
            } else {
                String fullClassName = component.getImplClass().getCanonicalName();
                String methodName = "execute";
                params.put("fullMethodName", fullClassName + "#" + methodName);
                defaultSlowTroubleInjector.make(params);
            }
        } else {
            troubleInjector.injectSlow(params, seconds);
        }
    }

    public void injectBig(String componentName, Map<String, String> params, String size) {
        IServiceTroubleInjector troubleInjector = troubleInjectorMap.get(componentName);
        if (null == troubleInjector) {
            log.warn("cannot find trouble injector by {}, use default trouble injector", componentName);
            IComponent component = componentMap.get(componentName);
            if (null == component) {
                log.warn("cannot find component by name {}", componentName);
            }
        } else {
            troubleInjector.injectBig(params, size);
        }
    }

    public void cancelInjectBig(String componentName) {
        IServiceTroubleInjector troubleInjector = troubleInjectorMap.get(componentName);
        if (null == troubleInjector) {
            log.warn("cannot find trouble injector by {}, use default trouble injector", componentName);
            IComponent component = componentMap.get(componentName);
            if (null == component) {
                log.warn("cannot find component by name {}", componentName);
            } else {
                String fullClassName = component.getImplClass().getCanonicalName();
                String methodName = "execute";
                Map<String, String> params = new HashMap<>();
                params.put("fullMethodName", fullClassName + "#" + methodName);
                defaultSlowTroubleInjector.cancel(params);
            }
        } else {
            troubleInjector.cancelInjectBig();
        }
    }
}
