
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chirp;

@Repository
public interface ChirpRepository extends JpaRepository<Chirp, Integer> {

	@Query("select c from Chirp c where c.sender.id=?1 and c.copy=false")
	Collection<Chirp> findChirpsSentByChorbiId(int chorbiId);

	@Query("select c from Chirp c where c.recipient.id=?1 and c.copy=true")
	Collection<Chirp> findChirpsReceivedByChorbiId(int chorbiId);

	//A2:The minimum, the maximum, and the average number of chirps that a chor-bi sends to other chorbies.
	@Query("select min(c.sentChirps.size), avg(c.sentChirps.size), max(c.sentChirps.size) from Chorbi c")
	Double[] minAvgMaxChirpsSent();

}
