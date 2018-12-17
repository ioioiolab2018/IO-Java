package pl.put.poznan.analyzer.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.put.poznan.analyzer.commons.Network;

@Repository
public interface NetworkRepository extends CrudRepository<Network, Integer> {

}