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
package com.sunchenbin.store.feilong.servlet.http.entity;

import java.io.Serializable;

import com.sunchenbin.store.feilong.core.date.TimeInterval;

/**
 * cookie实体.
 * 
 * <h3>关于 name && value字符说明:</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>name</td>
 * <td>名字和值都不能包含空白字符以及下列字符： @ : ;? , " / [ ] ( ) = 这些符号.</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>value</td>
 * <td>名字和值都不能包含空白字符以及下列字符： @ : ;? , " / [ ] ( ) = 这些符号.</td>
 * </tr>
 * </table>
 * </blockquote>
 * 
 * <h3>关于 maxAge</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>正数(positive value)</td>
 * <td>则表示该cookie会在max-age秒之后自动失效。<br>
 * 浏览器会将max-age为正数的cookie持久化，即写到对应的cookie文件中。无论客户关闭了浏览器还是电脑，只要还在max-age秒之前，登录网站时该cookie仍然有效 。</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>负数(negative value)</td>
 * <td>表示不保存不持久化,当浏览器退出就会删除</td>
 * </tr>
 * <tr valign="top">
 * <td>0(zero value)</td>
 * <td>表示删除</td>
 * </tr>
 * </table>
 * 默认和servlet 保持一致,为-1,表示不保存不持久化, 浏览器退出就删除,如果需要设置有效期,可以调用 {@link TimeInterval}类
 * </blockquote>
 * 
 * 
 * @author feilong
 * @version 1.0.0 2010-6-24 上午08:07:11
 * @since 1.0.0
 */
