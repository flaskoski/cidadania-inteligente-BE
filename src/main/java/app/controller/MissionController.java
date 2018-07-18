package app.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import app.model.Mission;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MissionController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    @Autowired
    private MissionRepository missionRepository;

    @RequestMapping("/myMissions")
    //public Mission sendMissions(@RequestParam(value="uid", defaultValue="") String idToken) {
    public ArrayList<Mission> sendMissions(@RequestHeader(value="Authorization") String idToken) {//@RequestHeader String idToken
        final ArrayList<Mission> missions = new ArrayList<>();
        final Mission[] mission = new Mission[1];
        mission[0] = new Mission("Aventura no MASP",
                "Agora você vai mostrar que sabe tudo de arte respondendo perguntas sobre obras de arte presentes num dos pontos mais famosos de São Paulo, o MASP!");
        if(idToken != null)
            System.out.println("token caught!");
//        if (StringUtils.isBlank(idToken)) {
//            throw new IllegalArgumentException("FirebaseTokenBlank");
//        }
        CountDownLatch latch = new CountDownLatch(1);
        ApiFutures.addCallback(FirebaseAuth.getInstance().verifyIdTokenAsync(idToken),
        new ApiFutureCallback<FirebaseToken>() {
            @Override
            public void onFailure(Throwable t) {
                missions.add(new Mission("Em busca do tesouro...", ""));
                System.out.print("failure");
                latch.countDown();
            }
            @Override
            public void onSuccess(FirebaseToken decodedToken) {
                missions.add(new Mission("Aventura no MASP",
                        "Agora você vai mostrar que sabe tudo de arte respondendo perguntas sobre obras de arte presentes num dos pontos mais famosos de São Paulo, o MASP!"));
                missions.add(new Mission("Em busca do tesouro...", ""));

                System.out.println("token is valid.");

                for(Mission m : missionRepository.findAll())
                    missions.add(m);

                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(missions.size());
        return missions;
    }

}
