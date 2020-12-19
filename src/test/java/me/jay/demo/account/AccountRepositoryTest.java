package me.jay.demo.account;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Optional;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@SpringBootTest 라고 설정하면 integration test라서 모든 application의 bean이 등록되서 느리고 테스트 용 db가 필요하며 테스트 로직에 따라 db 데이터도 변경된다.
@DataJpaTest //slicing test, embedded memory db를 사용해서 테스트한다.
public class AccountRepositoryTest {

	@Autowired
	DataSource dataSource;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	AccountRepository accountRepository;

	@Test
	public void di() throws SQLException {
		Account account =  new Account();
		account.setUsername("jay");
		account.setPassword("pass");
		account.setEmaail("my email");

		Account newAccount = accountRepository.save(account);

		assertThat(newAccount).isNotNull();

		Optional<Account> existingAccount = accountRepository.findByUsername(newAccount.getUsername());
		assertThat(existingAccount).isNotEmpty();

//		Account existingAccount = accountRepository.findByUsername(newAccount.getUsername());
//		assertThat(existingAccount).isNotNull();

		Optional<Account> notExistingAccount = accountRepository.findByUsername("who");
		assertThat(notExistingAccount).isEmpty();

//		Account notExistingAccount = accountRepository.findByUsername("who");
//		assertThat(notExistingAccount).isNull();
	}

}