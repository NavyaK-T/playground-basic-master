import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class FileUtilsTest {
	@Test
    public void testReadFile(){
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource("patients.txt").getFile());
        assertTrue(file.exists());
    }
	
}
