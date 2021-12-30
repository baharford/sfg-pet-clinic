package guru.springframework.repositories;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.model.Vet;

public interface VetRepository extends CrudRepository<Vet, Long> {

}
