package ml.windleaf.api.utils;

import java.lang.reflect.Modifier;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class ResolverUtil<T> {
    private final Class<? extends T> cls;

    public ResolverUtil(Class<? extends T> cls) {
        this.cls = cls;
    }

    public List<Class<? extends T>> getClassesBySuperclass(String packagePath) {
        List<Class<? extends T>> list = new ArrayList<>();
        org.apache.logging.log4j.core.config.plugins.util.ResolverUtil resolver = new org.apache.logging.log4j.core.config.plugins.util.ResolverUtil();
        resolver.setClassLoader(cls.getClassLoader());
        resolver.findInPackage(new org.apache.logging.log4j.core.config.plugins.util.ResolverUtil.Test() {
            @Override
            public boolean matches(Class<?> type) {
                return true;
            }

            @Override
            public boolean matches(URI resource) {
                return true;
            }

            @Override
            public boolean doesMatchClass() {
                return true;
            }

            @Override
            public boolean doesMatchResource() {
                return true;
            }
        }, packagePath);
        resolver.getClasses().forEach(it -> {
            if (cls.isAssignableFrom(it)
                && !it.isInterface()
                && !Modifier.isAbstract(it.getModifiers())) {
                list.add((Class<? extends T>) it);
            }
        });
        return list;
    }
}
