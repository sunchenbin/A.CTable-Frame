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

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunchenbin.store.feilong.core.bean.ConvertUtil;
import com.sunchenbin.store.feilong.core.date.DateExtensionUtil;
import com.sunchenbin.store.feilong.core.date.DatePattern;
import com.sunchenbin.store.feilong.core.date.DateUtil;
import com.sunchenbin.store.feilong.core.tools.slf4j.Slf4jUtil;
import com.sunchenbin.store.feilong.core.util.Validator;

/**
 * {@link javax.servlet.http.HttpSession HttpSession} 工具类.
 * 
 * <h3>session什么时候被创建?</h3>
 * 
 * <blockquote>
 * <p>
 * 一个常见的错误是以为session在有客户端访问时就被创建,<br>
 * 然而事实是直到某server端程序(如Servlet)调用 {@link javax.servlet.http.HttpServletRequest#getSession()} 这样的语句时才会被创建。
 * </p>
 * </blockquote>
 * 
 * <h3>session何时被删除?</h3>
 * 
 * <blockquote>
 * <ol>
 * <li>程序调用 {@link javax.servlet.http.HttpSession#invalidate()}</li>
 * <li>距离上一次收到客户端发送的session id时间间隔超过了session的最大有效时间</li>
 * <li>服务器进程被停止</li>
 * </ol>
 * 再次注意关闭浏览器只会使存储在客户端浏览器内存中的session cookie失效，不会使服务器端的session对象失效
 * </blockquote>
 * 
 * @author feilong
 * @version 1.0.0 2010-7-6 下午02:10:33
 * @version 1.0.1 Jan 15, 2013 2:31:32 PM 进行精简
 * @version 1.0.7 2014-6-17 14:32 删除历史不用的方法
 * @since 1.0.0
 */
public final class SessionUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionUtil.class);

    /** Don't let anyone instantiate this class. */
    private SessionUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * Gets the session map for log(仅仅用于log和debug使用).
     * 
     * @param session
     *            the session
     * @return the session map for log,如果session is null,则返回 empty的{@link LinkedHashMap}
     * @see HttpSession#getId()
     * @see HttpSession#getCreationTime()
     * @see HttpSession#getLastAccessedTime()
     * @see HttpSession#getMaxInactiveInterval()
     * @see HttpSession#getAttributeNames()
     * @see HttpSession#isNew()
     */
    public static Map<String, Object> getSessionInfoMapForLog(HttpSession session){
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        if (Validator.isNullOrEmpty(session)){
            return Collections.emptyMap();
        }

        Date now = new Date();

        // 返回SESSION创建时JSP引擎为它设的惟一ID号 
        map.put("session.getId()", session.getId());

        //返回SESSION创建时间
        long creationTime = session.getCreationTime();
        Date creationTimeDate = new Date(creationTime);
        map.put(
                        "session.getCreationTime()",
                        Slf4jUtil.formatMessage(
                                        "[{}],format:[{}],intervalToNow:[{}]",
                                        creationTime,
                                        DateUtil.date2String(creationTimeDate, DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND),
                                        DateExtensionUtil.getIntervalForView(creationTimeDate, now)));

        //返回此SESSION里客户端最近一次请求时间 
        long lastAccessedTime = session.getLastAccessedTime();
        Date lastAccessedTimeDate = new Date(lastAccessedTime);
        map.put(
                        "session.getLastAccessedTime()",
                        Slf4jUtil.formatMessage(
                                        "[{}],format:[{}],intervalToNow:[{}]",
                                        lastAccessedTime,
                                        DateUtil.date2String(lastAccessedTimeDate, DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND),
                                        DateExtensionUtil.getIntervalForView(lastAccessedTimeDate, now)));

        //返回两次请求间隔多长时间此SESSION被取消(in seconds) 
        //Returns the maximum time interval, in seconds, 
        //that the servlet container will keep this session open between client accesses. 
        //After this interval, the servlet container will invalidate the session. 
        //The maximum time interval can be set with the setMaxInactiveInterval method.

        //A negative time indicates the session should never timeout.
        int maxInactiveInterval = session.getMaxInactiveInterval();
        map.put(
                        "session.getMaxInactiveInterval()",
                        maxInactiveInterval + "s,format:" + DateExtensionUtil.getIntervalForView(1000L * maxInactiveInterval));

        // 返回服务器创建的一个SESSION,客户端是否已经加入 
        map.put("session.isNew()", session.isNew());

        @SuppressWarnings({ "unchecked" })
        Enumeration<String> attributeNames = session.getAttributeNames();
        map.put("session.getAttributeNames()", ConvertUtil.toList(attributeNames));

        return map;
    }

    /**
     * 遍历显示session的attribute,将 name /attributeValue 存入到map.
     * 
     * @param session
     *            the session
     * @return the attribute map
     */
    public static Map<String, Serializable> getAttributeMap(HttpSession session){
        Map<String, Serializable> map = new HashMap<String, Serializable>();

        @SuppressWarnings("unchecked")
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()){
            String name = attributeNames.nextElement();
            Serializable attributeValue = (Serializable) session.getAttribute(name);
            map.put(name, attributeValue);
        }
        return map;
    }

    /**
     * 替换session,防止利用JSESSIONID 伪造url进行session hack.
     * 
     * @param request
     *            request
     * @return the http session
     * @see "org.springframework.security.util.SessionUtils#startNewSessionIfRequired(HttpServletRequest, boolean, SessionRegistry)"
     */
    public static HttpSession replaceSession(HttpServletRequest request){
        // 当session存在时返回该session，否则不会新建session，返回null
        HttpSession session = request.getSession(false);

        if (null == session){// 是null 新建一个
            return request.getSession();
        }

        // getSession()/getSession(true)：当session存在时返回该session，否则新建一个session并返回该对象
        session = request.getSession();
        Map<String, Serializable> map = getAttributeMap(session);
        LOGGER.debug("old session: {}", session.getId());

        session.invalidate();

        session = request.getSession();
        LOGGER.debug("new session: {}", session.getId());

        for (String key : map.keySet()){
            session.setAttribute(key, map.get(key));
        }
        return session;
    }
}