package com.kristal.pricemodule.inmemorystore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.kristal.pricemodule.model.ConvertedPriceModel;

public class InMemoryStore {

	private Map<String, ConvertedPriceModel> store = new ConcurrentHashMap<>();

	private static InMemoryStore inMemoryStore = null;

	private InMemoryStore() {

	}

	public static InMemoryStore getInstance() {

		return InMemoryStoreHelper.INSTANCE;
	}

	public Map<String, ConvertedPriceModel> getStore() {
		return store;
	}

	public void setStore(Map<String, ConvertedPriceModel> store) {
		this.store = store;
	}

	public void putInStore(String comapnyName, ConvertedPriceModel convertedPrice) {
		this.store.put(comapnyName, convertedPrice);
	}

	private static class InMemoryStoreHelper {
		private static final InMemoryStore INSTANCE = new InMemoryStore();
	}

}
