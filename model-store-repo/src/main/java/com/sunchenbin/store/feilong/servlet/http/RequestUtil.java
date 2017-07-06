/*
 * Copyright (C) 2008 feilong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sunchenbin.store.feilong.servlet.http;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunchenbin.store.feilong.core.bean.ConvertUtil;
import com.sunchenbin.store.feilong.core.io.UncheckedIOException;
import com.sunchenbin.store.feilong.core.lang.CharsetType;
import com.sunchenbin.store.feilong.core.net.ParamUtil;
import com.sunchenbin.store.feilong.core.net.URIComponents;
import com.sunchenbin.store.feilong.core.net.URIUtil;
import com.sunchenbin.store.feilong.core.tools.jsonlib.JsonUtil;
import com.sunchenbin.store.feilong.core.util.Validator;
import com.sunchenbin.store.feilong.servlet.http.builder.RequestLogBuilder;
import com.sunchenbin.store.feilong.servlet.http.builder.RequestLogSwitch;
import com.sunchenbin.store.feilong.servlet.http.entity.HttpHeaders;
import com.sunchenbin.store.feilong.servlet.http.entity.RequestAttributes;

/**
 * {@link javax.servlet.http.HttpServletRequest}工具类.
 * 
 * <h3>{@link HttpServletRequest#getRequestURI() getRequestURI()} && {@link HttpServletRequest#getRequestURL() getRequestURL()}:</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">返回值</th>
 * </tr>
 * <tr valign="top">
 * <td><span style="color:red">request.getRequestURI()</span></td>
 * <td>/feilong/requestdemo.jsp</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td><span style="color:red">request.getRequestURL()</span></td>
 * <td>http://localhost:8080/feilong/requestdemo.jsp</td>
 * </tr>
 * </table>
 * </blockquote>
 * 
 * 
 * <h3>关于从request中获得相关路径和url：</h3>
 * 
 * <blockquote>
 * 
 * <ol>
 * <li>getServletContext().getRealPath("/") 后包含当前系统的文件夹分隔符（windows系统是"\"，linux系统是"/"），而getPathInfo()以"/"开头。</li>
 * <li>getPathInfo()与getPathTranslated()在servlet的url-pattern被设置为/*或/aa/*之类的pattern时才有值，其他时候都返回null。</li>
 * <li>在servlet的url-pattern被设置为*.xx之类的pattern时，getServletPath()返回的是getRequestURI()去掉前面ContextPath的剩余部分。</li>
 * </ol>
 * 
 * <table border="1" cellspacing="0" cellpadding="4">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>request.getContextPath()</td>
 * <td>request.getContextPath()</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>request.getPathInfo()</td>
 * <td>Returns any extra path information associated with the URL the client sent when it made this request. <br>
 * Servlet访问路径之后，QueryString之前的中间部分</td>
 * </tr>
 * <tr valign="top">
 * <td>request.getServletPath()</td>
 * <td>web.xml中定义的Servlet访问路径</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>request.getPathTranslated()</td>
 * <td>等于getServletContext().getRealPath("/") + getPathInfo()</td>
 * </tr>
 * <tr valign="top">
 * <td>request.getRequestURI()</td>
 * <td>等于getContextPath() + getServletPath() + getPathInfo()</td>
 * </tr>
 * <tr valign="top">
 * <td>request.getRequestURL()</td>
 * <td>等于getScheme() + "://" + getServerName() + ":" + getServerPort() + getRequestURI()</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>request.getQueryString()</td>
 * <td>&之后GET方法的参数部分<br>
 * Returns the query string that is contained in the request URL after the path. <br>
 * This method returns null if the URL does not have a query string. <br>
 * Same as the value of the CGI variable QUERY_STRING.</td>
 * </tr>
 * </table>
 * </blockquote>
 * 
 * @author feilong
 * @version 1.0 2011-11-3 下午02:24:55
 * @version 1.0.4 2014-3-27 14:38
 * @see RequestAttributes
 * @see RequestLogSwitch
 * @since 1.0.0
 */
