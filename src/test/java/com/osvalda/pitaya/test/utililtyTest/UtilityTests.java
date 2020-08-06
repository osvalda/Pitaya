package com.osvalda.pitaya.test.utililtyTest;

import org.testng.annotations.Test;

import static com.osvalda.pitaya.util.PropertiesUtility.getStringProperty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UtilityTests {

    @Test
    public void testMissingMandatoryField() {
        assertThatThrownBy(() -> {
            getStringProperty("fake", true);
        }).hasMessage("fake config field is mandatory!");
    }

    @Test
    public void testMandatoryField() {
        assertThat(getStringProperty("application.name", true)).isEqualTo("Pitaya");
    }

    @Test
    public void testNotMandatoryField() {
        assertThat(getStringProperty("fake", false)).isEqualTo("");
    }

}
