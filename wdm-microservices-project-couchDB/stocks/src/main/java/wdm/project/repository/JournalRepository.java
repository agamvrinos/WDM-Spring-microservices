package wdm.project.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wdm.project.dto.JournalEntry;

@Component
public class JournalRepository extends CouchDbRepositorySupport<JournalEntry> {

    @Autowired
    public JournalRepository(CouchDbConnector db) {
        super(JournalEntry.class, db);
        initStandardDesignDocument();
    }

}
