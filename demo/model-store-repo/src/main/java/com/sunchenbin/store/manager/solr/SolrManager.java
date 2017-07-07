package com.sunchenbin.store.manager.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

public interface SolrManager{
	
	public QueryResponse query(SolrQuery query);

}
