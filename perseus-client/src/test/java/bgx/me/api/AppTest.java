package bgx.me.api;

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class AppTest extends TestCase {

    public void testReadFile() throws IOException {
        App app = new App();
        File f1 = new File(".");
        System.out.println(f1.getAbsolutePath());
        File f = new File("data/test-file.json");
        String json = app.readFile(f);
        assertNotNull(json);
        System.out.println("JSON: " + json);
    }

    public void testExecute() throws Exception {
        App app = new App();
        app.setFileName("data/test-file.json");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        app.execute(baos);
        String result = new String(baos.toByteArray());
        System.out.println(result);
    }
}
