package com.sunchenbin.store.dao.test;


import java.util.List;

import com.sunchenbin.store.model.test.Test;

public interface TestMapper {

	public List<Test> findTest(Test test);
	
	public int findTestCount();

}
