package com.sunchenbin.store.manager.test;

import java.util.List;

import com.sunchenbin.store.model.test.Test;

public interface TestManager {

	public List<Test> findTest(Test test);
	
	public int findTestCount();
}
