/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.cookbook.datasense;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.mule.api.annotations.MetaDataKeyRetriever;
import org.mule.api.annotations.MetaDataRetriever;
import org.mule.api.annotations.components.MetaDataCategory;
import org.mule.common.metadata.DefaultMetaData;
import org.mule.common.metadata.DefaultMetaDataKey;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataKey;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.builder.DefaultMetaDataBuilder;
import org.mule.common.metadata.builder.PojoMetaDataBuilder;
import org.mule.modules.cookbook.CookBookConnector;

import com.cookbook.tutorial.service.Ingredient;
import com.cookbook.tutorial.service.Recipe;

/**
 * Category which can differentiate between input or output MetaDataRetriever
 */
@MetaDataCategory
public class DataSenseResolver {

	@Inject
	private CookBookConnector connector;
	
	/**
	 * Retrieves the list of keys
	 */
	@MetaDataKeyRetriever
	public List<MetaDataKey> getMetaDataKeys() throws Exception {
		List<MetaDataKey> keys = new ArrayList<MetaDataKey>();

		// Generate the keys
		keys.add(new DefaultMetaDataKey("id1", "Ingredient"));
		keys.add(new DefaultMetaDataKey("id2", "Recipe"));

		return keys;
	}

	/**
	 * Get MetaData given the Key the user selects
	 * 
	 * @param key
	 *            The key selected from the list of valid keys
	 * @return The MetaData model of that corresponds to the key
	 * @throws Exception
	 *             If anything fails
	 */
	@MetaDataRetriever
	public MetaData getMetaData(MetaDataKey key) throws Exception {
		DefaultMetaDataBuilder builder = new DefaultMetaDataBuilder();
		// Since our model is static and we can simply create the pojo model.
		PojoMetaDataBuilder<?> pojoObject = null;
		if ("id1".equals(key.getId()))
			pojoObject = builder.createPojo(Ingredient.class);
		else if ("id2".equals(key.getId()))
			pojoObject = builder.createPojo(Recipe.class);
		else
			throw new RuntimeException("Invalid key:" + key.getId());

		MetaDataModel model = pojoObject.build();
		MetaData metaData = new DefaultMetaData(model);

		return metaData;
	}

	public CookBookConnector getConnector() {
		return connector;
	}

	public void setConnector(CookBookConnector connector) {
		this.connector = connector;
	}

}
