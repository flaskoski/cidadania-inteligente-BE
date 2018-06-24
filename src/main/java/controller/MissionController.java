package controller;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import model.Mission;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class MissionController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/myMissions")
    //public Mission sendMissions(@RequestParam(value="uid", defaultValue="") String idToken) {
    public Mission sendMissions(@RequestHeader(value="Authorization") String idToken) {//@RequestHeader String idToken
        final ArrayList<Mission> missions = new ArrayList<>();
        final Mission[] mission = new Mission[1];
        mission[0] = new Mission("Aventura no MASP",
                "Agora você vai mostrar que sabe tudo de arte respondendo perguntas sobre obras de arte presentes num dos pontos mais famosos de São Paulo, o MASP!");
        if(idToken != null)
            System.out.println("token caught!");
//        if (StringUtils.isBlank(idToken)) {
//            throw new IllegalArgumentException("FirebaseTokenBlank");
//        }

        ApiFutures.addCallback(FirebaseAuth.getInstance().verifyIdTokenAsync(idToken),
        new ApiFutureCallback<FirebaseToken>() {
            @Override
            public void onFailure(Throwable t) {
                missions.add(new Mission("Em busca do tesouro...", ""));
                System.out.print("failure");
            }
            @Override
            public void onSuccess(FirebaseToken decodedToken) {
                missions.add(new Mission("Aventura no MASP",
                        "Agora você vai mostrar que sabe tudo de arte respondendo perguntas sobre obras de arte presentes num dos pontos mais famosos de São Paulo, o MASP!"));
                missions.add(new Mission("Em busca do tesouro...", ""));

                System.out.println(decodedToken);
            }
        });
        System.out.println(missions.size());
        return mission[0];
    }

}
