package ml.windleaf.api.utils;

import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil;

import java.lang.reflect.Modifier;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class ClassUtil<T> {
    private final Class<? extends T> cls;

    public ClassUtil(Class<? extends T> cls) {
        this.cls = cls;
    }

    public List<Class<? extends T>> getClassesBySuperclass(String packagePath) {
        List<Class<? extends T>> list = new ArrayList<>();
        ResolverUtil resolver = new ResolverUtil();
        resolver.setClassLoader(cls.getClassLoader());
        resolver.findInPackage(new ResolverUtil.Test() {
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
