package wdm.project.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wdm.project.dto.User;

@Component
public class UsersRepository extends CouchDbRepositorySupport<User> {

    @Autowired
    public UsersRepository(CouchDbConnector db) {
        super(User.class, db);
        initStandardDesignDocument();
    }
}
