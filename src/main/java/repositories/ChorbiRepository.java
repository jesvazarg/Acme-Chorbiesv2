
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chorbi;

@Repository
public interface ChorbiRepository extends JpaRepository<Chorbi, Integer> {

	@Query("select c from Chorbi c where c.userAccount.id = ?1")
	Chorbi findByUserAccountId(int userAccountId);

	//C1: A listing with the number of chorbies per country and city.
	@Query("select c.coordinate.country, c.coordinate.city, count(c) from Chorbi c group by c.coordinate.country, c.coordinate.city")
	Collection<Object[]> numberChorbiPerCountryAndCity();

	//----------------- 1º Opcion -----------------------------------

	//Para obtener la edad de un usuario:
	@Query("select (YEAR(CURRENT_TIMESTAMP) - YEAR(c.birthDate)) from Chorbi c where c.id=?1")
	Integer ageOfChorbi(int chorbiId);

	//C2: The minimum, the maximum, and the average ages of the chorbies.
	@Query("select min((YEAR(CURRENT_TIMESTAMP) - YEAR(c.birthDate))),avg((YEAR(CURRENT_TIMESTAMP) - YEAR(c.birthDate))),max((YEAR(CURRENT_TIMESTAMP) - YEAR(c.birthDate))) from Chorbi c")
	Double[] minMaxAvgAgeOfChorbi();

	//----------------- 2º Opcion -----------------------------------

	// Primero las pondre por separado para que sean mas fácil de leer y luego
	// pondré la query en su conjunto.

	//C2: The minimum ages of the chorbies.
	@Query("select min((YEAR(CURRENT_TIMESTAMP) - YEAR(c.birthDate)) -  case when (DATE_FORMAT(CURRENT_TIMESTAMP, '00-%m-%d')< DATE_FORMAT(c.birthDate, '00-%m-%d')) then 1 else 0 end )from Chorbi c")
	Double minAgeOfChorbi2();

	//C2: The average ages of the chorbies.
	@Query("select avg((YEAR(CURRENT_TIMESTAMP) - YEAR(c.birthDate)) -  case when (DATE_FORMAT(CURRENT_TIMESTAMP, '00-%m-%d')< DATE_FORMAT(c.birthDate, '00-%m-%d')) then 1 else 0 end )from Chorbi c")
	Double maxAgeOfChorbi2();

	//C2: The maximum ages of the chorbies.
	@Query("select max((YEAR(CURRENT_TIMESTAMP) - YEAR(c.birthDate)) -  case when (DATE_FORMAT(CURRENT_TIMESTAMP, '00-%m-%d')< DATE_FORMAT(c.birthDate, '00-%m-%d')) then 1 else 0 end )from Chorbi c")
	Double avgAgeOfChorbi2();

	//C2: The minimum, the maximum, and the average ages of the chorbies.
	@Query("select min((YEAR(CURRENT_TIMESTAMP) - YEAR(c.birthDate)) -  case when (DATE_FORMAT(CURRENT_TIMESTAMP, '00-%m-%d')< DATE_FORMAT(c.birthDate, '00-%m-%d')) then 1 else 0 end ), avg((YEAR(CURRENT_TIMESTAMP) - YEAR(c.birthDate)) -  case when (DATE_FORMAT(CURRENT_TIMESTAMP, '00-%m-%d')< DATE_FORMAT(c.birthDate, '00-%m-%d')) then 1 else 0 end ), max((YEAR(CURRENT_TIMESTAMP) - YEAR(c.birthDate)) -  case when (DATE_FORMAT(CURRENT_TIMESTAMP, '00-%m-%d')< DATE_FORMAT(c.birthDate, '00-%m-%d')) then 1 else 0 end )from Chorbi c")
	Double[] minMaxAvgAgeOfChorbi2();

	//C3: The ratio of chorbies who have not registered a credit card
	@Query("select (select (select count(ch)*1.0 from Chorbi ch)-count(cr)*1.0 from CreditCard cr) /  count(c)*1.0 from Chorbi c)")
	Double ratioNotRegisteredCreditcardPerChorbi();

	@Query("select count(s) from Chorbi s join s.creditCard cred where cred.expirationYear <= YEAR(CURRENT_DATE)")
	Double numberChorbiesWithInvalidCreditYear();

	@Query("select count(s) from Chorbi s join s.creditCard cred where cred.expirationMonth <= MONTH(CURRENT_DATE)")
	Double numberChorbiesWithInvalidCreditMonth();

	//C4: The ratios of chorbies who search for activities, friendship, and love.
	@Query("select count(s)*1.0/(select count(c) from Chorbi c) from SearchTemplate s where s.relationship!=null or s.relationship!=''")
	Double ratioPerChorbiAndSearchTemplateRelationship();

	//B.1: The list of chorbies, sorted by the number of likes they have got.
	@Query("select c from Chorbi c order by c.reciveSenses.size DESC")
	Collection<Chorbi> chorbiesSortedGotLikes();

	//A1: The minimum, the maximum, and the average number of chirps that a chor-bi receives from other chorbies.
	@Query("select min(c.reciveChirps.size), avg(c.reciveChirps.size), max(c.reciveChirps.size) from Chorbi c")
	Double[] minMaxAvgReciveChirps();

	//A3: The chorbies who have got more chirps.
	@Query("select c from Chorbi c where c.reciveChirps.size=(select max(c2.reciveChirps.size) from Chorbi c2)")
	Collection<Chorbi> findChorbiMoreReciveChirps();

	//A4: The chorbies who have sent more chirps.
	@Query("select c from Chorbi c where c.sentChirps.size=(select max(c2.sentChirps.size) from Chorbi c2)")
	Collection<Chorbi> findChorbiMoreSentChirps();

	//----------------- Dashboard 2.0 -----------------------------------

	//C3: A listing of chorbies sorted by the number of events to which they have registered.
	@Query("select c from Chorbi c order by c.events.size desc")
	Collection<Chorbi> chorbiesOrderByEventRegistered();

	//C4: A listing of chorbies that includes the amount that they due in fees.
	@Query("select c, c.amount from Chorbi c")
	Collection<Object[]> chorbiesAmountDueFee();

	//B1: The minimum, the maximum, and the average number of stars per chorbi.
	@Query("select sum(s.stars) from Sense s group by s.recipient order by sum(s.stars) ASC")
	Double[] minStarsPerChorbi();

	@Query("select sum(s.stars) from Sense s group by s.recipient order by sum(s.stars) DESC")
	Double[] maxStarsPerChorbi();

	@Query("select sum(s.stars)/(select count(c1) from Chorbi c1)*1.0 from Sense s")
	Double avgStarsPerChorbi();

	//B2: The list of chorbies, sorted by the average number of stars that they've got.
	@Query("select c from Chorbi c join c.reciveSenses s group by c order by sum(s.stars)/c.reciveSenses.size *1.0 desc")
	Collection<Chorbi> chorbiesSortedByAvgNumberOfStars();

}
