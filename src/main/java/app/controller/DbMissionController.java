//package app.controller;
//
//import app.model.Mission;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/mission")
//public class DbMissionController {
//    @Autowired
//    private MissionRepository repository;
//    @RequestMapping("/")
//    public List<Mission> getMissions(){
//
//        // repository.save(new Mission("Mission 1", "Try to get into the Database..."));
//        System.out.println("Customers found with findAll():");
//        System.out.println("-------------------------------");
//        for (Object mission : repository.findAll()) {
//            System.out.println(mission.toString());
//        }
//        System.out.println();
//        return repository.findAll();
//    }
//}
