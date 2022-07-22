package ml.windleaf.api.utils;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class ResolverUtil<T> {
    public List<Class<? extends T>> getClassesBySuperclass(String packagePath) {
        List<Class<? extends T>> list = new ArrayList<>();
        Class<?> cls = this.getTClass();
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

    private Class<T> getTClass() {
        return (Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