public final class RequestUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestUtil.class);

    /** Don't let anyone instantiate this class. */
    private RequestUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    // ******************************是否包含******************************************
    /**
     * 请求路径中是否包含某个参数名称 (注意:这是判断是否包含参数,而不是判断参数值是否为空).
     *
     * @param request
     *            请求
     * @param param
     *            参数名称
     * @return 包含该参数返回true,不包含返回false
     * @since 1.4.0
     */
    public static boolean containsParam(HttpServletRequest request,String param){
        if (Validator.isNullOrEmpty(param)){
            throw new NullPointerException("param can't be null/empty!");
        }
        @SuppressWarnings("unchecked")
        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()){
            String parameterName = parameterNames.nextElement();

            if (param.equals(parameterName)){
                return true;
            }
        }

        //感觉要比下面好些
        //Map<String, ?> map = getParameterMap(request);
        //return map.containsKey(param);
        return false;
    }

    /**
     * 获得参数 map(结果转成了 TreeMap).
     * 
     * <p>
     * 此方式会将tomcat返回的map 转成TreeMap 处理返回，便于log;也可以<span style="color:red">对这个map进行操作</span>
     * </p>
     * 
     * <h3>tomcat getParameterMap() <span style="color:red">locked</span>(只能读):</h3>
     * 
     * <blockquote>
     * 注意:tomcat 默认实现，返回的是 {@link "org.apache.catalina.util#ParameterMap<K, V>"},tomcat返回之前，会将此map的状态设置为locked,<br>
     * <p>
     * 不像普通的map数据一样可以修改。这是因为服务器为了实现一定的安全规范，所作的限制，WebLogic，Tomcat，Resin，JBoss等服务器均实现了此规范。
     * </p>
     * 此时，不能做以下的map操作：
     * 
     * <ul>
     * <li>{@link Map#clear()}</li>
     * <li>{@link Map#put(Object, Object)}</li>
     * <li>{@link Map#putAll(Map)}</li>
     * <li>{@link Map#remove(Object)}</li>
     * </ul>
     * 
     * </blockquote>
     * 
     * @param request
     *            the request
     * @return the parameter map
     * @see "org.apache.catalina.connector.Request#getParameterMap()"
     */
    public static Map<String, String[]> getParameterMap(HttpServletRequest request){
        @SuppressWarnings("unchecked")
        // http://localhost:8888/s.htm?keyword&a=
        // 这种链接  map key 会是 keyword,a 值都是空
        // servlet 3.0 此处返回类型的是 泛型数组 Map<String, String[]>
        Map<String, String[]> map = request.getParameterMap();

        // 转成TreeMap ,这样log出现的key 是有顺序的
        return new TreeMap<String, String[]>(map);
    }

    /**
     * 获得参数 and 单值map.
     * 
     * <p>
     * 由于 j2ee {@link ServletRequest#getParameterMap()}返回的map 值是数组形式,对于一些确认是单值的请求时(比如支付宝notify/return request)，不便于后续处理
     * </p>
     * 
     * @param request
     *            the request
     * @return the parameter single value map
     * @see #getParameterMap(HttpServletRequest)
     * @see ParamUtil#toSingleValueMap(Map)
     * @since 1.2.0
     */
    public static Map<String, String> getParameterSingleValueMap(HttpServletRequest request){
        Map<String, String[]> arrayValueMap = getParameterMap(request);
        return ParamUtil.toSingleValueMap(arrayValueMap);
    }

    /**
     * 将request 相关属性，数据转成json格式 以便log显示(目前仅作log使用).
     * 
     * <p>
     * 使用默认的 {@link RequestLogSwitch}
     * </p>
     * 
     * @param request
     *            the request
     * @return the request string for log
     * @see RequestLogSwitch
     */
    public static Map<String, Object> getRequestInfoMapForLog(HttpServletRequest request){
        return getRequestInfoMapForLog(request, RequestLogSwitch.NORMAL);
    }

    /**
     * 将request 相关属性，数据转成json格式 以便log显示(目前仅作log使用).
     * 
     * @param request
     *            the request
     * @param requestLogSwitch
     *            the request log switch
     * @return the request string for log
     * @see RequestLogBuilder#RequestLogBuilder(HttpServletRequest, RequestLogSwitch)
     */
    public static Map<String, Object> getRequestInfoMapForLog(HttpServletRequest request,RequestLogSwitch requestLogSwitch){
        return new RequestLogBuilder(request, requestLogSwitch).build();
    }

    // ******************************* url参数相关 getAttribute*****************************************************.
    // [start] url参数相关

    /**
     * 获得 attribute.
     *
     * @param <T>
     *            the generic type
     * @param request
     *            the request
     * @param name
     *            the name
     * @return the attribute
     * @since 1.3.0
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAttribute(HttpServletRequest request,String name){
        return (T) request.getAttribute(name);
    }

    /**
     * 取到request里面的属性值,<span style="color:green">自动转换类型</span>.
     *
     * @param <T>
     *            the generic type
     * @param request
     *            请求
     * @param name
     *            属性名称
     * @param klass
     *            the klass
     * @return the attribute
     * @see com.sunchenbin.store.feilong.core.bean.ConvertUtil#convert(Object, Class)
     * @since 1.3.0
     */
    public static <T> T getAttribute(HttpServletRequest request,String name,Class<T> klass){
        Object value = getAttribute(request, name);
        return ConvertUtil.convert(value, klass);
    }

    /**
     * 获得请求的?部分前面的地址.
     * <p>
     * <span style="color:red">自动识别 request 是否 forword</span>,如果是forword过来的,那么 取 {@link RequestAttributes#FORWARD_REQUEST_URI}变量
     * </p>
     * 
     * <pre>
     * 如:http://localhost:8080/feilong/requestdemo.jsp?id=2
     * 返回:http://localhost:8080/feilong/requestdemo.jsp
     * </pre>
     * 
     * 注:
     * 
     * <blockquote>
     * <table border="1" cellspacing="0" cellpadding="4">
     * <tr style="background-color:#ccccff">
     * <th align="left">字段</th>
     * <th align="left">返回值</th>
     * </tr>
     * <tr valign="top">
     * <td><span style="color:red">request.getRequestURI()</span></td>
     * <td>/feilong/requestdemo.jsp</td>
     * </tr>
     * <tr valign="top" style="background-color:#eeeeff">
     * <td><span style="color:red">request.getRequestURL()</span></td>
     * <td>http://localhost:8080/feilong/requestdemo.jsp</td>
     * </tr>
     * </table>
     * </blockquote>
     * 
     * @param request
     *            the request
     * @return 获得请求的?部分前面的地址
     */
    public static String getRequestURL(HttpServletRequest request){
        String forwardRequestUri = (String) request.getAttribute(RequestAttributes.FORWARD_REQUEST_URI);
        return Validator.isNotNullOrEmpty(forwardRequestUri) ? forwardRequestUri : request.getRequestURL().toString();
    }

    /**
     * Return the servlet path for the given request, detecting an include request URL if called within a RequestDispatcher include.
     * 
     * @param request
     *            current HTTP request
     * @return the servlet path
     */
    public static String getOriginatingServletPath(HttpServletRequest request){
        String servletPath = (String) request.getAttribute(RequestAttributes.FORWARD_SERVLET_PATH);
        return Validator.isNotNullOrEmpty(servletPath) ? servletPath : request.getServletPath();
    }

    /**
     * 获得请求的全地址.
     * 
     * <ul>
     * <li>如果request不含queryString,直接返回 requestURL(比如post请求)</li>
     * <li>如果request含queryString,直接返回 requestURL+编码后的queryString</li>
     * </ul>
     * 
     * @param request
     *            the request
     * @param charsetType
     *            编码集 {@link CharsetType}
     * @return 如:http://localhost:8080/feilong/requestdemo.jsp?id=2
     */
    public static String getRequestFullURL(HttpServletRequest request,String charsetType){
        String requestURL = getRequestURL(request);

        String queryString = request.getQueryString();
        if (Validator.isNullOrEmpty(queryString)){
            return requestURL;
        }
        return requestURL + URIComponents.QUESTIONMARK + URIUtil.decodeISO88591String(queryString, charsetType);
    }

    /**
     * scheme+port+getContextPath.
     * 
     * <p>
     * 区分 http 和https.
     * <p>
     * 
     * @param request
     *            the request
     * @return 如:http://localhost:8080/feilong/
     * @see org.apache.catalina.connector.Request#getRequestURL()
     */
    public static String getServerRootWithContextPath(HttpServletRequest request){

        StringBuilder sb = new StringBuilder();
        String scheme = request.getScheme();

        sb.append(scheme);
        sb.append("://");
        sb.append(request.getServerName());

        int port = request.getServerPort();
        if (port < 0){
            port = 80; // Work around java.net.URL bug
        }

        if ((scheme.equals(URIComponents.SCHEME_HTTP) && (port != 80)) || (scheme.equals(URIComponents.SCHEME_HTTPS) && (port != 443))){
            sb.append(':');
            sb.append(port);
        }

        sb.append(request.getContextPath());
        return sb.toString();
    }

    // [end]

    /**
     * 用于将请求转发到 {@link RequestDispatcher} 对象封装的资源，Servlet程序在调用该方法进行转发之前可以对请求进行前期预处理。
     * <p>
     * Forwards a request from a servlet to another resource (servlet, JSP file, or HTML file) on the server.<br>
     * This method allows one servlet to do preliminary processing of a request and another resource to generate the response.
     * </p>
     * <p>
     * For a <code>RequestDispatcher</code> obtained via <code>getRequestDispatcher()</code>, the <code>ServletRequest</code> object has its
     * path elements and parameters adjusted to match the path of the target resource.
     * </p>
     * 
     * <p>
     * <code>forward</code> should be called before the response has been committed to the client (before response body output has been
     * flushed). If the response already has been committed, this method throws an <code>IllegalStateException</code>. Uncommitted output in
     * the response buffer is automatically cleared before the forward.
     * </p>
     * 
     * <p>
     * The request and response parameters must be either the same objects as were passed to the calling servlet's service method or be
     * subclasses of the {@link ServletRequestWrapper} or {@link ServletResponseWrapper} classes that wrap them.
     * </p>
     * 
     * @param path
     *            the path
     * @param request
     *            a {@link ServletRequest} object
     *            that represents the request the client
     *            makes of the servlet
     * @param response
     *            a {@link ServletResponse} object
     *            that represents the response the servlet
     *            returns to the client
     * @since 1.2.2
     */
    public static void forward(String path,HttpServletRequest request,HttpServletResponse response){
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
        try{
            requestDispatcher.forward(request, response);
        }catch (ServletException e){
            LOGGER.error("", e);
            throw new RuntimeException(e);
        }catch (IOException e){
            LOGGER.error("", e);
            throw new UncheckedIOException(e);
        }
    }

    /**
     * 用于将 {@link RequestDispatcher} 对象封装的资源内容作为当前响应内容的一部分包含进来，从而实现可编程服务器的服务器端包含功能。
     * 
     * <p>
     * Includes the content of a resource (servlet, JSP page,HTML file) in the response. <br>
     * In essence, this method enables programmatic server-side includes.
     * </p>
     * 
     * <p>
     * 注：被包含的Servlet程序不能改变响应信息的状态码和响应头，如果里面包含这样的语句将被忽略。<br>
     * The {@link ServletResponse} object has its path elements and parameters remain unchanged from the caller's. <br>
     * The included servlet cannot change the response status code or set headers; any attempt to make a change is ignored.
     * </p>
     * 
     * <p>
     * The request and response parameters must be either the same objects as were passed to the calling servlet's service method or be
     * subclasses of the {@link ServletRequestWrapper} or {@link ServletResponseWrapper} classes that wrap them.
     * </p>
     * 
     * @param path
     *            the path
     * @param request
     *            a {@link ServletRequest} object
     *            that contains the client's request
     * @param response
     *            a {@link ServletResponse} object
     *            that contains the servlet's response
     * @see javax.servlet.RequestDispatcher#include(ServletRequest, ServletResponse)
     * @see "org.springframework.web.servlet.ResourceServlet"
     * @since 1.2.2
     */
    public static void include(String path,HttpServletRequest request,HttpServletResponse response){
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
        try{
            requestDispatcher.include(request, response);
        }catch (ServletException e){
            LOGGER.error("", e);
            throw new RuntimeException(e);
        }catch (IOException e){
            LOGGER.error("", e);
            throw new UncheckedIOException(e);
        }
    }

    // ****************************LocalAddr*****************************************

    /**
     * 获得项目本地ip地址.
     * 
     * @param request
     *            the request
     * @return Returns the Internet Protocol (IP) address of the interface on which the request was received.
     */
    public static String getLocalAddr(HttpServletRequest request){
        return request.getLocalAddr();
    }

    // **************************Header*******************************************

    /**
     * 获得客户端ip地址.
     * 
     * @param request
     *            the request
     * @return 获得客户端ip地址
     */
    public static String getClientIp(HttpServletRequest request){
        // WL-Proxy-Client-IP=215.4.1.29
        // Proxy-Client-IP=215.4.1.29
        // X-Forwarded-For=215.4.1.29
        // WL-Proxy-Client-Keysize=
        // WL-Proxy-Client-Secretkeysize=
        // X-WebLogic-Request-ClusterInfo=true
        // X-WebLogic-KeepAliveSecs=30
        // X-WebLogic-Force-JVMID=-527489098
        // WL-Proxy-SSL=false
        String unknown = "unknown";

        Map<String, String> map = new TreeMap<String, String>();

        //TODO X-real-ip 程哥说如果主站使用cdn的话，以前的方法获取到的不正确

        // 是否使用反向代理
        String ipAddress = request.getHeader(HttpHeaders.X_FORWARDED_FOR);
        map.put("1.header_xForwardedFor", ipAddress);
        if (Validator.isNullOrEmpty(ipAddress) || unknown.equalsIgnoreCase(ipAddress)){
            ipAddress = request.getHeader(HttpHeaders.PROXY_CLIENT_IP);
            map.put("2.header_proxyClientIP", ipAddress);
        }
        if (Validator.isNullOrEmpty(ipAddress) || unknown.equalsIgnoreCase(ipAddress)){
            ipAddress = request.getHeader(HttpHeaders.WL_PROXY_CLIENT_IP);
            map.put("3.header_wLProxyClientIP", ipAddress);
        }
        if (Validator.isNullOrEmpty(ipAddress) || unknown.equalsIgnoreCase(ipAddress)){
            ipAddress = request.getRemoteAddr();
            map.put("4.request.getRemoteAddr()", ipAddress);
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15){ // "***.***.***.***".length() = 15
            map.put("5.all", ipAddress);
            if (ipAddress.indexOf(",") > 0){
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                map.put("6.ipAddress", ipAddress);
            }
        }
        if (LOGGER.isDebugEnabled()){
            LOGGER.debug(JsonUtil.format(map));
        }
        return ipAddress;
    }

    // *****************************Header区域**************************************

    /**
     * User Agent中文名为用户代理，简称 UA.
     * 
     * <p>
     * 它是一个特殊字符串头，使得服务器能够识别客户使用的操作系统及版本、CPU 类型、浏览器及版本、浏览器渲染引擎、浏览器语言、浏览器插件等。
     * </p>
     * 
     * @param request
     *            the request
     * @return the user agent
     */
    public static String getHeaderUserAgent(HttpServletRequest request){
        return request.getHeader(HttpHeaders.USER_AGENT);
    }

    /**
     * 获得上个请求的URL.
     * 
     * <pre>
     * 请用于常规请求,必须走http协议才有值,javascript跳转无效
     * 
     * 以下情况请慎用:
     * 也就是说要通过&lt;a href=&quot;url&quot;&gt;sss&lt;/a&gt;标记才能获得那个值
     * 而通过改变location或是&lt;a href=&quot;javascript:location='url'&quot;&gt;sss&lt;/a&gt;都是得不到那个值得
     * 
     * referer是浏览器在用户提交请求当前页面中的一个链接时,将当前页面的URL放在头域中提交给服务端的,如当前页面为a.html,
     * 它里面有一个b.html的链接,当用户要访问b.html时浏览器就会把a.html作为referer发给服务端.
     * 
     * </pre>
     * 
     * @param request
     *            the request
     * @return 上个请求的URL
     */
    public static String getHeaderReferer(HttpServletRequest request){
        return request.getHeader(HttpHeaders.REFERER);
    }

    /**
     * 1、Origin字段里只包含是谁发起的请求，并没有其他信息 (通常情况下是方案，主机和活动文档URL的端口). <br>
     * 跟Referer不一样的是，Origin字段并没有包含涉及到用户隐私的URL路径和请求内容，这个尤其重要. <br>
     * 2、Origin字段只存在于POST请求，而Referer则存在于所有类型的请求.
     * 
     * @param request
     *            the request
     * @return the header origin
     */
    public static String getHeaderOrigin(HttpServletRequest request){
        return request.getHeader(HttpHeaders.ORIGIN);
    }

    /**
     * 判断一个请求是否是ajax请求.
     * 
     * <p>
     * 注:x-requested-with这个头是某些JS类库给加上去的，直接写AJAX是没有这个头的,<br>
     * jquery/ext 确定添加,暂时可以使用这个来判断
     * </p>
     * 
     * <p>
     * The X-Requested-With is a non-standard HTTP header which is mainly used to identify Ajax requests. <br>
     * Most JavaScript frameworks send this header with value of XMLHttpRequest.
     * </p>
     * 
     * @param request
     *            the request
     * @return 如果是ajax 请求 返回true
     * @see "http://en.wikipedia.org/wiki/X-Requested-With#Requested-With"
     */
    public static boolean isAjaxRequest(HttpServletRequest request){
        String header = request.getHeader(HttpHeaders.X_REQUESTED_WITH);
        return Validator.isNotNullOrEmpty(header) && header.equalsIgnoreCase(HttpHeaders.X_REQUESTED_WITH_VALUE_AJAX);
    }

    /**
     * 判断一个请求 ,不是ajax 请求.
     * 
     * @param request
     *            the request
     * @return 如果不是ajax 返回true
     */
    public static boolean isNotAjaxRequest(HttpServletRequest request){
        return !isAjaxRequest(request);
    }

    // *********************************************************************

    /**
     * 遍历显示request的attribute,将 name /attributeValue 存入到map.
     * 
     * <p style="color:red">
     * 目前如果直接 转json 如果属性有级联关系,会报错,
     * </p>
     * 
     * @param request
     *            the request
     * @return the attribute map
     */
    public static Map<String, Object> getAttributeMap(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()){
            String name = attributeNames.nextElement();
            Object attributeValue = request.getAttribute(name);
            map.put(name, attributeValue);
        }
        return map;
    }

    // *********************************获取值********************************

    /**
     * 获得request中的请求参数值.
     * 
     * @param request
     *            当前请求
     * @param paramName
     *            参数名称
     * @return 获得request中的请求参数值
     */
    public static String getParameter(HttpServletRequest request,String paramName){
        return request.getParameter(paramName);
    }
}
