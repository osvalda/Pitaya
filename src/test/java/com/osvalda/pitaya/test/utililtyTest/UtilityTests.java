package com.osvalda.pitaya.test.utililtyTest;

import org.testng.annotations.Test;

import static com.osvalda.pitaya.util.PropertiesUtility.getStringProperty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UtilityTests {

    @Test
    public void testMissingMandatoryField() {
        assertThatThrownBy(() -> { getStringProperty("fake", true); })
                .hasMessage("fake config field is mandatory!")
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testExistingMandatoryField() {
        assertThat(getStringProperty("application.name", true)).isEqualTo("Pitaya");
    }

    @Test
    public void testExistingNotMandatoryField() {
        assertThat(getStringProperty("application.name", false)).isEqualTo("Pitaya");
    }

    @Test
    public void testMissingNotMandatoryField() {
        assertThat(getStringProperty("fake", false)).isEmpty();
    }

}
