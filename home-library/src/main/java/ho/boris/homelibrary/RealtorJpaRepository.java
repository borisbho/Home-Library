package ho.boris.homelibrary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RealtorJpaRepository extends JpaRepository<Realtor,Integer>{
 	
	Realtor getOne(Integer id);
 	List<Realtor> findRealtorByRealtorFirstName(String realtorFirstName);

	@Query(
		"SELECT c " +
		"FROM Realtor c " +
		"WHERE c.realtorFirstName = :realtorFirstName" )
	List<Realtor> queryByRealtorName(
			@Param("realtorFirstName") String realtorFirstName); 
}
