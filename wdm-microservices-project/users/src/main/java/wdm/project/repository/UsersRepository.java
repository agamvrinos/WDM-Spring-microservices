package wdm.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wdm.project.dto.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
}
