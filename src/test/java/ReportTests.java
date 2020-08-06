import com.osvalda.pitaya.EndpointCoverageReporter;
import com.osvalda.pitaya.annotation.TestCaseSupplementary;
import org.testng.SkipException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.osvalda.pitaya.util.Methods.*;

@Listeners({EndpointCoverageReporter.class})
public class ReportTests {

    @Test
    @TestCaseSupplementary(api = {GET + "/posts", POST + "/fake"})
    public void testOne() {

    }

    @Test
    @TestCaseSupplementary(api = {GET + "/posts"})
    public void testTwo() {

    }

    @Test
    @TestCaseSupplementary(api = {GET + "/posts"})
    public void testSkipped() {
        throw new SkipException("message");
    }

    @Test
    @TestCaseSupplementary(api = {GET + "/posts"})
    public void testFailed() {
        throw new AssertionError("error");
    }

    @Test
    @TestCaseSupplementary(api = {PUT + "/getter/point"})
    public void testFive() {

    }
}
