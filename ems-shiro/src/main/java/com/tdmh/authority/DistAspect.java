package com.tdmh.authority;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author litairan on 2019/1/8.
 */
@Aspect
@Component
public class DistAspect {
//
//    @Pointcut("@annotation(com.tdmh.authority.DistFilterAnnotation)")
//    public void queryMethodPointcut() {
//    }
//
//    /**
//     * 环绕通知
//     * @param joinPoint ProceedingJoinPoint
//     * @return 方法返回的对象
//     * @throws Throwable 方法执行时抛出的异常，此处不做任何处理，直接抛出
//     */
//    @Around(value = "queryMethodPointcut()")
//    public Object doInterceptor(ProceedingJoinPoint joinPoint) throws Throwable {
//        Object object = joinPoint.proceed();
//        String methodName = this.getMethodName(joinPoint);
//        if (object != null) {
//            if (object instanceof Filterable) {
//                this.doFilter((Filterable) object, methodName);
//            }
//
//            if (object instanceof MetaSetter) {
//                this.metaHandler((MetaSetter)object, methodName);
//            }
//        }
//        return object;
//    }
//
//    /**
//     * 执行过滤操作
//     * @param filterable 方法返回的对象
//     * @param methodName 拦截的方法名称
//     */
//    private void doFilter(Filterable<?> filterable, String methodName) {
//        List<SysDataResource> resources = this.getDataResources(methodName);
//
//        // 如果
//        if (CollectionUtils.isEmpty(resources)) {
//            return;
//        }
//
//        filterable.doFilter(o -> {
//            Map<String, SysDataResource> dataColumnMap = new HashMap<>(resources.size());
//            for (SysDataResource column : resources) {
//                dataColumnMap.put(column.getDataCode(), column);
//            }
//
//            PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(o.getClass());
//            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
//                String name = propertyDescriptor.getName();
//                SysDataResource dataColumn = dataColumnMap.get(name);
//                if (dataColumn != null && !dataColumn.getIsAccessible()) {
//                    try {
//                        propertyDescriptor.getWriteMethod().invoke(o, new Object[] {null});
//                    } catch (Exception ex) {
//                        // skip
//                    }
//                }
//            }
//            return o;
//        });
//    }

//    /**
//     * 设置数据结构
//     * @param metaSetter 方法返回的对象
//     * @param methodName 拦截的方法名称
//     */
//    private void metaHandler(MetaSetter metaSetter, String methodName) {
//        List<SysDataResource> resources = this.getDataResources(methodName);
//        if (resources != null) {
//            metaSetter.setMeta(resources);
//        } else { // 如果没有设置数据资源，默认用户拥有访问全部资源的权限
//            List<SysDataResource> allResources = findAuthorityDataResource(methodName);
//            metaSetter.setMeta(allResources);
//        }
//    }
//
//    /**
//     * 根据方法名和用户ID获取用户的数据权限
//     * @param methodName 拦截的方法名称
//     * @return 用户的数据权限
//     */
//    private List<SysDataResource> getDataResources(String methodName) {
//        String userId = ShiroUtil.getUserId();
//        return this.userAuthorityHelper.getDataResource(methodName, userId);
//    }

//    /**
//     * 获取此方法对应的所有数据资源项
//     * @param methodName 拦截的方法名称
//     * @return 用户的数据权限
//     */
//    private List<SysDataResource> findAuthorityDataResource(String methodName) {
//        return null; // 此处代码省略
//    }
//
//    private String getMethodName(ProceedingJoinPoint joinPoint) {
//        Signature signature = joinPoint.getSignature();
//        // systemSettings.isSupportMethodParams()表示是否支持方法参数，默认支持。如果设置为不支持，则权限中的方法应设置为com.wawscm.shangde.module.security.service.impl.SysUserServiceImpl.findUser
//        if (systemSettings.isSupportMethodParams() && signature instanceof MethodSignature) {
//            MethodSignature methodSignature = (MethodSignature)signature;
//
//            StringBuilder sb = new StringBuilder();
//
//            sb.append(methodSignature.getDeclaringTypeName());
//            sb.append(".");
//            sb.append(methodSignature.getName());
//            sb.append("(");
//            Class<?>[] parametersTypes = methodSignature.getParameterTypes();
//            for (int i = 0; i < parametersTypes.length; i++) {
//                if (i > 0) {
//                    sb.append(",");
//                }
//                Class<?> parametersType = parametersTypes[i];
//                sb.append(parametersType.getSimpleName());
//            }
//            sb.append(")");
//            return sb.toString();
//        } else {
//            StringBuilder sb = new StringBuilder();
//            sb.append(signature.getDeclaringTypeName());
//            sb.append(".");
//            sb.append(signature.getName());
//            return sb.toString();
//        }
//    }
}
