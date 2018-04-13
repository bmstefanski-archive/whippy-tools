/*
 MIT License

 Copyright (c) 2018 Whippy Tools

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package pl.bmstefanski.tools.util.reflect;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import pl.bmstefanski.tools.util.SafeUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

 /*
    Author: Dzikoysk
    Source: https://github.com/FunnyGuilds/FunnyGuilds/blob/master/src/main/java/net/dzikoysk/funnyguilds/util/reflect/PacketSender.java
 */

public class Reflections {

    private static final Map<String, Class<?>> CLASS_CACHE = new HashMap<>();
    private static final Map<String, Field> FIELD_CACHE = new HashMap<>();
    private static final Map<String, FieldAccessor<?>> FIELD_ACCESSOR_CACHE = new HashMap<>();
    private static final Map<String, Method> METHOD_CACHE = new HashMap<>();
    private static final Class<?> INVALID_CLASS = InvalidMarker.class;
    private static final Method INVALID_METHOD = SafeUtils.safeInit(() -> InvalidMarker.class.getDeclaredMethod("invalidMethodMarker"));
    private static final Field INVALID_FIELD = SafeUtils.safeInit(() -> InvalidMarker.class.getDeclaredField("invalidFieldMarker"));
    private static final FieldAccessor<?> INVALID_FIELD_ACCESSOR = getField(INVALID_CLASS, Void.class, 0);

    public static String getVersion() {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        return name.substring(name.lastIndexOf('.') + 1) + ".";
    }

    public static String getFixedVersion() {
        return Bukkit.getServer().getClass().getPackage().getName();
    }

    public static Class<?> getClassCache(String className) {
        CLASS_CACHE.remove(className);
        return getClass(className);
    }

    public static Class<?> getClass(String className) {
        Class<?> clazz = CLASS_CACHE.get(className);

        if (clazz != null) {
            return clazz != INVALID_CLASS ? clazz : null;
        }

        try {
            clazz = Class.forName(className);
            CLASS_CACHE.put(className, clazz);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();

            CLASS_CACHE.put(className, INVALID_CLASS);
        }

        return clazz;
    }

    public static Class<?> getCraftClass(String name) {
        return getClass("net.minecraft.server." + getVersion() + name);
    }

    public static Class<?> getBukkitClass(String name) {
        return getClass("org.bukkit.craftbukkit." + getVersion() + name);
    }

    public static Object getHandle(Entity entity) {
        try {
            return getMethod(entity.getClass(), "getHandle").invoke(entity);
        } catch (InvocationTargetException | IllegalAccessException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static Object getHandle(World world) {
        try {
            return getMethod(world.getClass(), "getHandle").invoke(world);
        } catch (InvocationTargetException | IllegalAccessException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private static String constructFieldCacheKey(Class<?> clazz, String fieldName) {
        return clazz.getName() + "." + fieldName;
    }

    public static Field getField(Class<?> clazz, String fieldName) {
        String cacheKey = constructFieldCacheKey(clazz, fieldName);
        Field field = FIELD_CACHE.get(cacheKey);

        if (field != null) {
            return field != INVALID_FIELD ? field : null;
        }

        try {
            field = clazz.getDeclaredField(fieldName);

            FIELD_CACHE.put(cacheKey, field);
        } catch (NoSuchFieldException ex) {
            ex.printStackTrace();
            FIELD_CACHE.put(cacheKey, INVALID_FIELD);
        }

        return field;
    }

    public static <T> FieldAccessor<T> getField(Class<?> target, Class<T> fieldType, int index) {
        return getField(target, null, fieldType, index);
    }

    private static <T> FieldAccessor<T> getField(Class<?> target, String fieldName, Class<T> fieldType, int index) {
        String cacheKey = target.getName() + "." + (fieldName != null ? fieldName : "NONE") + "." + fieldType.getName() + "." + index;
        FieldAccessor<T> fieldAccessor = (FieldAccessor<T>) FIELD_ACCESSOR_CACHE.get(cacheKey);

        if (fieldAccessor != null) {
            if (fieldAccessor == INVALID_FIELD_ACCESSOR) {
                throw new IllegalArgumentException("Cannot find field with type " + fieldType);
            }

            return fieldAccessor;
        }

        for (Field field : target.getDeclaredFields()) {
            if (fieldName == null || field.getName().equals(fieldName) && fieldType.isAssignableFrom(field.getType()) && index-- <= 0) {
                field.setAccessible(true);

                fieldAccessor = new FieldAccessor<T>() {

                    @Override
                    public T get(Object target) {
                        try {
                            return (T) field.get(target);
                        } catch (IllegalAccessException ex) {
                            throw new RuntimeException("Cannot access reflection.", ex);
                        }
                    }

                    @Override
                    public void set(Object target, Object value) {
                        try {
                            field.set(target, value);
                        } catch (IllegalAccessException ex) {
                            throw new RuntimeException("Cannot access reflection.", ex);
                        }
                    }

                    @Override
                    public boolean hasField(Object target) {
                        return field.getDeclaringClass().isAssignableFrom(target.getClass());
                    }
                };

                break;
            }
        }

        if (fieldAccessor == null && target.getSuperclass() != null) {
            fieldAccessor = getField(target.getSuperclass(), fieldName, fieldType, index);
        }

        FIELD_ACCESSOR_CACHE.put(cacheKey, fieldAccessor != null ? fieldAccessor : INVALID_FIELD_ACCESSOR);

        if (fieldAccessor == null) {
            throw new IllegalArgumentException("Cannot find field with type " + fieldType);
        }

        return fieldAccessor;
    }


    public static Field getPrivateField(Class<?> clazz, String fieldName) {
        String cacheKey = constructFieldCacheKey(clazz, fieldName);
        Field field = FIELD_CACHE.get(cacheKey);

        if (field != null) {
            return field != INVALID_FIELD ? field : null;
        }

        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);

            FIELD_CACHE.put(cacheKey, field);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            FIELD_CACHE.put(cacheKey, INVALID_FIELD);
        }

        return field;
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... args) {
        String cacheKey = clazz.getName() + "." + methodName + "." + (args == null ? "NONE" : Arrays.toString(args));
        Method method = METHOD_CACHE.get(cacheKey);

        if (method != null) {
            return method != INVALID_METHOD ? method : null;
        }

        for (Method m : clazz.getMethods()) {
            if (m.getName().equals(methodName) && (args == null || classListEqual(args, m.getParameterTypes()))) {
                method = m;
                break;
            }
        }

        METHOD_CACHE.put(cacheKey, method == null ? INVALID_METHOD : method);
        return method;
    }

    public static Method getMethod(Class<?> clazz, String methodName) {
        return getMethod(clazz, methodName, null);
    }

    public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... args) {
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (Arrays.equals(constructor.getParameterTypes(), args)) {
                return constructor;
            }
        }

        return null;
    }

    public static boolean classListEqual(Class<?>[] firstClazz, Class<?>[] secondClazz) {
        if (firstClazz.length != secondClazz.length) {
            return false;
        }

        for (int i = 0; i < firstClazz.length; i++) {
            if (firstClazz[i] != secondClazz[i]) {
                return false;
            }
        }

        return true;
    }

    public interface ConstructorInvoker {
        Object invoke(Object... args);
    }

    public interface MethodInvoker {
        Object invoke(Object target, Object... args);
    }

    public interface FieldAccessor<T> {
        T get(Object target);
        void set(Object target, Object value);
        boolean hasField(Object target);
    }

    private static class InvalidMarker {
        Void invalidFieldMarker;

        void invalidMethodMarker() {

        }
    }

}
