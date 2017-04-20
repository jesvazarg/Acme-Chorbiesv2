
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

	@Query("select p from Person p where p.userAccount.id = ?1")
	Person findByUserAccountId(int userAccountId);

}
