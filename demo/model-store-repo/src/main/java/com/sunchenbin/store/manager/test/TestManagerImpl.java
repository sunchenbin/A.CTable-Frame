package com.sunchenbin.store.manager.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunchenbin.store.dao.test.TestMapper;
import com.sunchenbin.store.model.test.Test;

@Transactional
@Service("testManager")
public class TestManagerImpl implements TestManager {
	@Autowired
	private TestMapper testMapper;

	public List<Test> findTest(Test test){
		return testMapper.findTest(test);
	}

	public int findTestCount(){
		return testMapper.findTestCount();
	}		

}
