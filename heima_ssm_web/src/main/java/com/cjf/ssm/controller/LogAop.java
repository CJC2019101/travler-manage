package com.cjf.ssm.controller;

import com.cjf.ssm.domain.SysLog;
import com.cjf.ssm.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Date;


//@Aspect表示是一个切面类，需要配置 各个通知类型你对应的方法
//内部还是调用 业务层将封装好了的SysLog类信息上传到数据库中。项目的 “任何一个类” 都可以注入依赖，
//我们只是他们分了类，比如说此类，就被分配到了 表现层(因为他调用业务层了)。  以前我们的切面类是在业务层的呢！
@Component
public class LogAop {

    @Autowired
    //用于获取ip地址 request.getRemoteAddr()。前提必须在web.xml文件中配置 RequestContextListener
    //试试在web.xml文件中不配置
    private HttpServletRequest request;
    private Date Sdate;//调用方法的起始时间
    private Class clazz;//被代理的对象。       被代理对象获取到了，方法名、方法参数、注解这些都可以被获取。
    private long time;  //访问时长
    private Method method;//被代理对象执行的方法
    private String methodURL;//方法权限定类名
    private String url;//资源URL路径    尝试getMethodURL
    private String userName;//登录用户名
    @Autowired
    private ISysLogService sysLogService;
    private String ip;//ip地址
    private String EMessage=null;   //异常信息


    public void Destroy(){
        this.EMessage=null;
        this.Sdate=null;
        this.clazz=null;
        this.time=0l;
        this.method=null;
        this.methodURL=null;
        this.url=null;
        this.ip=null;
    }
    //前置通知----------》 获取开始访问的时间
    //JoinPoint： 连接点，被切入点表达式拦截的所有方法，封装了 切入点名称、切入点参数、切入点类对象....。
    public void doBefore(JoinPoint jp){
        //获取调用的起始时间，开始访问的时间。
        Sdate=new Date(System.currentTimeMillis());
      /*  //获取被代理对象的class 文件对象
        clazz=jp.getTarget().getClass();
        //获取方法
        String MethodName=jp.getSignature().getName();//获取方法名称
        Object[] params=jp.getArgs();//获取方法参数
        Class[] args=new Class[params.length];
        for (int i=0;i<params.length;i++){
            args[i]=params[i].getClass();
        }
        if (params.length==0||params==null){
            method=clazz.getMethod(MethodName);
        }else {
            method=clazz.getMethod(MethodName, args);
        }*/
    }

    //最终通知
    /*public void doAfter(JoinPoint jp){
        //获取访问时长，方法访问结束时间减去开始访问的时间。
        long time=new Date(System.currentTimeMillis()).getTime()-date.getTime();
        String URL=null;
        //获取 URL，@RequestMapping的值
        if (clazz!=null&&method!=null&&clazz!=LogAop.class&&clazz!=SysLogController.class){
            //获取类上的RequestMapping注解的class文件对象
            RequestMapping classannotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);//获取RequestMapping注解的class文件对象
           //获取RequestMapping注解的值
            if (classannotation!=null){
                String[] classValue = classannotation.value();
                //获取方法上的RequestMapping注解的class文件对象,并获取注解值
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                if (requestMapping!=null){
                    String[] methodValue = requestMapping.value();
                    URL=classValue[0]+methodValue[0];

                    //获取ip地址
                    String ip= request.getRemoteAddr();

                    //获取操作者名称
                    SecurityContext securityContext= SecurityContextHolder.getContext();//获取 Security框架的上下文
                    User principal = (User) securityContext.getAuthentication().getPrincipal();//获取登录的用户对象。
                    String username=principal.getUsername();
                    SecurityContextImpl contextImpl= (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
                    User user= (User) contextImpl.getAuthentication().getPrincipal();
                    String user_username=user.getUsername();
                    System.out.println("principal:"+username);
                    System.out.println("user_username:"+user_username);

                    //封装SysLog对象，并上传数据库中
                    SysLog sysLog=new SysLog();
                    sysLog.setVisitTime(date);//访问的起始时间
                    sysLog.setUsername(username);
                    sysLog.setExecutionTime(time);
                    sysLog.setIp(ip);
                    sysLog.setUrl(URL);
                    sysLog.setMethod("[类名]"+clazz.getName()+"[方法名]"+method.getName());
                    sysLog.setEMessage(EMessage);
                    try {
                        sysLogService.save(sysLog);
                    } catch (Exception e) {
                        System.out.println("添加用户日志失败。"+e);
                    }
                }

            }
        }
    }*/

