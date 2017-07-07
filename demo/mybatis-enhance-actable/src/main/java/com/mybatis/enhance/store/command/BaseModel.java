package com.mybatis.enhance.store.command;

import java.io.Serializable;


/**
 * 分页的基本属性
 *
 * @author sunchenbin
 * @version 2016年6月24日 上午10:55:21 
 */
public class BaseModel implements Serializable{

	private static final long serialVersionUID = -2467322075253424352L;
	
	/**
	 * 当前页码
	 */
	public int currentPage;
	
	/**
	 * 每页显示多少条，默认10条
	 */
	public int pageSize;
	
	public int start;// (currentPage-1)*pageSize

	
	public int getCurrentPage(){
		return currentPage;
	}

	
	public void setCurrentPage(int currentPage){
		this.currentPage = currentPage;
		this.setStart((currentPage-1)*getPageSize());
	}

	
	public int getPageSize(){
		if (this.pageSize == 0) {
			// 默认值设为10
			this.pageSize = 10;
		}
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

	
}
