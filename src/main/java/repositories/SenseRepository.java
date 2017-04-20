
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sense;

@Repository
public interface SenseRepository extends JpaRepository<Sense, Integer> {

	//B2: The minimum, the maximum, and the average number of likes per chorbi.
	@Query("select min(c.giveSenses.size), avg(c.giveSenses.size), max(c.giveSenses.size) from Chorbi c")
	Double[] minAvgMaxOfSenses();
}
