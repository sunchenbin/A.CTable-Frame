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

/**
 * Constants enumerating the HTTP headers.
 * 
 * <p>
 * All headers defined in RFC1945 (HTTP/1.0), RFC2616 (HTTP/1.1), and RFC2518 (WebDAV) are listed.
 * </p>
 *
 * @see "org.apache.http.HttpHeaders"
 * @see "org.springframework.http.HttpHeaders"
 * @since 1.0.8
 * @since httpcore 4.1
 */
public final class HttpHeaders{

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.1 */
    public static final String ACCEPT                      = "Accept";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.2 */
    public static final String ACCEPT_CHARSET              = "Accept-Charset";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.3 */
    public static final String ACCEPT_ENCODING             = "Accept-Encoding";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.4 */
    public static final String ACCEPT_LANGUAGE             = "Accept-Language";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.5 */
    public static final String ACCEPT_RANGES               = "Accept-Ranges";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.6 */
    public static final String AGE                         = "Age";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.1, RFC 2616 (HTTP/1.1) Section 14.7 */
    public static final String ALLOW                       = "Allow";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.2, RFC 2616 (HTTP/1.1) Section 14.8 */
    public static final String AUTHORIZATION               = "Authorization";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.9 */
    public static final String CACHE_CONTROL               = "Cache-Control";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.10 */
    public static final String CONNECTION                  = "Connection";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.3, RFC 2616 (HTTP/1.1) Section 14.11 */
    public static final String CONTENT_ENCODING            = "Content-Encoding";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.12 */
    public static final String CONTENT_LANGUAGE            = "Content-Language";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.4, RFC 2616 (HTTP/1.1) Section 14.13 */
    public static final String CONTENT_LENGTH              = "Content-Length";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.14 */
    public static final String CONTENT_LOCATION            = "Content-Location";

    /** The content disposition. */
    public static final String CONTENT_DISPOSITION         = "Content-Disposition";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.15 */
    public static final String CONTENT_MD5                 = "Content-MD5";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.16 */
    public static final String CONTENT_RANGE               = "Content-Range";

    /**
     * RFC 1945 (HTTP/1.0) Section 10.5, RFC 2616 (HTTP/1.1) Section 14.17 <code>{@value}</code><br>
     * 
     * <pre>
     * http://tools.ietf.org/html/rfc2616#section-7.2.1
     * 7.2.1 Type
     * 
     *    When an entity-body is included with a message, the data type of that body is determined via the header fields Content-Type and Content-Encoding. 
     *    These define a two-layer, ordered encoding model:
     * 
     *        entity-body := Content-Encoding( Content-Type( data ) )
     * 
     *    Content-Type specifies the media type of the underlying data.
     *    Content-Encoding may be used to indicate any additional content codings applied to the data, usually for the purpose of data compression, that are a property of the requested resource. There is no default encoding.
     * 
     *    Any HTTP/1.1 message containing an entity-body SHOULD include a Content-Type header field defining the media type of that body. 
     *    If and only if the media type is not given by a Content-Type field, the recipient MAY attempt to guess the media type via inspection of its
     *    content and/or the name extension(s) of the URI used to identify the resource. 
     *    If the media type remains unknown, the recipient SHOULD treat it as type "application/octet-stream".
     * </pre>
     * */
    public static final String CONTENT_TYPE                = "Content-Type";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.6, RFC 2616 (HTTP/1.1) Section 14.18 */
    public static final String DATE                        = "Date";

    /** <code>{@value}</code> RFC 2518 (WevDAV) Section 9.1 */
    public static final String DAV                         = "Dav";

    /** <code>{@value}</code> RFC 2518 (WevDAV) Section 9.2 */
    public static final String DEPTH                       = "Depth";

    /** <code>{@value}</code> RFC 2518 (WevDAV) Section 9.3 */
    public static final String DESTINATION                 = "Destination";

    /**
     * <code>{@value}</code>
     * 
     * <p>
     * RFC 2616 (HTTP/1.1) Section 14.19
     * </p>
     * 
     * <p>
     * Etag 是 http 1.1 规范引入的一个新的 http 实体头， Etag 在规范中仅仅只 etag 可以用来对同一个资源的其它实体头进行对比，没有做进一步的解释，<br>
     * 其实我们可以把 Etag 理解为一个服务器在某个资源上面做的一个记号，至于这个记号用来做什么要看服务器如何去解析它，因此我们在设计我们自己的应用的时候，可以借助 Etag 来实现客户端缓存和服务器之间的缓存协商控制。
     * </p>
     * 
     * <p>
     * 当浏览器第一次请求一个资源的时候，服务器在响应头里面加入 Etag 的标识， Etag 的值既是当前响应内容经过计算以后的值（ http 规范没有对 etag 值的计算方式做规定，可以是 md5 或者其它方式）当第二次浏览器发送请求的时候，浏览器便会用原先请求中
     * Etag 响应头的值作为 if-None-Match 请求头的值，这样服务器接受到此次请求以后，根据 if-None-Match 的取值和当前的内容进行对比，如果相同则返回一个 304 not modified 响应码，这样浏览器收到 304 后就会用客户端本地
     * cache 来完成对本次请求的响应。
     * </p>
     * 
     * @see "org.springframework.web.filter.ShallowEtagHeaderFilter"
     * */
    public static final String ETAG                        = "ETag";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.20 */
    public static final String EXPECT                      = "Expect";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.7, RFC 2616 (HTTP/1.1) Section 14.21 */
    public static final String EXPIRES                     = "Expires";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.8, RFC 2616 (HTTP/1.1) Section 14.22 */
    public static final String FROM                        = "From";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.23 */
    public static final String HOST                        = "Host";

