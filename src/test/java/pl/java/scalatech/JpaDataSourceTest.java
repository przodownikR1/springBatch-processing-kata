package pl.java.scalatech;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.java.scalatech.config.JpaConfig;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@SpringApplicationConfiguration(classes = { JpaConfig.class })
public class JpaDataSourceTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void shouldDatabaseSourceCreate() {
        Assertions.assertThat(dataSource).isNotNull();
    }

}
