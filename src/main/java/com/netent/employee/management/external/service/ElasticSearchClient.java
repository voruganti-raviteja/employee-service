package com.netent.employee.management.external.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhrasePrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.netent.employee.management.constants.ApplicationConstants;
import com.netent.employee.management.vo.Employee;

@Service
public class ElasticSearchClient {

	private static Logger LOGGER = LoggerFactory.getLogger(ElasticSearchClient.class);

	@Autowired
	Gson gson;

	@Resource
	private RestHighLevelClient esClient;

	public void indexEmployees(Employee employee) throws Exception {
		IndexRequest request = new IndexRequest(ApplicationConstants.EMPLOYEE_ES_INDEX);
		request.source(gson.toJson(employee), XContentType.JSON);
		request.id(employee.getId() + "");
		try {
			esClient.index(request, RequestOptions.DEFAULT);
		} catch (IOException e) {
			LOGGER.error("Error in indexing employee", e);
			throw new Exception();
		}
	}

	public List<Employee> searchEmployees(String searchKey, String searchValue) {

		SearchRequest request = new SearchRequest(ApplicationConstants.EMPLOYEE_ES_INDEX);
		request.allowPartialSearchResults(true);
		request.indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		if (searchKey.equals(ApplicationConstants.AGE_SEARCH_KEY)) {
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			boolQueryBuilder.must(QueryBuilders.matchQuery(ApplicationConstants.AGE_SEARCH_KEY, searchValue));
			searchSourceBuilder.query(boolQueryBuilder);
		}
		if (searchKey.equals(ApplicationConstants.NAME_SEARCH_KEY)) {
			MatchPhrasePrefixQueryBuilder queryBuilder = QueryBuilders
					.matchPhrasePrefixQuery(ApplicationConstants.NAME_SEARCH_KEY, searchValue);
			searchSourceBuilder.query(queryBuilder);
		}
		request.source(searchSourceBuilder);

		SearchResponse searchResponse = null;
		try {
			searchResponse = esClient.search(request, RequestOptions.DEFAULT);
		} catch (IOException e) {
			LOGGER.error("Error in searching employees", e);
			throw new RuntimeException("Error in searching employees.");
		}
		SearchHit[] hits = searchResponse.getHits().getHits();
		return Arrays.asList(hits).stream().map(hit -> gson.fromJson(hit.getSourceAsString(), Employee.class))
				.collect(Collectors.toList());

	}

}