public class CookieEntity implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5580364261848277853L;

    //	   The value of the cookie itself.
    /** name名称,名字和值都不能包含空白字符以及下列字符： @ : ;? , " / [ ] ( ) = 这些符号. */
    private String            name;                                    //NAME= ... "$Name" style is reserved

    /** value,名字和值都不能包含空白字符以及下列字符： @ : ;? , " / [ ] ( ) = 这些符号. */
    private String            value;

    /**
     * 设置存活时间,单位秒.
     * 
     * <blockquote>
     * <table border="1" cellspacing="0" cellpadding="4">
     * <tr style="background-color:#ccccff">
     * <th align="left">字段</th>
     * <th align="left">说明</th>
     * </tr>
     * <tr valign="top">
     * <td>正数(positive value)</td>
     * <td>则表示该cookie会在max-age秒之后自动失效。<br>
     * 浏览器会将max-age为正数的cookie持久化，即写到对应的cookie文件中。无论客户关闭了浏览器还是电脑，只要还在max-age秒之前，登录网站时该cookie仍然有效 。</td>
     * </tr>
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>负数(negative value)</td>
     * <td>表示不保存不持久化,当浏览器退出就会删除</td>
     * </tr>
     * <tr valign="top">
     * <td>0(zero value)</td>
     * <td>表示删除</td>
     * </tr>
     * </table>
     * </blockquote>
     * 
     * 默认和servlet 保持一致,为-1,表示不保存不持久化, 浏览器退出就删除,如果需要设置有效期,可以调用 {@link TimeInterval}类
     * 
     * <p>
     * ;Max-Age=VALUE
     * </p>
     * 
     * @see javax.servlet.http.Cookie#setMaxAge(int)
     */
    private int               maxAge           = -1;

    //*******************************************************************************************

    /**
     * ;Comment=VALUE ... describes cookie's use ;Discard ... implied by maxAge < 0
     */
    private String            comment;

    /**
     * ;Domain=VALUE ... domain that sees cookie
     */
    private String            domain;

    /**
     * ;Path=VALUE ... URLs that see the cookie
     */
    private String            path;

    /**
     * ;Secure ... e.g. use SSL
     */
    private boolean           secure;

    /**
     * This class supports both the Version 0 (by Netscape) and Version 1 (by RFC 2109) cookie specifications.
     * 
     * By default, cookies are created using Version 0 to ensure the best interoperability.
     * 
     * <p>
     * ;Version=1 ... means RFC 2109++ style
     * </p>
     */
    private int               version          = 0;

    /**
     * Not in cookie specs, but supported by browsers.
     */
    private boolean           httpOnly;

    /**
     * The Constructor.
     */
    public CookieEntity(){
        super();
    }

    /**
     * The Constructor.
     * 
     * @param name
     *            name名称,名字和值都不能包含空白字符以及下列字符： @ : ;? , " / [ ] ( ) = 这些符号.
     * @param value
     *            value,名字和值都不能包含空白字符以及下列字符： @ : ;? , " / [ ] ( ) = 这些符号.
     */
    public CookieEntity(String name, String value){
        this.name = name;
        this.value = value;
    }

    /**
     * The Constructor.
     *
     * @param name
     *            name名称,名字和值都不能包含空白字符以及下列字符： @ : ;? , " / [ ] ( ) = 这些符号.
     * @param value
     *            value,名字和值都不能包含空白字符以及下列字符： @ : ;? , " / [ ] ( ) = 这些符号.
     * @param maxAge
     *            设置存活时间,单位秒.
     *            <blockquote>
     *            <table border="1" cellspacing="0" cellpadding="4">
     *            <tr style="background-color:#ccccff">
     *            <th align="left">字段</th>
     *            <th align="left">说明</th>
     *            </tr>
     *            <tr valign="top">
     *            <td>正数(positive value)</td>
     *            <td>则表示该cookie会在max-age秒之后自动失效。<br>
     *            浏览器会将max-age为正数的cookie持久化，即写到对应的cookie文件中。无论客户关闭了浏览器还是电脑，只要还在max-age秒之前，登录网站时该cookie仍然有效 。</td>
     *            </tr>
     *            <tr valign="top" style="background-color:#eeeeff">
     *            <td>负数(negative value)</td>
     *            <td>表示不保存不持久化,当浏览器退出就会删除</td>
     *            </tr>
     *            <tr valign="top">
     *            <td>0(zero value)</td>
     *            <td>表示删除</td>
     *            </tr>
     *            </table>
     *            </blockquote>
     * 
     *            默认和servlet 保持一致,为-1,表示不保存不持久化, 浏览器退出就删除,如果需要设置有效期,可以调用 {@link TimeInterval}类
     *            <p>
     *            ;Max-Age=VALUE
     *            </p>
     */
    public CookieEntity(String name, String value, int maxAge){
        this.name = name;
        this.value = value;
        this.maxAge = maxAge;
    }

    /**
     * Gets the name名称,名字和值都不能包含空白字符以及下列字符： @ : ;? , " / [ ] ( ) = 这些符号.
     * 
     * @return the name名称,名字和值都不能包含空白字符以及下列字符： @ : ;? , " / [ ] ( ) = 这些符号
     */
    public String getName(){
        return name;
    }

    /**
     * Sets the name名称,名字和值都不能包含空白字符以及下列字符： @ : ;? , " / [ ] ( ) = 这些符号.
     * 
     * @param name
     *            the new name名称,名字和值都不能包含空白字符以及下列字符： @ : ;? , " / [ ] ( ) = 这些符号
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gets the value,名字和值都不能包含空白字符以及下列字符： @ : ;? , " / [ ] ( ) = 这些符号.
     * 
     * @return the value,名字和值都不能包含空白字符以及下列字符： @ : ;? , " / [ ] ( ) = 这些符号
     */
    public String getValue(){
        return value;
    }

    /**
     * Sets the value,名字和值都不能包含空白字符以及下列字符： @ : ;? , " / [ ] ( ) = 这些符号.
     * 
     * @param value
     *            the new value,名字和值都不能包含空白字符以及下列字符： @ : ;? , " / [ ] ( ) = 这些符号
     */
    public void setValue(String value){
        this.value = value;
    }

    /**
     * 获得 设置存活时间,单位秒.
     * <blockquote>
     * <table border="1" cellspacing="0" cellpadding="4">
     * <tr style="background-color:#ccccff">
     * <th align="left">字段</th>
     * <th align="left">说明</th>
     * </tr>
     * <tr valign="top">
     * <td>正数(positive value)</td>
     * <td>则表示该cookie会在max-age秒之后自动失效。<br>
     * 浏览器会将max-age为正数的cookie持久化，即写到对应的cookie文件中。无论客户关闭了浏览器还是电脑，只要还在max-age秒之前，登录网站时该cookie仍然有效 。</td>
     * </tr>
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>负数(negative value)</td>
     * <td>表示不保存不持久化,当浏览器退出就会删除</td>
     * </tr>
     * <tr valign="top">
     * <td>0(zero value)</td>
     * <td>表示删除</td>
     * </tr>
     * </table>
     * </blockquote>
     * 
     * @return the maxAge
     */
    public int getMaxAge(){
        return maxAge;
    }

    /**
     * 设置 设置存活时间,单位秒.
     * <blockquote>
     * <table border="1" cellspacing="0" cellpadding="4">
     * <tr style="background-color:#ccccff">
     * <th align="left">字段</th>
     * <th align="left">说明</th>
     * </tr>
     * <tr valign="top">
     * <td>正数(positive value)</td>
     * <td>则表示该cookie会在max-age秒之后自动失效。<br>
     * 浏览器会将max-age为正数的cookie持久化，即写到对应的cookie文件中。无论客户关闭了浏览器还是电脑，只要还在max-age秒之前，登录网站时该cookie仍然有效 。</td>
     * </tr>
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>负数(negative value)</td>
     * <td>表示不保存不持久化,当浏览器退出就会删除</td>
     * </tr>
     * <tr valign="top">
     * <td>0(zero value)</td>
     * <td>表示删除</td>
     * </tr>
     * </table>
     * </blockquote>
     * 
     * @param maxAge
     *            the maxAge to set
     */
    public void setMaxAge(int maxAge){
        this.maxAge = maxAge;
    }

    /**
     * 获得 ;Comment=VALUE .
     *
     * @return the comment
     */
    public String getComment(){
        return comment;
    }

    /**
     * 设置 ;Comment=VALUE .
     *
     * @param comment
     *            the comment to set
     */
    public void setComment(String comment){
        this.comment = comment;
    }

    /**
     * 获得 ;Domain=VALUE .
     *
     * @return the domain
     */
    public String getDomain(){
        return domain;
    }

    /**
     * 设置 ;Domain=VALUE .
     *
     * @param domain
     *            the domain to set
     */
    public void setDomain(String domain){
        this.domain = domain;
    }

    /**
     * 获得 ;Path=VALUE .
     *
     * @return the path
     */
    public String getPath(){
        return path;
    }

    /**
     * 设置 ;Path=VALUE .
     *
     * @param path
     *            the path to set
     */
    public void setPath(String path){
        this.path = path;
    }

    /**
     * 获得 ;Secure .
     *
     * @return the secure
     */
    public boolean getSecure(){
        return secure;
    }

    /**
     * 设置 ;Secure .
     *
     * @param secure
     *            the secure to set
     */
    public void setSecure(boolean secure){
        this.secure = secure;
    }

    /**
     * This class supports both the Version 0 (by Netscape) and Version 1 (by RFC 2109) cookie specifications.
     * 
     * By default, cookies are created using Version 0 to ensure the best interoperability.
     * 
     * <p>
     * ;Version=1 ... means RFC 2109++ style
     * </p>
     * 
     * @return the version
     */
    public int getVersion(){
        return version;
    }

    /**
     * This class supports both the Version 0 (by Netscape) and Version 1 (by RFC 2109) cookie specifications.
     * 
     * By default, cookies are created using Version 0 to ensure the best interoperability.
     * 
     * <p>
     * ;Version=1 ... means RFC 2109++ style
     * </p>
     * 
     * @param version
     *            the version to set
     */
    public void setVersion(int version){
        this.version = version;
    }

    /**
     * 获得 not in cookie specs, but supported by browsers.
     *
     * @return the httpOnly
     */
    public boolean getHttpOnly(){
        return httpOnly;
    }

    /**
     * 设置 not in cookie specs, but supported by browsers.
     *
     * @param httpOnly
     *            the httpOnly to set
     */
    public void setHttpOnly(boolean httpOnly){
        this.httpOnly = httpOnly;
    }

}