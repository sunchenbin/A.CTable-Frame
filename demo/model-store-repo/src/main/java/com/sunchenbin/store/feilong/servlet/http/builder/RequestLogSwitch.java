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
package com.sunchenbin.store.feilong.servlet.http.builder;

import java.io.Serializable;

/**
 * request log显示开关.
 * 
 * <p>
 * These classes are intended to be used as <code>Singletons</code>. There is no need to instantiate a new style each time. A program will
 * generally use one of the predefined constants on this class.
 * </p>
 * 
 * <h3>默认显示:</h3>
 * 
 * <blockquote>
 * <ul>
 * <li>{@link #showFullURL} 请求路径</li>
 * <li>{@link #showMethod} 提交方法</li>
 * <li>{@link #showParams} 参数</li>
 * </ul>
 * </blockquote>
 * 
 * <p>
 * 借鉴了 {@link org.apache.commons.lang3.builder.ToStringStyle} 的思想
 * </p>
 * 
 * 
 * <h3>readResolve()方法:</h3>
 * 
 * <blockquote>
 * <p>
 * 一般来说, 一个类实现了 Serializable接口, 我们就可以把它往内存地写再从内存里读出而"组装"成一个跟原来一模一样的对象. <br>
 * 不过当序列化遇到单例时,这里边就有了个问题: 从内存读出而组装的对象破坏了单例的规则. <br>
 * 单例是要求一个JVM中只有一个类对象的,而现在通过反序列化,一个新的对象克隆了出来.
 * </p>
 * </blockquote>
 *
 * @author feilong
 * @version 1.0.5 Apr 23, 2014 11:33:28 AM
 * @version 1.4.0 2015-8-4 23:33 重构整个类
 * @see org.apache.commons.lang3.builder.ToStringStyle
 * @since 1.4.0
 */
