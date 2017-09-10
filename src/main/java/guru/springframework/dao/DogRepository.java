package guru.springframework.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import guru.springframework.model.Dog;

@Repository
public interface DogRepository extends CrudRepository<Dog,Long> {
	
	Dog findByName(String name);

}
