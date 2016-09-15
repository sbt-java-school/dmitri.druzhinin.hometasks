package hometask8;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProxyUtils {
    private ProxyUtils(){}

    private static class CacheKey{
        private Method method;
        private Object[] args;
        private CacheKey(Method method, Object[] args){
            this.method=method;
            this.args=args;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CacheKey that = (CacheKey) o;

            if (method==null || that.method==null) return false;
            return Arrays.equals(args, that.args);

        }

        @Override
        public int hashCode() {
            int result = method != null ? method.hashCode() : 0;
            result = 31 * result + Arrays.hashCode(args);
            return result;
        }
    }
    private static class CacheValue{
        private Object result;
        private long timeOfResult;
        private CacheValue(Object result, long mlscs){
            this.result=result;
            this.timeOfResult=mlscs;
        }
    }



    static <T> T makeCached(T object){
        Object proxy=Proxy.newProxyInstance(ProxyUtils.class.getClassLoader(), object.getClass().getInterfaces(), new InvocationHandler() {
            private T delegate=object;
            private Map<CacheKey, CacheValue> cache=new HashMap<>();

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object result;
                Cache cacheAnnotation=method.getAnnotation(Cache.class);
                if(cacheAnnotation==null){
                    cacheAnnotation=delegate.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes()).getAnnotation(Cache.class);
                }
                if(cacheAnnotation==null){
                    result=method.invoke(delegate, args);
                    System.out.println("Non-cacheable method\n");
                }else{
                    CacheKey cacheKey=new CacheKey(method, args);
                    CacheValue cacheValue=cache.get(cacheKey);
                    if(cacheValue==null || (System.currentTimeMillis()-cacheValue.timeOfResult) >= cacheAnnotation.value()){
                        updateCache(cacheKey, method.invoke(delegate, args));
                        cacheValue=cache.get(cacheKey);
                    }
                    result=cacheValue.result;
                }
                return result;
            }

            private void updateCache(CacheKey cacheKey, Object newResult){
                CacheValue cacheValue=new CacheValue(newResult, System.currentTimeMillis());
                cache.put(cacheKey, cacheValue);
                System.out.println("Update cache\n");
            }

        });
        return (T)proxy;
    }
}
