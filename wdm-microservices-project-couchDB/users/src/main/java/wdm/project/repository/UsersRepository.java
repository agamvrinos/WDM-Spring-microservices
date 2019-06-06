package wdm.project.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wdm.project.dto.User;

import java.util.List;

@Component
@View(name = "users", map = "function(doc) { if (doc.type == 'object' ) emit(doc._id, doc.credit)}")
public class UsersRepository extends CouchDbRepositorySupport<User> {

    @Autowired
    public UsersRepository(CouchDbConnector db) {
        super(User.class, db);
        initStandardDesignDocument();
    }

    public List<User> findUser(String id) {
        return queryView("users", id);
    }
}
