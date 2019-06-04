package wdm.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wdm.project.dto.JournalEntry;
import wdm.project.dto.JournalEntryId;

@Repository
public interface JournalRepository extends JpaRepository<JournalEntry, JournalEntryId> {
}
