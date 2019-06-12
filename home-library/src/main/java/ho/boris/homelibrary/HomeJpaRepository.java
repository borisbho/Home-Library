package ho.boris.homelibrary;


import java.util.List;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
 
 
public interface HomeJpaRepository extends JpaRepository<Home,Integer>{
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
  	Home getOne(Integer id);
 
	List<Home> findHomeByHomeCity(String homeCity);

	@Query(
		"SELECT c " +
		"FROM Home c " +
		"WHERE c.homeCity = :homeCity ")
	List<Home> queryByHomeCity(
			@Param("homeCity") String homeCity); 
	
	
 		
}