    /** <code>{@value}</code> RFC 2518 (WevDAV) Section 9.4 */
    public static final String IF                          = "If";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.24 */
    public static final String IF_MATCH                    = "If-Match";

    /**
     * <code>{@value}</code>
     * 
     * <p>
     * RFC 1945 (HTTP/1.0) Section 10.9, RFC 2616 (HTTP/1.1) Section 14.25.
     * </p>
     * 
     * <p>
     * 当用户第一次浏览一个网站的时候，服务器会在响应头中增加 Last-modified 这个 http 响应头， Last-modified 的格式如：Last-modified: Fri, 16 Mar 201 04:00:25 GMT<br>
     * 当用户第二次再请求同样的 url 的时候，浏览器会将last-modified 的值附加到 if-modified-since 这个 http 请求头中，服务器端接收到请求后，首先 check 一下 if-modified-since 头信息中的时间是否与当前
     * url 对应的资源的最后修改时间一致，如果一致，则服务器返回 http 304 状态码，这样当浏览器收到 http 304 状态码了以后，就会利用本地缓存的内容来完成对本次用户操作的响应。
     * </p>
     * */
    public static final String IF_MODIFIED_SINCE           = "If-Modified-Since";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.26 */
    public static final String IF_NONE_MATCH               = "If-None-Match";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.27 */
    public static final String IF_RANGE                    = "If-Range";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.28 */
    public static final String IF_UNMODIFIED_SINCE         = "If-Unmodified-Since";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.10, RFC 2616 (HTTP/1.1) Section 14.29 */
    public static final String LAST_MODIFIED               = "Last-Modified";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.11, RFC 2616 (HTTP/1.1) Section 14.30 */
    public static final String LOCATION                    = "Location";

    /** <code>{@value}</code> RFC 2518 (WevDAV) Section 9.5 */
    public static final String LOCK_TOKEN                  = "Lock-Token";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.31 */
    public static final String MAX_FORWARDS                = "Max-Forwards";

    /** <code>{@value}</code> RFC 2518 (WevDAV) Section 9.6 */
    public static final String OVERWRITE                   = "Overwrite";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.12, RFC 2616 (HTTP/1.1) Section 14.32 */
    public static final String PRAGMA                      = "Pragma";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.33 */
    public static final String PROXY_AUTHENTICATE          = "Proxy-Authenticate";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.34 */
    public static final String PROXY_AUTHORIZATION         = "Proxy-Authorization";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.35 */
    public static final String RANGE                       = "Range";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.13, RFC 2616 (HTTP/1.1) Section 14.36 */
    public static final String REFERER                     = "Referer";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.37 */
    public static final String RETRY_AFTER                 = "Retry-After";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.14, RFC 2616 (HTTP/1.1) Section 14.38 */
    public static final String SERVER                      = "Server";

    /** <code>{@value}</code> RFC 2518 (WevDAV) Section 9.7 */
    public static final String STATUS_URI                  = "Status-URI";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.39 */
    public static final String TE                          = "TE";

    /** <code>{@value}</code> RFC 2518 (WevDAV) Section 9.8 */
    public static final String TIMEOUT                     = "Timeout";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.40 */
    public static final String TRAILER                     = "Trailer";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.41 */
    public static final String TRANSFER_ENCODING           = "Transfer-Encoding";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.42 */
    public static final String UPGRADE                     = "Upgrade";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.15, RFC 2616 (HTTP/1.1) Section 14.43 */
    public static final String USER_AGENT                  = "User-Agent";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.44 */
    public static final String VARY                        = "Vary";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.45 */
    public static final String VIA                         = "Via";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.46 */
    public static final String WARNING                     = "Warning";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.16, RFC 2616 (HTTP/1.1) Section 14.47 */
    public static final String WWW_AUTHENTICATE            = "WWW-Authenticate";

    // ****************************************header**************************************************************************

    /**
     * 1、Origin字段里只包含是谁发起的请求，并没有其他信息 (通常情况下是方案，主机和活动文档URL的端口)。<br>
     * 跟Referer不一样的是，Origin字段并没有包含涉及到用户隐私的URL路径和请求内容，这个尤其重要。 <br>
     * 2、Origin字段只存在于POST请求，而Referer则存在于所有类型的请求。<br>
     * <code>{@value}</code> .
     */
    public static final String ORIGIN                      = "origin";

    /**
     * <code>{@value}</code><br>
     * X-Forwarded-For:简称XFF头，它代表客户端，也就是HTTP的请求端真实的IP，只有在通过了HTTP 代理或者负载均衡服务器时才会添加该项。<br>
     * 它不是RFC中定义的标准请求头信息，在squid缓存代理服务器开发文档中可以找到该项的详细介绍。 <br>
     * 标准格式如下：<br>
     * X-Forwarded-For: client1, proxy1, proxy2.
     */
    public static final String X_FORWARDED_FOR             = "x-forwarded-for";

    /** <code>{@value}</code>. */
    public static final String PROXY_CLIENT_IP             = "Proxy-Client-IP";

    /** <code>{@value}</code> 这个应该是WebLogic前置HttpClusterServlet提供的属性，一般不需要自己处理，在WebLogic控制台中已经可以指定使用这个属性来覆盖. */
    public static final String WL_PROXY_CLIENT_IP          = "WL-Proxy-Client-IP";

    /** <code>{@value}</code>. */
    public static final String X_REQUESTED_WITH            = "X-Requested-With";

    /** <code>{@value}</code>. */
    public static final String X_REQUESTED_WITH_VALUE_AJAX = "XMLHttpRequest";

    /** Don't let anyone instantiate this class. */
    private HttpHeaders(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }
}
