import io.github.osvalda.pitaya.PitayaCoverageExtension;
import io.github.osvalda.pitaya.annotation.TestCaseSupplementary;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.github.osvalda.pitaya.util.Methods.*;

@ExtendWith(PitayaCoverageExtension.class)
public class JUnitReportTest {

    @Test
    @TestCaseSupplementary(api = {GET + "/posts", POST + "/fake"})
    public void testOne() {

    }

    @Test
    @TestCaseSupplementary(api = {GET + "/posts"})
    public void testTwo() {

    }

    @Test
    @Disabled
    @TestCaseSupplementary(api = {GET + "/posts"})
    public void testSkipped() {

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

    @Test
    @TestCaseSupplementary(api = {PUT + "/pet"})
    public void testSix() {

    }


}
