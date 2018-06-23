package controller;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import model.Mission;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MissionController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/myMissions")
    public Mission sendMissions(@RequestParam(value="name", defaultValue="World") String name) {
        final ArrayList<Mission> missions = new ArrayList<>();
        missions.add(new Mission("Aventura no MASP",
                "Agora você vai mostrar que sabe tudo de arte respondendo perguntas sobre obras de arte presentes num dos pontos mais famosos de São Paulo, o MASP!"));
        missions.add(new Mission("Em busca do tesouro...", ""));

        return new Mission("Aventura no MASP",
                "Agora você vai mostrar que sabe tudo de arte respondendo perguntas sobre obras de arte presentes num dos pontos mais famosos de São Paulo, o MASP!");
    }

}
