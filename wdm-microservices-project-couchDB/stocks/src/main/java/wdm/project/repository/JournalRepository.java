package wdm.project.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wdm.project.dto.JournalEntry;

import java.util.List;

@Component
@View(name = "events", map = "function(doc) { if (doc.type == 'event' ) emit(doc._id, doc.status)}")
public class JournalRepository extends CouchDbRepositorySupport<JournalEntry> {

    @Autowired
    public JournalRepository(CouchDbConnector db) {
        super(JournalEntry.class, db);
        initStandardDesignDocument();
    }

    public List<JournalEntry> findJournal(String id) {
        return queryView("events", id);
    }
}
