package monsterservice.controller;

import monsterservice.model.Monster;
import monsterservice.service.MonsterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monster")
public class MonsterController {
    @Autowired
    private MonsterService monsterService;

    @GetMapping("/greeting")
    public String getGreeting() {
        return "Hi there!";
    }

    ;

    @PostMapping("/create")
    public Monster postCreate(@RequestBody Monster monster) { // RequestBody รับค่าทาง Body
        return monsterService.postCreateMonsterService(monster);
    }

    ;

    @GetMapping("/get-all")
    public List<Monster> getAll() {
        return monsterService.getAllMonsterService(); //ส่งค่าเป็น list
    }

    ;

    @GetMapping("/get-information")
    public Monster getInformation(@RequestHeader Integer id) { // RequestHeader รับค่าทาง Header
        return monsterService.getInformationService(id);
    }

    @PutMapping("/update")
    public Monster putUpdate(@RequestBody Monster monster) { // RequestHeader รับค่าทาง Header
        return monsterService.updateMonsterByIdService(monster);
    }

    @DeleteMapping("/delete")
    public boolean delete(@RequestHeader Integer id) { // RequestHeader รับค่าทาง Header
        return monsterService.deleteMonsterService(id);
    }

    @PutMapping("/attack")
    public Monster putAttack(@RequestHeader Integer id, @RequestHeader Integer health) {
        // RequestHeader รับค่าทาง Header
        return monsterService.attackMonsterService(id, health);
    }

}
