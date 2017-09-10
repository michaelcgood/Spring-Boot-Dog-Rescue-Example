package guru.springframework.service;

import java.util.Date;
import java.util.List;

public interface DogService {
	
	void addADog(String name, Date rescued, Boolean vaccinated);
	
	void deleteADOG(String name, Long id);
	
	List<String> atriskdogs(Date rescued);
	
	long getGeneratedKey(String name, Date rescued, Boolean vaccinated);
	
}
