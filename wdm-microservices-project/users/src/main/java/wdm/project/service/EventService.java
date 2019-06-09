package wdm.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import wdm.project.dto.JournalEntry;
import wdm.project.dto.JournalEntryId;
import wdm.project.enums.Status;
import wdm.project.repository.JournalRepository;

@Service
public class EventService {

	@Autowired
	private JournalRepository journalRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveEvent(JournalEntryId journalEntryId, Status status) {
		JournalEntry journalEntry = new JournalEntry(journalEntryId, status);
		journalRepository.save(journalEntry);
	}
}