public abstract class RequestLogSwitch implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long            serialVersionUID                     = 288232184048495608L;

    /**
     * 正常的的RequestLogSwitch.
     * 
     * <h3>以下值会为true:</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>{@link #showFullURL} 请求路径</li>
     * <li>{@link #showMethod} 提交方法</li>
     * <li>{@link #showParams} 参数</li>
     * </ul>
     * </blockquote>
     *
     * @since 1.4.0
     */
    public static final RequestLogSwitch NORMAL                               = new NormalRequestLogSwitch();

    /**
     * 带身份信息的RequestLogSwitch.
     * 
     * <h3>以下值会为true:</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>{@link #showFullURL} 请求路径</li>
     * <li>{@link #showMethod} 提交方法</li>
     * <li>{@link #showParams} 参数</li>
     * <li>{@link #showIdentity} 身份信息(ip及UA)</li>
     * </ul>
     * </blockquote>
     *
     * @since 1.4.0
     */
    public static final RequestLogSwitch NORMAL_WITH_IDENTITY                 = new NormalWithIdentityRequestLogSwitch();

    /**
     * 带身份信息的RequestLogSwitch.
     * 
     * <h3>以下值会为true:</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>{@link #showFullURL} 请求路径</li>
     * <li>{@link #showMethod} 提交方法</li>
     * <li>{@link #showParams} 参数</li>
     * <li>{@link #showIdentity} 身份信息(ip及UA)</li>
     * <li>{@link #showIncludeInfos} 显示和include相关信息.</li>
     * <li>{@link #showForwardInfos} 显示和forward相关.</li>
     * </ul>
     * </blockquote>
     *
     * @since 1.4.0
     */
    public static final RequestLogSwitch NORMAL_WITH_IDENTITY_INCLUDE_FORWARD = new NormalWithIdentityIncludeForwardRequestLogSwitch();

    /**
     * 全的.
     * 
     * @since 1.4.0
     */
    public static final RequestLogSwitch FULL                                 = new FullRequestLogSwitch();

    /** 显示和FullURL相关. */
    private boolean                      showFullURL                          = true;

    /** 显示和Method. */
    private boolean                      showMethod                           = true;

    /** 显示参数. */
    private boolean                      showParams                           = true;

    // ****************************************************************************
    /** 显示IDENTITY(包含ip以及UA). */
    private boolean                      showIdentity;

    /** 显示cookie. */
    private boolean                      showCookies;

    /** 显示和ip相关. */
    private boolean                      showIPs;

    /** 显示和url相关. */
    private boolean                      showURLs;

    /** 显示和Port相关. */
    private boolean                      showPorts;

    /** 显示和else相关. */
    private boolean                      showElses;

    /** 显示和Header相关. */
    private boolean                      showHeaders;

    /** 显示和Error相关. */
    private boolean                      showErrors;

    /** 显示和forward相关. */
    private boolean                      showForwardInfos;

    /** 显示和include相关信息. */
    private boolean                      showIncludeInfos;

    /**
     * Gets the 显示参数.
     * 
     * @return the showParams
     */
    public boolean getShowParams(){
        return showParams;
    }

    /**
     * Sets the 显示参数.
     * 
     * @param showParams
     *            the showParams to set
     */
    protected void setShowParams(boolean showParams){
        this.showParams = showParams;
    }

    /**
     * Gets the 显示cookie.
     * 
     * @return the showCookies
     */
    public boolean getShowCookies(){
        return showCookies;
    }

    /**
     * Sets the 显示cookie.
     * 
     * @param showCookies
     *            the showCookies to set
     */
    protected void setShowCookies(boolean showCookies){
        this.showCookies = showCookies;
    }

    /**
     * Gets the 显示和ip相关.
     * 
     * @return the showIPs
     */
    public boolean getShowIPs(){
        return showIPs;
    }

    /**
     * Sets the 显示和ip相关.
     * 
     * @param showIPs
     *            the showIPs to set
     */
    protected void setShowIPs(boolean showIPs){
        this.showIPs = showIPs;
    }

    /**
     * Gets the 显示和url相关.
     * 
     * @return the showURLs
     */
    public boolean getShowURLs(){
        return showURLs;
    }

    /**
     * Sets the 显示和url相关.
     * 
     * @param showURLs
     *            the showURLs to set
     */
    protected void setShowURLs(boolean showURLs){
        this.showURLs = showURLs;
    }

    /**
     * Gets the 显示和Port相关.
     * 
     * @return the showPorts
     */
    public boolean getShowPorts(){
        return showPorts;
    }

    /**
     * Sets the 显示和Port相关.
     * 
     * @param showPorts
     *            the showPorts to set
     */
    protected void setShowPorts(boolean showPorts){
        this.showPorts = showPorts;
    }

    /**
     * Gets the 显示和else相关.
     * 
     * @return the showElses
     */
    public boolean getShowElses(){
        return showElses;
    }

    /**
     * Sets the 显示和else相关.
     * 
     * @param showElses
     *            the showElses to set
     */
    protected void setShowElses(boolean showElses){
        this.showElses = showElses;
    }

    /**
     * Gets the 显示和Header相关.
     * 
     * @return the showHeaders
     */
    public boolean getShowHeaders(){
        return showHeaders;
    }

    /**
     * Sets the 显示和Header相关.
     * 
     * @param showHeaders
     *            the showHeaders to set
     */
    protected void setShowHeaders(boolean showHeaders){
        this.showHeaders = showHeaders;
    }

    /**
     * Gets the 显示和Error相关.
     * 
     * @return the showErrors
     */
    public boolean getShowErrors(){
        return showErrors;
    }

    /**
     * Sets the 显示和Error相关.
     * 
     * @param showErrors
     *            the showErrors to set
     */
    protected void setShowErrors(boolean showErrors){
        this.showErrors = showErrors;
    }

    /**
     * Gets the 显示和FullURL相关.
     * 
     * @return the showFullURL
     */
    public boolean getShowFullURL(){
        return showFullURL;
    }

    /**
     * Sets the 显示和FullURL相关.
     * 
     * @param showFullURL
     *            the showFullURL to set
     */
    protected void setShowFullURL(boolean showFullURL){
        this.showFullURL = showFullURL;
    }

    /**
     * Gets the 显示和Method.
     * 
     * @return the showMethod
     */
    public boolean getShowMethod(){
        return showMethod;
    }

    /**
     * Sets the 显示和Method.
     * 
     * @param showMethod
     *            the showMethod to set
     */
    protected void setShowMethod(boolean showMethod){
        this.showMethod = showMethod;
    }

    /**
     * 获得 显示和forward相关.
     *
     * @return the showForwardInfos
     */
    public boolean getShowForwardInfos(){
        return showForwardInfos;
    }

    /**
     * 设置 显示和forward相关.
     *
     * @param showForwardInfos
     *            the showForwardInfos to set
     */
    protected void setShowForwardInfos(boolean showForwardInfos){
        this.showForwardInfos = showForwardInfos;
    }

    /**
     * 获得 显示和include相关信息.
     *
     * @return the showIncludeInfos
     */
    public boolean getShowIncludeInfos(){
        return showIncludeInfos;
    }

    /**
     * 设置 显示和include相关信息.
     *
     * @param showIncludeInfos
     *            the showIncludeInfos to set
     */
    protected void setShowIncludeInfos(boolean showIncludeInfos){
        this.showIncludeInfos = showIncludeInfos;
    }

    /**
     * 获得 显示IDENTITY(包含ip以及UA).
     *
     * @return the showIdentity
     */
    public boolean getShowIdentity(){
        return showIdentity;
    }

    /**
     * 设置 显示IDENTITY(包含ip以及UA).
     *
     * @param showIdentity
     *            the showIdentity to set
     */
    protected void setShowIdentity(boolean showIdentity){
        this.showIdentity = showIdentity;
    }

    //***************************************************************************************

    /**
     * This is an inner class to ensure its immutability.
     */
    private static final class FullRequestLogSwitch extends RequestLogSwitch{

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 4824997202393095960L;

        /**
         * Use the static constant rather than instantiating.
         */
        FullRequestLogSwitch(){
            this.setShowCookies(true);
            this.setShowElses(true);
            this.setShowErrors(true);
            this.setShowFullURL(true);
            this.setShowHeaders(true);
            this.setShowIPs(true);
            this.setShowMethod(true);
            this.setShowParams(true);
            this.setShowPorts(true);
            this.setShowURLs(true);
            this.setShowForwardInfos(true);
            this.setShowIncludeInfos(true);
            this.setShowIdentity(true);
        }

        /**
         * Ensure <code>Singleton</code> after serialization.
         *
         * @return the singleton
         */
        private Object readResolve(){
            return RequestLogSwitch.FULL;
        }
    }

    /**
     * This is an inner class to ensure its immutability.
     */
    private static final class NormalRequestLogSwitch extends RequestLogSwitch{

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 4824997202393095960L;

        /**
         * Use the static constant rather than instantiating.
         */
        NormalRequestLogSwitch(){
            this.setShowFullURL(true);
            this.setShowParams(true);
            this.setShowMethod(true);
        }

        /**
         * Ensure <code>Singleton</code> after serialization.
         *
         * @return the singleton
         */
        private Object readResolve(){
            return RequestLogSwitch.NORMAL;
        }
    }

    /**
     * This is an inner class to ensure its immutability.
     */
    private static final class NormalWithIdentityRequestLogSwitch extends RequestLogSwitch{

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 4824997202393095960L;

        /**
         * Use the static constant rather than instantiating.
         */
        NormalWithIdentityRequestLogSwitch(){
            this.setShowFullURL(true);
            this.setShowParams(true);
            this.setShowMethod(true);
            this.setShowIdentity(true);
        }

        /**
         * Ensure <code>Singleton</code> after serialization.
         *
         * @return the singleton
         */
        private Object readResolve(){
            return RequestLogSwitch.NORMAL_WITH_IDENTITY;
        }
    }

    /**
     * This is an inner class to ensure its immutability.
     */
    private static final class NormalWithIdentityIncludeForwardRequestLogSwitch extends RequestLogSwitch{

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 4824997202393095960L;

        /**
         * Use the static constant rather than instantiating.
         */
        NormalWithIdentityIncludeForwardRequestLogSwitch(){
            this.setShowFullURL(true);
            this.setShowParams(true);
            this.setShowMethod(true);
            this.setShowIdentity(true);
            this.setShowIncludeInfos(true);
            this.setShowForwardInfos(true);
        }

        /**
         * Ensure <code>Singleton</code> after serialization.
         *
         * @return the singleton
         */
        private Object readResolve(){
            return RequestLogSwitch.NORMAL_WITH_IDENTITY_INCLUDE_FORWARD;
        }
    }
}
