package org.mule.modules.cookbook.pagination;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.mule.api.MuleException;
import org.mule.modules.cookbook.CookBookConnector;
import org.mule.streaming.ProviderAwarePagingDelegate;

import com.cookbook.tutorial.service.CookBookEntity;
import com.cookbook.tutorial.service.SessionExpiredException;

public class CookbookPagingDelegate extends
		ProviderAwarePagingDelegate<Map<String, Object>, CookBookConnector> {

	private Integer currentPage = 0;
	ObjectMapper m = new ObjectMapper();

	private final Integer pageSize;
	private final String query;

	public CookbookPagingDelegate(String query, Integer pageSize) {
		super();
		this.query = query;
		this.pageSize = pageSize;
	}

	@Override
	public void close() throws MuleException {

	}

	@Override
	public List<Map<String, Object>> getPage(CookBookConnector connector)
			throws Exception {

		try {
			List<CookBookEntity> list = connector.getConnectionStrategy()
					.getClient()
					.searchWithQuery(query, currentPage++, pageSize);
			List<Map<String, Object>> result = m.convertValue(list,
					new TypeReference<List<Map<String, Object>>>() {
					});
			return result;
		} catch (SessionExpiredException ex) {
            //Revert the increment since we want to retry to get the same page if the reconnection is configured.
			currentPage--;
			throw ex;
		}
	}

	@Override
	public int getTotalResults(CookBookConnector connector) throws Exception {
		return 0;
	}

}
