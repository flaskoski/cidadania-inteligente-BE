package controller;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import model.Mission;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MissionController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/myMissions")
    public ArrayList<Mission> sendMissions(@RequestParam(value="idToken", defaultValue="") String idToken) {
        final ArrayList<Mission> missions = new ArrayList<>();
        ApiFutures.addCallback(FirebaseAuth.getInstance().verifyIdTokenAsync(idToken),
        new ApiFutureCallback<FirebaseToken>() {
            @Override
            public void onFailure(Throwable t) {}
            @Override
            public void onSuccess(FirebaseToken decodedToken) {
                missions.add(new Mission("Aventura no MASP",
                        "Agora você vai mostrar que sabe tudo de arte respondendo perguntas sobre obras de arte presentes num dos pontos mais famosos de São Paulo, o MASP!"));
                missions.add(new Mission("Em busca do tesouro...", ""));
            }
        });
        return missions;
    }

}
