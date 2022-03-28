package com.yawintutor;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yawintutor.secondary.Manager;
import com.yawintutor.secondary.ManagerRepository;

@Component
public class MyCustomWriter implements ItemWriter<Manager> {
	
	@Autowired
	ManagerRepository managerRepository ;
	
	@Override
	public void write(List<? extends Manager> list) throws Exception {
		for (Manager data : list) {
			System.out.println("MyCustomWriter    : Writing data    : " + data.getId()+" : "+data.getName()+" : "+data.getSalary());
			managerRepository.save(data);
		}
	}
}