package com.sunchenbin.store.manager.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolrManagerImpl implements SolrManager{
	
	@Autowired
	private SolrServer solrServer;

	public QueryResponse query(SolrQuery query){
		try{
			solrServer.query(query);
		}catch (SolrServerException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
