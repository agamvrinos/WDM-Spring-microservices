package wdm.project.repository;


import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wdm.project.dto.User;

import java.util.List;

@Component
public class UsersRepository extends CouchDbRepositorySupport<User> {

    @Autowired
    public UsersRepository(CouchDbConnector db) {
        super(User.class, db);
        initStandardDesignDocument();
    }

    @GenerateView
    @Override
    public List<User> getAll() {
        ViewQuery q = createQuery("all").descending(true);
        return db.queryView(q, User.class);
    }

    @GenerateView
    public List<User> findById(String id) {
        return queryView("by_id", id);
    }

}