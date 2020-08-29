package io.github.osvalda.pitaya.test.utililtyTest;

import io.github.osvalda.pitaya.util.PropertiesUtility;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PropertiesUtilityTests {

    @Test
    public void testMissingMandatoryField() {
        assertThatThrownBy(() -> { PropertiesUtility.getStringProperty("fake", true); })
                .hasMessage("fake config field is mandatory!")
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testExistingMandatoryField() {
        Assertions.assertThat(PropertiesUtility.getStringProperty("application.name", true)).isEqualTo("Test App 2000");
    }

    @Test
    public void testExistingNotMandatoryField() {
        Assertions.assertThat(PropertiesUtility.getStringProperty("application.name", false)).isEqualTo("Test App 2000");
    }

    @Test
    public void testMissingNotMandatoryField() {
        Assertions.assertThat(PropertiesUtility.getStringProperty("fake", false)).isEmpty();
    }

}
