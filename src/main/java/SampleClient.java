import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.CacheControlDirective;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

public class SampleClient {

    public static void main(String[] theArgs) {
    	
        // Create a FHIR client
        FhirContext fhirContext = FhirContext.forR4();
        IGenericClient client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4");
        client.registerInterceptor(new LoggingInterceptor(false));
        
        // Search for Patient resources
        
        String fileName = "config/patients.txt";
        
        File file = new File(SampleClient.class.getResource(fileName).getFile());
        List<String> names=Collections.emptyList();
        try {
        	names = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.ISO_8859_1));
		} catch (IOException e) {
			e.printStackTrace();
		}
        for(int i=1;i<=3;i++) {
        	long startTime = System.currentTimeMillis();
        	Iterator<String> lastNames = names.iterator();
	        while(lastNames.hasNext()) {
		        Bundle response = client
		                .search()
		                .forResource("Patient")
		                .where(Patient.FAMILY.matches().value(lastNames.next()))
		                .returnBundle(Bundle.class)	
		                .cacheControl(new CacheControlDirective().setNoCache(true))
		                .execute();
		               
	        }
	        long endTime =System.currentTimeMillis();
	        System.out.println("SUCCESS: Application execution time = " + (endTime - startTime) + " ms");
        }
    }
}