    //最终通知      判断异常信息是否为null，不为null封转数据并添加至数据中。
    public void doAfter(){
        if (EMessage!=null){
            SysLog sysLog=new SysLog();
            sysLog.setEMessage(EMessage);
            sysLog.setVisitTime(Sdate);
            sysLog.setUsername(userName);
            sysLog.setIp(ip);
            sysLog.setUrl(url);
            sysLog.setExecutionTime(time);
            sysLog.setMethod(methodURL);
            try {
                sysLogService.save(sysLog);
            } catch (Exception e) {
                System.out.println("添加错误日志信息失败。"+e);
            }finally {
                Destroy();
            }
        }
    }

    /*
     throwing  指定通知中一个形参名且必须是“Throwable”对象或其子类。
         其意义就是获取 目标方法(“切入点”) 中肯定抛出的异常（这是异常通知）。被修饰的通知必须有该形参。
         与其对应的后置通知可以获取到 切入点的返回值。 使用方式： 在后置通知对应的方法中添加一个
         Object类型的参数即可， 相应的配置文件中需要使用 “returnning”属性绑定参数名称，用于获取值。
     */
    //异常通知。      如果有异常则添加Exception异常信息,Error异常无法通过后续代码解决。
    public void AfterThrow(JoinPoint jp,Throwable throwable) throws NoSuchMethodException {

        //获取访问的方法 全限定类名
        // 如：       [类名]com.cjf.ssm.controller.UserController[方法名]findAll
        clazz=jp.getTarget().getClass();//获取目标对象的 class文件对象
        String methodName=jp.getSignature().getName();
        Object[] args=jp.getArgs();
        if (args.length==0||args==null)
            method=clazz.getMethod(methodName);
        else {
            Class[] params=new Class[args.length];
            for (int i=0;i<args.length;i++){
                params[i]=args[i].getClass();
            }
            method=clazz.getMethod(methodName, params);
        }
        methodURL="类名："+clazz.getName()+"方法名："+method.getName();

        //获取资源URL。      获取类上的注解值，方法上的注解值。
        if (clazz!=LogAop.class){
            //获取类上的注解
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if(classAnnotation!=null){
                String[] classValue = classAnnotation.value();//获取RequestMapping注解value属性值。一般都是一个
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if (methodAnnotation!=null){
                    String[] methodValue = methodAnnotation.value();

                    //获取资源URL
                    url=classValue[0]+methodValue[0];

                    //获取异常信息。   空指针异常信息返回的就是“null”
                    if (throwable.getMessage()==null){
                        EMessage="null";
                    }else {
                        EMessage=throwable.getMessage();
                    }

                    //获取ip地址
                    ip=request.getRemoteAddr();
                    if (ip.equals("0:0:0:0:0:0:0:1"))
                        ip="127.0.0.1";

                    //获取访问时长
                    time=new Date(System.currentTimeMillis()).getTime()-Sdate.getTime();

                    //获取访问用户。SpringSecurity在认证登录后，Security会把一个SecurityContextImpl对象存储到Session域中
                    //此对象中有当前登录用户的各种资料。
                         // SpringSecurity框架将登录成功的用户(User)存放在了Session域中。 key值："SPRING_SECURITY_CONTEXT"
                    SecurityContextImpl sessionUser = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
                    User Suser= (User) sessionUser.getAuthentication().getPrincipal();
                        // 也可以通过 SpringSecurity的域对象获取登录对象（User）
                    SecurityContext contextUser=SecurityContextHolder.getContext();
                    User Cuer= (User) contextUser.getAuthentication().getPrincipal();
                    userName=Suser.getUsername();


                }//methodAnnotation
            }//classAnnotation

        }


    }

}
