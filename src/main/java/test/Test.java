package test;

import java.io.File;
import java.util.Arrays;

import org.eclipse.rdf4j.federated.FedXConfig;
import org.eclipse.rdf4j.federated.FedXFactory;
import org.eclipse.rdf4j.federated.repository.FedXRepository;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Test {

	public static void main(String[] args) throws Exception {

		if (args.length < 2) {
			throw new Exception("expect <conf-file> <query-file>");
		}

		File dataConfig = new File(args[0]);
//		Repository repo = FedXFactory.createFederation(dataConfig);

		FedXRepository repo = FedXFactory.newFederation()
				.withConfig(new FedXConfig().withEnableMonitoring(false).withLogQueryPlan(false).withLogQueries(false)
						.withDebugQueryPlan(false).withJoinWorkerThreads(30).withUnionWorkerThreads(30)
						.withBoundJoinBlockSize(30).withEnforceMaxQueryTime(0))
				.withMembers(dataConfig).create();

		String q = "";

		try (BufferedReader br = new BufferedReader(new FileReader(args[1]))) {
			String line;
			while ((line = br.readLine()) != null) {
				q += line + "\n";
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
		// System.out.println(q); // This will print the contents of the file to the console

		long startTime = System.currentTimeMillis();

		try (RepositoryConnection conn = repo.getConnection()) {
			TupleQuery query = conn.prepareTupleQuery(QueryLanguage.SPARQL, q);
			try (TupleQueryResult res = query.evaluate()) {

				while (res.hasNext()) {
					System.out.println(res.next());
				}
			}
		}
		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
		System.out.println("query:"+args[1]+",config:"+args[0]+",time: " + elapsedTime + " milliseconds.");

		repo.shutDown();
		System.exit(0);

	}
}