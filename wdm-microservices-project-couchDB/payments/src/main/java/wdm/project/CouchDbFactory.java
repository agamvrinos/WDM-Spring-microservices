package wdm.project;

import java.net.MalformedURLException;
import org.apache.log4j.Logger;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class CouchDbFactory {

	private final Logger log = Logger.getLogger(CouchDbFactory.class);
	private CouchDbConnector dbConnector = null;
	@Value("${couchdb.username}")
	private String user;
	@Value("${couchdb.password}")
	String passwd;
	@Value("${couchdb.database}")
	String dbName;
	@Value("${couchdb.host}")
	String host;
	@Value("${couchdb.port}")
	String port;

	/**
	 * Function returns connector to database
	 *
	 * @return CouchDBConnection
	 */
	@Bean
	public CouchDbConnector CouchDbConnector() {
		if (dbConnector == null) {
			try {
				HttpClient httpClient = new StdHttpClient.Builder()
						.url("http://" + host + ":" + port + "/")
						.username(user)
						.password(passwd)
						.build();
				CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
				// connect and create database if not exist
				dbConnector = dbInstance.createConnector(dbName, true);

				log.info("Connected to CouchDb " + dbName + "...");
			} catch (MalformedURLException ex) {
				log.error("Malformed url: " + host);
			}
		}

		return dbConnector;
	}
}
