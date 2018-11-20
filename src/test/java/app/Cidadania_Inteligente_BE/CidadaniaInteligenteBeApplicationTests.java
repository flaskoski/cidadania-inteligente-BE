package app.Cidadania_Inteligente_BE;

import app.controller.MissionsRepository;
import app.model.Mission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CidadaniaInteligenteBeApplicationTests {
//    @Autowired
//    private MissionsRepository missionsRepository;
//    @Autowired
//    private MockMvc mvc;
    @Test
	public void receiveMissionsArray() throws Exception {
//        List<Mission> missions = missionsRepository.findAll();
//        this.mvc.perform(get("/myMissions")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
	}

}
