import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


@Slf4j
@SpringBootTest(classes = ApplicationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ApplicationTest {

    @Test
    public void loadFile() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("private.key");
        InputStream inputStream = classPathResource.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        for (String line = ""; line != null; line = reader.readLine()) {
            System.err.println(line);
        }
    }
}
