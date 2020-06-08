package com.gitee.sunchenbin.mybatis.actable.command;

import java.io.Serializable;
import java.util.LinkedHashMap;


/**
 * 分页的基本属性
 *
 * @author sunchenbin
 * @version 2016年6月24日 上午10:55:21 
 */
public class BaseModel implements Serializable{

	private static final long serialVersionUID = -2467322075253424352L;
	
	/**
	 * 当前页码,请先设置pageSize,否则使用默认的1
	 */
	public int currentPage = 1;
	
	/**
	 * 每页显示多少条，默认10条
	 */
	public int pageSize = 10;
	
	public int start;// (currentPage-1)*pageSize
	
	public LinkedHashMap<String, String> orderBy;

	public static String DESC = "desc";

	public static String ASC = "asc";
	
	public int getCurrentPage(){
		return currentPage;
	}

	
	public void setCurrentPage(int currentPage){
		this.currentPage = currentPage;
		this.setStart((currentPage-1)*getPageSize());
	}

	
	public int getPageSize(){
		return pageSize;
	}

	
	public void setPageSize(int pageSize){
		this.pageSize = pageSize;
	}

	
	public int getStart(){
		this.setStart((currentPage-1)*getPageSize());
		return start;
	}

	
	public void setStart(int start){
		this.start = start;
	}


	public LinkedHashMap<String, String> getOrderBy() {
		return orderBy;
	}


	public void setOrderBy(LinkedHashMap<String, String> orderBy) {
		this.orderBy = orderBy;
	}
	
}
