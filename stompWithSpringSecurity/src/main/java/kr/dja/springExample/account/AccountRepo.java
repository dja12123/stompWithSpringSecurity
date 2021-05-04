package kr.dja.springExample.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepo extends JpaRepository<AccountEntity, Long>
{
	boolean existsByUserId(String userId);
	AccountEntity findByUserId(String userid);
}
