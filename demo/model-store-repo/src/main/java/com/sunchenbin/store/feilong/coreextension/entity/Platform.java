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
package com.sunchenbin.store.feilong.coreextension.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 平台的配置,支持Spring注入.
 * 
 * <p>
 * 一个项目一般而言,有且仅有一个 {@link Platform}对象
 * </p>
 * 
 * <h3>使用方式:</h3>
 * 
 * <blockquote>
 * <ul>
 * <li>spring注入(<span style="color:red">推荐</span>)</li>
 * <li>Listener初始化</li>
 * <li>servlet初始化</li>
 * <li>...</li>
 * </ul>
 * </blockquote>
 * 
 * <h3>属性</h3>
 * 
 * 目前仅可以配置三个属性,将来规划好,可以新增其他属性
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>{@link #name}</td>
 * <td>平台名称(无实质作用,可以是中文,just see see 只是看看的)</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link #description}</td>
 * <td>平台描述(无实质作用,just see see 只是看看的).</td>
 * </tr>
 * <tr valign="top">
 * <td>{@link #type}</td>
 * <td>平台类型(核心作用, 值示例如 PC,MOBILE等),可以直接在代码中使用,当然也可以基于这里配置的数据 做mapping映射.
 * <p>
 * 比如配置文件中, 配置了值是pc,但是存储到数据库中是 integer 类型1 ,可以使用代码做一次convert转换
 * </p>
 * <p>
 * 此时,建议配置文件中配置可视识别的配置,如 PC 而不是 1这样的数据
 * </p>
 * </td>
 * </tr>
 * </table>
 * </blockquote>
 *
 * @author feilong
 * @version 1.3.0 2015年7月31日 下午5:46:23
 * @since 1.3.0
 */
public class Platform implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4900287863640204772L;

    /** 平台名称(无实质作用,可以是中文,just see see 只是看看的). */
    private String            name;

    /** 平台描述(无实质作用,just see see 只是看看的). */
    private String            description;

    /**
     * 平台类型(核心作用, 值示例如 PC,MOBILE等),可以直接在代码中使用,当然也可以基于这里配置的数据 做mapping映射.
     * 
     * <p>
     * 比如配置文件中, 配置了值是pc,但是存储到数据库中是 integer 类型1 ,可以使用代码做一次convert转换
     * </p>
     * <p>
     * 此时,建议配置文件中配置可视识别的配置,如 PC 而不是 1这样的数据
     * </p>
     */
    private String            type;

    /**
     * 获得 平台名称(无实质作用,可以是中文,just see see 只是看看的).
     *
     * @return the name
     */
    public String getName(){
        return name;
    }

    /**
     * 设置 平台名称(无实质作用,可以是中文,just see see 只是看看的).
     *
     * @param name
     *            the name to set
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * 获得 平台描述(无实质作用,just see see 只是看看的).
     *
     * @return the description
     */
    public String getDescription(){
        return description;
    }

    /**
     * 设置 平台描述(无实质作用,just see see 只是看看的).
     *
     * @param description
     *            the description to set
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * 获得 平台类型(核心作用, 值示例如 PC,MOBILE等),可以直接在代码中使用,当然也可以基于这里配置的数据 做mapping映射.
     *
     * @return the type
     */
    public String getType(){
        return type;
    }

    /**
     * 设置 平台类型(核心作用, 值示例如 PC,MOBILE等),可以直接在代码中使用,当然也可以基于这里配置的数据 做mapping映射.
     *
     * @param type
     *            the type to set
     */
    public void setType(String type){
        this.type = type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
