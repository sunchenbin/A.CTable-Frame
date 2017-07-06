package com.sunchenbin.store.manager.solr;

public class ItemSearchCondition{

	/**
	 * 排序 ：排序字段加asc,desc 标识升序和降序
	 * 比如：id+asc,mobile+desc(这样就是id升序并且mobile降序)
	 */
	private String		sorts;

	/** 关键字全文检索 */
	private String		keywords;

	/**
	 * 当前显示第几页
	 */
	private Integer		currPage;

	/** 每页显示数量 */
	private Integer		pageSize;

	/*******************************************************************************************************************/

	/**
	 * 用于聚合，传入需要聚合的字段，使用逗号分割就行
	 */
	private String[]	facets;

	/**
	 * @return the currPage
	 */
	public Integer getCurrPage(){
		return currPage;
	}

	/**
	 * @param currPage
	 *            the currPage to set
	 */
	public void setCurrPage(Integer currPage){
		this.currPage = currPage;
	}

	/**
	 * @return the sorts
	 */
	public String getSorts(){
		return sorts;
	}

	/**
	 * @param sorts
	 *            the sorts to set
	 */
	public void setSorts(String sorts){
		this.sorts = sorts;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize(){
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(Integer pageSize){
		this.pageSize = pageSize;
	}

	/**
	 * @return the keywords
	 */
	public String getKeywords(){
		return keywords;
	}

	/**
	 * @param keywords
	 *            the keywords to set
	 */
	public void setKeywords(String keywords){
		this.keywords = keywords;
	}
	
	/**
	 * @return the facets
	 */
	public String[] getFacets(){
		return facets;
	}

	/**
	 * @param facets
	 *            the facets to set
	 */
	public void setFacets(String[] facets){
		this.facets = facets;
	}

}
