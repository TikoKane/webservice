package sn.isi.m2gl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sn.isi.m2gl.web.rest.TestUtil;

public class CoronaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Corona.class);
        Corona corona1 = new Corona();
        corona1.setId(1L);
        Corona corona2 = new Corona();
        corona2.setId(corona1.getId());
        assertThat(corona1).isEqualTo(corona2);
        corona2.setId(2L);
        assertThat(corona1).isNotEqualTo(corona2);
        corona1.setId(null);
        assertThat(corona1).isNotEqualTo(corona2);
    }
}
